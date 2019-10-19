package org.thread.project.service.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CachedThreadPool implements ThreadPool{

	private  ExecutorService  executorService= Executors.newCachedThreadPool();
	@Override
	public void execute() {
		IntStream.range(1,11).forEach((i)->{
			executorService.submit(()->{
				IntStream.range(1,6).forEach((j)->{
					System.out.println("TASK"+"["+i+"]"+Thread.currentThread().getName()+" : "+j);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
				});
			});
		});
	}
	
	

}
