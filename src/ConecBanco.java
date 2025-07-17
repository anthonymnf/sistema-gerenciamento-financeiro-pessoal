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

}