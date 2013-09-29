package com.db.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.db.model.Hotel;
import com.db.model.MenuItem;
import com.google.appengine.api.utils.SystemProperty;
import com.mysql.jdbc.Statement;
import com.ui.model.OrderItemInstance;

public class DBQueryManager {

	public ArrayList<Hotel> getRestaurants() {

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Hotel> result = new ArrayList<Hotel>();

		System.out
				.println("-------- MySQL JDBC Connection Testing ------------");

		Connection connection = null;
		DBConnectionManager connect = new DBConnectionManager();
		connection = connect.getConnection(
				"jdbc:mysql://localhost:3306/hotels", "root", "");

		try {
			stmt = (Statement) connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM hotel");
			while (rs.next()) {
				Hotel hotel = new Hotel();
				hotel.setHotel_id(rs.getInt(1));
				hotel.setHotel_name(rs.getString("hotel_name"));
				hotel.setHotel_address(rs.getString("address"));
				hotel.setHotel_phone_num(rs.getString("phone_num"));
				result.add(hotel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return result;
	}

	public ArrayList<MenuItem> getMenu(int hotelId) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<MenuItem> result = new ArrayList<MenuItem>();

		System.out
				.println("-------- MySQL JDBC Connection Testing ------------");

		Connection connection = null;
		DBConnectionManager connect = new DBConnectionManager();
		connection = connect.getConnection(
		/* "jdbc:mysql://localhost:3306/hotels" */getUrl(), "root", "");

		try {
			stmt = (Statement) connection.createStatement();
			String query = "SELECT * FROM menu where hotel_id = " + hotelId;

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				MenuItem r = new MenuItem();
				r.setId(rs.getInt(1));
				r.setName(rs.getString("item_name"));
				r.setCategory(rs.getString("category"));
				r.setPrice(rs.getDouble("price"));
				r.setHotelId(rs.getInt("hotel_id"));
				result.add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return result;
	}

	public ArrayList<OrderItemInstance> getTopMenuItem(int hotelId) {
		// TODO Auto-generated method stub
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<OrderItemInstance> result = new ArrayList<OrderItemInstance>();

		System.out
				.println("-------- MySQL JDBC Connection Testing ------------");

		Connection connection = null;
		DBConnectionManager connect = new DBConnectionManager();
		connection = connect.getConnection(
		/* "jdbc:mysql://localhost:3306/hotels" */getUrl(), "root", "");

		try {
			stmt = (Statement) connection.createStatement();
			String query = "select o.item_id, m.item_name, sum(o.quantity) as count from orders as o, menu as m where o.hotel_id = "
					+ hotelId
					+ " and o.item_id = m.item_id group by item_id order by count desc";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				OrderItemInstance r = new OrderItemInstance();
				r.setId(rs.getInt(1));
				r.setName(rs.getString(2));
				r.setCount(rs.getInt(3));
				result.add(r);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return result;
	}

	public void insertOrder(List<OrderItemInstance> orderList, int hotelId) {
		Connection conn = null;
		Statement stmt = null;
		String uuid = null;
		ResultSet rs = null;
		java.util.Date date = Calendar.getInstance().getTime();

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = new DBConnectionManager().getConnection(
			/* "jdbc:mysql://localhost:3306/hotels" */getUrl(), "root", "");
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Inserting records into the order table...");

			if (orderList.isEmpty()) {
				System.out.println("Order List is Empty");
			}

			Iterator<OrderItemInstance> i = orderList.iterator();
			OrderItemInstance temp = null;
			stmt = (Statement) conn.createStatement();

			while (i.hasNext()) {
				temp = i.next();

				Statement st1 = (Statement) conn.createStatement();
				String q1 = "select item_id from menu where item_name = '"
						+ temp.getName() + "'";
				int itemID;
				System.out.println("Query: " + q1);
				rs = st1.executeQuery(q1);
				if (rs.next()) {
					itemID = rs.getInt(1);
					System.out.println("Item ID: " + itemID);
				}
				String sql = "insert into orders "
						+ "(time_of_purchase,quantity,item_id,hotel_id) "
						+ "values(\'" + new Timestamp(date.getTime()) + "\'"
						+ ", " + temp.getCount() + ", " + rs.getInt("item_id")
						+ ", " + hotelId + ")";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}

			System.out.println("Inserted records into the Order table...");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}// do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}

	String getUrl() {
		String url = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				// Load the class that provides the new "jdbc:google:mysql://"
				// prefix.
				Class.forName("com.mysql.jdbc.GoogleDriver");
				url = "jdbc:google:mysql://db-connection-test:one/hotels?user=root";
			} else {
				// Local MySQL instance to use during development.
				Class.forName("com.mysql.jdbc.Driver");
				url = "jdbc:mysql://127.0.0.1:3306/hotels?user=root";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url;
	}
}