<?php
  include("../Models/RestClient.php");
  include("../Models/Cart.php");
  include("../Models/Game.php");
  include("../Models/Console.php");

  //Envoi d'une requête GET au serveur pour récupérer la liste des jeux
  $json = $rest->setUrl('http://localhost:8080/FinalSR03Project/games/'.intval($_GET['gameId']))->get();
  
  // Récupération du JSON
  $jeu = (json_decode($json["content"]));

?>

<!doctype html>
<html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Loic & Flo - <?php echo $jeu->titre; ?></title>
    <link rel="stylesheet" href="http://dhbhdrzi4tiry.cloudfront.net/cdn/sites/foundation.min.css">
    <link rel="stylesheet" type="text/css" href="css/gameDetail.css">
  </head>
  <body>

    <!-- Start Top Bar -->
    <div class="top-bar">
      <div class="top-bar-left">
        <ul class="menu">
          <li class="menu-text">Loic & Flo</li>
          <!--
          <li><a href="#">One</a></li>
          <li><a href="#">Two</a></li>
          -->
        </ul>
      </div>
      <div class="top-bar-right">
        <ul class="menu">
          <li><a href="#">Panier</a></li>
          <li><a href="#">Connexion</a></li>
        </ul>
      </div>
    </div>
    <!-- End Top Bar -->

    <hr>
    <div class="row align-center">
      <div class="product-details center-text-for-small-only">
        <h1><?php echo $jeu->titre; ?></h1>
        <p class="product-description">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas id magna ac quam semper.deta</p>
          <div class="row">
            <div class="column small-12 large-5">
              <img class="thumbnail" src="<?php echo $jeu->url_image; ?>">
            </div>
            <div class="column small-12 large-7">
              <h6 class="product-color-title">Console</h6>
                <form class="product-option-selection large-8">
                    <select>
                      <?php 
                      
                      for ($i=0; $i < count($jeu->consoles); $i++) { 
                        echo "<option value=".$jeu->consoles[$i]->id.">".$jeu->consoles[$i]->nomConsole."</option>";
                      }
                      ?>
                    </select>
                  </label>
                </form>
                <div class="product-details-add-to-cart large-8">
                  <p><span class="in-stock">In Stock</span></p>
                  <hr>
                  <p class="price"><?php echo $jeu->prix; ?> €</p>
                  <p> <span>Qty:</span> <input class="qty" type="text" value="1"> </p>
                  <button class="button expanded">Add to Cart</button>
                </div>
            </div>
          </div>
      </div>
    </div>

    <div class="callout large secondary">
      <div class="row">
        <div class="large-4 columns">
          <h5>Vivamus Hendrerit Arcu Sed Erat Molestie</h5>
          <p>Curabitur vulputate, ligula lacinia scelerisque tempor, lacus lacus ornare ante, ac egestas est urna sit amet arcu. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed molestie augue sit.</p>
        </div>
        <div class="large-3 large-offset-2 columns">
          <ul class="menu vertical">
            <li><a href="#">One</a></li>
            <li><a href="#">Two</a></li>
            <li><a href="#">Three</a></li>
            <li><a href="#">Four</a></li>
          </ul>
        </div>
        <div class="large-3 columns">
          <ul class="menu vertical">
            <li><a href="#">One</a></li>
            <li><a href="#">Two</a></li>
            <li><a href="#">Three</a></li>
            <li><a href="#">Four</a></li>
          </ul>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="http://dhbhdrzi4tiry.cloudfront.net/cdn/sites/foundation.js"></script>
    <script>
      $(document).foundation();
    </script>
  </body>
</html>
