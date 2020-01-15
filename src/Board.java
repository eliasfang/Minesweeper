import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;

import javax.swing.JComponent;

public class Board extends JComponent {

	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		
		//menu box
		g.setColor(new Color(0, 175, 0));
		g.fillRect(0, 0, 720, 144);
		
		//reset button
		g.setColor(Color.WHITE);
		g.fillRoundRect(35, 39, 150, 65, 10, 10);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Tahoma", Font.BOLD, 24));
		g.drawString("NEW GAME", 40, 81);
		
		//play/pause button
		g.setColor(Color.WHITE);
		g.fillRoundRect(325, 40, 65, 65, 10, 10);
		if (Game.playing == true) {
			g.setColor(Color.BLACK);
			g.fillRect(343, 53, 10, 40);
			g.fillRect(361, 53, 10, 40);
			
		}
		if (Game.playing == false) {
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(5));
			g.fillPolygon(new int[] {340, 340, 379}, new int[] {51, 95, 73}, 3);
			
		}
		
		//time counter
		g.setColor(Color.BLACK);
		g.fillRect(555, 35, 125, 75);
		//System.out.println(Game.sec);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Dialog", Font.BOLD, 76));
		if (Game.sec > 999) {
			Game.sec = 999;
		}
		if (Game.win == true) {
			g.setColor(Color.GREEN);
			
		}
		if (Game.lose == true) {
			g.setColor(Color.RED);
			
		}
		if (Game.sec < 10) {
			g.drawString("00" + Game.sec, 555, 100);
			
		} else if (Game.sec < 100) {
			g.drawString("0" + Game.sec, 555, 100);
			
		} else {
			g.drawString("" + Game.sec, 555, 100);
			
		}
		
		//board
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {		
				
				//light squares
				if (i % 2 == 0 && j % 2 == 0) {
					g.setColor(new Color(0, 240, 0));
					
					//shows clicked tiles
					if (Game.clicked[i][j] == true) {
						g.setColor(new Color(255,222,173));
						
						//reveal mine
						if (Game.mines[i][j] == 1) {
							g.setColor(Color.RED);
							
						}
						
					}
					
					//highlights tile hovered
					if (Game.mouseX >= i * Game.TILE_SIZE + 10 && Game.mouseX < i * Game.TILE_SIZE + 82 && Game.mouseY >= j * Game.TILE_SIZE + 174 && Game.mouseY < j * Game.TILE_SIZE + Game.TILE_SIZE + 174 && Game.clicked[i][j] == false) {
						g.setColor(new Color(152, 251, 152));
						
					}
					
					//draw tile
					g.fillRect(i * Game.TILE_SIZE, 144 + j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
					
					//flag
					if (Game.flagged[i][j] == true && Game.clicked[i][j] == false) {
						g.setColor(Color.RED);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 17, 15, 17);
						g.setColor(Color.BLACK);
						g.setStroke(new BasicStroke(3));
						g.drawLine(i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 1 + 17, i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 25 + 17);
						g.fillRect(i * Game.TILE_SIZE + 7 + 21, 144 + j * Game.TILE_SIZE + 25 + 17, 17, 6);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 30 + 17, 30, 6);
						
					}
					
					//repaint canvas
					repaint();
					
				}
				
				if (i % 2 != 0 && j % 2 != 0) {
					g.setColor(new Color(0, 240, 0));
					
					//shows clicked tiles
					if (Game.clicked[i][j] == true) {
						g.setColor(new Color(255,222,173));
						
						//reveal mine
						if (Game.mines[i][j] == 1) {
							g.setColor(Color.RED);
							
						}
						
					}
					
					//hovering tile
					if (Game.mouseX >= i * Game.TILE_SIZE + 10 && Game.mouseX < i * Game.TILE_SIZE + 82 && Game.mouseY >= j * Game.TILE_SIZE + 174 && Game.mouseY < j * Game.TILE_SIZE + Game.TILE_SIZE + 174 && Game.clicked[i][j] == false) {
						g.setColor(new Color(152, 251, 152));
						
					}
					
					//draw tile
					g.fillRect(i * Game.TILE_SIZE, 144 + j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
					
					//flag
					if (Game.flagged[i][j] == true && Game.clicked[i][j] == false) {
						g.setColor(Color.RED);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 17, 15, 17);
						g.setColor(Color.BLACK);
						g.setStroke(new BasicStroke(3));
						g.drawLine(i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 1 + 17, i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 25 + 17);
						g.fillRect(i * Game.TILE_SIZE + 7 + 21, 144 + j * Game.TILE_SIZE + 25 + 17, 17, 6);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 30 + 17, 30, 6);
						
					}
					
					//repaint canvas
					repaint();
					
				}
				
				//dark squares
				if (i % 2 == 0 && j %2 != 0) {
					g.setColor(new Color(0, 230, 0));
					
					//shows clicked tiles
					if (Game.clicked[i][j] == true) {
						g.setColor(new Color(222,184,135));
						
						//reveal mine
						if (Game.mines[i][j] == 1) {
							g.setColor(Color.RED);
							
						}
						
					}
					
					//hovering tile
					if (Game.mouseX >= i * Game.TILE_SIZE + 10 && Game.mouseX < i * Game.TILE_SIZE + 82 && Game.mouseY >= j * Game.TILE_SIZE + 174 && Game.mouseY < j * Game.TILE_SIZE + Game.TILE_SIZE + 174 && Game.clicked[i][j] == false) {
						g.setColor(new Color(152, 251, 152));
						
					}
					
					//draw tile
					g.fillRect(i * Game.TILE_SIZE, 144 + j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
					
					//flag
					if (Game.flagged[i][j] == true && Game.clicked[i][j] == false) {
						g.setColor(Color.RED);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 17, 15, 17);
						g.setColor(Color.BLACK);
						g.setStroke(new BasicStroke(3));
						g.drawLine(i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 1 + 17, i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 25 + 17);
						g.fillRect(i * Game.TILE_SIZE + 7 + 21, 144 + j * Game.TILE_SIZE + 25 + 17, 17, 6);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 30 + 17, 30, 6);
						
					}
					
					//repaint canvas
					repaint();
					
				}
				
				if (i % 2 != 0 && j % 2 == 0) {
					g.setColor(new Color(0, 230, 0));
					
					//shows clicked tiles
					if (Game.clicked[i][j] == true) {
						g.setColor(new Color(222,184,135));
						
						//reveal mine
						if (Game.mines[i][j] == 1) {
							g.setColor(Color.RED);
							
						}
						
					}
					
					//hovering tile
					if (Game.mouseX >= i * Game.TILE_SIZE + 10 && Game.mouseX < i * Game.TILE_SIZE + 82 && Game.mouseY >= j * Game.TILE_SIZE + 174 && Game.mouseY < j * Game.TILE_SIZE + Game.TILE_SIZE + 174 && Game.clicked[i][j] == false) {
						g.setColor(new Color(152, 251, 152));
						
					}
					
					//draw tile
					g.fillRect(i * Game.TILE_SIZE, 144 + j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE);
					
					//flag
					if (Game.flagged[i][j] == true && Game.clicked[i][j] == false) {
						g.setColor(Color.RED);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 17, 15, 17);
						g.setColor(Color.BLACK);
						g.setStroke(new BasicStroke(3));
						g.drawLine(i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 1 + 17, i * Game.TILE_SIZE + 15 + 21, 144 + j * Game.TILE_SIZE + 25 + 17);
						g.fillRect(i * Game.TILE_SIZE + 7 + 21, 144 + j * Game.TILE_SIZE + 25 + 17, 17, 6);
						g.fillRect(i * Game.TILE_SIZE + 21, 144 + j * Game.TILE_SIZE + 30 + 17, 30, 6);
						
					}
					
					//repaint canvas
					repaint();
					
				}
				
				//display mines surrounding
				if (Game.clicked[i][j] == true && Game.numsSur[i][j] != 0 && Game.mines[i][j] == 0) {
					g.setFont(new Font("Tahoma", Font.BOLD, 40));
					if (Game.numsSur[i][j] == 1) {
						g.setColor(Color.BLUE);
						
					} else if (Game.numsSur[i][j] == 2) {
						g.setColor(new Color(0, 200, 0));
						
					} else if (Game.numsSur[i][j] == 3) {
						g.setColor(Color.RED);
						
					} else if (Game.numsSur[i][j] == 4) {
						g.setColor(Color.MAGENTA);
						
					} else {
						g.setColor(Color.BLACK);
						
					}
					g.drawString(Integer.toString(Game.numsSur[i][j]), i * Game.TILE_SIZE + 23, j * Game.TILE_SIZE + 195);
					repaint();
					
				}
				
				//draw mine
				if (Game.mines[i][j] == 1 && Game.clicked[i][j] == true) {
					g.setColor(Color.BLACK);
					g.fillRect(i * Game.TILE_SIZE + 25, j * Game.TILE_SIZE + 161, 20, 40);
					g.fillRect(i * Game.TILE_SIZE + 15, j * Game.TILE_SIZE + 171, 40, 20);
					g.fillRect(i * Game.TILE_SIZE + 20, j * Game.TILE_SIZE + 166, 30, 30);
					g.setStroke(new BasicStroke(5));
					g.drawLine(i * Game.TILE_SIZE + 34, j * Game.TILE_SIZE + 157, i * Game.TILE_SIZE + 34, j * Game.TILE_SIZE + 205);
					g.drawLine(i * Game.TILE_SIZE + 10, j * Game.TILE_SIZE + 180, i * Game.TILE_SIZE + 59, j * Game.TILE_SIZE + 180);
					g.drawLine(i * Game.TILE_SIZE + 17, j * Game.TILE_SIZE + 164, i * Game.TILE_SIZE + 53, j * Game.TILE_SIZE + 199);
					g.drawLine(i * Game.TILE_SIZE + 17, j * Game.TILE_SIZE + 199, i * Game.TILE_SIZE + 53, j * Game.TILE_SIZE + 164);
					
				}
				
				//TEST color mines
//				if (Game.mines[i][j] == 1) {
//					g.setColor(Color.YELLOW);
//					g.fillRect(i * Game.TILE_SIZE,  j * Game.TILE_SIZE + 144, Game.TILE_SIZE, Game.TILE_SIZE);
//					
//				}
				
			}
			
		}
		
		//win/lose message
		if (Game.win == true || Game.lose == true) {
			g.setColor(Color.WHITE);
			Game.mesY = -50 + (int) (new Date().getTime() - (Game.sec * 1000));
			if (Game.mesY >= 30) {
				Game.mesY = 30;
				
			}
			g.setFont(new Font("Tahoma", Font.BOLD, 36));
			g.drawString(Game.message, Game.mesX, Game.mesY);
			
		}
		
	}
	
}
