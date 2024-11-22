package eam_soft.service.transfer;
import java.util.PriorityQueue;

import eam_soft.models.Cotizante;

public class ColaTransferencia {
    private PriorityQueue<Cotizante> cola;

    public ColaTransferencia() {
        this.cola = new PriorityQueue<>(new ComparadorPrioridadCotizante());
    }

    public void agregarCotizante(Cotizante cotizante) {
        cola.add(cotizante);
    }

    public Cotizante obtenerSiguienteCotizante() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }
}

