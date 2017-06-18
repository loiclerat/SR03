


function getXhr()
{
	var xhr = null; 

	if(window.XMLHttpRequest) // Firefox et autres
		xhr = new XMLHttpRequest(); 
	else if(window.ActiveXObject){ // Internet Explorer 
		try {
		xhr = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	else { // XMLHttpRequest non supporté par le navigateur 
		alert("Votre navigateur ne supporte pas les objets XMLHTTPRequest..."); 
		xhr = false; 
	} 

	return xhr;
}


function searchGames()
{
	//Partie Ajax
	var xhr = getXhr();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			resultat = xhr.responseText;
			
			// Populate table
			var jsonResult = JSON.parse(resultat);
			for (var i=0 ; i<jsonResult.length ; i++)
			{	
				addRow(jsonResult[i].titre, jsonResult[i].prix, jsonResult[i].url_image, jsonResult[i].consoles, jsonResult[i].id);
			}
		} 
	}

	xhr.open("GET","../api/games",true);
	xhr.send(null); 

}

// Exéctuer au chargement de la page
(searchGames)();

// Crée une ligne dans le tableau 
function addRow(titre, prix, image, consoles, id)
{
		gameTable=document.getElementById("gameTable").getElementsByTagName('tbody').item(0);
	    row=document.createElement("tr");

	    cell1 = document.createElement("td");
	    cell2 = document.createElement("td");
	    cell3 = document.createElement("td");
	    cell4 = document.createElement("td");
	    cell5 = document.createElement("td");

	    // Titre et prix
     	textnode1=document.createTextNode(titre);
     	textnode2=document.createTextNode(prix);

     	// Image
        var imagenode = document.createElement("img");
		imagenode.setAttribute('src', image);
		imagenode.setAttribute('height', '100px');
		imagenode.setAttribute('width', '100px');

		// Choix de la console
		var consolenode = document.createElement("select");
		consolenode.setAttribute('name', 'console');

		for (var i=0 ; i<consoles.length ; i++)
		{
			var option = document.createElement("option");
			option.innerHTML = consoles[i].nomConsole;

			consolenode.appendChild(option);
		}

		// Choix de la quantité pour ajout au panier
		var quantity = document.createElement("input");
		quantity.setAttribute('type', 'number');
		quantity.setAttribute('name', 'quantity');
		quantity.setAttribute('min', '1');
		quantity.setAttribute('max', '999');
		quantity.value = 1;

		// Ajout au panier
		var addtocart = document.createElement("input");
		addtocart.setAttribute('type', 'button');
		addtocart.setAttribute('value', 'Add');
		addtocart.setAttribute('arg1', titre);

		addtocart.addEventListener('click', function addToCart(){
				
				//Partie Ajax
				var xhr = getXhr();

				xhr.onreadystatechange = function(){
					if(xhr.readyState == 4 && xhr.status == 200){
						alert("Ajout effectué");

						getPanier();
					}
				}

				xhr.open("POST","http://localhost:8080/SR03/api/commande/ligne",true);
				xhr.setRequestHeader("Authorization", readCookie("authentication"));
				xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				xhr.send('login=' + sessionStorage.getItem('username') + '&idJeu=' + id + '&qty=' + quantity.value + '&nomConsole=' + consolenode.value);

			});

		// Fill cell
        cell1.appendChild(imagenode);
        cell2.appendChild(textnode1);
        cell3.appendChild(textnode2);
        cell4.appendChild(consolenode);
        cell5.appendChild(quantity);
		cell5.appendChild(addtocart);

		// Fill row
        row.appendChild(cell1);
        row.appendChild(cell2);
        row.appendChild(cell3);
        row.appendChild(cell4);
        row.appendChild(cell5);
        gameTable.appendChild(row);
}

// ==============================================================================================



// ==============================================================================================
// == PANIER ==
// Menu déroulant

sfHover = function() {
        var sfEls = document.getElementById("menu").getElementsByTagName("LI");
        for (var i=0; i<sfEls.length; i++) {
                sfEls[i].onmouseover=function() {
                        this.className+=" sfhover";
                }
                sfEls[i].onmouseout=function() {
                        this.className=this.className.replace(new RegExp(" sfhover\\b"), "");
                }
        }
}
if (window.attachEvent) window.attachEvent("onload", sfHover);



