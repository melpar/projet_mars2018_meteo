package servlet.modifier;

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
 * Servlet implementation class ServletModifierArchiveEnvoyer
 */
@WebServlet("/ServletModifierArchiveEnvoyer")
public class ServletModifierArchiveEnvoyer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletModifierArchiveEnvoyer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		String id = request.getParameter("archiveId");

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
			archive.setId(Integer.parseInt(id));
			manager.getServeur().modifierDonnee(archive);
			request.getServletContext().getRequestDispatcher("/modifier.jsp").forward(request, response);
		} else {
			request.setAttribute("archiveId", id);
			request.setAttribute("valide", valide);
			request.getServletContext().getRequestDispatcher("/modifierArchive.jsp").forward(request, response);

		}

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
