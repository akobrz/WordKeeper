package com.wordkeeper.app.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student extends AbstractEntity implements Serializable {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Boolean logged;
	private LocalDate login_date;
	private long section;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getLogged() {
		return logged;
	}
	public void setLogged(Boolean logged) {
		this.logged = logged;
	}
	public LocalDate getLogin_date() {
		return login_date;
	}
	public void setLogin_date(LocalDate login_date) {
		this.login_date = login_date;
	}
	public long getSection() {
		return section;
	}
	public void setSection(long section) {
		this.section = section;
	}

}
