package pedido.classe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GerenciaIngresso {

    Map<Integer, Ingresso> ingresso = new HashMap<>();
    File fileIngressos = new File("Interface/Dados/ingressos.csv");

    public void carregarIngressos() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileIngressos), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Ingresso i = new Ingresso(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), Integer.parseInt(dados[3]), Float.parseFloat(dados[4]), Boolean.parseBoolean(dados[5]));
                ingresso.put(i.getIdIngresso(), i);
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    public int gerarIdIngresso() {
        int id = 1;  // ID começa em 1
        try (BufferedReader br = new BufferedReader(new FileReader(fileIngressos))) {
            while (br.readLine() != null) {
                id++;  // Incrementa o ID para cada linha encontrada (cada ingresso no arquivo)
            }
        } catch (IOException e) {
            System.out.println("Erro ao gerar ID do ingresso: " + e.getMessage());
        }
        return id;  // Retorna o próximo ID disponível
    }

    public float calculaSubtotal(float valor, boolean meia) {    // o desconto de horário está incluso no valor da sessão
        if (meia) {
            return  valor/2;
        }
        return valor;
    }

    public void criarIngresso(int idCliente, int idSessao, int assentoEscolhido, float subtotal, boolean ehMeia) {
        int idIngresso = gerarIdIngresso();  // Gerar o próximo ID disponível
        Ingresso i = new Ingresso(idIngresso, idCliente, idSessao, assentoEscolhido, subtotal, ehMeia);
        ingresso.put(idIngresso, i);
        atualizarCsvComNovoIngresso(i);  // Adicionar o novo ingresso ao arquivo CSV
    }

    public void atualizarCsvComNovoIngresso(Ingresso ingresso) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileIngressos, true))) {  // "true" para modo de apêndice
            // Escreve o ingresso no arquivo, adicionando ao final
            writer.println(ingresso.getIdIngresso() + ";" + ingresso.getIdCliente() + ";" + ingresso.getIdSessao() + ";" +
                    ingresso.getAssentoEscolhido() + ";" + ingresso.getSubtotal() + ";" + ingresso.isMeia());
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo de ingressos: " + e.getMessage());
        }
    }

    public Ingresso obterIngresso(int idIngresso) {
        ingresso.clear();
        carregarIngressos();
        return ingresso.get(idIngresso);
    }
}
