<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="beans.TypeConsole"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Game-Shop : Consoles</title>
		<!--  BOOTSTRAP INTEGRATION  -->
			
			<!-- Latest compiled and minified CSS -->
			<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
			
			<!-- Latest compiled and minified JavaScript -->
			<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		
	</head>
	<body>
	
		<jsp:include page="entete.jsp">
			<jsp:param value="Florian" name="username"/>
		</jsp:include>
		<div class="container">
			<h1>Types de consoles</h1>
			<!-- 
			<h4>Trier par</h4>
			
			Tri :
			<form  class="form-inline" method="post" action="GestionUsers">
				<div class="radio">
				    <label><input name="sortType" type="radio" value="1"/> nom</label>
				    <label><input name="sortType" type="radio" value="2"/> login</label>
				    <input type="hidden" name="action" value="sort" />
				</div>
				<button type="submit" class="btn btn-default">Trier</button>
			</form>
			 -->
			<table class="table">
			<tr>
				<th>ID</th>
				<th>
					<a href="GestionTypeConsole?action=sort">NOM</a>
				</th>
				<th>ACTIONS</th>
			</tr>
				<%
					Object obj = request.getAttribute("listeTC");
					if(obj!=null){
						List<TypeConsole> ltc = (List<TypeConsole>)obj;
						for(TypeConsole tc : ltc){
				%>
						<tr>
							<td><%=tc.getId()%></td>
							<td><%=tc.getNomConsole()%></td>
							<td>
								<a href="GestionTypeConsole?action=supprimer&id=<%=tc.getId()%>">Supprimer</a>
								<a href="GestionTypeConsole?action=modifier&id=<%=tc.getId()%>">Modifier</a>	
							</td>
						</tr>
				<%
						}
						
						
					}
				%>
			</table>
			
			<h3>
			<a href="GestionTypeConsole">Ajout</a>
			/Modif</h3>
			
			<form method="post" action="GestionTypeConsole">
					<label for="ID">ID :</label>
					<input type="text" name="ID" id="ID" value="${tcModif.id}"/>
					<br />
					<label for="nomConsole">Nom :</label>
					<input type="text" name="nomConsole" id="nomConsole" value="${tcModif.nomConsole}"/>
					<br />
					<input type="hidden" name=id value="${tcModif.id}"/>
					<input type="submit" value="Valider" />
			</form>
			
		</div>
	</body>
</html>