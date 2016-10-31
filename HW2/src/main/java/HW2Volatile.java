
/**
 * 
 * @author Shakhov-IN
 *
 */
public class HW2Volatile {
	public static final String PING = "PING %d\n";
	public static final String PONG = "PONG %d\n";
	public static volatile boolean syncFlag = false;
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread ping = new Thread(new Runnable() {
			private int c = 0;
			
			@Override
			public void run() {
				while (true) {
					// надеюсь применение volatile имелось в таком ключе. 
					// Как я показал в задаче HW1Synchronized поле syncFlag вполне может быть обычным boolean
					if (syncFlag) {   
						System.out.format(PING, c++);
						syncFlag = false;
					}
				}				
			}
		});
		
		Thread pong = new Thread(new Runnable() {
			private int c = 0;
			
			@Override
			public void run() {
				while (true) {
					// надеюсь применение volatile имелось в таком ключе. 
					// Как я показал в задаче HW1Synchronized поле syncFlag вполне может быть обычным boolean
					if (!syncFlag) {
						System.out.format(PONG, c++);
						syncFlag = true;
					}
				}				
			}
		});
		
		ping.start();
		pong.start();
		
		// используется для сравнения различных вариантов
		// Thread.sleep(10000);
		// ping.stop();
		// pong.stop();
	}

}

