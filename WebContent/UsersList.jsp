<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="beans.Utilisateur"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Game-Shop : Users</title>
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
			
			<table class="table">
			<tr>
				<th>ID</th>
				<th>
					<a href="GestionUsers?action=sort">NOM</a>
				</th>
				<th>PRENOM</th>
				<th>MAIL</th>
				<th>LOGIN</th>
				<th>PASSWORD</th>
				<th>ADRESSE</th>
				<th>CODE POSTAL</th>
				<th>VILLE</th>
				<th>ACTIONS</th>
			</tr>
				<%
					Object obj = request.getAttribute("listeU");
					if(obj!=null){
						List<Utilisateur> lu = (List<Utilisateur>)obj;
						for(Utilisateur u : lu){
				%>
						<tr>
							<td><%=u.getId()%></td>
							<td><%=u.getNom()%></td>
							<td><%=u.getPrenom()%></td>
							<td><%=u.getMail()%></td>
							<td><%=u.getLogin()%></td>
							<td><%=u.getPassword()%></td>
							<td><%=u.getAdresse()%></td>
							<td><%=u.getCpostal()%></td>
							<td><%=u.getVille()%></td>
							<td>
								<a href="GestionUsers?action=supprimer&id=<%=u.getId()%>">Supprimer</a>
								<a href="GestionUsers?action=modifier&id=<%=u.getId()%>">Modifier</a>	
							</td>
						</tr>
				<%
						}
						
						
					}
				%>
			</table>
			
			<h3>
			<a href="GestionUsers">Ajout</a>
			/Modif</h3>
			
			<form method="post" action="GestionUsers">
					<label for="ID">ID :</label>
					<input type="text" name="ID" id="ID" value="${uModif.id}"/>
					<br />
					<label for="nom">Nom :</label>
					<input type="text" name="nom" id="nom" value="${uModif.nom}"/>
					<br />
					<label for="prenom">Prenom :</label>
					<input type="text" name="prenom" id="prenom" value="${uModif.prenom}"/>
					<br />
					<label for="mail">Mail :</label>
					<input type="text" name="mail" id="mail" value="${uModif.mail}"/>
					<br />
					<label for="login">Login :</label>
					<input type="text" name="login" id="login" value="${uModif.login}"/>
					<br />
					<label for="password">Password :</label>
					<input type="text" name="password" id="password" value="${uModif.password}"/>
					<br />
					<label for="adresse">Adresse :</label>
					<input type="text" name="adresse" id="adresse" value="${uModif.adresse}"/>
					<br />
					<label for="cpostal">Code Postal :</label>
					<input type="text" name="cpostal" id="cpostal" value="${uModif.cpostal}"/>
					<br />
					<label for="ville">Ville :</label>
					<input type="text" name="ville" id="ville" value="${uModif.ville}"/>
					<br />
					<input type="hidden" name=id value="${uModif.id}"/>
					<input type="submit" value="Valider" />
			</form>
			
		</div>
	</body>
</html>