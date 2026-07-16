package com.exemplo.validadesapi.controller;

import com.exemplo.validadesapi.model.Produto;
import com.exemplo.validadesapi.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvarProduto(@RequestBody Produto produto) {
        return produtoService.salvar(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluir(id);
    }
}