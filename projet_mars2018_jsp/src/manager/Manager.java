package manager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.http.HttpServletRequest;

import rmi.Serveur;

public class Manager {

	boolean identifie = false;

	String ident = null;

	private Serveur serveur;

	public static Manager creer(HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		int port = 2000;

		try {
			Registry registry = LocateRegistry.getRegistry(port);

			manager.serveur = (Serveur) registry.lookup("serveurRMI");

		} catch (Exception e) {
			System.out.println("Erreur client RMI" + e.toString());
		}
		if (manager == null) {
			manager = new Manager();
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
