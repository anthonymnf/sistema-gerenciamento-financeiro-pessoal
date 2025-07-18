import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Despesa {
  private String idDespesa;
  private String nomeDespesa;
  private Double valor;
  private Date data;

  // Lista estática para armazenar todas as instâncias de despesas
  private static List<Despesa> listaDespesas = new ArrayList<>();

  public Despesa(String idDespesa, String nomeDespesa, Double valor, Date data) {
    this.idDespesa = idDespesa;
    this.nomeDespesa = nomeDespesa;
    this.valor = valor;
    this.data = data;

    listaDespesas.add(this);
  }

  public boolean excluirDespesa() {
    return listaDespesas.remove(this);
  }

  // Visualizar a própria despesa
  public Despesa visualizarDespesa() {
    return this;
  }

  // Listar todas as despesas
  public static List<Despesa> listarDespesa() {
    return listaDespesas;
  }

  // Buscar despesa por ID (estático)
  public static Despesa buscarDespesa(String id) {
    for (Despesa d : listaDespesas) {
      if (d.getIdDespesa().equalsIgnoreCase(id)) {
        return d;
      }
    }
    return null;
  }

  public boolean editarDespesa(String novoNome, double novoValor, Date novaData) {
    this.nomeDespesa = novoNome;
    this.valor = novoValor;
    this.data = novaData;
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

  @Override
  public String toString() {
    return "Despesa:\n-idDespesa: " + idDespesa + "\n- Nome da despesa: " + nomeDespesa + "\n- Valor: " + valor
        + "\n- Data: " + data;
  }

}
