package test;

import java.util.Date;

import org.junit.Test;

import base.Base;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Soleil;

public class TestModification {

    @Test
    public void testModificationDonnee() {
	Base b = new Base();
	b.ouvrir();
	DonneeMeteo donnee = new DonneeMeteo();
	donnee.setDirectionVent(0);
	donnee.setPluie(0);
	donnee.setSoleil(Soleil.AVERSE_ORAGEUSE);
	donnee.setTemperature(0);
	donnee.setVitesseVent(0);
	b.miseAJour(2, donnee);
	b.fermer();
    }

    @Test
    public void testModificationDate() {
	Base b = new Base();
	b.ouvrir();
	b.miseAJourDate(2, new Date(120, 1, 1));
	b.fermer();
    }

    @Test
    public void testModificationLieu() {
	Base b = new Base();
	b.ouvrir();
	Lieu lieu = new Lieu();
	lieu.setDepartement("departement2");
	lieu.setPays("pays2");
	lieu.setVille("ville2");
	b.miseAJourLieu(2, lieu);
	b.fermer();
    }
}
