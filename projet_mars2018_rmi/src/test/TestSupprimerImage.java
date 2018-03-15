package test;

import java.rmi.RemoteException;

import org.junit.Test;

import rmi.Serveur;
import rmi.impl.ServeurImpl;

public class TestSupprimerImage {
    @Test
    public void testSupprimerImage() {
	Serveur serv = new ServeurImpl();
	try {
	    serv.supprimerImage(9);
	} catch (RemoteException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
