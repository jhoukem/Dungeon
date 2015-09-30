package model;

import exceptions.DungeonTooSmallException;
import exceptions.MissingEntranceRoomException;
import exceptions.MissingExitRoomException;

public class Main {

	public static void main(String[] args) {

		Dungeon dj = new Dungeon();
		try {
			for (int i = 4; i < 7; i++) { // i = 4 because random init min arg is 4
				System.out.println("Dungeon number "+(i-3));
				dj.randomInit(i);
				while(!dj.isGameOver())
					dj.update();
				if(!dj.isFinish())// if the player is dead, he can retry the level
					i--;

			}

		} catch (DungeonTooSmallException | MissingExitRoomException | MissingEntranceRoomException e) {
			e.printStackTrace();
		}
	} 
}

