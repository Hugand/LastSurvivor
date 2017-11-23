//--------------------------------------------------------------
// 
// GamePlay Panel: Where the "real" game starts
//
//--------------------------------------------------------------

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{
	
	//---------------Variables------------------

	private static final long serialVersionUID = 1L;

	//Timers
	Timer t = new Timer(5, this); //Overall timer
	Timer timer = new Timer(delay, spawnerNr); //Spawn normies
	Timer ammuT = new Timer(ammuDelay, spawnAmms); //Spawn normal ammunition
	Timer bbSpawn = new Timer(bdelay, spawnerBB); //Spawn the Big Bads
	Timer tBoom = new Timer(boomdelay, explode); //Delay for the explosion
	Timer ndammuT = new Timer(nddelay, spawnNDAmms); //Spawn the grenades ammuniton
	
	//Mouse mouse = new Mouse(); - not in use at the moment
	
	//Objects
	public static Player player = new Player(); //Player
	static ArrayList<Bullet> bullets = new ArrayList<Bullet>(); //bullets
	static ArrayList<Enemy> normies = new ArrayList<Enemy>(); //normies
	static ArrayList<Enemy> bigBads = new ArrayList<Enemy>(); //big bads
	static ArrayList<Ammu> ammus = new ArrayList<Ammu>(); //normal ammunition
	static ArrayList<Ammu> nadesAmmu = new ArrayList<Ammu>(); //nades amunition
	static ArrayList<Grenades> grenades = new ArrayList<Grenades>(); //grenades
	static ArrayList<boomArea> boomareas = new ArrayList<boomArea>(); //Explosion area
	static ArrayList<BoomAnim> anim = new ArrayList<BoomAnim>(); //Explosion animations
	static Highscore high = new Highscore(); //highscore
	KeyBinds keybinds = new KeyBinds(); //KeyBinds

	//Images and sprites
	public BufferedImage image, normiesS, bigbadS, grenadeS, ammoS, gammoS, bulletS;
	
	//Boolean
	public static boolean isU, isD, isL, isR, rL, rR, shot, throwed;

	//Float
	public static float nSpeed = 0.4f;

	//Double
	public static double dirct;
	public static double h; //Hypotenuse
	public static double angle;

	//Int
	
	public static int delay = 5000, ammuDelay = 7000, nddelay = 20000, bdelay = 15000, boomdelay = 1000;
	public int score = 0;

	public static int lifebar = 50;

	public int highscore = 0;

	//------------------------------------------

	public Game(){
		t.start(); //Start the global timer
		timer.start(); //Start normies spawn timer
		ammuT.start(); //Start ammunition spawn timer
		bbSpawn.start(); //Start BigBads spawn timer
		ndammuT.start(); //Start nades ammunition spawn timer
		
		loadImages();
		highscore = high.loadHigh();
		
		tBoom.setRepeats(false);
		
		//-------------To enable mouse--------------
		//mouse = new Mouse();
		//addMouseMotionListener(mouse);
		//addMouseListener(mouse);
		//------------------------------------------
		
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocusInWindow();
		
		throwed = shot = isU = isD = isL = isR = rL = rR = false;
		
	}

	//-------------------Paint the objects-------------------
	public void paint(Graphics g) {
		
		super.paint(g);
		
		Graphics2D p = (Graphics2D) g;
		Graphics2D b = (Graphics2D) g;

		// Objects shapes
		Shape pl; // = new Rectangle2D.Double(-player.l/2, -player.a/2, player.l, player.l);
					// //Declare player shape
		Shape blt; // Bullets shape
		Shape nr; // Normies shape
		Shape bb; // BigBads shape
		Shape amm; // Ammunitions shape
		Shape grnd; // Grenades shape
		Shape nda; // Grenade ammunition shape

		// If mouse is enabled
		// p.fillOval(Mouse.getX()-5, Mouse.getY()-5, 10, 10);
		// p.fillOval(player.x+player.l/2-5,player.y+player.l/2-5, 10, 10);
		// p.drawString("Button: "+Mouse.getButton(), 100, 100);
		
		// Life bar
		p.setColor(new Color(155, 10, 10)); // red
		p.fillRect(50, 50, player.life * lifebar, 10);
		
		//Affine Transforms
		AffineTransform tx = new AffineTransform(); // Create new "plain" for player
		AffineTransform[] nrp = new AffineTransform[normies.size()]; // Normies rotation "plain"
		AffineTransform[] bbp = new AffineTransform[bigBads.size()]; // BigBads rotation "plain"
		
		//-------------- Draw Explosion Animation ----------------
		for(int i = anim.size()-1; i >= 0; i--){
			BoomAnim ani = anim.get(i);
			
			p.drawImage(ani.animation.getSprite(), (int) ani.x-50, (int) ani.y-50, null);
			ani.animation.update();
			
			if(ani.animation.finished == true){ //If the animation is finished remove it from the ArrayList
				anim.remove(i);
			}
		}
		//--------------------------------------------------------
		
		// -------------------Draw player-----------------------
		tx.translate(player.x + player.l / 2, player.y + player.a / 2);
		tx.rotate(player.rotate);

		Rectangle2D shape = new Rectangle2D.Double(-player.l / 2, -player.a / 2, player.l, player.a);
		pl = tx.createTransformedShape(shape);
		tx.scale(0.3, 0.3);
		tx.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		p.setColor(new Color(100, 200, 150)); // Aqua Blue color
		// p.fill(pl);
		p.drawImage(image, tx, null);

		// -----------------------------------------------------

		// BigBads enemys
		p.setColor(new Color(244, 60, 66)); // Brownish orangish yellow
		for (int i = 0; i < bigBads.size(); i++) {

			Enemy bigbad = bigBads.get(i);

			// ---------------Rotate bigbads towards the player------------------------
			bbp[i] = new AffineTransform();

			double dx = player.x - bigbad.x;
			double dy = player.y - bigbad.y;
			dirct = Math.atan2(dy, dx) - 1.57079633;

			bbp[i].translate(bigbad.x, bigbad.y);
			bbp[i].rotate(dirct);
			bb = new Rectangle2D.Double(-bigbad.l / 2, -bigbad.l / 2, bigbad.l, bigbad.l);
			bb = bbp[i].createTransformedShape(bb);
			bbp[i].scale(0.4, 0.4);
			bbp[i].translate(-bigbadS.getWidth() / 2, -bigbadS.getHeight() / 2);

			// ------------------------------------------------------------------------

			bigbad.move(dirct - 3.14159265, 0.2); //Move the big bads
			p.drawImage(bigbadS, bbp[i], null); //Draw them

			// Collision BigBads-Player
			if (bb.intersects(pl.getBounds()) == true) {
				player.life -= 2;
				bigBads.remove(i);
			}

			// Collision BigBads-Bullets
			for (int h = 0; h < bullets.size(); h++) {
				Bullet bullet = bullets.get(h);

				blt = new Ellipse2D.Double(bullet.x, bullet.y, bullet.l, bullet.l);

				// Collision BigBads-Bullet
				if (bb.intersects(blt.getBounds()) == true) {
					bigbad.life--;
					bullets.remove(h);
				}

			}

		}

		// Normies enemys
		p.setColor(new Color(244, 149, 66)); // Brownish orangish yellow
		for (int i = normies.size() - 1; i >= 0; i--) {

			Enemy normie = normies.get(i);

			// ---------------Rotate normies towards the player------------------------
			nrp[i] = new AffineTransform();

			double dx = player.x - normie.x;
			double dy = player.y - normie.y;
			dirct = Math.atan2(dy, dx) - 1.57079633;

			nrp[i].translate(normie.x, normie.y);
			nrp[i].rotate(dirct);
			nr = new Rectangle2D.Double(-normie.l / 2, -normie.l / 2, normie.l, normie.l);
			nr = nrp[i].createTransformedShape(nr);
			nrp[i].scale(0.3, 0.3);
			nrp[i].translate(-normiesS.getWidth() / 2, -normiesS.getHeight() / 2);

			// ------------------------------------------------------------------------
			
			p.drawImage(normiesS, nrp[i], null); //Draw the normies

			// Move the Normies
			normie.move(dirct - 3.14159265, 0.4);

			// Collision Normies-Player
			if (nr.intersects(pl.getBounds()) == true) {
				player.life -= 1;
				normies.remove(i);
			}

		}

		// Bullets
		p.setColor(new Color(150, 100, 250)); // Purple color
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);

			blt = new Ellipse2D.Double(bullet.x, bullet.y, bullet.l, bullet.l);
			b.drawImage(bulletS, bullet.x, bullet.y, null); //Draw the bullets
		}

		// Ammunition
		p.setColor(new Color(10, 200, 100)); // Green color
		for (int i = 0; i < ammus.size(); i++) {
			Ammu ammu = ammus.get(i);

			amm = new Ellipse2D.Double(ammu.x, ammu.y, ammu.l, ammu.l);
			b.drawImage(ammoS, ammu.x, ammu.y, null); //Draw the normal ammunition

			// Collision Ammunition-Player
			if (amm.intersects(pl.getBounds()) == true) {
				player.gunB += 20;
				ammus.remove(i);
			}
		}
		
		// Grenade ammunition
		p.setColor(new Color(250, 10, 100)); // Green color
		for (int i = 0; i < nadesAmmu.size(); i++) {
			Ammu nade = nadesAmmu.get(i);
			
			nda = new Ellipse2D.Double(nade.x, nade.y, nade.l, nade.l);
			b.drawImage(gammoS, nade.x, nade.y, null); //draw the grenade ammunition
			
			// Collision Ammunition-Player
			if (nda.intersects(pl.getBounds()) == true) { //If the player catches it, it gains one more
				player.grenade += 1; 				// grenade and remove the ammuniton from the ground
				nadesAmmu.remove(i);
			}
		}

		// Grenades
		p.setColor(new Color(22, 113, 188)); // Green color
		for (int i = 0; i < grenades.size(); i++) {
			Grenades grenade = grenades.get(i);
			
			
			grnd = new Ellipse2D.Double(grenade.x, grenade.y, grenade.l, grenade.l);

			grenade.Move();
			
			p.drawImage(grenadeS, grenade.x, grenade.y, null);
			
			if (grenade.speed > 0)
				grenade.speed -= 0.05;
			
			if (grenade.speed <= 0.15 && grenade.speed > 0) {
				grenade.speed = 0;
				
				tBoom.start(); //Delay the explosion
				anim.add(new BoomAnim(grenade.x, grenade.y)); //Show the animation
				boomareas.add(new boomArea(grenade.x, grenade.y, 200)); //Create the range of the explosion
				
			}
		}
		
		

		// Grenade range and mechanics
		p.setColor(new Color(100, 155, 20));

		for (int i = boomareas.size() - 1; i >= 0; i--) {
			boomArea boom = boomareas.get(i);

			Shape boomS = boom.draw(p);
			
			// Check collisons with normies
			for (int h = normies.size() - 1; h >= 0; h--) {
				Enemy normie = normies.get(h);

				nrp[h] = new AffineTransform();

				double dx = player.x - normie.x;
				double dy = player.y - normie.y;
				dirct = Math.atan2(dy, dx) - 1.57079633;

				nrp[h].translate(normie.x, normie.y);
				nrp[h].rotate(dirct);

				nr = new Rectangle2D.Double(-normie.l / 2, -normie.l / 2, normie.l, normie.l);
				nr = nrp[h].createTransformedShape(nr);

				if (boomS.intersects(nr.getBounds()) == true) {
					System.out.println("destroy");
					normies.remove(h);
				}
				
			}

			// Check collisions with the big bads
			for (int a = bigBads.size() - 1; a >= 0; a--) {

				Enemy bigbad = bigBads.get(a);

				// ---------------Rotate bigbads towards the player------------------------
				bbp[a] = new AffineTransform();

				double ax = player.x - bigbad.x;
				double ay = player.y - bigbad.y;
				dirct = Math.atan2(ay, ax) - 1.57079633;

				bbp[a].translate(bigbad.x, bigbad.y);
				bbp[a].rotate(dirct);
				bb = new Rectangle2D.Double(-bigbad.l / 2, -bigbad.l / 2, bigbad.l, bigbad.l);
				bb = bbp[a].createTransformedShape(bb);
				bbp[a].scale(0.4, 0.4);
				bbp[a].translate(-bigbadS.getWidth() / 2, -bigbadS.getHeight() / 2);
				
				
				
				if (boomS.intersects(bb.getBounds()) == true) {
					System.out.println("destroy");
					bigBads.remove(a);
				}

			}
			
			if(i == 0){ //If nothing hit, destroy the grenade and the area
				grenades.remove(i);
				boomareas.remove(i);
			}
				
		}

		// Collision Bullets-Normies
		for (int i = normies.size() - 1; i >= 0; i--) {
			Enemy normie = normies.get(i);

			for (int h = bullets.size() - 1; h >= 0; h--) {
				Bullet bullet = bullets.get(h);

				// ---------------Rotate normies towards the player------------------------
				nrp[i] = new AffineTransform();

				double dx = player.x - normie.x;
				double dy = player.y - normie.y;
				dirct = Math.atan2(dy, dx) - 1.57079633;

				nrp[i].translate(normie.x, normie.y);
				nrp[i].rotate(dirct);

				nr = new Rectangle2D.Double(-normie.l / 2, -normie.l / 2, normie.l, normie.l);
				nr = nrp[i].createTransformedShape(nr);

				// ------------------------------------------------------------------------

				blt = new Ellipse2D.Double(bullet.x, bullet.y, bullet.l, bullet.l);
				
				//If bullets hit the normies 
				if (blt.intersects(nr.getBounds()) == true) {
					normie.life -= 1;
					bullets.remove(h);
				}
			}

		}
		
		//Draw thext on the screen
		Font font = new Font("Arial", Font.PLAIN, 30); // Create a font and give it a size

		p.setFont(font); // Set the text font
		p.setColor(new Color(17, 17, 17)); // Black

		p.drawString("Ammo: " + player.gunB, 100, 130); // Bullets count
		p.drawString("Score: " + score, 100, 100); // Score
		p.drawString("Grenades: " + player.grenade, 100, 160); // Grenades count
		p.drawString("Highscore: " + highscore, 100, 190); //Highscore

		
		

	}



	public void actionPerformed(ActionEvent arg0) {

		repaint();
		
		//Implement OS Detection statement!!!
		Toolkit.getDefaultToolkit().sync();
		
		if (score > highscore) {
			highscore = score;
		}
		
		//Restrain the player to the window
		if(player.x < 0){
			player.x = 0;
		}else if(player.x > Main.width-player.l){
			player.x = Main.width-player.l;
		}else if(player.y < 0){
			player.y = 0;
		}else if(player.y > Main.height-player.a){
			player.y = Main.height-player.a;
		}
		
		//If the player is dead
		if (player.life <= 0) {
			if(highscore > score){ //save highscore
				high.saveHigh(highscore);
				System.out.println("Saved");
			}
			
			Main.cardlayout.show(Main.cardPanel, "Gameover"); //Move to gameover
			player.life = 1; //Because of some bug I cant remember, I guess
			
			Main.gameover.scoreL.setText(""+score); //Update the gameover score text
			Main.gameover.highL.setText(""+highscore); //Update the gameover highscore text
			
		}

		// Grenades
		for (int i = 0; i < grenades.size(); i++) {
			Grenades grenade = grenades.get(i);

			double dx = player.x - grenade.x;
			double dy = player.y - grenade.y;
			angle = Math.atan2(dy, dx) + 1.5;

			if (grenade.speed > 0)
				grenade.speed -= 0.05;

			if (grenade.speed < 0) {
				grenade.speed = 0;
			}

		}

		// Normies
		for (int i = 0; i < normies.size(); i++) {
			Enemy normie = normies.get(i);

			// Kill normies
			if (normie.life <= 0) {
				normies.remove(i);
				score++;
			}

		}

		// BigBads
		for (int i = 0; i < bigBads.size(); i++) {
			Enemy bigbad = bigBads.get(i);

			// Kill BigBads
			if (bigbad.life <= 0) {
				bigBads.remove(i);
				score += 2;
			}

		}

		// Move the bullets
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);

			bullet.Move();

			if (outBounds(bullet.x, bullet.y))
				bullets.remove(i); // Destroy if outBounds

		}

		// --------------------Move the player------------------------
		if (isD)
			player.y += player.speed;

		if (isU) {
			player.y -= player.speed;
		}

		if (isR)
			player.x += player.speed;
		if (isL)
			player.x -= player.speed;

		// Player Rotations
		if (rL)
			player.rotate += -0.03f;
		if (rR)
			player.rotate += 0.03f;
		// -----------------------------------------------------------

	}


	//#####################Funcs and methods###########################
	
	
	public JPanel getPanel() { //Return this panel to the main class
		return this;
	}
	
	public void reset(){ //Reset all the values
		//Save the highscore
		high.saveHigh(highscore);
		
		//Reset score
		score = 0;
		//Reset life
		player.life = 10;
		
		player.gunB = 20;
		player.grenade = 1;
		player.x = player.y = Main.width/2;
		
		//Clear ArrayLists
		normies.clear();
		bigBads.clear();
		ammus.clear();
		boomareas.clear();
		bullets.clear();
		grenades.clear();
		
		System.out.println("Values reseted");
	}


	public void loadImages(){ //Load all the sprites needed
		try{
			image = ImageIO.read(Main.class.getResource("/images/player.png"));
			normiesS = ImageIO.read(Main.class.getResource("/images/zombiedevil.png"));
			bigbadS = ImageIO.read(Main.class.getResource("/images/demon.png"));
			grenadeS = ImageIO.read(Main.class.getResource("/images/grenade.png"));
			ammoS = ImageIO.read(Main.class.getResource("/images/ammo.png"));
			gammoS = ImageIO.read(Main.class.getResource("/images/grndammo.png"));
			bulletS = ImageIO.read(Main.class.getResource("/images/bullet.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public boolean outBounds(int x, int y){ //Check if an object is out of the window
		if(x < 0 || x > Main.width || y < 0 || y > Main.height) return true;
		return false;
	}
	
	//For shooting with the mouse
	/*public void updateShooting() {
		if(Mouse.getButton() == 1){
			double dx = Mouse.getX()-player.x;
			double dy = Mouse.getY()-player.y;
			double dir = Math.atan2(dy, dx);
			shoot(player.x, player.y, dir);

		}

	}*/

	public static void Spawn(){

		//Go Left
			normies.add(new Enemy(Main.width, (int) Math.floor(Math.random() * (Main.height-50)), 5, -nSpeed, 0, 50));

		//Go Right
			normies.add(new Enemy(-50, (int) Math.floor(Math.random() * (Main.height-50)), 5, nSpeed, 0, 50));
	}

	//Shooting
	public void shoot(int x, int y, double dir){
		bullets.add(new BulletV(x, y, dir));
	}

	//Throw a grenade
	public void Throw(int x, int y, double dir){
		grenades.add(new GrenadesV(x, y, dir));
	}
	
	
	
	//##################### Timer Methods ########################
	
	//Spawn normies
	static ActionListener spawnerNr = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	          Spawn();
	      }
	};

	//Grenade explosion delay
	static ActionListener explode = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {

	      }
	};

	//Spawn big bads
	static ActionListener spawnerBB = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	    	  bigBads.add(new Enemy((int) Math.floor(Math.random() * (Main.width-50)), Main.height, 15, 0, -0.2f, 50));
	      }
	};

	//Spawn normal ammunition
	static ActionListener spawnAmms = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	          ammus.add(new Ammu((int) Math.floor(Math.random()*(Main.width-30)), (int) Math.floor(Math.random()*(Main.height-30)) ) );
	      }
	};
	
	//Spawn grenade ammunition
	static ActionListener spawnNDAmms = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	          nadesAmmu.add(new Ammu((int) Math.floor(Math.random()*(Main.width-30)), (int) Math.floor(Math.random()*(Main.height-30)) ) );
	      }
	};

	
	//Class for the keybindings
	public class KeyBinds {
		public KeyBinds(){
			super();
			
			presses(); //Fix some bugs here
			releases(); //Fix some bugs here
			
			//False - pressed
			//True - released
		}
		
		public void presses(){
			//Move Up
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "up");
			Main.cardPanel.getActionMap().put("up", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Up stuff
	            	isU = true;	//if up press enable movement
	            	System.out.println("Go up");
	            }
	        });
			
			//Move Down
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "down");
			Main.cardPanel.getActionMap().put("down", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Down stuff
	            	isD = true;	//if down press enable movement
	            }
	        });
			
			//Move Left
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
			Main.cardPanel.getActionMap().put("left", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Left stuff
	            	isL = true;	//if left press enable movement
	            }
	        });
			
			//Move Right
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");
			Main.cardPanel.getActionMap().put("right", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Right stuff
	            	isR = true; //if right press enable movement
	            }
	        });
			
			//Rotate Left
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "rttleft");
			Main.cardPanel.getActionMap().put("rttleft", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Rotate left stuff
	            	rL = true;	//if left press enable movement
	            }
	        });
			
			//Rotate Right
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false), "rttright");
			Main.cardPanel.getActionMap().put("rttright", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Rotate right stuff
	            	rR = true; //if right press enable movement
	            }
	        });
			
			//Shoot
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "shoot");
			Main.cardPanel.getActionMap().put("shoot", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Shoot stuff
	            	
	            	if(shot == false && player.gunB > 0){
	            		shoot(player.x+player.l/2, player.y+player.a/2, player.rotate-1.5);
	        			shot = true;
	        			player.gunB--;
	            	}
	            	
	            }
	        });
			
			//Throw Grenades
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0, false), "throw");
			Main.cardPanel.getActionMap().put("throw", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Throw grenades stuff
	            	
	            	if(throwed == false && player.grenade > 0){
	            		player.grenade--;
	        			Throw(player.x+player.l/2, player.y+player.a/2, player.rotate-1.5);
	        			throwed = true;
	            	}
	            	
	            }
	        });
			
			//Suicide for Debugging
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0, false), "die");
			Main.cardPanel.getActionMap().put("die", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Suicide stuff
	            	player.life = 0;
	            }
	        });
		}
		
		public void releases(){
			//Move Up
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "upr");
			Main.cardPanel.getActionMap().put("upr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Up stuff
	            	isU = false;	//if up press enable movement
	            }
	        });
			
			//Move Down
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "downr");
			Main.cardPanel.getActionMap().put("downr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Down stuff
	            	isD = false;	//if down press enable movement
	            }
	        });
			
			//Move Left
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftr");
			Main.cardPanel.getActionMap().put("leftr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Left stuff
	            	isL = false;	//if left press enable movement
	            }
	        });
			
			//Move Right
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightr");
			Main.cardPanel.getActionMap().put("rightr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Right stuff
	            	isR = false; //if right press enable movement
	            }
	        });
			
			//Rotate Left
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), "rttleftr");
			Main.cardPanel.getActionMap().put("rttleftr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Rotate left stuff
	            	rL = false;	//if left press enable movement
	            }
	        });
			
			//Rotate Right
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, true), "rttrightr");
			Main.cardPanel.getActionMap().put("rttrightr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Rotate right stuff
	            	rR = false; //if right press enable movement
	            }
	        });
			
			//Shoot
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "shootr");
			Main.cardPanel.getActionMap().put("shootr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Shoot stuff
	            	
	            	shot = false;
	            	
	            }
	        });
			
			//Throw Grenades
			Main.cardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0, true), "throwr");
			Main.cardPanel.getActionMap().put("throwr", new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Throw grenades stuff
	            	throwed = false;
	            	
	            }
	        });
		}
		
		
	}
	
}
