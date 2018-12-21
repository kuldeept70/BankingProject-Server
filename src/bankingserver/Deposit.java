/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.net.*;

/**
 *
 * @author Kuldeep Raj Tiwari
 */
public class Deposit {
    public static int deposit(int acc,int amt,String date){
        int bal=-2;
        String email="";
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select current_bal,email from USERDETAIL where acc="+acc);
        while(rs.next()){
        bal=Integer.parseInt(rs.getString(1));
        email=rs.getString(2);
        bal+=amt;
        }
        stmt.executeUpdate("Update USERDETAIL set current_bal="+bal+" where acc="+acc);
        SendEmail.sendmail(email,"Your acc XXXX"+acc+"Credited with INR "+amt+" new balance is INR "+bal,"Transaction Alert");
        }
        catch(Exception e){System.out.println("testing "+e);return(-1);}
        return(bal);
    }
    
}
