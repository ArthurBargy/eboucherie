<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <h2>Promotions</h2>
        <a href="accueil">Retour &agrave; l'Accueil</a>
        <hr>
        <c:choose>
            <c:when test="${empty promotion.id}">
                <h3>Cr&eacute;er Promotion</h3>
            </c:when>
            <c:otherwise>
                <h3>Modifier Promotion</h3>
            </c:otherwise>
        </c:choose>
        <form:form method="POST" commandName="promotion">
            <form:hidden path="id"/>
            <table class="no-zebra">
                <tr>
                    <td>Titre :</td>
                    <td><form:input path="titre" cssClass="text" /></td>
                    <td><form:errors path="titre" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Texte :</td>
                    <td><form:textarea path="texte" cssClass="text" /></td>
                    <td><form:errors path="texte" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Tarif :</td>
                    <td>
                        <form:select path="tarif.id" data-placeholder="Choisissez un tarif" cssClass="chosen">
                            <form:option value=""></form:option>
                            <c:forEach items="${tarifs}" var="tarif">
                                <form:option value="${tarif.id}">${tarif.type}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="tarif.id" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Photo :</td>
                    <td>
                        <form:select path="promotionPhoto.photo.id" data-placeholder="Choisissez une photo" cssClass="chosen">
                            <form:option value=""></form:option>
                            <c:forEach items="${photos}" var="photo">
                                <form:option value="${photo.id}">${photo.libelle}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="promotionPhoto" cssClass="error" /></td>
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
                    <td>En Ligne :</td>
                    <td><form:checkbox path="enLigne" /></td>
                    <td><form:errors path="enLigne" cssClass="error" /></td>
                </tr>
                <tr>
                    <c:choose>
                        <c:when test="${empty promotion.id}">
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

        <h3>Promotions Disponibles</h3>
        <form method="POST">
            <table>
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Texte</th>
                        <th>Type Tarif</th>
                        <th>Photo</th>
                        <th>D&eacute;but</th>
                        <th>Fin</th>
                        <th>En Ligne</th>
                        <th>Modifier</th>
                        <th>Supprimer</th>
                    </tr>
                </thead>
                <c:forEach items="${promotions}" var="promotion">
                    <tr>
                        <td>${promotion.titre}</td>
                        <td>${promotion.texte}</td>
                        <td>${promotion.tarif.type}</td>
                        <td>${promotion.promotionPhoto.photo.libelle}</td>
                        <td><fmt:formatDate value="${promotion.debut}" pattern="dd/MM/yyyy" /></td>
                        <td><fmt:formatDate value="${promotion.fin}" pattern="dd/MM/yyyy" /></td>
                        <td>${promotion.enLigne ? 'Oui' : 'Non'}</td>
                        <td>
                            <button name="update" value="${promotion.id}">Modifier</button>
                        </td>
                        <td>
                            <button name="delete" value="${promotion.id}"
                                    onclick="return window.confirm('Supprimer promotion ?');">Supprimer</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
