/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Kuldeep Raj Tiwari
 */
public class Withdraw {
    public static int withdraw(int acc,int amt,String date){
        int bal=0;
        int flag=0;
        String email="";
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select current_bal,email from USERDETAIL where acc="+acc);
        while(rs.next()){
        bal=Integer.parseInt(rs.getString(1));
        email=rs.getString(2);
        flag=1;
        }
        if(amt<=bal)
        { bal-=amt;
          stmt.executeUpdate("Update USERDETAIL set current_bal="+bal+" where acc="+acc);
          SendEmail.sendmail(email, "Your acc XXXX"+acc+" debited with amount INR "+amt+" Your balance is INR "+bal,"Transaction Alert");
        }
        else
            throw new ArithmeticException();
        }
        catch(ArithmeticException e){if(flag==1)return(-2);else return(-1);}
        catch(RuntimeException e){ return(bal);}
        catch(Exception e){System.out.println("err ; "+e);return(-1);}
        return(bal);
    }
    
}
