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
    <h2>Preparations</h2>
    <a href="accueil">Retour &agrave; l'Accueil</a>
    <hr>
    <c:choose>
        <c:when test="${empty preparation.id}">
            <h3>Cr&eacute;er Preparation</h3>
        </c:when>
        <c:otherwise>
            <h3>Modifier Preparation</h3>
        </c:otherwise>
    </c:choose>

    <form:form method="POST" commandName="preparation">
        <form:hidden path="id" />
        <table class="no-zebra">
            <tr>
                <td>Libelle :</td>
                <td><form:input path="libelle" cssClass="text" /></td>
                <td><form:errors path="libelle" cssClass="error" /></td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${empty preparation.id}">
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

    <h3>Preparations Disponibles</h3>
    <form method="POST">
        <table>
            <thead>
                <tr>
                    <th>Libell&eacute;</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
                </tr>
            </thead>
            <c:forEach items="${preparations}" var="preparation">
                <tr>
                    <td>${preparation.libelle}</td>
                    <td>
                        <button name="update" value="${preparation.id}">Modifier</button>
                    </td>
                    <td>
                        <button name="delete" value="${preparation.id}" onclick="return window.confirm('Supprimer Preparation ?');">Supprimer</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</body>
</html>
