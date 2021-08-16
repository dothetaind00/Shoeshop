package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "user_id")
    private Integer user;

	public Cart(Integer totalAmount, Double totalCost, Integer user) {
		super();
		this.totalAmount = totalAmount;
		this.totalCost = totalCost;
		this.user = user;
	}
    
    
}
