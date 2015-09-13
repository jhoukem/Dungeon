
public class Main {

	public static void main(String[] args) {
		
		Dungeon dj = new Dungeon();
		dj.init();
		while(!dj.isGameOver()){
			dj.update();
		}
		
		if(dj.p.currentRoom.isExit)
			System.out.println("Congratulation ! You escape from the dungeon !");
		else
			System.out.println("You have fallen in a trap !  Game Over ...");
	
	}
}
