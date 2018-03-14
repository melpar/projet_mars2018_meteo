<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
<li class="collection-item">Humidité : ${archive.donnee.pluie}%</li>
						<li class="collection-item">Direction de vent : ${archive.donnee.directionVent}°</li>
						<li class="collection-item">Vitesse du vent : ${archive.donnee.vitesseVent} km/h</li>
						<li class="collection-item">Ciel : ${archive.donnee.soleil.name}</li>
						<li class="collection-item">Température : ${archive.donnee.temperature}°C</li>
</body>
</html>