public class BulletV extends Bullet{
	
	// This is a Vector
	
	public BulletV(int x, int y, double dir) {
		super(x, y, dir);
		
		speed = 4;
		
		nx = speed * Math.cos(dir);
		ny = speed * Math.sin(dir);
		
	}
	
	public void Move(){
		x += nx;
		y += ny;
	}	

}
