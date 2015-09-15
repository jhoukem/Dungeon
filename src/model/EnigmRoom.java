package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class EnigmRoom extends Room {

	Scanner					sc					= new Scanner(System.in);
	String					line;
	String					question;
	HashMap<String, String>	answerToQuestion	= new HashMap<>();
	List<String>			listQuestion		= new ArrayList<>();

	public EnigmRoom(int n, boolean b) {
		super(n, b);
		/* à changer par un remplissage auto */
		answerToQuestion.put("question1", "réponse1");
		answerToQuestion.put("question2", "réponse2");
		/* idem */
		listQuestion.add("question1");
		listQuestion.add("question2");
	}

	@Override
	public void act(Player p) {
		System.out.println("Room nï¿½" + numero);
		System.out.println("welcome to the sphinx room, answering my question and you will get a sublime reward, fail and you will suffer my wrath");
		question = listQuestion.get((int) (Math.random() * listQuestion.size()));
	}

	public void askQuestion() {
		System.out.println(question);
	}

	public void getAnswer() {
		line = sc.nextLine();
	}

	/**
	 * WARNING this room need a locked room
	 * 
	 * @param line
	 * @param p
	 */
	public void checkAnswer(Player p) {
		if (line.equals(answerToQuestion.get(question))) {
			p.keyring.add(key);
		} else {
			System.out.println("feels my wrath");
			p.health--;
			System.out.println("Health :" + p.health);
		}
	}
}
