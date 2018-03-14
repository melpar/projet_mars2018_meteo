package util.pdf;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.faceless.pdf2.PDF;
import org.faceless.pdf2.PDFImage;
import org.faceless.pdf2.PDFPage;
import org.faceless.pdf2.PDFStyle;
import org.faceless.pdf2.StandardFont;

import bean.ArchiveMeteo;
import rmi.Serveur;
import rmi.impl.ServeurImpl;

public class FirstPdf {

    PDF p;

    public FirstPdf() {
	p = new PDF();
	p.setInfo("Author", "TeamDelArte");
	p.setInfo("Title", "Rapport météo");
    }

    public static void main(String[] args) throws RemoteException {
	Serveur serveur = new ServeurImpl();
	Date date = new Date(118, 2, 12);
	List<ArchiveMeteo> list = serveur.consulterParMois(date);
	ArchiveMeteo archive = list.get(0);
	FirstPdf pdf = new FirstPdf();
	pdf.nouvellePage(archive);

	OutputStream fo = pdf.download();
	pdf.creer(fo);
	try {
	    fo.close();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void nouvellePage(ArchiveMeteo archive) {
	PDFPage page = p.newPage("A4");
	PDFStyle mystyle = new PDFStyle();
	mystyle.setFont(new StandardFont(StandardFont.HELVETICA), 24);
	mystyle.setFillColor(Color.black);

	page.setStyle(mystyle);
	page.drawText(archive.getLieu().getPays() + ", " + archive.getLieu().getDepartement() + ", "
		+ archive.getLieu().getVille(), 50, page.getHeight() - 100);
	mystyle.setFont(new StandardFont(StandardFont.HELVETICA), 12);
	page.drawText((archive.getDate().getYear() + 1900) + "/" + (archive.getDate().getMonth() + 1) + "/"
		+ archive.getDate().getDay(), 50, page.getHeight() - 150);
	page.drawText("direction du vent :" + archive.getDonnee().getDirectionVent(), 50, page.getHeight() - 200);
	page.drawText("humidité :" + archive.getDonnee().getPluie(), 50, page.getHeight() - 220);
	page.drawText("température :" + archive.getDonnee().getTemperature(), 50, page.getHeight() - 240);
	page.drawText("vitesse du vent :" + archive.getDonnee().getVitesseVent(), 50, page.getHeight() - 260);
	page.drawText("ciel :" + archive.getDonnee().getSoleil().getName(), 50, page.getHeight() - 280);
	PDFImage image;
	try {
	    byte[] donne = archive.getPhotos().get(0).getImage();
	    InputStream inp = new BufferedInputStream(new FileInputStream("nuageux.jpg"));
	    InputStream targetStream = new BufferedInputStream(
		    new ByteArrayInputStream(Base64.getEncoder().encode(donne)));
	    /*
	     * byte[] donne = inp.ge System.out.println("taille : " + donne.length);
	     * 
	     * byte[] content = Base64.getEncoder().encode(donne); int size =
	     * content.length; InputStream is = null; byte[] b = new byte[size]; is = new
	     * ByteArrayInputStream(content);
	     * 
	     * // image = new PDFImage(Base64.getEncoder().encode(donne));
	     */
	    image = new PDFImage(inp);
	    page.drawImage(image, 100, 100, 255, 255);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public OutputStream download() {
	OutputStream fo;
	try {
	    fo = new FileOutputStream("Rapport.pdf");
	    return fo;
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }

    public void creer(OutputStream fo) {
	try {
	    p.render(fo);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
