<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lst" class="java.util.ArrayList" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel = "stylesheet" href = "https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
	<script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>           
	<script src = "Materialize/js/materialize.min.js"></script>
	<title>Visualiser mois</title>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<form class="col s12" method="get" action="ServletListerMois" >
      <div class="row">
      	<div class="input-field col s3">
			Date :        	
        </div>
        <div class="input-field col s3">
		    <select name="mois">
		      <option value="0"  selected>Janvier</option>
		      <option value="1">Février</option>
		      <option value="2">Mars</option>
		      <option value="3">Aril</option>
		      <option value="4">Mai</option>
		      <option value="5">Juin</option>
		      <option value="6">Juillet</option>
		      <option value="7">Aout</option>
		      <option value="8">Septembre</option>
		      <option value="9">Octobre</option>
		      <option value="10">Novembre</option>
		      <option value="11">Decembre</option>
		    </select>
		    <label>Mois</label>
	  	</div>
        <div class="input-field col s3">
		    <select name="annee">
	    		<c:forEach var = "i" begin = "2000" end = "2010">
        			 Item <c:out value = "${i}"/><p>
        			 <option value="<c:out value = "${i}"/>"  selected><c:out value = "${i}"/></option>
      			</c:forEach>
		    </select>
		    <label>Annee</label>
	  	</div>
        <div class="input-field col s3">
			<input name="submit" type="submit" value="Valider"/>       	
        </div>
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
					</ul>
				</div>
			</li>
		</c:forEach>
      </ul>
</body>
<script type="text/javascript">
 $(document).ready(function() {
    $('select').material_select();
  });
 </script>
</html>


