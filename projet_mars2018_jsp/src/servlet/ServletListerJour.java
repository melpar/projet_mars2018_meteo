package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ArchiveMeteo;
import rmi.Serveur;
import rmi.impl.ServeurImpl;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Serveur serveur =  new ServeurImpl();
		List<ArchiveMeteo> list = serveur.consulterParJour((Date) request.getAttribute("date"));
		System.out.println("element 1 : "+list.get(0).getLieu().getVille());
		request.setAttribute("lst",list);
		request.getServletContext().getRequestDispatcher(
				"/jours.jsp").
					forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
