package com.endegraaf.calcdemo;

import fit.ColumnFixture;

public class ABCDFixture extends ColumnFixture {

	private int num1;
	private int num2;

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public int result() {
		return Calculator.getInstance().Addition(this.num1, this.num2);
	}
}
