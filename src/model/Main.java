package model;

import exceptions.MissingEntranceRoomException;
import exceptions.MissingExitRoomException;

public class Main {

	public static void main(String[] args) {

		Dungeon dg = new Dungeon();

		try {
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
			System.out.println("Congratulation ! You have finished the game !");
		} catch (MissingExitRoomException | MissingEntranceRoomException e) {
			e.printStackTrace();
		}
	}

	//		try {
	//			for (int i = 4; i < 7; i++) { // i = 4 because random init min arg is 4
	//				System.out.println("Dungeon number "+(i-3));
	//				dg.randomInit(i);
	//				while(!dg.isGameOver())
	//					dg.update();
	//				if(!dg.isFinish())// if the player is dead, he can retry the level
	//					i--;
	//
	//			}
	//
	//		} catch (DungeonTooSmallException | MissingExitRoomException | MissingEntranceRoomException e) {
	//			e.printStackTrace();
	//		}
}

