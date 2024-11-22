package eam_soft.service.transfer;
import java.util.PriorityQueue;

import eam_soft.models.Cotizante;

public class TransferQueue {

    private PriorityQueue<Cotizante> cola;

    public TransferQueue() {
        this.cola = new PriorityQueue<>(new CompareCotizante());
    }

    public void addCotizante(Cotizante cotizante) {
        cola.add(cotizante);
    }

    public Cotizante getNextCotizante() {
        return cola.poll();
    }

    public boolean isEmpty() {
        return cola.isEmpty();
    }
}

