/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Antecedente;
import model.Delito;

/**
 *
 * @author HECTOR
 */
public class PersistenceAntecedente {
    
    
    

    public boolean reinsidente(String idUsuario) {

        Statement sentencia;
        Conexion conn = new Conexion();
        boolean reinsidente = false;
        int cantidadReinsidente = 0;

        try {

            sentencia = conn.obtener().createStatement();
            ResultSet resultado = sentencia.executeQuery("SELECT sentencias from antecedente a, usuario u where u.idUsuario =  \"" + idUsuario + "\" and u.idAntecedente = a.idAntecedente");
            while (resultado.next()) {

                cantidadReinsidente = resultado.getInt("sentencias");
            }

            System.out.println(cantidadReinsidente);
            if (cantidadReinsidente > 2) {
                reinsidente = true;
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + " Información");
        } catch (Exception e) {
            System.out.println("Error: " + e + " Información");
        }
        return reinsidente;
    }

    public List<Delito> getDelitosByNotUsuario() throws SQLException, ClassNotFoundException {

        Conexion conn = new Conexion();
        Statement sentencia;
        List<Delito> arrayDelitos = new ArrayList();

        try {

            sentencia = conn.obtener().createStatement();
            ResultSet resultado = sentencia.executeQuery("select * from Delito");
            while (resultado.next()) {

                Delito objDelito = new Delito();
                objDelito.setIdDelito( resultado.getInt("idDelito"));
                objDelito.setNombre(resultado.getString("nombre"));
                  //objDelito.setCategoria(resultado.getInt("categoria"));

                arrayDelitos.add(objDelito);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + " Información");
        } catch (Exception e) {
            System.out.println("Error: " + e + " Información");
        }
        return arrayDelitos;
    }

    public List<Delito> getDelitosByUsuario(String idUsuario) throws SQLException, ClassNotFoundException {

        Conexion conn = new Conexion();
        Statement sentencia;
        List<Delito> arrayDelitos = new ArrayList();

        try {

            sentencia = conn.obtener().createStatement();
            ResultSet resultado = sentencia.executeQuery(" SELECT d.idDelito, d.nombre \n"
                    + " FROM delito d, usuario u, antecedente_delito ad \n"
                    + " where d.idDelito = ad.idDelito \n"
                    + " and u.idAntecedente = ad.idAntecedente \n"
                    + " and u.idUsuario = \"" + idUsuario + "\"");

            while (resultado.next()) {

                Delito objDelito = new Delito();
                objDelito.setIdDelito(resultado.getInt("idDelito"));
                objDelito.setNombre(resultado.getString("nombre"));
                  //objDelito.setCategoria(resultado.getInt("categoria"));

                arrayDelitos.add(objDelito);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e + " Información");
        } catch (Exception e) {
            System.out.println("Error: " + e + " Información");
        }
        return arrayDelitos;
    }
}
