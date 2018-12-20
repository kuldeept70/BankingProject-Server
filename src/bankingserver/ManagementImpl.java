/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.sql.*;
import Interface.Management;
import Interface.UserDetails;

/**
 *
 * @author Kuldeep Raj Tiwari
 */
public class ManagementImpl extends UnicastRemoteObject implements Management{
    public ManagementImpl()throws RemoteException{
        super();
    }
    @Override
    public UserDetails getDetails(int acc){
        UserDetails u=new UserDetails();
        u.acc=0;
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select * from USERDETAIL where acc="+acc);
        while(rs.next()){
        u.acc=acc;
        u.email=rs.getString(12);
        u.name=rs.getString(2);
        u.add=rs.getString(8);
        u.mno=rs.getString(7);
        u.uid=rs.getString(9);
        u.balance=Integer.parseInt(rs.getString(3));
        u.uname=rs.getString(10);
        return(u);
        }
        }
        catch(Exception e){};
        return(u);
        
      }
    @Override
    public int newRegistration(UserDetails u){
       int flag=0; 
       try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select acc,flag from USERDETAIL where acc="+u.acc+" and mob="+u.mno);
        while(rs.next()){
            flag=1;
            if(Integer.parseInt(rs.getString(2))==0){
            System.out.println(" User acc found ");
            ResultSet res=stmt.executeQuery("Select * from USERLOGIN where username='"+u.uname+"'");
            while(res.next()){
                throw new ArithmeticException();
            }
            stmt.executeUpdate("Update USERDETAIL SET SECURITYQNS='"+u.sq+"',SECURITYANS='"+u.ans+"',USERNAME='"+u.uname+"',flag=1 where acc="+u.acc);
            stmt.executeUpdate("insert into USERLOGIN (USERNAME,PWD) VALUES ('"+u.uname+"','"+u.password+"')");
            return(1);
            }
            else
            {System.out.println("this acc already has username");return(-2);}
        }
       }
       catch(ArithmeticException e){System.out.println("User already exist");return(-1);}
       catch(Exception e){System.out.println("Registration "+e);};
       if(flag==1)
          return(0);
       else
           return(-3);
    }
    @Override
    public int openAccount(UserDetails u){
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        stmt.executeUpdate("Insert INTO USERDETAIL(acc,name,current_bal,dob,mob,address,aadhar,email) Values("+u.acc+",'"+u.name+"',"+u.balance+",'"+u.dob+"','"+u.mno+"','"+u.add+"','"+u.uid+"','"+u.email+"')");
        }catch(Exception e){System.out.println("openAccount error "+e);return 0;}
        return(1);
    }
    @Override
    public int withdraw(int acc,int amt,String date){
        int temp=Withdraw.withdraw(acc, amt, date);
        if((temp!=-1)&&(temp!=-2)){
          MakeStatement.createStatement(acc, amt, temp, date, "by bank withdraw");
          return(temp);
        }
        else
            return(temp);
    }
    @Override
    public int deposit(int acc,int amt,String date){
        int temp=Deposit.deposit(acc, amt, date);
        if(temp!=-1&&temp!=-2){
            MakeStatement.createStatement(acc, amt, temp, date, "by bank deposit");
            return(temp);
        }
        else
            return(temp);
    }
    @Override
    public int moneyTransfer (int acc,int r_acc,int amt,String date){
        UserDetails u=getDetails(r_acc);
        if(u.acc==0)
            return(0);
        else{
            int temp=Withdraw.withdraw(acc,amt,date);
            if((temp!=-1)&&(temp!=-2)){
             int xtemp=deposit(r_acc,amt,date);
             if(xtemp!=-1&&xtemp!=-2)
             {   MakeStatement.createStatement(acc, amt,temp, date,"Transfer by self");
                 return(temp);
             }
             else
             { Deposit.deposit(acc,amt,date);
                return(-1);
             }
            }
            return(temp);
        }
    }
    @Override
    public int billPayment(int acc,int amt,String date){
        int temp=Withdraw.withdraw(acc,amt,date);
        if((temp!=-1)&&(temp!=-2)){
          MakeStatement.createStatement(acc, amt,temp, date,"bill paid ");
                 return(1);
        }
        else
            return(temp);
    }
    @Override
    public ArrayList<String> getAccStatement(int acc){
        ArrayList<String> temp=new ArrayList<String>();
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select * from Transaction where acc="+acc+" order by DATE_TRAN,FINAL_AMOUNT");
        while(rs.next()){
            temp.add(rs.getString(1)+" "+rs.getString(2)+"\t   "+rs.getString(3)+"\t     "+rs.getString(4)+"\t     "+rs.getString(5));
        }
        }catch(Exception e){ System.out.println("account statement Not printed "+e);}
        return(temp);
    }
    @Override
    public UserDetails login(String u,String p){
        UserDetails user=new UserDetails();
        user.acc=0;
        int acc=0;
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select * from USERLOGIN where username='"+u+"' and pwd='"+p+"'");
        while(rs.next()){
          ResultSet res=stmt.executeQuery("Select acc from USERDETAIL where username='"+u+"'");
          while(res.next()){
              acc=Integer.parseInt(res.getString(1));
          }
          user=getDetails(acc);
          return(user);
        }
        return(user);
        }catch(Exception e){System.out.println("error in login "+e);return(user);}
    }
    @Override
    public String AdLogin(String u,String p){
        String Name="";
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select name from ADMINLOGIN where username='"+u+"' and pwd='"+p+"'");
        while(rs.next()){
            Name=rs.getString(1);
        }
        return(Name);
        }catch(Exception e){System.out.println("error in login "+e);return(Name);}
        
    }
    @Override
    public int changePassword(int acc,String pwd,String sec_ans){
        String str="";
        try{
        Connection conn=new Connect().getConnection();
        Statement stmt=conn.createStatement();
        ResultSet res=stmt.executeQuery("Select username from USERDETAIL where acc="+acc+" and securityans='"+sec_ans+"'");
        while(res.next()){
          str=res.getString(1);
          stmt.execute("Update Userlogin SET pwd='"+pwd+"'where username='"+str+"'");
            return(1);
        }
        }catch(Exception e){System.out.println(" Error in change password "+e);}
        return(0);
    }
    @Override
    public int getOTP(int acc){
        int otpgenerated=0;
        String email;
        Connection conn=new Connect().getConnection();
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select email from USERDETAIL where acc="+acc);
        while(rs.next()){
          email=rs.getString(1);
          otpgenerated=OTP.getOTP();
          SendEmail.sendmail(email, "Your OTP for ACC no XXXX"+acc+" is "+otpgenerated,"Requested One Time Password....");
        }
        return(otpgenerated);
        }catch(Exception e){return otpgenerated;}
    }
    
}
