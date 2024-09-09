package pedido;

import filme.classe.GerenciaFilme;
import pedido.classe.GerenciaPedido;
import pedido.classe.Pedido;
import sessao.classe.GerenciaSessao;
import sessao.classe.Sessao;
import usuarios.classe.Login;

import java.util.Collections;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaHistorico extends JFrame {
    private final JTable tabelaPedidos;
    private final DefaultTableModel modeloTabela;

    private List<Pedido> pedidos;
    private final Map<Integer, Integer> indiceParaIdPedido = new HashMap<>();

    private final GerenciaPedido gerenciaPedido = new GerenciaPedido();
    private final GerenciaSessao gerenciaSessao = new GerenciaSessao();
    private final GerenciaFilme gerenciaFilme = new GerenciaFilme();
    private final Login login = new Login("Cliente");

    public TelaHistorico(int idCliente) {

        // Pegar a lista de pedidos do cliente
        pedidos = gerenciaPedido.obterPedidosPorCliente(idCliente);

        setTitle("CineCIC | Histórico");    // Título da janela
        setSize(600, 400);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Configuração da tabela
        String[] colunas = {"Data", "Horário", "Filme", "Valor Total"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaPedidos = new JTable(modeloTabela);
        tabelaPedidos.setFillsViewportHeight(true);

        // Configuração do sorter para ordenação por horário
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabela);
        tabelaPedidos.setRowSorter(sorter);

        // Centralizar o texto nas células da tabela
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabelaPedidos.getColumnCount(); i++) {
            tabelaPedidos.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }

        JScrollPane scrollPane = new JScrollPane(tabelaPedidos);

        carregarPedidos(pedidos, idCliente);

        // Botões
        JButton btnProx = criarBotao("Próximo", "resources/icones/continuar.png", 105, 30);
        JButton btnVoltar = criarBotao("Voltar", "resources/icones/voltar.png", 90, 30);

        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.add(btnVoltar);
        panelBotoes.add(btnProx);

        // Adicionar a tabela ao painel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotoes, BorderLayout.SOUTH);

        add(panel);
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon, int larguraBotao, int alturaBotao) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(larguraBotao, alturaBotao)); // Tamanho do botão
        botao.setIconTextGap(5);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(17, 17, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        botao.addActionListener(_ -> {
            switch (nomeBotao) {
                case "Próximo" -> {
                    int rowIndex = tabelaPedidos.getSelectedRow();
                    if (rowIndex == -1) {
                        JOptionPane.showMessageDialog(TelaHistorico.this,
                                "Escolha um pedido.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int modeloRowIndex = tabelaPedidos.convertRowIndexToModel(rowIndex);

                        // Obter id do pedido
                        int idPedido = indiceParaIdPedido.get(modeloRowIndex);
                        new TelaPedido(idPedido).setVisible(true);
                    }
                }
                case "Voltar" -> dispose();
            }
        });
        return botao;
    }


    public void carregarPedidos(List<Pedido> pedidos, int idCliente) {
        modeloTabela.setRowCount(0); // Limpa a tabela antes de carregar novos dados
        int rowIndex = 0;
        indiceParaIdPedido.clear();

        // Inverte a ordem dos pedidos (último para o primeiro)
        Collections.reverse(pedidos);

        for (Pedido pedido : pedidos) {
            if (pedido.getIdUsuario() == idCliente) {
                int indice = modeloTabela.getRowCount();
                indiceParaIdPedido.put(indice, pedido.getIdPedido());

                int idSessao = pedido.getIngressos().getFirst().getIdSessao();
                Sessao sessao = gerenciaSessao.obterSessao(idSessao);

                String data = gerenciaSessao.converterDataParaString(gerenciaSessao.obterSessao(idSessao).getData());
                String horario = sessao.getHorario();
                String nomeFilme = gerenciaFilme.obterFilme(sessao.getIdFilme()).getTitulo();
                float valorTotal = pedido.getValorTotal();
                String valorTotalString = String.format("%.2f", valorTotal);

                modeloTabela.addRow(new Object[]{data.replace("-", "/"), horario, nomeFilme, valorTotalString.replace(".", ",")});

                indiceParaIdPedido.put(rowIndex, pedido.getIdPedido());
                rowIndex++;
            }

        }
    }
}
