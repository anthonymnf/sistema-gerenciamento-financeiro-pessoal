import java.util.ArrayList;
import java.util.Date;

public class Despesa {
  private String idDespesa;
  private String nomeDespesa;
  private Date dataInicio;
  private Date dataFim;
  private Usuario usuario;
  private String idCategoria;

  // Construtor
  public Despesa(String nomeDespesa, Double valor, Date dataI, Date dataF, Usuario usuario, String idCategoria, ConecBanco banco) {

    this.nomeDespesa = nomeDespesa;
    this.dataInicio = dataI;
    this.dataFim = dataF;
    this.usuario = usuario;
    this.idCategoria = idCategoria;

    // Inserir no banco de dados
    String tabela = "despesas";
    String colunas = "id_usuario, id_categoria, data_inicio, data_fim";
    String valores = "'" + usuario.getId_usuario() + "', '" + idCategoria + "', '" + new java.sql.Date(dataInicio.getTime())
        + "', '" + new java.sql.Date(dataFim.getTime()) + "'";

    System.err.println("sql: " + tabela + ", " + colunas + ", " + valores);
    banco.inserir(tabela, colunas, valores);

    ArrayList<Object> resultados = banco.BuscarERetornar("despesas", "id_despesas", "id_usuario = '" + usuario.getId_usuario()  + "' and id_categoria = '" + idCategoria + "'" + 
        " and data_inicio = '" + new java.sql.Date(dataInicio.getTime()) + "' and data_fim = '" + new java.sql.Date(dataFim.getTime()) + "'");

    if (resultados.size() > 0) {
      this.idDespesa = (String) resultados.get(0);
    } else {
      System.out.println("Erro ao inserir despesa no banco de dados.");
      return;
    }

    String tabelaIntermediaria = "categoriadespesas";
    String colunasInter = "id_despesas, id_categoria";
    String valoresInter = "'" + idDespesa + "', '" + idCategoria + "'";
    banco.inserir(tabelaIntermediaria, colunasInter, valoresInter);
  }

  public Despesa(String idDespesa, Usuario usuario, String idCategoria, Date dataInicio, Date dataFim) {
    this.idDespesa = idDespesa;
    this.usuario = usuario;
    this.idCategoria = idCategoria;
    this.dataInicio = dataInicio;
    this.dataFim = dataFim; 
  }

  public void excluirDespesa(ConecBanco banco) {
    banco.deletar("categoriadespesas", "id_despesas = '" + idDespesa + "'");
    banco.deletar("despesas", "id_despesas = '" + idDespesa + "'");
  }

  public Despesa visualizarDespesa() {
    return this;
  }

  public static void listarDespesas(ConecBanco banco) {
    
    banco.buscar("despesas", "id_despesas, id_usuario, id_categoria, data_inicio, data_fim", "");
  }

  public static Despesa buscarDespesa(ConecBanco banco, String id, Usuario usuario) {
    
    ArrayList<Object> resultados = banco.BuscarERetornar("despesas", "id_despesas, id_usuario, id_categoria, data_inicio, data_fim", "id_despesas = '" + id + "'");
    
    if (resultados.size() > 0) {
      String idDespesa = (String) resultados.get(0);
      String idCategoria = (String) resultados.get(2);
      Date dataInicio = (Date) resultados.get(3);
      Date dataFim = (Date) resultados.get(4);
      return new Despesa(idDespesa, usuario, idCategoria, dataInicio, dataFim);
    }
    return null;
  }

  public boolean editarDespesa(String novoNome, Date novaDataInicio, Date novaDataFim, ConecBanco banco) {
    this.nomeDespesa = novoNome;
    this.dataInicio = novaDataInicio;
    this.dataFim = novaDataFim;

    String atualizacoes = "data_inicio = '" + new java.sql.Date(
      novaDataFim.getTime()) + "', data_fim = '"
        + new java.sql.Date(dataFim.getTime()) + "'";
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
        "\n- Data Inicio: " + dataInicio +
        "\n- Data Fim: " + dataFim +
        "\n- Usuário: " + (usuario != null ? usuario.getNome() : "null") +
        "\n- Categoria: " + idCategoria;
  }
}
