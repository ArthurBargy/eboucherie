<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
     <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style/blueprint.css">
        <link rel="stylesheet" type="text/css" href="style/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="style/boucherie.css">
        <link rel="stylesheet" type="text/css" href="style/chosen.css">
        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui.custom.min.js"></script>
        <script type="text/javascript" src="js/jquery.ui.datepicker-fr.js"></script>
        <script type="text/javascript" src="js/chosen.jquery.min.js"></script>
        <script type="text/javascript" src="js/commun.js"></script>
        <title>eBoucherie Benz</title>
    </head>
     <body>
        <h2>Articles</h2>
        <a href="accueil">Retour &agrave; l'Accueil</a>
        <hr>
        <c:choose>
            <c:when test="${empty article.id}">
                <h3>Cr&eacute;er Article</h3>
            </c:when>
            <c:otherwise>
                <h3>Modifier Article</h3>
            </c:otherwise>
        </c:choose>
        <form:form method="POST" commandName="article">
            <form:hidden path="id"/>
            <table class="no-zebra">
                <tr>
                    <td>Libell&eacute; :</td>
                    <td><form:input path="libelle" cssClass="text" /></td>
                    <td><form:errors path="libelle" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Type :</td>
                    <td><form:input path="type" cssClass="text" /></td>
                    <td><form:errors path="type" cssClass="error" /></td>
                </tr>
                 <tr>
                    <td>Race :</td>
                    <td><form:input path="race" cssClass="text" /></td>
                    <td><form:errors path="race" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Description :</td>
                    <td><form:input path="description" cssClass="text" /></td>
                    <td><form:errors path="description" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Date d&eacute;but :</td>
                    <td><form:input path="debut" readonly="true" cssClass="text date" /></td>
                    <td><form:errors path="debut" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Date fin :</td>
                    <td><form:input path="fin" readonly="true" cssClass="text date" /></td>
                    <td><form:errors path="fin" cssClass="error" /></td>
                </tr>
                <tr>
                <tr>
                    <td>Photo :</td>
                    <td>
                        <form:select path="articlePhoto.photo.id" data-placeholder="Choisissez une photo" class="chosen">
                            <form:option value=""></form:option>
                            <c:forEach items="${photos}" var="photo">
                                <form:option value="${photo.id}">${photo.libelle}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="articlePhoto" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>En Ligne :</td>
                    <td><form:checkbox path="enLigne" /></td>
                    <td><form:errors path="enLigne" cssClass="error" /></td>
                </tr>
                <tr>
                    <c:choose>
                        <c:when test="${empty article.id}">
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

        <h3>Articles Disponibles</h3>
        <form method="POST">
            <table>
                <thead>
                    <tr>
                        <th>Libell&eacute;</th>
                        <th>Type</th>
                        <th>Race</th>
                        <th>Description</th>
                        <th>Date d&eacute;but</th>
                        <th>Date fin</th>
                        <th>Photo</th>
                        <th>En ligne</th>
                        <th>Modifier</th>
                        <th>Supprimer</th>
                    </tr>
                </thead>
                <c:forEach items="${articles}" var="article">
                    <tr>
                        <td>${article.libelle}</td>
                        <td>${article.type}</td>
                        <td>${article.race}</td>
                        <td>${article.description}</td>
                        <td>${article.debut}</td>
                        <td>${article.fin}</td>
                        <td>${article.articlePhoto.photo.libelle}</td>
                        <td>${article.enLigne ? 'Oui' : 'Non'}</td>
                        <td>
                            <button name="update" value="${article.id}">Modifier</button>
                        </td>
                        <td>
                            <button name="delete" value="${article.id}"
                                    onclick="return window.confirm('Supprimer article ?');">Supprimer</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
