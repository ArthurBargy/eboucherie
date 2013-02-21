<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<div id="header">
		<div class="logo">
			<a href="accueil"><img src="images/logo.png" width="130"
				height="130" alt="boucherie benz" border="0" /> </a>
		</div>

		<div class="header_liens">

			<ul class="liens">
				<li><a href="connexion.jsp" title="Connexion">Connexion</a></li>
				<li>-</li>
				<li><a href="" title="Mon Compte">Mon Compte</a></li>
				<li>-</li>
				<li><a href="" title="Panier"><img
						src="images/icone_panier.png" width="20" height="20" border="0">PANIER</a>
				</li>
			</ul>


		</div>
		<div class="barre_recherche">
			<form id="search">

				<input id="recherche" name="recherche" type="text" size="40"
					placeholder="Rechercher" />
			</form>
		</div>


		<div class="menu">
			<ul id="menuDeroulant">
				<li><a href="#">Nos engagements </a>
					<ul class="sousMenu">
						<li><a href="#">La qualité</a></li>
						<li><a href="#">L'abattage</a></li>
						<li><a href="#">La traçabilité</a></li>
					</ul>
				</li>
				<li><a href="#">Les recettes</a></li>
				<li><a href="#">Accès</a></li>
				<li><a href="contact.jsp">Contact</a></li>
			</ul>
		</div>
	</div>
</html>