package Loja;

import Connections.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaDAO {
        private Connection connection;
    public OfertaDAO(Connection connection){
        this.connection = connection;
    }

    public void adiciona(Oferta oferta){

        String comando = "INSERT INTO PRODUTOS (NOME, DESCRICAO, DESCONTO, PRECO, DATA_INICIO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS)){

            pstm.setString(1,oferta.getNome());
            pstm.setString(2,oferta.getDescricao());
            pstm.setDouble(3,oferta.getDesconto());
            pstm.setDouble(4,oferta.getPreco());
            pstm.setDate(5,oferta.getData());

            pstm.execute();
            try(ResultSet rst = pstm.getGeneratedKeys()) {
                while (rst.next()) {
                    oferta.setID(rst.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void adicionaID(Oferta oferta, int id){

        String comando = "INSERT INTO PRODUTOS (ID, NOME, DESCRICAO, DESCONTO, PRECO, DATA_INICIO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS)){
            pstm.setInt(1,id);
            pstm.setString(2,oferta.getNome());
            pstm.setString(3,oferta.getDescricao());
            pstm.setDouble(4,oferta.getDesconto());
            pstm.setDouble(5,oferta.getPreco());
            pstm.setDate(6,oferta.getData());

            pstm.execute();
            try(ResultSet rst = pstm.getGeneratedKeys()) {
                while (rst.next()) {
                    oferta.setID(rst.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void remove(int id){
        try (PreparedStatement pstm = connection.prepareStatement("DELETE FROM PRODUTOS WHERE ID = ?")){
            pstm.setInt(1,id);
            pstm.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void atualiza(Oferta oferta){
        try (PreparedStatement pstm = connection
                .prepareStatement("UPDATE PRODUTOS P SET P.NOME = ?, P.DESCRICAO = ?, P.DESCONTO= ?, P.PRECO = ?, P.DATA_INICIO = ? WHERE ID = ?")) {
            pstm.setString(1, oferta.getNome());
            pstm.setString(2, oferta.getDescricao());
            pstm.setDouble(3,oferta.getDesconto());
            pstm.setDouble(4,oferta.getPreco());
            pstm.setDate(5,oferta.getData());
            pstm.setInt(6, oferta.getId());
            pstm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Oferta> lista(){
        List<Oferta> ofertas = new ArrayList<Oferta>();
        String comando = "SELECT * FROM PRODUTOS";
        try (PreparedStatement pstm = connection.prepareStatement(comando)){
            pstm.execute();
            try(ResultSet rst = pstm.getResultSet()) {
                while (rst.next()) {
                    Oferta oferta = new Oferta(rst.getInt(1),rst.getString(2), rst.getString(3),
                            rst.getDouble(4),rst.getDouble(5),rst.getDate(6));
                    ofertas.add(oferta);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ofertas;
    }

    public Oferta busca(int id) {
        Oferta oferta = null;
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM PRODUTOS WHERE ID = ?")) {
            pstm.setInt(1, id);
            pstm.execute();
            try(ResultSet rst = pstm.getResultSet()) {
                while (rst.next()) {
                    oferta = new Oferta(rst.getInt(1),rst.getString(2), rst.getString(3),
                            rst.getDouble(4),rst.getDouble(5),rst.getDate(6));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return oferta;
    }
}
