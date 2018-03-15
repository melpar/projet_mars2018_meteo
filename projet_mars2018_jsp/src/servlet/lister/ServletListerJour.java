package servlet.lister;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ArchiveMeteo;
import manager.Manager;

/**
 * Servlet implementation class ServletListerJour
 */
@WebServlet("/ServletListerJour")
public class ServletListerJour extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletListerJour() {
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
		String dateString = request.getParameter("maDate");
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
		Date date = new Date();
		try {
			date = formatter.parse(dateString);
		} catch (java.text.ParseException e) {

		}

		Manager manager = Manager.creer(request);
		List<ArchiveMeteo> list = manager.getServeur().consulterParJour(date);
		request.setAttribute("lst", list);
		request.getServletContext().getRequestDispatcher("/jours.jsp").forward(request, response);
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
