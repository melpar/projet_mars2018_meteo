package test;

import org.junit.Test;

import base.Base;
import bean.ArchiveMeteo;

public class TestSelectionId {
    @Test
    public void testSelectionId() {

	Base base = new Base();
	base.ouvrir();
	ArchiveMeteo arc = base.consulterParId(1);
	base.fermer();
	System.out.println(arc.getDate());
	System.out.println(arc.getLieu().getDepartement());
	System.out.println(arc.getLieu().getPays());
	System.out.println(arc.getLieu().getVille());
	System.out.println(arc.getDonnee().getDirectionVent());
	System.out.println(arc.getDonnee().getVitesseVent());
	System.out.println(arc.getDonnee().getTemperature());

    }
}
