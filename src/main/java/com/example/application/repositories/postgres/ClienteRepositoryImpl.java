package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.application.entidades.Cliente;
import com.example.application.repositories.ClienteRepository;

public class ClienteRepositoryImpl implements ClienteRepository {

    public void inserir(Cliente cliente) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres", "2515");
            String sql = "INSERT INTO cliente (nome, email, cpf, endereco) VALUES (?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getEmail());
            pstm.setString(3, cliente.getCpf());
            pstm.setString(4, cliente.getEndereco());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }

    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres", "2515");
            String sql = "SELECT * FROM cliente;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente d = new Cliente();
                d.setId_cliente(rs.getInt("id_cliente"));
                d.setNome(rs.getString("nome"));
                d.setEmail(rs.getString("email"));
                d.setEndereco(rs.getString("endereco"));
                d.setCpf(rs.getString("cpf"));
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
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres", "2515");
            String sql = "DELETE FROM cliente where id_cliente=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public void editar(Cliente cliente) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres", "2515");
            String sql = "UPDATE cliente SET nome=?, email=?, cpf=?, endereco=? WHERE id_cliente=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getEmail());
            pstm.setString(3, cliente.getCpf());
            pstm.setString(4, cliente.getEndereco());
            pstm.setInt(5, cliente.getId_cliente());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public List<Cliente> contadorClientes() {
        List<Cliente> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres", "2515");
            String sql = "SELECT COUNT(nome) FROM produto;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente d = new Cliente();
                d.setNome(rs.getString("nome"));
                lista.add(d);
            }
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
        return lista;
    }

    public Cliente busca(String cf) {
        // List<Cliente> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres", "2515");
            Cliente cliente = new Cliente();
            String sql = "SELECT * FROM cliente WHERE cpf=?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, cf);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                return cliente;
            }
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
        return null;
    }

}
