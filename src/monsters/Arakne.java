package monsters;



public class Arakne extends Monster{

	public Arakne() {
		super((int) (Math.random()* 6 + 20), 10, "Arakne");
		//random life (btw 20-25)
	}
}
