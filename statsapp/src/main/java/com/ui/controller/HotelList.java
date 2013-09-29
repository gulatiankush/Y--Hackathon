package com.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.db.connection.DBQueryManager;
import com.db.model.Hotel;

@ManagedBean
@SessionScoped
public class HotelList implements Serializable{
	
	private List<Hotel> list;
	
	private Hotel selectedHotel;

	@ManagedProperty(value="#{user}")
	private User selectedUser;
	
	public HotelList() {
		list = new ArrayList<Hotel>();
	}

	public List<Hotel> getList() {
		//TODO: Hardcoded Hotel
		list = new DBQueryManager().getHotels();
		return list;
	}

	public void setList(List<Hotel> list) {
		this.list = list;
	}

	public Hotel getSelectedHotel() {
		return selectedHotel;
	}

	public void setSelectedHotel(Hotel selectedHotel) {
		this.selectedHotel = selectedHotel;
	}
	
	public void callURL() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try{
			if(selectedUser.getName().equalsIgnoreCase("user")) {
				context.redirect(context.getRequestContextPath() + "/index.xhtml");
			} else {
				context.redirect(context.getRequestContextPath() + "/Menu.xhtml");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
}