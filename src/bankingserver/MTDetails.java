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
public class MTDetails implements java.io.Serializable{
    int saccno,raccno,amount;
String name,bname;
boolean isNull()
	{
		if(name!=null && !name.isEmpty() && bname!=null && !bname.isEmpty())
	return true;
        else 
	return false;	
	}
    
}
