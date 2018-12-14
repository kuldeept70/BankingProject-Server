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
import java.util.*;
public class OTP {
    public static int getOTP(){
        Random r=new Random();
        int otp=r.nextInt((9999-1000)+1)+1000;
        return(otp);
    } 
    
}
