package org.thread.project.lock;

import java.util.Date;

public class SynchronizedLockOperation {

	private int availableSheets;

	{
		availableSheets = 200;
	}

	public void read() {
		System.out.println(new Date() + " "+Thread.currentThread().getName() + " reading available sheets : " + availableSheets);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void write(int i) {
		availableSheets=availableSheets-i;
		System.out.println(new Date() +" "+Thread.currentThread().getName() + " writing available sheets : ["+i+ "] " + availableSheets);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
