package org.thread.project.pc;

import java.util.Date;

public class Product {

	private Date added;
	private String name;
	private int quantity;
	{
		added = new Date();
	}

	public Product(String name, int quantity ) {
		this.name=name;
		this.quantity = quantity;
	}

	public Date getAdded() {
		return added;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "Product [added=" + added + ", name=" + name + ", quantity=" + quantity + "]";
	}

}
