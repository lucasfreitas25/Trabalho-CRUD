package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Cliente;

public interface ClienteRepository {
    public void inserir(Cliente cliente);

    public void editar(Cliente cliente);

    public void remover(int id);

    public List<Cliente> listar();

    public List<Cliente> contadorClientes();

}
