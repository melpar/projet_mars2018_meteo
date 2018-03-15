package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import bean.ArchiveMeteo;
import bean.Photo;
import validation.Validation;

public interface Serveur extends Remote {

    public boolean connexion(String identifiant, String mdp) throws RemoteException;

    /**
     * Permet de valider des données
     * 
     * @param pays
     *            pays à valider
     * @param ville
     *            ville à valider
     * @param departement
     *            departement à valider
     * @param direction
     *            direction à valider
     * @param vitesse
     *            vitesse à valider
     * @param temperature
     *            temperature à valider
     * @param pluie
     *            pluie à valider
     * @param date
     *            date à valider
     * @param soleil
     *            soleil à valider
     * @return objet de type Validation, contient les valeurs et messages
     *         d'erreur
     * @throws RemoteException
     */
    public Validation valider(String pays, String ville, String departement, String direction, String vitesse,
	    String temperature, String pluie, String date, String soleil) throws RemoteException;

    public String ajouterDonneesArchiveXML(String donneesFichier) throws RemoteException;

    public String ajouterDonneesArchiveJSON(String donneesFichier) throws RemoteException;

    public String ajouterDonneeArchive(ArchiveMeteo archive) throws RemoteException;

    public String ajouterPhoto(int archive, List<Photo> photos) throws RemoteException;

    public String modifierDonnee(ArchiveMeteo archive) throws RemoteException;

    public List<ArchiveMeteo> consulterParJour(Date date) throws RemoteException;

    public List<ArchiveMeteo> consulterParMois(Date date) throws RemoteException;

    public ArchiveMeteo consulterParId(int id) throws RemoteException;

    public byte[] productionPDF(List<ArchiveMeteo> archives) throws RemoteException;

    public Map<String, Double[]> getInformations(Date date) throws RemoteException;
}
