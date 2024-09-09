package sala.classe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import sessao.classe.*;

public class GerenciaSala {

    // Mapa de salas com chave sendo o id da sala e valor sendo a sala
    Map<Integer, Sala> salas = new HashMap<>();

    // Arquivo CSV que contém as salas
    File file = new File("Interface/Dados/salas.csv");

    // Método para carregar as salas do arquivo CSV
    public Map<Integer, Sala> carregarSala() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Sala sala = new Sala(
                        Integer.parseInt(dados[0]), // idSala
                        Integer.parseInt(dados[1]), // qtAssentos
                        Boolean.parseBoolean(dados[2]), // eh3d
                        Boolean.parseBoolean(dados[3]), // ehVip
                        Boolean.parseBoolean(dados[4])  // aberta
                );
                this.salas.put(Integer.parseInt(dados[0]), sala);
            }
        } catch (IOException e) {
            System.out.println();
        }
        return this.salas;
    }

    // Método para obter todas as salas
    public Sala obterSala(int indice) {
        carregarSala(); // Carrega as salas
        return salas.get(indice);   // Retorna a sala com o id passado
    }

    // Método para abrir todas as salas
    public String abrirTodasSalas() {
        boolean existeSalaParaAbrir = false;    // Inicializa a variável que verifica se existe sala para abrir
        try {
            for (Sala sala : salas.values()) {  // Percorre todas as salas
                if (!sala.isAberta()) { // Se a sala não estiver aberta
                    sala.setAberta(true);   // Abre a sala
                    existeSalaParaAbrir = true; // Existe sala para abrir
                }
            }
            if (!existeSalaParaAbrir) { // Se não existe sala para abrir
                return "não há salas para abrir";
            }
            return atualizarCSV();
        } catch (Exception e) {
            return "erro";
        }
    }

    // Método para abrir uma sala específica
    public String abrirSala(int indice) {
        try {
            Sala sala = salas.get(indice);  // Obtém a sala
            sala.setAberta(true);   // Abre a sala
            return atualizarCSV();  // Atualiza o arquivo CSV

        } catch (Exception e) {
            return "erro";
        }
    }

    // Método para fechar todas as salas
    public String fecharTodasSalas() {
        boolean existeSalaParaFechar = false;   // Inicializa a variável que verifica se existe sala para fechar
        try {
            for (Sala sala : salas.values()) {  // Percorre todas as salas
                if (sala.isAberta()) {  // Se a sala estiver aberta
                    sala.setAberta(false);  // Fecha a sala
                    existeSalaParaFechar = true;    // Existe sala para fechar
                }
            }
            if (!existeSalaParaFechar) {    // Se não existe sala para fechar
                return "não há salas para fechar";
            }
            return atualizarCSV();
        } catch (Exception e) {
            return "erro";
        }

    }

    // Método para fechar uma sala específica
    public String fecharSala(int indice) {
        try {
            Sala sala = salas.get(indice);  // Obtém a sala
            sala.setAberta(false);  // Fecha a sala
            return atualizarCSV();  // Atualiza o arquivo CSV
        } catch (Exception e) {
            return "erro";
        }
    }

    // Método para atualizar as informações de sala no arquivo CSV
    private String atualizarCSV() {
        String status = "";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Sala sala : salas.values()) {
                bw.write(sala.getIdSala() + ";" +
                        sala.getQtdAssentos() + ";" +
                        sala.isEh3d() + ";" +
                        sala.isEhVip() + ";" +
                        sala.isAberta());
                bw.newLine();
                status = "sucesso";
            }
        } catch (IOException e) {
            status = "erro";
        }
        return status;
    }

}