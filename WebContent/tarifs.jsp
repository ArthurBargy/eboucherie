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
        <h2>Tarifs</h2>
        <a href="accueil">Retour &agrave; l'Accueil</a>
        <hr>
        <c:choose>
            <c:when test="${empty tarif.id}">
                <h3>Cr&eacute;er Tarif</h3>
            </c:when>
            <c:otherwise>
                <h3>Modifier Tarif</h3>
            </c:otherwise>
        </c:choose>
        <form:form method="POST" commandName="tarif">
            <form:hidden path="id"/>
            <table class="no-zebra">
                <tr>
                    <td>Article :</td>
                    <td>
                        <form:select path="article.id" data-placeholder="Choisissez un article" cssClass="chosen exclusive">
                            <form:option value=""></form:option>
                            <c:forEach items="${articles}" var="article">
                                <form:option value="${article.id}">${article.libelle}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="article.id" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Preparation :</td>
                    <td>
                        <form:select path="preparation.id" data-placeholder="Choisissez une preparation" cssClass="chosen exclusive">
                            <form:option value=""></form:option>
                            <c:forEach items="${preparations}" var="preparation">
                                <form:option value="${preparation.id}">${preparation.libelle}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="preparation.id" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>TVA :</td>
                    <td>
                        <form:select path="tva.id" data-placeholder="Choisissez une TVA" cssClass="chosen">
                            <form:option value=""></form:option>
                            <c:forEach items="${tvas}" var="tva">
                                <form:option value="${tva.id}">${tva.libelle}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="tva.id" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Type :</td>
                    <td><form:input path="type" cssClass="text" /></td>
                    <td><form:errors path="type" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Groupe :</td>
                    <td><form:input path="groupe" cssClass="text" /></td>
                    <td><form:errors path="groupe" cssClass="error" /></td>
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
                    <td>Quantité minimum :</td>
                    <td><form:input path="minimum" cssClass="text" /></td>
                    <td><form:errors path="minimum" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Quantité maximum :</td>
                    <td><form:input path="maximum" cssClass="text" /></td>
                    <td><form:errors path="maximum" cssClass="error" /></td>
                </tr>
                <tr>
                   <c:choose>
                        <c:when test="${empty tarif.id}">
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

        <h3>Tarifs Disponibles</h3>
        <form method="POST">
            <table>
                <thead>
                    <tr>
                        <th>Article</th>
                        <th>Preparation</th>
                        <th>TVA</th>
                        <th>Type</th>
                        <th>Groupe</th>
                        <th>D&eacute;but</th>
                        <th>Fin</th>
                        <th>Minimum</th>
                        <th>Maximum</th>
                        <th>Modifier</th>
                        <th>Supprimer</th>
                    </tr>
                </thead>
                <c:forEach items="${tarifs}" var="tarif">
                    <tr>
                        <td>${tarif.article.libelle}</td>
                        <td>${tarif.preparation.libelle}</td>
                        <td>${tarif.tva.libelle}</td>
                        <td>${tarif.type}</td>
                        <td>${tarif.groupe}</td>
                        <td><fmt:formatDate value="${tarif.debut}" pattern="dd/MM/yyyy" /></td>
                        <td><fmt:formatDate value="${tarif.fin}" pattern="dd/MM/yyyy" /></td>
                        <td>${tarif.minimum}</td>
                        <td>${tarif.maximum}</td>
                        <td>
                            <button name="update" value="${tarif.id}">Modifier</button>
                        </td>
                        <td>
                            <button name="delete" value="${tarif.id}"
                                    onclick="return window.confirm('Supprimer tarif ?');">Supprimer</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
