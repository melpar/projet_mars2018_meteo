package servlet.lister;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ArchiveMeteo;
import manager.Manager;

/**
 * Servlet implementation class ServletListerMoisTelecharger
 */
@WebServlet("/ServletListerMoisTelecharger")
public class ServletListerMoisTelecharger extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletListerMoisTelecharger() {
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
		List<ArchiveMeteo> list = (List<ArchiveMeteo>) request.getSession().getAttribute("lst");
		BufferedInputStream buf = null;
		ServletOutputStream myOut = null;
		try {
			myOut = response.getOutputStream();

			byte[] fichier;
			// selection format du fichier a telecharger
			if (request.getParameter("pdf") != null) {
				fichier = Manager.creer(request).getServeur().productionPDF(list);
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=rapport.pdf");
			} else {
				fichier = Manager.creer(request).getServeur().productionXml(list);// Getting
				response.setContentType("application/xml"); // I want to
				response.addHeader("Content-Disposition", "attachment; filename=rapport.xml");
			}

			// envois du fichier au client
			response.setContentLength(fichier.length);
			InputStream input = new ByteArrayInputStream(fichier);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			while ((readBytes = buf.read()) != -1) {
				myOut.write(readBytes);
			}

		} catch (IOException ioe) {
		} finally {

			// traitement des erreur
			if (myOut != null) {
				myOut.close();
			}
			if (buf != null) {
				buf.close();
			}

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
