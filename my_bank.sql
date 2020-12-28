-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 28, 2020 at 05:02 AM
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
  `loginStatus` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dummy`
--

INSERT INTO `dummy` (`no_rek`, `kode_bank`, `nama_bank`, `nama_pemilik`, `username`, `password`, `saldo_dummy`, `loginStatus`) VALUES
('6304859233', '008', 'Mandiri', 'Imil Cangtip', 'cingmil', 'kucinghamil', 4100000, 'false'),
('91785674', '014', 'BCA', 'Kuma Ganteng', 'kumaku', 'kumapaling', 6500000, NULL),
('91785874', '014', 'BCA', 'Unying Ganteng', 'unych', 'unyingtjap', 6000000, NULL);

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
(110),
(110);

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
  `loginStatus` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nasabah`
--

INSERT INTO `nasabah` (`id_nasabah`, `alamat`, `gender`, `no_ktp`, `nama_lengkap`, `no_telp`, `saldo`, `birth_date`, `email`, `username`, `password`, `loginStatus`) VALUES
(35, 'Jakarta Timur', 'Wanita', '3175105303970014', 'Irene Nurintan', '083898224157', 3480500, '1997-03-13 00:00:00', 'rne@renee.co', 'rneirn', 'kumaganten', 'false'),
(37, 'Jakarta Timur', 'Pria', '3175105303970015', 'Cimot Bulat', '083898224158', 2974000, '2018-05-03 00:00:00', 'cimot@bulat.co', 'cimot', 'sayangcimo', 'false'),
(43, 'Jakarta Timur', 'Non Binary', '3175105302870015', 'Kelapa Muda', '083898223158', 3980500, '1987-02-03 00:00:00', 'kelapa@muda.co', 'kelapamud', 'kelapamuda', 'true'),
(45, 'Dahsyat', 'Dan Paling', '3175105305870015', 'Dia Pikir', '083898222158', 4493500, '1987-05-03 00:00:00', 'dia@yang.paling', 'hebat', 'merasapali', 'false'),
(47, 'Dahsyat', 'Danling', '3175105305870925', 'Bisa', '083898222358', 5000000, '1987-05-23 00:00:00', 'iya@yang.paling', 'palingbisa', 'palingpali', NULL),
(53, 'Jakarta', 'Non Binary', '394759204857394', 'Bismillah', '039485772834', 5000000, '2019-04-13 00:00:00', 'bis@mil.lah', 'basmalah', 'bismillahb', NULL),
(57, 'Lubang', 'bisa', '3175105308870925', 'Bismillah Bisa', '083894222358', 3480500, '1988-05-23 00:00:00', 'ayo@bisa.ya', 'bisadong', 'bismillabi', 'false'),
(66, 'Bekasi Utara', 'Pria', '3175394803928847', 'Kan An', '083894723812', 5000000, '1992-03-08 00:00:00', 'kan_an@gmail.com', 'ka_nan', 'pohoncemar', NULL),
(83, 'Pucuk', 'Non Binary', '3192857602934857', 'Es Teh Manis', '083928337234', 4493500, '1987-05-04 00:00:00', 'es@tehman.is', 'tehmanis', 'tehmanisse', 'false'),
(98, 'Lautan', 'Pria', '3175103948573648', 'Kerupuk Udang', '083898773567', 4280500, '1990-03-21 00:00:00', 'kerupuk@uda.ng', 'udank', 'kerupukdan', 'false'),
(102, 'Keramat', 'Pria', '318346356348', 'Namujoon', '083892773618', 5000000, '1994-08-04 00:00:00', 'nam@u.co', 'namu', 'namupohonku', 'false');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_nasabah` int(11) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `kode_transaksi` varchar(5) DEFAULT NULL,
  `rekening_tujuan` varchar(25) NOT NULL,
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
(42, 35, 'rneirn', '200', ' ', 6500, '2020-12-22', 'Biaya Admin'),
(44, 43, 'kelapamud', '200', '', 5000000, '2020-12-23', 'Uang Masuk'),
(46, 45, 'hebat', '200', '', 5000000, '2020-12-23', 'Uang Masuk'),
(48, 47, 'palingbisa', '200', '', 5000000, '2020-12-23', 'Uang Masuk'),
(54, 53, 'basmalah', '200', '', 5000000, '2020-12-23', 'Uang Masuk'),
(58, 57, 'bisadong', '200', '', 5000000, '2020-12-23', 'Uang Masuk'),
(59, 57, 'bisadong', '014', '91785674', 500000, '2020-12-23', 'Transfer Uang'),
(60, 57, 'bisadong', '200', '', 6500, '2020-12-23', 'Biaya Admin'),
(61, 57, 'bisadong', '014', '91785674', 500000, '2020-12-23', 'Transfer Uang'),
(62, 57, 'bisadong', '200', '', 6500, '2020-12-23', 'Biaya Admin'),
(64, 0, 'bisadong', '014', '91785874', 500000, '2020-12-23', 'Transfer Uang'),
(65, 0, 'bisadong', '200', '', 6500, '2020-12-23', 'Biaya Admin'),
(67, 66, 'ka_nan', '200', '', 5000000, '2020-12-23', 'Uang Masuk'),
(71, 37, 'cimot', '014', '91785874', 500000, '2020-12-23', 'Transfer Uang'),
(72, 0, 'cimot', '200', '', 6500, '2020-12-23', 'Biaya Admin'),
(73, 37, 'cimot', '014', '91785874', 500000, '2020-12-23', 'Transfer Uang'),
(74, 37, 'cimot', '200', '', 6500, '2020-12-23', 'Biaya Admin'),
(75, 37, 'cimot', '014', '91785874', 500000, '2020-12-23', 'Transfer Uang'),
(76, 37, 'cimot', '200', '', 6500, '2020-12-23', 'Biaya Admin'),
(77, 37, 'cimot', '014', '91785874', 500000, '2020-12-23', 'Transfer Uang'),
(78, 37, 'cimot', '200', '', 6500, '2020-12-23', 'Biaya Admin'),
(79, 43, 'kelapamud', NULL, '', 0, '2020-12-25', 'Transfer Uang'),
(80, 43, 'kelapamud', '200', '', 6500, '2020-12-25', 'Biaya Admin'),
(81, 43, 'kelapamud', '014', '91785874', 500000, '2020-12-25', 'Transfer Uang'),
(82, 43, 'kelapamud', '200', '', 6500, '2020-12-25', 'Biaya Admin'),
(84, 83, 'tehmanis', '200', '', 5000000, '2020-12-25', 'Uang Masuk'),
(85, 83, 'tehmanis', '008', '6304859233', 500000, '2020-12-25', 'Transfer Uang'),
(86, 83, 'tehmanis', '200', '', 6500, '2020-12-25', 'Biaya Admin'),
(87, 45, 'hebat', '008', '6304859233', 500000, '2020-12-25', 'Transfer Uang'),
(88, 45, 'hebat', '200', '', 6500, '2020-12-25', 'Biaya Admin'),
(92, 43, 'kelapamud', '014', '91785674', 500000, '2020-12-27', 'Transfer Uang'),
(93, 43, 'kelapamud', '200', '', 6500, '2020-12-27', 'Biaya Admin'),
(99, 98, 'udank', '200', 'Milik Sendiri', 5000000, '2020-12-27', 'Uang Masuk'),
(100, 98, 'udank', '014', '91785674', 500000, '2020-12-27', 'Transfer Uang'),
(101, 98, 'udank', '200', '', 6500, '2020-12-27', 'Biaya Admin'),
(103, 102, 'namu', '200', 'Milik Sendiri', 5000000, '2020-12-28', 'Uang Masuk'),
(104, 98, 'udank', '008', '63048659233', 100000, '2020-12-28', 'Transfer Uang'),
(105, 98, 'udank', '200', '', 6500, '2020-12-28', 'Biaya Admin'),
(106, 98, 'udank', '008', '6304859233', 100000, '2020-12-28', 'Transfer Uang'),
(107, 98, 'udank', '200', '', 6500, '2020-12-28', 'Biaya Admin');

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
