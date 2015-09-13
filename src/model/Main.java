package model;

public class Main {

	public static void main(String[] args) {
		
		Dungeon dj = new Dungeon();
		dj.init();
		while(!dj.isGameOver()){
			dj.update();
		}
	}
}
