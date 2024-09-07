package sala.classe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import sessao.classe.*;

public class GerenciaSala {

    Map<Integer, Sala> salas = new HashMap<>();
    File file = new File("Interface/Dados/salas.csv");

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

    public Sala obterSala(int indice) {
        carregarSala();
        return salas.get(indice);
    }

    public String abrirTodasSalas() {
        boolean existeSalaParaAbrir = false;
        try {
            for (Sala sala : salas.values()) {
                if (!sala.isAberta()) {
                    sala.setAberta(true);
                    existeSalaParaAbrir = true;
                }
            }
            if (!existeSalaParaAbrir) {
                return "não há salas para abrir";
            }
            return atualizarCSV();
        } catch (Exception e) {
            return "erro";
        }
    }

    public String abrirSala(int indice) {
        try {
            Sala sala = salas.get(indice);
            sala.setAberta(true);
            return atualizarCSV();

        } catch (Exception e) {
            return "erro";
        }
    }

    public String fecharTodasSalas() {
        boolean existeSalaParaFechar = false;
        try {
            for (Sala sala : salas.values()) {
                if (sala.isAberta()) {
                    sala.setAberta(false);
                    existeSalaParaFechar = true;
                }
            }
            if (!existeSalaParaFechar) {
                return "não há salas para fechar";
            }
            return atualizarCSV();
        } catch (Exception e) {
            return "erro";
        }

    }

    public String fecharSala(int indice) {
        try {
            Sala sala = salas.get(indice);
            sala.setAberta(false);
            return atualizarCSV();
        } catch (Exception e) {
            return "erro";
        }
    }

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