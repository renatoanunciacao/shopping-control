package com.shopping_control.dto;

public class CreateMarketResponse {
    
    private Long id;
    private String name;
    private String cnpj;
    private Long userId;

    public CreateMarketResponse(Long id, String name, String cnpj, Long userId) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

}
