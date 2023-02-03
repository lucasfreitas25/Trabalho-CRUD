package com.example.application.repositories.postgres;

import com.example.application.entidades.Venda;
import com.example.application.repositories.VendaRepository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class VendaRepositoryImpl implements VendaRepository {

    public void inserir(Venda venda) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/venda", "postgres", "2515");
            String sql = "INSERT INTO venda (cpf, pedido, valortotal, entrega, data) VALUES (?,?,?,?,?);";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, venda.getCpf());
            pstm.setString(2, venda.getPedido());
            pstm.setDouble(3, venda.getValortotal());
            pstm.setString(4, venda.getEntrega());
            pstm.setDate(5, Date.valueOf(venda.getData()));
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }

    }

    public List<Venda> listar() {
        List<Venda> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/venda", "postgres", "2515");
            String sql = "SELECT * FROM venda;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Venda d = new Venda();
                d.setId_venda(rs.getInt("id_venda"));
                d.setCpf(rs.getString("cpf"));
                d.setPedido(rs.getString("pedido"));
                d.setValortotal(rs.getDouble("valortotal"));
                d.setEntrega(rs.getString("entrega"));
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
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/venda", "postgres", "2515");
            String sql = "DELETE FROM venda where id_venda=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public void editar(Venda venda) {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/venda", "postgres", "2515");
            String sql = "UPDATE Venda SET cpf=?, pedido=?, valortotal=?, entrega=?, data=? WHERE id_venda=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, venda.getCpf());
            pstm.setString(2, venda.getPedido());
            pstm.setDouble(3, venda.getValortotal());
            pstm.setString(4, venda.getEntrega());
            pstm.setDate(5, Date.valueOf(venda.getData()));
            pstm.setInt(6, venda.getId_venda());
            pstm.execute();
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    }

    public boolean verificador(String cp) {
        // List<Cliente> lista = new ArrayList<>();
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres", "2515");
            // Cliente cliente = new Cliente();
            String sql = "SELECT cpf FROM cliente WHERE cpf=?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, cp);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
            con.close();
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
        return false;
    }

}
