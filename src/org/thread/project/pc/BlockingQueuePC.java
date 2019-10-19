package org.thread.project.pc;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class BlockingQueuePC {
	private static String[] productNames = { "Hail Oil", "Soap", "Shampoo", "Comb", "Mirror", "Cycle", "Bike", "Car", "Jeans","Shirt", "T-Shirt" };
	private Random random = null;
	private BlockingQueue<Product> blockingQueue =null;
	{
		random = new Random();
	}

	public BlockingQueuePC(BlockingQueue<Product> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}

	public void producer() {
		while (true) {
			try {
				blockingQueue.put(new Product(productNames[random.nextInt(productNames.length)],random.nextInt(100)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void consumer() {
		while (true) {
			Product product = null;
			try {
				product = blockingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(Thread.currentThread().getName()+":" + product);
			}
		}
	}

}
