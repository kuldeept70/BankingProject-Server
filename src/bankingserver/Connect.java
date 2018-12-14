/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

/**
 *
 * @author Kuldeep Raj Tiwari
 */
import java.sql.*;
public class Connect {
     Connection conn=null;
     Connection getConnection(){
     try{
         Class.forName("oracle.jdbc.driver.OracleDriver");
         conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/PDBORCL","smith","123456");
        }
     catch(Exception e){ }
     return conn;
     }
 }

