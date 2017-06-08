


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


(function traitementAjax()
{
	//Partie Ajax
	var xhr = getXhr();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			resultat = xhr.responseText;
			
			// Populate table
			var jsonResult = JSON.parse(resultat);
			console.log('Parsed : ' + jsonResult.length);
			for (var i=0 ; i<jsonResult.length ; i++)
			{	
				//console.log('Ligne titre : ' + jsonResult[i].consoles);

				addRow(jsonResult[i].titre, jsonResult[i].prix, jsonResult[i].url_image, jsonResult[i].consoles);
			}
		} 
	}

	xhr.open("GET","../api/games",true);
	xhr.send(null); 

} )();

// Crée une ligne dans le tableau 
function addRow(titre, prix, image, consoles)
{
	    gameTable=document.getElementsByTagName("tbody").item(0);
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
			console.log(titre + ' a été ajouté au panier ! Quantité : '+quantity.value+' ; console : '+consolenode.value);	

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
