package servlet;

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
		System.out.println("HOLLA");
		try {

			myOut = response.getOutputStream();
			List<ArchiveMeteo> list = (List<ArchiveMeteo>) request.getSession().getAttribute("listeArchive");
			System.out.println(list);
			if (list == null) {
				System.out.println("Liste vide");
			} else {
				System.out.println("Liste non vide");
			}
			byte[] fichier = Manager.creer(request).getServeur().productionPDF(list);// Getting mistake here
			// File myfile = new File(filepath + filename);
			//
			// //set response headers
			response.setContentType("application/pdf"); // I want to download a PDF file
			//
			response.addHeader("Content-Disposition", "attachment; filename=rapport.pdf");
			//
			response.setContentLength(fichier.length);
			//
			System.out.println("taille : " + fichier.length);
			InputStream input = new ByteArrayInputStream(fichier);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			//
			// read from the file; write to the ServletOutputStream
			while ((readBytes = buf.read()) != -1) {
				myOut.write(readBytes);
				System.out.println("passer");
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
		request.getSession().setAttribute("listeArchive", null);
		request.getServletContext().getRequestDispatcher("/mois.jsp").forward(request, response);
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
