/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Acer
 */
public class Users {

    private final Integer id;
    private String nombre;
    private String imagen;

    public Users() {
        this.id = null;
        this.nombre = null;
        this.imagen = null;
    }

    public Users(Integer id, String nombre, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }
    

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", nombre=" + nombre + ", imagen=" + imagen + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public Integer getId() {
        return id;
    }
}
