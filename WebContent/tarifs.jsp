<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style/blueprint.css">
        <link rel="stylesheet" type="text/css" href="style/boucherie.css">
        <link rel="stylesheet" type="text/css" href="style/chosen.css">
        <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
        <script type="text/javascript" src="js/chosen.jquery.min.js"></script>
        <script type="text/javascript" src="js/tarifs.js"></script>
        <title>eBoucherie Benz</title>
    </head>
    <body>
        <h2>Tarifs</h2>
        <a href="accueil">Retour à l'Accueil</a>
        <c:choose>
            <c:when test="${empty tarif.id}">
                <h3>Cr&eacute;er Tarif</h3>
            </c:when>
            <c:otherwise>
                <h3>Modifier Tarif</h3>
            </c:otherwise>
        </c:choose>
       
        <h3>Ajouter tarifs</h3>
        <form:form method="POST" commandName="tarif">
            <form:hidden path="id"/>
            <table class="no-zebra">
                <tr>
                    <td>Tarif1 :</td>
                    <td><form:input path="tarif1" /></td>
                    <td><form:errors path="tarif1" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Tarif2 :</td>
                    <td><form:input path="tarif2"/></td>
                    <td><form:errors path="tarif2" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Tarif3 :</td>
                    <td><form:input path="tarif3"/></td>
                    <td><form:errors path="tarif3" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Tarif4 :</td>
                    <td><form:input path="tarif4"/></td>
                    <td><form:errors path="tarif4" cssClass="error" /></td>
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
                        <th>Tarif1</th>
                        <th>Tarif2</th>
                        <th>Tarif3</th>
                        <th>Tarif4</th>
                        <th>Modifier</th>
                        <th>Supprimer</th>
                    </tr>
                </thead>
                <c:forEach items="${tarifs}" var="tarif">
                    <tr>
                        <td>${tarif.tarif1}</td>
                        <td>${tarif.tarif2}</td>
                        <td>${tarif.tarif3}</td>
                        <td>${tarif.tarif4}</td>
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
