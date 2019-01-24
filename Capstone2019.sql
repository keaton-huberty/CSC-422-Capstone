-- phpMyAdmin SQL Dump
-- version 4.4.15.9
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 22, 2019 at 03:44 AM
-- Server version: 5.6.37
-- PHP Version: 7.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Capstone2019`
--

-- --------------------------------------------------------

--
-- Table structure for table `userLogin`
--

CREATE TABLE IF NOT EXISTS `userLogin` (
  `userID` int(11) NOT NULL,
  `userName` varchar(30) NOT NULL,
  `userPassword` varchar(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userLogin`
--

INSERT INTO `userLogin` (`userID`, `userName`, `userPassword`) VALUES
(1, 'will', 'password'),
(2, 'mike', 'password1'),
(3, 'keaton', 'password2'),
(4, 'amin', 'password3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `userLogin`
--
ALTER TABLE `userLogin`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `userLogin`
--
ALTER TABLE `userLogin`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
