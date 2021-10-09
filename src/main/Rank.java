package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Rank {
	
	private Rectangle backButton = new Rectangle(Game.WIDTH / 2 + 120, 380, 100, 50);
	private Rectangle rankDisplay = new Rectangle(10, 10, 620, 450);
	
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		Font font2 = new Font("Serif", Font.BOLD, 50);
		g.setFont(font2);
		g.setColor(Color.white);
		g.drawString("NAME", 75, 50);
		g.drawString("SCORE", 390, 50);

		g2d.draw(rankDisplay);
		
		ArrayList<WriteRank> list = readList();

		
		
		 Font font3 = new Font("Serif", Font.BOLD, 25);
	      g.setFont(font3);
	      g.setColor(Color.white);
	      g.drawString("BACK", backButton.x + 15, backButton.y + 35);
	      g2d.draw(backButton);
		
	      g.setFont(font2);
		int size=list.size();
		if(list.size() > 4)	size = 4;
		for(int i=0; i<size; i++) {g.drawString(list.get(i).getName() + "   -------   "+list.get(i).getScore(), 100, 100+80*i);}
	}
	
	private ArrayList<WriteRank> readList()
	{
		String fileName = "Ranking.txt";
		Scanner inputStream = null;

		

		
		ArrayList<WriteRank> write = new ArrayList<>();
		
		try
		{
			inputStream = new Scanner(new File(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		
		while(inputStream.hasNextLine())
		{
			String line = inputStream.nextLine();
			String[] sub = line.split("-");
			String name;
			int score;
			
			name = sub[0].trim();
			score =  Integer.parseInt(sub[sub.length-1].trim());
			write.add(new WriteRank(name, score));
			
			
		}

		Collections.sort(write, new Comparator<WriteRank>() {

			@Override
			public int compare(WriteRank o1, WriteRank o2) {
				// TODO Auto-generated method stub
				if(o1.getScore() < o2.getScore()) {
					return 1;
				} else if(o1.getScore() > o2.getScore()) {
					return -1;
				} else {
					return 0;
				}				
			}
			
		});
		
		inputStream.close();
		return write;
	}
	
}
