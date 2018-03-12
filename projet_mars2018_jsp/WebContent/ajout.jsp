<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
<title>Ajout données</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
  <div class="row">
    <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <input placeholder="Pays" id="pays" type="text" class="validate">
          <label for="pays"></label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <input  placeholder="Ville" id="ville" type="text" class="validate">
          <label for="ville"></label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <a class="waves-effect waves-light btn">Connexion</a>
        </div>
      </div>
      </div>
    </form>
    
  </div>
</body>
</html>