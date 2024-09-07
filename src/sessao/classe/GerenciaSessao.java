package sessao.classe;

import sala.classe.*;
import filme.classe.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GerenciaSessao {

    protected File file = new File("Interface/Dados/sessoes.csv");

    GerenciaSala gerenciaSala = new GerenciaSala();
    GerenciaFilme gerenciaFilme = new GerenciaFilme();

    Map<Integer, Sessao> sessoes = new HashMap<>();

    // Construtor que recebe uma instância de GerenciaSala
    public GerenciaSessao() {}

    // Método para conferir se a data da sessão é a mesma e se o tempo de sessão conflita (horário inicial, horário final = horário inicial + duração do filme)
    public boolean verificarConflitoHorario(int idSala, Date data, String horario, int duracao) {
        carregarSessao();
        // Converter o horário inicial para minutos desde meia-noite
        int horaInicio = Integer.parseInt(horario.substring(0, 2));
        int minutoInicio = Integer.parseInt(horario.substring(3, 5));
        int inicioSessaoMinutos = horaInicio * 60 + minutoInicio;

        // Calcular o término da nova sessão em minutos desde meia-noite
        int fimSessaoMinutos = inicioSessaoMinutos + duracao;

        for (Sessao sessao : sessoes.values()) {
            Date dataSessaoDate = sessao.getData(); // LocalDate

            Date dataSessao = sessao.getData();
            int idSalaSessao = sessao.getIdSala();

            // Transformar ambas as datas em string antes de comparar
            String dataString = converterDataParaString(data);
            String dataSessaoString = converterDataParaString(dataSessao);

            if (idSalaSessao == idSala && dataSessaoString.equals(dataString)) {
                // Obter horário da sessão existente
                int horaSessaoExistente = Integer.parseInt(sessao.getHorario().substring(0, 2));
                int minutoSessaoExistente = Integer.parseInt(sessao.getHorario().substring(3, 5));
                int inicioSessaoExistenteMinutos = horaSessaoExistente * 60 + minutoSessaoExistente;

                // Obter duração do filme da sessão existente
                Filme filmeExistente = gerenciaFilme.obterFilme(sessao.getIdFilme());
                int duracaoFilmeExistente = filmeExistente.getDuracao();

                // Calcular o término da sessão existente
                int fimSessaoExistenteMinutos = inicioSessaoExistenteMinutos + duracaoFilmeExistente;

                // Verificar se há sobreposição de horários
                if ((inicioSessaoMinutos < fimSessaoExistenteMinutos) && (fimSessaoMinutos > inicioSessaoExistenteMinutos)) {
                    return true; // Há um conflito
                }
            }
        }
        return false; // Não há conflito
    }

    // Método para tirar o hh:mm:ss da data e transformar em String para comparar
    public String converterDataParaString(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(data);
    }

    // Método para converter yyyy-MM-dd para Date
    public Date converterStringParaDate(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(data);
        } catch (Exception e) {
            return null;
        }
    }

    // Método para trocar a vírgula por ponto no float
    public float converterStringParaFloat(String valor) {
        return Float.parseFloat(valor.replace(',', '.'));
    }

    public void carregarSessao() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");

                Map<Integer, Boolean> mapaAssentos = converterStringAssentosParaMapa(dados[9]);
                Date data = converterStringParaDate(dados[3]);
                float valor = converterStringParaFloat(dados[7]);

                Sessao sessao = new Sessao(
                        Integer.parseInt(dados[0]), // idSessao
                        Integer.parseInt(dados[1]), // idSala
                        Integer.parseInt(dados[2]), // idFilme
                        data, // data
                        dados[4], // horario
                        Boolean.parseBoolean(dados[5]), // dublado
                        Boolean.parseBoolean(dados[6]), // eh3d
                        valor, // valor
                        Boolean.parseBoolean(dados[8]), // ativa
                        mapaAssentos // mapa de assentos
                );
                this.sessoes.put(Integer.parseInt(dados[0]), sessao);
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Método para converter a string de assentos para um mapa
    public Map<Integer, Boolean> converterStringAssentosParaMapa(String stringAssentos) {
        Map<Integer, Boolean> mapaAssentos = new HashMap<>();

        for (int i = 0; i < stringAssentos.length(); i++) { // i começa em 0
            char c = stringAssentos.charAt(i);
            mapaAssentos.put(i + 1, c == '1');  // Incrementa i para que o ID comece de 1
        }

        return mapaAssentos;
    }

    // Método para converter o mapa de assentos para uma string **
    public String converterMapaAssentosParaString(Map<Integer, Boolean> mapaAssentos) {
        StringBuilder sb = new StringBuilder();
        int numeroMaximoAssentos = mapaAssentos.size();

        for (int i = 1; i <= numeroMaximoAssentos; i++) { // Começar de 1 até o máximo de assentos
            if (mapaAssentos.getOrDefault(i, true)) { // true para disponível, false para indisponível
                sb.append('1');
            } else {
                sb.append('0');
            }
        }
        return sb.toString();
    }

    // Método para inicializar o mapa de assentos com base na quantidade de assentos de uma sala específica
    public Map<Integer, Boolean> inicializarMapaAssentos(int idSala) {
        Sala sala = gerenciaSala.obterSala(idSala);

        if (sala != null) {
            Map<Integer, Boolean> mapaAssentos = new HashMap<>();
            for (int i = 1; i <= sala.getQtdAssentos(); i++) {
                mapaAssentos.put(i, true); // Inicializa todos os assentos como disponíveis (true)
            }
            return mapaAssentos;
        } else {
            return null; // Retorna null se a sala não for encontrada (ou pode lançar uma exceção se preferir)
        }
    }

    // Método para ocupar o assento comprado
    public void ocuparAssento(int idSessao, int idAssento) {
        carregarSessao();
        Sessao sessao = sessoes.get(idSessao);
        if (sessao != null) {
            Map<Integer, Boolean> mapaAssentos = sessao.getAssentos();
            if (mapaAssentos.get(idAssento)) {
                mapaAssentos.put(idAssento, false); // Ocupa o assento
                sessao.setAssentos(mapaAssentos);
                atualizarCSV();
            }
        }
    }

    // Método para obter as informações de uma sessão através do ID
    public Sessao obterSessao(int idSessao) {
        carregarSessao();
        return sessoes.get(idSessao);
    }

    // Método para cadastrar uma nova sessão
    public String cadastrarSessao(Sessao sessao) {

        // Converte o mapa de assentos para string
        String mapaAssentosString = converterMapaAssentosParaString(sessao.getAssentos());

        // Formata a data para salvar no arquivo CSV
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dataFormatada = dateFormat.format(sessao.getData());

        // Cria uma linha para adicionar ao CSV
        String linha = String.format("%d;%d;%d;%s;%s;%b;%b;%.2f;%b;%s",
                sessao.getIdSessao(), sessao.getIdSala(), sessao.getIdFilme(),
                dataFormatada, sessao.getHorario(), sessao.isDublado(),
                sessao.isEh3d(), sessao.getValor(), sessao.isAtiva(),
                mapaAssentosString);

        // Escreve a linha no arquivo CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            return "erro";

        }
        return "sucesso";
    }

    // Método para obter o mapa de assentos através do ID da sessão
    public Map<Integer, Boolean> obterMapaAssentos(int idSessao) {
        carregarSessao();
        Sessao sessao = sessoes.get(idSessao);
        if (sessao != null) {
            return sessao.getAssentos();
        }
        return null;
    }   // Pensar em colocar apenas o id do assento ocupado no csv

    // Obter todas as sessões com a data e o filme passados
    public Map<Integer, Sessao> obterSessoesPorDataEIdFilme(Date data, int idFilme) {
        carregarSessao();
        Map<Integer, Sessao> sessoesPorDataEIdFilme = new HashMap<>();

        for (Sessao sessao : sessoes.values()) {
            if (sessao.getData().equals(data) && sessao.getIdFilme() == idFilme) {
                sessoesPorDataEIdFilme.put(sessao.getIdSessao(), sessao);
            }
        }
        return sessoesPorDataEIdFilme;
    }

    // Método para calcular o valor inicial da sessão
    public float calcularValorSessao(int idFilme, int idSala, boolean eh3D, String horario) {
        Sala sala = gerenciaSala.obterSala(idSala);
        Filme filme = gerenciaFilme.obterFilme(idFilme);

        if (sala != null && filme != null) {
            float valor = 27.00f;

            if (eh3D && sala.isEhVip()) {    // se é 3d e vip = 51,00
                valor = 51.00f;
            }
            else if (eh3D) { // se é apenas 3d = 31,50
                valor = 31.50f;
            }
            else if (sala.isEhVip()) {  // se é apenas vip = 46,00
                valor = 46.00f;
            }

            // aumento de 5,00 no valor caso o filme seja lançamento
            if (filme.isEhLancamento()) {
                valor += 5.00f;
            }

            // desconto de 25% no valor caso a sessão seja entre 12h e 15h
            int horas = Integer.parseInt(horario.substring(0, 2));
            int minutos = Integer.parseInt(horario.substring(3, 5));

            if ((horas > 12 || (horas == 12 && minutos >= 0)) && (horas < 15 || (horas == 15 && minutos == 0))) {
                valor *= 0.75f;  // aplica o desconto de 25%
            }

            return valor;
        }
        return -1;  // erro
    }

    public int gerarIdSessao() {
        carregarSessao();
        if (sessoes.isEmpty()) {
            return 1;   // Se não houver sessões, o id será 1
        }
        return sessoes.size() + 1;   // Nenhuma sessão será excluída nunca, então o id sempre será o tamanho do map + 1
    }

    // Método para atualizar o arquivo CSV
    public void atualizarCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Sessao sessao : sessoes.values()) {
                String mapaAssentosString = converterMapaAssentosParaString(sessao.getAssentos());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String dataFormatada = dateFormat.format(sessao.getData());

                String linha = String.format("%d;%d;%d;%s;%s;%b;%b;%.2f;%b;%s",
                        sessao.getIdSessao(), sessao.getIdSala(), sessao.getIdFilme(),
                        dataFormatada, sessao.getHorario(), sessao.isDublado(),
                        sessao.isEh3d(), sessao.getValor(), sessao.isAtiva(),
                        mapaAssentosString);

                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    // Método para desativar todas as sessões caso todas as salas tenham sido fechadas
    public void desativarSessoesPorTodasSalasFechadas() {
        carregarSessao();
        Map<Integer, Sala> salas = gerenciaSala.carregarSala();
        try {
            for (Sessao sessao : sessoes.values()) {
                if (!salas.get(sessao.getIdSala()).isAberta()) {
                    sessao.setAtiva(false);
                }
            }
            atualizarCSV();
        } catch (Exception e) {
            System.out.println();
        }
    }

    // Método para desativar a sessão caso uma sala tenha sido fechada
    public void desativarSessaoPorSala(int idSala) {
        carregarSessao();
        try {
            for (Sessao sessao : sessoes.values()) {
                if (sessao.getIdSala() == idSala) {
                    sessao.setAtiva(false);
                    atualizarCSV();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

}
