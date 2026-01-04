package com.shopping_control.entity;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Market {
    
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    @CNPJ
    private String cnpj;

    @ManyToOne
    private User user;

    protected Market() {}

    public Market(String name, String cnpj, User user) {
        this.name = name;
        this.cnpj = cnpj;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public User getUser() {
        return user;
    }




}
