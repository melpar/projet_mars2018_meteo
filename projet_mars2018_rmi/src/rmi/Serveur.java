package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Photo;

public interface Serveur extends Remote {

    public boolean connexion(String identifiant, String mdp) throws RemoteException;

    public String ajouterDonneesArchive(List<ArchiveMeteo> archives) throws RemoteException;

    public String ajouterDonneeArchive(ArchiveMeteo archive) throws RemoteException;

    public String ajouterPhoto(Lieu lieu, Date date, List<Photo> photos) throws RemoteException;

    public String modifierDonnee(Lieu lieu, Date date, DonneeMeteo donnees) throws RemoteException;

    public List<ArchiveMeteo> consulterParJour(Date date) throws RemoteException;

    public List<ArchiveMeteo> consulterParMois(int annee, int mois) throws RemoteException;

    public boolean productionPDF(List<ArchiveMeteo> archives) throws RemoteException;
}
