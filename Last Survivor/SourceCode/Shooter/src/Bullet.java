//Bullet class

public class Bullet {
	int x, y, l, speed;
	double nx, ny;
	double angle;
	
	public Bullet(int x, int y, double dir){
		this.x = x;
		this.y = y;
		this.angle = dir;
		l = 10;
	}

	public void Move() {}
	
	
}
