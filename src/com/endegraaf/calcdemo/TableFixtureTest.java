package com.endegraaf.calcdemo;

import fitnesse.fixtures.TableFixture;

public class TableFixtureTest extends TableFixture {
	
	/* 
	 * Variables used in page.
	 */
	private String item;
	private String prodCod;
	private String price;
	private double totaltax;
	

	// Setters
	public void setItem(String item) {
		this.item = item;
	}
	public void setProductCode(String prodCod){
		this.prodCod = prodCod;
	}
	public void setPrice(String price){
		this.price = price;
	}
	// Getters
	public double GetTotalTax(){
		return totaltax;
	}
	
	
	protected void doStaticTable(int rows) {
		//TaxCalculator tc = new TaxCalculator();
		double totaltax = 0;

		for (int row = 1; row < rows - 3; row++) {
			totaltax += Double.parseDouble(getText(row, 2));
		}
		double taxintable = Double.parseDouble(getText(rows - 2, 2));
		if (taxintable == totaltax)
			right(rows - 2, 2);
		else
			wrong(rows - 2, 2, String.valueOf(totaltax));
	}
	
	private double ParseTableRow(int row){
		
		double columnValue = 0.0;
		int column = 2;
		
		columnValue = Double.parseDouble(getText(row,column));
				
		return columnValue;
		
	}

}









