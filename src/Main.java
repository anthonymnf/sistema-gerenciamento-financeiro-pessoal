import java.util.Date;

public class Main {
  public static void main(String[] args) {
    // Criando algumas despesas
    Despesa d1 = new Despesa("D001", "Aluguel", 1200.0, new Date());
    Despesa d2 = new Despesa("D002", "Internet", 100.0, new Date());

    // Criando algumas rendas
    Renda r1 = new Renda("R001", "Salário", 2500.0, new Date(), true);  // fixa
    Renda r2 = new Renda("R002", "Freela", 500.0, new Date(), false);   // extra

    // Listando tudo
    System.out.println("\n=== DESPESAS ===");
    for (Despesa d : Despesa.listarDespesa()) {
      System.out.println(d);
    }

    System.out.println("\n=== RENDAS ===");
    for (Renda r : Renda.listarRenda()) {
      System.out.println(r);
    }

    // Total de renda do mês
    int mesAtual = new Date().getMonth() + 1;
    int anoAtual = new Date().getYear() + 1900;

    double totalRenda = Renda.rendaTotalMensal(mesAtual, anoAtual);
    System.out.println("\nTotal de Renda no mês: R$ " + totalRenda);

    ConecBanco conecBanco = new ConecBanco("jdbc:postgresql://localhost:5432/Java", "postgres", "26042005");
    conecBanco.conectar();

    conecBanco.inserir("categoria", "nome_categoria", "'Alimentação'");
    conecBanco.deletar("categoria", "nome_categoria = 'Alimentação'");

    conecBanco.buscar("categoria", "nome_categoria", "id_categoria = 1");
    conecBanco.atualizar("usuario", "nome = 'João', email = 'joao@gmail.com', data_nascimento = '1990-01-01'", "id_usuario = 1");
    conecBanco.buscar("usuario", "nome, email, data_nascimento", "id_usuario = 1");
    conecBanco.buscar("usuario", "nome, email, data_nascimento", "nome = 'gqisah'");
    //System.err.println(conecBanco.buscarBoolean("usuario", "email = 'Pedro@gmail.com' and senha = '05134447'"));
    Usuario usuario = Usuario.login("email = Pedro@gmail.com", "senha = 05134447");
    usuario = usuario.login("Pedro@gmail.com", "05134447");
    if (usuario != null) {
      System.out.println("Usuário logado: " + usuario);
    } else {
      System.out.println("Falha no login.");
    }

    usuario = usuario.login("Peo@gmail.com", "034447");
    if (usuario != null) {
      System.out.println("Usuário logado: " + usuario);
    } else {
      System.out.println("Falha no login.");
    }

    System.err.println();
    conecBanco.desconectar();
  }
}

