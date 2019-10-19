package org.thread.project;

import java.util.concurrent.Exchanger;

public class ExchangerOperation {

	private Exchanger<String> exchanger;
	
	public String name;
	
	public ExchangerOperation(Exchanger<String> exchanger,String name) {
		super();
		this.exchanger = exchanger;
		this.name=name;
	}
	
	public void execute() {
		System.out.println(Thread.currentThread().getName()+" before exchange: "+name);
		try {
			name=exchanger.exchange(name);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" after exchange: "+name);
	}
	
}
