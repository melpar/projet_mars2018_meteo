<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
										<div class="col s12 m8 offset-m2 l6 offset-l3">
							        		<c:forEach var="photo" items="${archive.photos}">
							        			<%
														bean.Photo p = (bean.Photo)pageContext.getAttribute("photo");
														String image = new String(p.getImage(),"UTF-8");
													
														pageContext.setAttribute("p", image);
														pageContext.setAttribute("nom", p.getNom());
													%>
							        			  <div class="card" style="width: 50%">
												    <div class="card-image waves-effect waves-block waves-light">
												      <img class="activator" src="data:image/jpg;base64,${p}">
												    </div>
												    <div class="card-content">
												      <span class="card-title activator grey-text text-darken-4">${nom}</span>
												    </div>
												   
												  </div>
							          	</c:forEach> 
							      	</div>					    

</body>
</html>