package com.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name", length = 255)
	private String name;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@Column(name = "price")
	private double price;

	@Column(name = "sale")
	private int sale;

	@Column(name = "view")
	private int view;

	@Column(name = "image1")
	private String image1;

	@Column(name = "image2")
	private String image2;

	@Column(name = "image3")
	private String image3;

	@Column(name = "image4")
	private String image4;

	@Column(name = "oncreate")
	private Date oncreate;

	@Column(name = "onupdate")
	private Date onupdate;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private String status;
	
	@Column(name = "is_enable")
	private int is_enable;

}
