package rmi.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import base.Base;
import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Photo;
import rmi.Serveur;
import validation.Validation;

public class ServeurImpl implements Serveur {

    @Override
    public boolean connexion(String identifiant, String mdp) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	boolean ret = base.connexion(identifiant, mdp);
	base.fermer();
	return ret;
    }

    @Override
    public String ajouterDonneesArchive(List<ArchiveMeteo> archives) throws RemoteException {
	StringBuilder builder = new StringBuilder();
	for (ArchiveMeteo archive : archives) {
	    builder.append(ajouterDonneeArchive(archive));
	}
	return builder.toString();
    }

    @Override
    public String ajouterDonneeArchive(ArchiveMeteo archive) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	base.ajouterDonneeArchive(archive);
	base.fermer();
	return null;
    }

    @Override
    public String ajouterPhoto(Lieu lieu, Date date, List<Photo> photos) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String modifierDonnee(Lieu lieu, Date date, DonneeMeteo donnees) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<ArchiveMeteo> consulterParJour(Date date) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	List<ArchiveMeteo> archives = base.consulterParJour(date);
	base.fermer();
	return archives;
    }

    @Override
    public List<ArchiveMeteo> consulterParMois(Date date) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	List<ArchiveMeteo> archives = base.consulterParMois(date);
	base.fermer();
	return archives;
    }

    @Override
    public boolean productionPDF(List<ArchiveMeteo> archives) throws RemoteException {
	// TODO Auto-generated method stub
	return false;
    }

    public static void main(String[] args) {

	int port = 2000;

	Registry registry = null;

	try {
	    LocateRegistry.createRegistry(port);
	    registry = LocateRegistry.getRegistry(port);
	} catch (Exception e) {
	    System.out.println("Erreur createRegistry");
	}

	ServeurImpl si = new ServeurImpl();
	Serveur serveurRMI = null;

	try {
	    serveurRMI = (Serveur) UnicastRemoteObject.exportObject(si, 0);
	} catch (Exception e) {
	    System.out.println("Erreur exportObject");
	}

	try {
	    registry.rebind("serveurRMI", serveurRMI);
	} catch (Exception e) {
	    System.out.println("Erreur rebind");
	}

	System.out.println("Serveur RMI lancé");
    }

    @Override
    public Validation valider(String pays, String ville, String departement, String direction, String vitesse,
	    String temperature, String pluie) throws RemoteException {
	Validation v = new Validation();
	v.regexp(bean.Lieu.class, "ville", ville);
	v.regexp(bean.Lieu.class, "departement", departement);
	v.regexp(bean.Lieu.class, "pays", pays);
	v.regexp(bean.DonneeMeteo.class, "directionVent", direction);
	v.regexp(bean.DonneeMeteo.class, "vitesseVent", vitesse);
	v.regexp(bean.DonneeMeteo.class, "temperature", temperature);
	v.regexp(bean.DonneeMeteo.class, "pluie", pluie);
	return v;
    }

}
