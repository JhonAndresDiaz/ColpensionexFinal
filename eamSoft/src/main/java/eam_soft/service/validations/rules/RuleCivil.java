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

        resultado = validarPorFondo(cotizante.getFondo(), cotizante.getSemanasCotizadas());
        if (resultado != null) return resultado;

        resultado = validarPorFondo(cotizante.getFondoCivilOpcional(), cotizante.getSemanasCotizadas());
        if (resultado != null) return resultado;

        return new ValidationResult(false, "Rechazado: No pertenece a ninguna institución");
    }

    private ValidationResult validarPorCiudad(String ciudad) {
        switch (ciudad) {
            case "Bogotá":
            case "Medellín":
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





    /* 
    @Override
    public ResultadoValidacion validar(Cotizante cotizante) {
        switch (cotizante.getCiudad()) {
            case "Bogotá":
                return new ResultadoValidacion(false, "Rechazado: Nació y reside en Bogotá");
            case "Medellín":
                return new ResultadoValidacion(false, "Rechazado: Nació y reside en Medellín");
            case "Cali":
                return new ResultadoValidacion(false, "Rechazado: Nació y reside en Cali");
            default:
                if(cotizante.getPais().endsWith("tán")){
                    return new ResultadoValidacion(false, "Rechazado: Es de un país que termina en tán");
                }
        }
        if(cotizante.getEdad() > 62 && cotizante.getGenero().equals("Masculino")){
            return new ResultadoValidacion(false, "Rechazado: Alcanzó la edad para aplicar al RPM");
        }else if(cotizante.getEdad() > 57 && cotizante.getGenero().equals("Femenino")){
            return new ResultadoValidacion(false, "Rechazado: Alcanzó la edad para aplicar al RPM");
        }
        
        switch (cotizante.getFondoCivilOpcional()) {
            case "Porvenir":
                if(cotizante.getSemanasCotizadas() <= 800){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "Proteccion":
                if(cotizante.getSemanasCotizadas() <= 590){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "Colfondos":
                if(cotizante.getSemanasCotizadas() <= 300){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "Old Mutual":
                if(cotizante.getSemanasCotizadas() <= 100){
                    return new ResultadoValidacion(true, "Aprovado");
                }else{
                    return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                }
            case "Fondo extranjero":
                return new ResultadoValidacion(true, "Aprovado");
        
            default:
                switch (cotizante.getFondoCivilOpcional()) {
                    case "Porvenir":
                        if(cotizante.getSemanasCotizadas() <= 800){
                            return new ResultadoValidacion(true, "Aprovado");
                        }else{
                            return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                        }
                    case "Proteccion":
                        if(cotizante.getSemanasCotizadas() <= 590){
                            return new ResultadoValidacion(true, "Aprovado");
                        }else{
                            return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                        }
                    case "Colfondos":
                        if(cotizante.getSemanasCotizadas() <= 300){
                            return new ResultadoValidacion(true, "Aprovado");
                        }else{
                            return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                        }
                    case "Old Mutual":
                        if(cotizante.getSemanasCotizadas() <= 100){
                            return new ResultadoValidacion(true, "Aprovado");
                        }else{
                            return new ResultadoValidacion(false, "Rechazado: Supera el maximo de semanas para el fondo al que pertenece");
                        }
                    case "Fondo extranjero":
                        return new ResultadoValidacion(true, "Aprovado");
                
                    default:
                        return new ResultadoValidacion(false, "Rechazado: No pertenece a ninguna institución");
        
                }

        }
    }
    */

}