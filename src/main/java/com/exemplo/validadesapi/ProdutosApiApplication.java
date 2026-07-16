package com.exemplo.validadesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Ativa a configuração automática e o escaneamento [1, 18]
public class ProdutosApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdutosApiApplication.class, args); // Inicia o Spring [18]
    }
}