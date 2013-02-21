<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <h2>Photos</h2>
        <a href="accueil">Retour à l'Accueil</a>
        <h3>Uploader une photo</h3>
        <form:form method="POST" commandName="fichierPhoto" enctype="multipart/form-data">
            <form:hidden path="id"/>
            <table class="no-zebra">
                <tr>
                    <td>Libell&eacute; :</td>
                    <td><form:input path="libelle" /></td>
                    <td><form:errors path="libelle" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>Fichier :</td>
                    <td><form:input path="fichier" type="file" /></td>
                    <td><form:errors path="fichier" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><button name="save">Uploader</button></td>
                    <td colspan="2"><button name="reset">Effacer</button></td>
                </tr>
            </table>
        </form:form>

        <hr>
        <h3>Photos Disponibles</h3>
        <form method="POST">
            <table>
                <thead>
                    <tr>
                        <th>Libell&eacute;</th>
                        <th>Lien</th>
                        <th>Supprimer</th>
                    </tr>
                </thead>
                <c:forEach items="${photos}" var="photo">
                    <tr>
                        <td>${photo.libelle}</td>
                        <td>${photo.lien}</td>
                        <td>
                            <button name="delete" value="${photo.id}"
                                    onclick="return window.confirm('Supprimer photo ?');">Supprimer</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
