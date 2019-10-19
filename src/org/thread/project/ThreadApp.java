package org.thread.project;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import org.thread.project.lock.ReadWriteLockOperation;
import org.thread.project.lock.SynchronizedLockOperation;
import org.thread.project.pc.BlockingQueuePC;
import org.thread.project.pc.CustomBlockingQueuePC;
import org.thread.project.pc.Product;
import org.thread.project.queue.CustomBlockingQueue;
import org.thread.project.queue.WaitAndNotifyBlockingQueue;
import org.thread.project.service.executors.CachedThreadPool;
import org.thread.project.service.executors.FixedThreadPool;
import org.thread.project.service.executors.ForkJoinThreadPool;
import org.thread.project.service.executors.ScheduledThreadPool;
import org.thread.project.service.executors.SingleThreadPool;

public class ThreadApp {

	public static void main(String[] args) throws InterruptedException {
		// AtomicTest.counter();
		// AtomicTest.test();
		// AdderTest.test();
		// AccumulatorTest.test();
		// VolatileTest.test();
		// CountDownLatchCyclicBarrierPhaserTest.test();
		// ExchangerTest.test();
		//  SemaphoreTest.test();
		// BlockingQueuePcTest.test();
		// CustomBlockingQueuePcTest.test();
		// FixedThreadPoolTest.test();
		// CachedThreadPoolTest.test();
		// SingleThreadPoolTest.test();
		// ScheduledThreadPoolTest.test();
		// ForkJoinThreadPoolTest.test();
		// SynchronizeLockTest.test();
		ReadWriteLockTest.test();

	}

	public static class AtomicTest {

		public static void counter() throws InterruptedException {
			CountDownLatch countDownLatch = new CountDownLatch(100);
			CounterOperation counterOperation = new CounterOperation();
			for (int i = 0; i < 100; i++) {
				new Thread(() -> {
					counterOperation.writeCounter();
					countDownLatch.countDown();
				}).start();
			}
			countDownLatch.await();
			counterOperation.readCounter();
		}

		public static void test() throws InterruptedException {
			CountDownLatch countDownLatch = new CountDownLatch(100);
			CounterOperation counterOperation = new CounterOperation();
			for (int i = 0; i < 100; i++) {
				new Thread(() -> {
					counterOperation.writeAtomicCounter();
					countDownLatch.countDown();
				}).start();
			}
			countDownLatch.await();
			counterOperation.readAtomicCounter();
		}

	}

	public static class AdderTest {

		public static void test() throws InterruptedException {
			AdderOperation adderOperation = new AdderOperation();
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(() -> adderOperation.write());
			executorService.execute(() -> adderOperation.write());
			executorService.execute(() -> adderOperation.write());
			executorService.execute(() -> adderOperation.write());
			executorService.execute(() -> adderOperation.write());
			Thread.sleep(10000);
			executorService.execute(() -> adderOperation.read());
		}
	}

	public static class AccumulatorTest {

		public static void test() throws InterruptedException {
			AccumulatorOperation accumulatorOperation = new AccumulatorOperation();
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(() -> accumulatorOperation.write());
			executorService.execute(() -> accumulatorOperation.write());
			executorService.execute(() -> accumulatorOperation.write());
			executorService.execute(() -> accumulatorOperation.write());
			executorService.execute(() -> accumulatorOperation.write());
			Thread.sleep(10000);
			executorService.execute(() -> accumulatorOperation.read());
		}
	}

	public static class VolatileTest {

		public static void test() throws InterruptedException {
			VolatileOperation volatileOperation = new VolatileOperation();
			ExecutorService executorService = Executors.newCachedThreadPool();
			executorService.execute(() -> volatileOperation.startOperation());
			executorService.execute(() -> volatileOperation.startVolatileOperation());
			Thread.sleep(10000);
			executorService.execute(() -> volatileOperation.stopOperation());
			executorService.execute(() -> volatileOperation.stopVolatileOperation());
		}
	}

	public static class CountDownLatchCyclicBarrierPhaserTest {

