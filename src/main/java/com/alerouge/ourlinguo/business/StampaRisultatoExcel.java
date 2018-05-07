package com.alerouge.ourlinguo.business;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alerouge.ourlinguo.model.RisultatoRisposta;

public class StampaRisultatoExcel {

	private String nomeFile;
	private List<RisultatoRisposta> elencoRisultatoRisposta;
	
	private String dateTime;
	
	public StampaRisultatoExcel(List<RisultatoRisposta> elencoRisultatoRisposta){
		this.elencoRisultatoRisposta = elencoRisultatoRisposta;
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy (HH:mm:ss)");
		dateTime = LocalDateTime.now().format(dtf);
				
		Date date = new Date();
		this.nomeFile = "ourlinguoResult_" + new Timestamp(date.getTime()).toString().replace(" ", "") + ".xlsx";
	}

	public String creaStampa(){
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Result");
		
		int rowNum = 0;
		
		Row row = sheet.createRow(rowNum++);

		int colNum = 0;

		Font fontBold = workbook.createFont();
		fontBold.setBold(true);

		CellStyle cellStyleDataOra = workbook.createCellStyle();
		cellStyleDataOra.setFont(fontBold);
		
		// data/ora
		Cell cell = row.createCell(colNum++);
		cell.setCellValue(dateTime);
		cell.setCellStyle(cellStyleDataOra);
		
		// intestazione
		Font fontHeader = workbook.createFont();
		fontHeader.setColor(HSSFColor.WHITE.index);
		fontHeader.setBold(true);

		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); 
		cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyleHeader.setFont(fontHeader);
		
		rowNum++;
		
		colNum = 0;
		row = sheet.createRow(rowNum++);
		
		cell = row.createCell(colNum++);
		cell.setCellValue("Question");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(colNum++);
		cell.setCellValue("Correct Answer");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(colNum++);
		cell.setCellValue("Result");
		cell.setCellStyle(cellStyleHeader);

		cell = row.createCell(colNum++);
		cell.setCellValue("Given Anser");
		cell.setCellStyle(cellStyleHeader);

		
		CellStyle cellStyleGreyRow = workbook.createCellStyle();
		cellStyleGreyRow.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); 
		cellStyleGreyRow.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// borders
		cellStyleGreyRow.setBorderBottom(CellStyle.BORDER_THIN);
	    cellStyleGreyRow.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyleGreyRow.setBorderLeft(CellStyle.BORDER_THIN);
	    cellStyleGreyRow.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyleGreyRow.setBorderRight(CellStyle.BORDER_THIN);
	    cellStyleGreyRow.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyleGreyRow.setBorderTop(CellStyle.BORDER_THIN);
	    cellStyleGreyRow.setTopBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle cellStyleGreyWhite = workbook.createCellStyle();
		// borders
		cellStyleGreyWhite.setBorderBottom(CellStyle.BORDER_THIN);
	    cellStyleGreyWhite.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyleGreyWhite.setBorderLeft(CellStyle.BORDER_THIN);
	    cellStyleGreyWhite.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyleGreyWhite.setBorderRight(CellStyle.BORDER_THIN);
	    cellStyleGreyWhite.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyleGreyWhite.setBorderTop(CellStyle.BORDER_THIN);
	    cellStyleGreyWhite.setTopBorderColor(IndexedColors.BLACK.getIndex());

	    
		int contatoreRighe = 0;
		for (RisultatoRisposta risultatoRisposta: elencoRisultatoRisposta){

			contatoreRighe++;
			
			row = sheet.createRow(rowNum++);
			colNum = 0;
			// question
			cell = row.createCell(colNum++);
			cell.setCellValue(risultatoRisposta.getQuestion());
			if (contatoreRighe%2==0){
				cell.setCellStyle(cellStyleGreyRow);
			} else {
				cell.setCellStyle(cellStyleGreyWhite);
			}
			// correct answer
			cell = row.createCell(colNum++);
			cell.setCellValue(risultatoRisposta.getCorrectAnswer());
			if (contatoreRighe%2==0){
				cell.setCellStyle(cellStyleGreyRow);
			} else {
				cell.setCellStyle(cellStyleGreyWhite);
			}
			// result
			cell = row.createCell(colNum++);
			cell.setCellValue(risultatoRisposta.getResult());
			if (contatoreRighe%2==0){
				cell.setCellStyle(cellStyleGreyRow);
			} else {
				cell.setCellStyle(cellStyleGreyWhite);
			}
			// correct answer
			if (risultatoRisposta.getResult().equals("KO")){
				cell = row.createCell(colNum++);
				cell.setCellValue(risultatoRisposta.getGivenAnswer());
			} else {
				cell = row.createCell(colNum++);
				cell.setCellValue("");
			}
			if (contatoreRighe%2==0){
				cell.setCellStyle(cellStyleGreyRow);
			} else {
				cell.setCellStyle(cellStyleGreyWhite);
			}
		}
		
		//Auto size all the columns
	    for (int x = 0; x < sheet.getRow(colNum-1).getPhysicalNumberOfCells(); x++) {
	        sheet.autoSizeColumn(x);
	    }
		
	    String nomeFileCompleto = "results/"+this.nomeFile;
		try {
			FileOutputStream outputStream = new FileOutputStream(nomeFileCompleto);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e){
			
		} catch (IOException e){
			
		}
		return nomeFileCompleto;
	}
	
}
