-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2020 at 09:39 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `my_bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `dummy`
--

CREATE TABLE `dummy` (
  `no_rek` varchar(25) NOT NULL,
  `kode_bank` varchar(5) DEFAULT NULL,
  `nama_bank` varchar(10) DEFAULT NULL,
  `nama_pemilik` varchar(25) DEFAULT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `saldo_dummy` int(3) DEFAULT NULL,
  `loginStatus` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dummy`
--

INSERT INTO `dummy` (`no_rek`, `kode_bank`, `nama_bank`, `nama_pemilik`, `username`, `password`, `saldo_dummy`, `loginStatus`) VALUES
('6304859233', '008', 'Mandiri', 'Imil Cangtip', 'cingmil', 'kucinghamil', 3000000, NULL),
('91785674', '014', 'BCA', 'Kuma Ganteng', 'kumaku', 'kumapaling', 5500000, NULL),
('91785874', '014', 'BCA', 'Unying Ganteng', 'unych', 'unyingtjap', 3500000, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(66),
(66);

-- --------------------------------------------------------

--
-- Table structure for table `nasabah`
--

CREATE TABLE `nasabah` (
  `id_nasabah` int(11) NOT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `no_ktp` varchar(50) DEFAULT NULL,
  `nama_lengkap` varchar(50) DEFAULT NULL,
  `no_telp` varchar(20) DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `loginStatus` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nasabah`
--

INSERT INTO `nasabah` (`id_nasabah`, `alamat`, `gender`, `no_ktp`, `nama_lengkap`, `no_telp`, `saldo`, `birth_date`, `email`, `username`, `password`, `loginStatus`) VALUES
(35, 'Jakarta Timur', 'Wanita', '3175105303970014', 'Irene Nurintan', '083898224157', 3480500, '1997-03-13 00:00:00', 'rne@renee.co', 'rneirn', 'kumaganteng', 'false'),
(37, 'Jakarta Timur', 'Pria', '3175105303970015', 'Cimot Bulat', '083898224158', 5000000, '2018-05-03 00:00:00', 'cimot@bulat.co', 'cimot', 'sayangcimot', NULL),
(43, 'Jakarta Timur', 'Non Binary', '3175105302870015', 'Kelapa Muda', '083898223158', 5000000, '1987-02-03 00:00:00', 'kelapa@muda.co', 'kelapamud', 'kelapamudaenak', 'false'),
(45, 'Dahsyat', 'Dan Paling', '3175105305870015', 'Dia Pikir', '083898222158', 5000000, '1987-05-03 00:00:00', 'dia@yang.paling', 'hebat', 'merasapalingjago', NULL),
(47, 'Dahsyat', 'Danling', '3175105305870925', 'Bisa', '083898222358', 5000000, '1987-05-23 00:00:00', 'iya@yang.paling', 'palingbisa', 'palingpalingbisa', NULL),
(53, 'Jakarta', 'Non Binary', '394759204857394', 'Bismillah', '039485772834', 5000000, '2019-04-13 00:00:00', 'bis@mil.lah', 'basmalah', 'bismillahbisa', NULL),
(57, 'Lubang', 'bisa', '3175105308870925', 'Bismillah Bisa', '083894222358', 3480500, '1988-05-23 00:00:00', 'ayo@bisa.ya', 'bisadong', 'bismillabisayuk', 'false');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_nasabah` int(11) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `kode_transaksi` varchar(5) DEFAULT NULL,
  `rekening_tujuan` varchar(25) DEFAULT NULL,
  `trans_money` int(11) DEFAULT NULL,
  `transaction_date` date DEFAULT current_timestamp(),
  `tipe_transaksi` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_nasabah`, `username`, `kode_transaksi`, `rekening_tujuan`, `trans_money`, `transaction_date`, `tipe_transaksi`) VALUES
(36, 35, 'rneirn', '200', '', 5000000, '2020-12-20', 'Uang Masuk'),
(38, 37, 'cimot', '200', '', 5000000, '2020-12-22', 'Uang Masuk'),
(39, 35, 'rneirn', '014', '91785674', 500000, '2020-12-22', 'Transfer Uang'),
(40, 35, 'rneirn', '014', '91785674', 500000, '2020-12-22', 'Transfer Uang'),
(41, 35, 'rneirn', '014', '91785674', 500000, '2020-12-22', 'Transfer Uang'),
(42, 35, 'rneirn', '200', NULL, 6500, '2020-12-22', 'Biaya Admin'),
(44, 43, 'kelapamud', '200', NULL, 5000000, '2020-12-23', 'Uang Masuk'),
(46, 45, 'hebat', '200', NULL, 5000000, '2020-12-23', 'Uang Masuk'),
(48, 47, 'palingbisa', '200', NULL, 5000000, '2020-12-23', 'Uang Masuk'),
(54, 53, 'basmalah', '200', NULL, 5000000, '2020-12-23', 'Uang Masuk'),
(58, 57, 'bisadong', '200', NULL, 5000000, '2020-12-23', 'Uang Masuk'),
(59, 57, 'bisadong', '014', '91785674', 500000, '2020-12-23', 'Transfer Uang'),
(60, 57, 'bisadong', '200', NULL, 6500, '2020-12-23', 'Biaya Admin'),
(61, 57, 'bisadong', '014', '91785674', 500000, '2020-12-23', 'Transfer Uang'),
(62, 57, 'bisadong', '200', NULL, 6500, '2020-12-23', 'Biaya Admin'),
(64, 0, 'bisadong', '014', '91785874', 500000, '2020-12-23', 'Transfer Uang'),
(65, 0, 'bisadong', '200', NULL, 6500, '2020-12-23', 'Biaya Admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dummy`
--
ALTER TABLE `dummy`
  ADD PRIMARY KEY (`no_rek`);

--
-- Indexes for table `nasabah`
--
ALTER TABLE `nasabah`
  ADD PRIMARY KEY (`id_nasabah`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
