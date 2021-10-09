package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bullet implements EntityA{
	
	private double x,y;
	private BufferedImage bulletImage;
	
	public Bullet(double x, double y, Game game){
		this.x = x;
		this.y = y;
		this.bulletImage = game.getBulletImage();
	}
	
	public void tick() {
		y -= 10;		
	} 
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public void render(Graphics g) {
		g.drawImage(bulletImage,  (int)x, (int)y, null);
	}
	
	public double getY() {
		return y;
	}
	public double getX() {
		return x;
	}
}
