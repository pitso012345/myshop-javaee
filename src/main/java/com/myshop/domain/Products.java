package com.myshop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Products 
{
	private List<Product> list;

	// This default constructor is required if there are other constructors.
	public Products() {
		list = new ArrayList<Product>();
	}

	public Products(List<Product> list) {
		this.list = list;
	}

	@XmlElement(name="product")
	public List<Product> getList() { return list; }

	public void setList(List<Product> list) { this.list = list; }
	
}
