package com.alerouge.ourlinguo.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alerouge.ourlinguo.business.Words;

@Controller
public class HelperController {

	@RequestMapping("/startHelper")
	public String startHelper(@RequestParam(value = "callDa") String callDa, Model model){
	    
		// carico la mappa delle parole
	    File currDir = new File(".");
	    String nomeFileCompleto = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;
	    Words.init(nomeFileCompleto, 0, callDa);
	    
		model.addAttribute("elencoWords", Words.elencoRisposte);
		
	    return "helper";
	}
	
}
