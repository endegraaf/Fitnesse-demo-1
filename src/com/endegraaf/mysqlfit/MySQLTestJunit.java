package com.endegraaf.mysqlfit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MySQLTestJunit {
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * Test Cases.
	 */
	@Test
	public void TestDrivenDevelopment() throws SQLException, IOException{
		
		MySQLProcedures mst = new MySQLProcedures("STORED_PROCEDURE", "select_script.sql", "8");
		
		List<List<List<String>>> expectedValueList = mst.query();
		List<List<List<String>>> ownerList = new ArrayList<>();
		
		Owner Sjaak = new Owner(10);
		List<List<List<String>>> mySjaak = Sjaak.NewOwner("Sjaak", "Sjaaksma", "Sjaaktraat 1", "", "");
		
		Assert.assertNotNull("Object is created.", mySjaak);
//		Assert.assertEquals(expectedValueList, ownerList);
	}
	@Test
	public void GetValuesFromDatabase() throws SQLException, IOException{
		
		// Create instance of MySQLProcedures class.
		MySQLProcedures msp = new MySQLProcedures("STORED_PROCEDURE", "select_script.sql", "8");
		
		// Expected value is in expectedValueList
		List<List<List<String>>> expectedValueList = msp.query();
		
		// Actual value
		List<List<List<String>>> ownerList = new ArrayList<>();

		// Create the object to get the actual values from DB
		OwnerWrapper JohnWrapped = new OwnerWrapper();
		ScriptRunner sr = new ScriptRunner(msp.con(), false, true);
		List<List<List<String>>> JohnStringArray = sr.runScript(new BufferedReader(new FileReader("/home/eric/workspace/Fitnesse-demo-1/select_script.sql")), "8");
		//ownerList = JohnWrapped.AddStringItemToList(JohnStringArray);
		
		// Assertion
		Assert.assertEquals(expectedValueList, JohnStringArray);
	}
	
	@Test
	public void BuildRecordSetReturnList() throws SQLException, IOException{

		List<List<List<String>>> expectedValueList;
		List<List<List<String>>> ownerList = new ArrayList<>();
		
		// Create instance of MySQLProcedures class.
		MySQLProcedures msp = new MySQLProcedures("STORED_PROCEDURE", "select_script.sql", "20");
		
		// Expected value is in expectedValueList
		expectedValueList = msp.returnStaticTable();
		
		// Actual value
		ownerList = msp.query();
		
		// Assertion
		Assert.assertEquals(expectedValueList, ownerList);

	}
	
	@Test
	public void ExecuteSqlStatementMultipleRows() throws SQLException, IOException{

		List<String> list1 = new ArrayList<>();
		List<List<String>> list2 = new ArrayList<>(); 
		List<List<List<String>>> list3 = new ArrayList<>();
		
		// Expected value
		List<List<List<String>>> staticValueList;

		// Create instance of MySQLProcedures class.
		MySQLProcedures msp = new MySQLProcedures("RECORD_SET", "select_script3.sql", "20");
		
		// Expected value is in staticValueList
		staticValueList = msp.returnStaticTableMultiRow();
		
		for (int i=1; i<4; i++) {
			list1=null;
			list1 = new ArrayList<>();
			list2=null;
			list2 = new ArrayList<>();
			
			list1.add("first name");
			list1.add(msp.list(i, "first_name"));
			list2.add(list1);
			
			list1=null;
			list1 = new ArrayList<>();
			
			list1.add("last name");
			list1.add(msp.list(i, "last_name"));
			list2.add(list1);
			
			list1=null;
			list1 = new ArrayList<>();
			
			list1.add("address");
			list1.add(msp.list(i, "address"));			
			list2.add(list1);
			
			list3.add(list2);
		}
		
		// Assertion
		Assert.assertEquals(staticValueList, list3);

	}
	
	
}
