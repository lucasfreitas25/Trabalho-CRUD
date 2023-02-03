package com.example.application.repositories.postgres;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.example.application.entidades.Fornecedor;

import com.example.application.repositories.FornecedorRepository;

public class FornecedorRepositoryImpl implements FornecedorRepository {

    public void inserir(Fornecedor fornecedor) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fornecedor", "postgres", "2515");
            String sql = "INSERT INTO fornecedor (pedido, data) VALUES (?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, fornecedor.getPedido());
            pstm.setDate(2, Date.valueOf(fornecedor.getData()));
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }

    }

    public List<Fornecedor> listar() {
        List<Fornecedor> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fornecedor", "postgres", "2515");
            String sql = "SELECT * FROM fornecedor;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Fornecedor d = new Fornecedor();
                d.setId_fornecedor(rs.getInt("id_fornecedor"));
                d.setPedido(rs.getString("pedido"));
                d.setData(rs.getDate("data").toLocalDate());
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
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fornecedor", "postgres", "2515");
            String sql = "DELETE FROM fornecedor where id_fornecedor=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public void editar(Fornecedor fornecedor) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fornecedor", "postgres", "2515");
            String sql = "UPDATE fornecedor SET pedido=?, data=? WHERE id_fornecedor=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, fornecedor.getPedido());
            pstm.setDate(2, Date.valueOf(fornecedor.getData()));
            pstm.setInt(3, fornecedor.getId_fornecedor());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

}
