
/**
 * 
 * @author Shakhov-IN
 *
 */
public class HW1Synchronized {
	public static final String PING = "PING %d\n";
	public static final String PONG = "PONG %d\n";
	public static boolean syncFlag = false;
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread ping = new Thread(new Runnable() {
			private int c = 0;
			
			@Override
			public void run() {
				while (true) {
					if (syncFlag) {
						// здесь можно совсем убрать блок synchronized и ничего не поламается
						// т.к. методы System.out уже синхронизированы. Так зачем использовать еще одну синхронизацию?
						synchronized (PING) {	
							// для 2-х потоков нет нужды заново проверять флаг!!!
							// т.к. если он стал true, то флаг никак не мог изменить свое состояние (false ставится в этом потоке) 
							System.out.format(PING, c++);	
							// не обязательно делать в критической секции, т.к. 2 потока и ordering делают свое дело
							syncFlag = false;
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
						// здесь можно совсем убрать блок synchronized и ничего не поламается
						// т.к. методы System.out уже синхронизированы. Так зачем использовать еще одну синхронизацию?
						synchronized (PING) {	// МАГИЯ! методы System.out уже синхронизированы, так зачем использовать еще одну синхронизацию?
							// для 2-х потоков нет нужды заново проверять флаг!!! 
							// т.к. если он стал false, то флаг никак не мог изменить свое состояние (true ставится в этом потоке)							
							System.out.format(PONG, c++);	
							// не обязательно делать в критической секции, т.к. 2 потока и ordering делают свое дело 
							syncFlag = true;
						}
						
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
		
		/* вариант 
			synchronized (PING) {
				if (syncFlag) {
					System.out.println(String.format(PING, c++));
					syncFlag = false;
				}
			}	
		
		работает на ~30% медленнее, т.к. происходи захват монитора для каждой проверки, даже когда в крит. секцию должен попасть другой поток.
		
		А вообще задача не стояла, чтобы работало быстро.
		
		 */
	}

}

