package test;

import org.junit.Test;

import base.Base;
import bean.Lieu;

public class TestAjoutLieu {
    @Test
    public void testAjoutLieu() {
	Lieu lieu = new Lieu();
	lieu.setDepartement("Finistere");
	lieu.setPays("France");
	lieu.setVille("Brest");
	Base base = new Base();
	base.ouvrir();
	base.ajouterLieu(lieu);
	base.fermer();
	// assertTrue("Ok");
    }
}
