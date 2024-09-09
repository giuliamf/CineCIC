package usuarios.classe;

import usuarios.telascliente.TelaAreaCliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciaCliente {
    List<Cliente> clientes = lerClientes(); // Lista de clientes carregados do arquivo

    // Método para salvar o cadastro de um novo cliente
    public void salvarCadastro(Cliente cliente) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Interface/Dados/dadosClientes.csv", true))){
            bw.write(cliente.getIdCliente() + ";" +
                    cliente.getNome() + ";" +
                    cliente.getCpf() + ";" +
                    cliente.getSenha() + ";" +
                    cliente.getEmail());
            bw.newLine();
        }
        catch (Exception e) {
            System.out.println();
        }
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarClienteId(int idCliente) {
        for (Cliente c : clientes) {
            if (c.getIdCliente() == idCliente) {
                return c;
            }
        }
        return null;
    }

    // Método para buscar um cliente pelo email
    public Cliente buscarClienteEmail(String email) {
        for (Cliente c : clientes) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    // Método para atualizar o cadastro de um cliente logado
    public String atualizarCadastro(Cliente clienteLogado) {
        boolean clienteEncontrado = false;

        for (Cliente c : clientes) {
            if (c.getIdCliente() == clienteLogado.getIdCliente()) {
                if (!clienteLogado.getNome().isEmpty()) {
                    c.setNome(clienteLogado.getNome());
                }
                if (!clienteLogado.getCpf().isEmpty()) {
                    c.setCpf(clienteLogado.getCpf());
                }
                if (!clienteLogado.getEmail().isEmpty()) {
                    c.setEmail(clienteLogado.getEmail());
                }
                clienteEncontrado = true;
                break;
            }
        }
        if (clienteEncontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Interface/Dados/dadosClientes.csv"))) {
                for (Cliente c : clientes) {
                    bw.write(c.getIdCliente() + ";" +
                            c.getNome() + ";" +
                            c.getCpf() + ";" +
                            c.getSenha() + ";" +
                            c.getEmail());
                    bw.newLine();
                }
                return "sucesso";
            } catch (Exception e) {
                return "erro";
            }
        } else {
            return "não encontrado";
        }
    }

    // Método para atualizar a senha de um cliente
    public String atualizarSenha(int id, String senhaAtual, String novaSenha) {
        boolean clienteEncontrado = false;
        for (Cliente c : clientes) {
            if (c.getIdCliente() == id && c.getSenha().equals(senhaAtual)) {
                c.setSenha(novaSenha);
                clienteEncontrado = true;
                break;
            }
        }
        if (clienteEncontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Interface/Dados/dadosClientes.csv"))) {
                for (Cliente c : clientes) {
                    bw.write(c.getIdCliente() + ";" +
                            c.getNome() + ";" +
                            c.getCpf() + ";" +
                            c.getSenha() + ";" +
                            c.getEmail());
                    bw.newLine();
                }
                return "sucesso";
            } catch (Exception e) {
                return "erro";
            }
        } else {
            return "senha incorreta";
        }
    }

    // Método para excluir conta do cliente
    public String excluirConta(String email, String senha) {
        boolean clienteRemovido = false;
        for (Cliente c : clientes) {
            if (c.getEmail().equals(email) && c.getSenha().equals(senha)) {
                clientes.remove(c);
                clienteRemovido = true;
                break;
            }
        }
        if (clienteRemovido) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("Interface/Dados/dadosClientes.csv"))) {
                for (Cliente c : clientes) {
                    bw.write(c.getIdCliente() + ";" +
                            c.getNome() + ";" +
                            c.getCpf() + ";" +
                            c.getSenha() + ";" +
                            c.getEmail());
                    bw.newLine();
                }
                return "sucesso";
            } catch (Exception e) {
                return "erro";
            }
        } else {
            return "não encontrado";
        }
    }

    // Método para ler os clientes do arquivo
    private List<Cliente> lerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Interface/Dados/dadosClientes.csv"))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) { // Verifica se a linha tem o número correto de campos
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(Integer.parseInt(dados[0]));
                    cliente.setNome(dados[1]);
                    cliente.setCpf(dados[2]);
                    cliente.setSenha(dados[3]);
                    cliente.setEmail(dados[4]);
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.out.println();
        }
        return clientes;
    }
}