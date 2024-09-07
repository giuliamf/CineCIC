package sala.classe;

public class Sala {
    private int idSala;
    private int qtAssentos;
    private boolean eh3d;
    private boolean ehVip;
    private boolean aberta;

    public Sala() {}

    // Construtor
    public Sala(int idSala, int qtAssentos, boolean eh3d, boolean ehVip, boolean aberta) {
        this.idSala = idSala;
        this.qtAssentos = qtAssentos;
        this.eh3d = eh3d;
        this.ehVip = ehVip;
        this.aberta = aberta;
    }

    // Getters e Setters
    public int getIdSala() {return idSala;}
    public void setIdSala(int idSala) {this.idSala = idSala;}

    public int getQtdAssentos() {return qtAssentos;}
    public void setQtdAssentos(int qtAssentos) {this.qtAssentos = qtAssentos;}

    public boolean isEh3d() {return eh3d;}
    public void setEh3d(boolean eh3d) {this.eh3d = eh3d;}

    public boolean isEhVip() {return ehVip;}
    public void setEhVip(boolean ehVip) {this.ehVip = ehVip;}

    public boolean isAberta() {return aberta;}
    public void setAberta(boolean aberta) {this.aberta = aberta;}
}
