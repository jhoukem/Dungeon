package monsters;

public class Gnome extends Monster {
	public Gnome() {
		super((int) (Math.random()* 5 + 10), 2, "Gnome");
		//random life (btw 10-15)
	}
}
