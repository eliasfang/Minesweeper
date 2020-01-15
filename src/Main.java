
public class Main implements Runnable {
	Game game = new Game();

	public static void main(String[] args) {
		new Thread(new Main()).start();
		
	}
	
	@Override
	public void run() {
		
	}

}
