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

		BufferedInputStream buf = null;
		ServletOutputStream myOut = null;
		try {
			myOut = response.getOutputStream();
			List<ArchiveMeteo> list = (List<ArchiveMeteo>) request.getSession().getAttribute("lst");
			byte[] fichier;

			if (request.getParameter("pdf") != null) {
				fichier = Manager.creer(request).getServeur().productionPDF(list);// Getting
				// mistake
				// here

				// set response headers
				response.setContentType("application/pdf"); // I want to
															// download a PDF
															// file
				//
				response.addHeader("Content-Disposition", "attachment; filename=rapport.pdf");
			} else {
				fichier = Manager.creer(request).getServeur().productionXml(list);// Getting
				// mistake
				// here

				// set response headers
				response.setContentType("application/xml"); // I want to
				// download a PDF
				// file
				//
				response.addHeader("Content-Disposition", "attachment; filename=rapport.xml");
			}

			//
			response.setContentLength(fichier.length);
			//
			InputStream input = new ByteArrayInputStream(fichier);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			//
			// read from the file; write to the ServletOutputStream
			while ((readBytes = buf.read()) != -1) {
				myOut.write(readBytes);
			}

		} catch (IOException ioe) {
		} finally {

			// close the input/output streams
			if (myOut != null) {
				myOut.close();
			}
			if (buf != null) {
				buf.close();
			}

		}
		request.getSession().setAttribute("lst", null);
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
