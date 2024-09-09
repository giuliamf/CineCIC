/* Érica

 */

package filme.classe;

public class Filme {
    private String titulo, genero, sinopse, urlImg, classificacaoIndicativa;

    private short duracao;
    private int indice;
    private Boolean ehLancamento, emCartaz;

    public Filme() {}

    public Filme(int idFilme, boolean emCartaz, String urlImg, String titulo, String genero, short duracao, String classificacaoIndicativa, boolean ehLancamento, String sinopse) {
        this.indice = idFilme;
        this.emCartaz = emCartaz;
        this.titulo = titulo;
        this.genero = genero;
        this.duracao = duracao;
        this.classificacaoIndicativa = classificacaoIndicativa;
        this.ehLancamento = ehLancamento;
        this.sinopse = sinopse;
        this.urlImg = urlImg;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isEmCartaz() {
        return emCartaz;
    }
    public void setEmCartaz(boolean emCartaz) {
        this.emCartaz = emCartaz;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public short getDuracao() {
        return duracao;
    }
    public void setDuracao(short duracao) {
        this.duracao = duracao;
    }

    public String getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }
    public void setClassificacaoIndicativa(String classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public Boolean isEhLancamento() {
        return ehLancamento;
    }
    public void setEhLancamento(boolean ehLancamento) {
        this.ehLancamento = ehLancamento;
    }

    public void setIndice(int indice){
        this.indice = indice;
    }
    public int getIndice(){
        return this.indice;
    }

    public void setUrlImg(String urlImg){ this.urlImg = urlImg; }
    public String getUrlImg(){ return this.urlImg; }
}