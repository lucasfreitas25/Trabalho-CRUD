package com.example.application.repositories.postgres;

import com.example.application.entidades.Produto;
import com.example.application.repositories.ProdutoRepository;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository {

    public void inserir(Produto produto) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/produto", "postgres", "2515");
            String sql = "INSERT INTO produto (nome, valor, codigo) VALUES (?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, produto.getNome());
            pstm.setDouble(2, produto.getValor());
            pstm.setString(3, produto.getCodigo());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }

    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/produto", "postgres", "2515");
            String sql = "SELECT * FROM produto;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Produto d = new Produto();
                d.setId_produto(rs.getInt("id_produto"));
                d.setNome(rs.getString("nome"));
                d.setValor(rs.getDouble("valor"));
                d.setCodigo(rs.getString("codigo"));
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
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/produto", "postgres", "2515");
            String sql = "DELETE FROM produto where id_produto=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public void editar(Produto produto) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/produto", "postgres", "2515");
            String sql = "UPDATE produto SET nome=?, valor=?, codigo=? WHERE id_produto=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, produto.getNome());
            pstm.setDouble(2, produto.getValor());
            pstm.setString(3, produto.getCodigo());
            pstm.setInt(4, produto.getId_produto());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }
}
