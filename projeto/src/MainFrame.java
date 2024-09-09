import filme.TelaAreaCatalogo;
import filme.*;
import filme.classe.Filme;
import filme.classe.GerenciaFilme;
import pedido.classe.Carrinho;
import usuarios.*;
import promocao.TelaPromocoes;
import usuarios.classe.Cliente;
import usuarios.classe.Funcionario;
import usuarios.classe.Login;
import usuarios.telascliente.TelaAreaCliente;
import usuarios.telasfuncionario.TelaAreaFuncionario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    // Construtor da classe
    public MainFrame() {
        setTitle("CineCIC");    // Título da janela
        setSize(1000, 600);  // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela
        setLocationRelativeTo(null);    // Centraliza a janela na tela

        // Icone ao lado do nome da página
        ImageIcon favicon = new ImageIcon("Interface/Icons/pipoca.png");
        setIconImage(favicon.getImage());

        // Painel principal (conteúdo da página)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));    // Layout vertical
        mainPanel.setBackground(Color.WHITE);   // Cor de fundo branca
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));   // Espaçamento no topo

        // Painel para os botões no canto superior da página
        JPanel topBtnPanel = new JPanel();
        topBtnPanel.setLayout(new BoxLayout(topBtnPanel, BoxLayout.X_AXIS));    // Layout horizontal
        topBtnPanel.setBackground(Color.WHITE);  // Cor de fundo branca
        topBtnPanel.add(Box.createHorizontalGlue());    // "empurra" para a esquerda

            // Botão "Área do Cliente"
        topBtnPanel.add(criarBotao("Área do Cliente", "Interface/Icons/cliente.png", 30, 30, 200));  // Cria o botão
        topBtnPanel.add(Box.createRigidArea(new Dimension(30, 0)));   // Espaçamento entre os botões

            // Botão "Área do Funcionário"
        topBtnPanel.add(criarBotao("Área do Funcionário", "Interface/Icons/funcionario.png", 30, 30, 200));  // Cria o botão
        topBtnPanel.add(Box.createHorizontalGlue());    // "empurra" para a direita, centralizando

            // Adicionando o painel no MainFrame
        mainPanel.add(topBtnPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));   // Espaçamento entre os botões e o conteúdo

        // Painel para os botões no centro da página
        JPanel centralBtnPanel = new JPanel();
        centralBtnPanel.setLayout(new BoxLayout(centralBtnPanel, BoxLayout.X_AXIS));    // Layout horizontal
        centralBtnPanel.setBackground(Color.WHITE); // Cor de fundo branca
        centralBtnPanel.add(Box.createHorizontalGlue());    // "empurra" para a esquerda

            // Botão "Acessar o catálogo"
        centralBtnPanel.add(criarBotao("Acessar o catálogo", "Interface/Icons/catalogue.png", 50, 50, 300)); // Cria o botão
        centralBtnPanel.add(Box.createRigidArea(new Dimension(30, 0)));   // Espaçamento entre os botões

            // Botão "Consultar promoções"
        centralBtnPanel.add(criarBotao("Consultar promoções", "Interface/Icons/discount.png", 50, 50, 300)); // Cria o botão
        centralBtnPanel.add(Box.createHorizontalGlue());   // "empurra" para a direita (centralizando)

        // Adicionando o botão no MainFrame
        mainPanel.add(centralBtnPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel arredondado de resumo do catálogo
        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));    // Layout de fluxo centralizado
        moviePanel.setBackground(Color.WHITE);  // Cor de fundo branca
        moviePanel.setBorder(BorderFactory.createTitledBorder("Últimos lançamentos"));  // Borda com título

        GerenciaFilme info = new GerenciaFilme();
        // Detalhes do filme (objeto Filme)
        Map<Integer, Filme> filmes = info.carregarFilme();

        // Transformar o Map em uma lista de filmes, ordenando por índice em ordem decrescente (para pegar os últimos lançamentos)
        List<Filme> filmesOrdenados = filmes.values().stream()
                .filter(Filme::isEhLancamento)  // Filtra apenas os lançamentos
                .filter(Filme::isEmCartaz)       // Filtra apenas os filmes em cartaz
                .sorted(Comparator.comparingInt(Filme::getIndice).reversed())  // Ordena do maior para o menor índice
                .toList();

        int contador = 0;
        for (Filme filme : filmesOrdenados) {
            if (contador < 4) {  // Limita a 4 filmes
                moviePanel.add(createMovie(filme.getIndice(), filme.getUrlImg()));
                contador++;
            } else {
                break;
            }
        }

        // Define o tamanho preferido, mínimo e máximo do painel arredondado de filmes
        Dimension panelSize = new Dimension(800, 350);  // Tamanho do painel arredondado
        moviePanel.setPreferredSize(new Dimension(panelSize));  // Tamanho preferido
        moviePanel.setMinimumSize(new Dimension(panelSize));    // Tamanho mínimo
        moviePanel.setMaximumSize(new Dimension(panelSize));    // Tamanho máximo

        // Adiciona o painel arredondado ao MainFrame
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(moviePanel);

        // Ação principal de adicionar tudo ao MainFrame
        add(mainPanel);

        // Deslogar o cliente se necessário
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deslogarSeNecessario();
            }
        });

    }

    // Método para criar botões com ícones e ações específicas
    private JButton criarBotao(String nome, String caminhoIcon, int larguraIcon, int alturaIcon, int larguraBotao) {
        JButton btn = new JButton(nome);
        btn.setHorizontalTextPosition(JButton.RIGHT);   // Colocar o texto à direita da imagem
        btn.setVerticalTextPosition(JButton.CENTER);    // Centralizar verticalmente

        btn.setPreferredSize(new Dimension(larguraBotao, 100)); // Tamanho do botão, altura sempre será 100

        // Redimensionar a imagem
        ImageIcon icon = new ImageIcon(caminhoIcon);    // Imagem do botão
        Image img = icon.getImage().getScaledInstance(larguraIcon, alturaIcon, java.awt.Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(img));

       // Ações baseadas em qual botão foi clicado
        btn.addActionListener(_ -> {
            switch (nome) {
                case "Área do Cliente" -> {
                    if (estaLogado(nome)) {
                        new TelaAreaCliente().setVisible(true); // Abre a tela do cliente caso ele já esteja logado
                    }
                    else {
                        new TelaLoginCliente().setVisible(true);    // Abre a tela de login do cliente
                    }
                }
                case "Área do Funcionário" -> {
                    if (estaLogado(nome)) {
                        new TelaAreaFuncionario().setVisible(true); // Abre a tela do funcionário caso ele já esteja logado
                    }
                    else {
                        new TelaLoginFuncionario().setVisible(true);    // Abre a tela de login do funcionário
                    }
                }
                case "Acessar o catálogo" -> new TelaCatalogo().setVisible(true);
                case "Consultar promoções" -> new TelaPromocoes().setVisible(true);
            }
        });
        return btn;

    }

    // Método para criar rótulos de filmes com ícones e ações de clique
    private JLabel createMovie(int indice, String caminhoImg) {
        JLabel movie = new JLabel();    // Rótulo para o filme
        ImageIcon icon = new ImageIcon(caminhoImg);   // Imagem do filme
        Image img = icon.getImage().getScaledInstance(175, 300, java.awt.Image.SCALE_SMOOTH);   // Redimensiona a imagem

        movie.setIcon(new ImageIcon(img));  // Define a imagem no rótulo
        movie.setHorizontalAlignment(SwingConstants.CENTER);    // Centraliza horizontalmente
        movie.setVerticalAlignment(SwingConstants.CENTER);  // Centraliza verticalmente
        movie.setPreferredSize(new Dimension(175, 300));    // Tamanho do rótulo

        // Ação de clique no rótulo
        movie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new TelaAreaCatalogo(indice).setVisible(true);
            }
        });
        return movie;
    }

    // Método para verificar se o cliente ou funcionário está logado
    public boolean estaLogado(String area) {
        Login login;
        if (area.contains("Cliente")) {
            login = new Login("Cliente");
        } else {
            login = new Login("Funcionario");
        }
        return login.verificarSeLogado();
    }

    // Método para desconectar o cliente ou funcionário se necessário, ao fechar a janela
    public void deslogarSeNecessario() {
        // Limpar o login do cliente e funcionário
        Login loginCliente = new Login("Cliente");
        Cliente clienteLogado = loginCliente.obterClienteLogado();
        if (clienteLogado != null) {
            loginCliente.removerLogin(clienteLogado.getIdCliente());
        }

        Login loginFuncionario = new Login("Funcionario");
        Funcionario funcionarioLogado = loginFuncionario.obterFuncionarioLogado();
        if (funcionarioLogado != null) {
            loginFuncionario.removerLogin(funcionarioLogado.getIdFuncionario());
        }

        // Limpar o carrinho
        Carrinho carrinho = new Carrinho();
        carrinho.limparCarrinho();
    }

    // Método principal para executar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
