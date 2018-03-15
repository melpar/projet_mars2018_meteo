package test;

import java.util.Date;

import org.junit.Test;

import base.Base;
import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Soleil;

public class TestAjoutArchive {
    @Test
    public void testAjoutArchive() {
	DonneeMeteo donnee = new DonneeMeteo();
	donnee.setDirectionVent(50);
	donnee.setPluie(10);
	donnee.setSoleil(Soleil.AVERSES);
	donnee.setTemperature(8);
	donnee.setVitesseVent(25);

	Lieu lieu = new Lieu();
	lieu.setDepartement("Ille-et-Vilaine");
	lieu.setPays("France");
	lieu.setVille("Rennes");

	ArchiveMeteo archive = new ArchiveMeteo();
	archive.setDate(new Date(117, 03, 12));
	archive.setDonnee(donnee);
	archive.setLieu(lieu);
	archive.setPhotos(null);
	Base base = new Base();
	base.ouvrir();
	base.ajouterDonneeArchive(archive);
	base.fermer();
    }
}
