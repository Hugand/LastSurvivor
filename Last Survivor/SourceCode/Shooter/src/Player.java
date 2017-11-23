//----------------------------------------------------------------------
// Player: Self-explanatory. Creates the player and his caracteristics
//----------------------------------------------------------------------

public class Player {
	public int x, y, l, a, speed, life, gunB, grenade;
	public double rotate;
	
	public Player(){
		x = y = Main.width;
		l = 50;
		a = 50;
		speed = 3;
		rotate = 0;
		life = 10;
		gunB = 20;
		grenade = 1;
	}
	
}
