
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import eam_soft.data_management.CotizanteManager;
import eam_soft.models.Cotizante;
import eam_soft.service.Validator;
import eam_soft.service.transfer.ProcesoTransferencia;
import eam_soft.service.validations.rules.RuleInstitution;
import eam_soft.service.validations.rules.RuleBlackList;
import eam_soft.service.validations.rules.RulePreRetired;

public class App {
    public static void main(String[] args) {
        CotizanteManager cotizanteManager = new CotizanteManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Mostrar todos los cotizantes");
            System.out.println("2. Ejecutar proceso de validación para cada cotizante");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    List<Cotizante> cotizantes = cotizanteManager.findAllCotizantes("cotizantes.csv", cotizanteManager.getCotizantesCsv());
                    mostrarCotizantesEnTabla(cotizantes);
                    break;
                case 2:
                    ejecutarProcesoDeValidacion(cotizanteManager);
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarCotizantesEnTabla(List<Cotizante> cotizantes) {
        String formatoTabla = "| %-12s | %-10s | %-3s | %-3s | %-20s | %-10s | %-15s | %-10s | %-5s | %-5s |%n";
        System.out.format("+-----------------+------------+-------+------------------+--------+---------+-------+---------+-------------+-----------------+%n");
        System.out.format("| Nombre          | Documento  | Edad  | SemanasCotizadas | Fondo  | Ciudad  | Pais  | Genero  | ListaNegra  | Pre-pensionado  |%n");
        System.out.format("+-----------------+------------+-------+------------------+--------+---------+-------+---------+-------------+-----------------+%n");

        for (Cotizante cotizante : cotizantes) {
            System.out.format(formatoTabla,
                    cotizante.getNombre(),
                    cotizante.getDocumento(),
                    cotizante.getEdad(),
                    cotizante.getSemanasCotizadas(),
                    cotizante.getFondo(),
                    cotizante.getCiudad(),
                    cotizante.getPais(),
                    cotizante.getGenero(),
                    cotizante.getEnListaNegraUltimos6Meses(),
                    cotizante.esPrePensionado()
            );
        }

        System.out.format("+-----------------+------------+-------+------------------+--------+---------+-------+---------+-------------+-----------------+%n");

    }

    private static void ejecutarProcesoDeValidacion(CotizanteManager cotizanteManager) {        
        List<Cotizante> cotizantes = cotizanteManager.findAllCotizantes("cotizantes.csv", cotizanteManager.getCotizantesCsv());
        Validator validador = new Validator(Arrays.asList(
                new RuleBlackList(),
                new RulePreRetired(),
                new RuleInstitution()
        ));
        ProcesoTransferencia proceso = new ProcesoTransferencia(validador);

        System.out.println("\n--- Resultados del Proceso de Validación ---");
        proceso.procesarCotizantes(cotizantes);        
    }
}
