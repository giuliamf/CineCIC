package usuarios.telasfuncionario;

import usuarios.classe.Funcionario;
import usuarios.classe.GerenciaFuncionario;
import usuarios.classe.Login;

import javax.swing.*;
import java.awt.*;

public class TelaMudarSenhaFuncionario extends JFrame {
    JPasswordField passFieldAtual, passFieldNova;
    public TelaMudarSenhaFuncionario() {
        setTitle("CineCIC | Mudar senha");    // título da janela
        setSize(400, 200);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Área de digitar as informações
        // Painel de informações do cliente
        JPanel panelSenha = new JPanel(new GridBagLayout());
        panelSenha.setBorder(BorderFactory.createTitledBorder("Atualizar senha"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 10, 0);

        // Senha antiga
        JLabel lblSenhaAtual = new JLabel("Senha atual:");
        lblSenhaAtual.setHorizontalAlignment(SwingConstants.LEFT);
        passFieldAtual = new JPasswordField(12);

        // Tamanho da caixa de duração usando GBC
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSenha.add(lblSenhaAtual, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelSenha.add(passFieldAtual, gbc);

        // Senha nova
        JLabel lblSenhaNova = new JLabel("Nova senha:");
        lblSenhaNova.setHorizontalAlignment(SwingConstants.LEFT);
        passFieldNova = new JPasswordField(12);

        // Tamanho da caixa de duração usando GBC
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        panelSenha.add(lblSenhaNova, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelSenha.add(passFieldNova, gbc);

        // Botões de salvar e cancelar
        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Salvar", "resources/icones/salvar.png");
        JButton botaoCancelar = criarBotao("Cancelar", "resources/icones/cancelar.png");

        panelBotoes.add(botaoSalvar);
        panelBotoes.add(botaoCancelar);

        panel.add(panelSenha, BorderLayout.NORTH);
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
            if (nomeBotao.contains("Salvar")) {
                String senhaAtual = new String(passFieldAtual.getPassword());
                String novaSenha = new String(passFieldNova.getPassword());

                Login login = new Login("Funcionario");
                Funcionario funcionario = login.obterFuncionarioLogado();
                GerenciaFuncionario cadastro = new GerenciaFuncionario();
                String resultado = cadastro.atualizarSenha(funcionario.getIdFuncionario(), funcionario.getSenha(), funcionario.getSenha());

                if (resultado.equals("sucesso")) {
                    JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else if (resultado.equals("senha incorreta")) {
                    JOptionPane.showMessageDialog(null, "Senha atual incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar a senha!", "Erro", JOptionPane.ERROR_MESSAGE);
                    dispose();
                }
            } else {
                new TelaAreaFuncionario();
                dispose();
            }
        });

        return botao;
    }

}
