package usuarios;

import usuarios.classe.Cliente;
import usuarios.classe.GerenciaCliente;
import usuarios.classe.Login;
import usuarios.telascliente.TelaCadastrarCliente;
import usuarios.telascliente.TelaAreaCliente;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TelaLoginCliente extends JFrame {
    // Campos de texto para o usuário e senha
    private final JTextField userField;
    private final JPasswordField passField;

    // Construtor da classe
    public TelaLoginCliente() {
        setTitle("CineCIC");    // Título da janela
        setSize(350, 220);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de título
        JLabel titleLabel = new JLabel("Acessar a Área do Cliente", JLabel.CENTER); // Título
        JPanel titlePanel = new JPanel(new BorderLayout()); // Painel para centralizar o título
        titlePanel.add(titleLabel, BorderLayout.CENTER);    // Adiciona o título ao painel
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Espaçamento

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

        // Botao de criar conta
        JButton botaoCriar = criarBotao("Criar conta", 110, 25);
        panelBotao.add(botaoCriar);

        // Botão de cancelar
        JButton botaoCancelar = criarBotao("Cancelar", 90, 25);
        panelBotao.add(botaoCancelar);

        // Adiciona os painéis ao painel principal
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(entradaPanel, BorderLayout.CENTER);
        panel.add(panelBotao, BorderLayout.SOUTH);

        // Adiciona o painel principal ao JFrame
        add(panel);
    }

    // Método para criar botões
    public JButton criarBotao(String nomeBotao, int largura, int altura) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalAlignment(SwingConstants.CENTER);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(largura, altura)); // Tamanho do botão

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            if (nomeBotao.equals("Criar conta")) {
                new TelaCadastrarCliente().setVisible(true);
            } else if (nomeBotao.equals("Entrar")) {
                handleLogin();
            }
            dispose();
        });

        return botao;
    }

    // Método para o login
    private void handleLogin() {
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        Login login = new Login("Cliente");
        GerenciaCliente gerenciaCliente = new GerenciaCliente();
        Cliente cliente = gerenciaCliente.buscarClienteEmail(user);

        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
        } else if (!cliente.getSenha().equals(pass)) {
            JOptionPane.showMessageDialog(null, "Senha incorreta", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            login.registrarLoginCliente(cliente);
            new TelaAreaCliente().setVisible(true);
            dispose();
        }
    }
}
