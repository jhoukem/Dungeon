package model;

import java.util.Scanner;


public class EnigmRoom extends Room {

	private Scanner	sc = new Scanner(System.in);
	private int rep;
	private Question question;
	private boolean answered = false;

	public EnigmRoom(int n) {
		super(n);
		question = new Question("Es tu humain ?", "oui", "non");
		question.addAnswer("peut etre");
	}

	@Override
	public void act(Player p) {
		System.out.println("Room n°" + numero);
		if(!answered){
			System.out.println("Welcome to the sphinx room, answering my question and you will get a sublime reward, fail and you will suffer my wrath");
			question.ask();
			rep = getAnwser();
			if(question.isCorrectAnswer(rep)){
				System.out.println("Congratulation you can pass !");
				answered = true;
				checkKey(p);
			}
			else{
				System.out.println("Feels my wrath");
				p.health--;
				System.out.println("Health : " + p.health);
				if(p.health < 1)
					System.out.println("You are dead ! Game Over...");
				else{
					System.out.println("You have been send back to room n°"+p.getPreviousRoom().numero);
					p.setCurrentRoom(p.getPreviousRoom());
				}
			}
		}
	}


	private int getAnwser() {
		int rep = 0;
		String line;
		while(true){
			line = sc.nextLine();
			if(isANumber(line)){
				rep = Integer.parseInt(line);
				if(isACorrectNumber(rep)) // if the number is contains in the number of answer possible
					break;
				else
					System.out.println("Write a number between 1 and "+question.getPossibleAnswer().size());
			}
			else{
				System.out.println("Write the number of your anwser !");
			}
		}
		return rep;
	}

	private boolean isACorrectNumber(int num) {
		if(num > 0 && num < question.getPossibleAnswer().size() + 1)
			return true;
		return false;
	}

	public boolean isANumber(String line) {
		for(int i = 0; i < line.length(); i++){
			if(line.charAt(i) < '0' || line.charAt(i) > '9')
				return false;
		}
		return true;
	}

}
