/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Users;

/**
 *
 * @author Acer
 */
public class User {

    private final String tabla = "users";

    public void guardar(Connection conexion, Users user) throws SQLException {
        try {
            PreparedStatement consulta;
            if (user.getId() == null) {
                consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(nombre, imagen) VALUES(?, ?)");
                consulta.setString(1, user.getNombre());
                consulta.setString(2, user.getImagen());
            } else {
                consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET nombre = ?, imagen = ? WHERE id = ?");
                consulta.setString(1, user.getNombre());
                consulta.setString(2, user.getImagen());
                consulta.setInt(3, user.getId());
            }
            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public Users recuperarPorId(Connection conexion, int id) throws SQLException {
        Users user = null;
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT nombre, imagen FROM " + this.tabla + " WHERE id = ?");
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                user = new Users(id, resultado.getString("nombre"), resultado.getString("imagen"));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return user;
    }

    public void eliminar(Connection conexion, Users user) throws SQLException {
        try {
            PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + this.tabla + " WHERE id = ?");
            consulta.setInt(1, user.getId());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public ArrayList<Users> recuperarTodas(Connection conexion) throws SQLException {
        ArrayList<Users> users = new ArrayList<>();
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT id, nombre, imagen FROM " + this.tabla + " ORDER BY id");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                users.add(new Users(resultado.getInt("id"), resultado.getString("nombre"), resultado.getString("imagen")));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return users;
    }
}
