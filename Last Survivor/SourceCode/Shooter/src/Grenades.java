//Grenades class

public class Grenades {
	public int x, y, l;
	double nx, ny, dirc, speed;
	boolean boom;
	
	public Grenades(int x, int y, double dir){
		this.x = x;
		this.y = y;
		this.l = 10;
		boom = false;
		speed = 4;
	}

	public void Move() {}
}
