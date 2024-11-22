package eam_soft.service.transfer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eam_soft.data_management.CotizanteManager;
import eam_soft.models.Cotizante;
import eam_soft.service.validations.message.ValidationResult;

public class TransferirListaNegra {
    
    private final CotizanteManager cotizanteManager = new CotizanteManager();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  

    public TransferirListaNegra() {
    }

    public ValidationResult transferirAListaNegra(Cotizante cotizante){        
        cotizante.setEnListaNegraUltimos6Meses(new Date());
        cotizanteManager.save(cotizante, cotizanteManager.getListaNegraCsv(), "lista_negra.csv");
        return new ValidationResult(false, "Rechazado: Tiene observación disciplinaria, sera pasado a la lista negra con fecha de hoy "+sdf.format(cotizante.getEnListaNegraUltimos6Meses()));
    }

    public Cotizante reiniciarTiempoEnLista(Cotizante cotizante){
        return cotizante;
    }

    public ValidationResult searchOnBlacklist(Cotizante cotizante){
        List<Cotizante> listaNegra = cotizanteManager.findAllCotizantes("lista_negra.csv", cotizanteManager.getListaNegraCsv());
        for (Cotizante aux : listaNegra) {
            if(aux.getDocumento()!=null){
                if(aux.getDocumento().equals(cotizante.getDocumento())){
                    aux.setEnListaNegraUltimos6Meses(new Date());
                    cotizanteManager.updateListaNegra(listaNegra);
                    return new ValidationResult(false, "Rechazado: Esta en la lista negra, se encontrará en lista negra con fecha de hoy "+sdf.format(aux.getEnListaNegraUltimos6Meses()));
            
                }
            }
        }
        return new ValidationResult(true, "Aprobado");
    }
}
