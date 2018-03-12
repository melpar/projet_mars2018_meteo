package rmi.impl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Photo;
import rmi.Serveur;

public class ServeurImpl implements Serveur {

    @Override
    public String ajouterDonneesArchive(List<ArchiveMeteo> archives) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String ajouterDonneeArchive(ArchiveMeteo archive) throws RemoteException {
	// TODO Auto-generated method stub
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
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<ArchiveMeteo> consulterParMois(int annee, int mois) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean productionPDF(List<ArchiveMeteo> archives) throws RemoteException {
	// TODO Auto-generated method stub
	return false;
    }

}
