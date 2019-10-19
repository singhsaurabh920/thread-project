package org.thread.project.queue;

import java.util.Queue;

public class WaitAndNotifyBlockingQueue<T> extends CustomBlockingQueue<T> {

	public WaitAndNotifyBlockingQueue(int size, Queue<T> queue) {
		super(size, queue);
	}

	@Override
	public synchronized T take() throws InterruptedException {
		while (queue.isEmpty()) {
			this.wait();
		}
		T t = queue.remove();
		this.notifyAll();
		return t;
	}

	@Override
	public synchronized void put(T t) throws InterruptedException {
		while (queue.size() == size) {
			this.wait();
		}
		queue.add(t);
		this.notifyAll();
 
	}

}
