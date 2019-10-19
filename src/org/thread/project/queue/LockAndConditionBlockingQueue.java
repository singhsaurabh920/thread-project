package org.thread.project.queue;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class LockAndConditionBlockingQueue<T> extends CustomBlockingQueue<T> {

	private final Condition notFull;
	private final Condition notEmpty;
	private final ReentrantLock lock;

	{
		lock = new ReentrantLock(true);
		notFull = lock.newCondition();
		notEmpty = lock.newCondition();
	}

	public LockAndConditionBlockingQueue(int size, Queue<T> queue) {
		super(size, queue);
	}
	
	@Override
	public T take() throws InterruptedException {
		lock.lock();
		try {
			while (queue.isEmpty()) {
					notEmpty.await();
			}
			T t = queue.remove();
			notFull.signalAll();
			return t;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(T t) throws InterruptedException {
		lock.lock();
		try {
			while (queue.size() == size) {
				notFull.await();
			}
			queue.add(t);
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}

}
