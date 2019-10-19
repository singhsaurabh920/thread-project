package org.thread.project.service.executors;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForkJoinThreadPool implements ThreadPool {
	private final static ForkJoinPool pool = new ForkJoinPool();

	@Override
	public void execute() {
		//long mergedResult = pool.invoke(new ForkJoinPoolTask(64L));
		//System.out.println("Result = " + mergedResult);
		//List<String> files = pool.invoke(new ForkJoinPoolTask1("/home/insight/Pictures"));
		//files.forEach(System.out::println);
		ForkJoinPoolTask1 task1=new ForkJoinPoolTask1("/home/insight/Pictures/Sau1");
		ForkJoinPoolTask1 task2=new ForkJoinPoolTask1("/home/insight/Pictures/Sau2");
		pool.execute(task1);
		pool.execute(task2);
		pool.shutdown();
		while (!pool.isShutdown());
		List<String> result;
		result=task1.join();
		System.out.println("Sau1 Dir.................");
		result.forEach(System.out::println);
		result=task2.join();
		System.out.println("Sau2 Dir.................");
		result.forEach(System.out::println);
	}

	private class ForkJoinPoolTask extends RecursiveTask<Long> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final Long workLoad;

		public ForkJoinPoolTask(long l) {
			super();
			this.workLoad = l;
		}

		@Override
		protected Long compute() {
			if (this.workLoad > 16) {
				System.out.println("Splitting workLoad : " + this.workLoad);
				ForkJoinPoolTask subtask1 = new ForkJoinPoolTask(this.workLoad / 2);
				ForkJoinPoolTask subtask2 = new ForkJoinPoolTask(this.workLoad / 2);
				subtask1.fork();
				subtask2.fork();
				return subtask1.join() + subtask2.join();
			} else {
				System.out.println(Thread.currentThread().getName() + " : " + this.workLoad);
				return workLoad * 3;
			}
		}
	}

	private class ForkJoinPoolTask1 extends RecursiveTask<List<String>> {
		private static final long serialVersionUID = 1L;
		private final String path;
		
		public ForkJoinPoolTask1(String path) {
			this.path = path;
		}

		@Override
		protected List<String> compute() {
			File file = new File(path);
			File content[] = file.listFiles();
			Stream<String> firstStream = Stream.of(content).filter((ct) -> !ct.isDirectory()).map(File::getAbsolutePath);
			
			Stream<String> secondStream= Stream.of(content).filter((ct) -> ct.isDirectory()).map((ct) ->{ 
				ForkJoinPoolTask1 task=new ForkJoinPoolTask1(ct.getAbsolutePath());
				task.fork();
				return task;
			}).flatMap((tsk)->tsk.join().stream());
			
			return Stream.concat(firstStream, secondStream).collect(Collectors.toList());
	    }
	}

}
