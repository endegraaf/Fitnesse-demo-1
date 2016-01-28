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
	//private static final String PATH = 		"/home/eric/git/Fitnesse-demo-1/";
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
				//System.out.print("try to run the script.");
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

	public String list(int i, String attribName) throws SQLException {
		
		String retVal = null;
		java.sql.PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = this.con().prepareStatement("SELECT " + attribName + " from owners where id = " + i);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				retVal = resultSet.getString(attribName);
				System.out.println("Return value from function 'list' "	+ retVal);
			}
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException ignore) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			if (con() != null)
				try {
					con().close();
				} catch (SQLException ignore) {
				}
		}
		return retVal;
	}
	

	
	public List<List<List<String>>> returnStaticTable() {		
		return 
			asList(
				asList( // row level (3 rows)
						asList("first name", "Eric"), // cell column name, value
						asList("last name", "Janssen"),
						asList("address", "Langeweg"),
						asList("city", "Den Haag"),
						asList("telephone", "0243324908")
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


	/*
	 * 
	 * 	'1', 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023'
		'2', 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749'
		'3', 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763'

	 */
	public List<List<List<String>>> returnStaticTableMultiRow() {
		return asList(
					asList(
						// row level (3 rows)
						asList("first name", "George"), // cell column name, value
						asList("last name", "Franklin"),
						asList("address", "110 W. Liberty St.")
					),
					asList(
						// row level (3 rows)
						asList("first name", "Betty"), // cell column name, value
						asList("last name", "Davis"),
						asList("address", "638 Cardinal Ave.")
					),
					asList(
						// row level (3 rows)
						asList("first name", "Eduardo"), // cell column name, value
						asList("last name", "Rodriquez"),
						asList("address", "2693 Commerce St.")
					)
				);
	}

	





}