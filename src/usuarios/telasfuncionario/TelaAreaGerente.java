package usuarios.telasfuncionario;

import javax.swing.*;
import java.awt.*;

public class TelaAreaGerente extends JFrame {
    public TelaAreaGerente() {
        setTitle("CineCIC | Área do Gerente");    // título da janela
        setSize(350, 500);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel do meio para colocar itens que não vão no NORTH nem SOUTH
        JPanel panelMiddle = new JPanel();
        panelMiddle.setLayout(new BoxLayout(panelMiddle, BoxLayout.Y_AXIS));

        // Painel de título
        JLabel titleLabel = new JLabel("Área do Gerente", JLabel.CENTER);
        Font titleFont = new Font("Tahoma", Font.BOLD, 20);
        titleLabel.setFont(titleFont);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Espaço para o título não ficar grudado na margem da página
        panel.add(Box.createVerticalStrut(45), BorderLayout.NORTH);

        // Painel dos botões
        JPanel panelGerenciar = new JPanel();
        panelGerenciar.setLayout(new FlowLayout());
        panelGerenciar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Gerenciar funcionários"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botão de cadastrar filme
        JButton botaoAddFuncionario = criarBotao("Cadastrar novo funcionário", "Interface/Icons/adicionar.png",230, 50);   // espaços extras para centralizar as imagens
        panelGerenciar.add(botaoAddFuncionario);

        // Botão de remover filme
        JButton botaoRmvFuncionario = criarBotao("Remover funcionário", "Interface/Icons/remover.png", 230, 50);
        panelGerenciar.add(botaoRmvFuncionario);

        // Botão de editar filme
        JButton botaoEditFuncionario = criarBotao("Editar funcionário", "Interface/Icons/edit.png", 230, 50);
        panelGerenciar.add(botaoEditFuncionario);

        // Colocar no painel do meio
        panelMiddle.add(panelGerenciar);

        // Botão para sair da conta
        JButton botaoSair = criarBotao("Voltar", "Interface/Icons/voltar.png", 100, 50);

        // Colocar o botao de sair à direita
        JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRodape.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRodape.add(botaoSair);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(panelMiddle, BorderLayout.CENTER);
        panel.add(panelRodape, BorderLayout.SOUTH);

        add(panel);
        pack();

    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon, int larguraBotao, int alturaBotao) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(larguraBotao, alturaBotao)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            switch (nomeBotao) {
                case "Cadastrar novo funcionário" -> new TelaCadastrarFuncionario().setVisible(true);
                case "Remover funcionário" -> new TelaExcluirFuncionario().setVisible(true);
                case "Editar funcionário" -> new TelaEditarFuncionario().setVisible(true);
                default -> dispose();
            }
        });
        return botao;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaAreaGerente().setVisible(true));
    }
}
