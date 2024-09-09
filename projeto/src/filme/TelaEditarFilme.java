package filme;

import filme.classe.Filme;
import filme.classe.GerenciaFilme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaEditarFilme extends JFrame {
    JTextField txtTitulo, txtUrl, txtGenero, txtDuracao, txtIndicativa;
    JTextArea txtSinopse;
    JRadioButton rbLancamento, rbNotLancamento, rbEmCartaz, rbNotEmCartaz;
    GerenciaFilme info = new GerenciaFilme();
    JComboBox box;

    public TelaEditarFilme() {
        setTitle("CineCIC | Editar filme");    // Título da janela
        setSize(500, 800);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do filme
        JPanel panelEscolha = new JPanel(new GridBagLayout());
        panelEscolha.setBorder(BorderFactory.createTitledBorder("Escolha qual filme deseja editar"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 0);   // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        Map<Integer, Filme> filmes = info.carregarFilme();
        List<String> titulos = new ArrayList<>();

        // Escolher filme
        JLabel lblEscolha = new JLabel();
        lblEscolha.setHorizontalAlignment(SwingConstants.CENTER);
        filmes.forEach((k, v) -> {
            titulos.add(v.getTitulo());
        });
        box = new JComboBox(titulos.toArray());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEscolha.add(box, gbc);

        // Painel de informações do filme
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder("Informações do filme"));

        // Configurações do GridBagLayout
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 0);   // Espaçamento entre os componentes

        // Área de digitar as informações

        // Título
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        txtTitulo = new JTextField(20);

        // Tamanho da caixa do título usando GBC
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;  // peso que por algum motivo padronizou os outros (distancia do label para o field)
        panelInfos.add(lblTitulo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelInfos.add(txtTitulo, gbc);

        // URL
        JLabel lblUrl = new JLabel("Url da imagem:");
        lblUrl.setHorizontalAlignment(SwingConstants.LEFT);
        txtUrl = new JTextField(20);
        // Tamanho da caixa da url da imagem usando GBC
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInfos.add(lblUrl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelInfos.add(txtUrl, gbc);

        // Gênero
        JLabel lblGenero = new JLabel("Gênero:");
        lblGenero.setHorizontalAlignment(SwingConstants.LEFT);
        txtGenero = new JTextField(20);

        // Tamanho da caixa do gênero usando GBC
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfos.add(lblGenero, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelInfos.add(txtGenero, gbc);

        // Duração
        JLabel lblDuracao = new JLabel("Duração:");
        lblDuracao.setHorizontalAlignment(SwingConstants.LEFT);
        txtDuracao = new JTextField(20);

        // Tamanho da caixa de duração usando GBC
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInfos.add(lblDuracao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelInfos.add(txtDuracao, gbc);

        // Classificação Indicativa
        JLabel lblIndicativa = new JLabel("Classificação Indicativa:");
        lblIndicativa.setHorizontalAlignment(SwingConstants.LEFT);
        txtIndicativa = new JTextField(20);

        // Tamanho da caixa de duração usando GBC
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelInfos.add(lblIndicativa, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelInfos.add(txtIndicativa, gbc);

        // RadioButton de em cartaz
        /*JLabel lblEmCartaz = new JLabel("Está em cartaz?");
        lblEmCartaz.setHorizontalAlignment(SwingConstants.LEFT);
        rbEmCartaz = new JRadioButton("Sim");
        rbNotEmCartaz = new JRadioButton("Não");

        // Grupo para que apenas um possa ser verdadeiro
        ButtonGroup grupoCartaz = new ButtonGroup();
        grupoCartaz.add(rbEmCartaz);
        grupoCartaz.add(rbNotEmCartaz);

        // Painel para os botões de sim e não
        JPanel panelEmCartaz = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        panelEmCartaz.add(rbEmCartaz);
        panelEmCartaz.add(rbNotEmCartaz);

        // Onde ficam os botões de sim e não
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panelInfos.add(lblEmCartaz, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;  // usar duas colunas
        panelInfos.add(panelEmCartaz, gbc);*/


        // RadioButton de lançamento
        JLabel lblLancamento = new JLabel("É lançamento?");
        lblLancamento.setHorizontalAlignment(SwingConstants.LEFT);
        rbLancamento = new JRadioButton("Sim");
        rbNotLancamento = new JRadioButton("Não");

        // Grupo para que apenas um possa ser verdadeiro
        ButtonGroup grupoLancamento = new ButtonGroup();
        grupoLancamento.add(rbLancamento);
        grupoLancamento.add(rbNotLancamento);

        // Painel para os botões de sim e não
        JPanel panelLancamento = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        panelLancamento.add(rbLancamento);
        panelLancamento.add(rbNotLancamento);

        // Onde ficam os botões de sim e não
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panelInfos.add(lblLancamento, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;  // usar duas colunas
        panelInfos.add(panelLancamento, gbc);

        // Sinopse
        JLabel lblSinopse = new JLabel("Sinopse:");
        lblSinopse.setHorizontalAlignment(SwingConstants.LEFT);
        txtSinopse = new JTextArea(20, 20);   // Caixa de texto
        txtSinopse.setLineWrap(true);   // Quebrar linha
        txtSinopse.setWrapStyleWord(true);  // Quebrar palavra
        JScrollPane scrollPane = new JScrollPane(txtSinopse);   // Barra de rolagem
        //scrollPane.setPreferredSize(new Dimension(20, 20));

        // Tamanho da caixa de sinopse usando GBC
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        panelInfos.add(lblSinopse, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;  // usar duas colunas
        gbc.gridheight = 2; // altura da caixa
        gbc.fill = GridBagConstraints.BOTH; // permitir que a caixa cresça
        panelInfos.add(scrollPane, gbc);

        /* Redimensionar a barra de rolagem
        Dimension preferredSize = new Dimension(scrollPane.getPreferredSize().width, txtSinopse.getPreferredSize().height);
        scrollPane.setPreferredSize(preferredSize);*/

        // Botões de salvar e cancelar
        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Salvar", "Interface/Icons/salvar.png");   // Botão de salvar
        JButton botaoCancelar = criarBotao("Cancelar", "Interface/Icons/cancelar.png");  // Botão de cancelar

        // Adiciona os botões ao painel
        panelBotoes.add(botaoSalvar);
        panelBotoes.add(botaoCancelar);

        // Adiciona os painéis ao painel principal
        panel.add(panelEscolha, BorderLayout.NORTH);
        panel.add(panelInfos, BorderLayout.CENTER);
        panel.add(panelBotoes, BorderLayout.SOUTH);
        add(panel);
    }

    // Método para criar botões com ícones e ações específicas
    public JButton criarBotao(String nomeBotao, String caminhoIcon) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.LEFT);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(130, 50)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Ações baseadas em qual botão foi clicado
        botao.addActionListener(_ -> {
            if (nomeBotao.equals("Salvar")) {
                String filmeSelecionado = (String) box.getSelectedItem();

                // Encontre o filme original usando o filme selecionado
                Filme filmeOriginal = info.getFilmes().values().stream()
                        .filter(f -> f.getTitulo().equals(filmeSelecionado))
                        .findFirst()
                        .orElse(null);

                if (filmeOriginal != null) {
                    int indiceOriginal = filmeOriginal.getIndice();

                    String titulo = txtTitulo.getText().isEmpty() ? null : txtTitulo.getText();
                    String urlImg = txtUrl.getText().isEmpty() ? null : txtUrl.getText();
                    String genero = txtGenero.getText().isEmpty() ? null : txtGenero.getText();
                    short duracao = txtDuracao.getText().isEmpty() ? 0 : Short.parseShort(txtDuracao.getText());
                    String classificacaoIndicativa = txtIndicativa.getText().isEmpty() ? null : txtIndicativa.getText();
                    String sinopse = txtSinopse.getText().isEmpty() ? null : txtSinopse.getText();

                    // Verifique se algum botão de rádio Em Cartaz foi selecionado
                    boolean emCartaz = filmeOriginal.isEmCartaz(); // Mantém o valor original

                    // Verifique se algum botão de rádio Lançamento foi selecionado
                    Boolean ehLancamento;
                    if (rbLancamento.isSelected()) {
                        ehLancamento = true;
                    } else if (rbNotLancamento.isSelected()) {
                        ehLancamento = false;
                    } else {
                        ehLancamento = filmeOriginal.isEhLancamento(); // Mantém o valor original
                    }

                    // Crie um novo filme com as informações editadas
                    Filme filmeEditado = new Filme(indiceOriginal, emCartaz, urlImg, titulo, genero, duracao, classificacaoIndicativa, ehLancamento, sinopse);

                    // Chame o método de edição passando o filme editado
                    String resultado = info.editarFilme(filmeSelecionado, filmeEditado);

                    if (resultado.equals("sucesso")) {
                        JOptionPane.showMessageDialog(this, "Filme atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao atualizar filme!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Filme não encontrado.");
                }
            }

            dispose();
        });
        return botao;
    }

}