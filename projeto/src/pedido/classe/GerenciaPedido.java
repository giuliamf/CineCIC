package pedido.classe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciaPedido {

    // Criar um objeto da classe GerenciaIngresso para poder acessar os métodos dela
    GerenciaIngresso gerenciaIngresso = new GerenciaIngresso();

    // Criar um arquivo para salvar os pedidos
    File filePedido = new File("resources/banco de dados/pedidos.csv");

    // Criar uma lista para armazenar os pedidos
    List<Pedido> pedidos = new ArrayList<>();

    // Método para salvar um pedido no arquivo de pedidos
    public void salvarPedido(Pedido pedido) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePedido, true))) {
            writer.print(pedido.getIdPedido() + ";" + pedido.getIdUsuario() + ";" + pedido.getValorTotal() + ";");
            for (Ingresso ingresso : pedido.getIngressos()) {
                writer.print(ingresso.getIdIngresso() + ",");   // Pra ler cada id, separar por vírgula
            }
            writer.println();
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Método para criar um pedido
    public void criarPedido(int idUsuario, List<Ingresso> ingressos, float valorTotal) {
        int idPedido = gerarIdPedido(); // Gerar ID para o pedido

        // Gerar IDs para os ingressos
        int proximoIdIngresso = gerenciaIngresso.gerarIdIngresso();  // Obtém o próximo ID disponível no arquivo de ingressos

        // Atribuir IDs aos ingressos do carrinho (que inicialmente têm id = 0)
        for (Ingresso ingresso : ingressos) {
            ingresso.setIdIngresso(proximoIdIngresso);  // Define o ID para cada ingresso
            proximoIdIngresso++;  // Incrementa para o próximo ingresso
        }

        // Criar o pedido com os ingressos atualizados (com IDs corretos)
        Pedido pedido = new Pedido(idPedido, idUsuario, new ArrayList<>(ingressos), valorTotal);

        salvarPedido(pedido);

        // Salvar os ingressos no arquivo de ingressos
        for (Ingresso ingresso : ingressos) {
            gerenciaIngresso.criarIngresso(
                    ingresso.getIdCliente(), ingresso.getIdSessao(), ingresso.getAssentoEscolhido(),
                    ingresso.getSubtotal(), ingresso.isMeia()
            );
        }
    }

    // Método para gerar um ID para o pedido baseado no número de linhas do arquivo
    private int gerarIdPedido() {
        int id = 1; // ID começa em 1
        try (BufferedReader reader = new BufferedReader(new FileReader(filePedido))) {
            while (reader.readLine() != null) {
                id++;
            }
        } catch (IOException e) {
            System.out.println();
        }
        return id;
    }

    // Método para carregar os pedidos do arquivo
    private void carregarPedidos() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePedido))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                int idPedido = Integer.parseInt(dados[0]);
                int idUsuario = Integer.parseInt(dados[1]);
                float valorTotal = Float.parseFloat(dados[2]);
                String[] idsIngressos = dados[3].split(",");
                List<Ingresso> ingressos = new ArrayList<>();
                for (String idIngresso : idsIngressos) {
                    Ingresso ingresso = gerenciaIngresso.obterIngresso(Integer.parseInt(idIngresso));
                    ingressos.add(ingresso);
                }
                Pedido pedido = new Pedido(idPedido, idUsuario, ingressos, valorTotal);
                pedidos.add(pedido);
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Método para obter um pedido pelo idPedido
    public Pedido obterPedido(int idPedido) {
        carregarPedidos(); // Carregar os pedidos do arquivo
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido() == idPedido) {
                return pedido;
            }
        }
        return null;
    }

    // Método para obter ingressos do pedido
    public List<Ingresso> obterIngressosPorPedido(int idPedido) {
        carregarPedidos(); // Carregar os pedidos do arquivo
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido() == idPedido) {
                return pedido.getIngressos();
            }
        }
        return null;
    }

    // Método para obter os pedidos de um cliente pelo idCliente
    public List<Pedido> obterPedidosPorCliente(int idCliente) {
        carregarPedidos(); // Carregar os pedidos do arquivo
        List<Pedido> pedidosCliente = new ArrayList<>(); // Lista para armazenar os pedidos do cliente

        for (Pedido pedido : pedidos) {
            if (pedido.getIdUsuario() == idCliente) {
                pedidosCliente.add(pedido);
            }
        }

        return pedidosCliente;
    }
}
