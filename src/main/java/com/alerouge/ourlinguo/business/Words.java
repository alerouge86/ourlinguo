package com.alerouge.ourlinguo.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alerouge.ourlinguo.model.RisultatoRisposta;

public class Words {

	public static Map<String, String> abbinamentiParole;
	public static Map<String, String> abbinamentiParoleBackUp;	// non vengono cancellati
																// gli elementi da qui. Viene usata per il controllo delle risposte
	public static List<String> elencoRisposte;
	public static List<RisultatoRisposta> elencoRisultatoRisposta;	// tiene traccia delle risposte per poi stampare l'excel "risultato" alla fine
	
	public static List<String> elencoParoleItaliano;
	public static List<String> elencoParoleRusso;
	
	public static final String FILE_CAMPIONE_LOCATION = "uploads/originalFile.xlsx";
	public static final String FILE_ORIGINAL_LOCATION = "uploads/original.xlsx";
	
	
	public static void init(String nomeFile, int numeroParole, String callDa){

		if (elencoParoleItaliano!=null){
			// vuol dire che il metodo init è gia stato chiamato
			return;
		}
		
		Map<String, String> mappaCompleta = new HashMap<>();

		elencoParoleRusso = new ArrayList<String>();
		elencoParoleItaliano = new ArrayList<String>();
		
		try {
            FileInputStream excelFile = new FileInputStream(new File(nomeFile));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                int col = 0;
                String question = null;
                String answer = null;
                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        if (col++==0){
                        	question = upperCaseFirstLetter(currentCell.getStringCellValue()).trim();
                        } else {
                        	answer = upperCaseFirstLetter(currentCell.getStringCellValue()).trim();
                        }
                    }

                }
                if (callDa.equals("Russo")){
                    mappaCompleta.put(question, answer);
                } else {
                	mappaCompleta.put(answer, question);
                }

                elencoParoleRusso.add(question);
                elencoParoleItaliano.add(answer);
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

		
		// tra tutte le parole del file, devo selezionarle solo N
		abbinamentiParole = selezionaParole(mappaCompleta, numeroParole);
		
		abbinamentiParoleBackUp = new HashMap<String,String>(abbinamentiParole);

		initEleRisposte();
		
		elencoRisultatoRisposta = new LinkedList<>();
	}
	
	
	private static Map<String, String> selezionaParole(Map<String, String> mappaCompleta, int numeroParole){
		Map<String, String> mapNew = new HashMap<>();
		List<String> keys = new ArrayList<>(mappaCompleta.keySet());
		Collections.shuffle(keys);
		int paroleAggiunte = 0;
		for (String key: keys){
			if (numeroParole==0 || paroleAggiunte++<numeroParole){
				mapNew.put(key, mappaCompleta.get(key));
			} else {
				break;
			}
		}
		return mapNew;
	}
	
	
	private static String upperCaseFirstLetter(String strInput){
		try{
			return strInput.substring(0,1).toUpperCase() + strInput.substring(1);
		} catch (Exception e) {
			return strInput;
		}
	}
	
	
	private static void initEleRisposte(){
		
		elencoRisposte = new ArrayList<>();
		for (Map.Entry<String, String> entry: abbinamentiParole.entrySet()){
			elencoRisposte.add(entry.getValue());
		}
	}
	
	public static void restart(){
		abbinamentiParole = new HashMap<String,String>(abbinamentiParoleBackUp);
		initEleRisposte();
	}
	
	public static List<String> getElencoParoleItaliano() {
		return elencoParoleItaliano;
	}


	public static List<String> getElencoParoleRusso() {
		return elencoParoleRusso;
	}

}
