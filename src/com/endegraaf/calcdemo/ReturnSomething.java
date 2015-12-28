package com.endegraaf.calcdemo;

import fitnesse.testsystems.slim.SlimTestContext;
import fitnesse.testsystems.slim.Table;
import fitnesse.testsystems.slim.tables.QueryTable;

public class ReturnSomething extends QueryTable{

	public ReturnSomething(Table table, String id, SlimTestContext testContext) {
		super(table, id, testContext);
		// TODO Auto-generated constructor stub
	}

	public int ReturnSomething(){
		return 4;
	}
	
}
