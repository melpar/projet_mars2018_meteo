<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:useBean id="manager" class="manager.Manager" scope="session" />
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
<title>Menu</title>
</head>
<body>
	<nav style="background-color: #2979ff ">
    <div class="nav-wrapper">
      <a href="index.jsp" class="brand-logo">&nbsp; Projet UE SOR</a>
      <ul id="nav-mobile" class="right hide-on-med-and-down">
        <li><a href="jours.jsp">Visualiser jours</a></li>
        <li><a href="mois.jsp">Visualiser mois</a></li>
  
        <c:choose>
		    <c:when test="${manager.identifie}">
		    	<li><a href="ajout.jsp">Ajouter données</a></li>
		    	<li><a href="ajoutPhotos.jsp">Ajouter des photos</a></li>
		    	<li>
		    		<form name="myform" action="ServletDeconnexion">
    					<a href="javascript: submitform()">Déconnexion</a>
					</form> 
					<SCRIPT language="JavaScript">
					function submitform()
					{
					  document.myform.submit();
					}
					</SCRIPT>
				</li>
		    </c:when>
		    <c:otherwise>
		         <li><a href="connexion.jsp">Connexion</a></li>
		    </c:otherwise>
		</c:choose>     
      </ul>
    </div>
  </nav>
</body>
</html>