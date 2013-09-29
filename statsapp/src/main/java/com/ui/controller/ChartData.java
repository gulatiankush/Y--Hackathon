package com.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.db.connection.DBQueryManager;
import com.ui.model.OrderItemInstance;

@ManagedBean
@SessionScoped
public class ChartData {
    
    private List<OrderItemInstance> orders;
    private String jsonString;
	@ManagedProperty(value="#{hotelList}")
    private HotelList hotelList; 
    
    public ChartData() {
        orders = new ArrayList<OrderItemInstance>();
    }

    public void setJsonString(String myString) {
        this.jsonString = myString;
    }
    
    public String getJsonString() {
    	
    	orders = new DBQueryManager().getTopMenuItem(hotelList.getSelectedHotel().getId());
    	
    	JSONArray result = new JSONArray();
    	
    	JSONObject object = null;
    	for(OrderItemInstance o : orders) {
    		object = new JSONObject();
    		object.put("order", o.getName());
    		object.put("count", o.getCount());
    		result.add(object);
    	}
    	
    	String resultString = result.toJSONString();
    	String finalResult = resultString.replace("\"", "'");
    	
    	return finalResult;
    	  
/*        return " [ {'order':'Masala dosa', 'count':5},"
                + "{'order':'batata wada', 'count':3},"
                + "{'order':'paav bhaji', 'count':4},"
                + "{'order':'coffee', 'count':2},"
                + "{'order':'milk', 'count':1} ]";
*/
    }

    public void setOrders(List<OrderItemInstance> orders) {
        this.orders = orders;
    }

    public List<OrderItemInstance> getOrders() {
        return orders;
    }   
    
	public HotelList getHotelList() {
		return hotelList;
	}

	public void setHotelList(HotelList hotelList) {
		this.hotelList = hotelList;
	}
}