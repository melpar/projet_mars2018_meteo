package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bean.Lieu;

public class TestEqualsLieu {

    @Test
    public void testMemeInstance() {
	Lieu lieu1 = new Lieu();
	assertEquals("Equals ne detecte pas une égalité de reference", lieu1.equals(lieu1), true);
    }

    @Test
    public void testNull() {
	Lieu lieu1 = new Lieu();
	assertEquals("Equals ne detecte pas une reference null", lieu1.equals(null), false);
    }

    @Test
    public void testMauvaisType() {
	Lieu lieu1 = new Lieu();
	String str = "toto";
	assertEquals("Equals ne detecte pas la difference de type", lieu1.equals(str), false);
    }

    @Test
    public void testDeuxInstancesEgales() {
	Lieu lieu1 = new Lieu();
	lieu1.setDepartement("departement");
	lieu1.setPays("pays");
	lieu1.setVille("ville");
	Lieu lieu2 = new Lieu();
	lieu2.setDepartement("departement");
	lieu2.setPays("pays");
	lieu2.setVille("ville");
	assertEquals("Equals ne detecte pas deux instances avec les memes champs", lieu1.equals(lieu2), true);
    }

    @Test
    public void testDeuxInstancesDifferentes() {
	Lieu lieu1 = new Lieu();
	lieu1.setDepartement("departement");
	lieu1.setPays("pays");
	lieu1.setVille("ville");
	Lieu lieu2 = new Lieu();
	lieu2.setDepartement("departement2");
	lieu2.setPays("pays");
	lieu2.setVille("ville");
	assertEquals("Equals ne detecte pas deux instances avec des champs differents", lieu1.equals(lieu2), false);
    }
}
