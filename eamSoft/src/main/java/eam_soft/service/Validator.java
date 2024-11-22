package eam_soft.service;

import eam_soft.models.Cotizante;
import eam_soft.service.validations.message.ValidationResult;
import eam_soft.service.validations.rules.RuleInterfaz;

import java.util.List;

public class Validator {
    
    private final List<RuleInterfaz> reglas;

    public Validator(List<RuleInterfaz> reglas) {
        this.reglas = reglas;
    }

    public ValidationResult validar(Cotizante cotizante) {
        for (RuleInterfaz regla : reglas) {
            ValidationResult resultado = regla.validar(cotizante);
            if (!resultado.esAprobado()) {
                return resultado;
            }
        }
        return new ValidationResult(true, "");
    }
}
