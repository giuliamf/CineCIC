package usuarios.classe;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Cliente extends Usuario{
    private int idCliente;

    public Cliente() {
        super();
    }
    public Cliente(int idCliente, String nome, String cpf, String senha, String email) {
        super(nome, cpf, senha, email);
        this.idCliente = idCliente;
    }

    public static int gerarId() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("resources/banco de dados/dadosClientes.csv"), StandardCharsets.UTF_8))) {
            int max = 0;
            String linha;
            br.readLine(); // Pular cabeçalho
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

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
}
