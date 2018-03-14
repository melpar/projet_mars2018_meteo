package servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Photo;
import manager.Manager;

/**
 * Servlet implementation class ServletAjoutPhotoEnvoyer
 */
@WebServlet("/ServletAjoutPhotoEnvoyer")
@MultipartConfig
public class ServletAjoutPhotoEnvoyer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAjoutPhotoEnvoyer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Photo> listePhoto = new ArrayList<Photo>();
		for (Part part : request.getParts()) {

			String filename = getFilename(part);
			if (filename == null) {
				// Traiter les champs classiques ici (input
				// type="text|radio|checkbox|etc",
				// select, etc).
				String fieldname = part.getName();
				String fieldvalue = getValue(part);
				System.out.println("objet : " + fieldname);
			} else if (!filename.isEmpty()) {
				// Traiter les champs de type fichier (input type="file").
				String fieldname = part.getName();
				filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																														// fix.
				InputStream filecontent = part.getInputStream();

				Photo photo = new Photo();
				photo.setNom(fieldname);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = filecontent.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				buffer.flush();
				photo.setImage(buffer.toByteArray());
				listePhoto.add(photo);
			}
		}
		System.out.println(request.getParameter("imageBoutton"));

		Manager manager = Manager.creer(request);
		manager.getServeur().ajouterPhoto(Integer.parseInt(request.getParameter("imageBoutton")), listePhoto);

		request.getServletContext().getRequestDispatcher("/ajoutPhotos.jsp").forward(request, response);
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

	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private static String getValue(Part part) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
		StringBuilder value = new StringBuilder();
		char[] buffer = new char[1024];
		for (int length = 0; (length = reader.read(buffer)) > 0;) {
			value.append(buffer, 0, length);
		}
		return value.toString();
	}

}
