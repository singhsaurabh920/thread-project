package org.thread.project;

import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class AdderOperation {
	
	private Random random;
	private LongAdder longAdder;
	
	{
		random = new Random();
		longAdder = new LongAdder();
	}
	
	public AdderOperation() {
		
	}
	
	public void write() {
		for (int i = 0; i <100; i++) {
			longAdder.add(random.nextInt(100));
		}
	}

	public void read() {
		System.out.println(Thread.currentThread().getName()+" sum value : "+longAdder.sum());					
	}

}
