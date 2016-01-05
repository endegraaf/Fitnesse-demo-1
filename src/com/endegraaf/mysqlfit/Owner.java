package com.endegraaf.mysqlfit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import static java.util.Arrays.asList;

public class Owner {
	
	/*
	 * 10,
          @p_first_name,
          @p_last_name, 
          @p_address,
          @p_city,
          @p_telephone
	 */
	private int id;
	private String first_name;
	private String last_name;
	private String address;
	private String city;
	private String telephone;
	
	public Owner(int id){
		setId(id);
	}
	
	/*asList
	 * @parameters id, firstName, lastName, address, city, telephone
	 */
	public List<List<List<String>>> NewOwner(String firstName, String lastName,
			String address, String city, String telephone) {
		
		return asList(
					asList(
					asList("first name",firstName),
					asList("last name",lastName),
					asList("address",address)
					)
				);
	}

	/* 
	 * Getters and setters
	 */
	public int getId() {
		return id;
	}

	public void setId(int id2) {
		this.id = id2;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setId(long long1) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String string) {
		this.first_name = string;		
	}

	public void setValue(String string) {
		// TODO Auto-generated method stub
		
	}

	
	

}