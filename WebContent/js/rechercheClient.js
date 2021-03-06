$(document).ready(function() {
 
    $("#search").submit(function(e){
           e.preventDefault();
    });
    $("#recherche").autocomplete("ControlleurAutoCompletion",{ 
    	minChars:3 ,
        delay: 200,
        mustMatch: 1,
      });
    $("input#recherche").focus(function() {
    	$("input#recherche").keypress(function(e) {
	        if(e.which == 13) {
	        	 //recuperer les donnees de la formulaire, puis les serialiser
	            dataString = $("#FormulaireDeRecherche").serialize();
	            
	            var motClef =$.trim( $("input#recherche").val()); 
	            if(motClef == ''){
	            	
	            	$("input#recherche").css('border-color','red');
	            	alert("Veuillez saisir un mot clef!");
	            	return false;
	            }
	            
	            $("input#recherche").css('border-color','black');
	            dataString = "recherche=" + motClef;
	            
	            
	            //On recoit des donnees de type Json du Serveur
	            $.ajax({
	                type: "POST",
	                url: "ControlleurRechercheClient",
	                data: dataString,
	                dataType: "json",
	                //si on recoit une reponse du cote du serveur
	                success: function( data, textStatus, jqXHR) {
	                	var chaine = "";
	                    //si le mot clef existe on affiche la liste des reponses
	                     if(data.success){
	                    	 $(".contenuCentral").empty();
	                    	chaine += "<div class=\"heading\"><img alt=\"Resultats de votre recherche\" src=\"images/photos/resultats_recherche.gif\"></br></div>";
	                    	 chaine+="<p class=\"nbOfArticles\">"+
	                    	 "<strong>"+ data.lClient.length +" client(s) </strong>"+
	                    	 "</p></br>";
	                    	 chaine+="<p class=\"sort\"> </div>";
	                    	 chaine += "<div class=\"resultsContent\">";

	                    	 $.each( data.lClient, function(i, n){
	                    		 chaine+="<div class=\"blockColProd \">";
	                    		 chaine+="<strong>idClient: "+n["idClient"]+"</strong>"+
	                    		 "<ul> "+
		                    	 "<li>Nom: "+n["nom"]+"</li>"+
		                    	 "<li>Prenom: "+n["prenom"]+"</li>"+
		                    	 "<li>Groupe Tarif: "+n["groupeTarif"]+"</li>"+
		                    	 "</ul>"+
	                    		 "</div>";
	                    		 
	                        	
	                    		 });
	                    	 chaine+="</di>";
	                    	 $(".contenuCentral").html(chaine);
	                     } 
	                     //sinon on affiche un message d'erreur
	                     else {
	                    	 chaine="<h1>Aucun résultat pour votre recherche : \""+motClef+"\"</h1>"+
	                        "<div><h2>Conseils de recherche</h2></div> "+
	                    	 "<ul> "+
	                    	 "<li>Essayez plusieurs mots-clés de recherche</li>"+
	                    	 "<li>Vérifiez l'orthographe des mots-clés</li>"+
	                    	 "<li>Essayez des mots-clés associés ou des termes similaires</li>"+
	                    	 "</ul>";
	                         $(".contenuCentral").html(chaine);
	                     }
	                },
	                
	                //si le serveur ne retourne aucune reponse
	                error: function(jqXHR, textStatus, errorThrown){
	                     console.log("Le serveur ne répond pas correctement " + textStatus);
	                      $("#ajaxResponse").html(jqXHR.responseText);
	                }

	      
	            });        
	        }
    });
    });
    //checks for the button click event
   
 
});