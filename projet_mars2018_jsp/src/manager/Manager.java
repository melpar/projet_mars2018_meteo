package manager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.http.HttpServletRequest;

import rmi.Serveur;

public class Manager {

	/**
	 * Vrai si l'utilisateur est identifié
	 */
	boolean identifie = false;

	/**
	 * Contient l'identifiant de l'utilisateur, null si pas connecté
	 */
	String ident = null;

	/**
	 * Instance du serveur RMI
	 */
	private Serveur serveur;

	/**
	 * Permet de créer un manager. Retourne l'instance de session si existe.
	 * 
	 * @param request
	 *            permet d'accéder à la session
	 * @return une instance du manager, celle en session ou une nouvelle si
	 *         n'existe pas
	 */
	public static Manager creer(HttpServletRequest request) {
		// Récupération de l'objet manager en session
		Manager manager = (Manager) request.getSession().getAttribute("manager");

		// Si l'objet n'existe pas
		if (manager == null) {
			// Création d'un manager
			manager = new Manager();
			int port = 2000;

			try {
				Registry registry = LocateRegistry.getRegistry(port);
				// Récupération du serveur RMI
				manager.serveur = (Serveur) registry.lookup("serveurRMI");

			} catch (Exception e) {
				System.out.println("Erreur client RMI" + e.toString());
			}
			request.getSession().setAttribute("manager", manager);
		}
		return manager;
	}

	public boolean isIdentifie() {
		return identifie;
	}

	public void setIdentifie(boolean identifie) {
		this.identifie = identifie;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public Serveur getServeur() {
		return serveur;
	}

	public void setServeur(Serveur serveur) {
		this.serveur = serveur;
	}

}
