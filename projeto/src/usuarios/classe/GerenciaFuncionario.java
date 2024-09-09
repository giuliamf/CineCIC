package usuarios.classe;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciaFuncionario {
    List<Funcionario> funcionarios = lerFuncionarios(); // Lista de funcionários carregados do arquivo
    String status;  // Variável para armazenar o status da operação

    // Método para obter a lista de funcionários
    public Map<String, Integer> getFuncionarios() {
        Map<String, Integer> nomesFuncionarios = new HashMap<>();
        for (Funcionario f : funcionarios) {
            nomesFuncionarios.put(f.getNome(), f.getIdFuncionario());
        }
        return nomesFuncionarios;
    }

    // Método para salvar o cadastro de um novo funcionário
    public String salvarCadastro(Funcionario funcionario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/banco de dados/dadosFuncionarios.csv", true))){
            bw.write(funcionario.getIdFuncionario() + ";" +
                    funcionario.getNome() + ";" +
                    funcionario.getEmail() + ";" +
                    funcionario.getCpf() + ";" +
                    funcionario.getSenha() + ";" +
                    funcionario.getCargo());
            bw.newLine();
            status = "sucesso";
        } catch (Exception e) {
            status = "erro";
        }
        return status;
    }

    // Método para buscar um funcionário pelo email
    public Funcionario buscarFuncionarioEmail(String email) {
        for (Funcionario f : funcionarios) {
            if (f.getEmail().equals(email)) {
                return f;
            }
        }
        return null;
    }

    // Método para buscar um funcionário pelo ID
    public Funcionario buscarFuncionarioId(int idFuncionario) {
        for (Funcionario f : funcionarios) {
            if (f.getIdFuncionario() == idFuncionario) {
                return f;
            }
        }
        return null;
    }

    // Método para atualizar o cadastro de um funcionário
    public String atualizarCadastro(Funcionario funcionarioAtt) {
        boolean funcionarioEncontrado = false;

        for (Funcionario f : funcionarios) {
            if (f.getIdFuncionario() == funcionarioAtt.getIdFuncionario()) {
                if (!funcionarioAtt.getNome().isEmpty()) {
                    f.setNome(funcionarioAtt.getNome());
                }
                if (!funcionarioAtt.getEmail().isEmpty()) {
                    f.setEmail(funcionarioAtt.getEmail());
                }
                if (!funcionarioAtt.getCpf().isEmpty()) {
                    f.setCpf(funcionarioAtt.getCpf());
                }
                if (!funcionarioAtt.getSenha().isEmpty()) {
                    f.setSenha(funcionarioAtt.getSenha());
                }
                if (!funcionarioAtt.getCargo().isEmpty()) {
                    f.setCargo(funcionarioAtt.getCargo());
                }
                funcionarioEncontrado = true;
                break;
            }
        }

        if (funcionarioEncontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/banco de dados/dadosFuncionarios.csv"))) {
                for (Funcionario f : funcionarios) {
                    bw.write(f.getIdFuncionario() + ";" +
                            f.getNome() + ";" +
                            f.getEmail() + ";" +
                            f.getCpf() + ";" +
                            f.getSenha() + ";" +
                            f.getCargo());
                    bw.newLine();
                }
                status = "sucesso";
            } catch (Exception e) {
                status = "erro";
            }
        } else {
            status = "não encontrado";
        }
        return status;
    }

    // Método para remover o cadastro de um funcionário
    public String removerCadastro(int idFuncionario) {
        boolean cadastroRemovido = false;
        for (Funcionario f : funcionarios) {
            if (f.getIdFuncionario() == idFuncionario) {
                funcionarios.remove(f);
                cadastroRemovido = true;
                break;
            }
        }
        if (cadastroRemovido) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/banco de dados/dadosFuncionarios.csv"))) {
                for (Funcionario f : funcionarios) {
                    bw.write(f.getIdFuncionario() + ";" +
                            f.getNome() + ";" +
                            f.getEmail() + ";" +
                            f.getCpf() + ";" +
                            f.getSenha() + ";" +
                            f.getCargo());
                    bw.newLine();
                }
                status = "sucesso";
            } catch (Exception e) {
                status = "erro";
            }
        } else {
            status = "não encontrado";
        }
        return status;
    }

    // Método para atualizar a senha de um funcionário
    public String atualizarSenha(int idFuncionario, String senhaAtual, String senhaAntiga) {
        boolean funcionarioEncontrado = false;
        for (Funcionario f : funcionarios) {
            if (f.getIdFuncionario() == idFuncionario && f.getSenha().equals(senhaAtual)) {
                f.setSenha(senhaAntiga);
                funcionarioEncontrado = true;
                break;
            }
        }
        if (funcionarioEncontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/banco de dados/dadosFuncionarios.csv"))) {
                for (Funcionario f : funcionarios) {
                    bw.write(f.getIdFuncionario() + ";" +
                            f.getNome() + ";" +
                            f.getEmail() + ";" +
                            f.getCpf() + ";" +
                            f.getSenha() + ";" +
                            f.getCargo());
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

    // Método para ler os funcionários do arquivo
    private List<Funcionario> lerFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/banco de dados/dadosFuncionarios.csv"))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 6) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setIdFuncionario(Integer.parseInt(dados[0]));
                    funcionario.setNome(dados[1]);
                    funcionario.setEmail(dados[2]);
                    funcionario.setCpf(dados[3]);
                    funcionario.setSenha(dados[4]);
                    funcionario.setCargo(dados[5]);
                    funcionarios.add(funcionario);
                }
            }
        } catch (Exception e) {
            System.out.println();
        }
        return funcionarios;
    }
}
