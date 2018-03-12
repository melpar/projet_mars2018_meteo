package base;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;

/**
 * 
 * @author M1 - informatique Louarn - Parlant - Maresceaux - Le Guyader Classe
 *         qui contient les fonctionalite pour la basse mariadb
 */
public class Base {
    private static String config = "resources/mariadb";
    private Connection co;

    static {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	ResourceBundle resource = ResourceBundle.getBundle(config);
	String url = resource.getString("url");
	System.out.println(url);
    }

    /**
     * Ouverture de la basse
     * 
     * @return true, si la connection a la base a reussi
     */
    public boolean ouvrir() {
	ResourceBundle resource = ResourceBundle.getBundle(config);
	String url = resource.getString("url");
	String user = resource.getString("user");
	String password = resource.getString("password");
	try {
	    co = (Connection) DriverManager.getConnection(url, user, password);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    /**
     * Fermeture de la base
     */
    public void fermer() {
	try {
	    co.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void ajouterDonneeArchive(ArchiveMeteo archive) {
	// ajout du lieu
	this.ajouterLieu(archive.getLieu());

	// ajout de la donnee
	this.ajouterDonnee(archive.getDonnee());

	// creation de l'archive
	if (archive.getPhotos() != null) {
	    // ajout des photos
	}
    }

    public int ajouterDonnee(DonneeMeteo donnee) {
	int res = 0;

	try {
	    String sql = "insert into T_DONNEE_DON (DON_pluie, DON_directionVent, DON_vitesseVent, DON_soleil, DON_temperature)"
		    + "values (?, ?, ?,?,?) ";
	    PreparedStatement ps = co.prepareStatement(sql);
	    ps.setDouble(1, donnee.getPluie());
	    ps.setDouble(2, donnee.getDirectionVent());
	    ps.setDouble(3, donnee.getVitesseVent());
	    ps.setInt(4, donnee.getSoleil().getId());
	    ps.setDouble(5, donnee.getTemperature());
	    res = ps.executeUpdate();
	    System.out.println("Exec sql : " + sql);
	} catch (Exception e) {
	    System.out.println("Erreur Base.ajouterDonnee " + e.getMessage());
	}
	return res;
    }

    public int ajouterLieu(Lieu lieu) {
	int res = 0;

	try {
	    String sql = "insert into T_LIEU_LIE (LIE_ville, LIE_pays, LIE_departement)" + "values (?, ?, ?) ";
	    PreparedStatement ps = co.prepareStatement(sql);
	    ps.setString(1, lieu.getVille());
	    ps.setString(2, lieu.getPays());
	    ps.setString(3, lieu.getDepartement());
	    res = ps.executeUpdate();
	    System.out.println("Exec sql : " + sql);
	} catch (Exception e) {
	    System.out.println("Erreur Base.ajouterLieu " + e.getMessage());
	}
	return res;
    }
}
