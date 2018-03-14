<%@page import="bean.Soleil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%    pageContext.setAttribute("monEnum", Soleil.values()); %>

<jsp:useBean id="manager" class="manager.Manager" scope="session" />
<jsp:useBean id="valide" class="validation.Validation" scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
	<link rel = "stylesheet" href = "https://fonts.googleapis.com/icon?family=Material+Icons">
	<script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script> 
	<script src = "Materialize/js/materialize.min.js"></script>
	<title>Ajout données</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<c:choose>
		<c:when test="${manager.identifie}">
			<div class="row">
				<form method="post" action="ServletAjoutXML" enctype="multipart/form-data">
					<div class="file-field input-field">
						<div class="btn">
							<i class="material-icons right">insert_drive_file</i>
							<span>Fichier</span>
							<input type="file" multiple name="imageData">
						</div>
						<div class="file-path-wrapper">
							<input class="file-path validate" type="text" placeholder="Séléctionner un fichier d'archive" name="imageNom">
						</div>
						<div>
							<button class="btn waves-effect waves-light" type="submit" name="documentBoutton">Valider
				    			<i class="material-icons right">send</i>
				  			</button>  
			      		</div>
			    	</div>
			  	</form>
			</div>
  			<div class="row">
		    	<form class="col s12" method="get" action="ServletEnvoyerDonnee">
		      		<div class="row">
		        		<div class="input-field col s6">
				        	<input name="pays" placeholder="Pays" id="pays" type="text" class="validate" value="${valide.valeurs['pays']}">
				        	<label for="pays" style="color: red;">${valide.erreurs["pays"]}</label>
						</div>
			        	<div class="input-field col s6">
			        		<input name="ville" placeholder="Ville" id="ville" type="text" class="validate" value="${valide.valeurs['ville']}">
			          		<label for="ville" style="color: red;">${valide.erreurs["ville"]}</label>
			        	</div>
			        	<div class="input-field col s6">
			          		<input name="departement" placeholder="Département" id="departement" type="text" class="validate" value="${valide.valeurs['departement']}">
			          		<label for="departement" style="color: red;">${valide.erreurs["departement"]}</label>
			        	</div>
					</div>
			      	<div class="row">
			     		<div class="input-field col s3">
			          		<span>Date : </span>
			        	</div>
			        	<div class="input-field col s3">
			          		<input name="date" type="text" class="datepicker">
			        	</div>
			      	</div>
			      	<div class="row">
			        	<div class="input-field col s6">
					    	<select name="ciel">
					    		<c:forEach var="entry" items="${monEnum}">
			    					<option value="<c:out value = "${entry.getId()}"/>"  selected><c:out value = "${entry.getName()}"/></option>
								</c:forEach>
					    	</select>
						<label>Ciel</label>
				  	</div>
			        <div class="input-field col s6">
			        	<input  name="direction" placeholder="Direction du vent" id="direction" type="number" class="validate" value="${valide.valeurs['directionVent']}">
			          	<label for="direction" style="color: red;">${valide.erreurs["directionVent"]}</label>
			        </div>
			        <div class="input-field col s6">
			          	<input name="vitesse" placeholder="Vitesse du vent" id="vitesse" type="number" class="validate"  value="${valide.valeurs['vitesseVent']}">
			          	<label for="vitesse" style="color: red;">${valide.erreurs["vitesseVent"]}</label>
			        </div>
		        </div>
		        <div class="input-field col s6">
		          	<input name="temperature" placeholder="Température" id="temperature" type="number" class="validate" value="${valide.valeurs['temperature']}">
		          	<label for="temperature" style="color: red;">${valide.erreurs["temperature"]}</label>
		        </div>
		        <div class="input-field col s6">
		          	<input name="pluie" placeholder="Pluie" id="pluie" type="number" class="validate" value="${valide.valeurs['pluie']}">
		          	<label for="pluie" style="color: red;">${valide.erreurs["pluie"]}</label>
		        </div>
		      		<div class="row">
		        		<div class="input-field col s6">
		          			<button class="waves-effect waves-light btn">Envoyer</button>
		        		</div>
		      		</div>
				</form>
			</div>
  		</c:when>
		<c:otherwise>
			<jsp:include page="redirection.jsp"></jsp:include>	
		</c:otherwise>
	</c:choose> 
</body>
<script type="text/javascript">
$('.datepicker').pickadate({
    selectMonths: 3, // Creates a dropdown to control month
    selectYears: 18,
    selectDays: 12,
  });
</script>
<script type="text/javascript">
 $(document).ready(function() {
    $('select').material_select();
  });
 </script>
</html>