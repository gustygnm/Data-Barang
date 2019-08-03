-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Aug 01, 2019 at 02:17 AM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_barang`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_barang`
--

CREATE TABLE IF NOT EXISTS `tbl_barang` (
`id` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `stok` int(11) NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_barang`
--

INSERT INTO `tbl_barang` (`id`, `nama`, `stok`, `image`) VALUES
(4, 'Meja', 19, '/barang/img/Screenshot_2019-07-31-11-24-22-914_com.gojek.app.png'),
(13, 'nmax', 9, '/barang/img/crop_image.jpg'),
(16, 'tas', 98, '/barang/img/IMG_20190728_081542.jpg'),
(17, 'Sendok', 25, '/barang/img/IMG_20190731_145709.jpg'),
(18, 'HP', 12, '/barang/img/IMG-20190731-WA0004.jpg'),
(19, 'Hoodie', 2, '/barang/img/IMG-20190730-WA0010.jpeg'),
(20, 'tiket', 100, '/barang/img/IMG_20190712_174359.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_barang`
--
ALTER TABLE `tbl_barang`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_barang`
--
ALTER TABLE `tbl_barang`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
