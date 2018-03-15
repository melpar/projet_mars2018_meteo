package test;

import org.junit.Test;

import base.Base;

public class TestConnexion {

    @Test
    public void testConnexion() {

	Base base = new Base();
	base.ouvrir();
	assert (base.connexion("admin", "mdp"));
	base.fermer();
    }
}
