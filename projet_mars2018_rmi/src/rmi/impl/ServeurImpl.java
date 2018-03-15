package rmi.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import base.Base;
import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Photo;
import bean.Soleil;
import rmi.Serveur;
import util.parsers.ParserJSON;
import util.parsers.ParserXML;
import util.pdf.PdfWriter;
import util.xml.XmlWriter;
import validation.Validation;

public class ServeurImpl implements Serveur {

    @Override
    public boolean connexion(String identifiant, String mdp) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	// recupere vrai si le couple ident mdp est présent en base
	boolean ret = base.connexion(identifiant, mdp);
	base.fermer();
	return ret;
    }

    @Override
    public String ajouterDonneesArchiveXML(String donneesFichier) throws RemoteException {
	StringBuilder builder = new StringBuilder();
	// Récupère les archives à partir du contenu du fichier
	List<ArchiveMeteo> archives = ParserXML.parserXML(donneesFichier);
	for (ArchiveMeteo archive : archives) {
	    builder.append(ajouterDonneeArchive(archive));
	}
	return builder.toString();
    }

    @Override
    public String ajouterDonneesArchiveJSON(String donneesFichier) throws RemoteException {
	StringBuilder builder = new StringBuilder();
	// Récupère les archives à partir du contenu du fichier
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
	// Ajout de l'archive
	base.ajouterDonneeArchive(archive);
	base.fermer();
	return null;
    }

    @Override
    public String ajouterPhoto(int archive, List<Photo> photos) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	// Parcourir les photos
	for (Photo photo : photos) {
	    // Ajout des photos une par une
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
	// On teste si le lieu à été modifié
	if (!ancienne.getLieu().equals(archive.getLieu())) {
	    // Mise à jour du lieu
	    base.miseAJourLieu(archive.getId(), archive.getLieu());
	}
	// On teste si la date à été modifiée
	if (!ancienne.getDate().equals(archive.getDate())) {
	    // Mise à jour de la date
	    base.miseAJourDate(archive.getId(), archive.getDate());
	}
	// On test si les données météo ont été modifiées
	if (!ancienne.getDonnee().equals(archive.getDonnee())) {
	    // Mise à jour de la donnée
	    base.miseAJour(archive.getId(), archive.getDonnee());
	}
	return null;
    }

    @Override
    public List<ArchiveMeteo> consulterParJour(Date date) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	// Liste des archives pour le jour renseigné
	List<ArchiveMeteo> archives = base.consulterParJour(date);
	base.fermer();
	return archives;
    }

    @Override
    public List<ArchiveMeteo> consulterParMois(Date date) throws RemoteException {
	Base base = new Base();
	base.ouvrir();
	// Liste des archives pour le mois renseigné
	List<ArchiveMeteo> archives = base.consulterParMois(date);
	base.fermer();
	return archives;
    }

    @Override
    public byte[] productionPDF(List<ArchiveMeteo> archives) throws RemoteException {
	PdfWriter pdf = new PdfWriter();
	pdf.initialiser(archives);
	return pdf.generer();
    }

    @Override
    public byte[] productionXml(List<ArchiveMeteo> archives) throws RemoteException {
	return XmlWriter.generer(archives);
    }

    @Override
    public Validation valider(String pays, String ville, String departement, String direction, String vitesse,
	    String temperature, String pluie, String date, String soleil) throws RemoteException {
	Validation v = new Validation();
	// On teste les expressions régulières de chaque champs
	v.regexp(bean.Lieu.class, "ville", ville);
	v.regexp(bean.Lieu.class, "departement", departement);
	v.regexp(bean.Lieu.class, "pays", pays);
	v.regexp(bean.DonneeMeteo.class, "directionVent", direction);
	v.regexp(bean.DonneeMeteo.class, "vitesseVent", vitesse);
	v.regexp(bean.DonneeMeteo.class, "temperature", temperature);
	v.regexp(bean.DonneeMeteo.class, "pluie", pluie);

	// Traitement particulier pour le soleil et la date

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

	// v contient les données et les erreurs
	return v;
    }

    @Override
    public ArchiveMeteo consulterParId(int id) throws RemoteException {
	ArchiveMeteo arc;
	Base base = new Base();
	base.ouvrir();
	// Archive correspondant à l'id renseigné
	arc = base.consulterParId(id);
	base.fermer();
	return arc;
    }

    @Override
    public Map<String, Double[]> getInformationsVille(Date date) throws RemoteException {
	// Ensemble des informations
	// La clé est le nom de la ville
	Map<String, Double[]> informations = new HashMap<>();
	Base base = new Base();
	base.ouvrir();

	// On récupère tous les liens
	List<Lieu> lieux = base.getLieux();

	// Pour chaque lieu on cherche les données
	// Puis on initialise le map
	for (Lieu lieu : lieux) {
	    List<DonneeMeteo> donnees = base.getDonnees(date, lieu.getId());
	    Double[] infos = new Double[3];
	    double temperature = 0, pluie = 0, vitesse = 0;
	    // Si plusieurs données sont associées au jour, pour un lieu, on
	    // fait une moyenne
	    if (donnees.size() != 0) {
		for (DonneeMeteo donnee : donnees) {
		    temperature += (double) donnee.getTemperature();
		    pluie += donnee.getPluie();
		    vitesse += donnee.getVitesseVent();
		}
		infos[0] = temperature / donnees.size();
		infos[1] = pluie / donnees.size();
		infos[2] = vitesse / donnees.size();
		informations.put(lieu.getVille(), infos);
	    }
	}
	return informations;
    }

    public boolean supprimerImage(int idImage) throws RemoteException {
	boolean ret;
	Base base = new Base();
	base.ouvrir();
	// Suppression de l'image dans la base
	ret = base.supprimerImage(idImage);
	base.fermer();
	return ret;
    }

    public boolean supprimerArchive(int idArchive) throws RemoteException {
	boolean ret;
	Base base = new Base();
	base.ouvrir();
	// Suppression de l'image dans la base
	ret = base.supprimerArchive(idArchive);
	base.fermer();
	return ret;
    }

    /**
     * Permet de lancer le serveur RMI.
     * 
     * @param args
     */
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
}
