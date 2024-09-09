package pedido;

import pedido.classe.Carrinho;
import pedido.classe.Ingresso;
import sessao.TelaVerSessoes;
import sessao.classe.GerenciaSessao;
import sessao.classe.Sessao;
import usuarios.TelaLoginCliente;
import usuarios.classe.Login;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class TelaSelecionarAssento extends JFrame {

    private final Map<Integer, JButton> botoesAssentos;

    public TelaSelecionarAssento(Map<Integer, Boolean> mapaAssentos, ArrayList<Integer> listIdFilmeSessao) {
        this.botoesAssentos = new HashMap<>();

        if (mapaAssentos == null || mapaAssentos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mapa de assentos não encontrado ou vazio.");
            return;
        }

        // listIdFilmeSessao contém o id do filme na posição 0 e o id da sessão na posição 1

        setTitle("CineCIC | Escolher assentos");
        setSize(1450, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Adicionando gaps horizontais e verticais
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adicionando margens ao redor do painel principal

        // Painel que representa a tela do cinema
        JPanel painelTelaCinema = new JPanel();
        painelTelaCinema.setPreferredSize(new Dimension(600, 30));
        painelTelaCinema.setBackground(Color.DARK_GRAY);
        JLabel labelTela = new JLabel("Tela", JLabel.CENTER);
        labelTela.setForeground(Color.WHITE);
        painelTelaCinema.add(labelTela);

        panel.add(painelTelaCinema, BorderLayout.NORTH);

        // Painel dos assentos usando FlowLayout para manter o tamanho dos botões fixo
        JPanel painelAssentos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Espaço entre os botões
        painelAssentos.setPreferredSize(new Dimension(700, 500)); // Define um tamanho preferido para o painel de assentos
        painelAssentos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adicionando margens ao redor do painel de assentos

        Dimension tamanhoBotaoAssento = new Dimension(80, 50); // Tamanho específico para os botões de assentos

        for (Map.Entry<Integer, Boolean> entry : mapaAssentos.entrySet()) {
            Integer numeroAssento = entry.getKey();
            Boolean disponivel = entry.getValue();
            JButton botao = new JButton(String.valueOf(numeroAssento));
            botao.setPreferredSize(tamanhoBotaoAssento);
            botao.setMinimumSize(tamanhoBotaoAssento);
            botao.setMaximumSize(tamanhoBotaoAssento);

            if (disponivel) {
                botao.setBackground(Color.white);
                botao.setToolTipText("Clique para selecionar este assento");
            } else {
                //botao.setBackground(Color.GRAY);
                botao.setEnabled(false);
                botao.setToolTipText("Assento indisponível");
            }

            botao.addActionListener(_ -> {
                if (botao.getBackground() == Color.white) {
                    botao.setBackground(Color.lightGray);
                    botao.setToolTipText("Clique para desmarcar este assento");
                } else if (botao.getBackground() == Color.lightGray) {
                    botao.setBackground(Color.white);
                    botao.setToolTipText("Clique para selecionar este assento");
                }
            });

            botoesAssentos.put(numeroAssento, botao);
            painelAssentos.add(botao);
        }

        JScrollPane scrollPane = new JScrollPane(painelAssentos);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de confirmação na parte inferior
        JPanel painelConfirmacao = new JPanel();
        painelConfirmacao.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Adicionando gaps horizontais e verticais
        JButton btnConfirmar = criarBotao("Próximo", "resources/icones/continuar.png", 120, 30, listIdFilmeSessao);
        JButton btnVoltar = criarBotao("Voltar", "resources/icones/voltar.png", 105, 30, listIdFilmeSessao);
        JButton btnCancelar = criarBotao("Cancelar", "resources/icones/cancelar.png", 120, 30, listIdFilmeSessao);


        painelConfirmacao.add(btnCancelar);
        painelConfirmacao.add(btnVoltar);
        painelConfirmacao.add(btnConfirmar);

        panel.add(painelConfirmacao, BorderLayout.SOUTH);

        add(panel);
    }

    private void confirmarSelecao(int idSessao) {
        List<Integer> assentosSelecionados = new ArrayList<>();

        for (Map.Entry<Integer, JButton> entry : botoesAssentos.entrySet()) {
            JButton botao = entry.getValue();
            if (botao.getBackground() == Color.lightGray) {
                assentosSelecionados.add(entry.getKey());
            }
        }

        if (!assentosSelecionados.isEmpty()) {
            // Verificar se o cliente está logado
            Login login = new Login("Cliente");
            if (!login.verificarSeLogado()) {
                // Redirecionar para a tela de login
                new TelaLoginCliente().setVisible(true);
                dispose();
                return;
            }

            // Adicionar ingressos ao carrinho
            Carrinho carrinho = new Carrinho();
            int idCliente = login.obterIdLogado();

            GerenciaSessao gerenciaSessao = new GerenciaSessao();
            Sessao sessao = gerenciaSessao.obterSessao(idSessao);

            for (int assento : assentosSelecionados) {
                Ingresso ingresso = new Ingresso(0, idCliente, idSessao, assento, sessao.getValor(), false);
                // A meia será false até ser modificada na tela de escolher ingresso
                // O id será 0 até ir para o pedido concluído
                carrinho.adicionarIngresso(ingresso);
            }

            new TelaEscolherIngresso(assentosSelecionados).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione pelo menos um assento.");
        }
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon, int larguraBotao, int alturaBotao, ArrayList<Integer> listIdFilmeSessao) {

        int idFilme = listIdFilmeSessao.get(0);
        int idSessao = listIdFilmeSessao.get(1);

        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(larguraBotao, alturaBotao)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            switch (nomeBotao) {
                case "Próximo" -> {
                    confirmarSelecao(idSessao);
                    dispose();
                }
                case "Voltar" -> {
                    new TelaVerSessoes(idFilme).setVisible(true);
                    dispose();
                }
                case "Cancelar" -> {
                    dispose();
                }
                default -> dispose();
            }
        });
        return botao;
    }
}
