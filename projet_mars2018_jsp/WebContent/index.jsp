<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="manager" class="manager.Manager" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel = "stylesheet" href = "https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel = "stylesheet" href = "style/datepicker.css">
<link type="text/css" rel="stylesheet" href="Materialize/css/materialize.min.css"  media="screen,projection"/>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="Materialize/js/materialize.min.js">
$('.button-collapse').sideNav();

$('.collapsible').collapsible();

$('select').material_select();</script>

<script type="text/javascript" src="chart/Chart.min.js"></script>
<title>Projet UE SOR</title>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<form class="col s12" method="get" action="ServletIndex" >
    	<div class="row">
        	<div class="input-field col s6">
				<input name="maDate" type="text" class="datepicker" value="${dateEntre}"> 
				<label for="date" style="width: 100%">
				          			<div class="row">
								      <div class="col s4">Date : </div>
								      <div class="col s8" style="color: red;">${valide.erreurs["date"]}</div>
								    </div>
				          		</label>       	
        	</div>
        	<div class="input-field col s3">
				<button class="btn waves-effect waves-light cyan lighten-2" type="submit" name="submit">Valider
			    	<i class="material-icons right">send</i>
			  	</button>       	
        	</div>
        	
      	</div>
	</form>
	  <div id="main">
    <div class="row">
      <div class="col s6">
        <div style="padding: 35px;" align="center" class="card">
          <div class="row">
            <div class="left card-title">
              <b>Température par ville</b>
            </div>
          </div>

          <canvas id="temperatures" width="400" height="200"></canvas>
        </div>
      </div>

      <div class="col s6">
        <div style="padding: 35px;" align="center" class="card">
          <div class="row">
            <div class="left card-title">
              <b>Pourcentage de pluie par ville</b>
            </div>
          </div>
          <canvas id="pluie" width="400" height="200"></canvas>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col s12">
        <div style="padding: 35px;" align="center" class="card">
          <div class="row">
            <div class="left card-title">
              <b>Vitesse du vent par ville</b>
            </div>
          </div>

          <canvas id="vent" width="400" height="200"></canvas>
        </div>
      </div>
    </div>
  </div>
</body>

<script>
	var valeursTemperature=[];
	var valeursPluie=[];
	var valeursVent=[];
	var labels=[];
	<% 
	java.util.List<String> noms = (java.util.List)request.getAttribute("noms");
	if(noms != null){
	java.util.List<Double> valeursTemperature = (java.util.List)request.getAttribute("temperature");
	java.util.List<Double> valeursPluie = (java.util.List)request.getAttribute("pluie");
	java.util.List<Double> valeursVent = (java.util.List)request.getAttribute("vent");
	for (int i=0; i<noms.size(); i++) { %>
		valeursTemperature[<%= i %>] = "<%= valeursTemperature.get(i) %>";
		valeursPluie[<%= i %>] = "<%= valeursPluie.get(i) %>";
		valeursVent[<%= i %>] = "<%= valeursVent.get(i) %>";
		labels[<%= i %>] = "<%= noms.get(i) %>";
	<% 	
		}
	}%>
	var ctx = document.getElementById("temperatures").getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: labels,
	        datasets: [{
	            data: valeursTemperature,
	            backgroundColor: [
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(54, 162, 235, 0.2)',
	                'rgba(255, 206, 86, 0.2)',
	                'rgba(75, 192, 192, 0.2)',
	                'rgba(153, 102, 255, 0.2)',
	                'rgba(255, 159, 64, 0.2)'
	            ],
	            borderColor: [
	                'rgba(255,99,132,1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero:true
	                }
	            }]
	        },
	        legend: {
	            display: false
	        }
	    }
	});
	var ctx = document.getElementById("vent").getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: labels,
	        datasets: [{
	            data: valeursVent,
	            backgroundColor: [
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(54, 162, 235, 0.2)',
	                'rgba(255, 206, 86, 0.2)',
	                'rgba(75, 192, 192, 0.2)',
	                'rgba(153, 102, 255, 0.2)',
	                'rgba(255, 159, 64, 0.2)'
	            ],
	            borderColor: [
	                'rgba(255,99,132,1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero:true
	                }
	            }]
	        },
	        legend: {
	            display: false
	        }
	    }
	});
	var ctx = document.getElementById("pluie").getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'bar',
	    data: {
	        labels: labels,
	        datasets: [{
	            data: valeursPluie,
	            backgroundColor: [
	                'rgba(255, 99, 132, 0.2)',
	                'rgba(54, 162, 235, 0.2)',
	                'rgba(255, 206, 86, 0.2)',
	                'rgba(75, 192, 192, 0.2)',
	                'rgba(153, 102, 255, 0.2)',
	                'rgba(255, 159, 64, 0.2)'
	            ],
	            borderColor: [
	                'rgba(255,99,132,1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero:true
	                }
	            }]
	        },
	        legend: {
	            display: false
	        }
	    }
	});
</script>

<script type="text/javascript">
$('.datepicker').pickadate({
    selectMonths: 3, // Creates a dropdown to control month
    selectYears: 18,
    selectDays: 12,
  });
</script>

</html>