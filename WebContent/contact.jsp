<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="stylesheet" href="style/index.css" type="text/css" />
<link rel="stylesheet" href="style/contact.css" type="text/css" />

        
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Boucherie Benz - Vente en ligne</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="menuGauche.jsp" />
	<div class="contenuCentral">
		<!--  _________________________________Zone de contact____________________________________________________ -->

		<h2 class="titrePosition">Bienvenue dans votre page de contact
			administrateur</h2>

		<form>
			<table class="headerPosition">




				<tr class="formPosition1">

					<!-- Le login -->
					<!-------------------------------------------- -->
					<td><INPUT class="colorlog" TYPE="texte" NAME="Nom1"
						value="Nom" SIZE="20" />
				</tr>
				<br />
				<!-------------------------------------------- -->
				<tr class="formPosition1">
					<td><INPUT class="colorlog" TYPE="texte" NAME="Prenom1"
						value="Prenom" SIZE="20" />
				</tr>
				<br />
				<tr class="formPosition1">
					<td><INPUT class="colorlog" TYPE="email" NAME="email1"
						value="email" />
				</tr>
				<br />
				<!-- Le mot de passe -->
				<!-------------------------------------------- -->
				<tr class="formPosition1">
					<td><label for="message"> Postez votre message : </label>
				</tr>
				<br />
				<tr class="formPosition1">
					<td><textarea name="message" id="message" rows="10" cols="50"> </textarea>
				</tr>
				<br />

				</td>
				<!-------------------------------------------- -->
				</form>
			</table>


			<br />
			<table class="headerPosition1">
				<tr>
					<td><input type="submit" value="ENTRER"
						style="border: solid 2px gray; border-radius: 5px; text-align: center; box-shadow: 0 0 6px;" />
					</td>



					<td><input type="reset" value="ANNULER"
						style="border: solid 2px gray; border-radius: 5px; text-align: center; box-shadow: 0 0 6px;" />
					</td>
				</tr>

				

			</table>




			

			<!--  _____________________________________________________________________________________________________ -->
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>