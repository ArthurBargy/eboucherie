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
    <h2>Zones de Texte</h2>
    <a href="accueil">Retour &agrave; l'Accueil</a>
    <hr>
    <c:choose>
        <c:when test="${empty texte.nom}">
            <h3>Cr&eacute;er Zone de Texte</h3>
        </c:when>
        <c:otherwise>
            <h3>Modifier Zone de Texte</h3>
        </c:otherwise>
    </c:choose>

    <form:form method="POST" commandName="texte">
        <table class="no-zebra">
            <tr>
                <td>Nom :</td>
                <td><form:input path="nom" cssClass="text" /></td>
                <td><form:errors path="nom" cssClass="error" /></td>
            </tr>
            <tr>
                <td>Texte :</td>
                <td><form:textarea path="texte" cssClass="text" /></td>
                <td><form:errors path="texte" cssClass="error" /></td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${empty texte.nom}">
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

    <h3>Zones de Texte Disponibles</h3>
    <form method="POST">
        <table>
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Texte</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
                </tr>
            </thead>
            <c:forEach items="${textes}" var="texte">
                <tr>
                    <td>${texte.key}</td>
                    <td>${texte.value}</td>
                    <td>
                        <button name="update" value="${texte.key}">Modifier</button>
                    </td>
                    <td>
                        <button name="delete" value="${texte.key}" onclick="return window.confirm('Supprimer Texte ?');">Supprimer</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</body>
</html>
