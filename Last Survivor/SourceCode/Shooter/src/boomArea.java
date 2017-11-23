//Class for the greanade explosion area

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class boomArea {
	int x, y, l;
	Shape sp;
	
	public boomArea(int x, int y, int l){
		this.x = x;
		this.y = y;
		this.l = l;
	}
	
	public Shape draw(Graphics g){
		
		sp = new Ellipse2D.Double(x-l/2, y-l/2, l, l);
	
		return sp;
	}
	
}
