package eam_soft.cache;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateCSV {
    private Random random = new Random();

    private List<String> nombres = Arrays.asList(
        "Juan Perez", "Carlos Rodriguez", "Maria Garcia", 
        "Ana Lopez", "Luis Fernando", "Sofia Martinez", 
        "David Sanchez", "Isabel Ramirez", "Pedro Gomez", 
        "Laura Torres", "Joseph Contreras", "Maria Magdalena",
        "Andres Diaz"
    );

    private List<String> ciudadesColombia = Arrays.asList(
        "Medellin", "Bogota", "Cali", 
        "Pereira", "Armenia", "Manizales"
    );

    private List<String> ciudadesInternacionales = Arrays.asList(
        "Lima", "Buenos Aires", "Santiago", "Ciudad de Mexico", 
        "Quito", "La Paz", "Montevideo"
    );

    private List<String> paises = Arrays.asList(
        "Colombia", "Peru", "Argentina", "Chile", 
        "Mexico", "Ecuador", "Bolivia", "Uruguay"
    );

    private List<String> fondosPublicos = Arrays.asList(
        "Publico", "Inpec", "Policia", "Minsalud", "Armada"
    );

    private List<String> fondosCiviles = Arrays.asList(
        "Porvenir", "Proteccion", "Colfondos", "Old Mutual"
    );

    private List<String> detalles = Arrays.asList(
        "Si No", "No Si", "No No", "Si Si", "Si", "No"
    );

    public void generateData(int numRecords, String filename) {
        try (FileWriter csvWriter = new FileWriter(filename)) {
            csvWriter.append("id,nombre,documento,edad,semanas_cotizadas,")
                     .append("fondo_publico,fondo_civil,fondo,")
                     .append("fondo_civil_opcional,genero,ciudad,")
                     .append("pais,detalles,lista_negra_6_meses,pre_pensionado\n");

            for (int i = 1; i <= numRecords; i++) {
                String pais = paises.get(random.nextInt(paises.size()));
                
                boolean tieneInstitucion = random.nextDouble() < 0.95;
                boolean esPublico = tieneInstitucion ? random.nextDouble() < 0.5 : false;
                
                boolean prePensionado = random.nextDouble() < 0.17;

                String ciudad = pais.equals("Colombia") 
                    ? ciudadesColombia.get(random.nextInt(ciudadesColombia.size()))
                    : ciudadesInternacionales.get(random.nextInt(ciudadesInternacionales.size()));

                String fondoPublico = esPublico ? "Publico" : "...";
                String fondoCivil = !esPublico && tieneInstitucion ? "Civil" : "...";
                
                String fondo = esPublico 
                    ? fondosPublicos.get(random.nextInt(fondosPublicos.size())) 
                    : (tieneInstitucion ? fondosCiviles.get(random.nextInt(fondosCiviles.size())) : "...");

                csvWriter.append(String.format("%d,", i))
                         .append(String.format("%s,", nombres.get(random.nextInt(nombres.size()))))
                         .append(String.format("%d,", random.nextInt(900000000) + 1000000000))
                         .append(String.format("%d,", random.nextInt(51) + 25))
                         .append(String.format("%d,", random.nextInt(751) + 50))
                         .append(String.format("%s,", fondoPublico))
                         .append(String.format("%s,", fondoCivil))
                         .append(String.format("%s,", fondo))
                         .append(String.format("%s,", 
                             tieneInstitucion && !esPublico && random.nextBoolean()
                             ? fondosCiviles.get(random.nextInt(fondosCiviles.size())) 
                             : "..."))
                         .append(String.format("%s,", random.nextBoolean() ? "Masculino" : "Femenino"))
                         .append(String.format("%s,", ciudad))
                         .append(String.format("%s,", pais))
                         .append(String.format("%s,", detalles.get(random.nextInt(detalles.size()))))
                         .append(String.format("%b,", random.nextBoolean()))
                         .append(String.format("%b\n", prePensionado));
            }
            System.out.println("Se han generado " + numRecords + " registros en " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CreateCSV generator = new CreateCSV();
        generator.generateData(8500, "eamSoft/src/main/resources/cotizantes.csv");
    }
}
