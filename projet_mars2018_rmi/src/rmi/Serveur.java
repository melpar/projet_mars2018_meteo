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
     *            identifiant � tester
     * @param mdp
     *            mot de passe � tester
     * @return vrai si le couple est en base, faux sinon
     * @throws RemoteException
     */
    public boolean connexion(String identifiant, String mdp) throws RemoteException;

    /**
     * Permet de valider des donn�es.
     * 
     * @param pays
     *            pays � valider
     * @param ville
     *            ville � valider
     * @param departement
     *            departement � valider
     * @param direction
     *            direction � valider
     * @param vitesse
     *            vitesse � valider
     * @param temperature
     *            temperature � valider
     * @param pluie
     *            pluie � valider
     * @param date
     *            date � valider
     * @param soleil
     *            soleil � valider
     * @return objet de type Validation, contient les valeurs et messages
     *         d'erreur
     * @throws RemoteException
     */
    public Validation valider(String pays, String ville, String departement, String direction, String vitesse,
	    String temperature, String pluie, String date, String soleil) throws RemoteException;

    /**
     * Permet d'ajouter des archives m�t�o � partir d'un fihchier xml.
     * 
     * @param donneesFichier
     *            contenu du fichier
     * @return null
     * @throws RemoteException
     */
    public String ajouterDonneesArchiveXML(String donneesFichier) throws RemoteException;

    /**
     * Permet d'ajouter des archives m�t�o � partir d'un fichier json.
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
     * Permet d'ajouter des photos � une archive.
     * 
     * @param archive
     *            archive � laquelle la photo est associ�e
     * @param photos
     *            liste de photo � ajouter
     * @return null
     * @throws RemoteException
     */
    public String ajouterPhoto(int archive, List<Photo> photos) throws RemoteException;

    /**
     * Permet de modifier une donn�e m�t�o.
     * 
     * @param archive
     *            nouvelles donn�es de l'archive, contient l'ancien id
     * @return null
     * @throws RemoteException
     */
    public String modifierDonnee(ArchiveMeteo archive) throws RemoteException;

    /**
     * Permet de consulter la liste d'archives pour un jour donn�.
     * 
     * @param date
     *            jour � rechercher
     * @return liste d'archives
     * @throws RemoteException
     */
    public List<ArchiveMeteo> consulterParJour(Date date) throws RemoteException;

    /**
     * Permet de consulter la liste d'archives pour un mois donn�.
     * 
     * @param date
     *            mois � rechercher
     * @return liste d'archives
     * @throws RemoteException
     */
    public List<ArchiveMeteo> consulterParMois(Date date) throws RemoteException;

    /**
     * Permet de consulter une archive pour un id donn�.
     * 
     * @param id
     *            id � rechercher
     * @return archive associ�e
     * @throws RemoteException
     */
    public ArchiveMeteo consulterParId(int id) throws RemoteException;

    /**
     * Permet de produire un pdf.
     * 
     * @param archives
     *            archives � ajouter dans le pdf
     * @return tableau de bytes contenant le pdf
     * @throws RemoteException
     */
    public byte[] productionPDF(List<ArchiveMeteo> archives) throws RemoteException;

    /**
     * Permet de produire un fichier excel.
     * 
     * @param archives
     *            archives � ajouter dans le fichier excel
     * @return tableau de bytes contenant le fichier excel
     * @throws RemoteException
     */
    public byte[] productionXml(List<ArchiveMeteo> archives) throws RemoteException;

    /**
     * Permet de retourner des informations pour une date donn�e.
     * 
     * @param date
     *            date � rechercher
     * @return map ayant pour cl� la ville, et pour valeur un tableau de double.
     *         Premiere case, temperature puis pluie puis vent
     * @throws RemoteException
     */
    public Map<String, Double[]> getInformationsVille(Date date) throws RemoteException;

    /**
     * Permet de supprimer une image.
     * 
     * @param idImage
     *            id de l'image � supprimer
     * @return
     * @throws RemoteException
     */
    public boolean supprimerImage(int idImage) throws RemoteException;

    public boolean supprimerArchive(int idArchive) throws RemoteException;
}
