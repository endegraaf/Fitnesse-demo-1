package com.endegraaf.mysqlfit;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLTest {

	private static final String SERVER = 	"localhost:3306";
	private static final String DATABASE = 	"petclinic";
	private static final String USER = 		"petclinic";
	private static final String PASSWORD = 	"P3tCl!nic";
	private static final String PATH = "/home/eric/workspace/Fitnesse-demo-1/";
	QueryType qType;
	String queryFile;
	
	public MySQLTest(String sQType, String queryFile) throws FileNotFoundException{
		/*
		 * Know what type of query to run. 
		 * a) stored procedure 
		 * b) record set
		 * c) simple query f.e.
		 *   - insert
		 *   - select
		 *   - update
		 *   - delete
		 */
		SetQueryFile(queryFile);
		System.out.printf("Select %s as query type with %s as file.", sQType, queryFile);

		switch (sQType) {
		case "STORED_PROCEDURE":
			try {
				System.out.print("try to run the script.");
				this.RunAScript();
			} catch (IOException | SQLException e) {
				System.out.printf("Exception in execute of script %s .", e);
				//e.printStackTrace();
			}
			break;
		case "RECORD_SET":
			break;
		case "SIMPLE_QUERY":
			break;
		}
	}

	public void SetQType(QueryType qType){
		this.qType = qType;
	}
	public enum QueryType {
		STORED_PROCEDURE, RECORD_SET, SIMPLE_QUERY
	}
	public void SetQueryFile(String qFile){
		this.queryFile = qFile;
	}

	public Connection con(){
		Connection mConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = DriverManager.getConnection("jdbc:mysql://" + SERVER
					+ "/" + DATABASE + "?" + "user=" + USER + "&password="
					+ PASSWORD);
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to get mysql driver: " + e);
		} catch (SQLException e) {
			System.err.println("Unable to connect to server: " + e);
		}
		return mConnection;
	}
	
	/*
	 * This method will call the ScriptRunner class
	 * 
	 */
	public void RunAScript() throws FileNotFoundException, IOException,
			SQLException {
		Connection mConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = DriverManager.getConnection("jdbc:mysql://" + SERVER
					+ "/" + DATABASE + "?" + "user=" + USER + "&password="
					+ PASSWORD);
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to get mysql driver: " + e);
		} catch (SQLException e) {
			System.err.println("Unable to connect to server: " + e);
		}
		ScriptRunner runner = new ScriptRunner(mConnection, false, false);
		runner.runScript(new BufferedReader(new FileReader(PATH + queryFile)));
	}

	
	public List<List<List<String>>> query() throws SQLException {

		
		return 
			asList(// table level (1)
				asList( // row level (3)
					asList("company number", "4808147"), // cell column name, value
					asList("employee number", "1429"),
					asList("first name", "Bob"),
					asList("last name", "Martin"),
					asList("hire date", "10-Oct-1974")
				),asList(
					asList("company number", "5123122"),
					asList("employee number", "8832"),
					asList("first name", "Bob"),
					asList("last name", "Grenning"),
					asList("hire date", "15-Dec-1979")
				),asList(
					asList("company number", "4543"),
					asList("employee number", "45"),
					asList("first name", "Jan"),
					asList("last name", "Boelens"), 
					asList("hire date", "")
				)
			);
	}





}