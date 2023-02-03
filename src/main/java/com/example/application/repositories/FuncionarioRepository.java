package com.example.application.repositories;

import java.util.List;

import com.example.application.entidades.Funcionario;

public interface FuncionarioRepository {
    public void inserir(Funcionario funcionario);

    public void editar(Funcionario funcionario);

    public void remover(int id);

    public List<Funcionario> listar();
}
