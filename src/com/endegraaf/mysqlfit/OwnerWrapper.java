package com.endegraaf.mysqlfit;

import java.util.ArrayList;
import java.util.List;

public class OwnerWrapper {

//	private List<String> list;
//	
//	public List<String> AddListItem(String myItem){
//		List<String> list = new ArrayList<>();
//		list.add(myItem);
//		return list;
//	}
//
//
//	private List<String> list1;
	
	public List<List<List<String>>> AddStringItemToList(String[] stringArray){

		List<String> list1 = new ArrayList<>();
		for (int i = 0; i < stringArray.length; i++){
			list1.add(stringArray[i]);
		}
		List<List<String>> list2 = new ArrayList<>();

		list2.add(list1);
		
		List<List<List<String>>> list3 = new ArrayList<>();
		
		list3.add(list2);
		
		return list3;
	}

	
}
