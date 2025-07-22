import java.util.ArrayList;
import java.util.Date;

public class Despesa {
  private String idDespesa;
  private String nomeDespesa;
  private Date data;
  private Usuario usuario;
  private String idCategoria;
  private String idRelatorio;
  private Double valor;

  // Construtor
  public Despesa(String nomeDespesa, Double valor, Date data, Usuario usuario, String idCategoria, String idRelatorio,
      ConecBanco banco) {
    this.nomeDespesa = nomeDespesa;
    this.valor = valor;
    this.data = data;
    this.usuario = usuario;
    this.idCategoria = idCategoria;
    this.idRelatorio = idRelatorio;

    // Inserir no banco de dados
    String tabela = "despesas";
    String colunas = "nome_despesa, valor, data, id_usuario, id_categoria, id_relatorio";
    String valores = "'" + nomeDespesa + "', " + valor + ", '" + new java.sql.Date(data.getTime()) + "', '" +
        usuario.getId_usuario() + "', '" + idCategoria + "', '" + idRelatorio + "'";

    banco.inserir(tabela, colunas, valores);

    ArrayList<Object> resultados = banco.BuscarERetornar("despesas", "id_despesas",
        "nome_despesa = '" + nomeDespesa + "' and valor = " + valor + " and data = '"
            + new java.sql.Date(data.getTime()) +
            "' and id_usuario = '" + usuario.getId_usuario() + "' and id_categoria = '" + idCategoria
            + "' and id_relatorio = '" + idRelatorio + "'");

    if (resultados.size() > 0) {
      this.idDespesa = (String) resultados.get(0);
    } else {
      System.out.println("Erro ao inserir despesa no banco de dados.");
      return;
    }
  }

  public Despesa(String idDespesa, String nomeDespesa, Double valor, Date data, Usuario usuario, String idCategoria,
      String idRelatorio) {
    this.idDespesa = idDespesa;
    this.nomeDespesa = nomeDespesa;
    this.valor = valor;
    this.data = data;
    this.usuario = usuario;
    this.idCategoria = idCategoria;
    this.idRelatorio = idRelatorio;
  }

  public void excluirDespesa(ConecBanco banco) {
    banco.deletar("despesas", "id_despesas = '" + idDespesa + "'");
  }

  public Despesa visualizarDespesa() {
    return this;
  }

  public static void listarDespesas(ConecBanco banco) {
    banco.buscar("despesas", "id_despesas, nome_despesa, valor, data, id_usuario, id_categoria, id_relatorio", "");
  }

  public static Despesa buscarDespesa(ConecBanco banco, String id, Usuario usuario) {
    ArrayList<Object> resultados = banco.BuscarERetornar("despesas",
        "id_despesas, nome_despesa, valor, data, id_usuario, id_categoria, id_relatorio", "id_despesas = '" + id + "'");
    if (resultados.size() > 0) {
      String idDespesa = (String) resultados.get(0);
      String nomeDespesa = (String) resultados.get(1);
      Double valor = (Double) resultados.get(2);
      Date data = (Date) resultados.get(3);
      String idCategoria = (String) resultados.get(5);
      String idRelatorio = (String) resultados.get(6);
      return new Despesa(idDespesa, nomeDespesa, valor, data, usuario, idCategoria, idRelatorio);
    }
    return null;
  }

  public boolean editarDespesa(String novoNome, Double novoValor, Date novaData, String novaCategoria,
      String novoRelatorio, ConecBanco banco) {
    this.nomeDespesa = novoNome;
    this.valor = novoValor;
    this.data = novaData;
    this.idCategoria = novaCategoria;
    this.idRelatorio = novoRelatorio;

    String atualizacoes = "nome_despesa = '" + novoNome + "', valor = " + novoValor + ", data = '"
        + new java.sql.Date(novaData.getTime()) +
        "', id_categoria = '" + novaCategoria + "', id_relatorio = '" + novoRelatorio + "'";
    String condicao = "id_despesas = '" + idDespesa + "'";
    banco.atualizar("despesas", atualizacoes, condicao);
    return true;
  }

  public String getIdDespesa() {
    return idDespesa;
  }

  public void setIdDespesa(String idDespesa) {
    this.idDespesa = idDespesa;
  }

  public String getNomeDespesa() {
    return nomeDespesa;
  }

  public void setNomeDespesa(String nomeDespesa) {
    this.nomeDespesa = nomeDespesa;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public String getCategoria() {
    return idCategoria;
  }

  public void setCategoria(String categoria) {
    this.idCategoria = categoria;
  }

  @Override
  public String toString() {
    return "Despesa:\n" +
        "- ID: " + idDespesa +
        "\n- Nome: " + nomeDespesa +
        "\n- Valor: " + valor +
        "\n- Data: " + data +
        "\n- Usuário: " + (usuario != null ? usuario.getNome() : "null") +
        "\n- Categoria: " + idCategoria +
        "\n- Relatório: " + idRelatorio;
  }

  public static void listarDespesas(ConecBanco banco, Usuario usuario) {

    banco.buscar("despesas", "id_despesas, nome_despesa, valor, data, id_usuario, id_categoria, id_relatorio",
        "id_usuario = '" + usuario.getId_usuario() + "'");
  }

  public static double despesaTotalMensal(int mes, int ano, ConecBanco banco, Usuario usuario) {

    String mesStr = String.format("%02d", mes);
    String condicao = "id_usuario = '" + usuario.getId_usuario() + "' AND TO_CHAR(data, 'YYYY-MM') = '" + ano + "-" + mesStr + "'";
    ArrayList<Object> resultados = banco.BuscarERetornar("despesas", "SUM(valor)", condicao);

    if (resultados.size() > 0 && resultados.get(0) != null && !resultados.get(0).toString().isEmpty()) {
      return Double.parseDouble(resultados.get(0).toString());
    }
    return 0.0;
  }

}