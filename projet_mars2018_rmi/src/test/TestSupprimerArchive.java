package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import base.Base;

public class TestSupprimerArchive {
    @Test
    public void test() {
	Base base = new Base();
	base.ouvrir();
	// Suppression de l'image dans la base
	boolean test = base.supprimerArchive(28);
	base.fermer();
	assertTrue("erreur", test);
    }
}
