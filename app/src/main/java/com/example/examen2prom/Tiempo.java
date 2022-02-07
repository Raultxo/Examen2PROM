package com.example.examen2prom;

public class Tiempo {

    private String hora;
    private String temperatura;
    private String temperatura_min;
    private String temperatura_max;
    private String dia;
    private String estado;
    private String icono;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTemperatura_max() {
        return temperatura_max;
    }

    public String getTemperatura_min() {
        return temperatura_min;
    }

    public String getEstado() {
        return estado;
    }

    public String getIcono() {
        return icono;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public void setTemperatura_max(String temperatura_max) {
        this.temperatura_max = temperatura_max;
    }

    public void setTemperatura_min(String temperatura_min) {
        this.temperatura_min = temperatura_min;
    }
}
