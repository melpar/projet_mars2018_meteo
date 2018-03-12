package mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Soleil;

public class ListeArchiveMOCK {
    public static List<ArchiveMeteo> getByJour(Date date) {
	List<ArchiveMeteo> ret = new ArrayList<ArchiveMeteo>();
	ArchiveMeteo archive1 = new ArchiveMeteo();
	archive1.setDate(date);
	DonneeMeteo donnee = new DonneeMeteo();
	donnee.setDirectionVent(10.0);
	donnee.setPluie(20);
	donnee.setSoleil(Soleil.ENSOLEILLE);
	donnee.setTemperature(12);
	donnee.setVitesseVent(20);
	archive1.setDonnee(donnee);
	Lieu lieu = new Lieu();
	lieu.setDepartement("Finistere");
	lieu.setPays("France");
	lieu.setVille("Brest");
	archive1.setLieu(lieu);
	archive1.setPhotos(null);
	ret.add(archive1);
	return ret;
    }
}
