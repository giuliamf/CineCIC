package usuarios.classe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Funcionario extends Usuario{
    private int idFuncionario;
    private String cargo;

    public Funcionario() {
        super();
    }
    public Funcionario(int idFuncionario, String nome, String email, String cpf, String senha, String cargo) {
        super(nome, email, cpf, senha);
        this.idFuncionario = idFuncionario;
        this.cargo = cargo;
    }

    public static int gerarId() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("resources/banco de dados/dadosFuncionarios.csv"), StandardCharsets.UTF_8))) {
            int max = 0;
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] c = linha.split(";");
                if (Integer.parseInt(c[0]) > max) {
                    max = Integer.parseInt(c[0]);
                }
            }
            return max + 1;
        } catch (IOException | NullPointerException e) {
            return 0;
        }
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
