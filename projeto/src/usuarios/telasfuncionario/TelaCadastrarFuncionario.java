package usuarios.telasfuncionario;

import usuarios.classe.GerenciaFuncionario;
import usuarios.classe.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaCadastrarFuncionario extends JFrame {
    JTextField txtNome, txtCPF, txtEmail;
    JPasswordField passField;
    JComboBox box;

    public TelaCadastrarFuncionario() {
        setTitle("CineCIC | Cadastrar funcionário");    // título da janela
        setSize(400, 330);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
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

        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setHorizontalAlignment(SwingConstants.LEFT);
        txtEmail = new JTextField(20);

        // Tamanho da caixa do email usando GBC
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;  // peso que por algum motivo padronizou os outros (distancia do label para o field)
        panelInfos.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelInfos.add(txtEmail, gbc);

        // CPF
        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setHorizontalAlignment(SwingConstants.LEFT);
        txtCPF = new JTextField(20);

        // Tamanho da caixa do CPF usando GBC
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfos.add(lblCPF, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelInfos.add(txtCPF, gbc);

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

        // Cargo
        // Definindo a lista de cargos
        ArrayList<String> listaCargos = new ArrayList<>();
        listaCargos.add("Gerente");
        listaCargos.add("Funcionário");

        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setHorizontalAlignment(SwingConstants.LEFT);
        box = new JComboBox<>(listaCargos.toArray());
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelInfos.add(lblCargo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelInfos.add(box, gbc);

        // Botões de salvar e cancelar
        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Salvar", "Interface/Icons/salvar.png");
        JButton botaoCancelar = criarBotao("Cancelar", "Interface/Icons/cancelar.png");

        panelBotoes.add(botaoSalvar);
        panelBotoes.add(botaoCancelar);

        panel.add(panelBotoes, BorderLayout.SOUTH);
        panel.add(panelInfos, BorderLayout.NORTH);
        add(panel);
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(130, 50)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        botao.addActionListener(_ -> {
            if (nomeBotao.equals("Salvar")) {
                GerenciaFuncionario cadastro = new GerenciaFuncionario();
                Funcionario funcionario = new Funcionario();

                funcionario.setNome(txtNome.getText());
                funcionario.setCpf(txtCPF.getText());
                funcionario.setEmail(txtEmail.getText());
                funcionario.setSenha(passField.getText());
                funcionario.setCargo(box.getSelectedItem().toString());
                funcionario.setIdFuncionario(Funcionario.gerarId());

                String resultado = cadastro.salvarCadastro(funcionario);

                if (resultado.equals("sucesso")) {
                    JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário");
                }
            }
            dispose();
        });

        return botao;
    }

}

