package com.shopping_control.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateMarketRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String cnpj;

    //getters e setters
    public String name() {
        return name;
    }

    public String cnpj() {
        return cnpj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
}
