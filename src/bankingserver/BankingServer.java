/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import bankingserver.*;
import Interface.*;
/**
 *
 * @author Kuldeep Raj Tiwari
 */
public class BankingServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerFrame frame=new ServerFrame();
        try
        {   Management m=new ManagementImpl();
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.rebind("Management",m);
            System.err.println("server is working");
            frame.setTextStatus("Server Ready");
        }
        catch(Exception e)
        {   frame.setTextStatus("Server Failed To Start");
            System.err.println("System Exception : "+e.toString());
            e.printStackTrace();
        }
        // TODO code application logic here
    }
    
}
