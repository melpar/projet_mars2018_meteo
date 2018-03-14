package servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import manager.Manager;
import util.FilesUtil;

/**
 * Servlet implementation class ServletAjoutXML
 */
@WebServlet("/ServletAjoutXML")
@MultipartConfig
public class ServletAjoutXML extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAjoutXML() {
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
		for (Part part : request.getParts()) {

			String filename = FilesUtil.getFilename(part);
			if (filename == null) {
				// Traiter les champs classiques ici (input
				// type="text|radio|checkbox|etc",
				// select, etc).
				String fieldname = part.getName();
				String fieldvalue = FilesUtil.getValue(part);
				System.out.println("objet : " + fieldname);
			} else if (!filename.isEmpty()) {
				// Traiter les champs de type fichier (input type="file").
				String fieldname = part.getName();
				filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																														// fix.
				InputStream is = part.getInputStream();
				final int PKG_SIZE = 1024;
				byte[] data = new byte[PKG_SIZE];
				StringBuilder buffer = new StringBuilder(PKG_SIZE * 10);
				int size;

				size = is.read(data, 0, data.length);
				while (size > 0) {
					String str = new String(data, 0, size);
					buffer.append(str);
					System.out.println(str);
					size = is.read(data, 0, data.length);
				}
				manager.getServeur().ajouterDonneesArchive(buffer.toString());
				System.out.println("test");
				System.out.println(buffer.toString());
			}
		}
		System.out.println(request.getParameter("documentBoutton"));

		request.getServletContext().getRequestDispatcher("/ajout.jsp").forward(request, response);
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
