package org.thread.project.queue;

import java.util.Queue;

public abstract class CustomBlockingQueue<T> {

	protected final int size; 
	protected final Queue<T> queue;

	public CustomBlockingQueue(int size,Queue<T> queue) {
		this.size=size;
		this.queue=queue;
	}
	
	public abstract T take() throws InterruptedException;
	
	public abstract void put(T t) throws InterruptedException;
	
	
	

}
