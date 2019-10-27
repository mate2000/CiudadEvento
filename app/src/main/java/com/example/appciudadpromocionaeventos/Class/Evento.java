package com.example.appciudadpromocionaeventos.Class;

public class Evento {

    private String nombre;
    private String lugar;
    private String fechaIncio;
    private String fechaFinalizacion;
    private String horaInicio;
    private String horaFinalizacion;
    private String descripcion;
    private boolean financiado;
    private boolean publicitado;
    private boolean SocitarFinanciacion;
    private boolean soliciatarPublicidad;
    private String email;
    private boolean aprobado;


    public Evento(String nombre, String lugar, String fechaIncio, String fechaFinalizacion, String horaInicio,
                  String horaFinalizacion, String descripcion, boolean financiado, String email, boolean aprobado, boolean publicitado ,boolean SolicitarFinanciacion,
                  boolean SolicitarPublicidad) {
        this.nombre = nombre;
        this.lugar=lugar;
        this.fechaIncio=fechaIncio;
        this.fechaFinalizacion=fechaFinalizacion;
        this.horaInicio=horaInicio;
        this.horaFinalizacion=horaFinalizacion;
        this.descripcion=descripcion;
        this.financiado=financiado;
        this.publicitado=publicitado;
        this.SocitarFinanciacion=SolicitarFinanciacion;
        this.soliciatarPublicidad=SolicitarPublicidad;
        setEmail(email);
        setAprobado(aprobado);
    }



    public Evento(){
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFechaIncio() {
        return fechaIncio;
    }

    public void setFechaIncio(String fechaIncio) {
        this.fechaIncio = fechaIncio;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(String horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getFinanciado() {
        return financiado;
    }

    public void setFinanciado(boolean financiado) {
        this.financiado = financiado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String idetifiedCard) {
        this.email = idetifiedCard;
    }

    public boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public boolean isPublicitado() {
        return publicitado;
    }

    public void setPublicitado(boolean publicitado) {
        this.publicitado = publicitado;
    }

    public boolean isSocitarFinanciacion() {
        return SocitarFinanciacion;
    }

    public void setSocitarFinanciacion(boolean socitarFinanciacion) {
        SocitarFinanciacion = socitarFinanciacion;
    }

    public boolean isSoliciatarPublicidad() {
        return soliciatarPublicidad;
    }

    public void setSoliciatarPublicidad(boolean soliciatarPublicidad) {
        this.soliciatarPublicidad = soliciatarPublicidad;
    }
}

