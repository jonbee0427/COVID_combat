package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Game.STATE;

public class MouseInput implements MouseListener {



	@Override
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();


		// Play Button
		if(Game.State == STATE.MENU) {
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 150 && my <= 200) {
					// Play Button Pressed
					Game.State = Game.STATE.GAME;
				}
			}
			
			//added
			// Ranking Button
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 250 && my <= 300) {
					// Ranking Button Pressed
					Game.State = Game.STATE.RANKING;
				}
			}
			
			// Quit Button
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 350 && my <= 400) {
					// Quit Button Pressed
					System.exit(1);
				}
			}
		
		}
		else if(Game.State == STATE.RANKING) {
			if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
				if (my >= 370 && my <= 420) {
					// Back Button Pressed
					Game.State = Game.STATE.MENU;
				}
			}
		}
		
		else if(Game.State == Game.STATE.END)
	      {
	         //Back Button
	         if (mx >= Game.WIDTH / 2 + 330 && mx <= Game.WIDTH / 2 + 430) {
	            if (my >= 30 && my <= 80) {
	               // Back Button Pressed
	               Game.State = Game.STATE.MENU;
	            }
	         }
	      }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
