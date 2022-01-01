package com.wordkeeper.app.entities;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Section extends AbstractEntity implements Serializable {
	private String value;
	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Section [value=" + value + "]";
	}	
	
	

}
