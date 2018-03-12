package test;

import org.junit.Test;

import base.Base;

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

    // @Test
    // public void testAjoutDonnee() {
    // DonneeMeteo donnee = new DonneeMeteo();
    // donnee.setDirectionVent(10);
    // donnee.setPluie(20);
    // donnee.setSoleil(Soleil.AVERSES);
    // donnee.setTemperature(5);
    // donnee.setVitesseVent(25);
    //
    // Base base = new Base();
    // base.ouvrir();
    // base.ajouterDonnee(donnee);
    // base.fermer();
    // }

    // @Test
    // public void testAjoutArchive() {
    // DonneeMeteo donnee = new DonneeMeteo();
    // donnee.setDirectionVent(50);
    // donnee.setPluie(10);
    // donnee.setSoleil(Soleil.AVERSES);
    // donnee.setTemperature(8);
    // donnee.setVitesseVent(25);
    //
    // Lieu lieu = new Lieu();
    // lieu.setDepartement("Ille-et-Vilaine");
    // lieu.setPays("France");
    // lieu.setVille("Rennes");
    //
    // ArchiveMeteo archive = new ArchiveMeteo();
    // archive.setDate(new Date(117, 03, 12));
    // archive.setDonnee(donnee);
    // archive.setLieu(lieu);
    // archive.setPhotos(null);
    // Base base = new Base();
    // base.ouvrir();
    // base.ajouterDonneeArchive(archive);
    // base.fermer();
    // }

    // @Test
    // public void testSelectionJour() {
    //
    // Base base = new Base();
    // base.ouvrir();
    // List<ArchiveMeteo> archives = base.consulterParJour(new Date());
    // base.fermer();
    // for (ArchiveMeteo arc : archives) {
    // System.out.println(arc.getDate());
    // System.out.println(arc.getLieu().getDepartement());
    // System.out.println(arc.getLieu().getPays());
    // System.out.println(arc.getLieu().getVille());
    // System.out.println(arc.getDonnee().getDirectionVent());
    // System.out.println(arc.getDonnee().getVitesseVent());
    // System.out.println(arc.getDonnee().getTemperature());
    // }
    // }

    // @Test
    // public void testSelectionMois() {
    //
    // Base base = new Base();
    // base.ouvrir();
    // List<ArchiveMeteo> archives = base.consulterParMois(new Date());
    // base.fermer();
    // for (ArchiveMeteo arc : archives) {
    // System.out.println(arc.getDate());
    // System.out.println(arc.getLieu().getDepartement());
    // System.out.println(arc.getLieu().getPays());
    // System.out.println(arc.getLieu().getVille());
    // System.out.println(arc.getDonnee().getDirectionVent());
    // System.out.println(arc.getDonnee().getVitesseVent());
    // System.out.println(arc.getDonnee().getTemperature());
    // }
    // }

    @Test
    public void testConnexion() {

	Base base = new Base();
	base.ouvrir();
	assert (base.connexion("admin", "mdp"));
	base.fermer();
    }
}
