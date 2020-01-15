import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Game extends JComponent {
	public static int mouseX = -1;
	public static int mouseY = -1;
	public static final int TILE_SIZE = 72;
	public static int sec = 0;
	public static long extra = 0;
	public static boolean win = false;
	public static boolean lose = false;
	public static int mesX = 255;
	public static int mesY = -100;
	public static String message = "";
	public static boolean playing = true;
	
	public static int[][] mines = new int[10][8];
	public static int[][] numsSur = new int[10][8];
	public static boolean[][] clicked = new boolean[10][8];
	public static boolean[][] flagged = new boolean[10][8];
	public static Timer t;
	
	private int numSur = 0;
	private static boolean resetting = false;
	private int frameDelay = 1000;
	
	public Game() {
		//drawing the frame
		JFrame frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				if (Math.random() * 100 < 20) {
					mines[i][j] = 1;
					
				} else {
					mines[i][j] = 0;
					
				}
				
				clicked[i][j] = false;
				
			}
			
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				numSur = 0;
				
				for (int m = 0; m < 10; m++) {
					for (int n = 0; n < 8; n++) {
						if (!(m == i && n == j)) {
							if (isSurrounded(i, j, m, n) == true) {
								numSur++;
								
							}
						
						}
						
					}
					
				}
				numsSur[i][j] = numSur;
				
			}
			
		}
		
		Board board = new Board();
		board.setPreferredSize(new Dimension(720, 720));
		
		JPanel panel = new JPanel();
		panel.add(board);
		
		frame.add(panel);
		frame.pack();
		
		frame.addMouseMotionListener(new MouseMove());
		
		frame.addMouseListener(new MouseClick());
		
		frame.addKeyListener(new Keyboard());
		
		t = new Timer(frameDelay, new TimerListener());
		t.start();
		
	}
	
	//timer
	public class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sec += 1;
			repaint();
			
		}
		
		
	}
		
	//mouse movement
	public class MouseMove implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			System.out.println("Mouse moved to (" + mouseX + ", " + mouseY + ")");
			
			//paused or win
			if (playing == false || win == true) {
				if (e.getX() > 8 && e.getX() <= 728 && e.getY() > 173 && e.getY() <= 748) {
					mouseX = 0;
					mouseY = 0;
					
				}
				
			}
			
		}
		
		
	}
	
	//mouse clicks
	public class MouseClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (getTileX() != -1 && getTileY() != -1) {
				System.out.println("Mouse clicked tile [" + getTileX() + "," + getTileY() + "]");
				
			} else {
				//System.out.println("Invalid operation. Please click a tile.");
				
			}
			
			if (checkReset(e.getX(), e.getY())) {
				System.out.println("Game Reset");
				reset();
				
			}
			
			if(checkStatus(e.getX(), e.getY())) {
				if (playing == true) {
					System.out.println("Game Paused");
					pause();
					
				} else if (playing == false) {
					System.out.println("Game Resumed");
					resume();
					
				}
				
			}
			
			//paused or win
			if (playing == false || win == true) {
				System.out.println("Cannot play move.");
				
			}
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (getTileX() != -1 && getTileY() != -1) {
					System.out.println("Number of mines surrounding current tile: " + numsSur[getTileX()][getTileY()]);
					clicked[getTileX()][getTileY()] = true;
					
					if (numsSur[getTileX()][getTileY()] == 0 && clicked[getTileX()][getTileY()] == true && mines[getTileX()][getTileY()] == 0) {
						revealEmpty(getTileX(), getTileY());
						
					}
					
				}
				
				//display win/lose status
				if (resetting == false) {
					checkWin();
					
					//System.out.println("Win: " + win + " Lose: " + lose);
					
					if (win == true) {
						message = "YOU WIN!";
						System.out.println(message);
						
					}
					if (lose == true) {
						revealAll();
						message = "GAME OVER!";
						System.out.println(message);
						
					}
					
				}
				
			} else if (SwingUtilities.isRightMouseButton(e)) {
				if (getTileX() != -1 && getTileY() != -1) {
					if (flagged[getTileX()][getTileY()] == false) {
						flagged[getTileX()][getTileY()] = true;
						
					} else if (flagged[getTileX()][getTileY()] == true) {
						flagged[getTileX()][getTileY()] = false;
						
					}
					
				}
				
			}
			
		}
	
		@Override
		public void mouseReleased(MouseEvent e) {
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
	
	//keyboard clicks
	public class Keyboard implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
				System.out.println("Game Reset");
				reset();
				
			}
			if (e.getKeyChar() == KeyEvent.VK_SPACE) {
				if (playing == true) {
					System.out.println("Game Paused");
					pause();
				
				} else if (playing == false) {
					System.out.println("Game Resumed");
					resume();
					
				}
				
			}
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//returns x-coordinate of tile
	public int getTileX() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				if (mouseX >= i * TILE_SIZE + 10 && mouseX < i * TILE_SIZE + 82 && mouseY >= j * TILE_SIZE + 174 && mouseY < j * TILE_SIZE + TILE_SIZE + 174) {
					return i;
					
				}
				
			}
			
		}
		return -1;
		
	}
	
	//returns y-coordinate of tile
	public int getTileY() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				if (mouseX >= i * TILE_SIZE + 10 && mouseX < i * TILE_SIZE + 82 && mouseY >= j * TILE_SIZE + 174 && mouseY < j * TILE_SIZE + TILE_SIZE + 174) {
					return j;
					
				}
				
			}
			
		}
		return -1;
		
	}
	
	//checks surrounding mines
	public boolean isSurrounded(int x1, int y1, int x2, int y2) {
		if (x1 - x2 < 2 && x1 - x2 > -2 && y1 - y2 < 2 && y1 - y2 > -2 && mines[x2][y2] == 1) {
			return true;
			
		}
		return false;
		
	}
	
	public boolean checkReset(int x, int y) {
		if (x > 42 && x <= 192 && y > 70 && y <= 133) {
			return true;
			
		}
		
		return false;
		
	}
	
	//reset game
	public void reset() {
		t.restart();
		sec = 0;
		win = false;
		lose = false;
		resetting = true;
		mesY = -100;
		playing = true;
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				if (Math.random() * 100 < 20) {
					mines[i][j] = 1;
					
				} else {
					mines[i][j] = 0;
					
				}
				
				clicked[i][j] = false;
				flagged[i][j] = false;
				
			}
			
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				numSur = 0;
				
				for (int m = 0; m < 10; m++) {
					for (int n = 0; n < 8; n++) {
						if (!(m == i && n == j)) {
							if (isSurrounded(i, j, m, n) == true) {
								numSur++;
								
							}
						
						}
						
					}
					
				}
				numsSur[i][j] = numSur;
				
			}
			
		}
		
		resetting = false;
		
	}
	
	//check victory status
	public static void checkWin() {
		if (lose == false) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 8; j++) {
					if (clicked[i][j] == true && mines[i][j] == 1) {
						lose = true;
						t.stop();
						
					}
					
				}
				
			}
			
		}
		
		if (totalClicked() == 80 - totalMines() && win == false) {
			win = true;
			t.stop();
			
		}
		
	}
	
	//return num of mines
	public static int totalMines() {
		int total = 0;
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				if (mines[i][j] == 1) {
					total++;
					
				}
				
			}
			
		}
		
		return total;
		
	}
	
	//return num of clicked spaces
	public static int totalClicked() {
		int total = 0;
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				if (clicked[i][j] == true) {
					total++;
					
				}
				
			}
			
		}
		
		return total;
		
	}
	
	//reveals all tiles
	public static void revealAll() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 8; j++) {
				clicked[i][j] = true;
				
			}
			
		}
		
	}
	
	//check if pause/play clicked
	public boolean checkStatus(int x, int y) {
		if (x > 332 && x <= 397 && y > 70 && y <= 135) {
			return true;
			
		}
		
		return false;
		
	}
	
	//pause game
	public static void pause() {
		playing = false;
		t.stop();
		
	}
	
	//resume game
	public static void resume() {
		playing = true;
		t.start();
		
	}
	
	//reveals empty consecutive empty tiles and surrounding tiles
	public static void revealEmpty(int x, int y) {
		if (numsSur[x][y] == 0) {
			if (x != 0 && y != 0 && clicked[x - 1][y - 1] == false) {
				clicked[x - 1][y - 1] = true;
				revealEmpty(x - 1, y - 1);
				
			}
			if (x != 0 && clicked[x - 1][y] == false) {
				clicked[x - 1][y] = true;
				revealEmpty(x - 1, y);
				
			}
			if (y != 0 && clicked[x][y - 1] == false) {
				clicked[x][y - 1] = true;
				revealEmpty(x, y - 1);
			}
			if (x != 9 && y != 7 && clicked[x + 1][y + 1] == false) {
				clicked[x + 1][y + 1] = true;
				revealEmpty(x + 1, y + 1);

			}
			if (x != 9 && clicked[x + 1][y] == false) {
				clicked[x + 1][y] = true;
				revealEmpty(x + 1, y);

			}
			if (y != 7 && clicked[x][y + 1] == false) {
				clicked[x][y + 1] = true;
				revealEmpty(x, y + 1);

			}
			if (x != 0 && y != 7 && clicked[x - 1][y + 1] == false) {
				clicked[x - 1][y + 1] = true;
				revealEmpty(x - 1, y + 1);
				
			}
			if (x != 9 && y != 0 && clicked[x + 1][y - 1] == false) {
				clicked[x + 1][y - 1] = true;
				revealEmpty(x + 1, y - 1);
				
			}
			
		}
		
	}
	
}
