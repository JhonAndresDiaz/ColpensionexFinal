package eam_soft.service.validations.message;

public class ValidationResult {
    private boolean aprobado;
    private String motivo;

    public ValidationResult(boolean aprobado, String motivo) {
        this.aprobado = aprobado;
        this.motivo = motivo;
    }

    public boolean esAprobado() {
        return aprobado;
    }

    public String getMotivo() {
        return motivo;
    }
}
