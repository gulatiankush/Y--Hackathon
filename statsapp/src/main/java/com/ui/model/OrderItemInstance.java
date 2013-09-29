package com.ui.model;

public class OrderItemInstance {
	
	private int id;
	private String name;
	private int count;
	
	public OrderItemInstance() {
		
	}
	
	public OrderItemInstance(String name) {
		this.name = name;
		this.count = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCount(int count) {
		System.out.println("Value: "+count);
		this.count = count;
	}
}