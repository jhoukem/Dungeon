package monsters;

public class Vampire extends Monster {

	public Vampire() {
		super((int) ((Math.random() * 5) + 10), 12, "Vampire");
		// random life (btw 10-15)
	}

}