		public static void test() throws InterruptedException {
			Phaser cyclicPhaser = new Phaser(100);
			Phaser counterPhaser = new Phaser(100);
			CountDownLatch countDownLatch = new CountDownLatch(100);
			CyclicBarrier cyclicBarrier = new CyclicBarrier(100, () -> System.out.println("Cycle completed"));
			CyclicBarrierOperation cyclicBarrierOperation = new CyclicBarrierOperation();
			ExecutorService executorService = Executors.newCachedThreadPool();
			IntStream.range(1, 101).forEach((i) -> executorService.execute(() -> cyclicBarrierOperation.startOperation(cyclicBarrier, countDownLatch)));
			countDownLatch.await();
			System.out.println("Cyclic barrier Completed");
			IntStream.range(1, 101).forEach((i) -> 	executorService.execute(() -> cyclicBarrierOperation.startOperation(cyclicPhaser, counterPhaser)));
			counterPhaser.awaitAdvance(0);
			System.out.println("Cyclic phaser Completed");
		}
	}

	
	public static class ExchangerTest {
		public static void test() {
			Exchanger<String> exchanger=new Exchanger<>();
			ExchangerOperation exchangerOperation1=new ExchangerOperation(exchanger, "A");
			ExchangerOperation exchangerOperation2=new ExchangerOperation(exchanger, "B");
			new Thread(()->exchangerOperation1.execute()).start();
			new Thread(()->exchangerOperation2.execute()).start();
		}
	}
	
	public static class SemaphoreTest {
		public static void test() {
			Semaphore semaphore = new Semaphore(3, true);
			ExecutorService executorService = Executors.newFixedThreadPool(4);
			IntStream.range(1, 13).forEach((i) -> executorService.execute(() -> {
				System.out.println(Thread.currentThread().getName()+"[ "+i+" ] acquire : "+semaphore.availablePermits());
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				semaphore.release();
				System.out.println(Thread.currentThread().getName()+"[ "+i+" ] relesed  : "+semaphore.availablePermits());

			}));
			executorService.shutdown();
		}
		
	}
	public static class BlockingQueuePcTest {

		public static void test() {
			BlockingQueuePC pc = new BlockingQueuePC(new ArrayBlockingQueue<Product>(1000));
			ExecutorService executorService = Executors.newCachedThreadPool();
			IntStream.range(1, 11).forEach((i) -> executorService.execute(() -> pc.producer()));
			IntStream.range(1, 16).forEach((i) -> executorService.execute(() -> pc.consumer()));
		}
	}

	public static class CustomBlockingQueuePcTest {

		public static void test() {
			CustomBlockingQueue<Product> queue = new WaitAndNotifyBlockingQueue<>(1000, new LinkedList<>());
			CustomBlockingQueuePC pc = new CustomBlockingQueuePC(queue);
			ExecutorService executorService = Executors.newCachedThreadPool();
			IntStream.range(1, 11).forEach((i) -> executorService.execute(() -> pc.producer()));
			IntStream.range(1, 16).forEach((i) -> executorService.execute(() -> pc.consumer()));

		}
	}

	public static class FixedThreadPoolTest {

		public static void test() {
			FixedThreadPool threadPool = new FixedThreadPool();
			threadPool.execute();
		}
	}

	public static class CachedThreadPoolTest {

		public static void test() {
			CachedThreadPool threadPool = new CachedThreadPool();
			threadPool.execute();
		}
	}

	public static class SingleThreadPoolTest {

		public static void test() {
			SingleThreadPool threadPool = new SingleThreadPool();
			threadPool.execute();
		}
	}

	public static class ScheduledThreadPoolTest {

		public static void test() {
			ScheduledThreadPool threadPool = new ScheduledThreadPool();
			threadPool.execute();
		}
	}

	public static class ForkJoinThreadPoolTest {

		public static void test() {
			ForkJoinThreadPool threadPool=new ForkJoinThreadPool();
			threadPool.execute();
		}
	}
	
	public static class  SynchronizeLockTest{
		
		public static void test() {
			ExecutorService executorService = Executors.newCachedThreadPool();
			SynchronizedLockOperation lock= new SynchronizedLockOperation();
			IntStream.range(1, 8).forEach((i) ->executorService.execute(()->lock.read()) );
			IntStream.range(1, 8).forEach((i) ->executorService.execute(()->lock.write(i)));
		}
		
	}
	
   public static class  ReadWriteLockTest{
		
		public static void test() {
			ExecutorService executorService = Executors.newCachedThreadPool();
			ReadWriteLockOperation lock= new ReadWriteLockOperation();
			IntStream.range(1, 8).forEach((i) ->executorService.execute(()->lock.read()) );
			IntStream.range(1, 8).forEach((i) ->executorService.execute(()->lock.write(i)));
		}
		
	}
}
