package com.endegraaf.calcdemo;

public class ABCD {

	private static ABCD objABCD = null;

	private ABCD() {

	}

	public static ABCD getInstance() {
		if (objABCD == null) {
			objABCD = new ABCD();
		}
		return objABCD;
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
