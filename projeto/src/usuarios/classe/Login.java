package usuarios.classe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Login {
    protected File file;
    GerenciaCliente gerenciaCliente = new GerenciaCliente();
    GerenciaFuncionario gerenciaFuncionario = new GerenciaFuncionario();

    // Construtor que recebe o tipo de usuário (Cliente ou Funcionário)
    public Login(String tipo) {
        if (tipo.equals("Cliente")) {
            file = new File("resources/banco de dados/clienteLogado.csv");
        } else {
            file = new File("resources/banco de dados/funcionarioLogado.csv");
        }
    }

    // Método para verificar existe algum usuário logado
    public boolean verificarSeLogado() {
        return obterIdLogado() != -1;   // Se obterIdLogado retornar -1, não está logado
    }

    // Método para obter o ID do usuário logado
    public int obterIdLogado() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            linha = br.readLine();

            String[] dados = linha.split(";");
            return Integer.parseInt(dados[0]);

        } catch (Exception e) {
            return -1;
        }
    }

    // Método para remover o login
    public void removerLogin(int id) {
        List<String> linhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if ((dados.length > 0) && (Integer.parseInt(dados[0]) != id)) {
                    linhas.add(linha); // Adiciona as linhas que não correspondem ao email
                }
            }
        } catch (IOException e) {
            System.out.println();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println();
        }

    }

    // Método para registrar o login do cliente
    public void registrarLoginFuncionario(Funcionario funcionario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(funcionario.getIdFuncionario() + ";" +
                    funcionario.getEmail());
            bw.newLine();
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Método para registrar o login do funcionário
    public void registrarLoginCliente(Cliente cliente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(cliente.getIdCliente() + ";" +
                    cliente.getEmail());
            bw.newLine();
        } catch (IOException e) {
            System.out.println();;
        }
    }

    // Método para obter o cliente logado
    public Cliente obterClienteLogado() {
        int id = obterIdLogado();
        if (id == -1) {
            return null;
        } else {
            return gerenciaCliente.buscarClienteId(id);
        }
    }

    // Método para obter o funcionário logado
    public Funcionario obterFuncionarioLogado() {
        int id = obterIdLogado();
        if (id == -1) {
            return null;
        } else {
            return gerenciaFuncionario.buscarFuncionarioId(id);
        }
    }
}
