package eam_soft.service.validations.rules;

import eam_soft.models.Cotizante;
import eam_soft.service.validations.message.ValidationResult;

public class RulePreRetired implements RuleInterfaz{

    @Override
    public ValidationResult validar(Cotizante cotizante) {
        if (cotizante.esPrePensionado()) {
            return new ValidationResult(false, "Rechazado: Es pre-pensionado");
        }
        return new ValidationResult(true, "");
    }
}
