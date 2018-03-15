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
    public static List<ArchiveMeteo> parserJSON(String jsonIn) {

	final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
	List<ArchiveMeteo> archives = new ArrayList<ArchiveMeteo>();
	try

	{

	    new StringWriter();

	    final JsonReader reader = new JsonReader(new StringReader(jsonIn));

	    reader.beginArray();

	    while (reader.hasNext()) {

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
