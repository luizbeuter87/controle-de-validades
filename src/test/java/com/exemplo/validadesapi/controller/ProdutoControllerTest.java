package com.exemplo.validadesapi.controller;

import com.exemplo.validadesapi.model.Produto;
import com.exemplo.validadesapi.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Test
    void deveListarProdutos() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Leite");
        produto.setDataVencimento("2026-07-20");
        produto.setQuantidade(10);

        when(produtoService.listarTodos()).thenReturn(List.of(produto));

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Leite"));
    }
}
