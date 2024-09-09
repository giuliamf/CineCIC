package pedido.classe;

import sessao.classe.*;

public class Ingresso {
    private int idCliente;
    private int idIngresso;
    private int assentoEscolhido;
    private float subtotal;
    private boolean meia;
    private int idSessao;

    public Ingresso() {}

    public Ingresso(int idIngresso, int idCliente, int idSessao, int assento, float subtotal, boolean meia) {
        this.idCliente = idCliente;
        this.idIngresso = idIngresso;
        this.assentoEscolhido = assento;
        this.subtotal = subtotal;
        this.meia = meia;
        this.idSessao = idSessao;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdIngresso() {
        return idIngresso;
    }
    public void setIdIngresso(int idIngresso) {
        this.idIngresso = idIngresso;
    }

    public int getAssentoEscolhido() {
        return assentoEscolhido;
    }
    public void setAssentoEscolhido(int assentoEscolhido) {
        this.assentoEscolhido = assentoEscolhido;
    }

    public float getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public boolean isMeia() {
        return meia;
    }
    public void setMeia(boolean meia) {
        this.meia = meia;
    }

    public int getIdSessao() {
        return idSessao;
    }
    public void setIdSessao(int idSessao) {
        this.idSessao = idSessao;
    }
}
