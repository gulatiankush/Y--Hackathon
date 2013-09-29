package com.ui.controller;

import java.io.Serializable;  
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.db.connection.DBQueryManager;
import com.db.model.MenuItem;
import com.ui.model.OrderItemInstance;
 
@ManagedBean
@SessionScoped
public class Order implements Serializable {  
  
	private List<OrderItemInstance> orderItems;
	
	public Order() {
		this.orderItems = new ArrayList<OrderItemInstance>();
		updateItems();
	}

	public List<OrderItemInstance> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemInstance> orderItems) {
		this.orderItems = orderItems;
	}
      
	private void updateItems() {
		// TODO: Hardcoded value for hotel
		List<MenuItem> items = new DBQueryManager().getMenu(1);
		for(MenuItem i : items) {
			OrderItemInstance oi = new OrderItemInstance(i.getName());
			orderItems.add(oi);
		}
	}
	
	public void insertItems() {
		if(!getOrderItems().isEmpty()) {
			new DBQueryManager().insertOrder(getOrderItems(), 1);
			System.out.println("Inserting into DB");
		} else {
			System.out.println("Order List is Empty");
		}
	}
	
}