-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 02, 2020 at 05:24 PM
-- Server version: 8.0.21
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prjpsi`
--

-- --------------------------------------------------------

--
-- Table structure for table `annonces`
--

DROP TABLE IF EXISTS `annonces`;
CREATE TABLE IF NOT EXISTS `annonces` (
  `titre` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `domaine` int DEFAULT NULL,
  `prop` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  `reserved` tinyint(1) DEFAULT '0',
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `domaine` (`domaine`),
  KEY `prop` (`prop`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `annonces`
--

INSERT INTO `annonces` (`titre`, `description`, `prix`, `domaine`, `prop`, `date`, `reserved`, `id`) VALUES
('BENZZ ', 'benzz a vendre ', 50000, 1, 1, '0000-00-00', 1, 1),
('BMW', 'nouvelle BM a vendre', 13000, 1, 2, '0000-00-00', 0, 2),
('Peugeot a vendre ', 'peugeot 208', 6000, 1, 1, '2018-11-02', 0, 3),
('Peugeot a vendre ', 'peugeot 208', 6000, 1, 1, '2018-11-02', 0, 4),
('megane a vendre ', 'megane', 7000, 1, 1, '2020-10-31', 0, 5),
('Camaro a vendre ', 'chevrolet camaro 2017', 23000, 1, 1, '2020-10-31', 0, 6),
('f4 a vendre ', 'courbevoie 52', 2000000, 0, 1, '2020-10-31', 0, 7),
('f3 a vendre ', 'argenteuil f3 nouvelle', 6000000, 0, 1, '2020-10-31', 0, 8),
('batiment', 'batiment a vendre ', 10000000, 0, 7, '2020-11-01', 0, 10);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `nom` varchar(20) NOT NULL,
  `prenom` varchar(20) NOT NULL,
  `DateNS` date NOT NULL,
  `email` varchar(50) NOT NULL,
  `pwd` varchar(30) NOT NULL,
  `adr` varchar(200) NOT NULL,
  `codePostal` varchar(5) NOT NULL,
  `commune` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`nom`, `prenom`, `DateNS`, `email`, `pwd`, `adr`, `codePostal`, `commune`, `phone`, `active`, `id`) VALUES
('ALHOUCINE', 'YACINE', '1996-02-27', 'ey_al_houcine@esi.dz', 'mitchell00', 'paris', '75013', 'paris', '05555555', 1, 1),
('rain', 'walker', '0000-00-00', 'rainwalker@esi.dz', 'rain', 'Lycee Mahi', '09000', 'blida', '0556384757', 1, 2),
('MyFamilyName', 'LastName', '1995-03-03', 'me.me@gmail.com', 'me', 'Paris', '75000', 'Paris', '5555555555', 1, 3),
('john', 'wick', '1995-12-23', 'johnwick.com', 'azerty', 'myadress', '19000', 'moscow', '55555', 1, 4),
('Kuma', 'Qreq', '1995-12-27', 'kuma@gmail.com', 'kuma', 'kader', '09000', 'blida', '564987', 1, 5),
('neo@gmail.com', 'anderson', '1992-11-12', 'neo@gmail.com', 'neo', 'usa', '12000', 'newyork', '156230', 1, 6),
('anderson', 'neO', '1985-05-05', 'neo.anderson@gmail.com', 'neoo', 'russia', '15000', '4moscow', '78', 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `domaine`
--

DROP TABLE IF EXISTS `domaine`;
CREATE TABLE IF NOT EXISTS `domaine` (
  `id` int NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `domaine`
--

INSERT INTO `domaine` (`id`, `description`) VALUES
(0, 'immobilier'),
(1, 'vehicules'),
(2, 'informatique');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
