package usuarios.telasfuncionario;

import javax.swing.*;
import java.awt.*;

import filme.*;
import sala.*;
import sessao.*;
import usuarios.classe.Funcionario;
import usuarios.classe.Login;

public class TelaAreaFuncionario extends JFrame {
    public TelaAreaFuncionario() {
        setTitle("CineCIC | Área do Funcionário");    // título da janela
        setSize(350, 500);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("resources/icones/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel do meio para colocar itens que não vão no NORTH nem SOUTH
        JPanel panelMiddle = new JPanel();
        panelMiddle.setLayout(new BoxLayout(panelMiddle, BoxLayout.Y_AXIS));

        // Painel de título
        JLabel titleLabel = new JLabel("Área do Funcionário", JLabel.CENTER);
        Font titleFont = new Font("Tahoma", Font.BOLD, 20);
        titleLabel.setFont(titleFont);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Espaço para o título não ficar grudado na margem da página
        panel.add(Box.createVerticalStrut(45), BorderLayout.NORTH);

        // Painel dos botões de gerenciar conta
        JPanel panelGerenciar = new JPanel();
        panelGerenciar.setLayout(new FlowLayout());
        panelGerenciar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Gerenciar conta"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botão de atualizar cadastro

        JButton botaoVerDados = criarBotao("Visualizar dados", "resources/icones/view.png",230, 50);   // espaços extras para centralizar as imagens
        panelGerenciar.add(botaoVerDados);

        JButton botaoAttCadastro = criarBotao("Atualizar dados", "resources/icones/update.png",230, 50);   // espaços extras para centralizar as imagens
        panelGerenciar.add(botaoAttCadastro);

        // Botão de mudar a senha
        JButton botaoMudarSenha = criarBotao("Mudar a senha", "resources/icones/edit.png",230, 50);
        panelGerenciar.add(botaoMudarSenha);

        panelMiddle.add(panelGerenciar);

        // Painel dos botões de sessão
        JPanel panelSessao = new JPanel();
        panelSessao.setLayout(new FlowLayout());
        panelSessao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Gerenciar sessões"),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botão de cadastrar sessões
        JButton botaoAddSessao = criarBotao("Cadastrar nova sessão", "resources/icones/adicionar.png",230, 50);   // espaços extras para centralizar as imagens
        panelSessao.add(botaoAddSessao);

        // Colocar no painel do meio
        panelMiddle.add(panelSessao);

        // Painel dos botões de filme
        JPanel panelFilme = new JPanel();
        panelFilme.setLayout(new FlowLayout());
        panelFilme.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Gerenciar filmes"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botão de cadastrar filme
        JButton botaoAddFilme = criarBotao("Cadastrar novo filme", "resources/icones/adicionar.png",230, 50);   // espaços extras para centralizar as imagens
        panelFilme.add(botaoAddFilme);

        // Botão de remover filme
        JButton botaoRmvFilme = criarBotao("Gerenciar filmes em cartaz", "resources/icones/emcartaz.png", 230, 50);
        panelFilme.add(botaoRmvFilme);

        // Botão de editar filme
        JButton botaoEditFilme = criarBotao("Editar um filme", "resources/icones/edit.png", 230, 50);
        panelFilme.add(botaoEditFilme);

        // Colocar no painel do meio
        panelMiddle.add(panelFilme);

        // Painel dos botões de salas
        JPanel panelSala = new JPanel();
        panelSala.setLayout(new FlowLayout());
        panelSala.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Gerenciar salas"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botão de visualizar salas
        JButton botaoAddSala = criarBotao("Visualizar salas", "resources/icones/view.png",230, 50);   // espaços extras para centralizar as imagens
        panelSala.add(botaoAddSala);

        // Botão de abrir sala
        JButton botaoAbrirSala = criarBotao("Abrir salas", "resources/icones/abrir.png", 230, 50);
        panelSala.add(botaoAbrirSala);

        // Botão de fechar sala
        JButton botaoFecharSala = criarBotao("Fechar salas", "resources/icones/fechar.png", 230, 50);
        panelSala.add(botaoFecharSala);

        // Colocar no painel do meio
        panelMiddle.add(panelSala);

        // Botão para sair da conta, voltar e área do gerente
        JButton botaoVoltar = criarBotao("Voltar", "resources/icones/voltar.png", 100, 35);
        JButton botaoSair = criarBotao("Sair da conta", "resources/icones/sair.png", 140, 35);
        JButton botaoGerente = criarBotao("Área do Gerente", "resources/icones/gerente.png", 160, 35);

        // Colocar o botao de sair à direita
        JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRodape.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRodape.add(botaoVoltar);
        panelRodape.add(botaoGerente);
        panelRodape.add(botaoSair);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(panelMiddle, BorderLayout.CENTER);
        panel.add(panelRodape, BorderLayout.SOUTH);

        add(panel);
        pack();
    }

    public JButton criarBotao(String nomeBotao, String caminhoIcon, int larguraBotao, int alturaBotao) {
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
                // Botões de conta
                case "Visualizar dados" -> new TelaVisualizarDados().setVisible(true);
                case "Atualizar dados" -> new TelaAtualizarDadosFuncionario().setVisible(true);
                case "Mudar a senha" -> new TelaMudarSenhaFuncionario().setVisible(true);

                // Botões de sessão
                case "Cadastrar nova sessão" -> new TelaCadastrarSessao().setVisible(true);
                //case "Remover sessão existente" -> new TelaRemoverSessao().setVisible(true);
                //case "Editar sessão existente" -> new TelaEditarSessao().setVisible(true);

                // Botões de filme
                case "Cadastrar novo filme" -> new TelaCadastrarFilme().setVisible(true);
                case "Gerenciar filmes em cartaz" -> new TelaGerenciarCartaz().setVisible(true);
                case "Editar um filme" -> new TelaEditarFilme().setVisible(true);

                // Botões de sala
                case "Visualizar salas" -> new TelaEscolherVerSala().setVisible(true);
                case "Abrir salas" -> new TelaAbrirSala().setVisible(true);
                case "Fechar salas" -> new TelaFecharSala().setVisible(true);

                // Botões da área do gerente e de sair
                case "Área do Gerente" -> {
                    if (ehGerente()) {
                        new TelaAreaGerente().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Somente gerentes podem acessar essa área.");
                    }
                }
                case "Sair da conta" -> deslogarFuncionario();
                default -> dispose();
            }
        });
        return botao;
    }

    private boolean ehGerente() {
        Login login = new Login("Funcionario");
        Funcionario funcionario = login.obterFuncionarioLogado();
        return funcionario != null && (funcionario.getCargo().equals("Gerente"));
    }

    private void deslogarFuncionario() {
        Login login = new Login("Funcionario");
        Funcionario funcionario = login.obterFuncionarioLogado();
        if (funcionario != null) {
            login.removerLogin(funcionario.getIdFuncionario());
        } dispose();
    }

    public static void main(String[] args) {
        new TelaAreaFuncionario().setVisible(true);
    }

}
