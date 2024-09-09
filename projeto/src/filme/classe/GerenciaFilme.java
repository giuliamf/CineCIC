package filme.classe;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class GerenciaFilme {

    // Mapa para armazenar os filmes
    Map<Integer, Filme> filmes = new HashMap<>();

    // Arquivo para armazenar os filmes
    File file = new File("resources/banco de dados/dadosFilmes.csv");

    // Método para carregar os filmes do arquivo
    public Map<Integer, Filme> carregarFilme() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {// Lê o arquivo
            String linha;
            
            while (true) {
                linha = br.readLine();
                if (linha == null) {
                    break;
                } else {
                    String[] filme = linha.split(";");
                    Filme f = new Filme(Integer.parseInt(filme[0]), Boolean.parseBoolean(filme[1]), filme[2], filme[3], filme[4], Short.parseShort(filme[5]), filme[6], Boolean.parseBoolean(filme[7]), filme[8]);
                    this.filmes.put(Integer.parseInt(filme[0]), f);
                }
            }
        } catch (IOException e) {
            System.out.println();
        }
        return this.filmes;
    }

    // Método para adicionar um filme
    public Filme obterFilme(int indice) {
        carregarFilme();    // Carregar os filmes
        return filmes.get(indice);  // Retorna o filme com o id passado
    }

    // Método para colocar um filme em cartaz
    public void colocarEmCartaz(int idFilme) {
        String status = ""; // Variável para verificar se a operação foi bem sucedida

        // Mudar o objeto
        for (Map.Entry<Integer, Filme> entry : filmes.entrySet()) {
            Filme filme = entry.getValue();
            if (filme.getIndice() == idFilme) {
                filme.setEmCartaz(true);
                status = "sucesso";
            }
        }

        // Mudar o arquivo
        if (status.equals("sucesso")) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Filme filme : filmes.values()) {
                    bw.write(filme.getIndice() + ";" +
                            filme.isEmCartaz() + ";" +
                            filme.getUrlImg() + ";" +
                            filme.getTitulo() + ";" +
                            filme.getGenero() + ";" +
                            filme.getDuracao() + ";" +
                            filme.getClassificacaoIndicativa() + ";" +
                            filme.isEhLancamento() + ";" +
                            filme.getSinopse());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println();
            }
        }
    }

    // Método para tirar um filme de cartaz
    public void tirarDeCartaz(int idFilme) {
        String status = "";

        // Mudar o objeto
        for (Map.Entry<Integer, Filme> entry : filmes.entrySet()) {
            Filme filme = entry.getValue();
            if (filme.getIndice() == idFilme) {
                filme.setEmCartaz(false);
                status = "sucesso";
            }
        }

        // Mudar o arquivo
        if (status.equals("sucesso")) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Filme filme : filmes.values()) {
                    bw.write(filme.getIndice() + ";" +
                            filme.isEmCartaz() + ";" +
                            filme.getUrlImg() + ";" +
                            filme.getTitulo() + ";" +
                            filme.getGenero() + ";" +
                            filme.getDuracao() + ";" +
                            filme.getClassificacaoIndicativa() + ";" +
                            filme.isEhLancamento() + ";" +
                            filme.getSinopse());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println();
            }
        }
    }

    // Método para adicionar um filme no arquivo
    public String addFilmeCsv(Filme filme) {
        String status;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(filme.getIndice() + ";" +
                    filme.isEmCartaz() + ";" +
                    filme.getUrlImg() + ";" +
                    filme.getTitulo() + ";" +
                    filme.getGenero() + ";" +
                    filme.getDuracao() + ";" +
                    filme.getClassificacaoIndicativa() + ";" +
                    filme.isEhLancamento() + ";" +
                    filme.getSinopse());
            bw.newLine();
            bw.close();
            status = "sucesso";
        } catch (IOException e) {
            status = "erro";
        }
        return status;
    }

    // Método para excluir um filme
    public String editarFilme(String filmeSelecionado, Filme filmeEditado) {
        boolean filmeEncontrado = false;    // Variável para verificar se o filme foi encontrado
        String status = ""; // Variável para verificar se a operação foi bem sucedida

        // Procurar o filme e editar
        for (Filme filme : filmes.values()) {
            if (filme.getTitulo().equals(filmeSelecionado)) {
                filme.setTitulo(filmeEditado.getTitulo() != null ? filmeEditado.getTitulo() : filme.getTitulo());
                filme.setEmCartaz(filmeEditado.isEmCartaz());
                filme.setUrlImg(filmeEditado.getUrlImg() != null ? filmeEditado.getUrlImg() : filme.getUrlImg());
                filme.setGenero(filmeEditado.getGenero() != null ? filmeEditado.getGenero() : filme.getGenero());
                filme.setDuracao(filmeEditado.getDuracao() != 0 ? filmeEditado.getDuracao() : filme.getDuracao());
                filme.setClassificacaoIndicativa(filmeEditado.getClassificacaoIndicativa() != null ? filmeEditado.getClassificacaoIndicativa() : filme.getClassificacaoIndicativa());
                filme.setEhLancamento(filmeEditado.isEhLancamento());
                filme.setSinopse(filmeEditado.getSinopse() != null ? filmeEditado.getSinopse() : filme.getSinopse());

                filmeEncontrado = true;
                break;
            }
        }

        // Editar o arquivo se o filme foi encontrado
        if (filmeEncontrado) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Filme filme : filmes.values()) {
                    bw.write(filme.getIndice() + ";" +
                            filme.isEmCartaz() + ";" +
                            filme.getUrlImg() + ";" +
                            filme.getTitulo() + ";" +
                            filme.getGenero() + ";" +
                            filme.getDuracao() + ";" +
                            filme.getClassificacaoIndicativa() + ";" +
                            filme.isEhLancamento() + ";" +
                            filme.getSinopse());
                    bw.newLine();
                    status = "sucesso";
                }
            } catch (IOException e) {
                status = "erro";
            }
        } else {
            status = "não encontrado";
        }
        return status;
    }

    // Método para obter os filmes
    public Map<Integer, Filme> getFilmes() {
        return filmes;
    }

    // Método para gerar o id do filme
    public int gerarIdFilme() {
        return filmes.size() + 1;   // Nenhum filme será excluído nunca, então o id sempre será o tamanho do map + 1
    }

    // Método para verificar se há 10 filmes em cartaz (limite)
    public boolean verificaEspacoCartaz() {
        AtomicReference<Integer> cont = new AtomicReference<>(0);   // Variável para contar os filmes em cartaz (AtomicReference para poder ser alterada dentro do forEach)
        filmes.forEach((_, filme) -> {  // Contar os filmes em cartaz
            if (filme.isEmCartaz()) {   // Se o filme estiver em cartaz, incrementar o contador
                cont.getAndSet(cont.get() + 1); // Incrementar o contador
            }
        });
        return cont.get() < 10; // Se < 10 é porque tem espaço (true), se não, limite atingido (false)
    }

    // Método para pegar o id do filme através do título
    public Integer getIdFilme(String titulo) {
        for (Map.Entry<Integer, Filme> filme : filmes.entrySet()) {
            if (filme.getValue().getTitulo().equals(titulo)) {
                return filme.getKey();
            }
        }
        return null;
    }
}
