import java.sql.*;

public class ConecBanco {

    private String url;
    private String usuario;
    private String senha;

    public ConecBanco(String url, String usuario, String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    public void conectar() {
      
        try{
            Connection connection = DriverManager.getConnection(url, usuario, senha);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        }catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    /*CREATE TABLE categoria (
    id_categoria integer NOT NULL,
    nome_categoria text NOT NULL,
    CONSTRAINT categoria_pkey PRIMARY KEY (id_categoria)
    ); */

    public void inserir(String tabela, String colunas, String valores) {
        String sql = "INSERT INTO " + tabela + " (" + colunas + ") VALUES (" + valores + ")";
        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Data inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Insertion failed!");
            e.printStackTrace();
        }
    }


    public void desconectar() {
        try {
            DriverManager.getConnection(url, usuario, senha).close();
            System.out.println("Disconnected from the database!");
        } catch (SQLException e) {
            System.out.println("Disconnection failed!");
            e.printStackTrace();
        }
    }

}