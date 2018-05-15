/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class Conexion {
    
   private static Connection cnx = null;
   
   public Connection obtener() throws SQLException, ClassNotFoundException {
      if (cnx == null) {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_opencv?zeroDateTimeBehavior=convertToNull", "root", "");
         } catch (SQLException ex) {
            throw new SQLException(ex);
         } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
         }
      }
      return cnx;
   }
   public void cerrar() throws SQLException {
      if (cnx != null) {
         cnx.close();
      }
   }
}