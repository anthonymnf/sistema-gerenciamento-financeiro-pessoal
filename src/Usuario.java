import java.util.ArrayList;

public class Usuario {
    private String id_usuario;
    private String nome;
    private String email;
    private String senha;
    private String dataNascimento;

    public Usuario(String id, String nome, String email, String senha, String dataNascimento) {
        this.id_usuario = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void UsuarioEditar(String nome, String email, String senha, String dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    public static Usuario login(String email, String senha, ConecBanco conecBanco) {
        Usuario usuario = null;
        if (conecBanco.buscarBoolean("usuario", "email = '" + email + "' and senha = '" + senha + "'")) {
            ArrayList<Object> resulatdos = conecBanco.BuscarERetornar("usuario", "id_usuario, nome, email, senha, data_nascimento", "email = '" + email + "' and senha = '" + senha + "'");
            usuario = new Usuario((String) resulatdos.get(0), (String) resulatdos.get(1), (String) resulatdos.get(2), (String) resulatdos.get(3), (String) resulatdos.get(4));
        } else {
            System.out.println("Usuário ou senha inválidos.");
        }
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
