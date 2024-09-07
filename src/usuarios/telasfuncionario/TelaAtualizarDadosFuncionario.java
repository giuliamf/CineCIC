package usuarios.telasfuncionario;

import usuarios.classe.Funcionario;
import usuarios.classe.GerenciaFuncionario;
import usuarios.classe.Login;

import javax.swing.*;
import java.awt.*;

public class TelaAtualizarDadosFuncionario extends JFrame {
    JTextField txtNome, txtEmail, txtCPF;

    public TelaAtualizarDadosFuncionario() {
        // Configurações da janela
        setTitle("CineCIC | Atualizar cadastro");    // título da janela
        setSize(410, 230);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 10, 0);


        // Painel de informações do cliente
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder("Digite os dados que deseja modificar"));

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
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
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

        // Tamanho da caixa de E-mail usando GBC
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfos.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelInfos.add(txtEmail, gbc);

        // Botões de salvar e cancelar
        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Atualizar", "Interface/Icons/salvar.png");
        JButton botaoCancelar = criarBotao("Cancelar", "Interface/Icons/cancelar.png");

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

        botao.addActionListener(_ -> {
            if (nomeBotao.contains("Atualizar")) {
                String nome = txtNome.getText();
                String cpf = txtCPF.getText();
                String email = txtEmail.getText();

                Login login = new Login("Funcionario");
                Funcionario funcionario = login.obterFuncionarioLogado();
                GerenciaFuncionario cadastro = new GerenciaFuncionario();

                if (funcionario != null) {
                    if (!nome.isEmpty()) {
                        funcionario.setNome(nome);
                    }
                    if (!cpf.isEmpty()) {
                        funcionario.setCpf(cpf);
                    }
                    if (!email.isEmpty()) {
                        funcionario.setEmail(email);
                    }

                    String status = cadastro.atualizarCadastro(funcionario);
                    if (status.equals("sucesso")) {
                        JOptionPane.showMessageDialog(null, "Cadastro atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar cadastro!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar cadastro!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            dispose();
        });

        return botao;
    }
}
