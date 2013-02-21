<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="stylesheet" href="style/index.css" type="text/css" />
<link rel="stylesheet" href="style/connexion.css" type="text/css" />

        
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Boucherie Benz - Vente en ligne</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="menuGauche.jsp" />
	
	<!--  _________________________________Zone de connexion____________________________________________________ -->

	<div id="test">
	<h2 class="titrePosition"> Bienvenue dans votre espace client</h2>
	
		
	<form action ="infoclient.jsp" method ="post">
	
		<table class="tableSize" STYLE="background-image: url(images/fontConnexion.jpg); height:320px; width:492px"; >
		
			<tr> 
			
				<!-- Le titre de l'espace de connexion -->
					<td  class="headerPosition"> Espace Connexion </td>
				<!-- ---------------------------------- -->
			
			</tr>
			
			<tr>
			
				<!-- Le login -->
				<!-------------------------------------------- -->
					<td class="formPosition1"><input class="colorLog"  type="text" value="Login" name="Login" style="border:solid 1px black; border-radius:5px; text-align:center; box-shadow:0 0 6px;" />  
				<!-------------------------------------------- -->
			
			
				<!-- Le mot de passe -->
				<!-------------------------------------------- -->
					  <input class="colorLog" type="password" value="password" name="pswd" style="border:solid 1px black; border-radius:5px; text-align:center; box-shadow:0 0 6px;" />  </td>
				<!-------------------------------------------- -->
			
			</tr>
			
			
		<tr>
			<!-- Bouton de validation -->
			<td >
			  <input  class="subPosition1" type="submit" value="ENTRER" style="border:solid 2px gray; border-radius:5px; text-align:center; box-shadow:0 0 6px;" /> 
			</td>
			<!-- -------------------- -->
		</tr>
		
		</table>
		</form>
		
		<!-- Bouton d'accÃ¨s Ã  une page d'explication de comment devenir membre -->
		<form>
			<input class="subPosition" type="submit" value="  Comment nous rejoindre ?  " name="membre" >	
		</form>
		<!-- ----------------------------------------------------------------- -->
	</div>
<!--  _____________________________________________________________________________________________________ -->			
	


	<jsp:include page="footer.jsp" />
</body>
</html>