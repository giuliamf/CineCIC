package usuarios.telascliente;

import usuarios.classe.GerenciaCliente;

import javax.swing.*;
import java.awt.*;

public class TelaExcluirCliente extends JFrame {
    JTextField txtEmail;
    JPasswordField passField;

    public TelaExcluirCliente() {
        setTitle("CineCIC | Excluir conta");    // título da janela
        setSize(400, 200);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do cliente
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder("Confirme seus dados"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 10, 0);

        // E-mail
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
        txtEmail = new JTextField(20);

        // Tamanho da caixa do E-mail usando GBC
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;  // peso que por algum motivo padronizou os outros (distancia do label para o field)
        panelInfos.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelInfos.add(txtEmail, gbc);

        // Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
        passField = new JPasswordField(20);

        // Tamanho da caixa da senha usando GBC
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInfos.add(lblSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelInfos.add(passField, gbc);

        // Botões de salvar e cancelar
        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Excluir conta", "resources/icones/lixeira.png");
        JButton botaoCancelar = criarBotao("Cancelar", "resources/icones/cancelar.png");

        panelBotoes.add(botaoSalvar);
        panelBotoes.add(botaoCancelar);

        panel.add(panelInfos, BorderLayout.NORTH);
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
            if (nomeBotao.contains("Excluir conta")) {
                int confirmacao = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja excluir a conta?",
                        "Excluir conta",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    GerenciaCliente cadastro = new GerenciaCliente();

                    String email = txtEmail.getText();
                    String senha = new String(passField.getPassword());

                    String resultado = cadastro.excluirConta(email, senha);
                    if (resultado.equals("sucesso")) {
                        JOptionPane.showMessageDialog(this, "Conta excluída com sucesso!");
                        dispose();
                    } else if (resultado.equals("erro")) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir conta");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Email ou senha incorretos");
                    }
                }
            }
        else if (nomeBotao.contains("Cancelar")) {
            new TelaAreaCliente().setVisible(true);
            dispose();
        }
    });

        return botao;
    }
}
