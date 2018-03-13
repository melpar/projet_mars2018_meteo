<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lst" class="java.util.ArrayList" scope="request" />
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel = "stylesheet" href = "https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
	<link rel = "stylesheet" href = "style/datepicker.css">
	<script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>           
	<script src = "Materialize/js/materialize.min.js"></script>
	<title>Visualiser jours</title>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<form class="col s12" method="get" action="ServletListerJour" >
      <div class="row">
      	<div class="input-field col s3">
			Date :        	
        </div>
        <div class="input-field col s6">
			<input name="maDate" type="text" class="datepicker">        	
        </div>
        <button class="btn waves-effect waves-light cyan lighten-2" type="submit" name="submit">Valider
		    <i class="material-icons right">send</i>
		  </button>
      </div>
    </form>
     <ul class="collapsible" data-collapsible="expandable">
        <c:forEach var="archive" items="${lst}">
	        <li>
				<div class="collapsible-header"><i class="material-icons">place</i>${archive.lieu.ville}</div>
				<div class="collapsible-body">
					<ul class="collection">
						<li class="collection-item">Humidité : ${archive.donnee.pluie}%</li>
						<li class="collection-item">Direction de vent : ${archive.donnee.directionVent}°</li>
						<li class="collection-item">Vitesse du vent : ${archive.donnee.vitesseVent} km/h</li>
						<li class="collection-item">Ciel : ${archive.donnee.soleil.name}</li>
						<li class="collection-item">Température : ${archive.donnee.temperature}°C</li>
						<ul class="collapsible" data-collapsible="expandable">
							<li>
								<div class="collapsible-header"><i class="material-icons">photo_library</i>Photo</div>
									<div class="collapsible-body">
										<div class="col s12 m8 offset-m2 l6 offset-l3">
								        	<c:forEach var="photo" items="${archive.photos}">
								        		<div class="card-panel grey lighten-5 z-depth-1">
								        		<%
													bean.Photo p = (bean.Photo)pageContext.getAttribute("photo");
													String image = new String(p.getImage(),"UTF-8");
												
													pageContext.setAttribute("p", image);
													pageContext.setAttribute("nom", p.getNom());
												%>
								          		<div class="row valign-wrapper">
								            		<div class="col s2">
								              			<img src="data:image/jpg;base64,${p}" alt="" class="responsive-img"> <!-- notice the "circle" class -->
								           			</div>
								            		<div class="col s10">
								              			<span class="black-text">
								                			${nom}
								              			</span>
								            		</div>
								          		</div>
											</div>
								          </c:forEach> 
								      </div>					    
								</div>
							</li>
						</ul>
					</ul>
				</div>
			</li>
		</c:forEach>
      </ul>
</body>

<script type="text/javascript">
$('.datepicker').pickadate({
    selectMonths: 3, // Creates a dropdown to control month
    selectYears: 18,
    selectDays: 12,
  });
  
$(document).ready(function(){
    $('.carousel').carousel();
  });
</script>


</html>


