import java.util.ArrayList;
import java.util.Date;

public class Renda {
    private String idRenda;
    private String nomeRenda;
    private Double valor;
    private Date data;
    private boolean tipoRenda;


    public Renda(String nomeRenda, Double valor, Date data, boolean tipoRenda, Usuario usuario, ConecBanco banco) {
        this.nomeRenda = nomeRenda;
        this.valor = valor;
        this.data = data;
        this.tipoRenda = tipoRenda;

        // Inserir no banco de dados
        String tabela = "renda";
        String colunas = "nome_renda, valor, data, tipo_renda, id_usuario";
        String valores = "'" + nomeRenda + "', " + valor + ", '" +
                new java.sql.Date(data.getTime()) + "', " + tipoRenda + ", '" + usuario.getId_usuario() + "'";
            
        banco.inserir(tabela, colunas, valores);

        ArrayList<Object> resultados = banco.BuscarERetornar("renda", "id_renda",
                "nome_renda = '" + nomeRenda + "' and valor = " + valor + " and data = '"
                        + new java.sql.Date(data.getTime()) +
                        "' and tipo_renda = " + tipoRenda + " and id_usuario = '" + usuario.getId_usuario() + "'");
        if (resultados.size() > 0) {
            this.idRenda = (String) resultados.get(0);
        } else {
            System.out.println("Erro ao inserir renda no banco de dados.");
            return;
        }
    }

    public boolean editarRenda(String novoNome, Double novoValor, Date novaData, boolean novoTipo, ConecBanco banco) {
        this.nomeRenda = novoNome;
        this.valor = novoValor;
        this.data = novaData;
        this.tipoRenda = novoTipo;

        String atualizacoes = "nome_renda = '" + novoNome + "', valor = " + novoValor +
                ", data = '" + new java.sql.Date(novaData.getTime()) + "', tipo_renda = " + novoTipo;
        String condicao = "id_renda = '" + idRenda + "'";
        banco.atualizar("renda", atualizacoes, condicao);
        return true;
        
    }

    public void excluirRenda(ConecBanco banco) {
        banco.deletar("renda", "id_renda = '" + idRenda + "'");
    }

    public Renda visualizarRenda() {
        return this;
    }

    public static void listarRenda(ConecBanco banco, Usuario user) {
        banco.buscar("renda", "id_renda, nome_renda, valor, data, tipo_renda", "id_usuario = '" + user.getId_usuario() + "'");
    }


    // Calculo da renda total
    public static double rendaTotalMensal(int mes, int ano, ConecBanco banco, Usuario usuario) {
        double total = 0.0;
        String mesStr = String.format("%02d", mes);
        String condicao = "TO_CHAR(data, 'YYYY-MM') = '" + ano + "-" + mesStr + "' and id_usuario = '" + usuario.getId_usuario() + "'";
        ArrayList<Object> resultados = banco.BuscarERetornar("renda", "SUM(valor)", condicao);

        if (resultados.size() > 0 && resultados.get(0) != null && !resultados.get(0).toString().isEmpty()) {
            System.err.println("Resultados encontrados: " + resultados.size() + " - " + resultados.get(0));
            total = Double.parseDouble(resultados.get(0).toString());
        }
        return total;
    }

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
