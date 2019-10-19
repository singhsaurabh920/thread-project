package org.thread.project;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterOperation {

	private AtomicInteger atomicInteger=new AtomicInteger();
	private int count=0;
	
	public CounterOperation() {
		// TODO Auto-generated constructor stub
	}

	public void writeCounter() {
		for (int i = 0; i <100; i++) {
			++count;			
		}
	}

	public void readCounter() {
		System.out.println(Thread.currentThread().getName()+" increment counter : "+count);			
	}
	
	public void writeAtomicCounter() {
		for (int i = 0; i <100; i++) {
			atomicInteger.incrementAndGet();			
		}
	}

	public void readAtomicCounter() {
		System.out.println(Thread.currentThread().getName()+" atomic counter : "+atomicInteger.get());			
	}

}
