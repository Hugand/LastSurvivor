
public class GrenadesV extends Grenades{
	
	// This is a Vector
	
	public GrenadesV(int x, int y, double dir) {
		super(x, y, dir);
		
		dirc = dir;
		
		nx = speed * Math.cos(dir);
		ny = speed * Math.sin(dir);
		
	}
	
	public void Move(){
		
		nx = speed * Math.cos(dirc);
		ny = speed * Math.sin(dirc);
		
		x += nx;
		y += ny;
		
	}	

}
