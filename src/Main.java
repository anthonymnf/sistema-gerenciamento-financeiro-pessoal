import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Conexão com o banco
    ConecBanco conecBanco = new ConecBanco("jdbc:postgresql://localhost:5432/Java", "postgres", "26042005");
    conecBanco.conectar();
    
    // Login
    System.out.println("=== LOGIN ===");
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Senha: ");
    String senha = scanner.nextLine();

    Usuario usuario = Usuario.login(email, senha, conecBanco);

    if (usuario == null) {
      System.out.println("Falha no login. Encerrando o sistema.");
      return;
    }

    int opcao;
    do {
      System.out.println("\n=== MENU PRINCIPAL ===");
      System.out.println("1 - Cadastrar nova despesa");
      System.out.println("2 - Cadastrar nova renda");
      System.out.println("3 - Exibir rendas cadastradas");
      System.out.println("4 - Exibir despesas cadastradas");
      System.out.println("5 - Inserir nova categoria");
      System.out.println("6 - Atualizar categoria");
      System.out.println("7 - Excluir categoria");
      System.out.println("8 - Exibir relatório do mês atual");
      System.out.println("9 - Novo login");
      System.out.println("0 - Sair");
      System.out.print("Escolha uma opção: ");
      opcao = scanner.nextInt();
      scanner.nextLine(); // limpar o buffer

      switch (opcao) {
        case 1:
          System.out.println("Descrição da despesa:");
          String descDesp = scanner.nextLine();

          System.out.println("Valor:");
          double valorDesp = scanner.nextDouble();
          scanner.nextLine(); // consumir \n

          System.out.println("ID da categoria:");
          String idCategoria = scanner.nextLine();

          Date dataDesp = new Date(); // pega data atual

          Despesa novaDespesa = new Despesa(descDesp, valorDesp, dataDesp, usuario, idCategoria, "1", conecBanco);
          System.out.println("Despesa cadastrada com sucesso.");
          break;


        case 2:
          System.out.println("Descrição da renda:");
          String descRenda = scanner.nextLine();
          System.out.println("Valor:");
          double valorRenda = scanner.nextDouble();
          scanner.nextLine();
          System.out.println("É fixa? (true/false):");
          boolean isFixa = scanner.nextBoolean();
          scanner.nextLine();
          Renda novaRenda = new Renda(descRenda, valorRenda, new Date(), isFixa);
          novaRenda.salvarNoBanco();
          System.out.println("Renda cadastrada.");
          break;

        case 3:
          System.out.println("\n=== RENDAS CADASTRADAS ===");
          for (Renda r : Renda.listarRenda()) {
            System.out.println(r);
          }
          break;

        case 4:
          System.out.println("\n=== DESPESAS ===");
          conecBanco.buscar("despesas", "id_despesas, nome_despesa, valor, data, id_usuario, id_categoria, id_relatorio", "id_usuario = '" + usuario.getId_usuario() + "'");
          break;

        case 5:
          System.out.print("Nome da nova categoria: ");
          String nomeCat = scanner.nextLine();
          Categoria novaCategoria = new Categoria(nomeCat, conecBanco);
          System.out.println("Categoria inserida.");
          break;

        case 6:
          List<Despesa> despesas = Despesa.listarDespesas(conecBanco, usuario);
          if (despesas.isEmpty()) {
              System.out.println("Nenhuma despesa encontrada.");
                } else {
              for (Despesa d : despesas) {
                  System.out.println(d);
                  System.out.println("----------------------");
                  }
                }
          break;

        case 7:
          Renda.carregarDoBanco(); // garante que está atualizada
          List<Renda> rendas = Renda.listarRenda();
          if (rendas.isEmpty()) {
              System.out.println("Nenhuma renda encontrada.");
          } else {
              for (Renda r : rendas) {
                  System.out.println(r);
                  System.out.println("----------------------");
              }
          }
          break;

        case 8:
          int mesAtual = new Date().getMonth() + 1;
          int anoAtual = new Date().getYear() + 1900;

          double totalRenda = Renda.rendaTotalMensal(mesAtual, anoAtual);
          double totalDespesas = Despesa.despesaTotalMensal(mesAtual, anoAtual, conecBanco, usuario);
          double saldoFinal = totalRenda - totalDespesas;

          System.out.println("\n=== RELATÓRIO FINANCEIRO DO MÊS ===");
          System.out.println("Mês: " + mesAtual + "/" + anoAtual);
          System.out.println("Total de Renda: R$ " + totalRenda);
          System.out.println("Total de Despesas: R$ " + totalDespesas);
          System.out.println("Saldo Final: R$ " + saldoFinal);
          break;


        case 9:
          System.out.println("\n--- NOVO LOGIN ---");
          System.out.print("Email: ");
          email = scanner.nextLine();
          System.out.print("Senha: ");
          senha = scanner.nextLine();
          usuario = Usuario.login(email, senha, conecBanco);
          if (usuario != null) {
            System.out.println("Usuário logado: " + usuario);
          } else {
            System.out.println("Falha no login.");
          }
          break;

        case 0:
          System.out.println("Encerrando o sistema...");
          break;

        default:
          System.out.println("Opção inválida!");
      }

    } while (opcao != 0);

    conecBanco.desconectar();
    scanner.close();
  }
}
