package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.Test;

import base.Base;
import bean.Photo;

public class TestAjoutImage {

    @Test
    public void test() throws IOException, SerialException, SQLException {
	Base base = new Base();
	base.ouvrir();
	Photo photo = new Photo();
	// create file object
	File file = new File("test.jpg");
	// initialize a byte array of size of the file
	byte[] fileContent = new byte[(int) file.length()];
	FileInputStream inputStream = null;
	try {
	    // create an input stream pointing to the file
	    inputStream = new FileInputStream(file);
	    // read the contents of file into byte array
	    inputStream.read(fileContent);
	} catch (IOException e) {
	    throw new IOException("Unable to convert file to byte array. " + e.getMessage());
	} finally {
	    // close input stream
	    if (inputStream != null) {
		inputStream.close();
	    }
	}
	Blob image = new SerialBlob(fileContent);
	photo.setImage(image);
	photo.setNom("test");
	base.ajouterImage(1, photo);
	base.fermer();
    }

}
