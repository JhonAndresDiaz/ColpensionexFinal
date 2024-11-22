package eam_soft.orm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import eam_soft.models.Cotizante;

public class CSVOrmCotizante extends CSVOrm<Cotizante>{

    private static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
    private static final Locale LOCALE = Locale.ENGLISH;

    @Override
    public Cotizante mapearFila(Map<String, String> fila) {
        Cotizante cotizante = new Cotizante();
        cotizante.setNombre(fila.getOrDefault("nombre", ""));
        cotizante.setDocumento(fila.getOrDefault("documento", ""));
        cotizante.setEdad(parseEntero(fila.get("edad"), 0));
        cotizante.setSemanasCotizadas(parseEntero(fila.get("semanas_cotizadas"), 0));
        cotizante.setFondo(fila.getOrDefault("fondo", "NA"));
        cotizante.setFondoCivilOpcional(fila.getOrDefault("fondo_civil_opcional", "NA"));
        cotizante.setCiudad(fila.getOrDefault("ciudad", ""));
        cotizante.setPais(fila.getOrDefault("pais", ""));
        cotizante.setGenero(fila.getOrDefault("genero", ""));
        cotizante.setDetalles(fila.getOrDefault("detalles", ""));
        cotizante.setEnListaNegraUltimos6Meses(parseFecha(fila.get("lista_negra_6_meses")));
        cotizante.setEsPrePensionado(parseBooleano(fila.get("pre_pensionado")));
        return cotizante;
    }

    @Override
    public Map<String, String> extraerAtributos(Cotizante cotizante) {
        Map<String, String> atributos = new HashMap<>();
        atributos.put("nombre", cotizante.getNombre());
        atributos.put("documento", cotizante.getDocumento());
        atributos.put("edad", String.valueOf(cotizante.getEdad()));
        atributos.put("semanas_cotizadas", String.valueOf(cotizante.getSemanasCotizadas()));
        atributos.put("fondo", cotizante.getFondo());
        atributos.put("fondo_civil_opcional", cotizante.getFondoCivilOpcional());
        atributos.put("ciudad", cotizante.getCiudad());
        atributos.put("pais", cotizante.getPais());
        atributos.put("genero", cotizante.getGenero());
        atributos.put("detalles", cotizante.getDetalles());
        atributos.put("lista_negra_6_meses", String.valueOf(cotizante.getEnListaNegraUltimos6Meses()));
        atributos.put("pre_pensionado", String.valueOf(cotizante.esPrePensionado()));
        return atributos;
    }
    
    private int parseEntero(String valor, int valorPorDefecto) {
        try {
            return (valor != null && !valor.isEmpty()) ? Integer.parseInt(valor) : valorPorDefecto;
        } catch (NumberFormatException e) {
            return valorPorDefecto;
        }
    }

    private boolean parseBooleano(String valor) {
        return valor != null && Boolean.parseBoolean(valor);
    }

    private Date parseFecha(String valor) {
        if (valor == null || valor.isEmpty()) return null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, LOCALE);
            return sdf.parse(valor);
        } catch (Exception e) {
            return null;
        }
    }
}
