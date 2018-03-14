<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="manager" class="manager.Manager" scope="session" />
<jsp:useBean id="ident" class="java.lang.String" scope="request" />
<jsp:useBean id="mdp" class="java.lang.String" scope="request" />
<jsp:useBean id="erreur" class="java.lang.String" scope="request" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
<link rel = "stylesheet" href = "https://fonts.googleapis.com/icon?family=Material+Icons">
<title>Connexion</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	  	<div class="row">
	    	<form class="col s12" method="post" action="ServletConnexion">
	      		<div class="row">
	        		<div class="input-field col s6">
	          			<input placeholder="Identifiant" name="ident" type="text" class="validate" value="${ident}">
	          			<label for="email"></label>
	        		</div>
	      		</div>
	      		<div class="row">
	        		<div class="input-field col s6">
	          			<input  placeholder="Mot de passe" name="mdp" type="password" class="validate" value="${mdp}">
	          			<label for="password"></label>
	        		</div>
	      		</div>
	      		<div class="row">
	        		<button class="btn waves-effect waves-light" type="submit" name="submit">Connexion
			    		<i class="material-icons right">send</i>
			  		</button>
	      		</div>
	      		<div class="row" style="color: red">
	      			${erreur}
	      		</div>
			</form>
	    </div>
    </div>
</body>
</html>