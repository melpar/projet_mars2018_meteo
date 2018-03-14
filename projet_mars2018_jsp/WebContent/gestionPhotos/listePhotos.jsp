<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<ul class="collapsible" data-collapsible="expandable">
							<li>
							<div class="collapsible-header"><i class="material-icons">photo_library</i>Photo</div>
								<div class="collapsible-body">					
									<jsp:include page="photos.jsp" >
										<jsp:param name="archive" value="${archive}"/>
									</jsp:include>
								</div>
							</li>
						</ul>
</body>
</html>