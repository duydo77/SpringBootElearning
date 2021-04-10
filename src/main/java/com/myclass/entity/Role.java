package com.myclass.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", length = 30)
	private String name;

	@Column(name = "description")
	private String desc;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<User> users;
	
	public Role() {
	}

	public Role(int id, String name, String desc) {
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	
	public Role(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
