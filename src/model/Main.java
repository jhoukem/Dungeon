package model;

import exceptions.DungeonTooSmallException;
import exceptions.MissingExitRoom;

public class Main {

	public static void main(String[] args) {

		Dungeon dj = new Dungeon();
		try {
			dj.randomInit(4);
			while(!dj.isGameOver()){
				dj.update();
			}
		} catch (DungeonTooSmallException | MissingExitRoom e) {
			e.printStackTrace();
		}
		//			try {
		//				dj.initFromFile("dj2.txt");
		//				while(!dj.isGameOver()){
		//					dj.update();
		//				}
		//			} catch (MissingExitRoom e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}

	} 
}

