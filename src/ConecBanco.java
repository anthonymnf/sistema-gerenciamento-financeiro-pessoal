import java.sql.*;
import java.util.ArrayList;
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

    public void buscar(String tabela, String parametros, String condicao) {
        if (condicao.isEmpty()) {
            condicao = "1=1";
        }
        String sql = "SELECT " + parametros + " FROM " + tabela + " WHERE " + condicao + ";";
        //System.err.println("Executing SQL: " + sql);

        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            String[] param = parametros.split("\\s*,\\s*");

            while (resultSet.next()) {
                for (String p : param) {
                    Object valor = resultSet.getObject(p);
                    System.out.print(valor + " ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        }
    }

    public boolean buscarBoolean(String tabela, String condicao) {
        if (condicao.isEmpty()) {
            condicao = "1=1";
        }

        String sql = "SELECT 1 FROM " + tabela + " WHERE " + condicao + " LIMIT 1";

        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            return resultSet.next();  // se existe pelo menos um resultado, retorna true

        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        }

        return false;
    }


    public ArrayList<Object> BuscarERetornar(String tabela, String parametros, String condicao) {
        if (condicao.isEmpty()) {
            condicao = "1=1";
        }
        String sql = "SELECT " + parametros + " FROM " + tabela + " WHERE " + condicao + " limit 1;";
        
        ArrayList<Object> resultados = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            
            String[] param = parametros.split("\\s*,\\s*");
            if (resultSet.next()) {
                for (String p : param) {
                    Object valor = resultSet.getObject(p);
                    resultados.add(valor != null ? valor.toString() : "");
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        }
        return resultados;
    }


    public void deletar(String tabela, String condicao) {
        String sql = "DELETE FROM " + tabela + " WHERE " + condicao;
        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Deletion failed!");
            e.printStackTrace();
        }
    }

    public void atualizar(String tabela, String atualizacoes, String condicao) {
        String sql = "UPDATE " + tabela + " SET " + atualizacoes + " WHERE " + condicao;
        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Data updated successfully!");
        } catch (SQLException e) {
            System.out.println("Update failed!");
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