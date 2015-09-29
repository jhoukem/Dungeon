package rooms;

import java.util.ArrayList;
import java.util.Collections;

public class Question {

	private String question;
	private String correctAnswer;
	private ArrayList<String> possibleAnswer = new ArrayList<String>();


	public Question(String q, String a, String a2){
		setQuestion(q);
		setCorrectAnswer(a);
		possibleAnswer.add(a);
		possibleAnswer.add(a2);
	}



	public void ask(){
		System.out.println(question);
		System.out.println("Choose the number of the correct answer");
		Collections.shuffle(possibleAnswer);
		for(int i = 0; i < possibleAnswer.size(); i++){
			System.out.println("Answer n°"+(i+1)+" : "+possibleAnswer.get(i));
		}
	}


/**
 * 
 * @param a an possible answers (it will always be a wrong answer
 *  because the right one is set at the instantiation
 */
	public void addAnswer(String a){
		possibleAnswer.add(a);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getGoodAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String goodAnswer) {
		this.correctAnswer = goodAnswer;
	}

	public boolean isCorrectAnswer(int n) {
		if(possibleAnswer.get(n - 1).equals(correctAnswer))
			return true;
		return false;		
	}
	
	public ArrayList<String> getPossibleAnswer() {
		return possibleAnswer;
	}

	public void setPossibleAnswer(ArrayList<String> possibleAnswer) {
		this.possibleAnswer = possibleAnswer;
	}



}
