package com.db.model;

public class Hotel {
    private int hotel_id = 0;
    private String hotel_name = null;
    private String hotel_address = null;
    private String hotel_phone_num = null;
    
    public int getHotel_id()
    {
        return this.hotel_id;
    }
    
    public String getHotel_name()
    {
        return this.hotel_name;
    }
    
    public String getHotel_address()
    {
        return this.hotel_address;
    }
    
    public String getHotel_phone_num()
    {
        return this.hotel_phone_num;
    }
    
    public void setHotel_id(int id)
    {
        this.hotel_id = id;
    }
    
    public void setHotel_name(String name)
    {
        this.hotel_name = name;
    }
    
    public void setHotel_address(String address)
    {
        this.hotel_address = address;
    }
    
    public void setHotel_phone_num(String phone)
    {
        this.hotel_address = phone;
    }
    
}
