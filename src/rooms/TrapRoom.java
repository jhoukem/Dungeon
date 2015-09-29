package rooms;

import model.Player;

public class TrapRoom extends Room{

	private boolean activated;

	public TrapRoom(int n) {
		super(n);
	}


	@Override
	public void act(Player p) {
		if(!activated){
			System.out.println("You fall in a trap");
			p.setHealth(p.getHealth() - 1);
			System.out.println("Health : "+p.getHealth());
			setActivated(true);
		}
		if(p.isAlive())
			checkRoom(p);
	}


	public boolean isActivated() {
		return activated;
	}


	public void setActivated(boolean activated) {
		this.activated = activated;
	}

}
