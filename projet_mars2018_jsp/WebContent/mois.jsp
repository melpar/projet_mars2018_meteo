<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="manager" class="manager.Manager" scope="session" />
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
	<div class="container">
		<form class="col s12" method="get" action="ServletListerMois" >
	      	<div class="row">
	      		<div class="input-field col s3">
					Date :        	
	        	</div>
		        <div class="input-field col s3">
				    <select name="mois">
				      	<option value="1"  selected>Janvier</option>
				      	<option value="2">Février</option>
				      	<option value="3">Mars</option>
				      	<option value="4">Aril</option>
				      	<option value="5">Mai</option>
				      	<option value="6">Juin</option>
				      	<option value="7">Juillet</option>
				      	<option value="8">Aout</option>
				      	<option value="9">Septembre</option>
				      	<option value="10">Octobre</option>
				      	<option value="11">Novembre</option>
				      	<option value="12">Decembre</option>
				    </select>
				    <label>Mois</label>
			  	</div>
		        <div class="input-field col s3">
				    <select name="annee">
			    		<c:forEach var = "i" begin = "2010" end = "2020">
		        			 Item <c:out value = "${i}"/><p>
		        			 <option value="<c:out value = "${i}"/>"  selected><c:out value = "${i}"/></option>
		      			</c:forEach>
				    </select>
				    <label>Annee</label>
			  	</div>
			  	<div class="input-field col s3">
		       	<button class="btn waves-effect waves-light cyan lighten-2" type="submit" name="submit">Valider
				    <i class="material-icons right">send</i>
				</button>
				
				</div>
			</div>
		</form>
		<c:if test="${!lst.isEmpty()}">
					<form method="get" action="ServletListerMoisTelecharger" >
					<%
					 	session.setAttribute("lst",request.getAttribute("lst"));
					%>
						<input type="hidden" name="listeArchive" id="listeArchive" value="${lst}"/>
						<button class="btn waves-effect waves-light cyan lighten-2" type="submit" name="submit" >Télécharger
						    <i class="material-icons right">file_download</i>
						</button>
					</form>
				</c:if>
	    <ul class="collapsible" data-collapsible="expandable">
	    	<c:forEach var="archive" items="${lst}">
		    	<li>
		    		<%
		    		java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
		    		bean.ArchiveMeteo archive = (bean.ArchiveMeteo) pageContext.getAttribute("archive");
		    		String reportDate = df.format(archive.getDate());
		    		pageContext.setAttribute("d", reportDate);
		    		%>
					<div class="collapsible-header"><i class="material-icons">place</i>${archive.lieu.ville}<br>${d}</div>
					<div class="collapsible-body">
						<ul class="collection">
							<c:set var="archive" value="${archive}" scope="request"/>
							<jsp:include page="gestionInformations/informations.jsp" >
								<jsp:param name="archive" value="${archive}"/>
							</jsp:include>		
							<jsp:include page="gestionPhotos/listePhotos.jsp" >
								<jsp:param name="archive" value="${archive}"/>
							</jsp:include>
						</ul>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
<script type="text/javascript">
 $(document).ready(function() {
    $('select').material_select();
  });
 </script>
</html>


