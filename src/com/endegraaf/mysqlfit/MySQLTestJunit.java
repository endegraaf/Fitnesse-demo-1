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
	public void TestDrivenDevelopment() throws FileNotFoundException, SQLException{
		
		MySQLProcedures mst = new MySQLProcedures("","");
		
		List<List<List<String>>> expectedValueList = mst.query();
		List<List<List<String>>> ownerList = new ArrayList<>();
		
		OwnerWrapper SjaakWrapped = new OwnerWrapper();

		Owner Sjaak = new Owner(10);
		String[] mySjaak = Sjaak.NewOwner("Sjaak", "Sjaaksma", "Sjaaktraat 1", "Sjaakdam", "009-3232");
		
		ownerList = SjaakWrapped.AddStringItemToList(mySjaak);
		
		Assert.assertNotNull("Object is created.", ownerList);
//		Assert.assertEquals(expectedValueList, ownerList);
	}
	@Test
	public void GetValuesFromDatabase() throws SQLException, IOException{
		
		// Create instance of MySQLProcedures class.
		MySQLProcedures msp = new MySQLProcedures("STORED_PROCEDURE", "select_script.sql");
		
		// Expected value is in expectedValueList
		List<List<List<String>>> expectedValueList = msp.query();
		
		// Actual value
		List<List<List<String>>> ownerList = new ArrayList<>();

		// Create the object to get the actual values from DB
		OwnerWrapper JohnWrapped = new OwnerWrapper();
		ScriptRunner sr = new ScriptRunner(msp.con(), false, true);
		String[] JohnStringArray = sr.runScript(new BufferedReader(new FileReader("/home/eric/workspace/Fitnesse-demo-1/select_script.sql")));
		ownerList = JohnWrapped.AddStringItemToList(JohnStringArray);
		
		// Assertion
		Assert.assertEquals(expectedValueList, ownerList);
	}
	
	
	
	

}
