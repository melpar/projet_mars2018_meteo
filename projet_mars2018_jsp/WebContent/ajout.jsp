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
	<div class="container">
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
				<div class="divider"></div>
				
	  			<div class="row">
			    	<form class="col s12" method="get" action="ServletEnvoyerDonnee">
			      		<div class="row">
			      			<p class="flow-text">Lieu : </p>
			        		<div class="input-field col s6">
					        	<input name="pays" placeholder="Pays" id="pays" type="text" class="validate" value="${valide.valeurs['pays']}">
					        	<label for="pays">
						        	<div class="row">
								      <div class="col s3">Pays : </div>
								      <div class="col s8" style="color: red;">${valide.erreurs["pays"]}</div>
								    </div>
							    </label>
							</div>
				        	<div class="input-field col s6">
				        		<input name="ville" placeholder="Ville" id="ville" type="text" class="validate" value="${valide.valeurs['ville']}">
				          		<label for="ville">
				          			<div class="row">
								      <div class="col s3">Ville : </div>
								      <div class="col s8" style="color: red;">${valide.erreurs["ville"]}</div>
								    </div>
				          		</label>
				        	</div>
				        	<div class="input-field col s6">
				          		<input name="departement" placeholder="Département" id="departement" type="text" class="validate" value="${valide.valeurs['departement']}">
				          		<label for="departement">
				          			<div class="row">
								      <div class="col s4">Département : </div>
								      <div class="col s8" style="color: red;">${valide.erreurs["departement"]}</div>
								    </div>
				          		</label>
				        	</div>
						</div>
				      	<div class="row">
				      		<p class="flow-text">Date : </p>
				     		<div class="input-field col s3">
				          		<span>Date : </span>
				        	</div>
				        	<div class="input-field col s3">
				          		<input name="date" type="text" class="datepicker">
				        	</div>
				      	</div>
				      	<div class="row">
				      		<p class="flow-text">Données météo : </p>
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
				          	<label for="direction">
				          		<div class="row">
									<div class="col s4">Direction du vent : </div>
								    <div class="col s8" style="color: red;">${valide.erreurs["directionVent"]}</div>
								</div>
				          	</label>
				        </div>
				        <div class="input-field col s6">
				          	<input name="vitesse" placeholder="Vitesse du vent" id="vitesse" type="number" class="validate"  value="${valide.valeurs['vitesseVent']}">
				          	<label for="vitesse">
				          		<div class="row">
									<div class="col s4">Vitesse du vent : </div>
								    <div class="col s8" style="color: red;">${valide.erreurs["vitesseVent"]}</div>
								</div>
				          	</label>
				        </div>
			        </div>
			        <div class="input-field col s6">
			          	<input name="temperature" placeholder="Température" id="temperature" type="number" class="validate" value="${valide.valeurs['temperature']}">
			          	<label for="temperature">
			          		<div class="row">
								<div class="col s4">Température : </div>
								<div class="col s8" style="color: red;">${valide.erreurs["temperature"]}</div>
							</div>
			          	</label>
			        </div>
			        <div class="input-field col s6">
			          	<input name="pluie" placeholder="Pluie" id="pluie" type="number" class="validate" value="${valide.valeurs['pluie']}">
			          	<label for="pluie">
			          		<div class="row">
								<div class="col s5">Pourcentage de pluie : </div>
								<div class="col s7" style="color: red;">${valide.erreurs["pluie"]}</div>
							</div>
			          	</label>
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
	</div>
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