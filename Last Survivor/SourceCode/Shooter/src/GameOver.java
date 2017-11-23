//Game Over Panel

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameOver {
	
	public int mbx, mby, s = 0;
	
	public JLabel scoreL, highL;
	
	public GameOver() {
		mbx = Main.width/2 + 100;
		mby = Main.height/2 + 100;
	}
	
	
	
	public JPanel show() { //Method to return this panel to the Main class
		
		JButton restart = new JButton("Restart"); //Restart button
		JButton menu = new JButton("Menu"); //Menu button
		JPanel controlPanel = new JPanel(); //This JPanel
		
		
		scoreL = new JLabel(""+Main.game.score, SwingConstants.CENTER); //JLabel to show the score
		highL = new JLabel(""+Main.game.highscore, SwingConstants.CENTER); //JLabel to show the highscore
		
		//Main.game.getPanel().setVisible(false);
		
		controlPanel.setLayout(null);
		
		restart.addActionListener(new ActionListener() { //Restart button listener
	        public void actionPerformed(ActionEvent e) {
	           Main.game.reset();
	           Main.cardlayout.show(Main.cardPanel, "Game"); //Go to Game Panel
	        }
	    });
		
		menu.addActionListener(new ActionListener() { //Menu button Listener
	        public void actionPerformed(ActionEvent e) {
	           Main.cardlayout.show(Main.cardPanel, "Menu"); //Go to Menu Panel
	        }          
	    });
		
		controlPanel.add(restart); //Add all of the elements to the panel
		controlPanel.add(menu);
		controlPanel.add(scoreL);
		controlPanel.add(highL);
		
		restart.setBounds(Main.width/2-150, 400, 300, 50); //Position and resize the restart button
		menu.setBounds(Main.width/2-150, 480, 300, 50); //Position and resize the menu button
		
		scoreL.setBounds(0, 100, Main.width, 150); //Position and resize the score label
		scoreL.setFont(new Font("Arial", Font.PLAIN, 130)); //Set its font and size
		//scoreL.setBorder(BorderFactory.createLineBorder(Color.black)); //Set the borders
		
		highL.setBounds(0, 250, Main.width, 100); //Position and resize highscore label
		highL.setFont(new Font("Arial", Font.PLAIN, 60)); //Set its font and size
		
		return controlPanel;
		
	}
	
	
}
