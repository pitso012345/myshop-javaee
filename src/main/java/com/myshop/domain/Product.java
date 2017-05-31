package com.myshop.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	private String name;
	private String description;
	private Float price;

	// This default constructor is required if there are other constructors.
	public Product() {}
	
	public Product(String name, String description, Float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() { return price; }
	public void setPrice(Float price) { this.price = price; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
