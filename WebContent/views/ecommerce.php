<?php
  include("Models/RestClient.php");
  include("Models/Game.php");
  include("Models/Cart.php");

  //Envoi d'une requête GET au serveur pour récupérer la liste des jeux
  $jeux = $rest->setUrl('http://localhost:8080/FinalSR03Project/games')->get();
  // Récupération du JSON
  $json = (json_decode($jeux["content"]));

  
  $games = array();
  session_start();
  // Conversion des objets JSON en objet Jeux
  for($x = 0; $x < count($json); $x++) {

    $game = new Game();
    $game->setId($json[$x]->id);
    $game->setTitle($json[$x]->titre);
    $game->setUrl($json[$x]->url_image);
    $game->setPrice($json[$x]->prix);

    array_push(Cart::getInstance()->games, $game);
  }

  $games = Cart::getInstance()->games;
  $_SESSION['games'] = $games;
?>

<!doctype html>
<html class="no-js" lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Foundation | Welcome</title>
    <link rel="stylesheet" href="http://dhbhdrzi4tiry.cloudfront.net/cdn/sites/foundation.min.css">
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
    <div class="row small-up-2 medium-up-3 large-up-6">
      <?php for($x = 0; $x < count($games); $x++) { ?>
      <div class="column">
        <img class="thumbnail" src="<?php echo $games[$x]->getUrl(); ?>">
        <h5><?php echo $games[$x]->getTitle(); ?></h5>
        <p><?php echo $games[$x]->getPrice(); ?> €</p>
        <a href="Views/gameDetail.php?gameId=<?php echo $games[$x]->getId(); ?>" class="button small expanded hollow">Buy</a>
      </div>
      <?php } ?>
    </div>

    <hr>

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
