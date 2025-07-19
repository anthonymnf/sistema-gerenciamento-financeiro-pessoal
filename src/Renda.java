package scr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Renda {
    private String idRenda;
    private String nomeRenda;
    private Double valor;
    private Date data;
    private boolean tipoRenda; // true = fixa, false = extra

    // Lista estática para armazenar todas as rendas
    private static List<Renda> listaRendas = new ArrayList<>();

    // Configurações do banco (pegas do seu código original)
    private static final String URL = "jdbc:postgresql://localhost:5432/Java";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "26042005";

    public Renda(String idRenda, String nomeRenda, Double valor, Date data, boolean tipoRenda) {
        this.idRenda = idRenda;
        this.nomeRenda = nomeRenda;
        this.valor = valor;
        this.data = data;
        this.tipoRenda = tipoRenda;

        listaRendas.add(this);
    }

    public boolean editarRenda(String novoNome, Double novoValor, Date novaData, boolean novoTipo) {
        this.nomeRenda = novoNome;
        this.valor = novoValor;
        this.data = novaData;
        this.tipoRenda = novoTipo;
        return true;
    }

    public boolean excluirRenda() {
        return listaRendas.remove(this);
    }

    public Renda visualizarRenda() {
        return this;
    }

    public static List<Renda> listarRenda() {
        return listaRendas;
    }

    // Calculo da renda total
    public static double rendaTotalMensal(int mes, int ano) {
        double total = 0.0;
        for (Renda r : listaRendas) {
            // Ajusta para java.util.Date que tem meses 0-11 e anos desde 1900
            @SuppressWarnings("deprecation")
            int mesData = r.data.getMonth();
            @SuppressWarnings("deprecation")
            int anoData = r.data.getYear() + 1900;

            if (mesData == mes - 1 && anoData == ano) {
                total += r.valor;
            }
        }
        return total;
    }

    // ----- NOVOS MÉTODOS PARA CONEXÃO COM BANCO -----

    // Carrega as rendas do banco para a lista estática
    public static void carregarDoBanco() {
        listaRendas.clear();

        String sql = "SELECT id_renda, nome_renda, valor, data, tipo_renda FROM renda";

        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id_renda"));
                String nome = rs.getString("nome_renda");
                Double valor = rs.getDouble("valor");
                java.sql.Date dataSql = rs.getDate("data");
                boolean tipo = rs.getBoolean("tipo_renda");

                // Converte java.sql.Date para java.util.Date
                Date data = new Date(dataSql.getTime());

                new Renda(id, nome, valor, data, tipo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao carregar rendas do banco:");
            e.printStackTrace();
        }
    }

    // Salva esta renda no banco (insere ou atualiza)
    public boolean salvarNoBanco() {
        String sqlInsert = "INSERT INTO renda (id_renda, nome_renda, valor, data, tipo_renda) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE renda SET nome_renda = ?, valor = ?, data = ?, tipo_renda = ? WHERE id_renda = ?";

        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            // Verifica se já existe no banco (por id_renda)
            String sqlCheck = "SELECT COUNT(*) FROM renda WHERE id_renda = ?";
            try (PreparedStatement pstCheck = con.prepareStatement(sqlCheck)) {
                pstCheck.setInt(1, Integer.parseInt(idRenda));
                ResultSet rs = pstCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // Existe, atualiza
                    try (PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate)) {
                        pstUpdate.setString(1, nomeRenda);
                        pstUpdate.setDouble(2, valor);
                        pstUpdate.setDate(3, new java.sql.Date(data.getTime()));
                        pstUpdate.setBoolean(4, tipoRenda);
                        pstUpdate.setInt(5, Integer.parseInt(idRenda));
                        pstUpdate.executeUpdate();
                    }
                } else {
                    // Não existe, insere
                    try (PreparedStatement pstInsert = con.prepareStatement(sqlInsert)) {
                        pstInsert.setInt(1, Integer.parseInt(idRenda));
                        pstInsert.setString(2, nomeRenda);
                        pstInsert.setDouble(3, valor);
                        pstInsert.setDate(4, new java.sql.Date(data.getTime()));
                        pstInsert.setBoolean(5, tipoRenda);
                        pstInsert.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar renda no banco:");
            e.printStackTrace();
            return false;
        }
    }

    // Remove esta renda do banco
    public boolean excluirDoBanco() {
        String sql = "DELETE FROM renda WHERE id_renda = ?";

        try (Connection con = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, Integer.parseInt(idRenda));
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                listaRendas.remove(this);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir renda do banco:");
            e.printStackTrace();
            return false;
        }
    }

    // ----------------------------------------------

    public String getIdRenda() {
        return idRenda;
    }

    public String getNomeRenda() {
        return nomeRenda;
    }

    public Double getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

    public boolean isTipoRenda() {
        return tipoRenda;
    }

    @Override
    public String toString() {
        return "Renda:\n- idRenda: " + idRenda + "\n- Nome: " + nomeRenda + "\n- Valor: " + valor + "\n- Data: " + data
                + "\n- Tipo: " + (tipoRenda ? "Fixa" : "Extra");
    }
}
