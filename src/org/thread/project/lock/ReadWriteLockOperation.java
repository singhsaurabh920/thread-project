package org.thread.project.lock;

import java.util.Date;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockOperation {
	ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private int availableSheets;

	{
		availableSheets = 200;
	}

	public void read() {
		readWriteLock.readLock().lock();
		System.out.println(new Date() + " "+Thread.currentThread().getName() + " reading available sheets : " + availableSheets);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readWriteLock.readLock().unlock();
	}

	public void write(int i) {
		readWriteLock.writeLock().lock();
		availableSheets=availableSheets-i;
		System.out.println(new Date() +" "+Thread.currentThread().getName() + " writing available sheets : ["+i+ "] " + availableSheets);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readWriteLock.writeLock().unlock();
	}

}
