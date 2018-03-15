package test;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import base.Base;
import bean.ArchiveMeteo;

public class TestSelectionJour {
    @Test
    public void testSelectionJour() {

	Base base = new Base();
	base.ouvrir();
	List<ArchiveMeteo> archives = base.consulterParJour(new Date());
	base.fermer();
	for (ArchiveMeteo arc : archives) {
	    System.out.println(arc.getDate());
	    System.out.println(arc.getLieu().getDepartement());
	    System.out.println(arc.getLieu().getPays());
	    System.out.println(arc.getLieu().getVille());
	    System.out.println(arc.getDonnee().getDirectionVent());
	    System.out.println(arc.getDonnee().getVitesseVent());
	    System.out.println(arc.getDonnee().getTemperature());
	}
    }
}
