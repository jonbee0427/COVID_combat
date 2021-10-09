package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Heal implements EntityC {
		
	Random r = new Random();
	private Game game;
	private Controller c;
	private double x,y;
	private BufferedImage healImage;
	
	private int speed = r.nextInt(3)+1;
	
	
	public Heal(double x, double y, Controller c, Game game){
		this.x = x;
		this.y = y;
		this.c = c;
		this.game = game;
		this.healImage = game.getHealImage();
	}
	
	public void tick() {
		y += speed;
		
		if(y>Game.HEIGHT * Game.SCALE) {
			c.removeEntity(this);
			game.setHeal_exist(0);
		}
		
		if(Physics.Collision(this, game.getP()))
		{
			c.removeEntity(this);
			game.setHeal_exist(0);
			Game.HEALTH += 50;
			if(Game.HEALTH > 200) {
				Game.HEALTH = 200;
			}
		}

	}
	
	public void render(Graphics g) {
		g.drawImage(healImage,  (int)x, (int)y, null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
}
