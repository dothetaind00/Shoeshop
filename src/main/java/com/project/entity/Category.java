package com.project.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", length = 255)
	private String name;
	
	@Column(name = "isdisplay")
	private int isdisplay;
	
	@Column(name = "position")
	private int position;
	
	
	public Category() {
		super();
	}


	public Category(Integer id, String name, int isdisplay, int position) {
		super();
		this.id = id;
		this.name = name;
		this.isdisplay = isdisplay;
		this.position = position;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getIsdisplay() {
		return isdisplay;
	}


	public void setIsdisplay(int isdisplay) {
		this.isdisplay = isdisplay;
	}


	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}
	
	
	
	

	


}
