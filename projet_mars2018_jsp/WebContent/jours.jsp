<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="lst" class="java.util.ArrayList" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="Materialize/js/materialize.min.js"></script>
<title>Insert title here</title>
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
<!-- <input type="date" class="datepicker"> -->	
	<form class="col s12" action="ServletListerJour" >
      <div class="row">
      <div class="input-field col s3">
			Date :        	
        </div>
        <div class="input-field col s6">
			<input name="date" type="date" class="datepicker">        	
        </div>
        <div class="input-field col s3">
			<input name="submit" type="submit" value="Valider"/>       	
        </div>
      </div>
    </form>
    <ul class="collection with-header">
        <c:forEach var="archive" items="${lst}">
		<li class="collection-header"><h4>${archive.lieu.ville}</h4></li>
		<li class="collection-item">Humidité : ${archive.donnee.pluie}%</li>
		<li class="collection-item">Direction de vent : ${archive.donnee.directionVent}°</li>
		<li class="collection-item">Vitesse du vent : ${archive.donnee.vitesseVent} km/h</li>
		<li class="collection-item">${archive.donnee.soleil.name}</li>
		<li class="collection-item">${archive.donnee.temperature}°C</li>
		</c:forEach>
      </ul>
</body>
<script type="text/javascript">
$('.datepicker').pickadate({
    selectMonths: true, // Creates a dropdown to control month
    selectYears: 15, // Creates a dropdown of 15 years to control year,
  });
</script>
</html>


