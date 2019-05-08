package com.rest.restservice.controller.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * @author Lakha Singh
 *
 */
@Entity
@Table(name="user")
@ApiModel(description="This all about user details")
public class User implements Serializable {

	private static final long serialVersionUID = -7480176614623247571L;
	@Id
	@GeneratedValue
	private int userId;
	@Size(min=2,message="Name sholud have been greater than two charater.")
	@ApiModelProperty(notes="Name sholud have been greater than two charater")
	private String name;
	@Past
	private Date dob;
	public User() {
		
	}
	public User(int userId, String name, Date dob) {
		super();
		this.userId = userId;
		this.name = name;
		this.dob = dob;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
