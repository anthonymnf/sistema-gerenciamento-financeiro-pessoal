package scr;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Renda {
  private String idRenda;
  private String nomeRenda;
  private Double valor;
  private Date data;
  private boolean tipoRenda; // true = fixa, false = extra

  // Lista estática para armazenar todas as rendas
  private static List<Renda> listaRendas = new ArrayList<>();

  public Renda(String idRenda, String nomeRenda, Double valor, Date data, boolean tipoRenda) {
    this.idRenda = idRenda;
    this.nomeRenda = nomeRenda;
    this.valor = valor;
    this.data = data;
    this.tipoRenda = tipoRenda;

    listaRendas.add(this);
  }

  public boolean editarRenda(String novoNome, Double novoValor, Date novaData, boolean novoTipo) {
    this.nomeRenda = novoNome;
    this.valor = novoValor;
    this.data = novaData;
    this.tipoRenda = novoTipo;
    return true;
  }

  public boolean excluirRenda() {
    return listaRendas.remove(this);
  }

  public Renda visualizarRenda() {
    return this;
  }

  public static List<Renda> listarRenda() {
    return listaRendas;
  }
  // Calculo da renda total
  public static double rendaTotalMensal(int mes, int ano) {
    double total = 0.0;
    for (Renda r : listaRendas) {
      if (r.data.getMonth() == mes - 1 && r.data.getYear() + 1900 == ano) {
        total += r.valor;
      }
    }
    return total;
  }

  public String getIdRenda() {
    return idRenda;
  }

  public String getNomeRenda() {
    return nomeRenda;
  }

  public Double getValor() {
    return valor;
  }

  public Date getData() {
    return data;
  }

  public boolean isTipoRenda() {
    return tipoRenda;
  }

  @Override
  public String toString() {
    return "Renda:\n- idRenda: " + idRenda + "\n- Nome: " + nomeRenda + "\n- Valor: " + valor + "\n- Data: " + data
        + "\n- Tipo: " + (tipoRenda ? "Fixa" : "Extra");
  }
}

