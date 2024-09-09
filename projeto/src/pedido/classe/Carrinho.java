package pedido.classe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Carrinho{

    private final List<Ingresso> ingressos;
    private final File fileCarrinho = new File("Interface/Dados/carrinho.csv");

    // Construtor que inicializa a lista de ingressos e carrega os ingressos do carrinho do arquivo CSV
    public Carrinho() {
        this.ingressos = new ArrayList<>();
        carregarIngressosCarrinhoCsv();
    }

    // Método para retornar a lista de ingressos
    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    // Método para adicionar um ingresso à lista de carrinho e atualizar o arquivo CSV
    public void adicionarIngresso(Ingresso ingresso) {
        ingressos.add(ingresso);    // Adiciona o ingresso à lista de ingressos
        atualizarCsvCarrinho();    // Atualiza o arquivo CSV
    }

    // Método para remover um ingresso da lista de ingressos e atualizar o arquivo CSV
    public void removerIngresso(Ingresso ingresso) {
        ingressos.remove(ingresso);   // Remove o ingresso da lista de ingressos
        atualizarCsvCarrinho();   // Atualiza o arquivo CSV
    }

    // Método para calcular o valor total dos ingressos no carrinho
    public float calcularValorFinal() {
        float valorTotal = 0;   // Inicializa o valor total como 0
        for (Ingresso ingresso : ingressos) {   // Para cada ingresso na lista de ingressos
            valorTotal += ingresso.getSubtotal();   // Adiciona o subtotal do ingresso ao valor total
        }
        return valorTotal;  // Retorna o valor total
    }

    // Método para verificar se o carrinho está vazio
    public boolean carrinhoVazio() {
        carregarIngressosCarrinhoCsv(); // Carrega os ingressos do carrinho do arquivo CSV
        return ingressos.isEmpty(); // Retorna se a lista de ingressos está vazia
    }

    // Método para atualizar o arquivo CSV com os ingressos do carrinho
    public void atualizarCsvCarrinho() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileCarrinho))) {
            for (Ingresso ingresso : ingressos) {
                writer.println(0 + ";" + ingresso.getIdCliente() + ";" + ingresso.getIdSessao() + ";" +
                        ingresso.getAssentoEscolhido() + ";" + ingresso.getSubtotal() + ";" + ingresso.isMeia());
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Método para carregar os ingressos do carrinho do arquivo CSV
    private void carregarIngressosCarrinhoCsv() {
        ingressos.clear();  // Limpa a lista de ingressos para evitar duplicatas
        try (BufferedReader reader = new BufferedReader(new FileReader(fileCarrinho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                Ingresso ingresso = new Ingresso(
                        Integer.parseInt(dados[0]),
                        Integer.parseInt(dados[1]),
                        Integer.parseInt(dados[2]),
                        Integer.parseInt(dados[3]),
                        Float.parseFloat(dados[4]),
                        Boolean.parseBoolean(dados[5])
                );
                ingressos.add(ingresso);
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Método para limpar o carrinho
    public void limparCarrinho() {
        ingressos.clear();  // Limpa a lista de ingressos
        atualizarCsvCarrinho(); // Atualiza o arquivo CSV
    }
}
