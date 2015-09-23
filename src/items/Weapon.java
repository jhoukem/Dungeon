package items;

public abstract class Weapon {
	private int power;
	private final String name;
	
	public Weapon(int n, String s){
		setPower(n);
		name = s;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getName() {
		return name;
	}

}
