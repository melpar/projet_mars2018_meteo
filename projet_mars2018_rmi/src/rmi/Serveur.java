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

    /**
     * Tester la connexion.
     * 
     * @param identifiant
     *            identifiant à tester
     * @param mdp
     *            mot de passe à tester
     * @return vrai si le couple est en base, faux sinon
     * @throws RemoteException
     */
    public boolean connexion(String identifiant, String mdp) throws RemoteException;

    /**
     * Permet de valider des données.
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

    /**
     * Permet d'ajouter des archives météo à partir d'un fihchier xml.
     * 
     * @param donneesFichier
     *            contenu du fichier
     * @return null
     * @throws RemoteException
     */
    public String ajouterDonneesArchiveXML(String donneesFichier) throws RemoteException;

    /**
     * Permet d'ajouter des archives météo à partir d'un fichier json.
     * 
     * @param donneesFichier
     *            contenu du fichier
     * @return null
     * @throws RemoteException
     */
    public String ajouterDonneesArchiveJSON(String donneesFichier) throws RemoteException;

    /**
     * Permet d'ajouter une archive.
     * 
     * @param archive
     * @return null
     * @throws RemoteException
     */
    public String ajouterDonneeArchive(ArchiveMeteo archive) throws RemoteException;

    /**
     * Permet d'ajouter des photos à une archive.
     * 
     * @param archive
     *            archive à laquelle la photo est associée
     * @param photos
     *            liste de photo à ajouter
     * @return null
     * @throws RemoteException
     */
    public String ajouterPhoto(int archive, List<Photo> photos) throws RemoteException;

    /**
     * Permet de modifier une donnée météo.
     * 
     * @param archive
     *            nouvelles données de l'archive, contient l'ancien id
     * @return null
     * @throws RemoteException
     */
    public String modifierDonnee(ArchiveMeteo archive) throws RemoteException;

    /**
     * Permet de consulter la liste d'archives pour un jour donné.
     * 
     * @param date
     *            jour à rechercher
     * @return liste d'archives
     * @throws RemoteException
     */
    public List<ArchiveMeteo> consulterParJour(Date date) throws RemoteException;

    /**
     * Permet de consulter la liste d'archives pour un mois donné.
     * 
     * @param date
     *            mois à rechercher
     * @return liste d'archives
     * @throws RemoteException
     */
    public List<ArchiveMeteo> consulterParMois(Date date) throws RemoteException;

    /**
     * Permet de consulter une archive pour un id donné.
     * 
     * @param id
     *            id à rechercher
     * @return archive associée
     * @throws RemoteException
     */
    public ArchiveMeteo consulterParId(int id) throws RemoteException;

    /**
     * Permet de produire un pdf.
     * 
     * @param archives
     *            archives à ajouter dans le pdf
     * @return tableau de bytes contenant le pdf
     * @throws RemoteException
     */
    public byte[] productionPDF(List<ArchiveMeteo> archives) throws RemoteException;

    /**
     * Permet de produire un fichier excel.
     * 
     * @param archives
     *            archives à ajouter dans le fichier excel
     * @return tableau de bytes contenant le fichier excel
     * @throws RemoteException
     */
    public byte[] productionXml(List<ArchiveMeteo> archives) throws RemoteException;

    /**
     * Permet de retourner des informations pour une date donnée.
     * 
     * @param date
     *            date à rechercher
     * @return map ayant pour clé la ville, et pour valeur un tableau de double.
     *         Premiere case, temperature puis pluie puis vent
     * @throws RemoteException
     */
    public Map<String, Double[]> getInformationsVille(Date date) throws RemoteException;

    /**
     * Permet de supprimer une image.
     * 
     * @param idImage
     *            id de l'image à supprimer
     * @return
     * @throws RemoteException
     */
    public boolean supprimerImage(int idImage) throws RemoteException;

    public boolean supprimerArchive(int idArchive) throws RemoteException;
}
