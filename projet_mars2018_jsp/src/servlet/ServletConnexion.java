package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.Manager;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/ServletConnexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletConnexion() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ident = request.getParameter("ident");
		String mdp = request.getParameter("mdp");

		Manager manager = Manager.creer(request);

		if (manager.getServeur().connexion(ident, mdp)) {
			System.out.println("identification OK " + ident + " " + mdp);

			manager.setIdentifie(true);
			manager.setIdent(ident);

			response.sendRedirect("ServletAccueil");

			return;
		} else {
			System.out.println("Erreur ident " + ident + " " + mdp);
			request.setAttribute("ident", ident);
			request.setAttribute("mdp", mdp);
			request.setAttribute("erreur", "Identifiant ou mot de passe incorrect");
			request.getServletContext().getRequestDispatcher("/connexion.jsp").forward(request, response);

		}

	}

}
