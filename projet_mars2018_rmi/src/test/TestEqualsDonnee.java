package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bean.DonneeMeteo;
import bean.Soleil;

public class TestEqualsDonnee {

    @Test
    public void testMemeInstance() {
	DonneeMeteo donnee1 = new DonneeMeteo();
	assertEquals("Equals ne detecte pas une égalité de reference", donnee1.equals(donnee1), true);
    }

    @Test
    public void testNull() {
	DonneeMeteo donnee1 = new DonneeMeteo();
	assertEquals("Equals ne detecte pas une reference null", donnee1.equals(null), false);
    }

    @Test
    public void testMauvaisType() {
	DonneeMeteo donnee1 = new DonneeMeteo();
	String str = "toto";
	assertEquals("Equals ne detecte pas la difference de type", donnee1.equals(str), false);
    }

    @Test
    public void testDeuxInstancesEgales() {
	DonneeMeteo donnee1 = new DonneeMeteo();
	donnee1.setDirectionVent(1);
	donnee1.setPluie(2);
	donnee1.setSoleil(Soleil.AVERSE_ORAGEUSE);
	donnee1.setTemperature(3);
	donnee1.setTemperature(4);
	donnee1.setVitesseVent(5);
	DonneeMeteo donnee2 = new DonneeMeteo();
	donnee2.setDirectionVent(1);
	donnee2.setPluie(2);
	donnee2.setSoleil(Soleil.AVERSE_ORAGEUSE);
	donnee2.setTemperature(3);
	donnee2.setTemperature(4);
	donnee2.setVitesseVent(5);
	assertEquals("Equals ne detecte pas deux instances avec les memes champs", donnee1.equals(donnee2), true);
    }

    @Test
    public void testDeuxInstancesDifferentes() {
	DonneeMeteo donnee1 = new DonneeMeteo();
	donnee1.setDirectionVent(1);
	donnee1.setPluie(2);
	donnee1.setSoleil(Soleil.AVERSE_ORAGEUSE);
	donnee1.setTemperature(3);
	donnee1.setTemperature(4);
	donnee1.setVitesseVent(5);
	DonneeMeteo donnee2 = new DonneeMeteo();
	donnee2.setDirectionVent(1);
	donnee2.setPluie(2);
	donnee2.setSoleil(Soleil.ECLAIRCIES);
	donnee2.setTemperature(3);
	donnee2.setTemperature(4);
	donnee2.setVitesseVent(5);
	assertEquals("Equals ne detecte pas deux instances avec des champs differents", donnee1.equals(donnee2), false);
    }
}
