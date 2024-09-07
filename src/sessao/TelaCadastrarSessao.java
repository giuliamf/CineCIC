package sessao;

import filme.classe.Filme;
import filme.classe.GerenciaFilme;
import sala.classe.GerenciaSala;
import sala.classe.Sala;

import com.toedter.calendar.JDateChooser;
import sessao.classe.GerenciaSessao;
import sessao.classe.Sessao;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TelaCadastrarSessao extends JFrame {
    GerenciaFilme gerenciaFilme = new GerenciaFilme();
    GerenciaSala gerenciaSala = new GerenciaSala();
    GerenciaSessao gerenciaSessao = new GerenciaSessao();

    JLabel lblFilme, lblSala, lblData, lblHorario;
    JPanel panelEscolha;
    JRadioButton rb3D, rbDublado, rbVIP;
    JComboBox boxFilme, boxSala;

    JDateChooser dateChooser;
    JComboBox<String> horasComboBox, minutosComboBox;

    public TelaCadastrarSessao() {
        setTitle("CineCIC | Cadastrar sessão");    // Título da janela
        setSize(450, 330);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do filme
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder("Informações da sessão"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 0);   // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.1;

        // Área de digitar as informações
        // Escolher filme
        lblFilme = new JLabel("Filme:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInfos.add(lblFilme, gbc);

        Map<Integer, Filme> filmes = gerenciaFilme.carregarFilme();
        List<String> titulos = new ArrayList<>();

        filmes.forEach((_, filme) -> {
            if (filme.isEmCartaz()) {
                titulos.add(filme.getTitulo());
            }
        });
        boxFilme = new JComboBox(titulos.toArray());
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelInfos.add(boxFilme, gbc);

        // Painel de escolha de 3D e dublado
        panelEscolha = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelInfos.add(panelEscolha, gbc);

        // 3D
        rb3D = new JRadioButton("3D");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelEscolha.add(rb3D, gbc);

        // Dublado
        rbDublado = new JRadioButton("Dublado");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelEscolha.add(rbDublado, gbc);

        // VIP
        rbVIP = new JRadioButton("VIP");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelEscolha.add(rbVIP, gbc);

        // Escolher a sala
        lblSala = new JLabel("Sala:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfos.add(lblSala, gbc);

        Map<Integer, Sala> salas = gerenciaSala.carregarSala();
        List<Integer> idSalas = new ArrayList<>();

        // Método para carregar salas com base na seleção 3D
        carregarSalas(idSalas, salas, rb3D.isSelected(), rbVIP.isSelected());

        boxSala = new JComboBox(idSalas.toArray());
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelInfos.add(boxSala, gbc);

        // Adicionando ActionListener para atualizar boxSala quando rb3D ou rbVIP for selecionado ou desmarcado
        rb3D.addActionListener(_ -> {
            carregarSalas(idSalas, salas, rb3D.isSelected(), rbVIP.isSelected());
            boxSala.setModel(new DefaultComboBoxModel(idSalas.toArray()));
        });

        rbVIP.addActionListener(_ -> {
            carregarSalas(idSalas, salas, rb3D.isSelected(), rbVIP.isSelected());
            boxSala.setModel(new DefaultComboBoxModel(idSalas.toArray()));
        });


        // Escolher a data
        lblData = new JLabel("Data:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInfos.add(lblData, gbc);

        dateChooser = new JDateChooser();

        // Definindo a data mínima (hoje)
        dateChooser.setMinSelectableDate(new Date());

        // Definindo a data máxima (30 dias a partir de hoje)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 45);
        dateChooser.setMaxSelectableDate(cal.getTime());

        dateChooser.setDate(new Date()); // Define a data atual como padrão
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelInfos.add(dateChooser, gbc);

        // Escolher o horário
        lblHorario = new JLabel("Horário:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelInfos.add(lblHorario, gbc);

        // Cria os ComboBoxes para horas e minutos
        String[] horas = new String[11];
        String[] minutos = new String[6];

        for (int i = 0; i < 11; i++) {
            horas[i] = String.format("%02d", i + 12);  // Limita entre 12h e 22h
        }

        for (int i = 0; i < 6; i++) {
            minutos[i] = String.format("%02d", i * 10);
        }

        horasComboBox = new JComboBox<>(horas);
        minutosComboBox = new JComboBox<>(minutos);

        // Adiciona ActionListener para horasComboBox
        horasComboBox.addActionListener(_ -> {
            String horaSelecionada = (String) horasComboBox.getSelectedItem();
            if ("22".equals(horaSelecionada)) {
                minutosComboBox.setSelectedItem("00");
                minutosComboBox.setEnabled(false);
            } else {
                minutosComboBox.setEnabled(true);
            }
        });

        // Adiciona os ComboBoxes ao painel
        JPanel horarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        horarioPanel.add(horasComboBox);
        horarioPanel.add(new JLabel(":"));
        horarioPanel.add(minutosComboBox);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panelInfos.add(horarioPanel, gbc);

        // Botões de salvar e cancelar
        // Painel para os botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton botaoSalvar = criarBotao("Salvar", "Interface/Icons/salvar.png");   // Botão de salvar
        JButton botaoCancelar = criarBotao("Cancelar", "Interface/Icons/cancelar.png");  // Botão de cancelar

        // Adiciona os botões ao painel
        panelBotoes.add(botaoSalvar);
        panelBotoes.add(botaoCancelar);

        // Adiciona os painéis ao painel principal
        panel.add(panelInfos, BorderLayout.NORTH);
        panel.add(panelBotoes, BorderLayout.SOUTH);
        add(panel);

    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon) {
        JButton botao = new JButton(nomeBotao);
        botao.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        botao.setHorizontalAlignment(SwingConstants.CENTER);  // Alinhar o texto pela esquerda
        botao.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        botao.setPreferredSize(new Dimension(120, 30)); // Tamanho do botão
        botao.setIconTextGap(10);

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(img));

        // Adiciona ActionListener para o botão
        botao.addActionListener(_ -> {
            if (nomeBotao.equals("Salvar")) {

                // Verificação para garantir que todos os campos obrigatórios estão preenchidos (todos menos os radioButtons)
                if (boxFilme.getSelectedItem() == null ||
                        boxSala.getSelectedItem() == null ||
                        dateChooser.getDate() == null ||
                        horasComboBox.getSelectedItem() == null ||
                        minutosComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios.");
                    return;
                }

                // Coletando as informações fornecidas
                // Obter informações para a nova sessão
                Integer idSala = (Integer) boxSala.getSelectedItem();
                Date data = dateChooser.getDate();
                String hora = (String) horasComboBox.getSelectedItem();
                String minuto = (String) minutosComboBox.getSelectedItem();
                String horario = hora + ":" + minuto;
                Integer idFilme = gerenciaFilme.getIdFilme((String) boxFilme.getSelectedItem());
                Filme filme = gerenciaFilme.obterFilme(idFilme);
                int duracao = filme.getDuracao(); // retorna a duração em minutos do filme para saber o intervalo de tempo em que a sala estará ocupada
                int idSessao = gerenciaSessao.gerarIdSessao();
                float valor = gerenciaSessao.calcularValorSessao(idFilme, idSala, rb3D.isSelected(), horario);
                Map<Integer, Boolean> assentos = gerenciaSessao.inicializarMapaAssentos(idSala);
                boolean eh3d = rb3D.isSelected();
                boolean ehDublado = rbDublado.isSelected();

                if (gerenciaSessao.verificarConflitoHorario(idSala, data, horario, duracao)) {
                    JOptionPane.showMessageDialog(this, "Há um conflito de horários com outra sessão.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (valor == -1) {
                    JOptionPane.showMessageDialog(this, "Erro ao calcular o valor da sessão.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cria a sessão (ativa por padrão ao ser criada)
                Sessao sessao = new Sessao(idSessao, idSala, idFilme, data, horario, ehDublado, eh3d, valor, true, assentos);

                // Adiciona a sessão ao arquivo
                String resultado = gerenciaSessao.cadastrarSessao(sessao);

                if (resultado.equals("sucesso")) {
                    JOptionPane.showMessageDialog(this, "Sessão cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar a sessão.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                dispose();
            }
        });

        return botao;
    }

    private void carregarSalas(List<Integer> idSalas, Map<Integer, Sala> salas, boolean apenas3D, boolean apenasVIP) {
        idSalas.clear();
        salas.forEach((_, sala) -> {
            boolean condicao3D = !apenas3D || sala.isEh3d();
            boolean condicaoVIP = !apenasVIP || sala.isEhVip();
            if (sala.isAberta() && condicao3D && condicaoVIP) {
                idSalas.add(sala.getIdSala());
            }
        });
    }

}
