import java.util.ArrayList;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String dataNascimento;

    public Usuario(String nome, String email, String senha, String dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    public void UsuarioEditar(String nome, String email, String senha, String dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    public static Usuario login(String email, String senha) {
        ConecBanco conecBanco = new ConecBanco("jdbc:postgresql://localhost:5432/Java", "postgres", "26042005");
        Usuario usuario = null;
        conecBanco.conectar();
        if (conecBanco.buscarBoolean("usuario", "email = '" + email + "' and senha = '" + senha + "'")) {
            ArrayList<Object> resulatdos = conecBanco.BuscarERetornar("usuario", "nome, email, senha, data_nascimento", "email = '" + email + "' and senha = '" + senha + "'");
            usuario = new Usuario((String) resulatdos.get(0), (String) resulatdos.get(1), (String) resulatdos.get(2), (String) resulatdos.get(3));
        } else {
            System.out.println("Usuário ou senha inválidos.");
        }
        conecBanco.desconectar();
        return usuario;
    }

    public String toString() {
        return "Usuário{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                '}';
    }

}
