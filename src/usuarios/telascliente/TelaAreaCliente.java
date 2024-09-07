package usuarios.telascliente;

import filme.TelaCatalogo;
import pedido.TelaCarrinho;
import pedido.TelaHistorico;
import pedido.classe.Carrinho;
import promocao.*;
import usuarios.classe.Cliente;
import usuarios.classe.Login;

import javax.swing.*;
import java.awt.*;

public class TelaAreaCliente extends JFrame {
    private final Carrinho carrinho = new Carrinho();
    private final int idCliente = new Login("Cliente").obterIdLogado();
    // Construtor da tela
    public TelaAreaCliente() {
        setTitle("CineCIC | Área do Cliente");    // título da janela
        setSize(460, 520);  // tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // não sair ao fechar
        setLocationRelativeTo(null);    // centralizar

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel
        JPanel panel = new JPanel(new BorderLayout());

        // Painel do meio para colocar itens que não vão no NORTH nem SOUTH
        JPanel panelMiddle = new JPanel();
        panelMiddle.setLayout(new BoxLayout(panelMiddle, BoxLayout.Y_AXIS));

        // Painel de título
        JLabel titleLabel = new JLabel("Área do Cliente", JLabel.CENTER);
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
                BorderFactory.createEmptyBorder(10, 10, 70, 10)
        ));

        // Botão de atualizar cadastro

        JButton botaoVerDados = criarBotao("Ver dados da conta", "Interface/Icons/view.png",200, 50);   // espaços extras para centralizar as imagens
        panelGerenciar.add(botaoVerDados);

        JButton botaoAttCadastro = criarBotao("Atualizar cadastro", "Interface/Icons/update.png",200, 50);   // espaços extras para centralizar as imagens
        panelGerenciar.add(botaoAttCadastro);

        // Botão de mudar a senha
        JButton botaoMudarSenha = criarBotao("Mudar a senha", "Interface/Icons/edit.png",200, 50);
        panelGerenciar.add(botaoMudarSenha);

        // Botão de excluir conta
        JButton botaoExcluirConta = criarBotao("Excluir a conta", "Interface/Icons/lixeira.png",200, 50);
        panelGerenciar.add(botaoExcluirConta);

        // Colocar no painel do meio
        panelMiddle.add(panelGerenciar);

        // Painel dos botões de pedido
        JPanel panelPedido = new JPanel();
        panelPedido.setLayout(new FlowLayout());
        panelPedido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Pedidos"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Botão de fazer pedido
        JButton botaoAddPedido = criarBotao("Fazer pedido", "Interface/Icons/pedido.png",200, 50);   // espaços extras para centralizar as imagens
        panelPedido.add(botaoAddPedido);

        // Botão de carrinho
        JButton botaoCarrinho = criarBotao("Abrir carrinho", "Interface/Icons/cart.png",200, 50);   // espaços extras para centralizar as imagens
        panelPedido.add(botaoCarrinho);

        panelMiddle.add(panelPedido);

        // Painel dos botões de consultas
        JPanel panelConsulta = new JPanel();
        panelConsulta.setLayout(new FlowLayout());
        panelConsulta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Consultas"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botão de histórico
        JButton botaoHistorico = criarBotao("Consultar histórico", "Interface/Icons/history.png",200, 50);   // espaços extras para centralizar as imagens
        panelConsulta.add(botaoHistorico);

        // Botão de promocao
        JButton botaoPromocoes = criarBotao("Consultar promoções", "Interface/Icons/promotions.png",200, 50);   // espaços extras para centralizar as imagens
        panelConsulta.add(botaoPromocoes);

        // Colocar no painel do meio
        panelMiddle.add(panelConsulta);

        // Botão para sair da conta
        JButton botaoSair = criarBotao("Sair da conta", "Interface/Icons/sair.png", 140, 35);
        JButton botaoVoltar = criarBotao("Voltar", "Interface/Icons/voltar.png", 100, 35);


        // Colocar o botao de sair à direita
        JPanel panelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRodape.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRodape.add(botaoVoltar);
        panelRodape.add(botaoSair);

        // Adicionar os painéis ao painel principal
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(panelMiddle, BorderLayout.CENTER);
        panel.add(panelRodape, BorderLayout.SOUTH);

        add(panel);

    }

    // Método para criar botões e adicionar ações
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
                case "Fazer pedido" -> new TelaCatalogo().setVisible(true);
                case "Abrir carrinho" -> new TelaCarrinho().setVisible(true);
                case "Ver dados da conta" -> new TelaVerConta().setVisible(true);
                case "Atualizar cadastro" -> new TelaAttCadastroCliente().setVisible(true);

                case "Mudar a senha" -> new TelaMudarSenhaCliente().setVisible(true);
                case "Excluir a conta" -> {
                    new TelaExcluirCliente().setVisible(true);
                    dispose();
                }
                case "Consultar histórico" -> new TelaHistorico(idCliente).setVisible(true);
                case "Consultar promoções" -> new TelaPromocoes().setVisible(true);
                case "Sair da conta" -> {
                    deslogarCliente();
                    carrinho.limparCarrinho();
                }
                default -> dispose();
            }
        });

        return botao;
    }

    void deslogarCliente() {
        Login login = new Login("Cliente");
        Cliente clienteLogado = login.obterClienteLogado();
        if (clienteLogado != null) {
            login.removerLogin(clienteLogado.getIdCliente());
            dispose();
        }
    }
}
