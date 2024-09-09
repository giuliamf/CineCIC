package filme;

import javax.swing.*;
import java.awt.*;

public class TelaGerenciarCartaz extends JFrame {
    public TelaGerenciarCartaz() {
        setTitle("CineCIC | Gerenciar cartaz");    // Título da janela
        setSize(330, 200);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de botoes de gerenciar cartaz
        JPanel panelGerencia = new JPanel(new GridBagLayout());
        panelGerencia.setBorder(BorderFactory.createTitledBorder("Gerenciar filmes em cartaz"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botões
        JButton botaoAdd = criarBotao("Adicionar filme ao cartaz", "resources/icones/adicionar.png", 210, 30);
        JButton botaoRemove = criarBotao("Remover filme do cartaz", "resources/icones/remover.png", 210, 30);

        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        panelGerencia.add(botaoAdd, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        panelGerencia.add(botaoRemove, gbc);

        // Botão de voltar
        JPanel panelVoltar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton botaoVoltar = criarBotao("Voltar", "resources/icones/voltar.png", 100, 30);

        panelVoltar.add(botaoVoltar);

        panel.add(panelGerencia, BorderLayout.CENTER);
        panel.add(panelVoltar, BorderLayout.SOUTH);

        add(panel);
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon, int width, int height) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(width, height)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            switch (nomeBotao) {
                case "Adicionar filme ao cartaz" -> {
                    new TelaAddCartaz().setVisible(true);
                    dispose();
                }
                case "Remover filme do cartaz" -> {
                    new TelaRmvCartaz().setVisible(true);
                    dispose();
                }
                default -> dispose();
            }
        });
        return botao;
    }
}
