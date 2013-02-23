<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="style/blueprint.css">
    <link rel="stylesheet" type="text/css" href="style/boucherie.css">
    <title>eBoucherie Benz</title>
</head>
<body>
    <h2>TVAs</h2>
    <a href="accueil">Retour &agrave; l'Accueil</a>
    <hr>
    <c:choose>
        <c:when test="${empty tva.id}">
            <h3>Cr&eacute;er TVA</h3>
        </c:when>
        <c:otherwise>
            <h3>Modifier TVA</h3>
        </c:otherwise>
    </c:choose>

    <form:form method="POST" commandName="tva">
        <form:hidden path="id" />
        <table class="no-zebra">
            <tr>
                <td>Pourcentage :</td>
                <td><form:input path="pourcentage" cssClass="text" /></td>
                <td><form:errors path="pourcentage" cssClass="error" /></td>
            </tr>
            <tr>
                <td>Libelle :</td>
                <td><form:input path="libelle" cssClass="text" /></td>
                <td><form:errors path="libelle" cssClass="error" /></td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${empty tva.id}">
                        <td><button name="save">Cr&eacute;er</button></td>
                    </c:when>
                    <c:otherwise>
                        <td><button name="save">Modifier</button></td>
                    </c:otherwise>
                </c:choose>
                <td colspan="2"><button name="reset">Effacer</button></td>
            </tr>
        </table>
    </form:form>

    <hr>

    <h3>TVAs Disponibles</h3>
    <form method="POST">
        <table>
            <thead>
                <tr>
                    <th>Pourcentage</th>
                    <th>Libell&eacute;</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
                </tr>
            </thead>
            <c:forEach items="${tvas}" var="tva">
                <tr>
                    <td>${tva.pourcentage}</td>
                    <td>${tva.libelle}</td>
                    <td>
                        <button name="update" value="${tva.id}">Modifier</button>
                    </td>
                    <td>
                        <button name="delete" value="${tva.id}" onclick="return window.confirm('Supprimer TVA ?');">Supprimer</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</body>
</html>
