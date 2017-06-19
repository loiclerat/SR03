


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
		addtocart.setAttribute('class', 'button expanded');
		addtocart.setAttribute('style', 'background-color:#00cc00');
		addtocart.setAttribute('arg1', titre);

		addtocart.addEventListener('click', function addToCart(){
				
				//Partie Ajax
				var xhr = getXhr();

				xhr.onreadystatechange = function(){
					if(xhr.readyState == 4){
						if(xhr.status == 200){
							getPanier();
						}
						else if (xhr.status == 401){
							alert("La session a expiré");

							// clear username storage
				            sessionStorage.clear();
				            // delete authentication token
				            eraseCookie('authentication');
				            document.location.href="../index.html";
						}
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
		if(xhr.readyState == 4){
			if (xhr.status == 401){
				alert("La session a expiré");

				// clear username storage
	            sessionStorage.clear();
	            // delete authentication token
	            eraseCookie('authentication');
	            document.location.href="../index.html";
			}
			else if (xhr.status == 200){

				// Si le panier est déjà rempli, on nettoie
				var content =document.getElementById("content");
				content.innerHTML = "";

				resultat = xhr.responseText;
				
				// Populate oanier
				var jsonResult = JSON.parse(resultat);
				var lignes = jsonResult.ligneCommandes;
				
				// Populate table
				var i = 0;
				for (i=0 ; i<lignes.length ; i++)
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

				document.getElementById('nbItems').innerHTML = i+' Items';

				// Ajout du prix total
				var price=document.getElementById("price");
				price.innerHTML = jsonResult.price +" €";
	        }
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
	    var content=document.getElementById("content");
	    var newRow = document.createElement("div");
	    newRow.setAttribute("class", "checkout-summary-item");


	    // Image

	        var imagenode = document.createElement("img");
			imagenode.setAttribute('src', image);
			imagenode.style.height = '70px';
			imagenode.style.width = 'auto';


		// Infos 

		var infos = document.createElement("div");
		infos.setAttribute("class", "item-name");

			// Titre
			var title = document.createElement("a");
			title.innerHTML = titre;

			// Console
			var consolenode = document.createElement("p");
			var consolespan = document.createElement("class", "title");
			consolespan.innerHTML = "Console : ";
			consolenode.appendChild(consolespan);
			var consoletext = document.createTextNode(consoleName);
			consolenode.appendChild(consoletext);

			// Quantité
			var quantitynode = document.createElement("p");
			var quantityspan = document.createElement("class", "title");
			quantityspan.innerHTML = "Quantité : ";
			quantitynode.appendChild(quantityspan);
			var quantitytext = document.createTextNode(qte);
			quantitynode.appendChild(quantitytext);

		infos.appendChild(consolenode);
		infos.appendChild(quantitynode);


		// Prix + remove

		var infos2 = document.createElement("div");
		infos2.setAttribute("class", "item-price");
     		
     		// Prix
     		var price = document.createElement("p");
     		price.setAttribute("class", "title");
     		price.innerHTML = prix+" €";

			// Remove
			var removeFromCart = document.createElement("a");
			removeFromCart.addEventListener('click', function removeFromCart(){
					
					//Partie Ajax
					var xhr = getXhr();

					xhr.onreadystatechange = function(){
						if(xhr.readyState == 4){
							if(xhr.status == 200){
								getPanier();
							}
							else if (xhr.status == 401){
								alert("La session a expiré");

								// clear username storage
					            sessionStorage.clear();
					            // delete authentication token
					            eraseCookie('authentication');
					            document.location.href="../index.html";
							}
						}
					}

					xhr.open("DELETE","http://localhost:8080/SR03/api/commande/user/"+sessionStorage.getItem('username')+"/jeu/"+idJeu+"/console/"+idConsole,true);
					xhr.setRequestHeader("Authorization", readCookie("authentication"));
					xhr.send(null);

				});
			removeFromCart.innerHTML = "Remove";

		infos2.appendChild(price);
		infos2.appendChild(removeFromCart);


		// Fill cell
        newRow.appendChild(imagenode);
        newRow.appendChild(infos);
        newRow.appendChild(infos2);
        content.appendChild(newRow);
}


function validateCart(){
						
	//Partie Ajax
	var xhr = getXhr();
	var login = sessionStorage.getItem('username');

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
			alert("Commande validée !");

			getPanier();
			}
			else if (xhr.status == 401){
				alert("La session a expiré");

				// clear username storage
	            sessionStorage.clear();
	            // delete authentication token
	            eraseCookie('authentication');
	            document.location.href="../index.html";
			}
		}
	}

	xhr.open("GET","http://localhost:8080/SR03/api/commande/validateCurrent/"+login,true);
	xhr.setRequestHeader("Authorization", readCookie("authentication"));
	xhr.send(null);

};



// ==============================================================================================
