/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HECTOR
 */
public class Conexion {

    private static Connection cnx = null;
    private int iUltimo = 0;

    public Conexion() {
    }

    public static Connection obtener() throws SQLException, ClassNotFoundException {
        if (cnx == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cnx = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/deteccion", "root", "");
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
                throw new ClassCastException(ex.getMessage());
            }
        }
        return cnx;
    }

    public static void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
    }

    public DefaultTableModel getUsuarios() {
        DefaultTableModel modelo = new DefaultTableModel();
        Statement sentencia;
        try {
            sentencia = obtener().createStatement();
            ResultSet resultado = sentencia.executeQuery("select * from usuario");
            ResultSetMetaData campos = resultado.getMetaData();
            int cantidadColumnas = campos.getColumnCount();
            for (int i = 1; i <= cantidadColumnas; i++) {
                modelo.addColumn(campos.getColumnLabel(i));
            }
            while (resultado.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = resultado.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            resultado.close();
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + " Información");
        } catch (Exception e) {
            System.out.println("Error: " + e + " Información");
        }
        return modelo;
    }

    public void deleteUsuario(int idUsuario) throws ClassNotFoundException {
        try {

            PreparedStatement sentencia;
            String instruccion = "Update usuario set estado=false where idUsuario='" + idUsuario + "'";
            sentencia = obtener().prepareStatement(instruccion);
            sentencia.execute();
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + " Información");
        }
    }

    public void setUsuarios(String nombres, String apellidos, String documento, String telefono, String correo, String genero, boolean estado, String direccion, String imagen, int idAntecedente) throws SQLException {

        try {
            PreparedStatement sentencia;
            String instruccion = "insert into usuario values(?,?,?,?,?,?,?,?,?,?,?)";
            sentencia = obtener().prepareStatement(instruccion);
            sentencia.setString(1, documento);
            sentencia.setString(2, nombres);
            sentencia.setString(3, apellidos);
            sentencia.setString(4, documento);
            sentencia.setString(5, telefono);
            sentencia.setString(6, correo);
            sentencia.setString(7, genero);
            sentencia.setBoolean(8, estado);
            sentencia.setString(9, direccion);
            sentencia.setString(10, imagen);
            sentencia.setInt(11, idAntecedente);
            sentencia.execute();
        } catch (SQLException e) {
            System.out.println("Error SQL" + e + "Información");
        } catch (Exception e) {
            System.out.println("Error del sistema " + e + " Información");
        }
        
    }

    public void updateUsuario(int idUsuario, String nombres, String apellidos, String documento, String telefono, String correo, String genero, boolean estado, String direccion, String imagen, int idAntecedente) throws ClassNotFoundException {

        try {

            PreparedStatement sentencia;
            String instruccion = "Update usuario set nombres=?, apellidos=?, documento=?, telefono=?, correo=?, genero=?, estado=?, direccion=?, imagen=?, idAntecedente=? where idUsuario='" + idUsuario + "'";
            sentencia = obtener().prepareStatement(instruccion);
            sentencia.setString(1, nombres);
            sentencia.setString(2, apellidos);
            sentencia.setString(3, documento);
            sentencia.setString(4, telefono);
            sentencia.setString(5, correo);
            sentencia.setString(6, genero);
            sentencia.setBoolean(7, estado);
            sentencia.setString(8, direccion);
            sentencia.setString(9, imagen);
            sentencia.setInt(10, idAntecedente);
            sentencia.execute();
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + "Información");
        }
    }

    public String getById(String id) {
        try {
            Statement sentencia;
            sentencia = obtener().createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM usuario WHERE idUsuario = '" + id + "'");
            String ultimo = ""; 
            while (resultado.next()) {
                ultimo = resultado.getObject(2).toString() + " " + resultado.getObject(3).toString();
            }
            resultado.close();
            return ultimo;
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + " Información");
        } catch (Exception e) {
            System.out.println("Error: " + e + " Información");
        }
        return "";
    }
    
    public List<String> getList() {
        List<String> list = null;
        Statement sentencia;
        try {
            sentencia = obtener().createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM `usuario` ");
            list = new ArrayList<String>();
            int iAux = 0;
            while (resultado.next()) {
                iAux = Integer.parseInt(resultado.getObject(1).toString());
//                System.out.println(iAux);
                list.add(iAux, resultado.getObject(2).toString() + " " + resultado.getObject(3).toString());
            }
            for (String string : list) {
                System.out.println("Lista: " + string);
            }
            resultado.close();
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + " Información");
        } catch (Exception e) {
            System.out.println("Error: " + e + " Información");
        }
        return list;
    }
}
