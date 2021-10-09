package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class End {
	
	private Rectangle backButton = new Rectangle(Game.WIDTH / 2 + 330, 25, 100, 50);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font("Serif", Font.BOLD, 25);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("BACK", backButton.x + 15, backButton.y + 35);
		g2d.draw(backButton);
	}
}
