package com.shopping_control.entity;

import java.util.HashSet;
import java.util.Set;

import com.shopping_control.entity.enums.AuthProvider;

import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_plans", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "plan_id"))
    private Set<Plan> plans = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    // Construtor vazio obrigatório para JPA
    protected User() {
    }

    public User(String name, String email, String password, AuthProvider provider) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true;
        this.provider = provider;
    }

    // Getters (evite setters indiscriminados)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void deactivate() {
        this.active = false;
    }

    public Set<Plan> getPlans() {
        return plans;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

}
