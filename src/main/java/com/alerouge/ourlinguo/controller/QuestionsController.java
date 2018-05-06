package com.alerouge.ourlinguo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alerouge.ourlinguo.business.Words;
import com.alerouge.ourlinguo.model.Question;

@RestController
public class QuestionsController {

	@RequestMapping("/nextQuestion")
	public Question nextQuestion(){

//		Words.init();
		
		Question question = new Question();

		List<String> eleRisposteRandom = new ArrayList<>();

		String questionStr = null;
		String rispostaStr = null;
		for (Map.Entry<String, String> entry: Words.abbinamentiParole.entrySet()){
			questionStr = entry.getKey();
			question.setQuestion(questionStr);
			rispostaStr = entry.getValue();
			eleRisposteRandom.add(rispostaStr);
			break;
		}
		// rimuovo l'abbinanamento appena elaborato
		Words.abbinamentiParole.remove(questionStr);

		// uso una lista temporanea in modo da poter cancellare momentaneamente le risposte gia usate
		List<String> listaTemp = new ArrayList<>(Words.elencoRisposte);
		
		// tolgo dalla lista temp la risposta esatta
		listaTemp.remove(listaTemp.indexOf(rispostaStr));
		
		// seleziono 3 risposte random + quella corretta
		int posizioneCasuale = (int)(Math.random()*listaTemp.size());
		eleRisposteRandom.add(listaTemp.get(posizioneCasuale));
		listaTemp.remove(posizioneCasuale);
		
		posizioneCasuale = (int)(Math.random()*listaTemp.size());
		eleRisposteRandom.add(listaTemp.get(posizioneCasuale));
		listaTemp.remove(posizioneCasuale);

		posizioneCasuale = (int)(Math.random()*listaTemp.size());
		eleRisposteRandom.add(listaTemp.get(posizioneCasuale));
		listaTemp.remove(posizioneCasuale);

		// shuffle
		Collections.shuffle(eleRisposteRandom);

		question.setAnswers(eleRisposteRandom);
		
		if (Words.abbinamentiParole.size()>0){
			question.setPresenteAltraDomanda(true);
		}
		
		return question;
	}
	
	@RequestMapping("/verifyAnswer")
	public String verifyAnswer(@RequestParam(value = "question") String question, @RequestParam(value = "answer") String answer){
		if (answer.equals(Words.abbinamentiParoleBackUp.get(question))){
			return "OK";
		} else {
			return "KO";
		}
	}

}
