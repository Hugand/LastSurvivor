//Menu Panel Class


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu{
	
	public int mbx, mby;
	public BufferedImage title;
	
	public Menu() {
		super();
		
		mbx = Main.width/2 + 100;
		mby = Main.height/2 + 100;
		
		try{
			title = ImageIO.read(new File("src/images/title.png")); //Read the title image
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public JPanel show() {
		
		JButton startButton = new JButton("Start"); //Start button
		JPanel controlPanel = new JPanel(); //This panel
		JLabel picLabel = new JLabel(new ImageIcon(title)); //Title label
		
		controlPanel.setLayout(null);
		
		
		startButton.addActionListener(new ActionListener() { //Start button listener
	        public void actionPerformed(ActionEvent e) {
	        	Main.cardlayout.show(Main.cardPanel, "Game"); //Go to Game Panel
	        	Main.cardPanel.setFocusable(true);
	   			Main.cardPanel.requestFocusInWindow();
	        }          
	    });
		
		controlPanel.add(startButton);
		controlPanel.add(picLabel);
		
		picLabel.setBounds(0, 0, Main.width, 300);
		startButton.setBounds(Main.width/2-150, 400, 300, 50);
		
		return controlPanel;
		
	}
	
}
