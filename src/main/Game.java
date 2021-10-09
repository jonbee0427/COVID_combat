package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;	// Mask
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "COVID-Combat";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage player = null;
	private BufferedImage enemy = null;
	private BufferedImage bullet = null;
	private BufferedImage background = null;
	private BufferedImage gameover = null;
	private BufferedImage heal = null;
	private BufferedImage mask = null;	// Mask
	private BufferedImage bubble = null;	// Mask
	
	private boolean is_shooting = false;
	
	private int enemy_count =5;
	private int enemy_killed = 0;
	private int total_enemy_killed = 0;	// Add
	private int mask_exist = 0;	// Mask
	private int heal_exist = 0;	// Heal
	
	Random r = new Random();	// Mask & Heal
	
	private Player p;
	private Controller c;
	private Menu menu;
	private Rank rank;
	private End end;
	private LogIn login=new LogIn(this);//add

	private long start=0, time=0; // add
	private int check = 0;//add
	private String ID = "";//ID
	
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	public LinkedList<EntityC> ec;	// Mask & Heal
	int total = 10; // add
	int index = 0;
	
	public static int HEALTH = 100 * 2;
	
	public static enum STATE 
	{
		MENU, GAME, RANKING, END
	};
	
	public static STATE State = STATE.MENU;
	
	
	
	public void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {	
			player = loader.loadImage("/pengsu.png");
			enemy = loader.loadImage("/cororo.png");
			bullet = loader.loadImage("/bullet2.png");
			background = loader.loadImage("/background.png");
			gameover = loader.loadImage("/gameover.jpg");
			heal = loader.loadImage("/heal.png");
			mask = loader.loadImage("/mask.png");	// Mask
			bubble = loader.loadImage("/bubble.png");	// Mask
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		c = new Controller(this);
		p = new Player(200,200, this, c);
		menu = new Menu();
		rank = new Rank();
		end = new End();
		
		
		ea = c.getEntityA();
		eb = c.getEntityB();
		ec = c.getEntityC();	// Mask & Heal
		
		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput());
		
		c.createEnemy(enemy_count);
	}
	
	
	
	private synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		login.render();//add

	}
	
	private synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		
		setStart(0);
		
		while(running) {
			// game loop
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				delta--;
			}
			render();
			
			
			if(State == STATE.GAME)
			{
				if(check==0){
					setStart(System.currentTimeMillis());
					check=1;
				}
				long time = (System.currentTimeMillis() - getStart())/1000;
				setTime(time);
			}

			
			if(System.currentTimeMillis() - timer > 1000) {timer += 1000;}
		}
		stop();
	}
	
	private void tick() {
		if(State == STATE.GAME)
		{
			
			p.tick();
			c.tick();
			
			// transferred to if(State == STATE.GAME)
			if(enemy_killed >= enemy_count)
			{
				enemy_count += 3;
				enemy_killed = 0;
				c.createEnemy(enemy_count);
			}
			
			// Mask
			if(mask_exist == 0)
			{
				if(r.nextInt(100*60)<=5) {
					c.createMask();
					mask_exist = 1;
				}
			}
			
			// Heal
			if(heal_exist == 0)
			{
				if(r.nextInt(100*60)<=5) {
					c.createHeal();
					heal_exist = 1;
				}
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.fillRect(0, 0, 800, 800);
		
		g.drawImage(background,  0,  0,  null);
		
		if(State == STATE.GAME)
		{
			p.render(g);
			c.render(g);
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(5, 5, 200, 50);
			
			g.setColor(Color.green);
			g.fillRect(5, 5, HEALTH, 50);
			
			g.setColor(Color.black);
			g.drawRect(5, 5, HEALTH, 50);
			
			Font font = new Font("Serif", Font.BOLD, 30);	// Add
			g.setFont(font);	// Add
			g.setColor(Color.white);	// Add
			g.drawString("SCORE: " + total_enemy_killed, Game.WIDTH * Game.SCALE - 190, 30);	// Add
			
						
			if(total_enemy_killed%10==0 && total_enemy_killed!=0) {
				total+=5;
				total_enemy_killed++;
			}
	
				   
			int minute=(int)((total-getTime())/60);
			int second=(int)((total-getTime())%60);
			
			
			if(total-getTime()<10) {
				g.setColor(Color.red);
			}
				
			
			g.drawString("TIME: " + minute+" : "+second, Game.WIDTH * Game.SCALE - 190, 65);
			
			if(HEALTH <= 0 || getTime() > total) {
				if((second <= 0 && second > -6) || HEALTH <= 0)
	            {
	               g.drawImage(gameover,0,0,getWidth(),getHeight(),this);
	               
	            }

				Font font2 = new Font("Serif", Font.BOLD, 25);	//added
				g.setFont(font2);	//added
				g.setColor(Color.white);
				g.drawString("USER: "+getID(), Game.WIDTH * Game.SCALE-395, Game.HEIGHT*Game.SCALE-100);
				g.drawString("SCORE: "+total_enemy_killed, Game.WIDTH * Game.SCALE-395, Game.HEIGHT*Game.SCALE-50);

				Font time = new Font("Serif", Font.BOLD, total);
	            g.setFont(time);
	            g.setColor(Color.white);
	            g.drawString("EXIT IN " + (6+second) + "...", 450, 50);
				
				
	            
	            ////////////////////////////////////////
	            if(HEALTH <= 0 || getTime() > total)
	            {
	                  State = STATE.END;
	                  
	                  String s = Integer.toString(total_enemy_killed);
	                     
	             		BufferedWriter bw;
	             		try {
	             			bw = new BufferedWriter(new FileWriter("Ranking.txt", true));
	       				bw.write(ID + " ------------ " + s+"\r\n");
	       				bw.flush();
	       				bw.close();
	             		} catch (IOException e1) {
	             			// TODO Auto-generated catch block
	             			e1.printStackTrace();
	             		}
	            }
			}
		}
		
		else if(State == STATE.MENU)
        {
           menu.render(g);
           total = 30;
           setTime(0);
           setStart(0);
           check = 0;
           setEnemy_count(5);
           setTotal_enemy_killed(0);
           HEALTH = 200;
        }
        
        //added
        else if(State == STATE.END)
        {
           g.drawImage(gameover,0,0,getWidth(),getHeight(),this);
           end.render(g);
           
           Font font2 = new Font("Serif", Font.BOLD, 25);   //added
           g.setFont(font2);   //added
           g.setColor(Color.white);
           
           //x-axis and the size modified
           g.drawString("USER: " + getID(), Game.WIDTH * Game.SCALE-395, Game.HEIGHT*Game.SCALE-100);
           g.drawString("SCORE: " + getTotal_enemy_killed(), Game.WIDTH * Game.SCALE-395, Game.HEIGHT*Game.SCALE-50);
           
        }
		
		
		//added
		else if(State == STATE.RANKING)
		{
			rank.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(State == STATE.GAME)
		{
			if(key == KeyEvent.VK_RIGHT) {
				p.setVelX(5);
			} else if(key == KeyEvent.VK_LEFT) {
				p.setVelX(-5);
			} else if(key == KeyEvent.VK_DOWN) {
				p.setVelY(5);
			} else if(key == KeyEvent.VK_UP) {
				p.setVelY(-5);
			}
			if(key == KeyEvent.VK_SPACE && !is_shooting) {
				is_shooting = true;
				c.addEntity(new Bullet(p.getX(), p.getY(), this));
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT) {
			p.setVelX(0);
		} else if(key == KeyEvent.VK_LEFT) {
			p.setVelX(0);
		} else if(key == KeyEvent.VK_DOWN) {
			p.setVelY(0);
		} else if(key == KeyEvent.VK_UP) {
			p.setVelY(0);
		} else if(key == KeyEvent.VK_SPACE) {
			is_shooting = false;
		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	public BufferedImage getPlayerImage() {
		return player;
	}
	public BufferedImage getEnemyImage() {
		return enemy;
	}
	public BufferedImage getBulletImage() {
		return bullet;
	}
	public BufferedImage getHealImage() {
		return heal;
	}
	public int getEnemy_count() {
		return enemy_count;
	}
	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}
	public int getEnemy_killed() {	// Add
		return enemy_killed;
	}
	public void setEnemy_killed(int enemy_killed) {	// Add
		this.enemy_killed = enemy_killed;
	}
	
	public int getTotal_enemy_killed() {
		return total_enemy_killed;
	}
	public void setTotal_enemy_killed(int total_enemy_killed) {
		this.total_enemy_killed = total_enemy_killed;
	}
	
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	// Mask
	public BufferedImage getMaskImage() {
		return mask;
	}
	public BufferedImage getBubbleImage() {
		return bubble;
	}
	public int getMask_exist() {
		return mask_exist;
	}
	public void setMask_exist(int mask_exist) {
		this.mask_exist = mask_exist;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	// Heal
	public int getHeal_exist() {
		return heal_exist;
	}
	public void setHeal_exist(int heal_exist) {
		this.heal_exist = heal_exist;
	}
}
