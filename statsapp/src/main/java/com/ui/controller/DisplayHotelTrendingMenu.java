package com.ui.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.db.connection.DBQueryManager;
import com.db.model.*;
import com.ui.model.OrderItemInstance;

@ManagedBean
@SessionScoped
public class DisplayHotelTrendingMenu implements Serializable{
	
	private List<OrderItemInstance> menu;
	@ManagedProperty(value="#{hotelList}")
    private HotelList hotelList; 
	
	public DisplayHotelTrendingMenu() {
		menu = new ArrayList<OrderItemInstance>();
	}

	public List<OrderItemInstance> getMenu() {
		//TODO: Hardcoded Hotel
		menu = new DBQueryManager().getTopMenuItem(getHotelList().getSelectedHotel().getId());
		return menu;
	}

	public void setMenu(List<OrderItemInstance> menu) {
		this.menu = menu;
	}

	public HotelList getHotelList() {
		return hotelList;
	}

	public void setHotelList(HotelList hotelList) {
		this.hotelList = hotelList;
	}	
}