package servlet.ajouter;

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
@WebServlet("/ServletAjoutFichier")
@MultipartConfig
public class ServletAjoutFichier extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAjoutFichier() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean isXML = true;
		Manager manager = Manager.creer(request);
		final int PKG_SIZE = 1024;
		StringBuilder buffer = new StringBuilder(PKG_SIZE * 10);
		for (Part part : request.getParts()) {

			String filename = FilesUtil.getFilename(part);
			if (filename == null) {
				// Traiter les champs classiques ici (input
				// type="text|radio|checkbox|etc",
				// select, etc).
				String fieldname = part.getName();
				String fieldvalue = FilesUtil.getValue(part);
				if (fieldname.equals("group1") && fieldvalue.equals("json")) {
					isXML = true;
				}
			} else if (!filename.isEmpty()) {
				// Traiter les champs de type fichier (input type="file").

				filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																														// fix.
				InputStream is = part.getInputStream();

				byte[] data = new byte[PKG_SIZE];
				int size;

				size = is.read(data, 0, data.length);
				while (size > 0) {
					String str = new String(data, 0, size);
					buffer.append(str);
					size = is.read(data, 0, data.length);
				}

			}
		}
		String group1 = request.getParameter("group1");
		// compare selected value
		if ("json".equals(group1)) {
			isXML = false;
		}
		if (isXML) {
			manager.getServeur().ajouterDonneesArchiveXML(buffer.toString());
		} else {
			manager.getServeur().ajouterDonneesArchiveJSON(buffer.toString());
		}
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
