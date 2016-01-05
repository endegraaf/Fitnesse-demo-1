package com.endegraaf.mysqlfit;

import java.util.ArrayList;
import java.util.List;

public class OwnerWrapper {


	/*
	 * Class to make the array lists that can be used to compare the Fitnesse
	 * Query Table page.
	 */
	public List<List<List<String>>> AddStringItemToList(String string) {

		// Contains string array
		// Attribute and attribute value
		List<String> list1 = new ArrayList<>();
		list1.add(string);
	
		// List to hold forementioned array
		List<List<String>> list2 = new ArrayList<>();
		list2.add(list1);

		// One extra hold that list.
		List<List<List<String>>> list3 = new ArrayList<>();
		list3.add(list2);

		return list3;
	}

	
	
	
}
