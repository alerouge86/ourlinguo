package com.alerouge.ourlinguo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alerouge.ourlinguo.business.StampaRisultatoExcel;
import com.alerouge.ourlinguo.business.Words;
import com.alerouge.ourlinguo.utilities.Utility;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String home(Map<String, Object> model){
		return "index";
	}

	
	private String fileLocation;
	 
	@PostMapping("/uploadExcelFileTemp")
	public String uploadFileTemp(@RequestParam(value = "callDa") String callDa, Model model, MultipartFile file) throws IOException {
		
		// pulisco la cartella "temp"
	    File currDir = new File(".");
	    String pathTemp = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + "uploads/temp/";
	    FileUtils.cleanDirectory(new File(pathTemp));
		
	    InputStream in = file.getInputStream();
	    fileLocation = pathTemp + file.getOriginalFilename();
	    try {
		    FileOutputStream f = new FileOutputStream(fileLocation);
		    int ch = 0;
		    while ((ch = in.read()) != -1) {
		        f.write(ch);
		    }
		    f.flush();
		    f.close();
		    
		    // carico la mappa delle parole
		    Words.init(fileLocation, 0, callDa);
		    model.addAttribute("numeroDomande", String.valueOf(Words.abbinamentiParoleBackUp.size()));
		    return "start";
		} catch (Exception e) {
		    return "index";
		}
	}
	
	
	@PostMapping("/uploadExcelFileOriginal")
	public String uploadFileOriginal(Model model, MultipartFile file) throws IOException {
	    File currDir = new File(".");
	    String nomeFileCompleto = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;

	    String pathTemp = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + "uploads/";
		
	    InputStream in = file.getInputStream();
	    fileLocation = pathTemp + file.getOriginalFilename();
	    try {
		    FileOutputStream f = new FileOutputStream(nomeFileCompleto);
		    int ch = 0;
		    while ((ch = in.read()) != -1) {
		        f.write(ch);
		    }
		    f.flush();
		    f.close();
		    
		    model.addAttribute("fileOriginalCaricato", "Y");
		} catch (Exception e) {
		    model.addAttribute("fileOriginalCaricato", "N");
		}
	    return "index";
	}
	

	@RequestMapping("/restoreFileExcel")
	public String restoreFileExcel(Model model){

		try {
		    File currDir = new File(".");
		    String nomeFileCompletoSorg = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_CAMPIONE_LOCATION;
		    String nomeFileCompletoDest = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;
			
		    File fileSorg = new File(nomeFileCompletoSorg);
		    File fileDest = new File(nomeFileCompletoDest);
		    
		    
		    Files.copy(fileSorg.toPath(), fileDest.toPath(), StandardCopyOption.REPLACE_EXISTING);

		    model.addAttribute("fileCampioneCaricato", "Y");
		    
		} catch (Exception e) {
		    model.addAttribute("fileCampioneCaricato", "N");
		}
	    return "index";
	}


	@RequestMapping("/startOriginal")
	public String startOriginal(@RequestParam(value = "callDa") String callDa, @RequestParam(value = "numeroParole") String numeroParole, Model model){
	    
		boolean inizia = false;
		if (numeroParole!=null){
			int numeroParoleInt = 0;
			try {
				numeroParoleInt = Integer.parseInt(numeroParole);
			} catch (Exception e) {
			}
			if (numeroParoleInt>0){
				// carico la mappa delle parole
			    File currDir = new File(".");
			    String nomeFileCompleto = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;

			    Words.init(nomeFileCompleto, numeroParoleInt, callDa);
			    inizia = true;
			}
		}
		
		model.addAttribute("callDa", callDa);
		
		if (!inizia){
			model.addAttribute("richiediNumeroParole", "Y");
		    model.addAttribute("numeroDomande", "0");
		} else {
			model.addAttribute("richiediNumeroParole", "N");
		    model.addAttribute("numeroDomande", String.valueOf(Words.abbinamentiParoleBackUp.size()));
		}
		
	    return "start";
	}

	
	
	@RequestMapping("/restart")
	public String restart(Model model){
		Words.restart();
	    model.addAttribute("numeroDomande", String.valueOf(Words.abbinamentiParoleBackUp.size()));

		return "start";
	}

	@RequestMapping(value="/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response) throws IOException {
     
	    File currDir = new File(".");
	    String nomeFileCompleto = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;
         
		Utility.downloadFile(nomeFileCompleto, false, response);
    }


	@RequestMapping(value="/viewResult", method = RequestMethod.GET)
    public void viewResult(HttpServletResponse response) throws IOException {
     
		// esegue la stampa
		StampaRisultatoExcel stampaRisultatoExcel = new StampaRisultatoExcel(Words.elencoRisultatoRisposta);
		String nomeFileCompleto = stampaRisultatoExcel.creaStampa();

		Utility.downloadFile(nomeFileCompleto, true, response);
    }
	
}
