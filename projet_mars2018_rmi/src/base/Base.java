package base;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

import javax.sql.rowset.serial.SerialBlob;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Photo;
import bean.Soleil;
import util.Cryptage;

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
	    e.printStackTrace();
	}
    }

    /**
     * Ouverture de la base
     * 
     * @return true, si la connection a la base a reussi
     */
    public boolean ouvrir() {
	// On récupère les données dans le fichier de ressources
	ResourceBundle resource = ResourceBundle.getBundle(config);
	String url = resource.getString("url");
	String user = resource.getString("user");
	String password = resource.getString("password");
	try {
	    co = (Connection) DriverManager.getConnection(url, user, password);
	} catch (SQLException e) {
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
	    e.printStackTrace();
	}
    }

    /**
     * Ajout d'une donnée à la base
     * 
     * @param archive
     *            archive à ajouter
     * @return id de l'archive ajoutée, -1 si erreur
     */
    public int ajouterDonneeArchive(ArchiveMeteo archive) {
	// ajout du lieu
	int res = 0;
	int idLieu = this.ajouterLieu(archive.getLieu());
	if (idLieu == -1) {
	    return -1;
	}
	// ajout de la donnee
	int idDonnee = this.ajouterDonnee(archive.getDonnee());
	if (idDonnee == -1) {
	    // Erreur
	}
	// creation de l'archive
	int idArchive = -1;
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
		idArchive = result.getInt(1);
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.ajouterLieu " + e.getMessage());
	}

	// On ajoute les photos si il y en a
	if (archive.getPhotos() != null) {
	    for (Photo photo : archive.getPhotos()) {
		this.ajouterImage(idArchive, photo);
	    }
	}
	// id de l'archive créée
	return idArchive;
    }

    /**
     * Permet d'ajouter les données météo à la base
     * 
     * @param donnee
     *            données à ajouter
     * @return id de l'objet donneeMeteo ajouté
     */
    public int ajouterDonnee(DonneeMeteo donnee) {
	try {
	    String sql = "insert into T_DONNEE_DON (DON_pluie, DON_directionVent, DON_vitesseVent, DON_soleil, DON_temperature)"
		    + "values (?, ?, ?,?,?) ";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setDouble(1, donnee.getPluie());
	    ps.setDouble(2, donnee.getDirectionVent());
	    ps.setDouble(3, donnee.getVitesseVent());
	    ps.setInt(4, donnee.getSoleil().getId());
	    ps.setDouble(5, donnee.getTemperature());
	    ps.executeUpdate();
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

    /**
     * Permet d'ajouter un lieu à la base. Si le lieu existe déjà (même valeurs)
     * on utilise cet id.
     * 
     * @param lieu
     *            lieu à ajouter
     * @return id du lieu ajouté
     */
    public int ajouterLieu(Lieu lieu) {
	int res = this.verifierLieuExiste(lieu);
	if (res == -1) {
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
	}
	return res;
    }

    /**
     * Permet de verifier si un lieu existe déjà en base.
     * 
     * @param lieu
     *            lieu à rechercher
     * @return id du lieu
     */
    private int verifierLieuExiste(Lieu lieu) {
	try {
	    String ville = lieu.getVille().toUpperCase();
	    String pays = lieu.getPays().toUpperCase();
	    String departement = lieu.getDepartement().toUpperCase();
	    String sql = "SELECT * FROM `T_LIEU_LIE` WHERE UPPER(LIE_ville) = '" + ville + "' AND UPPER(LIE_pays) = '"
		    + pays + "' AND UPPER(LIE_departement) = '" + departement + "'";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    // ps.setDate(1, new java.sql.Date(date.getTime()));
	    ResultSet rs = ps.executeQuery(sql);
	    System.out.println("Exec sql : " + sql);

	    while (rs.next()) {
		return rs.getInt("LIE_id");
	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.verifierLieu " + e.getMessage());
	}
	return -1;
    }

    /**
     * Permet de consulter une liste d'archive pour un jour donné
     * 
     * @param date
     *            jour recherché
     * @return liste d'archives
     */
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
		archive.setPhotos(this.getPhotos(rs.getInt("ARC_id")));
		archive.setId(rs.getInt("ARC_id"));
		archives.add(archive);
	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.consulterParJour " + e.getMessage());
	}
	return archives;

    }

    /**
     * Permet de retourner les images pour une archive donnée.
     * 
     * @param idArchive
     *            id de l'archive à rechercher
     * @return liste des images associées
     */
    private List<Photo> getPhotos(int idArchive) {
	List<Photo> photos = new ArrayList<>();
	String selectSQL = "SELECT * FROM T_PHOTO_PHO WHERE PHO_archive = " + idArchive;
	try {
	    PreparedStatement preparedStatement = co.prepareStatement(selectSQL);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL);
	    while (rs.next()) {
		Photo photo = new Photo();
		int blobLength = (int) rs.getBlob("PHO_image").length();
		byte[] blobAsBytes = rs.getBlob("PHO_image").getBytes(1, blobLength);
		photo.setImage(Base64.getEncoder().encode(blobAsBytes));
		photo.setNom(rs.getString("PHO_nom"));
		photo.setId(rs.getInt("PHO_id"));
		photos.add(photo);
	    }
	} catch (SQLException e) {
	    System.out.println("Erreur Base.getPhotos " + e.getMessage());

	}
	return photos;
    }

    /**
     * Pour de consulter la liste d'archives pour un mois donné
     * 
     * @param date
     *            contient le mois et l'année à chercher
     * @return liste d'archives associées
     */
    public List<ArchiveMeteo> consulterParMois(java.util.Date date) {
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
		archive.setDate(rs.getDate("ARC_date"));
		archive.setDonnee(this.getDonnee(rs.getInt("ARC_donnee")));
		archive.setLieu(this.getLieu(rs.getInt("ARC_lieu")));
		archive.setPhotos(this.getPhotos(rs.getInt("ARC_id")));
		archives.add(archive);
	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.consulterParJour " + e.getMessage());
	}
	return archives;
    }

    /**
     * Permet d'obtenir un lieu en fonction de l'id.
     * 
     * @param id
     *            id à rechercher
     * @return lieu associé
     */
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
		lieu.setId(id);
	    }
	} catch (SQLException e) {
	    System.out.println("Erreur Base.getLieu " + e.getMessage());

	}

	return lieu;
    }

    /**
     * Permet d'obtenir l'objet donnée en fonction de son id
     * 
     * @param id
     *            id à rechercher
     * @return donnée associée
     */
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
		donnee.setSoleil(Soleil.getById(rs.getInt("DON_soleil")));
		donnee.setTemperature(rs.getInt("DON_temperature"));
		donnee.setVitesseVent(rs.getDouble("DON_vitesseVent"));
	    }
	} catch (SQLException e) {
	    System.out.println("Erreur Base.getLieu " + e.getMessage());

	}

	return donnee;
    }

    /**
     * Permet de savoir si le couple id mdp existe en base
     * 
     * @param identifiant
     *            identifiant à tester
     * @param mdp
     *            mot de passe à tester
     * @return vrai si existe en base, faux sinon
     */
    public boolean connexion(String identifiant, String mdp) {
	util.Cryptage cry = new Cryptage(mdp);
	String mdpCrypte = "";
	try {
	    mdpCrypte = cry.chiffrer();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	String selectSQL = "SELECT * FROM T_CONNEXION_CON WHERE CON_identifiant = '" + identifiant
		+ "' AND CON_motDePasse = '" + mdpCrypte + "'";
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

    /**
     * Permet d'ajouter une photo à une archive
     * 
     * @param idArchive
     *            id de l'archive associée
     * @param photo
     *            photo à ajouter
     */
    public void ajouterImage(int idArchive, Photo photo) {

	try {
	    String sql = "insert into T_PHOTO_PHO (PHO_nom, PHO_image, PHO_archive)" + "values (?, ?, ?) ";
	    PreparedStatement ps = co.prepareStatement(sql);
	    ps.setString(1, photo.getNom());
	    ps.setBlob(2, new SerialBlob(photo.getImage()));
	    ps.setInt(3, idArchive);
	    ps.executeUpdate();
	    System.out.println("Exec sql : " + sql);
	} catch (Exception e) {
	    System.out.println("Erreur Base.ajouterLieu " + e.getMessage());
	}

    }

    /**
     * Permet de chercher une archive météo en fonction de son id
     * 
     * @param id
     *            id à rechercher
     * @return archive associée
     */
    public ArchiveMeteo consulterParId(int id) {
	ArchiveMeteo archive = new ArchiveMeteo();
	try {
	    String sql = "SELECT * FROM `T_ARCHIVE_ARC` WHERE `ARC_id` = '" + id + "'";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    // ps.setDate(1, new java.sql.Date(date.getTime()));
	    ResultSet rs = ps.executeQuery(sql);
	    System.out.println("Exec sql : " + sql);

	    if (rs.next()) {
		archive.setDate(rs.getDate("ARC_date"));
		archive.setDonnee(this.getDonnee(rs.getInt("ARC_donnee")));
		archive.setLieu(this.getLieu(rs.getInt("ARC_lieu")));
		archive.setPhotos(this.getPhotos(rs.getInt("ARC_id")));
		archive.setId(rs.getInt("ARC_id"));
	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.consulterParJour " + e.getMessage());
	}
	return archive;
    }

    /**
     * Permet de mettre à jour un lieu en fonction de son id.
     * 
     * @param id
     *            id du lieu à mettre à jour
     * @param lieu
     *            lieu associé
     * @return id du lieu, -1 si il y eu une erreur
     */
    public int miseAJourLieu(int id, Lieu lieu) {
	int idLieu = this.ajouterLieu(lieu);
	try {
	    String sql = "update T_ARCHIVE_ARC set ARC_lieu = " + idLieu + " where ARC_id = " + id;
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    ps.executeUpdate();
	    ResultSet result = ps.getGeneratedKeys();
	    if (result.next()) {
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
		return result.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.miseAJourLieu" + e.getMessage());
	}
	return -1;
    }

    /**
     * Permet de mettre à jour la date en fonction de l'id de l'archive
     * associée.
     * 
     * @param id
     *            id de l'archive
     * @param date
     *            nouvelle date
     * @return id de l'archive modifiée, -1 si erreur
     */
    public int miseAJourDate(int id, java.util.Date date) {
	try {
	    String sql = "update T_ARCHIVE_ARC set ARC_date = '" + new java.sql.Date(date.getTime())
		    + "' where ARC_id = " + id;
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    ps.executeUpdate();
	    ResultSet result = ps.getGeneratedKeys();
	    if (result.next()) {
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
		return result.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.MiseAJourDate " + e.getMessage());
	}
	return -1;
    }

    /**
     * Permet de mette à jour les données associées à une archive.
     * 
     * @param id
     *            id de l'archive à modifier
     * @param donnee
     *            nouvelles données
     * @return id de l'archive modifiée, -1 si erreur
     */
    public int miseAJour(int id, DonneeMeteo donnee) {
	int idDonnee = selectionnerIdDonne(id);
	this.modifierDonnee(idDonnee, donnee);
	try {
	    String sql = "update T_ARCHIVE_ARC set ARC_donnee = " + idDonnee + " where ARC_id = " + id;
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	    ps.executeUpdate();
	    ResultSet result = ps.getGeneratedKeys();
	    if (result.next()) {
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
		return result.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.miseAJourDonnee " + e.getMessage());
	}
	return -1;
    }

    /**
     * Permet d'obtenir l'id des données associées à une archive.
     * 
     * @param id
     *            id de l'archive
     * @return id des données
     */
    private int selectionnerIdDonne(int id) {
	try {
	    String sql = "SELECT * FROM `T_ARCHIVE_ARC` WHERE `ARC_id` = '" + id + "'";
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    // ps.setDate(1, new java.sql.Date(date.getTime()));
	    ResultSet rs = ps.executeQuery(sql);
	    System.out.println("Exec sql : " + sql);

	    if (rs.next()) {
		return rs.getInt("ARC_donnee");

	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.selectionnerIdDonnee " + e.getMessage());
	}
	return -1;
    }

    /**
     * Permet de modifier des données.
     * 
     * @param idDonnee
     *            id des données à modifier
     * @param donnee
     *            nouvelles valeurs
     * @return id de la donnée modifiée, -1 si erreur
     */
    private int modifierDonnee(int idDonnee, DonneeMeteo donnee) {
	try {
	    String sql = "update T_DONNEE_DON set DON_pluie = " + donnee.getPluie() + ", DON_directionVent = "
		    + donnee.getDirectionVent() + ", DON_vitesseVent = " + donnee.getVitesseVent() + ", DON_soleil = "
		    + donnee.getSoleil().getId() + ", DON_temperature = " + donnee.getTemperature() + " WHERE DON_id = "
		    + idDonnee;
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.executeUpdate();
	    ResultSet result = ps.getGeneratedKeys();
	    if (result.next()) {
		System.out.println("Exec sql : " + sql + " enregistré à l'id" + result.getInt(1));
		return result.getInt(1);
	    }
	} catch (Exception e) {
	    System.out.println("Erreur Base.modifierDonnee " + e.getMessage());
	}
	return -1;
    }

    /**
     * Permet d'obtenir la liste des lieux contenus en base.
     * 
     * @return liste des lieux
     */
    public List<Lieu> getLieux() {
	List<Lieu> lieux = new ArrayList<Lieu>();
	String selectSQL = "SELECT * FROM T_LIEU_LIE";
	try {
	    PreparedStatement preparedStatement = co.prepareStatement(selectSQL);
	    ResultSet rs = preparedStatement.executeQuery(selectSQL);
	    while (rs.next()) {
		Lieu lieu = new Lieu();
		lieu.setDepartement(rs.getString("LIE_departement"));
		lieu.setPays(rs.getString("LIE_pays"));
		lieu.setVille(rs.getString("LIE_ville"));
		lieu.setId(rs.getInt("LIE_id"));
		lieux.add(lieu);
	    }
	} catch (SQLException e) {
	    System.out.println("Erreur Base.getLieu " + e.getMessage());

	}
	return lieux;
    }

    /**
     * Permet d'obtenir les données pour une date et un lieu.
     * 
     * @param date
     *            date à chercher
     * @param lieu
     *            lieu à chercher
     * @return les des données
     */
    public List<DonneeMeteo> getDonnees(java.util.Date date, int lieu) {
	List<DonneeMeteo> donnees = new ArrayList<DonneeMeteo>();
	System.out.println(new java.sql.Date(date.getTime()));
	try {
	    String sql = "SELECT * FROM `T_ARCHIVE_ARC` WHERE `ARC_date` = '" + new java.sql.Date(date.getTime())
		    + "' AND ARC_lieu = " + lieu;
	    PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    // ps.setDate(1, new java.sql.Date(date.getTime()));
	    ResultSet rs = ps.executeQuery(sql);
	    System.out.println("Exec sql : " + sql);

	    while (rs.next()) {
		donnees.add(this.getDonnee(rs.getInt("ARC_donnee")));

	    }

	} catch (Exception e) {
	    System.out.println("Erreur Base.consulterParJour " + e.getMessage());
	}
	return donnees;
    }

    /**
     * Permet de supprimer une image.
     * 
     * @param idImage
     *            id de l'image à supprimer
     * @return vrai si supprimé, faux si erreur
     */
    public boolean supprimerImage(int idImage) {
	try {
	    String sql = "DELETE FROM `T_PHOTO_PHO` WHERE `PHO_id` = " + idImage;
	    java.sql.Statement stmt = co.createStatement();
	    stmt.executeUpdate(sql);
	    return true;
	} catch (

	Exception e) {
	    System.out.println("Erreur Base.supprimerImage " + e.getMessage());
	}
	return false;
    }

    private void supprimerImageParArchive(int idArchive) {
	try {
	    String sql = "DELETE FROM `T_PHOTO_PHO` WHERE `PHO_archive` = " + idArchive;
	    java.sql.Statement stmt = co.createStatement();
	    stmt.executeUpdate(sql);
	} catch (

	Exception e) {
	    System.out.println("Erreur Base.supprimerImage " + e.getMessage());
	}
    }

    private void supprimerDonnee(int id) {
	try {
	    String sql = "DELETE FROM `T_DONNEE_DON` WHERE `DON_id` = " + id;
	    java.sql.Statement stmt = co.createStatement();
	    stmt.executeUpdate(sql);
	} catch (

	Exception e) {
	    System.out.println("Erreur Base.supprimerImage " + e.getMessage());
	}
    }

    public boolean supprimerArchive(int idArchive) {
	boolean ret = true;
	// Suppression des images
	this.supprimerImageParArchive(idArchive);
	// Recupérer id données
	int idDonnee = this.selectionnerIdDonne(idArchive);
	// Suppression de l'archive
	try {
	    String sql = "DELETE FROM `T_ARCHIVE_ARC` WHERE `ARC_id` = " + idArchive;
	    java.sql.Statement stmt = co.createStatement();
	    stmt.executeUpdate(sql);
	} catch (Exception e) {
	    ret = false;
	    System.out.println("Erreur Base.supprimerArchive " + e.getMessage());
	}
	// Suppression des données
	this.supprimerDonnee(idDonnee);
	return ret;
    }

}
