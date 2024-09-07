package usuarios.telascliente;

import usuarios.classe.Cliente;
import usuarios.classe.Login;

import javax.swing.*;
import java.awt.*;

public class TelaVerConta extends JFrame {
    public TelaVerConta() {
        // Carregar informações do cliente logado
        Login gerenciarLogin = new Login("Cliente");
        Cliente cliente = gerenciarLogin.obterClienteLogado();

        // Configurações da janela
        setTitle("CineCIC | Dados da sua conta");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ícone da janela
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de informações do cliente
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder("Informações da sua conta"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels de informações com design aprimorado
        JLabel lblNome = new JLabel(String.format("Nome: %s", cliente.getNome()));
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));
        lblNome.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInfos.add(lblNome, gbc);

        JLabel lblEmail = new JLabel(String.format("Email: %s", cliente.getEmail()));
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
        lblEmail.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        panelInfos.add(lblEmail, gbc);

        JLabel lblCpf = new JLabel(String.format("CPF: %s", cliente.getCpf()));
        lblCpf.setFont(new Font("Arial", Font.BOLD, 14));
        lblCpf.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        panelInfos.add(lblCpf, gbc);

        // Separador para criar um espaço visual
        JSeparator separator = new JSeparator();
        gbc.gridy++;
        panelInfos.add(separator, gbc);

        // Adiciona o painel de informações ao painel principal
        panelPrincipal.add(panelInfos, BorderLayout.CENTER);

        // Botão para fechar a tela
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(_ -> dispose());
        JPanel panelBotao = new JPanel();
        panelBotao.add(btnFechar);

        // Adiciona o painel do botão ao painel principal
        panelPrincipal.add(panelBotao, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
        add(panelPrincipal);
    }
}
