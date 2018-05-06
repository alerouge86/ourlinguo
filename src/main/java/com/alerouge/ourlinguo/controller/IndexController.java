package com.alerouge.ourlinguo.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alerouge.ourlinguo.business.Words;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String home(Map<String, Object> model){
		return "index";
	}

	
	private String fileLocation;
	 
	@PostMapping("/uploadExcelFile")
	public String uploadFile(@RequestParam(value = "callDa") String callDa, Model model, MultipartFile file) throws IOException {
		
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
			    String nomeFileConPath = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;

			    Words.init(nomeFileConPath, numeroParoleInt, callDa);
			    inizia = true;
			}
		}
		
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
     
        File file = null;
         
	    File currDir = new File(".");
	    String nomeFileConPath = currDir.getAbsolutePath().substring(0, currDir.getAbsolutePath().length() - 1) + Words.FILE_ORIGINAL_LOCATION;
        file = new File(nomeFileConPath);
         
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
 
         
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

	
}
