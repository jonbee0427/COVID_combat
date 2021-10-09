package main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogIn extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String ID=null;
	private Game game;
	
	LogIn(Game game) {
		this.game = game;
	}
	
	public void render() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("ID: ");
		
		final JTextField txtID = new JTextField(12);
		JButton logBtn = new JButton("Log In");
		
		
		panel.add(label);
		panel.add(txtID);
		panel.add(logBtn);
		
		logBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							
				ID = txtID.getText();
				game.setID(ID); 	////////add
				System.out.println(ID);
				dispose();
		
			}
		});
		
		add(panel);
		
		setVisible(true);
		setSize(Game.WIDTH, Game.HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	


}
