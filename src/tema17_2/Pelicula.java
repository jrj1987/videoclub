/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema17_2;

/**
 *
 * @author MEDAC
 */
public class Pelicula {

    private String titulo;
    private int año;
    private int puntuacion;
    private String sinopsis;

    public Pelicula(String titulo, int año, int puntuacion, String sinopsis) {
        this.titulo = titulo;
        this.año = año;
        this.puntuacion = puntuacion;
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return titulo + " (" + año + ")" + "[" + puntuacion + "]";
    }

}
