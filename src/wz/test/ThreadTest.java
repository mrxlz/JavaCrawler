package wz.test;

public class ThreadTest implements Runnable {

	private String count;
	
	public ThreadTest() {
		count = "0";
	}
	
	@Override
	public synchronized void run() {
		count +="1";
		System.out.println(count);
	}
	
	public static void main(String[] args) {
		ThreadTest thread = new ThreadTest();
		Thread t1 = new Thread(thread);
		Thread t2 = new Thread(thread);
		t1.start();
		t2.start();
	}

}
