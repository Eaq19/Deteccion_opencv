/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author HECTOR
 */
public class Delito {
    
    private int idDelito;
    private String nombre;
    private int categoria;

    public Delito() {
    }

    public Delito(int idDelito, String nombre, int categoria) {
        this.idDelito = idDelito;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public int getIdDelito() {
        return idDelito;
    }

    public void setIdDelito(int idDelito) {
        this.idDelito = idDelito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
}
