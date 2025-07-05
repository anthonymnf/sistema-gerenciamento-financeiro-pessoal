import java.util.Date;

public class Despesa {
  private String idDespesa;
  private String nomeDespesa;
  private Double valor;
  private Date data;

  public Despesa(String idDespesa, String nomeDespesa, Double valor, Date data) {
    this.idDespesa = idDespesa;
    this.nomeDespesa = nomeDespesa;
    this.valor = valor;
    this.data = data;
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
