package rooms;

import java.util.ArrayList;
import java.util.Scanner;

import items.Baton;
import items.HealPotion;
import items.Mace;
import items.Spike;
import items.Sword;
import items.Weapon;
import model.GenerateFromFile;
import model.Player;


public class EnigmaRoom extends Room {

	private Scanner	sc = new Scanner(System.in);
	private int rep;
	private Question question;
	private boolean answered = false;

	public EnigmaRoom(int n) {
		super(n);
		try {
			ArrayList<Question> questions = GenerateFromFile.getAllQuestions("lib_questions.txt");
			question = getRandomQuestion(questions);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @param questions the arraylist with all the question
	 * @return a random question in the liste questions
	 */
	private Question getRandomQuestion(ArrayList<Question> questions) {
		int alea = (int) (Math.random() * questions.size());
		return questions.get(alea);
	}

	@Override
	public void act(Player p) {
		
		if(!answered){// if the player has not answered to the sphinx question
			System.out.println("Welcome to the sphinx room, answering my question"
					+ " and you will get a sublime reward, fail and you will suffer my wrath");
			question.ask();
			rep = getAnwser();
			if(question.isCorrectAnswer(rep)){
				System.out.println("Congratulation you can pass !");
				answered = true;
				giveRandomWeapon(p);
				checkRoom(p);
			}
			else{// if the answer is not correct, the player lose life and is send back to the previous room
				System.out.println("Feels my wrath");
				p.setHealth(p.getHealth() - 1);
				System.out.println("Health : " + p.getHealth());
				if(!p.isAlive())
					System.out.println("You are dead ! Game Over...");
				else{
					System.out.println("You have been send back to room "
							+ "n�"+p.getPreviousRoom().getNumero());
					p.setCurrentRoom(p.getPreviousRoom());
					p.getCurrentRoom().act(p);//a voir
				}
			}
		}
		else
			checkRoom(p);

	}


	public void giveRandomWeapon(Player p) {
		Weapon wp;
		int alea = (int) (Math.random()*101);
		if(alea < 30)
			wp = new Baton();
		else if(alea < 75)
			wp = new Sword();
		else if(alea < 90)
			wp = new Spike();
		else 
			wp = new Mace();

		if(p.getWp().getPower() < wp.getPower()){
			System.out.println("I give you a "+wp.getName());
			p.setWp(wp);
		}
		else if(p.getWp().getPower() == wp.getPower()){
			System.out.println("You already have a"+wp.getName());
			System.out.println("So I give you a health potion. You can use it"
					+ " by writing 'potion' in the commands bar");
			p.getSecours().add(new HealPotion());
		}
		else{
			System.out.println("I have a "+wp.getName()+" but your "
					+ ""+p.getWp().getName()+" is better !");
			System.out.println("So I give you a health potion. You can use it"
					+ " by writing potion in the commands bar");
			p.getSecours().add(new HealPotion());
		}

	}

	private int getAnwser() {
		int rep = 0;
		String line;
		while(true){
			line = sc.nextLine();
			if(isANumber(line)){
				rep = Integer.parseInt(line);
				if(isACorrectNumber(rep)) // if the number is contains in the number of possibles answers
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

	public boolean isACorrectNumber(int num) {
		if(num > 0 && num < question.getPossibleAnswer().size() + 1)
			return true;
		return false;
	}

}
