package eam_soft.service.validations.rules;

import eam_soft.models.Cotizante;
import eam_soft.service.validations.message.ValidationResult;

import java.util.Map;

public class RuleCivil implements RuleInterfaz{

    private static final Map<String, Integer> LIMITE_SEMANAS_FONDOS = Map.of(
            "Porvenir", 800,
            "Proteccion", 590,
            "Colfondos", 300,
            "Old Mutual", 100
    );

    @Override
    public ValidationResult validar(Cotizante cotizante) {

        ValidationResult resultado = validarPorCiudad(cotizante.getCiudad());
        if (resultado != null) return resultado;

        if (cotizante.getCiudad().endsWith("tán")) {
            return new ValidationResult(false, "Rechazado: Es de una ciudad que termina en tán");
        }

        resultado = validarPorEdadYGenero(cotizante);
        if (resultado != null) return resultado;

        resultado = validarPorFondo(cotizante.getFondoCivilOpcional(), cotizante.getSemanasCotizadas());
        if (resultado != null) return resultado;

        return new ValidationResult(false, "Rechazado: No pertenece a ninguna institución");
    }

    private ValidationResult validarPorCiudad(String ciudad) {
        switch (ciudad) {
            case "Bogota":
            case "Medellin":
            case "Cali":
                return new ValidationResult(false, "Rechazado: Nació y reside en " + ciudad);
            default:
                return null;
        }
    }

    private ValidationResult validarPorEdadYGenero(Cotizante cotizante) {
        if (cotizante.getEdad() > 62 && "Masculino".equals(cotizante.getGenero())) {
            return new ValidationResult(false, "Rechazado: Alcanzó la edad para aplicar al RPM Masculino");
        } else if (cotizante.getEdad() > 57 && "Femenino".equals(cotizante.getGenero())) {
            return new ValidationResult(false, "Rechazado: Alcanzó la edad para aplicar al RPM Femenino");
        }
        return null;
    }

    private ValidationResult validarPorFondo(String fondoCivil, int semanasCotizadas) {
        if (fondoCivil == null) return null;

        if ("Fondo extranjero".equals(fondoCivil)) {
            return new ValidationResult(true, "");
        }

        Integer limiteSemanas = LIMITE_SEMANAS_FONDOS.get(fondoCivil);
        if (limiteSemanas != null) {
            if (semanasCotizadas < limiteSemanas) {
                return new ValidationResult(true, "");
            } else {
                return new ValidationResult(false, "Rechazado: Supera el máximo de semanas para el fondo al que pertenece");
            }
        }
        return null;
    }
}
