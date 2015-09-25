package monsters;

public class Vampire extends Monster{

	public Vampire() {
		super((int) (Math.random()* 5 + 10), 12, "Gnome");
		//random life (btw 10-15)
	}

}
