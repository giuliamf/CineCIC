package promocao;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TelaPromocoes extends JFrame {
    // Construtor da classe
    public TelaPromocoes() {
        setTitle("CineCIC");    // Título da janela
        setSize(500, 250);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela, mas mantém a aplicação aberta
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal (janela)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());    // Layout de borda
        mainPanel.setBackground(Color.WHITE);   // Cor de fundo branca


        // Painel (conteúdo da página)
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Promoções Atuais");   // Título do border
        titledBorder.setTitleColor(Color.red);  // Mudar a cor do título do border
        titledBorder.setTitleFont(new Font("Tahoma", Font.BOLD, 12));   // Mudar a fonte do título do border

        // Painel principal (conteúdo da página)
        JPanel promoPanel = new JPanel();
        promoPanel.setLayout(new BoxLayout(promoPanel, BoxLayout.Y_AXIS));    // Layout vertical
        promoPanel.setBorder(BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        )); // Adiciona o border ao painel
        promoPanel.setBackground(Color.WHITE);   // Cor de fundo branca
        promoPanel.add(Box.createRigidArea(new Dimension(0, 20)));   // Espaçamento no topo

        // Ícones das promoções
        ImageIcon meiaEntradaIcon = resizeIcon("resources/icones/coupon.png", 30, 30);
        ImageIcon descontoHorarioIcon = resizeIcon("resources/icones/relogio.png", 35, 35);

        // Adicionando promoção de meia entrada
        JLabel meiaEntrada = new JLabel("Meia Entrada para estudantes, idosos e professores!", meiaEntradaIcon, JLabel.LEFT);
        meiaEntrada.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centraliza o texto
        meiaEntrada.setFont(new Font("Tahoma", Font.PLAIN, 15));    // Muda a fonte do texto
        promoPanel.add(meiaEntrada); // Adiciona o texto ao painel
        promoPanel.add(Box.createRigidArea(new Dimension(0, 20)));   // Espaçamento entre os textos

        // Adicionando promoção de desconto no horário
        JLabel descontoHorario = new JLabel("25% de desconto no ingresso para sessões entre 12h e 15h!", descontoHorarioIcon, JLabel.LEFT);
        descontoHorario.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centraliza o texto
        descontoHorario.setFont(new Font("Tahoma", Font.PLAIN, 15));    // Muda a fonte do texto
        promoPanel.add(descontoHorario); // Adiciona o texto ao painel
        promoPanel.add(Box.createRigidArea(new Dimension(0, 20)));   // Espaçamento entre os textos


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton voltar = new JButton("Sair");
        voltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(voltar);
        voltar.addActionListener(_ -> dispose());

        // Adicionando o painel no MainFrame
        mainPanel.add(promoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal na janela
        add(mainPanel);
    }

    // Método para redimensionar ícones
    private ImageIcon resizeIcon(String path, int largura, int altura) {
        ImageIcon icon = new ImageIcon(path);   // Cria um ícone com o caminho passado
        Image image = icon.getImage();  // Pega a imagem do ícone
        Image newImg = image.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);    // Redimensiona a imagem
        return new ImageIcon(newImg);   // Retorna o ícone redimensionado
    }

    // Método principal para executar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPromocoes().setVisible(true));
    }
}
