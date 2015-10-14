package model;

import java.util.Scanner;

import exceptions.DungeonTooSmallException;
import exceptions.MissingEntranceRoomException;
import exceptions.MissingExitRoomException;

public class Main {

	static Scanner sc = new Scanner(System.in);
	
	
	public static  boolean isANumber(String line) {
		if(line.isEmpty())
			return false;
		for(int i = 0; i < line.length(); i++){
			if(line.charAt(i) < '0' || line.charAt(i) > '9')
				return false;
		}
		return true;
	}
	
	public static int getAnwser() {
		int rep = 0;
		String line;
		while(true){
			line = sc.nextLine();
			if(isANumber(line)){
				rep = Integer.parseInt(line);
				if(rep == 1 || rep == 2) // if the number is contains in the number of possibles answers
					break;
				else
					System.out.println("Write a number between 1 and 2");
			}
			else{
				System.out.println("Write the number of your anwser !");
			}
		}
		return rep;
	}
	
	
	public static void main(String[] args) {
		
		boolean random = false;
		System.out.println("1) Random dj");
		System.out.println("2) Dungeon from text");

		int rep = Main.getAnwser();
		if(rep == 1)
			random = true;
		else
			random = false;
		
		
		Dungeon dg = new Dungeon();

		try {

			if(!random){
				for(int i = 1; i < 4;i++){
					dg.initFromFile("dj"+i+".txt");
					System.out.println("Dungeon number "+i);
					while(!dg.isGameOver())
						dg.update();
					if(!dg.isFinish()){// if the player is dead, he can retry the level
						i--;
						System.out.println("You loose ! The level is restarted !");
					}
				}
			}
			else{
				for (int i = 4; i < 7; i++) { // i = 4 because random init min arg is 4
					System.out.println("Dungeon number "+(i-3));
					dg.randomInit(i);
					while(!dg.isGameOver())
						dg.update();
					if(!dg.isFinish()){// if the player is dead, he can retry the level
						i--;
						System.out.println("You loose ! The level is restarted randomly... !");	
					}
				}
			}
			System.out.println("Congratulation ! You have finished the game !");
		} catch (MissingExitRoomException | MissingEntranceRoomException | DungeonTooSmallException e) {
			e.printStackTrace();
		}
	}
}

