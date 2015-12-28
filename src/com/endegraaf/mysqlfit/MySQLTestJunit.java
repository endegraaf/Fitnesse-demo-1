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
		MySQLTest mst = new MySQLTest("STORED_PROCEDURE", "select_script.sql");
		mst.RunAScript();
		
		Assert.assertEquals("", "5.5.46-0ubuntu0.14.04.2");
	}
	
	@Test
	public void TestThatQueryCanBePassedAndReturnRecordSet() throws SQLException, FileNotFoundException, IOException{
		
		MySQLTest mst = new MySQLTest("STORED_PROCEDURE", "select_script.sql");
		
		Assert.assertEquals(mst.query(), "5.5.46-0ubuntu0.14.04.2");
	}


}
