<%@ page import="com.boucheriebenz.eboucherie.model.Animal"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Boucherie Benz - Vente en ligne</title>
        <link rel="stylesheet" href="style/ficheanimal.css" type="text/css" />
        <script src="js/jquery-1.8.3.min.js"></script>
        <script src="slidesjs/js/slides.min.jquery.js"></script>
</head>
<body>
		<jsp:include page="header.jsp" />
	<jsp:include page="menuGauche.jsp" />
	<jsp:include page="footer.jsp" />
	
            <div id="div1">			
					<h1 class=titre>Fiche Descriptive</h1>
                        <div class="fiche">                  
					    <a href=""><img src="${ficheanimal.photo1}" width="407" height="325" border="0"/></a>
                            </br>
                            </br>     
						    <a class="infos">Type : </a><a> ${ficheanimal.type} </a>
                              </br> </br>     
                            <a class="infos">Age : </a><a>${ficheanimal.age} </a>
                              </br> </br>          
                            <a class="infos">Poids : </a><a> ${ficheanimal.poids}</a>
                              </br> </br>
                            <a class="infos">Sexe : </a><a> ${ficheanimal.sexe}</a>
                              </br> </br>
                            <a class="infos">Race : </a><a> ${ficheanimal.race}</a>
                              </br> </br>
                            <a class="infos">Numéro de traçabilité : </a><a> ${ficheanimal.num_trac}</a>
                              </br> </br>   
  	                      	<a class="infos">Prix max : </a><a> ${ficheanimal.prix_max}</a>
                              </br> </br>
                            <a class="infos">Prix min : </a><a> ${ficheanimal.prix_min}</a>
                              </br> </br>						
                        </div>		
		    </div>
	</body>
</html>

</body>
</html>