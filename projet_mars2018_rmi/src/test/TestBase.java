package test;

import org.junit.Test;

import base.Base;
import bean.DonneeMeteo;
import bean.Soleil;

public class TestBase {

    // @Test
    // public void testAjoutLieu() {
    // Lieu lieu = new Lieu();
    // lieu.setDepartement("Finistere");
    // lieu.setPays("France");
    // lieu.setVille("Brest");
    // Base base = new Base();
    // base.ouvrir();
    // base.ajouterLieu(lieu);
    // base.fermer();
    // // assertTrue("Ok");
    // }

    @Test
    public void testAjoutDonnee() {
	DonneeMeteo donnee = new DonneeMeteo();
	donnee.setDirectionVent(10);
	donnee.setPluie(20);
	donnee.setSoleil(Soleil.AVERSES);
	donnee.setTemperature(5);
	donnee.setVitesseVent(25);

	Base base = new Base();
	base.ouvrir();
	base.ajouterDonnee(donnee);
	base.fermer();
    }

}
