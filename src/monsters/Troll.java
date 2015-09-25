package monsters;

public class Troll extends Monster {

	public Troll() {
		super((int) (Math.random()* 21 + 10), 20, "Troll");
		//random life (btw 10-15)
	}
}
