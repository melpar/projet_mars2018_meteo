package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.Manager;

/**
 * Servlet implementation class ServletIndex
 */
@WebServlet("/ServletIndex")
public class ServletIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletIndex() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Manager manager = Manager.creer(request);
		Map<String, Double[]> informations = manager.getServeur().getInformationsVille(date);
		List<String> noms = new ArrayList<>();
		List<Double> temperature = new ArrayList<>();
		List<Double> pluie = new ArrayList<>();
		List<Double> vent = new ArrayList<>();
		for (String key : informations.keySet()) {
			temperature.add(informations.get(key)[0]);
			pluie.add(informations.get(key)[1]);
			vent.add(informations.get(key)[2]);
			noms.add(key);
		}
		// temperaturesNom temperature pluie vent

		request.setAttribute("noms", noms);
		request.setAttribute("temperature", temperature);
		request.setAttribute("pluie", pluie);
		request.setAttribute("vent", vent);
		request.setAttribute("initialise", true);

		request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
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
