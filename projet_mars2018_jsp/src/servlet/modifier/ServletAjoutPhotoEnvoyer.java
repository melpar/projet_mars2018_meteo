package servlet.modifier;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Photo;
import manager.Manager;
import util.FilesUtil;

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

		String[] listNom = null;
		List<Photo> listePhoto = new ArrayList<Photo>();
		for (Part part : request.getParts()) {
			String filename = FilesUtil.getFilename(part);
			if (filename == null) {
				// Traitement des champs classiques.
				String fieldname = part.getName();
				String fieldvalue = FilesUtil.getValue(part);
				if (fieldname.equals("imageNom")) {
					// recuperation des nom d'image
					listNom = fieldvalue.split(".jpg,?|.jpeg,?");
				}
			} else if (!filename.isEmpty()) {
				// Traiter les champs de type fichier.
				InputStream filecontent = part.getInputStream();

				Photo photo = new Photo();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				// transformation du format de l'image.
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

		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
		System.out.println(request.getParameter("imageBoutton"));
		String values[] = request.getParameter("imageBoutton").split("-");

		String erreur = null;
		if (listePhoto.size() == 0) {
			erreur = "Erreur : pas de fichier sélectionné";
		} else {
			// association des noms aux photo
			for (int i = 0; i < listePhoto.size(); i++) {
				listePhoto.get(i).setNom(listNom[i]);
			}
			// envois des photos sur le serveur
			Manager manager = Manager.creer(request);
			manager.getServeur().ajouterPhoto(Integer.parseInt(values[0]), listePhoto);
			erreur = "Photo(s) ont été ajoutée(s)";
		}
		request.setAttribute("message", erreur);
		request.setAttribute("dateEntre", values[1]);
		request.getServletContext().getRequestDispatcher("/modifier.jsp").forward(request, response);
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
