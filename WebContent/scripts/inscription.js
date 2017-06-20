
function surligne(champ, erreur)
{
   if(erreur)
      champ.style.backgroundColor = "#fba";
   else
      champ.style.backgroundColor = "#fff";
}

function verifLogin(champ)
{
   if(champ.value.length < 2 || champ.value.length > 25)
   {
      surligne(champ, true);
      return false;
   }
   else
   {
      surligne(champ, false);
      return true;
   }
}


function verifNom(champ)
{
   if(champ.value.length < 2)
   {
      surligne(champ, true);
      return false;
   }
   else
   {
      surligne(champ, false);
      return true;
   }
}


function verifMail(champ)
{
   var regex = /^[a-zA-Z0-9._-]+@[a-z0-9._-]{2,}\.[a-z]{2,4}$/;
   if(!regex.test(champ.value))
   {
      surligne(champ, true);
      return false;
   }
   else
   {
      surligne(champ, false);
      return true;
   }
}


function verifCodePostal(champ)
{
   var code = parseInt(champ.value);
   if(isNaN(code))
   {
      surligne(champ, true);
      return false;
   }
   else
   {
      surligne(champ, false);
      return true;
   }
}


function verifPass(champ)
{
   var regex =/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,}$/;

   if(!regex.test(champ.value))
   {
      surligne(champ, true);
      return false;
   }
   else
   {
      surligne(champ, false);
      return true;
   }
}

function verifIncriptionForm(myself)
{
	var f = myself.form;

	var pseudoOk = verifLogin(f.login);
	var nomOk = verifNom(f.nom);
	var prenomOk = verifNom(f.prenom);
	var codepostalOk = verifCodePostal(f.cpostal);
	var villeOk = verifNom(f.ville);
	var adresseOk = verifNom(f.adresse);
	var mailOk = verifMail(f.mail);
	var passOk = verifPass(f.password);

   	 	
   if(pseudoOk && nomOk && prenomOk && codepostalOk && mailOk && villeOk && adresseOk && passOk && f.password.value === f.password_conf.value)
   {
		sendForm(f);
   	return true;
   }
   else if (!passOk)
   {
      alert("Mot de passe trop faible. Viellez à utiliser des lettre majuscules, minuscules, des chiffres et à ce que votre mot de passe soit d'au moins 6 caratères");
      return false;
   }
   else if (f.password.value !== f.password_conf.value)
	{
   	alert("Les mots de passe de correspondent pas");
   	return false;
	}  
	else
	{
   	alert("Veuillez remplir correctement tous les champs");
   	return false;
	}
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


function sendForm(f)
{
	//Partie Ajax
	var xhr = getXhr();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
         if (xhr.status == 200){
			alert("Ajout effectué !!");

         window.location.href = "index.html";
		   } 
         else if (xhr.status == 401){
            alert("Ce login est déjà utilisé");
            surligne(f.login, true);
         }
      }
	}

	xhr.open("POST","api/users",true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	xhr.send('nom=' + f.nom.value + '&prenom=' + f.prenom.value + '&mail=' + f.mail.value + '&adresse=' + f.adresse.value + 
           '&cpostal=' + f.cpostal.value + '&ville=' + f.ville.value + '&login=' + f.login.value + '&password=' + f.password.value);
	
}


