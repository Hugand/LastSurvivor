//Mouse Panel Disclaimer- Not in use at the moment

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{
	
	
	public boolean clicked = false;
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	//private Player p = Game.player;
	
	public static int getX(){
		return mouseX;
	}
	
	public static int getY(){
		return mouseY;
	}
	
	public static int getButton(){
		return mouseB;
	}
	
	
	
	
	public void mouseDragged(MouseEvent e) {
		
		/*if(Main.stage == 1){
			mouseX = e.getX();
			mouseY = e.getY();
			double dx = Mouse.getX()-p.x+p.l/2;
			double dy = Mouse.getY()-p.y+p.a/2;
			double dir = Math.atan2(dy, dx);
			p.rotate = dir+1.5;
		
		}*/
		
	}

	
	public void mouseMoved(MouseEvent e) {
		
		/*if(Main.stage == 1){
			mouseX = e.getX();
			mouseY = e.getY();
			double dx = Mouse.getX()-p.x+p.l/2;
			double dy = Mouse.getY()-p.y+p.a/2;
			double dir = Math.atan2(dy, dx);
			p.rotate = dir+1.5;
		}*/
	}
	
	public void mouseClicked(MouseEvent e) {}

	
	public void mouseEntered(MouseEvent e) {}

	
	public void mouseExited(MouseEvent e) {}

	
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
		clicked = true;
	}

	
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
		clicked = false;
	}


}
