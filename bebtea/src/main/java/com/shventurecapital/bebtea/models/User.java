package com.shventurecapital.bebtea.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Entity
@Table(name = "user")
public class User {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String name;
	
	private String address;
	
	private String flavor;
	
	private String size;
        
        private String quantity;
        
        private String amount;
        
        private String status;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
        
        public String getQuantity(){
            return quantity;
        }
        
        public void setQuantity(String quantity){
            this.quantity = quantity;
        }
	
        public String getAmount(){
            return amount;
        }
        
        public void setAmount(String amount){
            this.amount = amount;
        }
        
        public String getStatus(){
            return status;
        }
        
        public void setStatus(String status){
            this.status = status;
        }

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", address=" + address + ", flavor=" + flavor + ", size="+size+", quantity="+quantity+", amount="+amount+"]";
	}

	
}
