package usuarios.telascliente;

import usuarios.classe.GerenciaCliente;
import usuarios.classe.Cliente;

import javax.swing.*;
import java.awt.*;

public class TelaCadastrarCliente extends JFrame {
    JTextField txtNome, txtCPF, txtEmail;
    JPasswordField passField;

    public TelaCadastrarCliente() {
        setTitle("CineCIC | Cadastrar cliente");    // título da janela
        setSize(400, 270);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do cliente
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder("Dados necessários"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 10, 0);

        // Área de digitar as informações

        // Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.LEFT);
        txtNome = new JTextField(20);

        // Tamanho da caixa do título usando GBC
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;  // peso que por algum motivo padronizou os outros (distancia do label para o field)
        panelInfos.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelInfos.add(txtNome, gbc);

        // CPF
        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setHorizontalAlignment(SwingConstants.LEFT);
        txtCPF = new JTextField(20);

        // Tamanho da caixa do CPF usando GBC
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInfos.add(lblCPF, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelInfos.add(txtCPF, gbc);

        // E-mail
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
        txtEmail = new JTextField(20);

        // Tamanho da caixa de duração usando GBC
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfos.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelInfos.add(txtEmail, gbc);

        // Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
        passField = new JPasswordField(20);

        // Tamanho da caixa de duração usando GBC
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInfos.add(lblSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelInfos.add(passField, gbc);

        // Botões de salvar e cancelar
        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Criar conta", "resources/icones/salvar.png");
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
            if (nomeBotao.contains("Criar conta")) {
                GerenciaCliente cadastro = new GerenciaCliente();
                Cliente cliente = new Cliente();

                cliente.setNome(txtNome.getText());
                cliente.setCpf(txtCPF.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setSenha(passField.getText());
                cliente.setIdCliente(Cliente.gerarId());

                cadastro.salvarCadastro(cliente);

                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
            }
            dispose();
        });

        return botao;
    }
}
