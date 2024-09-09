package filme;

import pedido.classe.Carrinho;
import sessao.TelaVerSessoes;

import javax.swing.*;
import java.awt.*;

public class TelaAreaCatalogo extends JFrame {
    private final Carrinho carrinho = new Carrinho();
    public TelaAreaCatalogo(int indiceFilme) {
        setTitle("CineCIC");    // título da janela
        setSize(250, 200);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal com GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Botões
        JButton btnDetalhes = createBtn("Ver detalhes do filme", indiceFilme);
        JButton btnSessoes = createBtn("Ver sessões disponíveis", indiceFilme);

        // Botão de voltar com ícone
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente
        btnVoltar.setPreferredSize(new Dimension(200, 30)); // Tamanho do botão
        btnVoltar.setIconTextGap(10);
        ImageIcon icon = new ImageIcon("resources/icones/voltar.png");
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        btnVoltar.setIcon(new ImageIcon(img));
        btnVoltar.addActionListener(_ -> dispose());

        // Configurações para centralizar os botões e espaçamento entre eles
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btnDetalhes, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(btnSessoes, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(btnVoltar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private JButton createBtn(String descricaoBotao, int indiceFilme) {
        JButton btn = new JButton(descricaoBotao);
        btn.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente
        btn.setPreferredSize(new Dimension(200, 30)); // Tamanho do botão

        // Ações baseadas em qual botão foi clicado
        btn.addActionListener(_ -> {
            switch (descricaoBotao) {
                case "Ver detalhes do filme" -> new TelaDetalhesFilme(indiceFilme).setVisible(true);
                case "Ver sessões disponíveis" -> {
                    if (!carrinho.carrinhoVazio()) {
                        JOptionPane.showMessageDialog(this, "Limpe o carrinho antes de prosseguir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else {
                        new TelaVerSessoes(indiceFilme).setVisible(true);  // mudar o ver sessao
                    }
                }
            }
            dispose();
        });
        return btn;
    }
}
