package base;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Soleil;

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

    public int ajouterDonneeArchive(ArchiveMeteo archive) {
	// ajout du lieu
	int res = 0;
	int idLieu = this.ajouterLieu(archive.getLieu());
	if (idLieu == -1) {
	    // Erreur
	}
	// ajout de la donnee
	int idDonnee = this.ajouterDonnee(archive.getDonnee());
	if (idDonnee == -1) {
	    // Erreur
	}
	// creation de l'archive
	try {
	    String sql = "insert into T_ARCHIVE_ARC (ARC_lieu, ARC_date, ARC_donnee)" + "values (?, ?, ?) ";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, idLieu);
	    java.sql.Date dateSql = new Date(archive.getDate().getTime());
	    ps.setDate(2, dateSql);
	    ps.setInt(3, idDonnee);
	    res = ps.executeUpdate();
	    ResultSet result = ps.getGeneratedKeys();
	    if (result.next()) {
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.ajouterLieu " + e.getMessage());
	}

	if (archive.getPhotos() != null) {
	    // ajout des photos
	}

	return res;
    }

    public int ajouterDonnee(DonneeMeteo donnee) {
	int res = 0;

	try {
	    String sql = "insert into T_DONNEE_DON (DON_pluie, DON_directionVent, DON_vitesseVent, DON_soleil, DON_temperature)"
		    + "values (?, ?, ?,?,?) ";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setDouble(1, donnee.getPluie());
	    ps.setDouble(2, donnee.getDirectionVent());
	    ps.setDouble(3, donnee.getVitesseVent());
	    ps.setInt(4, donnee.getSoleil().getId());
	    ps.setDouble(5, donnee.getTemperature());
	    res = ps.executeUpdate();
	    ResultSet result = ps.getGeneratedKeys();
	    if (result.next()) {
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
		return result.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.ajouterDonnee " + e.getMessage());
	}
	return -1;
    }

    public int ajouterLieu(Lieu lieu) {
	int res = 0;

	try {
	    String sql = "insert into T_LIEU_LIE (LIE_ville, LIE_pays, LIE_departement)" + "values (?, ?, ?) ";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, lieu.getVille());
	    ps.setString(2, lieu.getPays());
	    ps.setString(3, lieu.getDepartement());
	    res = ps.executeUpdate();
	    ResultSet result = ps.getGeneratedKeys();
	    if (result.next()) {
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
		return result.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.ajouterLieu " + e.getMessage());
	}
	return res;
    }

    public List<ArchiveMeteo> consulterParJour(java.util.Date date) {
	List<ArchiveMeteo> archives = new ArrayList<ArchiveMeteo>();
	System.out.println(new java.sql.Date(date.getTime()));
	try {
	    String sql = "SELECT * FROM `T_ARCHIVE_ARC` WHERE `ARC_date` = '" + new java.sql.Date(date.getTime()) + "'";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    // ps.setDate(1, new java.sql.Date(date.getTime()));
	    ResultSet rs = ps.executeQuery(sql);
	    System.out.println("Exec sql : " + sql);

	    while (rs.next()) {
		ArchiveMeteo archive = new ArchiveMeteo();
		archive.setDate(date);
		archive.setDonnee(this.getDonnee(rs.getInt("ARC_donnee")));
		archive.setLieu(this.getLieu(rs.getInt("ARC_lieu")));
		// archive.setPhotos(this.getPhoto(rs.getInt("ARC_id")));
		archives.add(archive);
	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.consulterParJour " + e.getMessage());
	}
	return archives;

    }

    public List<ArchiveMeteo> consulterParMois(java.util.Date date) {
	// SELECT month(ARC_date), year(ARC_date) FROM T_ARCHIVE_ARC;
	List<ArchiveMeteo> archives = new ArrayList<ArchiveMeteo>();
	System.out.println(new java.sql.Date(date.getTime()));
	java.sql.Date dateSql = new java.sql.Date(date.getTime());
	String[] dateString = dateSql.toString().split("-");
	try {
	    String sql = "SELECT * FROM `T_ARCHIVE_ARC` WHERE month(ARC_date) = " + dateString[1]
		    + " AND year(ARC_date) = " + dateString[0];
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    // ps.setDate(1, new java.sql.Date(date.getTime()));
	    ResultSet rs = ps.executeQuery(sql);
	    System.out.println("Exec sql : " + sql);

	    while (rs.next()) {
		ArchiveMeteo archive = new ArchiveMeteo();
		archive.setDate(date);
		archive.setDonnee(this.getDonnee(rs.getInt("ARC_donnee")));
		archive.setLieu(this.getLieu(rs.getInt("ARC_lieu")));
		// archive.setPhotos(this.getPhoto(rs.getInt("ARC_id")));
		archives.add(archive);
	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.consulterParJour " + e.getMessage());
	}
	return archives;
    }

    private Lieu getLieu(int id) {
	Lieu lieu = new Lieu();
	String selectSQL = "SELECT * FROM T_LIEU_LIE WHERE LIE_id = " + id;
	try {
	    PreparedStatement preparedStatement = co.prepareStatement(selectSQL);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL);
	    while (rs.next()) {
		lieu.setDepartement(rs.getString("LIE_departement"));
		lieu.setPays(rs.getString("LIE_pays"));
		lieu.setVille(rs.getString("LIE_ville"));
	    }
	} catch (SQLException e) {
	    System.out.println("Erreur Base.getLieu " + e.getMessage());

	}

	return lieu;
    }

    private DonneeMeteo getDonnee(int id) {
	DonneeMeteo donnee = new DonneeMeteo();
	String selectSQL = "SELECT * FROM T_DONNEE_DON WHERE DON_id = " + id;
	try {
	    PreparedStatement preparedStatement = co.prepareStatement(selectSQL);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL);
	    System.out.println("Exec sql : " + selectSQL);
	    while (rs.next()) {
		donnee.setDirectionVent(rs.getDouble("DON_directionVent"));
		donnee.setPluie(rs.getDouble("DON_pluie"));
		donnee.setSoleil(Soleil.AVERSE_ORAGEUSE);
		donnee.setTemperature(rs.getInt("DON_temperature"));
		donnee.setVitesseVent(rs.getDouble("DON_vitesseVent"));
	    }
	} catch (SQLException e) {
	    System.out.println("Erreur Base.getLieu " + e.getMessage());

	}

	return donnee;
    }

    public boolean connexion(String identifiant, String mdp) {
	String selectSQL = "SELECT * FROM T_CONNEXION_CON WHERE CON_identifiant = '" + identifiant
		+ "' AND CON_motDePasse = '" + mdp + "'";
	try {
	    PreparedStatement preparedStatement = co.prepareStatement(selectSQL);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL);
	    System.out.println("Exec sql : " + selectSQL);
	    while (rs.next()) {
		return true;
	    }
	} catch (SQLException e) {
	    System.out.println("Erreur Base.getLieu " + e.getMessage());

	}
	return false;
    }
}
