package util.parsers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Soleil;

public class ParserXML {
    /**
     * Permet de créer une liste à partir du contenu d'un fichier xml
     * 
     * @param donnees
     *            contenu du fichier xml
     * @return liste créée
     */
    public static List<ArchiveMeteo> parserXML(String donnees) {
	try {
	    Document doc = Jsoup.parse(donnees, "", Parser.xmlParser());
	    List<ArchiveMeteo> listeArchives = new ArrayList<ArchiveMeteo>();
	    Elements lesArchives = doc.select("archive");
	    for (Element archive : lesArchives) {
		ArchiveMeteo uneArchive = new ArchiveMeteo();
		// Initialisation du lieu
		Element lieuxml = archive.getElementsByTag("lieu").first();
		Lieu lieu = new Lieu();
		lieu.setDepartement(lieuxml.getElementsByTag("departement").text());
		lieu.setPays(lieuxml.getElementsByTag("pays").text());
		lieu.setVille(lieuxml.getElementsByTag("ville").text());
		uneArchive.setLieu(lieu);

		// Date
		Element datexml = archive.getElementsByTag("date").first();
		String[] elementsDateString = datexml.text().split("/");
		int[] elementsDate = new int[3];
		for (int i = 0; i < 3; i++) {
		    elementsDate[i] = Integer.parseInt(elementsDateString[i]);
		}
		uneArchive.setDate(new Date(elementsDate[2] - 1900, elementsDate[1] - 1, elementsDate[0]));

		// Donnees meteo
		Element donneexml = archive.getElementsByTag("donnees").first();
		DonneeMeteo donnee = new DonneeMeteo();
		double dir = Double.parseDouble(donneexml.getElementsByTag("direction").text());
		donnee.setDirectionVent(dir);
		double pluie = Double.parseDouble(donneexml.getElementsByTag("pluie").text());
		donnee.setPluie(pluie);
		Soleil sol = Soleil.getByNom(donneexml.getElementsByTag("ciel").text());
		donnee.setSoleil(sol);
		int temperature = Integer.parseInt(donneexml.getElementsByTag("temperature").text());
		donnee.setTemperature(temperature);
		double vitesse = Double.parseDouble(donneexml.getElementsByTag("vitesse").text());
		donnee.setVitesseVent(vitesse);
		uneArchive.setDonnee(donnee);
		listeArchives.add(uneArchive);
	    }
	    return listeArchives;
	} catch (Exception e) {
	    return new ArrayList<ArchiveMeteo>();
	}
    }

    public static void main(String[] args) {
	StringBuilder contentBuilder = new StringBuilder();
	try (Stream<String> stream = Files.lines(Paths.get("archives.xml"), StandardCharsets.UTF_8)) {
	    stream.forEach(s -> contentBuilder.append(s).append("\n"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	List<ArchiveMeteo> archives = ParserXML.parserXML(contentBuilder.toString());
    }
}
