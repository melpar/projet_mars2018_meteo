package rmi.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import base.Base;
import bean.ArchiveMeteo;
import bean.Photo;
import bean.Soleil;
import rmi.Serveur;
import util.parsers.ParserJSON;
import util.parsers.ParserXML;
import util.pdf.FirstPdf;
import validation.Validation;

public class ServeurImpl implements Serveur {

    @Override
    public boolean connexion(String identifiant, String mdp) throws RemoteException {
	System.out.println("ident " + identifiant + " mdp " + mdp + ".");
	Base base = new Base();
	base.ouvrir();
	boolean ret = base.connexion(identifiant, mdp);
	base.fermer();
	return ret;
    }

    @Override
    public String ajouterDonneesArchiveXML(String donneesFichier) throws RemoteException {
	StringBuilder builder = new StringBuilder();
	List<ArchiveMeteo> archives = ParserXML.parserXML(donneesFichier);
	for (ArchiveMeteo archive : archives) {
	    builder.append(ajouterDonneeArchive(archive));
	}
	return builder.toString();
    }

    @Override
    public String ajouterDonneesArchiveJSON(String donneesFichier) throws RemoteException {
	StringBuilder builder = new StringBuilder();
	List<ArchiveMeteo> archives = ParserJSON.parserJSON(donneesFichier);
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
    public String ajouterPhoto(int archive, List<Photo> photos) throws RemoteException {
	// TODO Auto-generated method stub
	Base base = new Base();
	base.ouvrir();
	for (Photo photo : photos) {
	    base.ajouterImage(archive, photo);
	}
	base.fermer();
	return null;
    }

    @Override
    public String modifierDonnee(ArchiveMeteo archive) throws RemoteException {
	// TODO Auto-generated method stub
	Base base = new Base();
	base.ouvrir();
	ArchiveMeteo ancienne = base.consulterParId(archive.getId());
	if (!ancienne.getLieu().equals(archive.getLieu())) {
	    // Mise à jour du lieu
	    base.miseAJourLieu(archive.getId(), archive.getLieu());
	}
	// if (!ancienne.getDate().equals(archive.getDate())) {
	// // Mise à jour de la date
	// base.miseAJourDate(archive.getId(), archive.getDate());
	// }
	// if (!ancienne.getDonnee().equals(archive.getDonnee())) {
	// // Mise à jour de la donnée
	// base.miseAJour(archive.getId(), archive.getDonnee());
	// }
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
    public byte[] productionPDF(List<ArchiveMeteo> archives) throws RemoteException {
	// TODO Auto-generated method stub
	FirstPdf pdf = new FirstPdf();
	pdf.initialiser(archives);
	return pdf.generer();
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

	System.out.println("Serveur RMI lancÃ©");
    }

    @Override
    public Validation valider(String pays, String ville, String departement, String direction, String vitesse,
	    String temperature, String pluie, String date, String soleil) throws RemoteException {
	Validation v = new Validation();
	v.regexp(bean.Lieu.class, "ville", ville);
	v.regexp(bean.Lieu.class, "departement", departement);
	v.regexp(bean.Lieu.class, "pays", pays);
	v.regexp(bean.DonneeMeteo.class, "directionVent", direction);
	v.regexp(bean.DonneeMeteo.class, "vitesseVent", vitesse);
	v.regexp(bean.DonneeMeteo.class, "temperature", temperature);
	v.regexp(bean.DonneeMeteo.class, "pluie", pluie);
	// Tester soleil
	if (Soleil.getById(Integer.parseInt(soleil)) == null) {
	    v.addErreur("soleil", "La valeur n'est pas valide");
	} else {
	    v.addValue("soleil", soleil);
	}

	// Tester date
	SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
	Date dateD = new Date();
	try {
	    dateD = formatter.parse(date);
	    v.addValue("date", date);
	} catch (java.text.ParseException e) {
	    v.addErreur("date", "La date n'est pas au bon format");
	}

	return v;
    }

    @Override
    public ArchiveMeteo consulterParId(int id) throws RemoteException {
	ArchiveMeteo arc;
	Base base = new Base();
	base.ouvrir();
	arc = base.consulterParId(id);
	base.fermer();
	return arc;
    }

}
