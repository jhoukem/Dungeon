package items;

import model.Player;

public class HealPotion{
	private int value;
	
	
	public HealPotion() {
		setValue(40);
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	public void use(Player p){
		p.getSecours().remove(this);
		if(p.getHealth() + getValue() > 100)
			p.setHealth(100);
		else
			p.setHealth(p.getHealth() + getValue());
		System.out.println("You used a health potion and restored your health");
		System.out.println("Health : "+p.getHealth());
	}

}
