package rooms;

import java.util.Scanner;

import model.Player;
import monsters.Monster;


public class MonsterRoom extends Room {

	private Scanner	sc = new Scanner(System.in);
	private Monster monster ;

	public MonsterRoom(int n, Monster m) {
		super(n);
		monster = m;
	}

	@Override
	public void act(Player p){
		displayNum();
		if(monster.isAlive()){
			System.out.println("You are attacked by a monster ! ("+monster.getName()+")");
			fight(p);
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
					System.out.println("Write a number between 1 and 3");
			}
			else
				System.out.println("Write the number of your action choice !");
		}
		return rep;
	}

	private boolean isACorrectNumber(int num) {
		if(num < 1 || num > 3)
			return false;
		return true;
	}

	private void fight(Player p) {
		boolean escaped = false;

		while(p.isAlive() && monster.isAlive() && !escaped){
			displayInfos(p);
			int choice = getAnwser();
			if(choice == 1){
				monster.setHealth(monster.getHealth()-p.getWp().getPower());
				monster.act(p);
				System.out.println("You make "+p.getWp().getPower()+" damages to the monster");
				System.out.println("The monster makes you "+monster.getPower()+" damages");
			}
			else if(choice == 2){
				if(Math.random()*101 > 85){
					monster.setHealth(monster.getHealth()-p.getWp().getPower());
					System.out.println("Successful dodge !");
					System.out.println("You make "+p.getWp().getPower()+" damages to the monster");
				}
				else{
					System.out.println("You failed to dodge the monster attack");
					System.out.println("The monster makes you "+monster.getPower()+" damages");
					monster.act(p);
				}
			}
			else if(choice == 3){
				if(Math.random()*101 > 70){
					escaped  = true;
					System.out.println("You escaped from the monster and you ran back to the previous room");
					p.setCurrentRoom(p.getPreviousRoom());
				}
				else{
					monster.act(p);
					System.out.println("You failed to escape from the monster");
					System.out.println("The monster makes you "+monster.getPower()+" damages");
				}
					
			}
		}
		
		if(!p.isAlive()){
			System.out.println("You are dead ! Game Over...");
		}
		else if(!monster.isAlive()){
			System.out.println("Your health :"+p.getHealth());
			System.out.println("You kill the monster !");
			checkItem(p);
		}
		

	}

	private void displayInfos(Player p) {
		System.out.println("Your health :"+p.getHealth());
		System.out.println("Monster health : "+monster.getHealth());
		System.out.println("Choose an action :");
		System.out.println("1 : Attack(100%)\n2 : Dodge(15%)\n3 : Escape(30%)");
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

}
