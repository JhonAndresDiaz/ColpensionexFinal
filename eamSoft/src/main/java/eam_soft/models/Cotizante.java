package eam_soft.models;

import java.util.Date;

public class Cotizante {
    private String nombre;
    private String documento;
    private int edad;
    private int semanasCotizadas;
    private String fondo;
    private String fondoCivilOpcional;
    private Date enListaNegraUltimos6Meses;
    private boolean esPrePensionado;
    private String ciudad;
    private String pais;
    private String detalles;
    private String genero;

    public Cotizante(String nombre, String documento, int edad, int semanasCotizadas, String fondo, String fondoCivilOpcional, Date enListaNegraUltimos6Meses, boolean esPrePensionado, String ciudad, String pais, String detalles, String genero) {
        this.nombre = nombre;
        this.documento = documento;
        this.edad = edad;
        this.semanasCotizadas = semanasCotizadas;
        this.fondo = fondo;
        this.fondoCivilOpcional = fondoCivilOpcional;
        this.enListaNegraUltimos6Meses = enListaNegraUltimos6Meses;
        this.esPrePensionado = esPrePensionado;
        this.ciudad = ciudad;
        this.pais = pais;
        this.detalles = detalles;
        this.genero = genero;
    }

    public Cotizante() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getSemanasCotizadas() {
        return semanasCotizadas;
    }

    public void setSemanasCotizadas(int semanasCotizadas) {
        this.semanasCotizadas = semanasCotizadas;
    }

    public Date getEnListaNegraUltimos6Meses() {
        return enListaNegraUltimos6Meses;
    }

    public void setEnListaNegraUltimos6Meses(Date enListaNegraUltimos6Meses) {
        this.enListaNegraUltimos6Meses = enListaNegraUltimos6Meses;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    public String getFondoCivilOpcional() {
        return this.fondoCivilOpcional;
    }

    public void setFondoCivilOpcional(String fondoPrivado) {
        this.fondoCivilOpcional = fondoPrivado;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDetalles() {
        return this.detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean esPrePensionado() {
        return this.esPrePensionado;
    }

    public void setEsPrePensionado(boolean esPrePensionado) {
        this.esPrePensionado = esPrePensionado;
    }

    @Override
    public String toString() {
        return "Cotizante{" +
                "nombre='" + nombre + '\'' +
                ", documento='" + documento + '\'' +
                ", edad=" + edad +
                ", semanasCotizadas=" + semanasCotizadas + '\'' +
                ", fondoPublico=" +fondo +
                ", fondoCivil=" +fondoCivilOpcional +
                ", ciudad=" +ciudad +
                ", pais=" +pais +
                ", genero=" +genero +
                ", detalles= "+detalles+
                ", enListaNegraUltimos6Meses=" +enListaNegraUltimos6Meses +
                ", esPrePensionado=" +esPrePensionado +
                '}';
    }

    public String toCSV() {
        return ","+nombre+","+documento+","+edad+","+semanasCotizadas+","+enListaNegraUltimos6Meses+","+esPrePensionado+","+"..."+","+"..."+","+fondo+","+fondoCivilOpcional+","+genero+","+ciudad+","+pais+","+detalles;
    }
}
