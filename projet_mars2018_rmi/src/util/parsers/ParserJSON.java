package util.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import bean.ArchiveMeteo;

public class ParserJSON {
    /**
     * Permet d'obtenir une liste d'archives en fonction du contenu d'un fichier
     * json.
     * 
     * @param jsonIn
     *            contenu du fichier
     * @return liste des archives extraites
     */
    public static List<ArchiveMeteo> parserJSON(String jsonIn) {
	// On initialise le parser avec le bon formatage de dates
	final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
	List<ArchiveMeteo> archives = new ArrayList<ArchiveMeteo>();
	try

	{

	    new StringWriter();

	    final JsonReader reader = new JsonReader(new StringReader(jsonIn));

	    reader.beginArray();
	    // Pour chaque objet détecté
	    while (reader.hasNext()) {
		// Création de l'objet archive
		final ArchiveMeteo archive = gson.fromJson(reader, ArchiveMeteo.class);

		archives.add(archive);

	    }

	    reader.endArray();
	    reader.close();
	    reader.close();

	} catch (final IOException e) {
	    e.printStackTrace();

	}
	return archives;

    }

    public static void main(String[] args) {
	final String jsonIn = "[{\"lieu\":{\" pays\":\"france\",\" ville\":\"brest\",\"departement\":\"finistere\"},\"date\":\"14/03/2018\",\"donnee\":{\"soleil\":\"NUAGEUX\",\"direction\":10,\"vitesse\": 50,\"temperature\":20,\"pluie\":5}}]";

    }
}
