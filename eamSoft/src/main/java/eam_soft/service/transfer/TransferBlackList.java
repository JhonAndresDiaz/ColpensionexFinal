package eam_soft.service.transfer;

import eam_soft.data_management.CotizanteManager;
import eam_soft.models.Cotizante;
import eam_soft.service.validations.message.ValidationResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransferBlackList {
    
    private final CotizanteManager cotizanteManager = new CotizanteManager();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public TransferBlackList() {}

    public ValidationResult transferToBlackList(Cotizante cotizante) {
        cotizante.setEnListaNegraUltimos6Meses(new Date());
        cotizanteManager.save(cotizante, cotizanteManager.getListaNegraCsv(), "lista_negra.csv");
        return new ValidationResult(false, "Rechazado: Tiene observación disciplinaria, será pasado a la lista negra con la fecha de hoy " + DATE_FORMAT.format(cotizante.getEnListaNegraUltimos6Meses()));
    }
    
    public ValidationResult searchOnBlacklist(Cotizante cotizante) {
        List<Cotizante> listaNegra = cotizanteManager.findAllCotizantes("lista_negra.csv", cotizanteManager.getListaNegraCsv());
        for (Cotizante aux : listaNegra) {
            if (cotizante.getDocumento().equals(aux.getDocumento())) {
                aux.setEnListaNegraUltimos6Meses(new Date());
                cotizanteManager.updateListaNegra(listaNegra);
                return new ValidationResult(false, "Rechazado: Está en la lista negra en los últimos seis meses");
            }
        }
        return new ValidationResult(true, "Aprobado");
    }

    public Cotizante resetTimeInList(Cotizante cotizante){
        return cotizante;
    } 
}
