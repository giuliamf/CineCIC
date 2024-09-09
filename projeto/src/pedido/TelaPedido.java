package pedido;

import filme.classe.Filme;
import filme.classe.GerenciaFilme;
import pedido.classe.GerenciaPedido;
import pedido.classe.Ingresso;
import pedido.classe.Pedido;
import sala.classe.GerenciaSala;
import sala.classe.Sala;
import sessao.classe.GerenciaSessao;
import sessao.classe.Sessao;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaPedido extends JFrame {
    GerenciaSessao gerenciaSessao = new GerenciaSessao();
    GerenciaFilme gerenciaFilme = new GerenciaFilme();
    GerenciaSala gerenciaSalas = new GerenciaSala();

    public TelaPedido(int idPedido) {
        GerenciaPedido gerenciaPedido = new GerenciaPedido();

        setTitle(String.format("CineCIC | Pedido nº %d", idPedido));    // Título da janela
        setSize(500, 450);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Ícone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Obter ingressos do pedido
        List<Ingresso> ingressos = gerenciaPedido.obterIngressosPorPedido(idPedido);

        // Painel de ingressos com layout vertical
        JPanel ingressosPanel = new JPanel();
        ingressosPanel.setLayout(new BoxLayout(ingressosPanel, BoxLayout.Y_AXIS));
        ingressosPanel.setPreferredSize(new Dimension(450, 150 * ingressos.size())); // tamanho do painel = quantidade de ingressos
        ingressosPanel.setMinimumSize(new Dimension(450, 150 * ingressos.size()));

        for (Ingresso ingresso : ingressos) {
            // Painel de cada ingresso com borda
            JPanel ingressoPanel = new JPanel(new BorderLayout());
            ingressoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.darkGray));  // Apenas a borda inferior

            criarIngressos(ingresso, ingressoPanel);  // Cria os componentes do painel do ingresso

            ingressosPanel.add(ingressoPanel);  // Adiciona o painel do ingresso ao painel de ingressos
        }

        // Adicionando o painel de ingressos dentro de um JScrollPane
        JScrollPane scrollPane = new JScrollPane(ingressosPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Scroll só aparece se necessário
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Remove o scroll horizontal

        // Adiciona o JScrollPane ao painel principal (usando BorderLayout.CENTER para garantir o scroll)
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel inferior com o valor total dos ingressos
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Adiciona borda para separação

        Pedido pedido = gerenciaPedido.obterPedido(idPedido);
        JLabel totalLabel = new JLabel(String.format("Valor Total: R$ %.2f", pedido.getValorTotal()));

        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(totalLabel);

        // Painel do botão de voltar
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(_ -> dispose());
        panelBotoes.add(btnVoltar);

        // Painel inferior
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(totalPanel, BorderLayout.NORTH);
        painelInferior.add(panelBotoes, BorderLayout.SOUTH);

        // Adiciona o painel de total e de botões ao painel principal
        mainPanel.add(painelInferior, BorderLayout.PAGE_END);

        add(mainPanel);  // Adiciona o painel principal ao frame
    }

    // Método para criar o painel de cada ingresso
    public void criarIngressos(Ingresso ingresso, JPanel ingressoPanel) {
        // Define o tamanho fixo do painel de ingresso
        ingressoPanel.setPreferredSize(new Dimension(450, 150));  // Tamanho fixo do painel
        ingressoPanel.setMaximumSize(new Dimension(450, 150));
        ingressoPanel.setMinimumSize(new Dimension(450, 150));

        // Obter informações da sessão e filme
        Sessao sessao = gerenciaSessao.obterSessao(ingresso.getIdSessao());
        Filme filme = gerenciaFilme.obterFilme(sessao.getIdFilme());
        Sala sala = gerenciaSalas.obterSala(sessao.getIdSala());

        String nomeFilme = filme.getTitulo();
        String tipoSala = sala.isEhVip() ? "VIP" : "Comum";
        String tipoSessao = sessao.isEh3d() ? "3D" : "2D";
        String idioma = sessao.isDublado() ? "Dublado" : "Legendado";
        String data = gerenciaSessao.converterDataParaString(sessao.getData());
        String horario = sessao.getHorario();
        int assento = ingresso.getAssentoEscolhido();
        String tipoIngresso = ingresso.isMeia() ? "Meia-Entrada" : "Inteira";
        float subtotal = ingresso.getSubtotal();

        // Painel de imagem à esquerda (QR Code)
        JPanel qrPanel = new JPanel();
        qrPanel.setPreferredSize(new Dimension(130, 130));  // Tamanho da área do QR code

        // Ícone do QR code
        ImageIcon qrCode = new ImageIcon("Interface/Imagens/CineCIC.png");

        // Redimensiona a imagem do QR code
        Image img = qrCode.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);

        // Adiciona a imagem redimensionada ao JLabel
        JLabel qrCodeLabel = new JLabel(new ImageIcon(img));

        // Adiciona o JLabel ao painel
        qrPanel.add(qrCodeLabel);

        // Adiciona o painel de QR à esquerda
        ingressoPanel.add(qrPanel, BorderLayout.WEST);

        // Painel com as informações do ingresso, layout vertical (1 coluna)
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));  // Uma coluna para exibir itens um abaixo do outro
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Espaçamento interno

        // Adicionando as informações ao painel, uma abaixo da outra
        infoPanel.add(new JLabel(String.format("%s [%s] [%s]", nomeFilme, tipoSessao, idioma)));
        infoPanel.add(new JLabel(String.format("Sala %d [%s]", sessao.getIdSala(), tipoSala)));
        infoPanel.add(new JLabel(String.format("Assento %d | %s", assento, tipoIngresso)));
        infoPanel.add(new JLabel(String.format("%s | %s", data.replace("-", "/"), horario)));
        infoPanel.add(new JLabel(String.format("R$ %.2f", subtotal)));

        ingressoPanel.add(infoPanel, BorderLayout.CENTER);  // Adiciona o painel de informações no centro
    }
}
