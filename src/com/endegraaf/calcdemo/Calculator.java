package com.endegraaf.calcdemo;

public class Calculator {

	private static Calculator objCalculator = null;

	private Calculator() {

	}

	public static Calculator getInstance() {
		if (objCalculator == null) {
			objCalculator = new Calculator();
		}
		return objCalculator;
	}

	public int Multiply(int a, int b) {
		int val = 0;
		val = a * b;
		return val;
	}

	public int Addition(int num1, int num2) {
		return num1 - num2;
	}

}
