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
			for (var i=0 ; i<jsonResult.length ; i++)
			{	
				addRow(/** **/);
			}
		} 
	}

	xhr.open("GET",/** **/,true);
	xhr.send(null); 

} )();


// =====================

// Récupérer contenu du panier
// Valider panier
// Ajouter au panier
// Supprimer du panier