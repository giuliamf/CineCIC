package filme;

import javax.swing.*;
import java.awt.*;
import filme.classe.Filme;
import filme.classe.GerenciaFilme;

public class TelaDetalhesFilme extends JFrame {
    // Construtor da classe
    public TelaDetalhesFilme(int indice) {
        // Carregar informações dos filmes
        GerenciaFilme gerenciarFilme = new GerenciaFilme();
        gerenciarFilme.carregarFilme();

        // Obtém o filme através do índice fornecido
        Filme filme = gerenciarFilme.obterFilme(indice);

        // Título da janela usando o título do filme
        String tituloPagina = String.format("CineCIC | %s", filme.getTitulo());
        setTitle(tituloPagina);    // Título da janela
        setSize(680, 520);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de informações do filme
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder(String.format("Detalhes do filme: %s", filme.getTitulo())));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Painel para a imagem
        JLabel fotoFilme = new JLabel();
        ImageIcon image = new ImageIcon(filme.getUrlImg());
        Image img = image.getImage().getScaledInstance(228, 388, java.awt.Image.SCALE_SMOOTH);
        fotoFilme.setIcon(new ImageIcon(img));

        // Centraliza a imagem no JLabel
        fotoFilme.setHorizontalAlignment(SwingConstants.CENTER);
        fotoFilme.setVerticalAlignment(SwingConstants.CENTER);
        fotoFilme.setPreferredSize(new Dimension(310, 500));

        // Adiciona a imagem ao painel de informações
        panelInfos.add(fotoFilme, gbc);

        // Painel para os labels
        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.Y_AXIS));
        panelLabels.setPreferredSize(new Dimension(310, 500));

        // Ajusta a posição do GridBagConstraints para a segunda coluna
        gbc.gridx = 1;
        gbc.gridy = 0;

        // Cria e adiciona os labels com as informações do filme
        JLabel lblGenero = new JLabel("Gênero: " + filme.getGenero());
        JLabel lblDuracao = new JLabel("Duração: " + filme.getDuracao() + " minutos");

        // Verifica e formata a classificação indicativa
        String classificacao = filme.getClassificacaoIndicativa();
        String textoClassificacao;

        if (classificacao.equals("Livre")) {
            textoClassificacao = "Classificação: " + classificacao;
        } else {
            textoClassificacao = "Classificação: " + classificacao + " anos";
        }
        JLabel lblClassificacao = new JLabel(textoClassificacao);

        JLabel lblLancamento = new JLabel("É lançamento: " + (filme.isEhLancamento() ? "Sim" : "Não"));
        JLabel lblSinopse = new JLabel("<html><body style='width: 300px'>" + filme.getSinopse() + "</body></html>");

        // Adiciona os labels ao painel de labels
        panelLabels.add(lblGenero);
        panelLabels.add(lblDuracao);
        panelLabels.add(lblClassificacao);
        panelLabels.add(lblLancamento);
        panelLabels.add(lblSinopse);

        // Adiciona o painel de labels ao painel de informações
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panelInfos.add(panelLabels, gbc);

        // Adiciona o painel de informações ao painel principal
        panel.add(panelInfos, BorderLayout.CENTER);

        // Painel para o botão de voltar
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setPreferredSize(new Dimension(110, 30));
        botaoVoltar.setIconTextGap(5);
        ImageIcon icon = new ImageIcon("Interface/Icons/voltar.png");
        Image imgIcon = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botaoVoltar.setIcon(new ImageIcon(imgIcon));
        botaoVoltar.addActionListener(_ -> dispose());
        panelBotoes.add(botaoVoltar);

        // Adiciona o painel de botões ao painel principal
        panel.add(panelBotoes, BorderLayout.SOUTH);

        add(panel); // Adiciona o painel principal ao JFrame
    }
}
