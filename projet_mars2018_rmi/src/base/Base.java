package base;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;

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
}
