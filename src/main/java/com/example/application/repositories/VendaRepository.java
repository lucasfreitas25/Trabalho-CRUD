package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Venda;

public interface VendaRepository {
    public void inserir(Venda venda);

    public void editar(Venda venda);

    public void remover(int id);

    public List<Venda> listar();

    public boolean verificador(String cp);
}
