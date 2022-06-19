package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection iniciaConexao(){
        Connection connection = null;
        try {
            connection =
                    DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/loja_mestre_andre?useTimezone=true&serverTimezone=UTC","root","lucca");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void fechaConexao(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
