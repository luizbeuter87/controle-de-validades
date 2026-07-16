package com.exemplo.validadesapi.service;

import com.exemplo.validadesapi.model.Produto;
import com.exemplo.validadesapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
 * Camada de Serviço: Centraliza a lógica de negócio e validações.
 */
@Service // Marca a classe como um componente de serviço do Spring [1]
public class ProdutoService {

    @Autowired // Realiza a injeção automática do repositório [1]
    private ProdutoRepository repository;

    /**
     * Lista todos os produtos ordenados por data de vencimento.
     */
    public List<Produto> listarTodos() {
        // Você pode usar o método padrão do JPA para buscar todos os registros [4]
        return repository.findAll();
    }

    /**
     * Salva um produto aplicando validações de integridade.
     */
    public Produto salvar(Produto produto) {
        // Lógica de Negócio: Impede o cadastro de quantidades negativas [5]
        if (produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }

        // Lógica de Negócio: Validação de data (opcional)
        // Aqui poderíamos adicionar alertas se o produto já entrar no sistema vencido
        
        return repository.save(produto); // Persiste o objeto no banco via ORM [4, 6]
    }

    /**
     * Exclui um produto do inventário.
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}