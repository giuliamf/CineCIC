package filme;

import filme.classe.Filme;
import filme.classe.GerenciaFilme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaRmvCartaz extends JFrame {
    GerenciaFilme gerenciaFilme = new GerenciaFilme();
    JComboBox box;

    public TelaRmvCartaz() {
        setTitle("CineCIC | Gerenciar cartaz");    // Título da janela
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
        panelEscolha.setBorder(BorderFactory.createTitledBorder("Escolha o filme para remover do cartaz"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 10);   // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        // Sala
        Map<Integer, Filme> filmes = gerenciaFilme.carregarFilme();
        List<String> nomeFilmes = new ArrayList<>();

        filmes.forEach((_, filme) -> {
            if (filme.isEmCartaz()) {
                nomeFilmes.add(filme.getTitulo());
            }
        });

        JLabel lblFilme = new JLabel();
        lblFilme.setHorizontalAlignment(SwingConstants.CENTER);
        box = new JComboBox(nomeFilmes.toArray());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEscolha.add(box, gbc);

        // Botão de refresh
        JButton botaoRefresh = criarBotao("Atualizar lista", "Interface/Icons/reload.png", 140, 30, 5);
        gbc.gridy = 1;
        panelEscolha.add(botaoRefresh, gbc);

        // Botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoAdicionar = criarBotao("Remover", "Interface/Icons/remover.png", 140, 30, 5);
        JButton botaoSair = criarBotao("Voltar", "Interface/Icons/voltar.png", 140, 30, 5);

        panelBotoes.add(botaoAdicionar);
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
            String opcaoSelecionada = (String) box.getSelectedItem();

            // Pegar o id do filme através do titulo selecionado
            Integer idFilme = gerenciaFilme.getIdFilme(opcaoSelecionada);

            switch (nomeBotao) {
                case "Remover" -> {
                    assert idFilme != null;
                    gerenciaFilme.tirarDeCartaz(idFilme);
                    JOptionPane.showMessageDialog(null, "Filme removido do cartaz com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
                case "Atualizar lista" -> atualizarComboBox();
                case "Voltar" -> {
                    new TelaGerenciarCartaz().setVisible(true);
                    dispose();
                }
            }
        });

        return botao;
    }

    private void atualizarComboBox() {
        // Atualizar as opções do JComboBox com as novas informações
        Map<Integer, Filme> filmes = gerenciaFilme.carregarFilme();
        List<String> nomeFilmes = new ArrayList<>();

        filmes.forEach((_, filme) -> {
            if (filme.isEmCartaz()) {
                nomeFilmes.add(filme.getTitulo());
            }
        });

        // Atualiza o modelo do JComboBox
        box.setModel(new DefaultComboBoxModel(nomeFilmes.toArray()));
    }

}
