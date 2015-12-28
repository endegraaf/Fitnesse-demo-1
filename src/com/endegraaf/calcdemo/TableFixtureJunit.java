package com.endegraaf.calcdemo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TableFixtureJunit {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		TableFixtureTest tft = new TableFixtureTest();
		
		double temp = tft.GetTotalTax();
		
		assertEquals(temp,0.0);
		
		
	}

}
