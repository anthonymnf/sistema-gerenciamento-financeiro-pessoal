package scr;
import java.util.ArrayList;
import java.util.List;

public class Categoria {
  private String idCategoria;
  private String nomeCategoria;

  // Lista estática para armazenar todas as categorias criadas
  private static List<Categoria> listaCategorias = new ArrayList<>();

  // Construtor
  public Categoria(String idCategoria, String nomeCategoria) {
    this.idCategoria = idCategoria;
    this.nomeCategoria = nomeCategoria;

    // Adiciona automaticamente à lista
    listaCategorias.add(this);
  }

  // Método para editar categoria
  public boolean editarCategoria(String novoNome) {
    this.nomeCategoria = novoNome;
    return true;
  }

  // Método para excluir a categoria (remove da lista)
  public boolean excluirCategoria() {
    return listaCategorias.remove(this);
  }

  // Método para visualizar (retornar a própria categoria)
  public Categoria visualizarCategoria() {
    return this;
  }

  // Método estático para listar todas as categorias
  public static List<Categoria> listarCategoria() {
    return listaCategorias;
  }

  // Método estático para buscar uma categoria por ID
  public static Categoria buscarCategoria(String id) {
    for (Categoria c : listaCategorias) {
      if (c.getIdCategoria().equalsIgnoreCase(id)) {
        return c;
      }
    }
    return null;
  }

  // Getters
  public String getIdCategoria() {
    return idCategoria;
  }

  public String getNomeCategoria() {
    return nomeCategoria;
  }

  // Setters
  public void setNomeCategoria(String nomeCategoria) {
    this.nomeCategoria = nomeCategoria;
  }

  // Método toString para facilitar visualização
  @Override
  public String toString() {
    return "Categoria{" +
        "id='" + idCategoria + '\'' +
        ", nome='" + nomeCategoria + '\'' +
        '}';
  }
}
