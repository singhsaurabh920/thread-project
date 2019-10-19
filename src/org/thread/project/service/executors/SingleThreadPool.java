package org.thread.project.service.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SingleThreadPool implements ThreadPool  {
	
	private  ExecutorService  executorService = null;
	
	{
		executorService = Executors.newSingleThreadExecutor();
	}


	@Override
	public void execute() {
		IntStream.range(1, 11).forEach((i)->{
			executorService.submit(()->{
				IntStream.range(1, 6).forEach((j)->{
					System.out.println("TASK"+"["+i+"]"+Thread.currentThread().getName()+" : "+j);
				});
			});
		});
	}

}
