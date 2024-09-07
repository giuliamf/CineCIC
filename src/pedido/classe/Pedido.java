package pedido.classe;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int idPedido;
    private int idUsuario;
    private List<Ingresso> ingressos;
    private float valorTotal;

    public Pedido() {}

    public Pedido(int idPedido, int idUsuario, List<Ingresso> ingressos, float valorTotal) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.ingressos = ingressos;
        this.valorTotal = valorTotal;
    }

    public int getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }
    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    public float getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}


