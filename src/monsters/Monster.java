package monsters;

import model.Player;

public abstract class Monster {
	private int health;
	private int power;
	private String name;
	

	public boolean isAlive(){
		return health > 0;
	}
	
	public Monster(int h, int p, String n){
		setHealth(h);
		setPower(p);
		setName(n);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void act(Player p) {
//		int r = (int) (Math.random() * 100);
//		if(r < 33 )//attack
			p.setHealth(p.getHealth() - power);
	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
