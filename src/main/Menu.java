package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	private Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
	private Rectangle rankButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);  //modified "helpButton" -> "rankButton"
	private Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
	
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font("Serif", Font.BOLD, 50);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("COVID-COMBAT", 140, 100);
		
		Font font1 = new Font("Serif", Font.BOLD, 30);
		g.setFont(font1);
		g.drawString("Play", playButton.x + 20, playButton.y + 30);
		g2d.draw(playButton);
		g.drawString("Quit", playButton.x + 20, playButton.y + 230);
		g2d.draw(quitButton);
		
		
		Font font2 = new Font("Serif", Font.BOLD, 20);
		g.setFont(font2);
		g.drawString("Ranking", playButton.x + 13, playButton.y + 130);
		g2d.draw(rankButton);
	}
}
