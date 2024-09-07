package sessao.classe;

import java.util.Date;
import java.util.Map;


public class Sessao {
    private int idSessao; // Atributo para o id da sessão
    private int idSala; // Atributo para o id da sala
    private int idFilme; // Atributo para o id do filme
    private Date data; // Atributo para a data
    private String horario; // Atributo para a hora
    private boolean dublado; // Atributo para saber se o filme é dublado
    private Map<Integer, Boolean> assentos; // Dicionário para assentos
    private boolean eh3d; // Atributo para saber se o filme é 3D
    private float valor; // Atributo para o valor
    private boolean ativa; // Atributo para saber se a sessão está ativa

    // Construtor
    public Sessao() {}

    public Sessao(int idSessao, int idSala, int idFilme, Date data, String horario, boolean dublado, boolean eh3d, float valor, boolean ativa, Map<Integer, Boolean> assentos) {
        this.idSessao = idSessao;
        this.idSala = idSala;
        this.idFilme = idFilme;
        this.data = data;
        this.horario = horario;
        this.dublado = dublado;
        this.eh3d = eh3d;
        this.valor = valor;
        this.ativa = ativa;
        this.assentos = assentos;
    }

    // Getters e Setters
    public int getIdSessao() {
        return idSessao;
    }
    public void setIdSessao(int idSessao) {
        this.idSessao = idSessao;
    }

    public int getIdSala() {
        return idSala;
    }
    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getIdFilme() {
        return idFilme;
    }
    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isDublado() {
        return dublado;
    }
    public void setDublado(boolean dublado) {
        this.dublado = dublado;
    }

    public Map<Integer, Boolean> getAssentos() {
        return assentos;
    }
    public void setAssentos(Map<Integer, Boolean> assentos) {
        this.assentos = assentos;
    }

    public boolean isEh3d() {
        return eh3d;
    }
    public void setEh3d(boolean eh3d) {
        this.eh3d = eh3d;
    }

    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean isAtiva() {
        return ativa;
    }
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
