//-------------------------------------------------------------
//
// Name: Last Survivor
// Author: Hugo Gomes
// 
// Description: As the last survivor, kill the zombie demons
// and the BIG Demons and survive for as long as possible.
// This game was made for the #100DaysOfCode Challenge
//
//-------------------------------------------------------------

//-------------------------------------------------------------
// Main Class: Where everything comes together and where all
// the panels are managed
//-------------------------------------------------------------


import java.awt.CardLayout;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JComponent{
	
	private static final long serialVersionUID = 1L;
	
	Timer t = new Timer(5, (ActionListener) this);
	
	static JFrame window = new JFrame(); //Window Frame
	
	static JPanel cardPanel = new JPanel();
	
	static CardLayout cardlayout = new CardLayout();
	
	static Menu menu = new Menu(); //Create the Menu
	static Game game = new Game(); //Create the Game
	static GameOver gameover = new GameOver(); //Create the Game Over
	
	public static int width = 900, height = 800;
	
	public Main() {
		
		t.start();
		
		cardPanel.setFocusable(true);
		cardPanel.requestFocusInWindow();
		
	}
	
	public static void main(String[] a) {
		
		cardPanel.setLayout(cardlayout);
		
		
		cardPanel.add(menu.show(), "Menu"); //Add menu panel to cardlayout
		cardPanel.add(game.getPanel(), "Game"); //Add game panel to cardlayout
		cardPanel.add(gameover.show(), "Gameover"); //Add gameover panel to cardlayout
		
		
		window = new JFrame();	//Create the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(500,50, width, height); //Set window position and size
		window.getContentPane().add(cardPanel); //Add the cardPanel to the window
		window.setVisible(true);
		window.setResizable(false);
		
	}
	
}
