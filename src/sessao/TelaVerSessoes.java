package sessao;

import pedido.TelaSelecionarAssento;
import sessao.classe.*;
import filme.classe.*;
import sala.classe.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.List;
import java.text.SimpleDateFormat;
import java.util.*;

public class TelaVerSessoes extends JFrame {
    private final JTable tabelaSessoes;
    private final DefaultTableModel modeloTabela;
    private final GerenciaSessao gerenciaSessao = new GerenciaSessao();
    private final GerenciaSala gerenciaSala = new GerenciaSala();

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final JComboBox<String> comboBoxData = new JComboBox<>();

    // Declarar o mapa para armazenar o mapeamento entre o índice da linha da tabela e o idSessao
    private final Map<Integer, Integer> indiceParaIdSessao = new HashMap<>();

    private final ArrayList<Integer> listIdFilmeSessao = new ArrayList<>();

    // Construtor da classe
    public TelaVerSessoes(int idFilme) {
        GerenciaFilme gerenciaFilme = new GerenciaFilme();
        Filme filme = gerenciaFilme.obterFilme(idFilme);

        listIdFilmeSessao.add(idFilme);

        setTitle(String.format("CineCIC | Assistir %s", filme.getTitulo()));
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ícone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Filtro de data
        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel lblData = new JLabel("Data:");

        Calendar calendar = Calendar.getInstance();

        // Adiciona o dia atual e os próximos 6 dias ao JComboBox (total de 7 dias)
        for (int i = 0; i < 7; i++) {
            comboBoxData.addItem(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        JButton btnFiltrar = criarBotao("Filtrar", "Interface/Icons/filter.png", 90, 30, idFilme);

        // Adiciona os componentes ao painel de filtros
        filtrosPanel.add(lblData);
        filtrosPanel.add(comboBoxData);
        filtrosPanel.add(btnFiltrar);

        // Configuração da tabela
        String[] colunas = {"Sala", "Horário", "Idioma", "Tipo de sessão", "Tipo de sala"};
        modeloTabela = new DefaultTableModel(colunas, 6);
        tabelaSessoes = new JTable(modeloTabela);
        tabelaSessoes.setFillsViewportHeight(true);

        // Configuração do sorter para ordenação por horário
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabela);
        tabelaSessoes.setRowSorter(sorter);

        // Centralizar o texto nas células da tabela
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabelaSessoes.getColumnCount(); i++) {
            tabelaSessoes.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }

        JScrollPane scrollPane = new JScrollPane(tabelaSessoes);

        // Carregar sessões para a data atual
        carregarSessoes(new Date(), idFilme);

        // Botões
        JButton btnProx = criarBotao("Próximo", "Interface/Icons/continuar.png", 105, 30, idFilme);
        JButton btnVoltar = criarBotao("Voltar", "Interface/Icons/voltar.png", 90, 30, idFilme);

        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotoes.add(btnVoltar);
        panelBotoes.add(btnProx);

        // Adicionando os componentes ao painel principal
        panel.add(filtrosPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionando o painel ao JFrame
        add(panel);
    }

    // Método para criar botões
    public JButton criarBotao(String nomeBotao, String caminhoIcon, int larguraBotao, int alturaBotao, int idFilme) {
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
                    int rowIndex = tabelaSessoes.getSelectedRow();
                    if (rowIndex == -1) {
                        JOptionPane.showMessageDialog(TelaVerSessoes.this,
                                "Escolha uma sessão.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int modelRowIndex = tabelaSessoes.convertRowIndexToModel(rowIndex);

                        // Recupera o idSessao do mapa
                        Integer idSessao = indiceParaIdSessao.get(modelRowIndex);
                        listIdFilmeSessao.add(idSessao);

                        if (idSessao == null) {
                            JOptionPane.showMessageDialog(TelaVerSessoes.this,
                                    "Sessão não encontrada.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            Map<Integer, Boolean> mapaAssentos = gerenciaSessao.obterMapaAssentos(idSessao);
                            new TelaSelecionarAssento(mapaAssentos, listIdFilmeSessao).setVisible(true);
                            dispose();
                        }
                    }
                }
                case "Filtrar" -> {
                    try {
                        Date dataFiltrada = sdf.parse((String) comboBoxData.getSelectedItem());
                        carregarSessoes(dataFiltrada, idFilme);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TelaVerSessoes.this,
                                "Erro ao filtrar por data.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                case "Voltar" -> dispose();
            }
        });

        return botao;
    }

    // Método para carregar as sessões com base na data e no ID do filme
    private void carregarSessoes(Date data, int idFilme) {
        modeloTabela.setRowCount(0); // Limpa a tabela antes de carregar novos dados
        indiceParaIdSessao.clear(); // Limpa o mapeamento antes de carregar novos dados

        SimpleDateFormat _ = new SimpleDateFormat("dd/MM/yyyy");
        Map<Integer, Sessao> sessoes = gerenciaSessao.obterSessoesPorDataEIdFilme(data, idFilme);

        int rowIndex = 0; // Índice da linha na tabela

        for (Map.Entry<Integer, Sessao> entrada : sessoes.entrySet()) {
            Sessao sessao = entrada.getValue();

            // Verifica se a sessão está ativa antes de adicioná-la à tabela
            if (!sessao.isAtiva()) {
                continue; // Pula para a próxima iteração se a sessão não estiver ativa
            }

            // Pegar se a sala é vip através da sessão.getIdSala
            Sala sala = gerenciaSala.obterSala(sessao.getIdSala());

            Object[] linha = {
                    sessao.getIdSala(),
                    sessao.getHorario(),
                    sessao.isDublado() ? "Dublado" : "Legendado",
                    sessao.isEh3d() ? "3D" : "2D",
                    sala.isEhVip() ? "VIP" : "Comum"
            };
            modeloTabela.addRow(linha);
            // Mapear o índice da linha ao idSessao
            indiceParaIdSessao.put(rowIndex, sessao.getIdSessao());
            rowIndex++; // Incrementa o índice da linha da tabela
        }
        //tabelaSessoes.getRowSorter().toggleSortOrder(1); // Ordenar por horário
    }

}
