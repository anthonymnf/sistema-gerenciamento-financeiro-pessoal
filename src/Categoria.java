import java.util.ArrayList;
public class Categoria {


  String idCategoria;
  private String nomeCategoria;

  public Categoria(String nomeCategoria, ConecBanco banco) {

    this.nomeCategoria = nomeCategoria;

    String tabela = "categoria";
    String colunas = "nome_categoria";
    String valores = "'" + nomeCategoria + "'";
    banco.inserir(tabela, colunas, valores);
  }

  public Categoria(String idCategoria, String nomeCategoria, ConecBanco banco) {
    this.idCategoria = idCategoria;
    this.nomeCategoria = nomeCategoria;

    String tabela = "categoria";
    String colunas = "id_categoria, nome_categoria";
    String valores = "'" + idCategoria + "', '" + nomeCategoria + "'";
    banco.inserir(tabela, colunas, valores);
  }

  public boolean editarCategoria(String novoNome, ConecBanco banco) {
    this.nomeCategoria = novoNome;
    String atualizacoes = "nome_categoria = '" + novoNome + "'";
    String condicao = "nome_categoria = '" + nomeCategoria + "'";
    banco.atualizar("categoria", atualizacoes, condicao);
    return true;
  }

  public void excluirCategoria(ConecBanco banco) {
    banco.deletar("categoria", "nome_categoria = '" + nomeCategoria + "'");
  }

  public Categoria visualizarCategoria() {
    return this;
  }

  public static void listarCategorias(ConecBanco banco) {
    banco.buscar("categoria", "id_categoria, nome_categoria", "");
  }

  public static void excluirCategoria(String nome, ConecBanco banco) {
    banco.deletar("categoria", "nome_categoria = '" + nome + "'");
  }

  public static Categoria buscarCategoria(ConecBanco banco, String idCategoria) {
    ArrayList<Object> resultados = banco.BuscarERetornar("categoria", "id_categoria, nome_categoria", "id_categoria = '" + idCategoria + "'");
    if (resultados.size() > 0) {
      String id = (String) resultados.get(0);
      String nome = (String) resultados.get(1);
      return new Categoria(id, nome, banco);
    }
    return null;
  }

  public static boolean atualizarCategoria(String idCategoria, String novoNome, ConecBanco banco) {
    String atualizacoes = "nome_categoria = '" + novoNome + "'";
    String condicao = "id_categoria = '" + idCategoria + "'";
    banco.atualizar("categoria", atualizacoes, condicao);
    return true;
  }

  public String getNomeCategoria() {
    return nomeCategoria;
  }

  public void setNomeCategoria(String nomeCategoria) {
    this.nomeCategoria = nomeCategoria;
  }

  @Override
  public String toString() {
    return "Categoria{" +
        ", nome='" + nomeCategoria + '\'' +
        '}';
  }
}
