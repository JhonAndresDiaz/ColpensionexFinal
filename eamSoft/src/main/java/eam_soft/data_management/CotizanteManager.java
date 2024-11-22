package eam_soft.data_management;

import eam_soft.cache.SuperCache;
import eam_soft.models.Cotizante;
import eam_soft.orm.CSVOrmCotizante;

import java.io.*;
import java.util.*;

public class CotizanteManager {

    private CSVOrmCotizante ormCotizante;
    private SuperCache cache;

    private final String cotizantesCsv;
    private final String listaNegraCsv;
    private static final String LISTA_NEGRA_CACHE = "lista_negra.csv";

    public CotizanteManager() {
        this.ormCotizante = new CSVOrmCotizante();
        this.cache = SuperCache.getInstance();
        this.cotizantesCsv = loadResourcePath("cotizantes.csv");
        this.listaNegraCsv = loadResourcePath("lista_negra.csv");
        cleanExpiredEntriesInListaNegra();
    }

    private String loadResourcePath(String resourceName) {
        return getClass().getClassLoader().getResource(resourceName).getPath();
    }

    public List<Cotizante> findAllCotizantes(String fileName, String filePath) {
        Map<String, Map<String, String>> cachedData = getCachedData(fileName);
        if (cachedData != null) {
            return mapFromCache(cachedData);
        }
        List<Cotizante> cotizantes = ormCotizante.leerTodasLasFilas(filePath);
        updateCache(fileName, cotizantes);
        return cotizantes;
    }

    public Optional<Cotizante> findById(String id, String fileName, String filePath) {
        return findAllCotizantes(fileName, filePath).stream()
                .filter(cotizante -> cotizante.getDocumento().equals(id))
                .findFirst();
    }

    public void save(Cotizante cotizante, String filePath, String cacheName) {
        ormCotizante.escribirFila(filePath, cotizante);
        cache.delete(cacheName);
    }

    private void cleanExpiredEntriesInListaNegra() {
        List<Cotizante> listaNegra = findAllCotizantes(LISTA_NEGRA_CACHE, listaNegraCsv);

        List<Cotizante> updatedListaNegra = new ArrayList<>();
        Date today = new Date();

        for (Cotizante cotizante : listaNegra) {
            if (!isEntryExpired(cotizante, today)) {
                updatedListaNegra.add(cotizante);
            }
        }
        updateListaNegra(updatedListaNegra);
    }

    private boolean isEntryExpired(Cotizante cotizante, Date referenceDate) {
        if (cotizante.getEnListaNegraUltimos6Meses() == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cotizante.getEnListaNegraUltimos6Meses());
        calendar.add(Calendar.MONTH, 6);
        return calendar.getTime().before(referenceDate);
    }

    public void updateListaNegra(List<Cotizante> listaNegra) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(listaNegraCsv))) {
            for (Cotizante cotizante : listaNegra) {
                writer.println(cotizante.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
        cache.delete(LISTA_NEGRA_CACHE);
    }

    private Map<String, Map<String, String>> getCachedData(String cacheName) {
        return (Map<String, Map<String, String>>) cache.getCache(cacheName);
    }

    private List<Cotizante> mapFromCache(Map<String, Map<String, String>> cachedData) {
        List<Cotizante> cotizantes = new LinkedList<>();
        for (Map<String, String> data : cachedData.values()) {
            cotizantes.add(ormCotizante.mapearFila(data));
        }
        return cotizantes;
    }

    private void updateCache(String cacheName, List<Cotizante> cotizantes) {
        Map<String, Map<String, String>> cacheData = new HashMap<>();
        for (Cotizante cotizante : cotizantes) {
            cacheData.put(cotizante.getDocumento(), ormCotizante.extraerAtributos(cotizante));
        }
        cache.addCache(cacheName, cacheData);
    }

    public String getCotizantesCsv() {
        return this.cotizantesCsv;
    }

    public String getListaNegraCsv() {
        return this.listaNegraCsv;
    }
}
