<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">
	<link rel="stylesheet" href="style/index.css" type="text/css" />
	<link rel="stylesheet" href="style/password.css" type="text/css" />
	<head>
		<title> Boucherie Benz - Vente en ligne	</title>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<jsp:include page="menuGauche.jsp" />
		
		<div class="pass">
		<form method="post" action="index.jsp">
<h1>Changement du mot de passe : </h1>
Ancien mot de passe :<br/> <input type="password" name="ancien"/><br/>
Nouveau mot de passe :<br/> <input type="password" name="nouveau"/><br/>
Confirmation du nouveau mot de passe :<br/><input type="password" name="confirm"/><br/>

<input type="submit" value="OK"/>
</form>
		</div>
		
		<jsp:include page="footer.jsp" />
	
	</body>
</html>