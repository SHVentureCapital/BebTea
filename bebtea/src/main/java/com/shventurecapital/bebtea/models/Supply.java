package com.shventurecapital.bebtea.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "supply")
public class Supply {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String item;
	
	private String size;
        
        private String quantity;
        

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", Item=" + item + ", size="+size+", quantity="+quantity+"]";
	}
}
