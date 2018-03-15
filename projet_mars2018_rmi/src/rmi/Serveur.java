package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import bean.ArchiveMeteo;
import bean.Photo;
import bean.Soleil;
import validation.Validation;

public interface Serveur extends Remote {

    public boolean connexion(String identifiant, String mdp) throws RemoteException;

    public Validation valider(String pays, String ville, String departement, String direction, String vitesse,
	    String temperature, String pluie, Date date, Soleil soleil) throws RemoteException;

    public String ajouterDonneesArchiveXML(String donneesFichier) throws RemoteException;

    public String ajouterDonneesArchiveJSON(String donneesFichier) throws RemoteException;

    public String ajouterDonneeArchive(ArchiveMeteo archive) throws RemoteException;

    public String ajouterPhoto(int archive, List<Photo> photos) throws RemoteException;

    public String modifierDonnee(ArchiveMeteo archive) throws RemoteException;

    public List<ArchiveMeteo> consulterParJour(Date date) throws RemoteException;

    public List<ArchiveMeteo> consulterParMois(Date date) throws RemoteException;

    public ArchiveMeteo consulterParId(int id) throws RemoteException;

    public byte[] productionPDF(List<ArchiveMeteo> archives) throws RemoteException;
}
