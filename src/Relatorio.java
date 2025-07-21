import java.util.ArrayList;
import java.util.Date;
public class Relatorio {
  private String idRelatorio;
  private Date data;
  private String horario;
  private Usuario usuario;

  public Relatorio(String idRelatorio, Date data, String horario, Usuario usuario, ConecBanco banco) {
    this.idRelatorio = idRelatorio;
    this.data = data;
    this.horario = horario;
    this.usuario = usuario;

    String tabela = "relatorio";
    String colunas = "id_relatorio, id_usuario, data, horario";
    String valores = "'" + idRelatorio + "', '" + usuario.getId_usuario() + "', '" +
        new java.sql.Date(data.getTime()) + "', '" + horario + "'";
    banco.inserir(tabela, colunas, valores);
  }

  public Relatorio(String idRelatorio, Date data, String horario, Usuario usuario) {
    this.idRelatorio = idRelatorio;
    this.data = data;
    this.horario = horario;
    this.usuario = usuario;
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

  public void excluirRelatorio(ConecBanco banco) {
    banco.deletar("relatorio", "id_relatorio = '" + idRelatorio + "'");
  }

  public Relatorio visualizarRelatorio() {
    return this;
  }

  public static void listarRelatorios(ConecBanco banco) {
    banco.buscar("relatorio", "id_relatorio, data, horario, id_usuario", "");
  }

  public static Relatorio buscarRelatorio(String id, ConecBanco banco) {
    ArrayList<Object> resultados = banco.BuscarERetornar("relatorio", "id_relatorio, data, horario, id_usuario", "id_relatorio = '" + id + "'");
    if (resultados.size() > 0) {
      String idRelatorio = (String) resultados.get(0);
      Date data = (Date) resultados.get(1);
      String horario = (String) resultados.get(2);
      String idUsuario = (String) resultados.get(3);
      Usuario usuario = Usuario.login(idUsuario, "", banco); // Assuming login method can fetch user by ID
      return new Relatorio(idRelatorio, data, horario, usuario);
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
