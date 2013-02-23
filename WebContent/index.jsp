<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.boucheriebenz.eboucherie.controller.*" %>
<%@ page import="com.boucheriebenz.eboucherie.service.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="stylesheet" href="style/index.css" type="text/css" />

        <link rel="stylesheet" href="slidesjs/css/slider.css"> 
        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript" src="slidesjs/js/slides.min.jquery.js"></script>
        <script type="text/javascript" src="js/accueil.js"></script>
         <link rel="stylesheet" type="text/css" href="js/jquery.autocomplete.css" />
         <script type="text/javascript" src="js/jquery.autocomplete.js"></script>
         <script type="text/javascript" src="js/recherche.js"></script> 
		<link rel="stylesheet" href="style/resultat.css" type="text/css" />
<!--          <script type="text/javascript" src="js/rechercheClient.js"></script> -->
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Boucherie Benz - Vente en ligne</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="menuGauche.jsp" />
	<c:if test="${promotions == null}">
	<% response.sendRedirect("accueil"); %>
	</c:if>
	<div class="contenuCentral">
        <a href="promotions">G&eacute;rer Promotions</a> -
        <a href="photos">G&eacute;rer Photos</a> -
        <a href="articles">G&eacute;rer Articles</a> -
        <a href="tarifs">G&eacute;rer Tarifs</a> -
        <a href="preparations">G&eacute;rer Preparations</a> -
        <a href="tvas">G&eacute;rer TVAs</a> -
        <a href="textes">G&eacute;rer Zones de Texte</a>
        <br><br>
		<div id="slides" >
			<div class="slides_container">
				<c:forEach items="${promotions}" var="promotion">
					<div>
						<h2>${promotion.titre}</h2>
						<p>${promotion.texte}</p>
						<img src="${promotion.promotionPhoto.photo.lien}">
					</div>
				</c:forEach>
			</div>
			<a href="#" class="prev"><img src="slidesjs/img/arrow-prev.png"
				alt="Prev">
			</a> <a href="#" class="next"><img src="slidesjs/img/arrow-next.png"
				alt="Next">
			</a>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>