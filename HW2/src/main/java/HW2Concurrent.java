import java.util.concurrent.locks.ReentrantLock;


/**
 * 
 * @author Shakhov-IN
 *
 */
public class HW2Concurrent {
	public static final String PING = "PING %d\n";
	public static final String PONG = "PONG %d\n";
	public static ReentrantLock syncLock = new ReentrantLock();
	public static boolean syncFlag = false;
	
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread ping = new Thread(new Runnable() {
			private int c = 0;
			
			@Override
			public void run() {
				while (true) {
					if (syncFlag) {
						syncLock.lock();
						try {
							System.out.format(PING, c++);
						} finally {
							syncFlag = false;
							syncLock.unlock();
						}						
					}
				}				
			}
		});
		
		Thread pong = new Thread(new Runnable() {
			private int c = 0;
			
			@Override
			public void run() {
				while (true) {
					if (!syncFlag) {
						syncLock.lock();
						try {
							System.out.format(PONG, c++);
						} finally {
							syncFlag = true;
							syncLock.unlock();
						}
					}
				}				
			}
		});
		
		ping.start();
		pong.start();
		
		// я уже все высказал насчет этого "дурного" задания: нет смысла в синхронизации вывода в PrintStream в 2-х потоках, т.к. он уже синхронизован
		// поэтому нет заметной разницы чем его синхронизировать внешне. 
		
		// используется для сравнения различных вариантов
		// Thread.sleep(10000);
		// ping.stop();
		// pong.stop();
		
	}

}

