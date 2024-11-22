package eam_soft.service.validations.rules;

import eam_soft.models.Cotizante;
import eam_soft.service.transfer.TransferirListaNegra;
import eam_soft.service.validations.message.ValidationResult;

public class RuleInstitution implements RuleInterfaz{

    private final TransferirListaNegra transferList = new TransferirListaNegra();
    private final RuleCivil procesarComoCivil = new RuleCivil();
    
    @Override
    public ValidationResult validar(Cotizante cotizante) {
        String fondo = cotizante.getFondo();
        String detalles = cotizante.getDetalles();

        switch (fondo) {
            case "Armada":
                return validarArmada(detalles, cotizante);

            case "Inpec":
                return validarInpec(detalles, cotizante);

            case "Policia":
                return validarPolicia(detalles, cotizante);

            case "Minsalud":
                return validarMinisterios(detalles, cotizante);

            case "Minterior":
                return validarMinisterios(detalles, cotizante);
                
            default:
                return procesarComoCivil.validar(cotizante);
        }
    }

    private ValidationResult validarArmada(String detalles, Cotizante cotizante) {
        if ("Si".equals(detalles)) {
            return new ValidationResult(true, "");
        }
        return procesarComoCivil.validar(cotizante);
    }

    private ValidationResult validarInpec(String detalles, Cotizante cotizante) {
        String[] detallePartes = detalles.split(" ");
        if ("Si".equals(detallePartes[0]) || "No Si".equals(detalles)) {
            return new ValidationResult(true, "");
        }
        return procesarComoCivil.validar(cotizante);
    }

    private ValidationResult validarPolicia(String detalles, Cotizante cotizante) {
        if ("Si Si".equals(detalles)) {
            return new ValidationResult(true, "");
        }
        return procesarComoCivil.validar(cotizante);
    }

    private ValidationResult validarMinisterios(String detalles, Cotizante cotizante) {
        if ("No".equals(detalles)) {
            return new ValidationResult(true, "");
        }
        return transferList.transferirAListaNegra(cotizante);
    }


    /* 
    @Override
    public ResultadoValidacion validar(Cotizante cotizante) {
        ReglaCivil procesarComoCivil = new ReglaCivil();
        switch (cotizante.getFondo()) {
            
            case "Armada":
                if(cotizante.getDetalles().equals("Si")){
                    return new ResultadoValidacion(true, "");
                }else{
                    return procesarComoCivil.validar(cotizante);
                }
                
            case "Inpec":
                String[] detalles = cotizante.getDetalles().split(" ");
                if(detalles[0].equals("Si")){
                    return new ResultadoValidacion(true, "");
                }else if(cotizante.getDetalles().equals("No Si")){
                    return new ResultadoValidacion(true, "");
                }else{
                    return procesarComoCivil.validar(cotizante);
                }                
            
            case "Policia":
                if(cotizante.getDetalles().equals("Si Si")){
                    return new ResultadoValidacion(true, "");
                }else{
                    return procesarComoCivil.validar(cotizante);
                }                
            
            case "Minsalud":
                if(cotizante.getDetalles().equals("No")){
                    return new ResultadoValidacion(true, "");
                }else{
                    return transferList.transferirAListaNegra(cotizante);
                }
            
            case "Minterior":
                if(cotizante.getDetalles().equals("No")){
                    return new ResultadoValidacion(true, "");
                }else{
                    return transferList.transferirAListaNegra(cotizante);
                }
            default:
                return procesarComoCivil.validar(cotizante);
        }
    }
    */
}
