package sala;

import sala.classe.GerenciaSala;
import sala.classe.Sala;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaAbrirSala extends JFrame {
    GerenciaSala gerenciaSala = new GerenciaSala();
    JComboBox box;

    public TelaAbrirSala() {
        setTitle("CineCIC | Abrir salas");    // Título da janela
        setSize(400, 200);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do filme
        JPanel panelEscolha = new JPanel(new GridBagLayout());
        panelEscolha.setBorder(BorderFactory.createTitledBorder("Escolha a sala"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 10);   // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        // Sala
        Map<Integer, Sala> salas = gerenciaSala.carregarSala();
        List<Object> idSalas = new ArrayList<>();

        idSalas.add("Todas as salas");
        salas.forEach((_, sala) -> {
            if (!sala.isAberta()) {
                idSalas.add(sala.getIdSala());
            }
        });

        // Área de escolher a sala
        JLabel lblFuncionario = new JLabel();
        lblFuncionario.setHorizontalAlignment(SwingConstants.CENTER);
        box = new JComboBox(idSalas.toArray());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEscolha.add(box, gbc);

        // Botão de refresh
        JButton botaoRefresh = criarBotao("Atualizar lista", "Interface/Icons/reload.png", 140, 30, 5);
        gbc.gridy = 1;
        panelEscolha.add(botaoRefresh, gbc);

        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoAbrir = criarBotao("Abrir", "Interface/Icons/abrir.png", 120, 30, 10);
        JButton botaoSair = criarBotao("Voltar", "Interface/Icons/voltar.png", 120, 30, 10);

        panelBotoes.add(botaoAbrir);
        panelBotoes.add(botaoSair);

        panel.add(panelEscolha, BorderLayout.NORTH);
        panel.add(panelBotoes, BorderLayout.SOUTH);

        add(panel);
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon, int width, int height, int gap) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.CENTER);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(width, height)); // Tamanho do botão
        botao.setIconTextGap(gap);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        botao.addActionListener(_ -> {
            Object opcaoSelecionada = box.getSelectedItem();

            if (nomeBotao.equals("Abrir")) {
                if (opcaoSelecionada instanceof String && opcaoSelecionada.equals("Todas as salas")) {
                    if (gerenciaSala.abrirTodasSalas().equals("sucesso")) {
                        JOptionPane.showMessageDialog(null, "Todas as salas foram abertas com sucesso.");
                    } else if (gerenciaSala.abrirTodasSalas().equals("erro")) {
                        JOptionPane.showMessageDialog(null, "Erro ao abrir as salas.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Todas as salas já estão abertas");
                    }
                } else if (opcaoSelecionada instanceof Integer salaSelecionada) {
                    if (gerenciaSala.abrirSala(salaSelecionada).equals("sucesso")) {
                        JOptionPane.showMessageDialog(null, String.format("Sala %d aberta com sucesso.", salaSelecionada));
                    } else {
                        JOptionPane.showMessageDialog(null, String.format("Erro ao abrir a sala %d", salaSelecionada));
                    }
                }
            }
            else if (nomeBotao.equals("Atualizar lista")) {
                atualizarComboBox();
            }
            else if (nomeBotao.equals("Voltar")) {
                dispose();
            }
        });
        return botao;
    }

    private void atualizarComboBox() {
        // Atualizar as opções do JComboBox com as novas informações de salas
        Map<Integer, Sala> salas = gerenciaSala.carregarSala();
        List<Object> idSalas = new ArrayList<>();

        idSalas.add("Todas as salas");
        salas.forEach((_, sala) -> {
            if (!sala.isAberta()) {
                idSalas.add(sala.getIdSala());
            }
        });

        // Atualiza o modelo do JComboBox
        box.setModel(new DefaultComboBoxModel(idSalas.toArray()));
    }
}
