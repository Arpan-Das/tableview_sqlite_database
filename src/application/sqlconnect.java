package application;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class sqlconnect {
	static Connection conn;
	
	public static Connection dbconnect() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:table.db");
			
			JOptionPane.showMessageDialog(null, "Connection Established!!");
			
			return conn;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
	public static ObservableList<user> getDatauser(){
		Connection conn = dbconnect();
		ObservableList<user> list = FXCollections.observableArrayList();
		
		try {
			
			//PreparedStatement ps = conn.prepareStatement("select * from user");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user");
			
			while(rs.next()) {
				list.add(new user(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("type")));
				
			}
						
		}catch(Exception e) {
			
		}
		return list;
	}
}
