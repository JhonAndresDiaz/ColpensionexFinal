package eam_soft.service.validations.rules;

import eam_soft.models.Cotizante;
import eam_soft.service.validations.message.ValidationResult;

public interface RuleInterfaz {
    ValidationResult validar(Cotizante cotizante);
}
