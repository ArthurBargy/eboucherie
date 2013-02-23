<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style/index.css" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/accueil.js"></script>
<link rel="stylesheet" type="text/css" href="js/jquery.autocomplete.css" />
<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/recherche.js"></script>
<link rel="stylesheet" href="style/resultat.css" type="text/css" />
<title>Boucherie Benz</title>
</head>
<body>
    <jsp:include page="header.jsp" />
    <jsp:include page="menuGauche.jsp" />

    <div class="contenuCentral">
        Historique : ${textes['historique']}
        <hr>
        Modalit&eacute; d'Inscription : ${textes['inscription']}
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>