//Class to "create" and start the explosion animation

import java.awt.image.BufferedImage;
public class BoomAnim {
	float x, y;
	
	//Img for each animation
	 public BufferedImage[] explodeAnim = {Sprite.getSprite(0, 0), Sprite.getSprite(1, 0), Sprite.getSprite(2, 0), Sprite.getSprite(3, 0), 
			Sprite.getSprite(4, 0), Sprite.getSprite(5, 0), Sprite.getSprite(6, 0), Sprite.getSprite(7, 0), Sprite.getSprite(8, 0), Sprite.getSprite(9, 0)};
		
	//anim state
	 public Animation explosion = new Animation(explodeAnim, 10);
	
	// This is the anim
	 public Animation animation = explosion;
	
	public BoomAnim(float x, float y){
		this.x = x;
		this.y = y;
		
		this.animation.start();
	}
	
}
