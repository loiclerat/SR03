


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
			console.log('Résultat : ' + resultat);

			// Populate table
			var jsonResult = JSON.parse(resultat);
			console.log('Parsed : ' + jsonResult.length);
			for (var i=0 ; i<jsonResult.length ; i++)
			{	
				console.log('Ligne titre : ' + jsonResult[i].titre);
			}

			//document.getElementById('gameTable').innerHTML = jsonResponse.titre;	
		} 
	}

	xhr.open("GET","http://localhost:8080/SR03/api/games",true);
	xhr.send(null); 

} )();
