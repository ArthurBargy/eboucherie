<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<link rel="stylesheet" href="style/index.css" type="text/css" />
<link rel="stylesheet" href="style/animaux.css" type="text/css" />

<link rel="stylesheet" href="style/jquery-ui.css">
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/jquery-ui.button.min.js"></script>
<script src="js/jquery-ui.core.min.js"></script>
<script src="js/jquery-ui.spinner.min.js"></script>
<script src="js/jquery-ui.widget.min.js"></script>
<script src="js/jquery-ui.custom.min.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Boucherie Benz - Vente en ligne</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="menuGauche.jsp" />

	<div class="contenuCentral">
		<p class="tri">
			<label>TRIER PAR&nbsp;</label> <select name="comboTri">
				<option> </option>
				<option>Prix croissant</option>
				<option>Prix decroissant</option>
			</select>
		</p>
		

		<table class="tabAnimaux">


			<c:forEach var="animalTemp" items="${animaux}" varStatus="status">
				<c:if test="${(status.index mod 3) == 0  }">
					<tr></c:if>

				<td class="caseAnimal" onclick="this.href='index.jsp'"><a
					class="texte" href="ficheanimal?id=${animalTemp.id}"> <img src="${animalTemp.photo1}"
						width="160" height="120" border="0" /></br> ${animalTemp.type} ${animalTemp.race} </a></br>
					<p class="prix">${animalTemp.prix_min} €</p>

					<p class="banniere">
						<a class="boutonListe" href="#"><img
							src="images/BoutonAjoutListeUp.png" border="0" width="65"
							height="25"
							onmouseover="this.src='images/BoutonAjoutListeDown.png'"
							onmouseout="this.src='images/BoutonAjoutListeUp.png'" /> </a> <input
							id="spinner${status.index}" value="1">

						<script>
							$(function() {
								$("#spinner${status.index}").spinner({
									spin : function(event, ui) {
										if (ui.value > 10) {
											$(this).spinner("value", 0);
											return false;
										} else if (ui.value < 0) {
											$(this).spinner("value", 10);
											return false;
										}
									}
								});
							});
						</script>


						<a class="boutonPanier" href="#"><img
							src="images/Bouton_Panier_Up.png" border="0" width="35"
							height="25"
							onmouseover="this.src='images/Bouton_Panier_Down.png'"
							onmouseout="this.src='images/Bouton_Panier_Up.png'" /> </a>
					</p>
				</td>

				<c:if test="${((status.index+1) mod 3) == 0 or status.last }">
					</tr>
				</c:if>

			</c:forEach>
		</table>
		</br>
		<%--For displaying Previous link except for the 1st page --%>
		<c:if test="${pageActuelle != 1}">
			<a class="pagination" href="animaux?page=${pageActuelle - 1}">&lsaquo;
				Pr&eacute;c&eacute;dent</a>

		</c:if>

		<%--For displaying Page numbers. The when condition does not display a link for the current page--%>

		<c:forEach begin="1" end="${nombrePages}" var="i">
			<c:choose>
				<c:when test="${pageActuelle eq i}">
					<note class="pageCourante">${i}</note>
				</c:when>
				<c:otherwise>
					<a class="pagination" href="animaux?page=${i}">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>


		<%--For displaying Next link --%>
		<c:if test="${pageActuelle lt nombrePages}">
			<a class="pagination" href="animaux?page=${pageActuelle + 1}">Suivant
				&rsaquo;</a>

		</c:if>
		</div>

	<jsp:include page="footer.jsp" />
</body>
</html>