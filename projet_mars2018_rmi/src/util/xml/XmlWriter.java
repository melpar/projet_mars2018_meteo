package util.xml;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;

public class XmlWriter {
    public static byte[] generer(List<ArchiveMeteo> list) {
	StringBuilder sbuilder = new StringBuilder();
	sbuilder.append("<archives>");
	for (ArchiveMeteo arc : list) {
	    sbuilder.append("<archive>");
	    initialiserLieu(sbuilder, arc.getLieu());
	    initialiserDate(sbuilder, arc.getDate());
	    initialiserDonnee(sbuilder, arc.getDonnee());
	    sbuilder.append("</archive>");
	}
	sbuilder.append("</archives>");
	return sbuilder.toString().getBytes();
    }

    private static void initialiserDonnee(StringBuilder sbuilder, DonneeMeteo donnee) {
	sbuilder.append("<donnees>");
	sbuilder.append("<ciel>").append(donnee.getSoleil().getName()).append("</ciel>");
	sbuilder.append("<direction>").append(donnee.getDirectionVent()).append("</direction>");
	sbuilder.append("<vitesse>").append(donnee.getVitesseVent()).append("</vitesse>");
	sbuilder.append("<temperature>").append(donnee.getTemperature()).append("</temperature>");
	sbuilder.append("<pluie>").append(donnee.getPluie()).append("</pluie>");
	sbuilder.append("</donnees>");
    }

    private static void initialiserDate(StringBuilder sbuilder, Date date) {
	sbuilder.append("<date>");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String dateS = sdf.format(date);
	sbuilder.append(dateS);
	sbuilder.append("</date>");
    }

    private static void initialiserLieu(StringBuilder sbuilder, Lieu lieu) {
	sbuilder.append("<lieu>");
	sbuilder.append("<pays>");
	sbuilder.append(lieu.getPays());
	sbuilder.append("</pays>");
	sbuilder.append("<departement>");
	sbuilder.append(lieu.getDepartement());
	sbuilder.append("</departement>");
	sbuilder.append("<ville>");
	sbuilder.append(lieu.getVille());
	sbuilder.append("</ville>");
	sbuilder.append("</lieu>");
    }
}
