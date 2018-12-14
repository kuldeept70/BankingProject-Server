/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import java.sql.*;

/**
 *
 * @author Kuldeep Raj Tiwari
 */
public class MakeStatement {
    public static void createStatement(int acc,int amt,int bal,String date,String by){
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        try{
        if(stmt.executeUpdate("INSERT INTO TRANSACTION (ACC,DATE_TRAN,BY_TRAN,AMOUNT_TRAN,FINAL_AMOUNT) VALUES("+acc+",'"+date+"','"+by+"','"+Integer.toString(amt)+"',"+bal+" )")==0)
            System.out.print("No row affected");
        }catch(Exception e){System.out.println("hello "+e);}
        }
        catch(Exception e){ System.out.println(e);}
    }
}
