package org.thread.project.service.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool implements ThreadPool{
	
	private  ScheduledExecutorService  executorService= Executors.newScheduledThreadPool(8);
	private  int count=0;

	@Override
	public void execute() {
		executorService.scheduleWithFixedDelay(()->{
			if(count==5) {
				executorService.shutdown();
			}		
			System.out.println(Thread.currentThread().getName()+" is shutdoun "+executorService.isShutdown());
			System.out.println(Thread.currentThread().getName()+" is terminated "+executorService.isTerminated());
			count++;
		}, 5, 5,TimeUnit.SECONDS );
		
	}

}
