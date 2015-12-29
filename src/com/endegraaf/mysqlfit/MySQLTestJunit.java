package com.endegraaf.mysqlfit;

import static org.junit.Assert.*;

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
	
	@Test
	public void TestThatQueryCanBePassed() throws SQLException, FileNotFoundException, IOException{
		MySQLProcedures mst = new MySQLProcedures("STORED_PROCEDURE", "select_script.sql");
		mst.RunAScript();
		Assert.assertEquals("", "5.5.46-0ubuntu0.14.04.2");
	}
	
	@Test
	public void TestCreateOwnerInstance(){
		//Owner Jan = new Owner(null, "Jan", "Janssen", "Janstraat 1", "Zwammerdam", "090-3092");
		//Assert.assertEquals("Janssen", Jan.getLast_name());
	}

	@Test
	public void TestDrivenDevelopment() throws FileNotFoundException, SQLException{
		
		MySQLProcedures mst = new MySQLProcedures("","");
		
		List<List<List<String>>> expectedValueList = mst.query();
		List<List<List<String>>> ownerList = new ArrayList<>();
		
		OwnerWrapper SjaakWrapped = new OwnerWrapper();

		Owner Sjaak = new Owner();
		String[] mySjaak = Sjaak.NewOwner(null, "Sjaak", "Sjaaksma", "Sjaaktraat 1", "Sjaakdam", "009-3232");
		
		ownerList = SjaakWrapped.AddStringItemToList(mySjaak);

		
		Assert.assertEquals(expectedValueList, ownerList);

		
	}
	
	

}
