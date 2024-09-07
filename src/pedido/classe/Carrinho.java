package pedido.classe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Carrinho{
    private final List<Ingresso> ingressos;
    private final File fileCarrinho = new File("Interface/Dados/carrinho.csv");

    public Carrinho() {
        this.ingressos = new ArrayList<>();
        carregarIngressosCarrinhoCsv();
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        ingressos.add(ingresso);
        atualizarCsvCarrinho();
    }

    public void removerIngresso(Ingresso ingresso) {
        ingressos.remove(ingresso);
        atualizarCsvCarrinho();
    }

    public float calcularValorFinal() {
        float valorTotal = 0;
        for (Ingresso ingresso : ingressos) {
            valorTotal += ingresso.getSubtotal();
        }
        return valorTotal;
    }

    public boolean carrinhoVazio() {
        carregarIngressosCarrinhoCsv();
        return ingressos.isEmpty();
    }

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

    private void carregarIngressosCarrinhoCsv() {
        ingressos.clear();
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

    public void limparCarrinho() {
        ingressos.clear();
        atualizarCsvCarrinho();
    }
}
