package com.endegraaf.mysqlfit;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * Class will take care of all the MySQL related functions.
 * @author Eric de Graaf
 *
 */
public class MySQLProcedures {

	private static final String SERVER = 	"localhost:3306";
	private static final String DATABASE = 	"petclinic";
	private static final String USER = 		"petclinic";
	private static final String PASSWORD = 	"P3tCl!nic";
	private static final String PATH = 		"/home/eric/workspace/Fitnesse-demo-1/";
	private String queryFile;
	private String ownerId;
	private String[] returnArray;
	
	/**
	 * Constructor for this function.
	 * @param sQType
	 * @param queryFile
	 * @param ownerId
	 * @throws FileNotFoundException
	 */
	public MySQLProcedures(String sQType, String queryFile, String ownerId) throws FileNotFoundException{ 
		/*
		 * Know what type of query to run. 
		 * a) stored procedure w/o resultset
		 * b) query with record set
		 * c) simple query f.e.
		 *   - insert
		 *   - select
		 *   - update
		 *   - delete
		 */
		SetQueryFile(queryFile);
		setOwnerId(ownerId);
		
		System.out.printf("Select %s as query type with %s as file and ownerId %s", sQType, queryFile, ownerId);

		switch (sQType) {
		case "STORED_PROCEDURE":
			
			if(queryFile!=null)
			try {
				System.out.print("try to run the script.");
				this.RunAScript();
			} catch (IOException | SQLException e) {
				System.out.printf("Exception in execute of script %s .", e);
			}
			break;
		case "RECORD_SET":
			break;
		case "SIMPLE_QUERY":
			break;
		}
	}

	public Connection con(){
		Connection mConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = DriverManager.getConnection(
					"jdbc:mysql://" + SERVER + "/" + DATABASE + 
					"?" + "user=" + USER + "&password="+ PASSWORD);
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to get mysql driver: " + e);
		} catch (SQLException e) {
			System.err.println("Unable to connect to server: " + e);
		}
		return mConnection;
	}
	
	/**
	 * This method will call the ScriptRunner class
	 */
	public List<List<List<String>>> RunAScript() throws FileNotFoundException, IOException, SQLException {
		ScriptRunner runner = new ScriptRunner(con(), false, false);
		
		return runner.runScript(new BufferedReader(new FileReader(PATH + queryFile)), getOwnerId());
	}

	
	public List<List<List<String>>> query() throws SQLException, FileNotFoundException, IOException {
				
		return this.RunAScript();
	}

	public List<List<List<String>>> list() throws SQLException {

		java.sql.PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<List<List<String>>> owners = null;

		try {
			//ScriptRunner scrRun = new ScriptRunner(con(), false, false);
			try {
				this.RunAScript();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			statement = this.con().prepareStatement(
					"SELECT * from owners where id = 8");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				owners = asList(asList(
						asList("first name", resultSet.getString("first_name")),
						asList("last name", resultSet.getString("last_name")),
						asList("address", resultSet.getString("address")),
						asList("city", resultSet.getString("city")),
						asList("telephone", resultSet.getString("telephone"))));
			}
		} finally {
			if (resultSet != null)	
				try {resultSet.close();} catch (SQLException ignore) {}
			if (statement != null)
				try {statement.close();} catch (SQLException ignore) {}
			if (con() != null)
				try { con().close();} catch (SQLException ignore) {}
		}
		return owners;
	}

	

	
	public List<List<List<String>>> returnStaticTable() {		
		return 
			asList(
				asList( // row level (3 rows)
						asList("first name", "Sjaak"), // cell column name, value
						asList("last name", "Sjaaksma"),
						asList("address", "Sjaakstraat 7"),
						asList("city", "Sjaakdam"),
						asList("telephone", "8687680098")
					)
			);
	}

	
	
	/* 
	 * Getters and Setters 
	 * */
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public void SetQueryFile(String qFile){
		this.queryFile = qFile;
	}





}