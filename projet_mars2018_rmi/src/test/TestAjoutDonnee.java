package test;

import org.junit.Test;

import base.Base;
import bean.DonneeMeteo;
import bean.Soleil;

public class TestAjoutDonnee {
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
