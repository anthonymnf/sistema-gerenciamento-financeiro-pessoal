import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Conexão com o banco
    ConecBanco conecBanco = new ConecBanco("jdbc:postgresql://localhost:5432/Java", "postgres", "26042005");
    conecBanco.conectar();
    
    // Login
    System.err.println("=== LOGIN E CADASTRO ===");
    System.out.print("1 -- Login\n2 - Cadastro\nEscolha uma opção: ");
    int opcaoLogin = scanner.nextInt();
    scanner.nextLine(); // limpar o buffer
    String email = "";
    String senha = "";
    if (opcaoLogin == 2) {
      System.out.println("=== CADASTRO ===");
      System.out.print("Nome: ");
      String nome = scanner.nextLine();
      System.err.println("CPF: (apenas números)");
      String cpf = scanner.nextLine();
      System.out.print("Email: ");
      email = scanner.nextLine();
      System.out.print("Senha: ");
      senha = scanner.nextLine();
      System.out.print("Data de Nascimento (dd/MM/yyyy): ");
      String dataNascimentoStr = scanner.nextLine();
      Date dataNascimento = null;
      try {
        dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimentoStr);
      } catch (java.text.ParseException e) {
        System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
        conecBanco.desconectar();
        scanner.close();
        return;
      }


      conecBanco.inserir("usuario", "nome, cpf, email, senha, data_nascimento", "'" + nome + "', '" + cpf + "', '" + email + "', '" + senha + "', '" + new java.sql.Date(dataNascimento.getTime()) + "'");
      System.out.println("Usuário cadastrado com sucesso!");
    } else if (opcaoLogin != 1) {
      System.out.println("Opção inválida. Encerrando o sistema.");
      conecBanco.desconectar();
      scanner.close();
      return;
    }


    System.out.println("=== LOGIN ===");
    System.out.print("Email: ");
    email = scanner.nextLine();
    System.out.print("Senha: ");
    senha = scanner.nextLine();

    Usuario usuario = Usuario.login(email, senha, conecBanco);

    if (usuario == null) {
      System.out.println("Falha no login. Encerrando o sistema.");
      conecBanco.desconectar();
      scanner.close();
      return;
    }

    int opcao;
    do {
      System.out.println("\n=== MENU PRINCIPAL ===");
      System.out.println("1 - Cadastrar nova despesa");
      System.out.println("2 - Cadastrar nova renda");
      System.out.println("3 - Exibir rendas cadastradas");
      System.out.println("4 - Exibir despesas cadastradas");
      System.err.println("5 - despesas por categoria");
      System.out.println("6 - Exibir relatório do mês atual");
      System.err.println("7 - Relatorio de despesas e rendas");
      System.out.println("8 - Novo login");
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

          System.out.println("categoria:");
          String idCategoria = scanner.nextLine();

          if (!conecBanco.buscarBoolean("categoria", "nome_categoria = '" + idCategoria + "'")) {
            new Categoria(idCategoria, conecBanco);
            idCategoria = conecBanco.BuscarERetornar("categoria", "id_categoria", "nome_categoria = '" + idCategoria + "'").get(0).toString();
          } else{
            idCategoria = conecBanco.BuscarERetornar("categoria", "id_categoria", "nome_categoria = '" + idCategoria + "'").get(0).toString();
          }

          Date dataDesp = null;
          System.out.println("Data (dd/MM/yyyy):");
          String dataStrDesp = scanner.nextLine();
          try {
            dataDesp = new SimpleDateFormat("dd/MM/yyyy").parse(dataStrDesp);
          } catch (java.text.ParseException e) {
            System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
            break;
          }

          new Despesa(descDesp, valorDesp, dataDesp, usuario, idCategoria, conecBanco);
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
          System.out.println("Data (dd/MM/yyyy):");
          String dataStr = scanner.nextLine();
          Date dataRenda = null;
          try {
            dataRenda = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
          } catch (java.text.ParseException e) {
            System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
            break;
          }

          new Renda(descRenda, valorRenda, dataRenda, isFixa, usuario, conecBanco);
          System.out.println("Renda cadastrada.");
          break;

        case 3:
          System.out.println("\n=== RENDAS CADASTRADAS ===");
          Renda.listarRenda(conecBanco, usuario);
          break;

        case 4:
          System.out.println("\n=== DESPESAS ===");
          conecBanco.buscar("despesas", "id_despesas, nome_despesa, valor, data, id_usuario, id_categoria", "id_usuario = '" + usuario.getId_usuario() + "'");
          break;

        case 5:
          System.out.println("\n=== DESPESAS POR CATEGORIA ===");
          System.out.println("a categoria:");
          String categoria = scanner.nextLine();
          String idcategoria = conecBanco.BuscarERetornar("categoria", "id_categoria", "nome_categoria = '" + categoria + "'").get(0).toString();
          conecBanco.buscar("despesas", "id_despesas, nome_despesa, valor, data, id_usuario, id_categoria", "id_categoria = '" + idcategoria + "' and id_usuario = '" + usuario.getId_usuario() + "'");
          break;

        case 6:
          int mesAtual = new Date().getMonth() + 1;
          int anoAtual = new Date().getYear() + 1900;

          double totalRenda = Renda.rendaTotalMensal(mesAtual, anoAtual, conecBanco, usuario);
          double totalDespesas = Despesa.despesaTotalMensal(mesAtual, anoAtual, conecBanco, usuario);
          double saldoFinal = totalRenda - totalDespesas;

          System.out.println("\n=== RELATÓRIO FINANCEIRO DO MÊS ===");
          System.out.println("Mês: " + mesAtual + "/" + anoAtual);
          System.out.println("Total de Renda: R$ " + totalRenda);
          System.out.println("Total de Despesas: R$ " + totalDespesas);
          System.out.println("Saldo Final: R$ " + saldoFinal);
          break;

        case 7:
          System.out.println("\n=== RELATÓRIO DE DESPESAS E RENDAS ===");
          System.err.println("digite o mês (1-12)");
          int mes = scanner.nextInt();
          System.err.println("digite o ano (ex: 2023)");
          int ano = scanner.nextInt();
          double totalRendaGeral = Renda.rendaTotalMensal(mes, ano, conecBanco, usuario);
          double totalDespesasGeral = Despesa.despesaTotalMensal(mes, ano, conecBanco, usuario);
          double saldoGeral = totalRendaGeral - totalDespesasGeral;
          System.out.println("Total de Renda Geral: R$ " + totalRendaGeral);
          System.out.println("Total de Despesas Geral: R$ " + totalDespesasGeral);
          System.out.println("Saldo Geral: R$ " + saldoGeral);
          break;

        case 8:
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
