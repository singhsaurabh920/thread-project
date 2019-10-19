package org.thread.project;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class CyclicBarrierOperation {

	public void startOperation(CyclicBarrier cyclicBarrier,CountDownLatch countDownLatch) {
		System.out.println(Thread.currentThread().getName()+" Phase One Completed");
		try {
			cyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" Phase two Completed");
		try {
			cyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" Phase three Completed");
		try {
			cyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		countDownLatch.countDown();
	}
	
	public void startOperation(Phaser cyclicPhaser,Phaser counterPhaser) {
		System.out.println(Thread.currentThread().getName()+" "+cyclicPhaser.getPhase());
		cyclicPhaser.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getName()+" "+cyclicPhaser.getPhase());
		cyclicPhaser.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getName()+" "+cyclicPhaser.getPhase());
		cyclicPhaser.arriveAndAwaitAdvance();
		counterPhaser.arrive();
	}

}