// Récup éléments
function getPanier()
{
	var login = sessionStorage.getItem('username');

	var xhr = getXhr();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){

			// Si le tableau est déjà rempli, on nettoie
			var parent =document.getElementById("cartTable");
			var child =document.getElementById("cartTable").getElementsByTagName('tbody').item(0);
			if (child)
				parent.removeChild(child);

			var newBody = document.createElement("tbody");
			parent.appendChild(newBody);

			resultat = xhr.responseText;
			
			// Populate oanier
			var jsonResult = JSON.parse(resultat);
			var lignes = jsonResult.ligneCommandes;
			
			// Populate table
			for (var i=0 ; i<lignes.length ; i++)
			{	
				var game = lignes[i].jeu_id;
				var consoles = game.consoles;
				var consoleName = "";

				// Recherche du nom de la console
				for (var j=0 ; j<consoles.length ; j++)
				{
					if (consoles[j].id === lignes[i].console_id)
						consoleName = consoles[j].nomConsole;
				}

				addCartRow(game.titre, lignes[i].linePrice, game.url_image, consoleName, game.id, lignes[i].quantity, lignes[i].console_id);
			}

			// Ajout du prix total
			var cartTable=document.getElementById("cartTable").getElementsByTagName('tbody').item(0);
		    var row=document.createElement("tr");
		    var cell1=document.createElement("td");
		    var cell2=document.createElement("td");

		    var price=document.createTextNode("Prix total : "+jsonResult.price);
		    var valider=document.createElement("input");
		    valider.setAttribute('type', 'button');
			valider.setAttribute('value', 'Commander');

			valider.addEventListener('click', function validateCart(){
					
					//Partie Ajax
					var xhr = getXhr();

					xhr.onreadystatechange = function(){
						if(xhr.readyState == 4 && xhr.status == 200){
							alert("Commande validée !");

							getPanier();
						}
					}

					xhr.open("GET","http://localhost:8080/SR03/api/commande/validateCurrent/"+login,true);
					xhr.setRequestHeader("Authorization", readCookie("authentication"));
					xhr.send(null);

				});

			cell1.appendChild(price);
			cell2.appendChild(valider);
		    row.appendChild(cell1);
		    row.appendChild(cell2);
        	cartTable.appendChild(row);
		} 
	}

	xhr.open("GET", "http://localhost:8080/SR03/api/commande/getCurrent/"+login,true);
	xhr.setRequestHeader("Authorization", readCookie("authentication"));
	xhr.send(null); 
}

(getPanier)();


// Crée une ligne dans le panier 
function addCartRow(titre, prix, image, consoleName, idJeu, qte, idConsole)
{
	    cartTable=document.getElementById("cartTable").getElementsByTagName('tbody').item(0);
	    row=document.createElement("tr");

	    cell1 = document.createElement("td");
	    cell2 = document.createElement("td");
	    cell3 = document.createElement("td");
	    cell4 = document.createElement("td");
	    cell5 = document.createElement("td");
	    cell6 = document.createElement("td");

	    // Titre et prix
     	title=document.createTextNode(titre);
     	lineprice=document.createTextNode(prix+" €");

     	// Image
        var imagenode = document.createElement("img");
		imagenode.setAttribute('src', image);
		imagenode.setAttribute('height', '50px');
		imagenode.setAttribute('width', '50px');

		// Console
		var consolenode = document.createTextNode(consoleName);

		// Quantité
		var quantity = document.createTextNode("x"+qte);

		// Ajout au panier
		var removeFromCart = document.createElement("input");
		removeFromCart.setAttribute('type', 'button');
		removeFromCart.setAttribute('value', 'Remove');
		removeFromCart.setAttribute('arg1', titre);

		removeFromCart.addEventListener('click', function removeFromCart(){
				
				//Partie Ajax
				var xhr = getXhr();

				xhr.onreadystatechange = function(){
					if(xhr.readyState == 4 && xhr.status == 200){
						// Refresh panier
						getPanier();
					}
				}

				xhr.open("DELETE","http://localhost:8080/SR03/api/commande/user/"+sessionStorage.getItem('username')+"/jeu/"+idJeu+"/console/"+idConsole,true);
				xhr.setRequestHeader("Authorization", readCookie("authentication"));
				xhr.send(null);

			});

		// Fill cell
        cell1.appendChild(imagenode);
        cell2.appendChild(title);
        cell3.appendChild(consolenode);
        cell4.appendChild(quantity);
        cell5.appendChild(lineprice);
		cell6.appendChild(removeFromCart);

		// Fill row
        row.appendChild(cell1);
        row.appendChild(cell2);
        row.appendChild(cell3);
        row.appendChild(cell4);
        row.appendChild(cell5);
        row.appendChild(cell6);
        cartTable.appendChild(row);
}

// ==============================================================================================
