package usuarios.telasfuncionario;

import usuarios.classe.Funcionario;
import usuarios.classe.GerenciaFuncionario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaExcluirFuncionario extends JFrame {
    JComboBox box;

    GerenciaFuncionario gerenciaFuncionario = new GerenciaFuncionario();
    Map<String, Integer> mapFuncionarios = gerenciaFuncionario.getFuncionarios();

    public TelaExcluirFuncionario() {
        setTitle("CineCIC | Remover funcionário");    // Título da janela
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
        panelEscolha.setBorder(BorderFactory.createTitledBorder("Informações do funcionário"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 0);   // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        List<String> listaFuncionarios = new ArrayList<>(mapFuncionarios.keySet());

        // Área de escolher o funcionário
        JLabel lblFuncionario = new JLabel();
        lblFuncionario.setHorizontalAlignment(SwingConstants.CENTER);
        box = new JComboBox(listaFuncionarios.toArray(new String[0]));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEscolha.add(box, gbc);

        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Remover", "resources/icones/lixeira.png");
        JButton botaoCancelar = criarBotao("Cancelar", "resources/icones/cancelar.png");

        panelBotoes.add(botaoSalvar);
        panelBotoes.add(botaoCancelar);

        panel.add(panelEscolha, BorderLayout.NORTH);
        panel.add(panelBotoes, BorderLayout.SOUTH);

        add(panel);
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.CENTER);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(150, 30)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            if (nomeBotao.equals("Remover")) {
                String funcionarioSelecionado = (String) box.getSelectedItem();
                int confirmacao = JOptionPane.showConfirmDialog(
                        this,
                        String.format("Tem certeza que deseja remover o/a funcionário/a %s?", funcionarioSelecionado),
                        "Remover funcionário",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    int idFuncionario = mapFuncionarios.get(funcionarioSelecionado);

                    String resultado = gerenciaFuncionario.removerCadastro(idFuncionario);
                    if (resultado.equals("sucesso")) {
                        JOptionPane.showMessageDialog(this, "Funcionário removido com sucesso!");
                    } else if (resultado.equals("erro")) {
                        JOptionPane.showMessageDialog(this, "Erro ao remover funcionário");
                    } else {
                        JOptionPane.showMessageDialog(this, "Funcionário não encontrado");
                    }
                }
            }
            dispose();
        });
        return botao;
    }
}
