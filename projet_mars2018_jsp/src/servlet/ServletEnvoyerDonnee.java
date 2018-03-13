package servlet;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ArchiveMeteo;
import bean.DonneeMeteo;
import bean.Lieu;
import bean.Soleil;
import rmi.Serveur;
import validation.Validation;

/**
 * Servlet implementation class ServletEnvoyerDonnee
 */
@WebServlet("/ServletEnvoyerDonnee")
public class ServletEnvoyerDonnee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletEnvoyerDonnee() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		int port = 2000;

		try {
			Registry registry = LocateRegistry.getRegistry(port);

			Serveur serveur = (Serveur) registry.lookup("serveurRMI");
			Validation valide = serveur.valider(request.getParameter("pays"), request.getParameter("ville"),
					request.getParameter("departement"), request.getParameter("direction"),
					request.getParameter("vitesse"), request.getParameter("temperature"));
			if (valide.isValide()) {
				// creation archive meteo
				ArchiveMeteo archive = new ArchiveMeteo();
				// recuperation date
				String dateString = request.getParameter("date");
				SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
				Date date = new Date();
				try {
					date = formatter.parse(dateString);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				archive.setDate(date);
				// recuperation du lieu
				Lieu lieu = new Lieu();
				lieu.setPays(request.getParameter("pays"));
				lieu.setDepartement(request.getParameter("departement"));
				lieu.setVille(request.getParameter("ville"));
				archive.setLieu(lieu);
				// recuperation donnee meteo
				DonneeMeteo donnee = new DonneeMeteo();
				donnee.setDirectionVent(Double.parseDouble(request.getParameter("direction")));
				donnee.setPluie(Double.parseDouble(request.getParameter("pluie")));
				donnee.setSoleil(Soleil.valueOf(request.getParameter("ciel")));
				donnee.setTemperature(Integer.parseInt(request.getParameter("temperature")));
				donnee.setVitesseVent(Double.parseDouble(request.getParameter("vitesse")));
				archive.setDonnee(donnee);
				// envois au serveur
				serveur.ajouterDonneeArchive(archive);
				System.out.println("passer archive");
			} else {
				request.setAttribute("valide", valide);
				System.out.println("pas passer archive");
				System.out.println(valide.getErreurs().get("temperature"));
				System.out.println(valide.getValeurs().get("temperature"));
			}
		} catch (Exception e) {
			System.out.println("Erreur client RMI" + e.toString());
		}
		request.getServletContext().getRequestDispatcher("/ajout.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
