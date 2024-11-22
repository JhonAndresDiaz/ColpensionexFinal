package eam_soft.service.transfer;

import eam_soft.models.Cotizante;
import eam_soft.service.Validator;
import eam_soft.service.validations.message.ValidationResult;

import java.util.List;

public class TransferProcess {

    private TransferQueue queue = new TransferQueue();
    private Validator validator;

    public TransferProcess(Validator validator) {
        this.validator = validator;
    }

    public void processCotizantes(List<Cotizante> cotizantes) {
        long approved = cotizantes.stream()
            .peek(cotizante -> {
                ValidationResult result = validator.validar(cotizante);
                if (result.esAprobado()) {
                    queue.addCotizante(cotizante);
                    System.out.printf("Cotizante: %s con identificación %s, aprobado y agregado a la cola.%n",
                            cotizante.getNombre(), cotizante.getDocumento());
                } else {
                    System.out.printf("Cotizante: %s con identificación %s, %s%n",
                            cotizante.getNombre(), cotizante.getDocumento(), result.getMotivo());
                }
            })
            .filter(cotizante -> validator.validar(cotizante).esAprobado())
            .count();

        System.out.println("\n------ Resumen de Resultados ------");
        System.out.printf("Total de Cotizantes Aprobados: %d%n", approved);
        System.out.printf("Total de Cotizantes Rechazados: %d%n", cotizantes.size() - approved);
    }

    public void dispatchCotizantes() {
        System.out.println("\n------ Despacho de Cotizantes en Orden de Prioridad ------");
        while (!queue.isEmpty()) {
            Cotizante cotizante = queue.getNextCotizante();
            System.out.printf("Procesando cotizante: %s con identificación %s%n",
                    cotizante.getNombre(), cotizante.getDocumento());
        }
    }
}
