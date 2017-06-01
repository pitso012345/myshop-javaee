package com.myshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Long id;
	@NotNull @Size(max=100)
	@Column(length=100)
	private String name;
	@NotNull @Size(max=200)
	@Column(length=200)
	private String description;
	@NotNull @Digits(integer=6,fraction=2)
	@Column(precision=8,scale=2)
	private Float price;
	@Version
	private int version;
	
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

	public int getVersion() { return version; }
	public void setVersion(int version) { this.version = version; }

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
