

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

-- --------------------------------------------------------

--
-- Structure de la table `Commande`
--

CREATE TABLE `Commande` (
  `commandeid` int(11) NOT NULL,
  `utilisateur_id` int(11) DEFAULT NULL,
  `date_commande` date DEFAULT NULL,
  `prix` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Commande`
--

INSERT INTO `Commande` (`commandeid`, `utilisateur_id`, `date_commande`, `prix`) VALUES
(1, 1, '2017-04-02', 20),
(2, 3, '2017-01-16', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `Jeu`
--

CREATE TABLE `Jeu` (
  `jeuId` int(11) NOT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `url_image` varchar(255) DEFAULT NULL,
  `prix` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Jeu`
--

INSERT INTO `Jeu` (`jeuId`, `titre`, `url_image`, `prix`) VALUES
(1, 'PES 2017', 'url', 19.99),
(2, 'The Witcher', 'http://www.syfantasy.fr/images/produits/small_The_Witcher_31.jpg', 60);

-- --------------------------------------------------------

--
-- Structure de la table `LigneCommande`
--

CREATE TABLE `LigneCommande` (
  `ligneId` int(11) NOT NULL,
  `jeu_id` int(11) DEFAULT NULL,
  `commande_id` int(11) DEFAULT NULL,
  `quantity` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `LigneCommande`
--

INSERT INTO `LigneCommande` (`ligneId`, `jeu_id`, `commande_id`, `quantity`) VALUES
(1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `TypeConsole`
--

CREATE TABLE `TypeConsole` (
  `consoleId` int(11) NOT NULL,
  `nomConsole` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `TypeConsole`
--

INSERT INTO `TypeConsole` (`consoleId`, `nomConsole`) VALUES
(2, 'PS3'),
(1, 'PS4'),
(5, 'SWITCH'),
(4, 'WII U'),
(6, 'XBOX 360'),
(3, 'XBOX ONE');

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `userId` int(11) NOT NULL PRIMARY KEY,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `cpostal` varchar(5) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Utilisateur`
--

INSERT INTO `Utilisateur` (`userId`, `nom`, `prenom`, `mail`, `login`, `password`, `adresse`, `cpostal`, `ville`) VALUES
(1, 'Cartier', 'Florian', 'flo.cartier@hotmail.fr', 'flcartie', 'floflo', '1 bis rue de l\'Ã©pargne', '60200', 'Compiègne'),
(2, 'Hordequin', 'Theo', 'theo@mail.fr', 'thordequ', 'theotheo', 'je ne sais pas', '60200', 'CompiÃ¨gne'),
(3, 'Jeannot', 'Paul', 'paul.jeannot@gmail.com', 'pjeanno', 'paulpaul', 'Pres de l\'utc', '60200', 'Compiègne'),
(8, 'Duran', 'Ester', 'esterduranbarquilla@gmail.com', 'estDuran', 'passEster', 'spain', '23453', 'Madronera');


CREATE TABLE `td2`.`Jeu_TypeConsole` ( `JeuId` INT(11) NOT NULL , `TypeConsoleId` INT(11) UNIQUE NOT NULL , PRIMARY KEY (`JeuId`, `TypeConsoleId`)) ENGINE = InnoDB;
--
-- Index pour les tables exportées
--

--
-- Index pour la table `Commande`
--
ALTER TABLE `Commande`
  ADD PRIMARY KEY (`commandeid`),
  ADD KEY `FK_Commande_user` (`utilisateur_id`);
