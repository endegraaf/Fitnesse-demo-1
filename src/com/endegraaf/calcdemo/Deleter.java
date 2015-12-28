package com.endegraaf.calcdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Driver;

import oracle.jdbc.OracleDriver;
//nl015udbp5114.unix.dlnv
public class Deleter {
	private static final String OracleURL = "jdbc:oracle:thin@10.18.178.52:6208:PINT10T";
	private static final String OracleUser = "tester";
	private static final String OraclePassword = "tester";

	public static void main (String[] args) throws SQLException { 
		
		//OracleDriver conn = new OracleDriver();
		DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
		
		Connection conn = DriverManager.getConnection
				  ("jdbc:oracle:oci8:@10.18.178.52:6208:PINT10T", "tester", "tester");
		
		//Connection conn = DriverManager.getConnection(OracleURL, OracleUser, OraclePassword);
		//Connection conn = DriverManager.getConnection(OracleURL);
		
	    //Connection conn = new OracleDriver().defaultConnection();
//	    String sql = "DELETE FROM " + args[0];
//	    if (args.length > 1) sql += " WHERE " + args[1];
//	    try {
//	      Statement stmt = conn.createStatement();
//	      stmt.executeUpdate(sql);
//	      stmt.close();
//	    } catch (SQLException e) {System.err.println(e.getMessage());}
	  }
}
