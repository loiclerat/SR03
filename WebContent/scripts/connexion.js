
function verifForm(myself)
{
	var f = myself.form;

	//var pseudoOk = verifLogin(f.login);
 	
	traitementAjax(f);
   return true;
}


// Création utilisateur

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


function traitementAjax(f)
{
	//Partie Ajax
	var xhr = getXhr();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log("token received");
			
			createCookie('authentication',xhr.responseText,5);
			sessionStorage.setItem('username', f.login.value);
			document.location.href="jeux.html";
		} 
	}

	xhr.open("POST","../api/users/authenticate",true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	xhr.send('login=' + f.login.value + '&password=' + f.password.value);
	
}

