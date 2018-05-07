package com.alerouge.ourlinguo.model;

public class RisultatoRisposta {
	private String question;
	private String correctAnswer;
	private String result;
	private String givenAnser;
	
	public RisultatoRisposta(String question, String correctAnswer, String result, String givenAnser) {
		super();
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.result = result;
		this.givenAnser = givenAnser;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getGivenAnswer() {
		return givenAnser;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnser = givenAnswer;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
