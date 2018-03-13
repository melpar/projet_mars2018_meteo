<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lst" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="erreur" class="java.lang.String" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel = "stylesheet" href = "https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
	<script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>           
	<script src = "Materialize/js/materialize.min.js"></script>
	<title>Visualiser jours</title>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<form class="col s12" method="get" action="ServletAjoutPhoto" >
      <div class="row">
      	<div class="input-field col s3">
			Date :        	
        </div>
        <div class="input-field col s6">
			<input name="maDate" type="text" class="datepicker" value="${dateEntre}"> 
			<label for="erreur" style="color: red;">${erreur}</label>       	
        </div>
        <div class="input-field col s3">
			<button class="btn waves-effect waves-light" type="submit" name="submit">Valider
		    	<i class="material-icons right">send</i>
		  	</button>      	
        </div>
        
      </div>
    </form>
     <ul class="collapsible" data-collapsible="expandable">
        <c:forEach var="archive" items="${lst}">
	        <li>
				<div class="collapsible-header"><i class="material-icons">place</i>${archive.lieu.pays}, ${archive.lieu.departement}, ${archive.lieu.ville}</div>
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
									<ul class="collection">
										<li class="collection-item">
										<form method="post" action="ServletAjoutPhotoEnvoyer" enctype="multipart/form-data">
										    <div class="file-field input-field">
										      <div class="btn">
										      	<i class="material-icons right">add_a_photo</i>
										        <span>Fichiers</span>
										        <input type="file" multiple name="imageData">
										      </div>
										      <div class="file-path-wrapper">
										        <input class="file-path validate" type="text" placeholder="Envoyer une ou plusieurs photos" name="imageNom">
										      </div>
										      <div>
										        <button class="btn waves-effect waves-light" type="submit" name="imageBoutton" value="${archive.id}%">Envoyer
											    	<i class="material-icons right">send</i>
											  	</button>  
										      </div>
										    </div>
										  </form>
										</li>
									</ul>
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
</script>
<script type="text/javascript"> 
	 function lunch(ApplicationName) 
	{
		w = new ActiveXObject("WScript.Shell");
		w.run(ApplicationName);
		return true;
	}
 </script>


</html>