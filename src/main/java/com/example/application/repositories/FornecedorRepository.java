package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Fornecedor;

public interface FornecedorRepository {
    public void inserir(Fornecedor fornecedor);

    public void editar(Fornecedor fornecedor);

    public void remover(int id);

    public List<Fornecedor> listar();
}
