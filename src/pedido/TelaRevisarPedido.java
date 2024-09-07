package pedido;

import filme.classe.GerenciaFilme;
import pedido.classe.*;
import sala.classe.GerenciaSala;
import sessao.classe.GerenciaSessao;
import sessao.classe.Sessao;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaRevisarPedido extends JFrame {
    Carrinho carrinho = new Carrinho();
    GerenciaSessao gerenciaSessao = new GerenciaSessao();

    public TelaRevisarPedido(List<Ingresso> ingressos) {

        setTitle("CineCIC | Revisar pedido");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ícone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panel);

        // Painel de informações dos ingressos
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Layout de coluna vertical

        // Detalhes do pedido = pegar o primeiro objeto da lista ingressos
        Ingresso primeiroIngresso = ingressos.getFirst();
        GerenciaSessao gerenciaSessao = new GerenciaSessao();
        int idFilme = gerenciaSessao.obterSessao(primeiroIngresso.getIdSessao()).getIdFilme();
        Date data = gerenciaSessao.obterSessao(primeiroIngresso.getIdSessao()).getData();

        GerenciaFilme gerenciaFilme = new GerenciaFilme();
        String detalhesPedido = String.format("%s | Sala %d | %s | %s",
                gerenciaFilme.obterFilme(idFilme).getTitulo(),
                gerenciaSessao.obterSessao(primeiroIngresso.getIdSessao()).getIdSala(),
                gerenciaSessao.obterSessao(primeiroIngresso.getIdSessao()).getHorario(),
                gerenciaSessao.converterDataParaString(data).replace("-", "/"));

        // Preenchendo o painel com os detalhes de cada ingresso
        for (Ingresso ingresso : ingressos) {
            JPanel ingressoPanel = new JPanel(new BorderLayout());

            boolean ehMeia = ingresso.isMeia();
            String tipoIngresso = ehMeia ? "Meia" : "Inteira";

            String detalhesIngresso = String.format("Assento: %s | %s | Valor: R$%.2f",
                    ingresso.getAssentoEscolhido(), tipoIngresso, ingresso.getSubtotal());

            JLabel labelIngresso = new JLabel(detalhesIngresso);
            ingressoPanel.add(labelIngresso, BorderLayout.CENTER);

            // Adicionando espaço ao redor dos detalhes do ingresso
            ingressoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding adicional

            infoPanel.add(ingressoPanel);
        }

        // Adicionando o painel de ingressos dentro de um JScrollPane
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(380, 250)); // Limita a altura do painel para no máximo 5 ingressos
        scrollPane.setBorder(BorderFactory.createTitledBorder(detalhesPedido));
        panel.add(scrollPane, BorderLayout.NORTH);

        // Exibindo o valor total no canto inferior
        JPanel valorPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        valorPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        float valorTotal = carrinho.calcularValorFinal();
        JLabel totalLabel = new JLabel(String.format("Valor total: R$%.2f", valorTotal));
        valorPanel.add(totalLabel);

        panel.add(valorPanel, BorderLayout.CENTER);

        // Painel de botões "Voltar" e "Finalizar Pedido"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton voltarButton = new JButton("Carrinho");
        voltarButton.addActionListener(_ -> {
            new TelaCarrinho().setVisible(true); // Abre a tela do carrinho
            dispose(); // Fecha a tela atual
        });

        JButton finalizarButton = new JButton("Finalizar Pedido");
        finalizarButton.addActionListener(_ -> {
            if (carrinho.carrinhoVazio()) {
                JOptionPane.showMessageDialog(this, "O carrinho está vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                finalizarPedido(valorTotal);
                ocuparAssentos(ingressos);
                JOptionPane.showMessageDialog(this, "Pedido concluído com sucesso!");
                dispose(); // Fecha a tela após finalizar
            }
        });


        buttonPanel.add(voltarButton);
        buttonPanel.add(finalizarButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void finalizarPedido(float valorTotal) {
        GerenciaPedido gerenciaPedido = new GerenciaPedido();
        Pedido pedido = new Pedido();

        pedido.setValorTotal(valorTotal);

        List<Ingresso> ingressosCarrinho = new ArrayList<>(carrinho.getIngressos());
        Ingresso primeiroIngresso = ingressosCarrinho.getFirst();
        int idUsuario = primeiroIngresso.getIdCliente();

        // Cria o pedido utilizando o GerenciaPedido
        gerenciaPedido.criarPedido(idUsuario, ingressosCarrinho, pedido.getValorTotal());

        // Limpa o carrinho
        carrinho.limparCarrinho();
    }

    private void ocuparAssentos(List<Ingresso> ingressos) {
        for (Ingresso ingresso : ingressos) {
            gerenciaSessao.ocuparAssento(ingresso.getIdSessao(), ingresso.getAssentoEscolhido());
        }
    }
}