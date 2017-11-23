//Class for the enemys

public class Enemy {
	
	int life, l; 
	float x, y, xspeed, yspeed;
	
	public Enemy(float x, float y, int life, float xspeed, float yspeed, int l){
		this.x = x;
		this.y = y;
		this.life = life;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.l = l;
	}
	
	public void move(double dir, double speed){ //Move the enemy towards the player
		
		this.x += speed * Math.sin(dir);
    	this.y -= speed * Math.cos(dir);
    	
	}
}
