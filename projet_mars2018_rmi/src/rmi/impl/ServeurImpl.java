package rmi.impl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import base.Base;
import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Photo;
import mock.ListeArchiveMOCK;
import rmi.Serveur;

public class ServeurImpl implements Serveur {

    @Override
    public boolean connexion(String identifiant, String mdp) throws RemoteException {
	if ("identifiant".equals(identifiant) && "mdp".equals(mdp)) {
	    return true;
	}
	return false;
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
	// TODO Auto-generated method stub
	return ListeArchiveMOCK.getByJour(date);
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
