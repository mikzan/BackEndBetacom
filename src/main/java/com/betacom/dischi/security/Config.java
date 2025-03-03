package com.betacom.dischi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class Config {
    
   
    
    // Bean per la codifica delle password
    @Bean
    PasswordEncoder passwordEncoder() {
        // Restituisce un codificatore di password delegante (utilizza vari algoritmi)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
