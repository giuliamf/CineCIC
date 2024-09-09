package pedido;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import pedido.classe.Carrinho;
import pedido.classe.GerenciaIngresso;
import pedido.classe.Ingresso;

public class TelaEscolherIngresso extends JFrame {

    private final Carrinho carrinho;
    private final List<Integer> assentosSelecionados; // Manter uma referência aos assentos selecionados
    private final JPanel painelIngressos; // Painel de ingressos

    public TelaEscolherIngresso(List<Integer> assentosSelecionados) {

        this.assentosSelecionados = assentosSelecionados; // Inicializando os assentos selecionados
        carrinho = new Carrinho(); // Inicializando o carrinho

        setTitle("CineCIC | Escolher Ingressos");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ícone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel para seleção de ingressos
        painelIngressos = new JPanel();
        painelIngressos.setLayout(new BoxLayout(painelIngressos, BoxLayout.Y_AXIS));

        // Verifica o número de assentos selecionados para limitar a altura do painel
        int maxAssentosVisiveis = 5;

        if (assentosSelecionados.size() > maxAssentosVisiveis) {
            painelIngressos.setPreferredSize(new Dimension(400, maxAssentosVisiveis * 100)); // Limita a altura para 5 linhas
        }

        // Itera sobre os assentos selecionados para adicionar cada ingresso
        for (Integer assento : assentosSelecionados) {
            JPanel painelAssento = new JPanel(new FlowLayout());
            JLabel label = new JLabel("Assento " + assento);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Inteira", "Meia"});

            // Criar botão de remover diretamente usando o método criarBotao
            JButton btnRemover = criarBotaoRemover(assento);

            painelAssento.add(label);
            painelAssento.add(comboBox);
            painelAssento.add(btnRemover); // Adiciona o botão de remover
            painelIngressos.add(painelAssento);
        }

        // Adiciona o painelIngressos dentro de um JScrollPane
        JScrollPane scrollPane = new JScrollPane(painelIngressos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Ingressos"));

        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões de ação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = criarBotao("Revisar pedido", "Interface/Icons/review.png", 160);
        JButton btnVoltar = criarBotao("Cancelar", "Interface/Icons/cancelar.png", 110);

        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnConfirmar);

        panel.add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona o painel principal ao frame
        add(panel);
    }

    private void atualizarPainelIngressos() {
        painelIngressos.removeAll(); // Remove todos os componentes do painel

        for (Integer assento : assentosSelecionados) {
            JPanel painelAssento = new JPanel(new FlowLayout());
            JLabel label = new JLabel("Assento " + assento);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Inteira", "Meia"});

            // Criar botão de remover diretamente usando o método criarBotaoRemover
            JButton btnRemover = criarBotaoRemover(assento);

            painelAssento.add(label);
            painelAssento.add(comboBox);
            painelAssento.add(btnRemover); // Adiciona o botão de remover
            painelIngressos.add(painelAssento);
        }

        painelIngressos.revalidate(); // Atualiza a interface
        painelIngressos.repaint(); // Re-renderiza o painel
    }

    private Ingresso findIngressoByAssento(int assento) {
        for (Ingresso ingresso : carrinho.getIngressos()) {
            if (ingresso.getAssentoEscolhido() == assento) {
                return ingresso;
            }
        }
        return null; // Ingresso não encontrado
    }

    // Método auxiliar para criar botões
    private JButton criarBotao(String nomeBotao, String caminhoIcon, int larguraBotao) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(larguraBotao, 30)); // Tamanho do botão
        botao.setIconTextGap(5);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        botao.addActionListener(_ -> {
            switch (nomeBotao) {
                case "Revisar pedido" -> {
                    revisarPedido();
                    new TelaRevisarPedido(carrinho.getIngressos()).setVisible(true);
                }
                case "Cancelar" -> carrinho.limparCarrinho();
            }
            dispose(); // Fecha a tela atual
        });
        return botao;
    }

    private void revisarPedido() {
        // Itera sobre os assentos selecionados e verifica as opções Inteira/Meia
        Component[] componentes = painelIngressos.getComponents();
        for (Component componente : componentes) {
            JPanel painelAssento = (JPanel) componente;
            JLabel labelAssento = (JLabel) painelAssento.getComponent(0);
            JComboBox<String> comboBox = (JComboBox<String>) painelAssento.getComponent(1);

            // Obtém o número do assento do label
            String textoAssento = labelAssento.getText().replace("Assento ", "");
            int assento = Integer.parseInt(textoAssento);

            // Verifica a escolha (Inteira ou Meia) e atualiza o ingresso
            Ingresso ingresso = findIngressoByAssento(assento);
            GerenciaIngresso gerenciaIngresso = new GerenciaIngresso();
            if (ingresso != null) {
                boolean meia = Objects.equals(comboBox.getSelectedItem(), "Meia");
                ingresso.setMeia(meia); // Atualiza o ingresso para meia ou inteira

                // Atualiza o subtotal do ingresso
                ingresso.setSubtotal(gerenciaIngresso.calculaSubtotal(ingresso.getSubtotal(), meia));
            }
        }

        // Atualiza o arquivo CSV
        carrinho.atualizarCsvCarrinho();
    }

    private JButton criarBotaoRemover(int assento) {
        JButton botao = new JButton();
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        botao.setVerticalTextPosition(JButton.CENTER);
        botao.setPreferredSize(new Dimension(40, 25));

        // Redimensionar a imagem do ícone de remoção
        ImageIcon icon = new ImageIcon("Interface/Icons/delete.png");
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ação ao clicar no botão de remover
        botao.addActionListener(_ -> {
            Ingresso ingresso = findIngressoByAssento(assento);
            if (ingresso != null) {
                carrinho.removerIngresso(ingresso); // Remove o ingresso do carrinho
                assentosSelecionados.remove(Integer.valueOf(assento)); // Remove o assento da lista de selecionados
                atualizarPainelIngressos(); // Atualiza o painel de ingressos
            }
        });

        return botao;
    }

    private void confirmarEscolhaIngressos() {
        // Lógica para processar a escolha dos ingressos
        JOptionPane.showMessageDialog(this, "Ingressos selecionados com sucesso!");
        dispose(); // Fechar a janela após a confirmação
    }
}
