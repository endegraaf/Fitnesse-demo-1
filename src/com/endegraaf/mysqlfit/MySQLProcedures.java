package com.endegraaf.mysqlfit;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLProcedures {

	private static final String SERVER = 	"localhost:3306";
	private static final String DATABASE = 	"petclinic";
	private static final String USER = 		"petclinic";
	private static final String PASSWORD = 	"P3tCl!nic";
	private static final String PATH = 		"/home/eric/workspace/Fitnesse-demo-1/";
	private String queryFile;
	private String ownerId;
	private String[] returnArray;
	
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
		System.out.printf("Select %s as query type with %s as file and ownerId %s", sQType, queryFile, ownerId);

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
		returnArray = runner.runScript(new BufferedReader(new FileReader(PATH + queryFile)));
	}

	
	public List<List<List<String>>> query() throws SQLException, FileNotFoundException, IOException {

//		List<List<List<String>>> ownerList = new ArrayList<>();
//
//		// Create the object to get the actual values from DB
//		OwnerWrapper JohnWrapped = new OwnerWrapper();
//		ScriptRunner sr = new ScriptRunner(con(), false, true);
//		String[] JohnStringArray = sr.runScript(new BufferedReader(new FileReader("/home/eric/workspace/Fitnesse-demo-1/select_script.sql")));
//		ownerList = JohnWrapped.AddStringItemToList(JohnStringArray);
		
		//return ownerList;
		
		// Create instance of MySQLProcedures class.
		MySQLProcedures msp = new MySQLProcedures("STORED_PROCEDURE", "select_script.sql", "8");
		
		// Actual value
		List<List<List<String>>> ownerList = new ArrayList<>();
		
		ownerList = msp.list();
		
		return ownerList;
		
		
//		return 
//			asList(// table level (1)
//				asList( // row level (3 rows)
//					asList("first name", "Carlos"), // cell column name, value
//					asList("last name", "Estaban"),
//					asList("address", "2335 Independence La."),
//					asList("city", ""),
//					asList("telephone", "6085555488")
//				),asList( // row level (3 rows)
//						asList("first name", "Jan"), // cell column name, value
//						asList("last name", "Janssen"),
//						asList("address", "Janstraat 2"),
//						asList("city", "Janmeer"),
//						asList("telephone", "080987098")
//				),asList( // row level (3 rows)
//						asList("first name", "Sjaak"), // cell column name, value
//						asList("last name", "Sjaaksma"),
//						asList("address", "Sjaakstraat 7"),
//						asList("city", "Sjaakdam"),
//						asList("telephone", "8687680098")
//					)
//			);
	}

	
	public List<List<List<String>>> list() throws SQLException {
        
        java.sql.PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<List<List<String>>> owners = null;

        try {
            
            statement = this.con().prepareStatement("SELECT * from owners where id = 8");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	
            owners = asList(
            			asList(
            					asList("first name",resultSet.getString("first_name")),
            					asList("last name",resultSet.getString("last_name")),
            					asList("address",resultSet.getString("address")),
            					asList("city",resultSet.getString("city")),
            					asList("telephone",resultSet.getString("telephone"))
            					)
            			);
//				owners.addAll(asList("first name", resultSet.getString("first_name")));
//				owners.addAll(asList("last name", resultSet.getString("last_name")));
//				owners.addAll(asList("address", resultSet.getString("address")));
//				owners.addAll(asList("city", resultSet.getString("city")));
//				owners.addAll(asList("telephone", resultSet.getString("telephone")));
            }
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (con() != null) try { con().close(); } catch (SQLException ignore) {}
        }

        

        return owners;
    }






}