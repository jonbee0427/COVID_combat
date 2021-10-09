package main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Controller { 
	
	private LinkedList<EntityA> ea = new LinkedList<EntityA>();
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();
	private LinkedList<EntityC> ec = new LinkedList<EntityC>();	// Mask & Heal
	
	EntityA enta;
	EntityB entb;
	EntityC entc;	// Mask & Heal
	
	Random r = new Random();
	private Game game;
	
	public Controller(Game game) {
		this.game = game;
	}
	
	public void createEnemy(int enemy_count) {
		for(int i=0; i<enemy_count; i++) {
			addEntity(new Enemy(r.nextInt(640), 10, this, game));
		}
	}
	
	// Mask
	public void createMask() {
		addEntity(new Mask(r.nextInt(640), 10, this, game));
	}
	
	// Heal
	public void createHeal() {
		addEntity(new Heal(r.nextInt(640), 10, this, game));
	}
	
	public void tick() {
		for(int i =0; i< ea.size(); i++) {
			enta = ea.get(i);
			
			enta.tick();
		}
		for(int i =0; i< eb.size(); i++) {
			entb = eb.get(i);
			
			entb.tick();
		}
		// Mask
		for(int i =0; i< ec.size(); i++) {
			entc = ec.get(i);
			
			entc.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i =0; i< ea.size(); i++) {
			enta = ea.get(i);
			
			enta.render(g);
		}
		for(int i =0; i< eb.size(); i++) {
			entb = eb.get(i);
			
			entb.render(g);
		}
		// Mask
		for(int i =0; i< ec.size(); i++) {
			entc = ec.get(i);
			
			entc.render(g);
		}
		
	}
	
	public void addEntity(EntityA block) {
		ea.add(block);
	}
	public void removeEntity(EntityA block) {
		ea.remove(block);
	}
	public void addEntity(EntityB block) {
		eb.add(block);
	}
	public void removeEntity(EntityB block) {
		eb.remove(block);
	}
	// Mask
	public void addEntity(EntityC block) {
		ec.add(block);
	}
	// Mask
	public void removeEntity(EntityC block) {
		ec.remove(block);
	}
	public LinkedList<EntityA> getEntityA() {
		return ea;
	}
	public LinkedList<EntityB> getEntityB() {
		return eb;
	}
	// Mask
	public LinkedList<EntityC> getEntityC() {
		return ec;
	}
	
}
