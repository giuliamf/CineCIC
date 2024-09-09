package usuarios.telasfuncionario;

import usuarios.classe.GerenciaFuncionario;
import usuarios.classe.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaEditarFuncionario extends JFrame {
    JTextField txtNome, txtCpf, txtCargo;
    JPasswordField passField;
    JComboBox boxFuncionario, boxCargo;

    GerenciaFuncionario cadastroFuncionario = new GerenciaFuncionario();
    Map<String, Integer> mapFuncionarios = cadastroFuncionario.getFuncionarios();

    public TelaEditarFuncionario() {
        setTitle("CineCIC | Editar funcionário");    // Título da janela
        setSize(400, 400);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha a janela sem fechar a aplicação
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel de informações do filme
        JPanel panelEscolha = new JPanel(new GridBagLayout());
        panelEscolha.setBorder(BorderFactory.createTitledBorder("Escolha o funcionário"));

        // Configurações do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 0);   // Espaçamento entre os componentes
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;

        List<String> listaFuncionarios = new ArrayList<>(mapFuncionarios.keySet());

        // Área de escolher o funcionário
        JLabel lblFuncionario = new JLabel();
        lblFuncionario.setHorizontalAlignment(SwingConstants.CENTER);
        boxFuncionario = new JComboBox(listaFuncionarios.toArray(new String[0]));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEscolha.add(boxFuncionario, gbc);

        // Painel de informações do funcionário
        JPanel panelInfos = new JPanel(new GridBagLayout());
        panelInfos.setBorder(BorderFactory.createTitledBorder("Dados do funcionário"));

        // Configurações do GridBagLayout
        gbc.fill = GridBagConstraints.HORIZONTAL;   // Preenchimento horizontal
        gbc.insets = new Insets(5, 0, 10, 0);   // Espaçamento entre os componentes

        // Área de digitar as informações
        // Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setHorizontalAlignment(SwingConstants.LEFT);
        txtNome = new JTextField(20);

        // Tamanho da caixa do nome usando GBC
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;  // Peso que por algum motivo padronizou os outros (distância do label para o field)
        panelInfos.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelInfos.add(txtNome, gbc);

        // CPF
        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setHorizontalAlignment(SwingConstants.LEFT);
        txtCpf = new JTextField(20);

        // Tamanho da caixa do cpf usando GBC
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelInfos.add(lblCPF, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelInfos.add(txtCpf, gbc);

        // Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
        passField = new JPasswordField(20);

        // Tamanho da caixa da senha usando GBC
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelInfos.add(lblSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelInfos.add(passField, gbc);

        // Cargo
        // Definindo a lista de cargos
        ArrayList<String> listaCargos = new ArrayList<>();
        listaCargos.add("");
        listaCargos.add("Gerente");
        listaCargos.add("Funcionário");


        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setHorizontalAlignment(SwingConstants.LEFT);
        boxCargo = new JComboBox<>(listaCargos.toArray());
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelInfos.add(lblCargo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelInfos.add(boxCargo, gbc);

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

    private JButton criarBotao(String nomeBotao, String caminhoIcon) {
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
                String funcionarioSelecionado = (String) boxFuncionario.getSelectedItem();
                int idFuncionario = mapFuncionarios.get(funcionarioSelecionado);

                if (funcionarioSelecionado != null) {

                    Funcionario funcionario = cadastroFuncionario.buscarFuncionarioId(idFuncionario);

                    if (funcionario != null) {
                        String nome = txtNome.getText();
                        String cpf = txtCpf.getText();
                        String senha = new String(passField.getPassword());
                        String cargo = (String) boxCargo.getSelectedItem();

                        if (!nome.isEmpty()) {
                            funcionario.setNome(nome);
                        }
                        if (!cpf.isEmpty()) {
                            funcionario.setCpf(cpf);
                        }
                        if (!senha.isEmpty()) {
                            funcionario.setSenha(senha);
                        }
                        if (!cargo.isEmpty()) {
                            funcionario.setCargo(cargo);
                        }

                        String resultado = cadastroFuncionario.atualizarCadastro(funcionario);
                        if (resultado.equals("sucesso")) {
                            JOptionPane.showMessageDialog(this, "Cadastro atualizado com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Erro ao atualizar cadastro!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Funcionário não encontrado.");
                    }
                }
            } else {
                dispose();
            }
            dispose();
        });
        return botao;
    }
}
