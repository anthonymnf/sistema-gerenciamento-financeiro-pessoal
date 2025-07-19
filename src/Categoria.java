import java.util.ArrayList;
import java.util.List;

public class Categoria {
  private String idCategoria;
  private String nomeCategoria;

  private static List<Categoria> listaCategorias = new ArrayList<>();

  public Categoria(String idCategoria, String nomeCategoria, ConecBanco banco) {
    this.idCategoria = idCategoria;
    this.nomeCategoria = nomeCategoria;
    listaCategorias.add(this);

    String tabela = "categoria";
    String colunas = "id_categoria, nome_categoria";
    String valores = "'" + idCategoria + "', '" + nomeCategoria + "'";
    banco.inserir(tabela, colunas, valores);
  }

  public boolean editarCategoria(String novoNome, ConecBanco banco) {
    this.nomeCategoria = novoNome;
    String atualizacoes = "nome_categoria = '" + novoNome + "'";
    String condicao = "id_categoria = '" + idCategoria + "'";
    banco.atualizar("categoria", atualizacoes, condicao);
    return true;
  }

  public boolean excluirCategoria(ConecBanco banco) {
    banco.deletar("categoria", "id_categoria = '" + idCategoria + "'");
    return listaCategorias.remove(this);
  }

  public Categoria visualizarCategoria() {
    return this;
  }

  public static List<Categoria> listarCategoria() {
    return listaCategorias;
  }

  public static Categoria buscarCategoria(String id) {
    for (Categoria c : listaCategorias) {
      if (c.getIdCategoria().equalsIgnoreCase(id)) {
        return c;
      }
    }
    return null;
  }

  public String getIdCategoria() {
    return idCategoria;
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
        "id='" + idCategoria + '\'' +
        ", nome='" + nomeCategoria + '\'' +
        '}';
  }
}
