package com.shopping_control.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "categories", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "market_id"})
})
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "market_id")
    private Market market;

    protected Category() {}

    public Category(String name, Market market) {
        this.name = name;
        this.market = market;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Market getMarket() {
        return market;
    }

}
