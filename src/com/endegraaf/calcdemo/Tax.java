package com.endegraaf.calcdemo;

public interface Tax {
	public String name();

	public Float getBurden(Float price);
}
