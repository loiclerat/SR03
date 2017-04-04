<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="beans.Jeu"%>
<%@page import="beans.TypeConsole"%>
<%@page import="dao.TypeConsoleDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Liste des Jjeux</title>
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
			<h1>Users List</h1>
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
					<a href="GestionJeux?action=sort">TITRE</a>
				</th>
				<th>URL</th>
				<th>PRIX</th>
				<th>CONSOLE</th>
				<th>ACTIONS</th>
			</tr>
				<%
					Object obj = request.getAttribute("listeJ");
					if(obj!=null){
						List<Jeu> lj = (List<Jeu>)obj;
						for(Jeu j : lj){
				%>
						<tr>
							<td><%=j.getId()%></td>
							<td><%=j.getTitre()%></td>
							<td><%=j.getUrl_image()%></td>
							<td><%=j.getPrix()%></td>
							<td><%=j.gettConsole().getNomConsole()%></td>
							<td>
								<a href="GestionJeux?action=supprimer&id=<%=j.getId()%>">Supprimer</a>
								<a href="GestionJeux?action=modifier&id=<%=j.getId()%>">Modifier</a>	
							</td>
						</tr>
				<%
						}
						
						
					}
				%>
			</table>
			
			<h3>
			<a href="GestionJeux">Ajout</a>
			/Modif</h3>
			
			<form method="post" action="GestionJeux">
					<label for="ID">ID :</label>
					<input type="text" name="ID" id="ID" value="${uModif.id}"/>
					<br />
					<label for="titre">Titre :</label>
					<input type="text" name="titre" id="titre" value="${jModif.titre}"/>
					<br />
					<label for="url_image">Url de l'image :</label>
					<input type="text" name="url_image" id="url_image" value="${jModif.url_image}"/>
					<br />
					<label for="prix">Prix :</label>
					<input type="text" name="prix" id="prix" value="${jModif.prix}"/>
					<br />
					<label for="typeConsole_id">Console :</label>
					
					<select name="typeConsole_id" id="typeConsole_id" class="form-control">
					<%
						List<TypeConsole> ltc = TypeConsoleDAO.findAll();
						for(TypeConsole j : ltc){
					%>
					  <option value=<%= j.getId() %>><%=j.getNomConsole() %></option>
					<%
						}
					%>
					</select>
					
					<br />
					<input type="hidden" name=id value="${jModif.id}"/>
					<input type="submit" value="Valider" />
			</form>
			
		</div>
	</body>
</html>