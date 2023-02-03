package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Produto;

public interface ProdutoRepository {
    public void inserir(Produto produto);

    public void editar(Produto produto);

    public void remover(int id);

    public List<Produto> listar();
}
