CREATE DATABASE  IF NOT EXISTS `hotelmanagementdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hotelmanagementdb`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: hotelmanagementdb
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `calamviec`
--

DROP TABLE IF EXISTS `calamviec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calamviec` (
  `MaCLV` int NOT NULL,
  `TenCLV` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `GioBatDau` time NOT NULL,
  `GioKetThuc` time NOT NULL,
  PRIMARY KEY (`MaCLV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calamviec`
--

LOCK TABLES `calamviec` WRITE;
/*!40000 ALTER TABLE `calamviec` DISABLE KEYS */;
INSERT INTO `calamviec` VALUES (1,'Ca sáng','07:00:00','15:00:00'),(2,'Ca chiều','15:00:00','23:00:00'),(3,'Ca đêm','23:00:00','07:00:00');
/*!40000 ALTER TABLE `calamviec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chucvu`
--

DROP TABLE IF EXISTS `chucvu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chucvu` (
  `MaCV` int NOT NULL,
  `TenChucVu` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `HeSoLuong` double NOT NULL,
  PRIMARY KEY (`MaCV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chucvu`
--

LOCK TABLES `chucvu` WRITE;
/*!40000 ALTER TABLE `chucvu` DISABLE KEYS */;
INSERT INTO `chucvu` VALUES (1,'Nhân viên lễ tân',1.2),(2,'Nhân viên dọn phòng',1.1),(3,'Quản lý',1.5);
/*!40000 ALTER TABLE `chucvu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dichvu`
--

DROP TABLE IF EXISTS `dichvu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dichvu` (
  `MaDV` varchar(20) NOT NULL,
  `TenDV` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `DonGiaDV` double NOT NULL,
  `MaLoaiDV` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`MaDV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dichvu`
--

LOCK TABLES `dichvu` WRITE;
/*!40000 ALTER TABLE `dichvu` DISABLE KEYS */;
INSERT INTO `dichvu` VALUES ('DV01','Nước uống',20000,'LDV01'),('DV02','Đồ ăn nhẹ',50000,'LDV02'),('DV03','Giặt ủi',100000,'LDV03');
/*!40000 ALTER TABLE `dichvu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoadon`
--

DROP TABLE IF EXISTS `hoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoadon` (
  `MaHD` int NOT NULL,
  `MaPhong` int NOT NULL,
  `NgayInHD` datetime NOT NULL,
  `TongTien` double NOT NULL,
  `MaNV` int DEFAULT NULL,
  PRIMARY KEY (`MaHD`),
  KEY `MaPhong` (`MaPhong`),
  KEY `MaNV` (`MaNV`),
  CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`MaPhong`) REFERENCES `phong` (`MaPhong`),
  CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoadon`
--

LOCK TABLES `hoadon` WRITE;
/*!40000 ALTER TABLE `hoadon` DISABLE KEYS */;
INSERT INTO `hoadon` VALUES (1,1,'2024-12-05 10:30:00',2040000,2),(2,2,'2024-12-06 11:30:00',1700000,3),(3,3,'2024-12-07 08:30:00',2400000,1);
/*!40000 ALTER TABLE `hoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khach`
--

DROP TABLE IF EXISTS `khach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khach` (
  `MaKhach` int NOT NULL,
  `TenKhach` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `NgaySinh` date NOT NULL,
  `SDT` varchar(11) NOT NULL,
  `CMND` varchar(20) NOT NULL,
  `Email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `QuocTich` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `GioiTinh` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`MaKhach`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khach`
--

LOCK TABLES `khach` WRITE;
/*!40000 ALTER TABLE `khach` DISABLE KEYS */;
INSERT INTO `khach` VALUES (1,'Nguyen Van K','1980-05-15','0987654321','123456789','nvk@example.com','Vietnam','Nam'),(2,'Tran Thi L','1995-07-25','0987654322','987654321','ttl@example.com','Vietnam','Nữ'),(3,'Le Van M','1988-03-10','0987654323','456789123','lvm@example.com','Vietnam','Nam'),(4,'Pham Thi N','1990-01-20','0987654324','123123123','ptn@example.com','Vietnam','Nữ'),(5,'Hoang Van O','1993-11-30','0987654325','987987987','hvo@example.com','Vietnam','Nam');
/*!40000 ALTER TABLE `khach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loaidv`
--

DROP TABLE IF EXISTS `loaidv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loaidv` (
  `MaLoaiDV` varchar(20) NOT NULL,
  `TenLoaiDV` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`MaLoaiDV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loaidv`
--

LOCK TABLES `loaidv` WRITE;
/*!40000 ALTER TABLE `loaidv` DISABLE KEYS */;
INSERT INTO `loaidv` VALUES ('LDV01','Dịch vụ nước uống'),('LDV02','Dịch vụ đồ ăn'),('LDV03','Dịch vụ giặt ủi');
/*!40000 ALTER TABLE `loaidv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loaiphong`
--

DROP TABLE IF EXISTS `loaiphong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loaiphong` (
  `MaLP` varchar(20) NOT NULL,
  `TenLoaiPhong` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`MaLP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loaiphong`
--

LOCK TABLES `loaiphong` WRITE;
/*!40000 ALTER TABLE `loaiphong` DISABLE KEYS */;
INSERT INTO `loaiphong` VALUES ('LP01','Phòng tiêu chuẩn'),('LP02','Phòng cao cấp'),('LP03','Phòng VIP');
/*!40000 ALTER TABLE `loaiphong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvien` (
  `MaNV` int NOT NULL,
  `TenNV` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `NgaySinh` date NOT NULL,
  `CMND` varchar(20) NOT NULL,
  `SDT` varchar(11) NOT NULL,
  `LuongCoBan` double NOT NULL,
  `TrangThai` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `GioiTinh` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `UserName` varchar(50) DEFAULT NULL,
  `Password` varchar(50) DEFAULT NULL,
  `Email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `NgayGiaNhap` date DEFAULT NULL,
  `HinhAnh` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `MaCLV` int DEFAULT NULL,
  `MaCV` int DEFAULT NULL,
  PRIMARY KEY (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvien`
--

LOCK TABLES `nhanvien` WRITE;
/*!40000 ALTER TABLE `nhanvien` DISABLE KEYS */;
INSERT INTO `nhanvien` VALUES (1,'Nguyen Van A','1990-01-01','123456789','0901234567',7000000,'Hoạt động','Nam','nva','password123','nva@example.com','2020-01-15',NULL,1,1),(2,'Tran Thi B','1992-05-10','987654321','0902234567',8000000,'Hoạt động','Nữ','ttb','password456','ttb@example.com','2021-03-20',NULL,2,2),(3,'Le Van C','1988-11-20','456789123','0903234567',7500000,'Hoạt động','Nam','lvc','password789','lvc@example.com','2022-06-01',NULL,3,1),(4,'Pham Thi D','1995-09-15','123123123','0904234567',6800000,'Nghỉ việc','Nữ','ptd','password012','ptd@example.com','2021-08-10',NULL,1,2),(5,'Hoang Van E','1993-04-30','987987987','0905234567',9000000,'Hoạt động','Nam','hve','password345','hve@example.com','2020-09-05',NULL,2,1);
/*!40000 ALTER TABLE `nhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieudatphong`
--

DROP TABLE IF EXISTS `phieudatphong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieudatphong` (
  `MaPDP` int NOT NULL,
  `MaPhong` int DEFAULT NULL,
  `MaKhach` int DEFAULT NULL,
  `MaNV` int DEFAULT NULL,
  `NgayDatPhong` datetime NOT NULL,
  `TraPhong` datetime NOT NULL,
  `DonGiaThue` double NOT NULL,
  `DonGiaPhong` double DEFAULT NULL,
  PRIMARY KEY (`MaPDP`),
  KEY `MaPhong` (`MaPhong`),
  KEY `MaKhach` (`MaKhach`),
  KEY `MaNV` (`MaNV`),
  CONSTRAINT `phieudatphong_ibfk_1` FOREIGN KEY (`MaPhong`) REFERENCES `phong` (`MaPhong`),
  CONSTRAINT `phieudatphong_ibfk_2` FOREIGN KEY (`MaKhach`) REFERENCES `khach` (`MaKhach`),
  CONSTRAINT `phieudatphong_ibfk_3` FOREIGN KEY (`MaNV`) REFERENCES `nhanvien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieudatphong`
--

LOCK TABLES `phieudatphong` WRITE;
/*!40000 ALTER TABLE `phieudatphong` DISABLE KEYS */;
INSERT INTO `phieudatphong` VALUES (1,1,1,2,'2024-12-01 12:00:00','2024-12-05 10:00:00',2000000,500000),(2,2,2,3,'2024-12-03 14:00:00','2024-12-06 11:00:00',1650000,550000),(3,3,3,1,'2024-12-04 18:00:00','2024-12-07 08:00:00',2100000,700000);
/*!40000 ALTER TABLE `phieudatphong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieudv`
--

DROP TABLE IF EXISTS `phieudv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieudv` (
  `MaPhieuDV` int NOT NULL,
  `MaPhong` int DEFAULT NULL,
  `MaDV` varchar(20) DEFAULT NULL,
  `NgaySD` datetime NOT NULL,
  `TienDV` double NOT NULL,
  `SoLuong` int NOT NULL,
  `GiaDV` double DEFAULT NULL,
  PRIMARY KEY (`MaPhieuDV`),
  KEY `MaPhong` (`MaPhong`),
  KEY `MaDV` (`MaDV`),
  CONSTRAINT `phieudv_ibfk_1` FOREIGN KEY (`MaPhong`) REFERENCES `phong` (`MaPhong`),
  CONSTRAINT `phieudv_ibfk_2` FOREIGN KEY (`MaDV`) REFERENCES `dichvu` (`MaDV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieudv`
--

LOCK TABLES `phieudv` WRITE;
/*!40000 ALTER TABLE `phieudv` DISABLE KEYS */;
INSERT INTO `phieudv` VALUES (1,1,'DV01','2024-12-01 13:00:00',20000,2,40000),(2,2,'DV02','2024-12-03 16:00:00',50000,1,50000),(3,3,'DV03','2024-12-04 20:00:00',100000,3,300000);
/*!40000 ALTER TABLE `phieudv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong`
--

DROP TABLE IF EXISTS `phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phong` (
  `MaPhong` int NOT NULL,
  `TenPhong` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `SoNguoi` int NOT NULL,
  `DonGia` double NOT NULL,
  `TrangThai` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `MaLP` varchar(20) DEFAULT NULL,
  `Tang` int NOT NULL,
  PRIMARY KEY (`MaPhong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong`
--

LOCK TABLES `phong` WRITE;
/*!40000 ALTER TABLE `phong` DISABLE KEYS */;
INSERT INTO `phong` VALUES (1,'Phong 101',2,500000,'Trống','LP01',1),(2,'Phong 102',2,550000,'Đang sử dụng','LP02',1),(3,'Phong 201',3,700000,'Trống','LP01',2),(4,'Phong 202',4,850000,'Đang bảo trì','LP03',2),(5,'Phong 301',4,900000,'Trống','LP03',3);
/*!40000 ALTER TABLE `phong` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-06 21:24:17
