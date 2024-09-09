package sala;

import sala.classe.GerenciaSala;
import sala.classe.Sala;

import javax.swing.*;
import java.awt.*;

public class TelaVerSala extends JFrame {
    public TelaVerSala(int idSala) {
        // Carregar informações das salas
        GerenciaSala gerenciarSala = new GerenciaSala();
        gerenciarSala.carregarSala();

        Sala sala = gerenciarSala.obterSala(idSala);

        // Configurações da janela
        setTitle(String.format("CineCIC | Sala %d", idSala));
        setSize(300, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Icone da janela
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de informações da sala
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder(String.format("Informações da Sala %d", idSala)));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adiciona as informações da sala com labels
        JLabel lblIdSala = new JLabel(String.format("ID da Sala: %d", sala.getIdSala()));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInfos.add(lblIdSala, gbc);

        JLabel lblAssentos = new JLabel(String.format("Quantidade de Assentos: %d", sala.getQtdAssentos()));
        gbc.gridy++;
        panelInfos.add(lblAssentos, gbc);

        JLabel lbl3d = new JLabel("Suporta Filmes 3D: " + (sala.isEh3d() ? "Sim" : "Não"));
        gbc.gridy++;
        panelInfos.add(lbl3d, gbc);

        JLabel lblVip = new JLabel("Sala VIP: " + (sala.isEhVip() ? "Sim" : "Não"));
        gbc.gridy++;
        panelInfos.add(lblVip, gbc);

        JLabel lblAberta = new JLabel("Está Aberta: " + (sala.isAberta() ? "Sim" : "Não"));
        gbc.gridy++;
        panelInfos.add(lblAberta, gbc);

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
