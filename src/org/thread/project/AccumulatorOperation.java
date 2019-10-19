package org.thread.project;

import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

public class AccumulatorOperation {
	
	private Random random;
	private LongAccumulator accumulator;
	
	{
		random = new Random();
		accumulator = new LongAccumulator((x,y)-> Math.max(x, y), 0);
	}
	
	public AccumulatorOperation() {
		
	}
	
	public void write() {
		for (int i = 0; i <100; i++) {
			accumulator.accumulate(random.nextInt(100));
		}
	}

	public void read() {
		System.out.println(Thread.currentThread().getName()+" accumulated value : "+accumulator.get());					
	}


}
