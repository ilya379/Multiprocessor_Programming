

/**
 * 
 * @author Shakhov-IN
 *
 */
public class HW2Synchronized {
	
	public static int N = 16;
	public static int M = 10000;
	
	public static State[] states;
	
	
	public static void init() {
		states = new State[N];
		for (int i=0; i<N; i++) {
			states[i] = new State(i+1);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		for (int i=0; i<N; i++) {
			
		}
		
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
		
	}
	
	@sun.misc.Contended
	public static class State {
		public boolean syncFlag = false;
		public final int name;
		public State(int name) {
			this.name = name;
		}
		
	}

}

