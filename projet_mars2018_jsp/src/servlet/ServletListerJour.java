package servlet;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
import rmi.Serveur;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int port = 2000;

		try {
			Registry registry = LocateRegistry.getRegistry(port);

			Serveur serveur = (Serveur) registry.lookup("serveurRMI");

			List<ArchiveMeteo> list = serveur.consulterParJour(date);
			String str = new String(list.get(0).getPhotos().get(0).getImage(), "UTF-8");
			System.out.println(str);
			request.setAttribute("lst", list);
		} catch (Exception e) {
			System.out.println("Erreur client RMI" + e.toString());
		}

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
