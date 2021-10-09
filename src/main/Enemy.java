package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy implements EntityB {
		
	Random r = new Random();
	private Game game;
	private Controller c;
	private double x,y;
	private BufferedImage enemyImage;
	
	private int speed = r.nextInt(3)+1;
	
	
	public Enemy(double x, double y, Controller c, Game game){
		this.x = x;
		this.y = y;
		this.c = c;
		this.game = game;
		this.enemyImage = game.getEnemyImage();
	}
	
	public void tick() {
		y += speed;
		
		
		if(y>Game.HEIGHT * Game.SCALE) {
			speed = r.nextInt(3)+1;
			x= r.nextInt(640);
			y = -10;
		}
		
		
		for(int i = 0; i< game.ea.size(); i++)
		{
			EntityA tempEnt = game.ea.get(i);
			if(Physics.Collision(this, tempEnt))
			{
				c.removeEntity(tempEnt);
				c.removeEntity(this);
				game.setEnemy_killed(game.getEnemy_killed() + 1);
				game.setTotal_enemy_killed(game.getTotal_enemy_killed() + 1);	// Add
			}
		}

	}
	
	public void render(Graphics g) {
		g.drawImage(enemyImage,  (int)x, (int)y, null);
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
