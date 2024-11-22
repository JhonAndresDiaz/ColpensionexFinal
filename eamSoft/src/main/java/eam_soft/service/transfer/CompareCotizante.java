package eam_soft.service.transfer;

import java.util.Comparator;
import eam_soft.models.Cotizante;

public class CompareCotizante implements Comparator<Cotizante> {

    @Override
    public int compare(Cotizante cotizante1, Cotizante cotizante2) {
        return Comparator
        .comparingInt(Cotizante::getSemanasCotizadas).reversed() 
        .thenComparingInt(Cotizante::getEdad).reversed()
        .thenComparing(Cotizante::getFondo)
        .compare(cotizante1, cotizante2);
    }
}
