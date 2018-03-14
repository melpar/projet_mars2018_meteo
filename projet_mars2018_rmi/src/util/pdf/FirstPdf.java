package util.pdf;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.print.PageFormat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFGraphics;
import com.qoppa.pdfWriter.PDFPage;

import bean.ArchiveMeteo;
import bean.Photo;
import rmi.Serveur;
import rmi.impl.ServeurImpl;

public class FirstPdf {

    PDFDocument p;

    public FirstPdf() {
	p = new PDFDocument();
    }

    public static void main(String[] args) throws RemoteException {

	Serveur serveur = new ServeurImpl();
	Date date = new Date(118, 2, 12);
	List<ArchiveMeteo> list = serveur.consulterParMois(date);
	ArchiveMeteo archive = list.get(0);
	FirstPdf pdf = new FirstPdf();
	try {
	    pdf.nouvellePage(archive);
	    pdf.p.saveDocument("Rapport.pdf");
	    pdf.generer();
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    public void initialiser(List<ArchiveMeteo> list) {
	for (ArchiveMeteo archive : list) {
	    nouvellePage(archive);
	}
    }

    public void nouvellePage(ArchiveMeteo archive) {
	PDFPage newPage = p.createPage(new PageFormat());
	int marge = 100;
	// Draw to the page
	Graphics2D g2d = newPage.createGraphics();
	g2d.setFont(PDFGraphics.HELVETICA.deriveFont(24f));
	//
	g2d.drawString(archive.getLieu().getPays() + ", " + archive.getLieu().getDepartement() + ", "
		+ archive.getLieu().getVille(), marge, 100);
	g2d.setFont(PDFGraphics.HELVETICA.deriveFont(12f));
	g2d.drawString((archive.getDate().getYear() + 1900) + "/" + (archive.getDate().getMonth() + 1) + "/"
		+ archive.getDate().getDay(), marge, 150);
	g2d.drawString("direction du vent :" + archive.getDonnee().getDirectionVent(), marge, 200);
	g2d.drawString("humidité :" + archive.getDonnee().getPluie(), marge, 220);
	g2d.drawString("température :" + archive.getDonnee().getTemperature(), marge, 240);
	g2d.drawString("vitesse du vent :" + archive.getDonnee().getVitesseVent(), marge, 260);
	g2d.drawString("ciel :" + archive.getDonnee().getSoleil().getName(), marge, 280);
	//
	p.addPage(newPage);
	pagePhoto(archive.getPhotos());
    }

    private void pagePhoto(List<Photo> list) {

	while (list.size() > 0) {
	    PDFPage page = p.createPage(new PageFormat());
	    Graphics2D g2d = page.createGraphics();
	    for (int i = 0; i < 3 && list.size() > 0; i++) {

		byte[] photo = list.get(0).getImage();
		byte[] photoDecode = Base64.getDecoder().decode(photo);
		InputStream in = new ByteArrayInputStream(photoDecode);
		try {
		    float[] scales = { 1f, 1f, 1f, 1f };
		    float[] offsets = new float[4];
		    RescaleOp rop = new RescaleOp(scales, offsets, null);
		    g2d.drawImage(ajouterImage(in), rop, 100, 100 + (200 * i));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		list.remove(0);
	    }
	    p.addPage(page);
	}
    }

    private BufferedImage ajouterImage(InputStream in) throws IOException {
	BufferedImage img = ImageIO.read(in);
	int w = img.getWidth(null);
	int h = img.getHeight(null);
	BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	Graphics g = bi.getGraphics();
	g.drawImage(img, 0, 0, null);
	return bi;
    }

    public byte[] generer() {
	ByteArrayOutputStream os = new ByteArrayOutputStream();
	try {
	    p.saveDocument(os);
	    byte[] fichier = os.toByteArray();
	    return fichier;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
