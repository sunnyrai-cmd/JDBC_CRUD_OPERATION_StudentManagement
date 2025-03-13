package com.jspider.prepared_crud_operation.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class StudentConnection {
	
	public static Connection getStudentConnection() {
		Driver driver;
		try {
			
//			Step-1 Load/Register The Driver
			
			driver = new Driver();
			DriverManager.registerDriver(driver); //
			
//			Step-2 Create Connection
			
			String url = "jdbc:mysql://localhost:3306/jdbc-weekend";
			String name = "root";
			String password = "SUNNY@123";
			
			return DriverManager.getConnection(url, name, password);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		

}
}
