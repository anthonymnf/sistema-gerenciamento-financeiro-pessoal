import java.util.Date;

public class Main {
  public static void main(String[] args) {
    // Conexão com o banco
    ConecBanco conecBanco = new ConecBanco("jdbc:postgresql://localhost:5432/Java", "postgres", "26042005");
    conecBanco.conectar();
    
    // Login
    Usuario usuario = Usuario.login("Pedro@gmail.com", "05134447", conecBanco);
    if (usuario != null) {
      System.out.println("Usuário logado: " + usuario);
    } else {
      System.out.println("Falha no login.");
    }

    // Criando categoria (se necessário)
    Categoria categoria = new Categoria("Jogos", conecBanco);
    
    // Criando algumas despesas
    Despesa d1 = new Despesa("Aluguel", 1200.0, new Date(), usuario, "6", "6", conecBanco);
    
    // Criando algumas rendas
    Renda r1 = new Renda("Salário", 2500.0, new Date(), true);  // fixa
    Renda r2 = new Renda("Freela", 500.0, new Date(), false);   // extra
    r1.salvarNoBanco();
    
    // Listando tudo
    System.out.println("\n=== DESPESAS ===");
    //d1.listarDespesas(conecBanco);
     
    System.out.println("\n=== RENDAS ===");
    for (Renda r : Renda.listarRenda()) {
      System.out.println(r);
    }

    // Total de renda do mês
    int mesAtual = new Date().getMonth() + 1;
    int anoAtual = new Date().getYear() + 1900;

    double totalRenda = Renda.rendaTotalMensal(mesAtual, anoAtual);
    System.out.println("\nTotal de Renda no mês: R$ " + totalRenda);

    // Operações de exemplo com o banco
    conecBanco.inserir("categoria", "nome_categoria", "'Alimentação'");
    conecBanco.deletar("categoria", "nome_categoria = 'Alimentação'");

    conecBanco.buscar("categoria", "nome_categoria", "id_categoria = 1");
    conecBanco.atualizar("usuario", "nome = 'João', email = 'joao@gmail.com', data_nascimento = '1990-01-01'", "id_usuario = 1");
    conecBanco.buscar("usuario", "nome, email, data_nascimento", "id_usuario = 1");
    conecBanco.buscar("usuario", "nome, email, data_nascimento", "nome = 'gqisah'");

    // Outro teste de login
    Usuario outroUsuario = Usuario.login("Peo@gmail.com", "034447", conecBanco);
    if (outroUsuario != null) {
      System.out.println("Usuário logado: " + outroUsuario);
    } else {
      System.out.println("Falha no login.");
    }
    
    conecBanco.desconectar();
  }
}
