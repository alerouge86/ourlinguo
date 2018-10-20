package com.alerouge.ourlinguo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alerouge.ourlinguo.business.Words;

@Controller
public class HelperController {

	
	@RequestMapping("/showHelper")
	public String showHelper(Model model){
	    
		model.addAttribute("flgShowHelper", "Y");
		
	    return "helper";
	}
	
	
	@RequestMapping("/startHelper")
	public String startHelper(@RequestParam(value = "callDa") String callDa, @RequestParam(value = "showTranslation") String showTranslation, Model model){
		// carico la mappa delle parole
	    File currDir = new File(".");
	    String nomeFileCompleto = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;
	    Words.init(nomeFileCompleto, 0, callDa);
	    
		model.addAttribute("flgStartHelper", "Y");
		
		List<String> elencoOption = null;
			
		if ("Y".equals(showTranslation)){
			elencoOption = new ArrayList<String>();
	        if (callDa.equals("Russo")){
	        	int index = 0;
	        	for (String parola: Words.getElencoParoleRusso()){;
	        		elencoOption.add(parola + " (" + Words.getElencoParoleItaliano().get(index++) + ")");
	        	}
	        } else {
	        	int index = 0;
	        	for (String parola: Words.getElencoParoleItaliano()){;
	        		elencoOption.add(parola + " (" + Words.getElencoParoleRusso().get(index++) + ")");
	        	}
	        }
		} else {
	        if (callDa.equals("Russo")){
	        	elencoOption = Words.getElencoParoleRusso();
	        } else {
	        	elencoOption = Words.getElencoParoleItaliano();
	        }
		}
		model.addAttribute("elencoWords", elencoOption);
	    return "helper";
	}
	
}
