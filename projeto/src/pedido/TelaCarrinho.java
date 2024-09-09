package pedido;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import filme.classe.Filme;
import pedido.classe.Carrinho;
import pedido.classe.Ingresso;
import sessao.classe.GerenciaSessao;
import sessao.classe.Sessao;

public class TelaCarrinho extends JFrame {
    private final Carrinho carrinho;
    private final JPanel painelIngressos;

    public TelaCarrinho() {
        carrinho = new Carrinho(); // Carrega o carrinho do arquivo CSV

        setTitle("CineCIC | Carrinho");    // título da janela
        setSize(500, 370);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Ícone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de ingressos
        painelIngressos = new JPanel();
        painelIngressos.setLayout(new BoxLayout(painelIngressos, BoxLayout.Y_AXIS));

        // Adicionar os ingressos carregados do carrinho
        carregarIngressosDoCarrinho();

        JScrollPane scrollPane = new JScrollPane(painelIngressos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(380, 250)); // Limita a altura do painel para no máximo 5 ingressos
        scrollPane.setBorder(BorderFactory.createTitledBorder("Ingressos no carrinho"));
        panel.add(scrollPane, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = criarBotao("Limpar carrinho", "resources/icones/bag.png", 160);
        JButton btnConfirmar = criarBotao("Revisar pedido", "resources/icones/review.png", 160);
        JButton btnVoltar = criarBotao("Voltar", "resources/icones/voltar.png", 100);

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnConfirmar);

        panel.add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona o painel principal ao frame
        add(panel);
    }

    private void carregarIngressosDoCarrinho() {
        List<Ingresso> ingressos = carrinho.getIngressos();
        for (Ingresso ingresso : ingressos) {
            JPanel painelAssento = new JPanel(new FlowLayout());
            JLabel label = new JLabel("Assento " + ingresso.getAssentoEscolhido() + " - " + (ingresso.isMeia() ? "Meia" : "Inteira"));

            // Criar botão de remover
            JButton btnRemover = criarBotaoRemover(ingresso);

            painelAssento.add(label);
            painelAssento.add(btnRemover);
            painelIngressos.add(painelAssento);
        }
    }

    private JButton criarBotaoRemover(Ingresso ingresso) {
        JButton botao = new JButton();
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        botao.setVerticalTextPosition(JButton.CENTER);
        botao.setPreferredSize(new Dimension(40, 25));

        // Redimensionar a imagem do ícone de remoção
        ImageIcon icon = new ImageIcon("resources/icones/delete.png");
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ação ao clicar no botão de remover
        botao.addActionListener(_ -> {
            carrinho.removerIngresso(ingresso); // Remove o ingresso do carrinho
            painelIngressos.remove(botao.getParent()); // Remove o painel correspondente ao ingresso
            painelIngressos.revalidate(); // Atualiza a interface
            painelIngressos.repaint(); // Re-renderiza o painel
        });

        return botao;
    }

    // Método auxiliar para criar botões
    private JButton criarBotao(String nomeBotao, String caminhoIcon, int larguraBotao) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(larguraBotao, 30)); // Tamanho do botão
        botao.setIconTextGap(5);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        botao.addActionListener(_ -> {
            switch (nomeBotao) {
                case "Revisar pedido" -> {
                    new TelaRevisarPedido(carrinho.getIngressos()).setVisible(true);
                    dispose();
                }
                case "Limpar carrinho" -> {
                    carrinho.limparCarrinho();
                    dispose();
                }
                case "Voltar" -> dispose();
            }
        });
        return botao;
    }
}
