
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import eam_soft.data_management.CotizanteManager;
import eam_soft.models.Cotizante;
import eam_soft.service.Validator;
import eam_soft.service.transfer.TransferProcess;
import eam_soft.service.validations.rules.*;

public class App {

    private static final CotizanteManager COTIZANTE_MANAGER = new CotizanteManager();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
        
            switch (opcion) {
                case 1:
                    mostrarTodosLosCotizantes();
                    break;
                case 2:
                    ejecutarProcesoDeValidacion();
                    break;
                case 3:
                    salirDelPrograma();
                    return; // Salir del bucle principal
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Mostrar todos los cotizantes");
        System.out.println("2. Ejecutar proceso de validación para cada cotizante");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        while (!SCANNER.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            SCANNER.next();
        }
        int opcion = SCANNER.nextInt();
        SCANNER.nextLine(); // Limpiar buffer
        return opcion;
    }

    private static void mostrarTodosLosCotizantes() {
        List<Cotizante> cotizantes = COTIZANTE_MANAGER.findAllCotizantes("cotizantes.csv", COTIZANTE_MANAGER.getCotizantesCsv());
        if (cotizantes.isEmpty()) {
            System.out.println("No hay cotizantes para mostrar.");
        } else {
            mostrarCotizantesEnTabla(cotizantes);
        }
    }

    private static void mostrarCotizantesEnTabla(List<Cotizante> cotizantes) {
        String formatoTabla = "| %-10s | %-10s | %-3d | %-3d | %-10s | %-8s | %-5s | %-6s | %-10b | %-10b |%n";

        System.out.format("| Nombre          | Documento    | Edad | Semanas | Fondo      | Ciudad   | País  | Género | ListaNegra | PrePensionado |%n");
        System.out.format("|-----------------+--------------+-----+-----+------------+----------+-------+--------+-----------+-------------+%n");

        cotizantes.forEach(cotizante -> System.out.format(
                formatoTabla,
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
        ));

        System.out.format("+-----------------+--------------+-----+-----+------------+----------+-------+--------+-----------+-------------+%n");
    }

    private static void ejecutarProcesoDeValidacion() {
        List<Cotizante> cotizantes = COTIZANTE_MANAGER.findAllCotizantes("cotizantes.csv", COTIZANTE_MANAGER.getCotizantesCsv());

        if (cotizantes.isEmpty()) {
            System.out.println("No hay cotizantes para validar.");
            return;
        }

        Validator validador = crearValidador();
        TransferProcess proceso = new TransferProcess(validador);

        System.out.println("\n--- Resultados del Proceso de Validación ---");
        proceso.processCotizantes(cotizantes);
    }

    private static Validator crearValidador() {
        return new Validator(Arrays.asList(
                new RuleBlackList(),
                new RulePreRetired(),
                new RuleInstitution()
        ));
    }

    private static void salirDelPrograma() {
        System.out.println("Saliendo del programa...");
        SCANNER.close();
        System.exit(0);
    }

    /* 
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
            scanner.nextLine();

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
        TransferProcess proceso = new TransferProcess(validador);

        System.out.println("\n--- Resultados del Proceso de Validación ---");
        proceso.processCotizantes(cotizantes);        
    }
    */



    

    
}
