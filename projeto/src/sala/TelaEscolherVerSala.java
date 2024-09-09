package sala;

import sala.classe.GerenciaSala;
import sala.classe.Sala;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaEscolherVerSala extends JFrame {
    GerenciaSala gerenciaSala = new GerenciaSala();
    JComboBox box;

    public TelaEscolherVerSala() {
        setTitle("CineCIC | Visualizar salas");    // Título da janela
        setSize(400, 180);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do filme
        JPanel panelEscolha = new JPanel(new GridBagLayout());
        panelEscolha.setBorder(BorderFactory.createTitledBorder("Escolha a sala"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 0);   // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        // Sala
        Map<Integer, Sala> salas = gerenciaSala.carregarSala();
        List<Integer> idSalas = new ArrayList<>();

        // Área de escolher a sala
        JLabel lblFuncionario = new JLabel();
        lblFuncionario.setHorizontalAlignment(SwingConstants.CENTER);
        salas.forEach((k, v) -> {
            idSalas.add(v.getIdSala());
        });
        box = new JComboBox(idSalas.toArray());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEscolha.add(box, gbc);

        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoVisualizar = criarBotao("Continuar", "resources/icones/continuar.png");
        JButton botaoSair = criarBotao("Voltar", "resources/icones/voltar.png");

        panelBotoes.add(botaoVisualizar);
        panelBotoes.add(botaoSair);

        panel.add(panelEscolha, BorderLayout.NORTH);
        panel.add(panelBotoes, BorderLayout.SOUTH);

        add(panel);
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.CENTER);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(120, 30)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        botao.addActionListener(_ -> {
            Integer salaSelecionada = (Integer) box.getSelectedItem();
            if (nomeBotao.equals("Continuar")) {
                assert salaSelecionada != null;
                new TelaVerSala(salaSelecionada).setVisible(true);
            }
            if (nomeBotao.equals("Voltar")) {
                dispose();
            }
        });
        return botao;
    }
}