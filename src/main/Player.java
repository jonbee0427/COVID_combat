 package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player implements EntityA{
	
	
	private double velX = 0;
	private double velY = 0;
	private double x=0,y=0;
	private BufferedImage playerImage;
	private BufferedImage bubbleImage;
	private double check = 1;	// Mask
	private long start=0, time=0, second=0;	// Mask
	private static final long total=5; // Mask
	private Boolean mask_activated = false;	// Mask
	
	Game game;
	Controller controller;
	
	public Player(double x, double y, Game game, Controller controller) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.playerImage = game.getPlayerImage();
		this.bubbleImage = game.getBubbleImage();
		this.controller = controller;
	}
	
	
	
	public void tick() {
		x+=velX;
		y+=velY;
		
		if(x <= 0)
			x = 0;
		if(x >= 640 - 18)
			x = 640 - 18;
		if(y <= 0)
			y = 0;
		if(y >= 480 - 32)
			y = 480 - 32;
		
		// Mask
		if(mask_activated==true) {
			if(check==0){
				start = System.currentTimeMillis();
				check=1;
			}
			time = (System.currentTimeMillis() - start)/1000;
			second = (total-time)%60;
			if(second<=0) {
				mask_activated = false;
			}
		}
		
		for(int i=0; i < game.eb.size(); i++)
		{
			EntityB tempEnt = game.eb.get(i);
			
			if(Physics.Collision(this, tempEnt))
			{
				controller.removeEntity(tempEnt);
				// Mask
				if(mask_activated == false) {
					Game.HEALTH -= 40;
				}
				game.setEnemy_killed(game.getEnemy_killed() + 1);
			}
		}
	}
	
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	
	public void render(Graphics g) {
		// Mask
		if(mask_activated==true) {
			g.drawImage(bubbleImage,  (int)x-8, (int)y+3, null);
		}
		g.drawImage(playerImage,  (int)x, (int)y, null);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	// Mask
	public void setMask_activated(boolean mask_activated) {
		this.mask_activated = mask_activated;
		check = 0;
	}	
	
}