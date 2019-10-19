package org.thread.project;

public class VolatileOperation {

	public long count = 0;
	public boolean flag = true;
	public volatile boolean volaliteFlag = true;

	public void startOperation() {
		System.out.println("Operation start called");
		while (flag) {
			count++;
		}
		System.out.println("Operation shut down");
	}

	public void stopOperation() {	
		flag = false;
		System.out.println("Operation stop called");
	}

	public void startVolatileOperation() {
		System.out.println("Volalite Operation start called");
		while (volaliteFlag) {
			System.out.println(count);
		}
		System.out.println("Volalite Operation shut down");
	}

	public void stopVolatileOperation() {
		volaliteFlag = false;
		System.out.println("Volalite Operation stop called");
	}
	

}
