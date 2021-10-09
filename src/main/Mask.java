package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Mask implements EntityC {
	
	Random r = new Random();
	private Game game;
	private Controller c;
	private double x,y;
	private BufferedImage maskImage;
	
	private int speed = r.nextInt(3)+1;
	
	
	public Mask(double x, double y, Controller c, Game game){
		this.x = x;
		this.y = y;
		this.c = c;
		this.game = game;
		this.maskImage = game.getMaskImage();
	}
	
	public void tick() {
		y += speed;
		
		if(y>Game.HEIGHT * Game.SCALE) {
			c.removeEntity(this);
			game.setMask_exist(0);
		}
		
		if(Physics.Collision(this, game.getP()))
		{
			c.removeEntity(this);
			game.setMask_exist(0);
			game.getP().setMask_activated(true);
		}

	}
	
	public void render(Graphics g) {
		g.drawImage(maskImage,  (int)x, (int)y, null);
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
