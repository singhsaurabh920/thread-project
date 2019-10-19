package org.thread.project.pc;

import java.util.Random;

import org.thread.project.queue.CustomBlockingQueue;

public class CustomBlockingQueuePC {
	private static String[] productNames = { "Hail Oil", "Soap", "Shampoo", "Comb", "Mirror", "Cycle", "Bike", "Car", "Jeans","Shirt", "T-Shirt" };
	private Random random = null;
	private CustomBlockingQueue<Product> customBlockingQueue;
	{
		random = new Random();
	}

	public CustomBlockingQueuePC(CustomBlockingQueue<Product> customBlockingQueue) {
		super();
		this.customBlockingQueue = customBlockingQueue;
	} 
	
	public void producer() {
		while (true) {
			try {
				customBlockingQueue.put(new Product(productNames[random.nextInt(productNames.length)],random.nextInt(100)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void consumer() {
		while (true) {
			Product product = null;
			try {
				product=customBlockingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(Thread.currentThread().getName()+":" + product);
			}
		}
	}

	
	

}
