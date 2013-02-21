<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="stylesheet" href="style/index.css" type="text/css" />

        <link rel="stylesheet" href="slidesjs/css/slider.css"> 
        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript" src="slidesjs/js/slides.min.jquery.js"></script>
        <script type="text/javascript" src="js/accueil.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Boucherie Benz - Vente en ligne</title>
</head>
<body>
	

	<jsp:include page="header.jsp" />
	<jsp:include page="menuGauche.jsp" />
	

	<div class="contenuCentral">
	<!--	<c:if test="${ empty client}">
			<h2>Espace client en construction</h2>
			<form method="post" action="infos">
				<input type="submit" value="Infos">
			</form>
		</c:if> -->

		<c:if test="${  empty client}">
			<div style="text-align:center;">
				<div id ="titre">Vos informations</div>
				<table id="infos" border="1px" align="center">
					<tr>
						<td>Prénom</td><td><!-- ${ client.getPrenom()}  -->     Martial</td>
					</tr>
					<tr>
						<td>Nom</td><td><!--${client.getNom()}-->     Durand</td>
					</tr>
					<tr>
						<td>Age</td><td><!--${client.getAge()}-->     32</td>
					</tr>
					<tr>
						<td>Ville</td><td><!--${client.getVille()}-->     Enfer</td>
					</tr>
		
				</table>
			</div>
			
			<div style="text-align:center; margin-top:15px">
				<form method="post" action="password.jsp">
					<input type="submit" value="Changer votre mot de passe">
				</form>
			</div>
			
		<!-- 	<div style="text-align:center; margin-top:15px">
				<form method="post" action="infos">
					<input type="submit" value="Client aléatoire">
				</form>
			</div> -->
		</c:if>
	</div>

<jsp:include page="footer.jsp" />
</body>
</html>