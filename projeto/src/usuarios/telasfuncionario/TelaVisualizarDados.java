package usuarios.telasfuncionario;

import usuarios.classe.Funcionario;
import usuarios.classe.Login;

import javax.swing.*;
import java.awt.*;

public class TelaVisualizarDados extends JFrame {
    public TelaVisualizarDados() {
        // Carregar informações do cliente logado
        Login gerenciarLogin = new Login("Funcionario");
        Funcionario funcionario = gerenciarLogin.obterFuncionarioLogado();

        // Configurações da janela
        setTitle("CineCIC | Dados da sua conta");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ícone da janela
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
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
        JLabel lblNome = new JLabel(String.format("Nome: %s", funcionario.getNome()));
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));
        lblNome.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInfos.add(lblNome, gbc);

        JLabel lblEmail = new JLabel(String.format("Email: %s", funcionario.getEmail()));
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
        lblEmail.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        panelInfos.add(lblEmail, gbc);

        JLabel lblCpf = new JLabel(String.format("CPF: %s", funcionario.getCpf()));
        lblCpf.setFont(new Font("Arial", Font.BOLD, 14));
        lblCpf.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        panelInfos.add(lblCpf, gbc);

        JLabel lblCargo = new JLabel(String.format("Cargo: %s", funcionario.getCargo()));
        lblCargo.setFont(new Font("Arial", Font.BOLD, 14));
        lblCargo.setForeground(Color.DARK_GRAY);
        gbc.gridy++;
        panelInfos.add(lblCargo, gbc);

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
