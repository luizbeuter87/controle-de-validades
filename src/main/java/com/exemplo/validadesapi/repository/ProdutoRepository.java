package com.exemplo.validadesapi.repository;

import com.exemplo.validadesapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca como componente de acesso a dados [7, 8]
public interface ProdutoRepository extends JpaRepository<Produto, Long> { 
    // O JpaRepository já fornece métodos como save(), findAll() e deleteById() [7, 9]
}