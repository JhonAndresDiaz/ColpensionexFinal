package eam_soft.orm;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import eam_soft.models.Cotizante;

public abstract class CSVOrm<T> {

    protected abstract T mapearFila(Map<String, String> fila);
    protected abstract Map<String, String> extraerAtributos(T elemento);

    public LinkedList<T> leerTodasLasFilas(String archivoCsv) {
        LinkedList<T> resultados = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCsv))) {
            String headerLine = br.readLine();
            if (headerLine == null) return resultados;

            String[] headers = headerLine.split(",");
            String line;
            while ((line = br.readLine()) != null) {
                Map<String, String> fila = procesarLinea(headers, line);
                T entidad = mapearFila(fila);
                resultados.add(entidad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private Map<String, String> procesarLinea(String[] headers, String line) {
        String[] valores = line.split(",");
        Map<String, String> fila = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            fila.put(headers[i], valores[i]);
        }

        return fila;
    }

    public void escribirFila(String archivoCsv, T elemento) {
        Map<String, String> atributos = extraerAtributos(elemento);

        try (FileWriter writer = new FileWriter(archivoCsv, true)) {
            if (elemento instanceof Cotizante) {
                Cotizante cotizante = (Cotizante) elemento;
                writer.write(cotizante.toCSV() + "\n");
            } else {
                String fila = construirFilaCSV(atributos);
                writer.write(fila + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String construirFilaCSV(Map<String, String> atributos) {
        StringJoiner joiner = new StringJoiner(",");
        for (String valor : atributos.values()) {
            joiner.add(valor);
        }
        return joiner.toString();
    }

    public void actualizarFila(String archivoCsv, Predicate<T> condicion, T nuevoElemento) {
        List<T> filas = leerTodasLasFilas(archivoCsv);
        try (FileWriter writer = new FileWriter(archivoCsv)) {
            if (!filas.isEmpty()) {
                Map<String, String> encabezado = extraerAtributos(filas.get(0));
                writer.write(String.join(",", encabezado.keySet()) + "\n");
            }
            for (T fila : filas) {
                if (condicion.test(fila)) {
                    writer.write(construirFilaCSV(extraerAtributos(nuevoElemento)) + "\n");
                } else {
                    writer.write(construirFilaCSV(extraerAtributos(fila)) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarFila(String archivoCsv, Predicate<T> condicion) {
        List<T> filas = leerTodasLasFilas(archivoCsv);
        try (FileWriter writer = new FileWriter(archivoCsv)) {
            if (!filas.isEmpty()) {
                Map<String, String> encabezado = extraerAtributos(filas.get(0));
                writer.write(String.join(",", encabezado.keySet()) + "\n");
            }
            for (T fila : filas) {
                if (!condicion.test(fila)) {
                    writer.write(construirFilaCSV(extraerAtributos(fila)) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
