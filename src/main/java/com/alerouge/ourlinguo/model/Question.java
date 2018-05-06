package com.alerouge.ourlinguo.model;

import java.util.List;

public class Question {
	
	private String question;
	private List<String> answers;
	private boolean presenteAltraDomanda;
	
	public Question(){
		
	}

	public Question(String question, boolean presenteAltraDomanda, List<String> answers) {
		super();
		this.question = question;
		this.presenteAltraDomanda = presenteAltraDomanda;
		this.answers = answers;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public boolean isPresenteAltraDomanda() {
		return presenteAltraDomanda;
	}

	public void setPresenteAltraDomanda(boolean presenteAltraDomanda) {
		this.presenteAltraDomanda = presenteAltraDomanda;
	}

	
}
