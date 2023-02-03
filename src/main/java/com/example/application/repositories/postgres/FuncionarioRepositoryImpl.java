package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Funcionario;
import com.example.application.repositories.FuncionarioRepository;

public class FuncionarioRepositoryImpl implements FuncionarioRepository {

    public void inserir(Funcionario funcionario) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/funcionario", "postgres", "2515");
            String sql = "INSERT INTO funcionario (nome, categoria, cpf, salario) VALUES (?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, funcionario.getNome());
            pstm.setString(2, funcionario.getCategoria());
            pstm.setString(3, funcionario.getCPF());
            pstm.setDouble(4, funcionario.getSalario());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }

    }

    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/funcionario", "postgres", "2515");
            String sql = "SELECT * FROM funcionario;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Funcionario d = new Funcionario();
                d.setId_funcionario(rs.getInt("id_funcionario"));
                d.setNome(rs.getString("nome"));
                d.setCategoria(rs.getString("categoria"));
                d.setCPF(rs.getString("cpf"));
                d.setSalario(rs.getDouble("salario"));
                lista.add(d);
            }
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
        return lista;
    }

    public void remover(int id) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/funcionario", "postgres", "2515");
            String sql = "DELETE FROM funcionario where id_funcionario=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public void editar(Funcionario funcionario) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/funcionario", "postgres", "2515");
            String sql = "UPDATE funcionario SET nome=?, categoria=?, cpf=?, salario=? WHERE id_funcionario=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, funcionario.getNome());
            pstm.setString(2, funcionario.getCategoria());
            pstm.setString(3, funcionario.getCPF());
            pstm.setDouble(4, funcionario.getSalario());
            pstm.setInt(5, funcionario.getId_funcionario());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

}
