<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
  <div class="row">
    <form class="col s12">
      <div class="row">
        <div class="input-field col s6">
          <input placeholder="Email" id="email" type="text" class="validate">
          <label for="email"></label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s6">
          <input  placeholder="Mot de passe" id="password" type="password" class="validate">
          <label for="password"></label>
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