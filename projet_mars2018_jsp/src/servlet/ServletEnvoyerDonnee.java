package servlet;

import java.io.IOException;
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
import manager.Manager;
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

		Manager manager = Manager.creer(request);

		String pays = request.getParameter("pays");
		String ville = request.getParameter("ville");
		String departement = request.getParameter("departement");
		String direction = request.getParameter("direction");
		String vitesse = request.getParameter("vitesse");
		String temperature = request.getParameter("temperature");
		String pluie = request.getParameter("pluie");
		String date = request.getParameter("date");
		String ciel = request.getParameter("ciel");

		Validation valide = manager.getServeur().valider(pays, ville, departement, direction, vitesse, temperature,
				pluie, date, ciel);
		if (valide.isValide()) {
			// creation archive meteo
			ArchiveMeteo archive = new ArchiveMeteo();
			// recuperation date
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
			Date dateD = new Date();
			try {
				dateD = formatter.parse(date);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			archive.setDate(dateD);
			// recuperation du lieu
			Lieu lieu = new Lieu();
			lieu.setPays(pays);
			lieu.setDepartement(departement);
			lieu.setVille(ville);
			archive.setLieu(lieu);
			// recuperation donnee meteo
			DonneeMeteo donnee = new DonneeMeteo();
			donnee.setDirectionVent(Double.parseDouble(direction));
			donnee.setPluie(Double.parseDouble(pluie));
			donnee.setSoleil(Soleil.getById(Integer.parseInt(ciel)));
			donnee.setTemperature(Integer.parseInt(temperature));
			donnee.setVitesseVent(Double.parseDouble(vitesse));
			archive.setDonnee(donnee);
			// envois au serveur
			manager.getServeur().ajouterDonneeArchive(archive);
		} else {
			request.setAttribute("valide", valide);

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
