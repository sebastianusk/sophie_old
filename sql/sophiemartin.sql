-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 14, 2016 at 04:13 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sophiemartin`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`username`, `password`) VALUES
('chaidir', 'juventus1');

-- --------------------------------------------------------

--
-- Table structure for table `databarang`
--

CREATE TABLE IF NOT EXISTS `databarang` (
  `kode_barang` varchar(15) NOT NULL,
  `nama_barang` varchar(40) NOT NULL,
  `harga_tpg` int(7) NOT NULL,
  `disc_member` int(3) NOT NULL,
  `kategori` varchar(15) NOT NULL,
  `tanggal_update` date NOT NULL,
  `stok` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `databarang`
--

INSERT INTO `databarang` (`kode_barang`, `nama_barang`, `harga_tpg`, `disc_member`, `kategori`, `tanggal_update`, `stok`) VALUES
('AAAAA', 'BASD', 300000, 20, 'SEAF', '2016-01-10', 15);

-- --------------------------------------------------------

--
-- Table structure for table `datakonter`
--

CREATE TABLE IF NOT EXISTS `datakonter` (
`kode_konter` int(5) NOT NULL,
  `nama_konter` varchar(20) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `datakonter`
--

INSERT INTO `datakonter` (`kode_konter`, `nama_konter`) VALUES
(1, 'jancuk'),
(2, 'cuka');

-- --------------------------------------------------------

--
-- Table structure for table `dataorder`
--

CREATE TABLE IF NOT EXISTS `dataorder` (
  `kode_order` varchar(15) NOT NULL,
  `kode_konter` int(5) NOT NULL,
  `kode_barang` varchar(15) NOT NULL,
  `jumlah_barang` int(5) NOT NULL,
  `status_order` int(5) NOT NULL,
  `tgl_masuk` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `datatransaksi`
--

CREATE TABLE IF NOT EXISTS `datatransaksi` (
`kode_transaksi` int(5) NOT NULL,
  `kode_konter` int(5) NOT NULL,
  `tanggal_transaksi` date NOT NULL,
  `nominal_transaksi` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `non_volatile_mem`
--

CREATE TABLE IF NOT EXISTS `non_volatile_mem` (
  `parameter` varchar(20) NOT NULL,
  `nilai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `non_volatile_mem`
--

INSERT INTO `non_volatile_mem` (`parameter`, `nilai`) VALUES
('ordercounter', 47);

-- --------------------------------------------------------

--
-- Table structure for table `popularcount`
--

CREATE TABLE IF NOT EXISTS `popularcount` (
`index_pop` int(11) NOT NULL,
  `kode_barang` varchar(20) NOT NULL,
  `kode_konter` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `tanggal_pesan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `statusorder`
--

CREATE TABLE IF NOT EXISTS `statusorder` (
  `status_order` int(5) NOT NULL,
  `keterangan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `databarang`
--
ALTER TABLE `databarang`
 ADD PRIMARY KEY (`kode_barang`);

--
-- Indexes for table `datakonter`
--
ALTER TABLE `datakonter`
 ADD PRIMARY KEY (`kode_konter`);

--
-- Indexes for table `dataorder`
--
ALTER TABLE `dataorder`
 ADD PRIMARY KEY (`kode_order`);

--
-- Indexes for table `datatransaksi`
--
ALTER TABLE `datatransaksi`
 ADD PRIMARY KEY (`kode_transaksi`);

--
-- Indexes for table `non_volatile_mem`
--
ALTER TABLE `non_volatile_mem`
 ADD PRIMARY KEY (`parameter`);

--
-- Indexes for table `popularcount`
--
ALTER TABLE `popularcount`
 ADD PRIMARY KEY (`index_pop`);

--
-- Indexes for table `statusorder`
--
ALTER TABLE `statusorder`
 ADD PRIMARY KEY (`status_order`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `datakonter`
--
ALTER TABLE `datakonter`
MODIFY `kode_konter` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `datatransaksi`
--
ALTER TABLE `datatransaksi`
MODIFY `kode_transaksi` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `popularcount`
--
ALTER TABLE `popularcount`
MODIFY `index_pop` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
