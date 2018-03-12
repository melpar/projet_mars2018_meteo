<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
   <head>
      <title>The Materialize Collapsible Example</title>
      <meta name = "viewport" content = "width = device-width, initial-scale = 1">      
      <link rel = "stylesheet"
         href = "https://fonts.googleapis.com/icon?family=Material+Icons">
      <link rel = "stylesheet"
         href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
      <script type = "text/javascript"
         src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>           
      <script src = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js">
      </script>
   </head>
   
   <body> 
   		<jsp:include page="header.jsp"></jsp:include>
      <h4>Simple Accordion</h4>
      <ul class = "collapsible" data-collapsible = "accordion">
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">filter_drama</i>First Section</div>
            <div class = "collapsible-body"><p>This is first section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">place</i>Second Section</div>
            <div class = "collapsible-body"><p>This is second section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">whatshot</i>Third Section</div>
            <div class = "collapsible-body"><p>This is third section.</p></div>
         </li>
      </ul>
      
      <h4>Popout Accordion</h4>
      <ul class = "collapsible popout" data-collapsible = "accordion">
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">filter_drama</i>First Section</div>
            <div class = "collapsible-body"><p>This is first section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">place</i>Second Section</div>
            <div class = "collapsible-body"><p>This is second section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">whatshot</i>Third Section</div>
            <div class = "collapsible-body"><p>This is third section.</p></div>
         </li>
      </ul>
      
      <h4>Accordion with Preselected Section</h4>
      <ul class = "collapsible" data-collapsible = "accordion">
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">filter_drama</i>First Section</div>
            <div class = "collapsible-body"><p>This is first section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header active">
               <i class = "material-icons">place</i>Second Section</div>
            <div class = "collapsible-body"><p>This is second section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">whatshot</i>Third Section</div>
            <div class = "collapsible-body"><p>This is third section.</p></div>
         </li>
      </ul>
      
      <h4>Expandables</h4>
      <ul class = "collapsible" data-collapsible = "expandable">
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">filter_drama</i>First Section</div>
            <div class = "collapsible-body"><p>This is first section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">place</i>Second Section</div>
            <div class = "collapsible-body"><p>This is second section.</p></div>
         </li>
         
         <li>
            <div class = "collapsible-header">
               <i class = "material-icons">whatshot</i>Third Section</div>
            <div class = "collapsible-body"><p>This is third section.</p></div>
         </li>
      </ul>	  		  
   </body>  
</html>