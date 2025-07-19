import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Relatorio {
  private String idRelatorio;
  private Date data;
  private String horario;
  private Usuario usuario;

  private static List<Relatorio> listaRelatorios = new ArrayList<>();

  public Relatorio(String idRelatorio, Date data, String horario, Usuario usuario, ConecBanco banco) {
    this.idRelatorio = idRelatorio;
    this.data = data;
    this.horario = horario;
    this.usuario = usuario;
    listaRelatorios.add(this);

    String tabela = "relatorio";
    String colunas = "id_relatorio, id_usuario, data, horario";
    String valores = "'" + idRelatorio + "', '" + usuario.getIdUsuario() + "', '" +
        new java.sql.Date(data.getTime()) + "', '" + horario + "'";
    banco.inserir(tabela, colunas, valores);
  }

  public boolean editarRelatorio(Date novaData, String novoHorario, ConecBanco banco) {
    this.data = novaData;
    this.horario = novoHorario;
    String atualizacoes = "data = '" + new java.sql.Date(novaData.getTime()) +
        "', horario = '" + novoHorario + "'";
    String condicao = "id_relatorio = '" + idRelatorio + "'";
    banco.atualizar("relatorio", atualizacoes, condicao);
    return true;
  }

  public boolean excluirRelatorio(ConecBanco banco) {
    banco.deletar("relatorio", "id_relatorio = '" + idRelatorio + "'");
    return listaRelatorios.remove(this);
  }

  public Relatorio visualizarRelatorio() {
    return this;
  }

  public static List<Relatorio> listarRelatorio() {
    return listaRelatorios;
  }

  public static Relatorio buscarRelatorio(String id) {
    for (Relatorio r : listaRelatorios) {
      if (r.getIdRelatorio().equalsIgnoreCase(id)) {
        return r;
      }
    }
    return null;
  }

  public String getIdRelatorio() {
    return idRelatorio;
  }

  public Date getData() {
    return data;
  }

  public String getHorario() {
    return horario;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public void setHorario(String horario) {
    this.horario = horario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public String toString() {
    return "Relatorio{" +
        "id='" + idRelatorio + '\'' +
        ", data=" + data +
        ", horario='" + horario + '\'' +
        ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
        '}';
  }
}
