package servlet.modifier;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ArchiveMeteo;
import manager.Manager;
import validation.Validation;

/**
 * Servlet implementation class ServletModifierArchive
 */
@WebServlet("/ServletModifierArchive")
public class ServletModifierArchive extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletModifierArchive() {
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
		// ArchiveMeteo archive =
		ArchiveMeteo archive = manager.getServeur().consulterParId(Integer.parseInt(request.getParameter("archiveId")));
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);

		Validation valide = manager.getServeur().valider(archive.getLieu().getPays(), archive.getLieu().getVille(),
				archive.getLieu().getDepartement(), Double.toString(archive.getDonnee().getDirectionVent()),
				Double.toString(archive.getDonnee().getVitesseVent()),
				Integer.toString(archive.getDonnee().getTemperature()), Double.toString(archive.getDonnee().getPluie()),
				formatter.format(archive.getDate()), Integer.toString(archive.getDonnee().getSoleil().getId()));

		request.setAttribute("valide", valide);
		request.setAttribute("archiveId", request.getParameter("archiveId"));
		request.getServletContext().getRequestDispatcher("/modifierArchive.jsp").forward(request, response);
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
