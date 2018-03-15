package util.pdf;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

public class PdfWriter {

    // Variables statiques de la page PDF
    private static final int HAUTEUR_MARGE = 100;
    private static final int HAUTEUR_IMAGE = 100;
    private static final int MARGE = 100;

    // document pdf
    PDFDocument p;

    // creation du document pdf
    public PdfWriter() {
	p = new PDFDocument();
    }

    /**
     * phase de test
     * 
     * @param args
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException {

	Serveur serveur = new ServeurImpl();
	Date date = new Date(118, 2, 12);
	List<ArchiveMeteo> list = serveur.consulterParMois(date);
	PdfWriter pdf = new PdfWriter();
	try {
	    pdf.initialiser(list);
	    pdf.p.saveDocument("Rapport.pdf");
	    pdf.generer();
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    /**
     * ecriture des donnees dans le document
     * 
     * @param list
     */
    public void initialiser(List<ArchiveMeteo> list) {
	for (ArchiveMeteo archive : list) {
	    nouvellePage(archive);
	}
    }

    /**
     * ecriture d'une donnee meteo
     * 
     * @param archive
     */
    public void nouvellePage(ArchiveMeteo archive) {
	PDFPage newPage = p.createPage(new PageFormat());
	Graphics2D g2d = newPage.createGraphics();
	g2d.setFont(PDFGraphics.HELVETICA.deriveFont(24f));

	// ecriture des donnees archive
	g2d.drawString(archive.getLieu().getPays() + ", " + archive.getLieu().getDepartement() + ", "
		+ archive.getLieu().getVille(), MARGE, 100);
	g2d.setFont(PDFGraphics.HELVETICA.deriveFont(12f));
	g2d.drawString((archive.getDate().getYear() + 1900) + "/" + (archive.getDate().getMonth() + 1) + "/"
		+ archive.getDate().getDay(), MARGE, 120);
	g2d.drawString("direction du vent :" + archive.getDonnee().getDirectionVent(), MARGE, 140);
	g2d.drawString("humidité :" + archive.getDonnee().getPluie(), MARGE, 160);
	g2d.drawString("température :" + archive.getDonnee().getTemperature(), MARGE, 180);
	g2d.drawString("vitesse du vent :" + archive.getDonnee().getVitesseVent(), MARGE, 200);
	g2d.drawString("ciel :" + archive.getDonnee().getSoleil().getName(), MARGE, 220);
	if (archive.getPhotos().size() > 0) {
	    // definition de la zone utilise
	    int niveau = 240;
	    // ajout des photos
	    pagePhoto(archive.getPhotos(), newPage, niveau);
	} else {

	    p.addPage(newPage);
	}

    }

    /**
     * ajout d'une liste de photo
     * 
     * @param list
     *            de photo
     * @param page
     *            en cours
     * @param niveau
     *            d'ecriture de la page en cours
     */
    private void pagePhoto(List<Photo> list, PDFPage pageInformation, int niveau) {
	PDFPage page = pageInformation;
	// tant qu'il reste des photo a afficher
	while (list.size() > 0) {
	    Graphics2D g2d = page.createGraphics();
	    // tant qu'on a pas atteind le fond de la page
	    for (int i = (niveau) / (HAUTEUR_IMAGE + 15); HAUTEUR_MARGE + ((HAUTEUR_IMAGE + 50) * i) + HAUTEUR_IMAGE
		    + 15 < PDFDocument.getDefaultPageFormat().getHeight() - HAUTEUR_MARGE && list.size() > 0; i++) {
		// recuperation de la photo au bon format
		byte[] photo = list.get(0).getImage();
		byte[] photoDecode = Base64.getDecoder().decode(photo);
		InputStream in = new ByteArrayInputStream(photoDecode);
		try {
		    float[] scales = { 1f, 1f, 1f, 1f };
		    float[] offsets = new float[4];
		    RescaleOp rop = new RescaleOp(scales, offsets, null);
		    // desinner l'image et sa legend au bon emplacement
		    g2d.drawImage(ajouterImage(in), rop, 100, HAUTEUR_MARGE + ((HAUTEUR_IMAGE + 50) * i));
		    g2d.drawString("Nom :" + list.get(0).getNom(), MARGE,
			    HAUTEUR_MARGE + ((HAUTEUR_IMAGE + 50) * i) + HAUTEUR_IMAGE + 15);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		// passer a la prochaine photo
		list.remove(0);
	    }
	    // passer a la prochaine page
	    p.addPage(page);
	    niveau = 0;
	    page = p.createPage(new PageFormat());
	}
    }

    /**
     * transforme un InputStream en BufferedImage
     * 
     * @param in
     * @return BufferedImage
     * @throws IOException
     */
    private BufferedImage ajouterImage(InputStream in) throws IOException {
	BufferedImage img = ImageIO.read(in);
	int w = img.getWidth();
	int h = img.getHeight();
	BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	Graphics g = bi.getGraphics();
	g.drawImage(img, 0, 0, null);
	bi = scale(bi, h);
	return bi;
    }

    /**
     * retourne le pdf en tableau de bit
     * 
     * @return pdf format byte[]
     */
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

    /**
     * Effectue une homothétie de l'image.
     * 
     * @param bi
     *            l'image.
     * @return une image réduite ou agrandie.
     * 
     */
    public static BufferedImage scale(BufferedImage bi, int height) {
	double scaleValue = (double) HAUTEUR_IMAGE / height;
	AffineTransform tx = new AffineTransform();
	tx.scale(scaleValue, scaleValue);
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	BufferedImage biNew = new BufferedImage((int) (bi.getWidth() * scaleValue), (int) (bi.getHeight() * scaleValue),
		bi.getType());
	return op.filter(bi, biNew);

    }
}
