package usuarios;

import usuarios.classe.Funcionario;
import usuarios.classe.GerenciaFuncionario;
import usuarios.classe.Login;
import usuarios.telasfuncionario.TelaAreaFuncionario;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TelaLoginFuncionario extends JFrame {
    // Campos de texto para o usuário e senha
    private final JTextField userField;
    private final JPasswordField passField;

    // Construtor da classe
    public TelaLoginFuncionario() {
        setTitle("CineCIC");    // Título da janela
        setSize(335, 220);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de título
        JLabel titleLabel = new JLabel("Acessar a Área do Funcionário", JLabel.CENTER);
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel para centralizar os campos de usuário e senha
        JPanel entradaPanel = new JPanel(null);
        entradaPanel.setBounds(10, 50, 260, 150);  // Posição e tamanho do painel

        // Borda
        Border border = BorderFactory.createLineBorder(Color.decode("#B0C4DE"), 1);
        entradaPanel.setBorder(border);

        // "Usuário"
        JLabel userLabel = new JLabel("E-mail:");
        userLabel.setBounds(10, 10, 80, 25);
        entradaPanel.add(userLabel);

        // Espaço para digitar o usuário
        userField = new JTextField();
        userField.setBounds(100, 10, 200, 25);
        entradaPanel.add(userField);

        // "Senha"
        JLabel passLabel = new JLabel("Senha:");
        passLabel.setBounds(10, 40, 80, 25);
        entradaPanel.add(passLabel);

        // Espaço para digitar a senha
        passField = new JPasswordField();
        passField.setBounds(100, 40, 200, 25);
        entradaPanel.add(passField);

        // Painel de botões
        JPanel panelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botão de entrar
        JButton botaoLogin = criarBotao("Entrar", 80, 25);
        panelBotao.add(botaoLogin);

        // Botão de cancelar
        JButton botaoCancelar = criarBotao("Cancelar", 90, 25);
        panelBotao.add(botaoCancelar);


        // Adiciona os painéis ao painel principal
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(entradaPanel, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        // Adiciona o painel ao JFrame
        add(panel);
    }

    public JButton criarBotao(String nomeBotao, int largura, int altura) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalAlignment(SwingConstants.CENTER);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(largura, altura)); // Tamanho do botão

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            if (nomeBotao.equals("Entrar")) {
                handleLogin();
            }
            dispose();
        });

        return botao;
    }

    // Método para lidar com o login
    private void handleLogin() {
        String email = userField.getText();
        String pass = new String(passField.getPassword());

        // Verifica se o login foi bem sucedido
        Login login = new Login("Funcionario");
        GerenciaFuncionario gerenciaFuncionario = new GerenciaFuncionario();
        Funcionario funcionario = gerenciaFuncionario.buscarFuncionarioEmail(email);

        // Se o login foi bem-sucedido, abre a tela do funcionário
        if (funcionario == null) {
            JOptionPane.showMessageDialog(null, "Funcionário não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!funcionario.getSenha().equals(pass)) {
            JOptionPane.showMessageDialog(null, "Senha incorreta", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            login.registrarLoginFuncionario(funcionario);
            new TelaAreaFuncionario().setVisible(true);
            dispose();
        }
    }
}