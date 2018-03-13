<%@page import="bean.Soleil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%    pageContext.setAttribute("monEnum", Soleil.values()); %>

<jsp:useBean id="valide" class="validation.Validation" scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
<script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script> 
<script src = "Materialize/js/materialize.min.js"></script>
<title>Ajout données</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
  <div class="row">
    <form class="col s12" method="get" action="ServletEnvoyerDonnee">
      <div class="row">
        <div class="input-field col s6">
          <input name="pays" placeholder="Pays" id="pays" type="text" class="validate">
          <label for="pays"></label>
        </div>
        <div class="input-field col s6">
          <input name="ville" placeholder="Ville" id="ville" type="text" class="validate">
          <label for="ville"></label>
        </div>
        <div class="input-field col s6">
          <input name="departement" placeholder="Département" id="departement" type="text" class="validate">
          <label for="departement"></label>
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
          <input  name="direction" placeholder="Diresction du vent" id="direction" type="number" class="validate">
          <label for="direction">${valide.valeurs[direction]}</label>
        </div>
        <div class="input-field col s6">
          <input name="vitesse" placeholder="Vitesse du vent" id="vitesse" type="number" class="validate">
          <label for="vitesse"></label>
        </div>
        </div>
        <div class="input-field col s6">
          <input name="temperature" placeholder="Température" id="temperature" type="number" class="validate">
          <label for="temperature"></label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <button class="waves-effect waves-light btn">Envoyer</button>
        </div>
      </div>
      </div>
    </form>
    <p style="color: red;">${erreur}</p>
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