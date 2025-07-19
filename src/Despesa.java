import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Despesa {
  private String idDespesa;
  private String nomeDespesa;
  private Double valor;
  private Date data;
  private Usuario usuario;
  private Categoria categoria;

  private static List<Despesa> listaDespesas = new ArrayList<>();

  // Construtor
  public Despesa(String idDespesa, String nomeDespesa, Double valor, Date data, Usuario usuario, Categoria categoria,
      ConecBanco banco) {
    this.idDespesa = idDespesa;
    this.nomeDespesa = nomeDespesa;
    this.valor = valor;
    this.data = data;
    this.usuario = usuario;
    this.categoria = categoria;

    listaDespesas.add(this);

    // Inserir no banco de dados
    String tabela = "despesas";
    String colunas = "id_despesas, id_usuario, data_inicio, data_fim";
    String valores = "'" + idDespesa + "', '" + usuario.getIdUsuario() + "', '" + new java.sql.Date(data.getTime())
        + "', '" + new java.sql.Date(data.getTime()) + "'";
    banco.inserir(tabela, colunas, valores);

    String tabelaIntermediaria = "categoriadespesas";
    String colunasInter = "id_despesas, id_categoria";
    String valoresInter = "'" + idDespesa + "', '" + categoria.getIdCategoria() + "'";
    banco.inserir(tabelaIntermediaria, colunasInter, valoresInter);
  }

  public boolean excluirDespesa(ConecBanco banco) {
    banco.deletar("categoriadespesas", "id_despesas = '" + idDespesa + "'");
    banco.deletar("despesas", "id_despesas = '" + idDespesa + "'");
    return listaDespesas.remove(this);
  }

  public Despesa visualizarDespesa() {
    return this;
  }

  public static List<Despesa> listarDespesa() {
    return listaDespesas;
  }

  public static Despesa buscarDespesa(String id) {
    for (Despesa d : listaDespesas) {
      if (d.getIdDespesa().equalsIgnoreCase(id)) {
        return d;
      }
    }
    return null;
  }

  public boolean editarDespesa(String novoNome, double novoValor, Date novaData, ConecBanco banco) {
    this.nomeDespesa = novoNome;
    this.valor = novoValor;
    this.data = novaData;

    String atualizacoes = "data_inicio = '" + new java.sql.Date(novaData.getTime()) + "', data_fim = '"
        + new java.sql.Date(novaData.getTime()) + "'";
    String condicao = "id_despesas = '" + idDespesa + "'";
    banco.atualizar("despesas", atualizacoes, condicao);
    return true;
  }

  // Getters e Setters

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

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  @Override
  public String toString() {
    return "Despesa:\n" +
        "- ID: " + idDespesa +
        "\n- Nome: " + nomeDespesa +
        "\n- Valor: " + valor +
        "\n- Data: " + data +
        "\n- Usuário: " + (usuario != null ? usuario.getNome() : "null") +
        "\n- Categoria: " + (categoria != null ? categoria.getNomeCategoria() : "null");
  }
}
