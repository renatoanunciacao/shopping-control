package com.shopping_control.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;



@Component
public class CustomJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>>{

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> plans = jwt.getClaimAsStringList("plans");

        if(plans == null) {
           return List.of();
        }
       
        List<GrantedAuthority> authorities = plans.stream()
            .map(plan -> new SimpleGrantedAuthority("PLAN_" + plan))
            .collect(Collectors.toList());

        return authorities;
      
    }

} 
    
