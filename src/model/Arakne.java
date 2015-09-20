package model;

public class Arakne extends Monster{

	public Arakne() {
		super((int) (Math.random()* 6 + 5), 3, "Arakne");
		//random life (btw 5-10)
	}
	public static void main(String[] args) {
		for(int i=0;i<5;i++)
		System.out.println((int) (Math.random()*101) );
	}
}
