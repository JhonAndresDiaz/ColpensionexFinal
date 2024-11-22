package eam_soft.service.validations.rules;

import eam_soft.models.Cotizante;
import eam_soft.service.transfer.TransferBlackList;
import eam_soft.service.validations.message.ValidationResult;

public class RuleInstitution implements RuleInterfaz{

    private final TransferBlackList transferList = new TransferBlackList();
    private final RuleCivil procesarComoCivil = new RuleCivil();
    
    @Override
    public ValidationResult validar(Cotizante cotizante) {
        switch (cotizante.getFondo()) {
            
            case "Armada":
                if(cotizante.getDetalles().equals("Si")){
                    return new ValidationResult(true, "");
                }else{
                    return procesarComoCivil.validar(cotizante);
                }
                
            case "Inpec":
                String[] detalles = cotizante.getDetalles().split(" ");
                if(detalles[0].equals("Si")){
                    return new ValidationResult(true, "");
                }else if(cotizante.getDetalles().equals("No Si")){
                    return new ValidationResult(true, "");
                }else{
                    return procesarComoCivil.validar(cotizante);
                }                
            
            case "Policia":
                if(cotizante.getDetalles().equals("Si Si")){
                    return new ValidationResult(true, "");
                }else{
                    return procesarComoCivil.validar(cotizante);
                }                
            
            case "Minsalud":
                if(cotizante.getDetalles().equals("No")){
                    return new ValidationResult(true, "");
                }else{
                    return transferList.transferToBlackList(cotizante);
                }
            
            case "Minterior":
                if(cotizante.getDetalles().equals("No")){
                    return new ValidationResult(true, "");
                }else{
                    return transferList.transferToBlackList(cotizante);
                }
            default:
                return procesarComoCivil.validar(cotizante);
        }
    }
}
