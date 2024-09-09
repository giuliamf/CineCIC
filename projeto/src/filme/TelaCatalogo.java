package filme;

import filme.classe.Filme;
import filme.classe.GerenciaFilme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class TelaCatalogo extends JFrame {

    // Construtor da classe
    public TelaCatalogo() {
        setTitle("CineCIC | Catálogo");    // Título da janela
        setSize(1000, 820);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do filme
        JPanel fotosPanel = new JPanel(new GridBagLayout());
        fotosPanel.setBorder(BorderFactory.createTitledBorder("Filmes em cartaz"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Área dos filmes
        // Painel de filmes no topo
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcTop = new GridBagConstraints();   // Configurações do GridBagLayout
        gbcTop.gridx = 0;
        gbcTop.gridy = 0;
        gbcTop.insets = new Insets(5, 10, 10, 10);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcBottom = new GridBagConstraints();    // Configurações do GridBagLayout
        gbcBottom.gridx = 0;
        gbcBottom.gridy = 1;
        gbcBottom.insets = new Insets(5, 10, 10, 10);

        GerenciaFilme info = new GerenciaFilme();
        // Detalhes do filme (objeto Filme)
        Map<Integer, Filme> filmes = info.carregarFilme();

        // Adiciona os filmes ao painel
        int contador = 0;
        for (Map.Entry<Integer, Filme> map : filmes.entrySet()) {
            Filme filme = map.getValue();
            if (contador < filmes.size() && contador < 10 && filme.isEmCartaz()) {
                if (contador < 5) {   // Adiciona os 5 primeiros filmes no topo
                    addMovieToPanel(topPanel, filme.getIndice(), filme.getUrlImg(), gbcTop.gridx);
                    gbcTop.gridx++;
                } else {
                    addMovieToPanel(bottomPanel, filme.getIndice(), filme.getUrlImg(), gbcBottom.gridx);
                    gbcBottom.gridx++;
                }
                contador++;
            }
        }

        while (gbcTop.gridx < 5) {
            topPanel.add(Box.createHorizontalGlue(), gbcTop);
            gbcTop.gridx++;
        }

        while (gbcBottom.gridx < 5) {
            bottomPanel.add(Box.createHorizontalGlue(), gbcBottom);
            gbcBottom.gridx++;
        }

        // Adicionando os painéis no painel principal
        fotosPanel.add(topPanel, gbcTop);
        fotosPanel.add(bottomPanel, gbcBottom);

        // Painel para o botão de voltar
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton botaoVoltar = criarBotao("Voltar", "Interface/Icons/voltar.png");
        panelBotoes.add(botaoVoltar);

        // Adicionando o painel principal no painel
        panel.add(fotosPanel, BorderLayout.NORTH);
        panel.add(panelBotoes, BorderLayout.SOUTH);
        // Adicionando o painel no JFrame
        add(panel);

    }

    // Adiciona um filme ao painel
    private void addMovieToPanel(JPanel panel, int indice, String caminhoImg, int gridx) {
        GridBagConstraints gbc = new GridBagConstraints();  // Configurações do GridBagLayout
        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(createMovie(indice, caminhoImg), gbc);  // Adiciona o filme ao painel
    }

    // Cria um filme
    private JLabel createMovie(int indice, String caminhoImg) {
        JLabel movie = new JLabel();
        ImageIcon icon = new ImageIcon(caminhoImg);   // Imagem do filme
        Image img = icon.getImage().getScaledInstance(175, 300, java.awt.Image.SCALE_SMOOTH);

        movie.setIcon(new ImageIcon(img));  // Define a imagem no rótulo
        movie.setHorizontalAlignment(SwingConstants.CENTER);    // Centraliza horizontalmente
        movie.setVerticalAlignment(SwingConstants.CENTER);  // Centraliza verticalmente
        movie.setPreferredSize(new Dimension(175, 300));    // Tamanho do rótulo

        // Ação de clique no rótulo
        movie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaAreaCatalogo(indice).setVisible(true);
            }
        });
        return movie;
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(100, 50)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            if (nomeBotao.equals("Voltar")) {
                dispose();
            }
        });
        return botao;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCatalogo().setVisible(true));
    }
}
