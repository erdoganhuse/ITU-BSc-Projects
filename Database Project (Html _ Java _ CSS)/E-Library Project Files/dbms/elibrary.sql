-- MySQL dump 10.13  Distrib 5.5.31, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: elibrary
-- ------------------------------------------------------
-- Server version	5.5.31-0+wheezy1-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DVD_categories`
--

DROP TABLE IF EXISTS `DVD_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DVD_categories` (
  `DVD_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `DVD_category_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`DVD_category_id`),
  UNIQUE KEY `DVD_category_name_UNIQUE` (`DVD_category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DVD_categories`
--

LOCK TABLES `DVD_categories` WRITE;
/*!40000 ALTER TABLE `DVD_categories` DISABLE KEYS */;
INSERT INTO `DVD_categories` VALUES (5,'Documentary'),(3,'Education'),(2,'Game'),(1,'Movie'),(4,'Song');
/*!40000 ALTER TABLE `DVD_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `DVD_informations`
--

DROP TABLE IF EXISTS `DVD_informations`;
/*!50001 DROP VIEW IF EXISTS `DVD_informations`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `DVD_informations` (
  `record_id` tinyint NOT NULL,
  `DVD_id` tinyint NOT NULL,
  `DVD_name` tinyint NOT NULL,
  `DVD_publication_date` tinyint NOT NULL,
  `DVD_category_name` tinyint NOT NULL,
  `DVD_rental_count` tinyint NOT NULL,
  `DVD_duration` tinyint NOT NULL,
  `library_name` tinyint NOT NULL,
  `floor_name` tinyint NOT NULL,
  `bookcase_id` tinyint NOT NULL,
  `bookshelf_id` tinyint NOT NULL,
  `bookcase_name` tinyint NOT NULL,
  `bookshelf_name` tinyint NOT NULL,
  `DVD_column` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `available` tinyint NOT NULL,
  `url` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `DVDs`
--

DROP TABLE IF EXISTS `DVDs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DVDs` (
  `DVD_id` int(11) NOT NULL AUTO_INCREMENT,
  `DVD_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `DVD_duration` int(11) DEFAULT NULL,
  `DVD_publication_date` date DEFAULT NULL,
  `DVD_rental_count` int(11) DEFAULT '0',
  `DVD_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`DVD_id`),
  UNIQUE KEY `DVD_name_UNIQUE` (`DVD_name`,`DVD_publication_date`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DVDs`
--

LOCK TABLES `DVDs` WRITE;
/*!40000 ALTER TABLE `DVDs` DISABLE KEYS */;
INSERT INTO `DVDs` VALUES (1,'Sefiller',150,'2012-01-01',0,1),(2,'Beatles - Please Please Me',60,'1963-01-01',0,4),(3,'Cosmos',60,'1980-01-01',0,5),(4,'Battlefield',99,'2013-01-01',0,2),(5,'Serenade',4,'2007-01-01',0,4),(8,'Best of Mozart',126,'2000-01-01',1,4),(9,'Planet Earth',44,'2006-01-01',0,5);
/*!40000 ALTER TABLE `DVDs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authors` (
  `author_id` int(11) NOT NULL AUTO_INCREMENT,
  `author_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `author_information` mediumtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`author_id`),
  UNIQUE KEY `author_name_UNIQUE` (`author_name`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,'Feyzli Yazar',NULL),(2,'Reşat Nuri',NULL),(3,'J.R.R. Tolkien',NULL),(4,'Dante Alieghieri',NULL),(5,'Ahmet Hamdi Tanpınar',NULL),(6,'Derp Derpson',NULL),(7,'İskender Pala',NULL),(8,'Paul Auster',NULL),(9,'Albert Camus',NULL),(10,'Marcel Proust',NULL),(11,'Franz Kafka',NULL),(12,'Antoine de Saint-Exupéry',NULL),(13,'André Malraux',NULL),(14,'Louis-Ferdinand Céline',NULL),(15,'John Steinbeck',NULL),(16,'Ernest Hemingway',NULL),(17,'Alain-Fournier',NULL),(18,'Boris Vian',NULL),(19,'Simone de Beauvoir',NULL),(20,'Samuel Beckett',NULL),(21,'Jean-Paul Sartre Nobel',NULL),(22,'Umberto Eco',NULL),(23,'Aleksandr Soljenitsin',NULL),(24,'Guillaume Apollinaire',NULL),(25,'Hergé',NULL),(26,'Claude Lévi-Strauss',NULL),(27,'Aldous Huxley',NULL),(28,'George Orwell',NULL),(29,'René Goscinny',NULL),(30,'Eugène Ionesco',NULL),(31,'Marguerite Yourcenar',NULL),(32,'Vladimir Nabokov',NULL),(33,'William Faulkner',NULL),(34,'Margaret Mitchell',NULL),(35,'Françoise Sagan',NULL),(37,'Hugo Pratt',NULL),(38,'Fishbane Thorton',NULL),(39,'Osman Yazıcıoğlu',NULL),(40,'Mehmet Bakioğlu',NULL),(41,'M. Kaya Yazgan',NULL),(42,'Barry Strauss',NULL),(43,'Ali Ahmetbeyoğlu',NULL),(44,'John Gribbin',NULL),(45,'Brian Greene',NULL),(46,'John Gertner',NULL),(47,'Katherine Webb',NULL),(48,'Lesley Livingston',NULL),(50,'45',NULL),(57,'Bill Gates',NULL),(62,'Richard Stallman',NULL);
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_categories`
--

DROP TABLE IF EXISTS `book_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_categories` (
  `book_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_category_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`book_category_id`),
  UNIQUE KEY `book_category_name_UNIQUE` (`book_category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_categories`
--

LOCK TABLES `book_categories` WRITE;
/*!40000 ALTER TABLE `book_categories` DISABLE KEYS */;
INSERT INTO `book_categories` VALUES (5,'Education'),(8,'Engineering'),(7,'History'),(1,'Novel'),(3,'Science'),(2,'Story'),(6,'Technology');
/*!40000 ALTER TABLE `book_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `book_informations`
--

DROP TABLE IF EXISTS `book_informations`;
/*!50001 DROP VIEW IF EXISTS `book_informations`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `book_informations` (
  `record_id` tinyint NOT NULL,
  `book_id` tinyint NOT NULL,
  `book_name` tinyint NOT NULL,
  `author_name` tinyint NOT NULL,
  `book_category_name` tinyint NOT NULL,
  `book_publication_year` tinyint NOT NULL,
  `book_ISBN` tinyint NOT NULL,
  `book_rental_count` tinyint NOT NULL,
  `library_name` tinyint NOT NULL,
  `floor_name` tinyint NOT NULL,
  `bookcase_id` tinyint NOT NULL,
  `bookshelf_id` tinyint NOT NULL,
  `bookcase_name` tinyint NOT NULL,
  `bookshelf_name` tinyint NOT NULL,
  `book_column` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `available` tinyint NOT NULL,
  `url` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `bookcases`
--

DROP TABLE IF EXISTS `bookcases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookcases` (
  `bookcase_id` int(11) NOT NULL AUTO_INCREMENT,
  `floor_id` int(11) NOT NULL,
  `bookcase_name` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`bookcase_id`),
  KEY `BC_FL_FK` (`floor_id`),
  CONSTRAINT `BC_FL_FK` FOREIGN KEY (`floor_id`) REFERENCES `floors` (`floor_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookcases`
--

LOCK TABLES `bookcases` WRITE;
/*!40000 ALTER TABLE `bookcases` DISABLE KEYS */;
INSERT INTO `bookcases` VALUES (1,1,'a'),(2,1,'b'),(3,1,'c'),(4,2,'d'),(5,2,'e'),(6,3,'f'),(7,3,'g'),(8,3,'h'),(9,4,'j'),(10,4,'k'),(11,4,'l'),(12,5,'m'),(13,6,'n'),(14,6,'o');
/*!40000 ALTER TABLE `bookcases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `books` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `author_id` int(11) NOT NULL,
  `book_publication_year` year(4) DEFAULT NULL,
  `book_ISBN` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `book_rental_count` int(11) DEFAULT '0',
  `book_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `book_ISBN_UNIQUE` (`book_ISBN`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Görünmeyen',8,1968,'123',0,1),(2,'Yabancı',9,1942,'1',0,1),(3,'Kayıp Zamanın İzinde',10,1913,'2',0,1),(4,'Dava',11,1925,'3',1,1),(5,'Küçük Prens',12,1943,'5',0,2),(6,'İnsanlık Durumu',13,1933,'7',0,1),(7,'Gecenin Sonuna Yolculuk',14,1932,'9',0,7),(8,'Gazap Üzümleri',15,1939,'11',0,7),(9,'Çanlar Kimin İçin Çalıyor',16,1940,'14',0,1),(10,'Adsız Ülke',17,1913,'18',0,2),(11,'Günlerin Köpüğü',18,1947,'35',0,2),(12,'İkinci Cins',19,1949,'32',0,1),(13,'Varlık ve Hiçlik',21,1943,'34',0,1),(15,'Gulag Takımadaları',23,1973,'38',0,7),(16,'Alkoller',24,1913,'39',0,2),(17,'Mavi Lotus',25,1936,'40',0,1),(18,'Hüzünlü Dönenceler',26,1955,'41',0,1),(19,'Cesur Yeni Dünya',27,1932,'42',0,1),(20,'Bin Dokuz Yüz Seksen Dört',28,1949,'43',0,1),(21,'Galyalı Asteriks',29,1959,'45',0,2),(22,'Kel Kantocu',30,1953,'46',0,7),(23,'Zenon',31,1968,'47',0,7),(24,'Lolita',32,1955,'50',0,5),(25,'Ses ve Öfke',33,1929,'51',0,6),(26,'Rüzgar Gibi Geçti',34,1936,'54',0,1),(27,'Günaydın Hüzün',35,1954,'55',0,8),(28,'Lord of the Rings',3,1954,'56',0,1),(29,'Bir Tuz Denizi Şarkısı',37,1967,'59',0,1),(30,'Temel Fizik',38,1999,'61',0,5),(31,'Temel Mukavemet',39,1998,'9754867065',0,8),(32,'Mühendislik Mekaniği Dinamik',40,2012,'9786053777557',0,5),(33,'Sayısal Elektronik',41,2013,'9786051334738',0,8),(34,'Tarihe Yön Veren Büyük Komutanlar',42,2013,'9786050203073',0,7),(35,'Avrupa Hunları',43,2010,'9786055200282',0,7),(36,'Erwin Schrödinger ve Kuantum Devrimi',44,2000,'9786051067933',0,3),(37,'Saklı Gerçekler',45,2005,'9789754037807',0,3),(38,'Fikir Fabrikası',46,2008,'9786058651845',0,3),(39,'Büyükannemin Sandığı',47,2013,'9786053431916',0,2),(40,'Karanlık Işık',48,2013,'9786051424002',0,2),(42,'Why Linux is better than Windows',57,2000,'456123784296',0,6),(43,'My biggest regret, Windows',57,2001,'5787146582154',0,6),(44,'Please accept my apologize',57,2002,'666',0,6),(45,'Torvalds is our lord and savior',57,2013,'789632145',0,6),(46,'Free as in Freedom',62,2002,'0983159211',0,6),(47,'İlahi Komedya-Cehennem',4,2013,'978-975-21-1681-8',0,1);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookshelves`
--

DROP TABLE IF EXISTS `bookshelves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookshelves` (
  `bookshelf_id` int(11) NOT NULL AUTO_INCREMENT,
  `bookcase_id` int(11) NOT NULL,
  `bookshelf_name` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `book_limit` int(11) DEFAULT '50',
  PRIMARY KEY (`bookshelf_id`),
  KEY `BS_BC_FK` (`bookcase_id`),
  CONSTRAINT `BS_BC_FK` FOREIGN KEY (`bookcase_id`) REFERENCES `bookcases` (`bookcase_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookshelves`
--

LOCK TABLES `bookshelves` WRITE;
/*!40000 ALTER TABLE `bookshelves` DISABLE KEYS */;
INSERT INTO `bookshelves` VALUES (9,1,'aa',50),(10,2,'ba',50),(11,2,'bb',50),(12,3,'ca',50),(13,3,'cb',50),(14,3,'cc',50),(15,4,'da',50),(16,4,'db',50),(17,4,'dc',50),(18,4,'dd',50),(19,5,'ea',50),(20,5,'eb',50),(21,6,'fa',50),(22,6,'fb',50),(23,7,'ga',50),(24,7,'gb',50),(25,8,'h',50),(26,9,'k',50),(27,10,'j',50),(28,11,'l',50),(29,12,'m',50),(30,13,'n',50),(31,14,'o',50);
/*!40000 ALTER TABLE `bookshelves` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `current_logs`
--

DROP TABLE IF EXISTS `current_logs`;
/*!50001 DROP VIEW IF EXISTS `current_logs`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `current_logs` (
  `max(log_id)` tinyint NOT NULL,
  `record_id` tinyint NOT NULL,
  `expected_returning_date` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `electronic_DVDs`
--

DROP TABLE IF EXISTS `electronic_DVDs`;
/*!50001 DROP VIEW IF EXISTS `electronic_DVDs`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `electronic_DVDs` (
  `record_id` tinyint NOT NULL,
  `DVD_id` tinyint NOT NULL,
  `DVD_name` tinyint NOT NULL,
  `DVD_publication_date` tinyint NOT NULL,
  `DVD_category_name` tinyint NOT NULL,
  `DVD_rental_count` tinyint NOT NULL,
  `DVD_duration` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `url` tinyint NOT NULL,
  `available` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `electronic_books`
--

DROP TABLE IF EXISTS `electronic_books`;
/*!50001 DROP VIEW IF EXISTS `electronic_books`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `electronic_books` (
  `record_id` tinyint NOT NULL,
  `book_id` tinyint NOT NULL,
  `book_name` tinyint NOT NULL,
  `author_name` tinyint NOT NULL,
  `book_category_name` tinyint NOT NULL,
  `book_publication_year` tinyint NOT NULL,
  `book_ISBN` tinyint NOT NULL,
  `book_rental_count` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `url` tinyint NOT NULL,
  `available` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `electronic_magazines`
--

DROP TABLE IF EXISTS `electronic_magazines`;
/*!50001 DROP VIEW IF EXISTS `electronic_magazines`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `electronic_magazines` (
  `record_id` tinyint NOT NULL,
  `magazine_id` tinyint NOT NULL,
  `magazine_name` tinyint NOT NULL,
  `magazine_category_name` tinyint NOT NULL,
  `magazine_publication_date` tinyint NOT NULL,
  `magazine_issue_number` tinyint NOT NULL,
  `magazine_rental_count` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `url` tinyint NOT NULL,
  `available` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `electronic_resources`
--

DROP TABLE IF EXISTS `electronic_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `electronic_resources` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`record_id`),
  CONSTRAINT `ER_RECORD_FK` FOREIGN KEY (`record_id`) REFERENCES `records` (`record_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `electronic_resources`
--

LOCK TABLES `electronic_resources` WRITE;
/*!40000 ALTER TABLE `electronic_resources` DISABLE KEYS */;
INSERT INTO `electronic_resources` VALUES (97,'http://tr.wikipedia.org/wiki/Corto_Maltese'),(112,'http://www.idefix.com/kitap/dava-franz-kafka/'),(125,'http://www.idefix.com/Kitap/tanim.asp?sid=F5R8GROXHY4CNFIFTD63&searchstring=yabanc%C4%B1'),(126,'http://www.idefix.com/kitap/insanlik-durumu-a'),(127,'http://www.idefix.com/kitap/asteriks-galyali-asteriks-albert-uderzo/tanim.asp?sid=IOSXS45OOR1RH81C4PQQ'),(129,'http://www.idefix.com/Kitap/tanim.asp?sid=JCPNTZFNNG5GSGUKT1Q7&searchstring=R%C3%BCzgar%20Gibi%20Ge%C3%A7ti'),(130,'http://www.idefix.com/kitap/gazap-uzumleri-john-steinbeck/tanim.asp?sid=XXX7BMPY11NOKPZNN7GU'),(131,'http://en.wikipedia.org/wiki/Cosmos:_A_Personal_Voyage'),(139,'http://oyungezer.com.tr'),(140,'http://www.penguen.com'),(141,'http://tr.wikipedia.org/wiki/Please_Please_Me'),(143,'http://www.chip.com.tr/'),(145,'http://www.whylinuxisbetter.net/'),(150,'http://static.fsf.org/nosvn/faif-2.0.pdf'),(151,'http://www.youtube.com/watch?v=JtA9Js-22ko'),(153,'http://www.youtube.com/watch?v=Rb0UmrCXxVA'),(164,'http://www.idefix.com/kitap/sakli-gercekler-brian-greene/tanim.asp?sid=D6TC2MEUVG3GFGKS8625'),(165,'http://www.imdb.com/title/tt0795176/');
/*!40000 ALTER TABLE `electronic_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `floors`
--

DROP TABLE IF EXISTS `floors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `floors` (
  `floor_id` int(11) NOT NULL AUTO_INCREMENT,
  `library_id` int(11) NOT NULL,
  `floor_name` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`floor_id`,`library_id`,`floor_name`),
  KEY `FL_LB_FK` (`library_id`),
  CONSTRAINT `FL_LB_FK` FOREIGN KEY (`library_id`) REFERENCES `libraries` (`library_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `floors`
--

LOCK TABLES `floors` WRITE;
/*!40000 ALTER TABLE `floors` DISABLE KEYS */;
INSERT INTO `floors` VALUES (1,1,'m1'),(2,1,'m2'),(3,1,'m3'),(4,2,'rz'),(5,2,'r1'),(6,2,'r2');
/*!40000 ALTER TABLE `floors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libraries`
--

DROP TABLE IF EXISTS `libraries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libraries` (
  `library_id` int(11) NOT NULL AUTO_INCREMENT,
  `library_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `library_address` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`library_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libraries`
--

LOCK TABLES `libraries` WRITE;
/*!40000 ALTER TABLE `libraries` DISABLE KEYS */;
INSERT INTO `libraries` VALUES (1,'Mustafa İnan Kütüphanesi','Maslak'),(2,'Makine Fakültesi Ratip Berker Kütüphanesi','Gümüşsuyu');
/*!40000 ALTER TABLE `libraries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `record_id` int(11) NOT NULL,
  `renting_date` date NOT NULL,
  `expected_returning_date` date DEFAULT NULL,
  `returning_date` date DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `LOG_RECORDS_FK` (`record_id`),
  CONSTRAINT `LOG_RECORDS_FK` FOREIGN KEY (`record_id`) REFERENCES `records` (`record_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES (1,45,72,'2013-12-15','2014-01-14',NULL),(2,1,136,'2013-12-15','2014-01-14',NULL),(3,40,155,'2013-12-16','2014-01-15',NULL),(4,40,119,'2013-12-16','2014-01-15',NULL);
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `magazine_categories`
--

DROP TABLE IF EXISTS `magazine_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazine_categories` (
  `magazine_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `magazine_category_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`magazine_category_id`),
  UNIQUE KEY `magazine_category_name_UNIQUE` (`magazine_category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazine_categories`
--

LOCK TABLES `magazine_categories` WRITE;
/*!40000 ALTER TABLE `magazine_categories` DISABLE KEYS */;
INSERT INTO `magazine_categories` VALUES (6,'Comic'),(5,'Computer'),(4,'Engineering'),(3,'Life'),(2,'Science');
/*!40000 ALTER TABLE `magazine_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `magazine_informations`
--

DROP TABLE IF EXISTS `magazine_informations`;
/*!50001 DROP VIEW IF EXISTS `magazine_informations`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `magazine_informations` (
  `record_id` tinyint NOT NULL,
  `magazine_id` tinyint NOT NULL,
  `magazine_name` tinyint NOT NULL,
  `magazine_category_name` tinyint NOT NULL,
  `magazine_publication_date` tinyint NOT NULL,
  `magazine_issue_number` tinyint NOT NULL,
  `magazine_rental_count` tinyint NOT NULL,
  `library_name` tinyint NOT NULL,
  `floor_name` tinyint NOT NULL,
  `bookcase_id` tinyint NOT NULL,
  `bookshelf_id` tinyint NOT NULL,
  `bookcase_name` tinyint NOT NULL,
  `bookshelf_name` tinyint NOT NULL,
  `book_column` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `available` tinyint NOT NULL,
  `url` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `magazines`
--

DROP TABLE IF EXISTS `magazines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `magazines` (
  `magazine_id` int(11) NOT NULL AUTO_INCREMENT,
  `magazine_name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `magazine_issue_number` int(11) DEFAULT NULL,
  `magazine_publication_date` date DEFAULT NULL,
  `magazine_rental_count` int(11) DEFAULT '0',
  `magazine_category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`magazine_id`),
  UNIQUE KEY `index2` (`magazine_name`,`magazine_issue_number`,`magazine_publication_date`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `magazines`
--

LOCK TABLES `magazines` WRITE;
/*!40000 ALTER TABLE `magazines` DISABLE KEYS */;
INSERT INTO `magazines` VALUES (1,'Penguen',1,'2004-01-01',0,6),(2,'Penguen',2,'2005-01-01',1,6),(3,'Chip',10,'1992-01-01',0,5),(4,'IEEE',5,'1960-01-01',0,2),(6,'Time',6,'1923-01-01',0,3),(8,'Oyungezer',6,'2005-01-01',1,5);
/*!40000 ALTER TABLE `magazines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `physical_DVDs`
--

DROP TABLE IF EXISTS `physical_DVDs`;
/*!50001 DROP VIEW IF EXISTS `physical_DVDs`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `physical_DVDs` (
  `record_id` tinyint NOT NULL,
  `DVD_id` tinyint NOT NULL,
  `DVD_name` tinyint NOT NULL,
  `DVD_publication_date` tinyint NOT NULL,
  `DVD_category_name` tinyint NOT NULL,
  `DVD_rental_count` tinyint NOT NULL,
  `DVD_duration` tinyint NOT NULL,
  `library_name` tinyint NOT NULL,
  `floor_name` tinyint NOT NULL,
  `bookcase_id` tinyint NOT NULL,
  `bookshelf_id` tinyint NOT NULL,
  `bookcase_name` tinyint NOT NULL,
  `bookshelf_name` tinyint NOT NULL,
  `DVD_column` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `available` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `physical_books`
--

DROP TABLE IF EXISTS `physical_books`;
/*!50001 DROP VIEW IF EXISTS `physical_books`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `physical_books` (
  `record_id` tinyint NOT NULL,
  `book_id` tinyint NOT NULL,
  `book_name` tinyint NOT NULL,
  `author_name` tinyint NOT NULL,
  `book_category_name` tinyint NOT NULL,
  `book_publication_year` tinyint NOT NULL,
  `book_ISBN` tinyint NOT NULL,
  `book_rental_count` tinyint NOT NULL,
  `library_name` tinyint NOT NULL,
  `floor_name` tinyint NOT NULL,
  `bookcase_id` tinyint NOT NULL,
  `bookshelf_id` tinyint NOT NULL,
  `bookcase_name` tinyint NOT NULL,
  `bookshelf_name` tinyint NOT NULL,
  `book_column` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `available` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `physical_magazines`
--

DROP TABLE IF EXISTS `physical_magazines`;
/*!50001 DROP VIEW IF EXISTS `physical_magazines`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `physical_magazines` (
  `record_id` tinyint NOT NULL,
  `magazine_id` tinyint NOT NULL,
  `magazine_name` tinyint NOT NULL,
  `magazine_category_name` tinyint NOT NULL,
  `magazine_publication_date` tinyint NOT NULL,
  `magazine_issue_number` tinyint NOT NULL,
  `magazine_rental_count` tinyint NOT NULL,
  `library_name` tinyint NOT NULL,
  `floor_name` tinyint NOT NULL,
  `bookcase_id` tinyint NOT NULL,
  `bookshelf_id` tinyint NOT NULL,
  `bookcase_name` tinyint NOT NULL,
  `bookshelf_name` tinyint NOT NULL,
  `book_column` tinyint NOT NULL,
  `physical_electronic` tinyint NOT NULL,
  `available` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `physical_resources`
--

DROP TABLE IF EXISTS `physical_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physical_resources` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `bookshelf_id` int(11) NOT NULL,
  `book_column` int(11) DEFAULT NULL,
  `current_log_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`record_id`),
  UNIQUE KEY `index2` (`bookshelf_id`,`book_column`),
  CONSTRAINT `PR_RECORDS_FK` FOREIGN KEY (`record_id`) REFERENCES `records` (`record_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physical_resources`
--

LOCK TABLES `physical_resources` WRITE;
/*!40000 ALTER TABLE `physical_resources` DISABLE KEYS */;
INSERT INTO `physical_resources` VALUES (69,24,10,0),(70,10,1,0),(71,11,1,0),(72,12,4,1),(73,12,6,0),(74,13,2,0),(75,14,3,0),(76,14,20,0),(77,15,2,0),(78,16,37,0),(79,17,24,0),(80,18,7,0),(81,20,6,0),(82,21,3,0),(83,20,9,0),(84,21,6,0),(85,22,36,0),(86,22,21,0),(87,23,8,0),(88,24,34,0),(89,25,6,0),(90,26,25,0),(91,27,5,0),(92,28,15,0),(93,29,6,0),(95,31,9,0),(96,31,15,0),(98,17,36,0),(99,22,11,0),(100,29,50,0),(101,23,18,0),(102,17,48,0),(103,25,47,0),(104,15,46,0),(105,29,45,0),(106,29,43,0),(107,10,43,0),(108,17,42,0),(109,29,41,0),(111,19,6,0),(114,11,36,0),(115,17,10,0),(116,25,28,0),(117,30,27,0),(118,15,20,0),(119,16,23,4),(120,20,25,0),(121,11,18,0),(122,12,17,0),(123,16,6,0),(124,19,16,0),(128,19,11,0),(132,19,5,0),(136,10,3,2),(142,29,32,0),(144,19,28,0),(146,11,48,0),(147,24,46,0),(148,25,37,0),(149,30,20,0),(152,19,20,0),(154,26,1,0),(155,9,1,3),(158,17,6,0),(159,19,7,0),(160,10,5,0);
/*!40000 ALTER TABLE `physical_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `records`
--

DROP TABLE IF EXISTS `records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `records` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL,
  `x_id` int(11) NOT NULL,
  `physical-electronic` bit(1) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `records`
--

LOCK TABLES `records` WRITE;
/*!40000 ALTER TABLE `records` DISABLE KEYS */;
INSERT INTO `records` VALUES (69,1,1,''),(70,1,2,''),(71,1,3,''),(72,1,4,''),(73,1,5,''),(74,1,6,''),(75,1,7,''),(76,1,8,''),(77,1,9,''),(78,1,10,''),(79,1,11,''),(80,1,12,''),(81,1,13,''),(82,1,11,''),(83,1,15,''),(84,1,16,''),(85,1,17,''),(86,1,18,''),(87,1,19,''),(88,1,20,''),(89,1,21,''),(90,1,22,''),(91,1,23,''),(92,1,24,''),(93,1,25,''),(94,1,26,''),(95,1,27,''),(96,1,28,''),(97,1,29,'\0'),(98,1,30,''),(99,1,31,''),(100,1,32,''),(101,1,33,''),(102,1,34,''),(103,1,35,''),(104,1,36,''),(105,1,37,''),(106,1,38,''),(107,1,39,''),(108,1,40,''),(109,1,9,''),(110,1,1,'\0'),(111,1,21,''),(112,1,4,'\0'),(114,1,1,''),(115,1,2,''),(116,1,3,''),(117,1,4,''),(118,2,1,''),(119,2,2,''),(120,2,3,''),(121,2,4,''),(122,2,6,''),(123,1,22,''),(124,1,4,''),(125,1,2,'\0'),(126,1,6,'\0'),(127,1,21,'\0'),(128,1,26,''),(129,1,26,'\0'),(130,1,8,'\0'),(131,3,3,'\0'),(132,2,3,''),(136,2,8,''),(139,2,8,'\0'),(140,2,1,'\0'),(141,3,2,'\0'),(142,3,2,''),(143,2,3,'\0'),(144,1,42,''),(145,1,42,'\0'),(146,1,43,''),(147,1,44,''),(148,1,45,''),(149,1,46,''),(150,1,46,'\0'),(151,3,5,'\0'),(152,3,8,''),(153,3,8,'\0'),(154,3,8,''),(155,3,8,''),(156,3,8,''),(157,3,8,''),(158,1,29,''),(159,3,5,''),(160,3,3,''),(161,3,3,''),(162,3,3,''),(163,2,1,''),(164,1,37,'\0'),(165,3,9,'\0');
/*!40000 ALTER TABLE `records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suggestions`
--

DROP TABLE IF EXISTS `suggestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suggestions` (
  `suggestion_id` int(11) NOT NULL AUTO_INCREMENT,
  `suggestion` text NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`suggestion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suggestions`
--

LOCK TABLES `suggestions` WRITE;
/*!40000 ALTER TABLE `suggestions` DISABLE KEYS */;
INSERT INTO `suggestions` VALUES (1,'Site çok güzel olmuş elinize sağlık',2),(2,'Bu siteyi sürekli kullanıyorum harikasınız',NULL),(3,'Mustafa hariç geri kalanınızı çok seviyorum, başarılar',NULL);
/*!40000 ALTER TABLE `suggestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `types` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_name_UNIQUE` (`type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (1,'Book'),(3,'DVD'),(2,'Magazine');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_nickname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `user_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `user_surname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `user_password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `user_authority_state` bit(1) DEFAULT b'0',
  `user_last_access_time` datetime DEFAULT NULL,
  `user_point` varchar(45) COLLATE utf8_unicode_ci DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_nickname_UNIQUE` (`user_nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'tugrulyatagan','Tuğrul','Yatağan','900150983cd24fb0d6963f7d28e17f72','\0','2013-12-15 14:43:11','0'),(39,'a','Emre','Gökrem','0cc175b9c0f1b6a831c399e269772661','','2013-12-16 20:26:24','0'),(40,'erdogan','Hüseyin','Erdoğan','202cb962ac59075b964b07152d234b70','\0','2013-12-16 19:31:52','0'),(45,'uye','Üye','Kullanıcı','41b3b100f2c1686edef944ef821679b8','\0','2013-12-16 19:16:57','0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waiting_resources`
--

DROP TABLE IF EXISTS `waiting_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `waiting_resources` (
  `user_id` int(11) NOT NULL,
  `record_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waiting_resources`
--

LOCK TABLES `waiting_resources` WRITE;
/*!40000 ALTER TABLE `waiting_resources` DISABLE KEYS */;
INSERT INTO `waiting_resources` VALUES (1,2),(2,2),(2,17),(2,36),(2,43),(3,7),(4,4),(40,36),(40,39),(40,43),(40,136),(40,155);
/*!40000 ALTER TABLE `waiting_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `DVD_informations`
--

/*!50001 DROP TABLE IF EXISTS `DVD_informations`*/;
/*!50001 DROP VIEW IF EXISTS `DVD_informations`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `DVD_informations` AS select `physical_DVDs`.`record_id` AS `record_id`,`physical_DVDs`.`DVD_id` AS `DVD_id`,`physical_DVDs`.`DVD_name` AS `DVD_name`,`physical_DVDs`.`DVD_publication_date` AS `DVD_publication_date`,`physical_DVDs`.`DVD_category_name` AS `DVD_category_name`,`physical_DVDs`.`DVD_rental_count` AS `DVD_rental_count`,`physical_DVDs`.`DVD_duration` AS `DVD_duration`,`physical_DVDs`.`library_name` AS `library_name`,`physical_DVDs`.`floor_name` AS `floor_name`,`physical_DVDs`.`bookcase_id` AS `bookcase_id`,`physical_DVDs`.`bookshelf_id` AS `bookshelf_id`,`physical_DVDs`.`bookcase_name` AS `bookcase_name`,`physical_DVDs`.`bookshelf_name` AS `bookshelf_name`,`physical_DVDs`.`DVD_column` AS `DVD_column`,`physical_DVDs`.`physical_electronic` AS `physical_electronic`,`physical_DVDs`.`available` AS `available`,'' AS `url` from `physical_DVDs` union all select `electronic_DVDs`.`record_id` AS `record_id`,`electronic_DVDs`.`DVD_id` AS `DVD_id`,`electronic_DVDs`.`DVD_name` AS `DVD_name`,`electronic_DVDs`.`DVD_publication_date` AS `DVD_publication_date`,`electronic_DVDs`.`DVD_category_name` AS `DVD_category_name`,`electronic_DVDs`.`DVD_rental_count` AS `DVD_rental_count`,`electronic_DVDs`.`DVD_duration` AS `DVD_duration`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,`electronic_DVDs`.`physical_electronic` AS `physical_electronic`,`electronic_DVDs`.`available` AS `available`,`electronic_DVDs`.`url` AS `url` from `electronic_DVDs` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `book_informations`
--

/*!50001 DROP TABLE IF EXISTS `book_informations`*/;
/*!50001 DROP VIEW IF EXISTS `book_informations`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `book_informations` AS select `physical_books`.`record_id` AS `record_id`,`physical_books`.`book_id` AS `book_id`,`physical_books`.`book_name` AS `book_name`,`physical_books`.`author_name` AS `author_name`,`physical_books`.`book_category_name` AS `book_category_name`,`physical_books`.`book_publication_year` AS `book_publication_year`,`physical_books`.`book_ISBN` AS `book_ISBN`,`physical_books`.`book_rental_count` AS `book_rental_count`,`physical_books`.`library_name` AS `library_name`,`physical_books`.`floor_name` AS `floor_name`,`physical_books`.`bookcase_id` AS `bookcase_id`,`physical_books`.`bookshelf_id` AS `bookshelf_id`,`physical_books`.`bookcase_name` AS `bookcase_name`,`physical_books`.`bookshelf_name` AS `bookshelf_name`,`physical_books`.`book_column` AS `book_column`,`physical_books`.`physical_electronic` AS `physical_electronic`,`physical_books`.`available` AS `available`,'' AS `url` from `physical_books` union all select `electronic_books`.`record_id` AS `record_id`,`electronic_books`.`book_id` AS `book_id`,`electronic_books`.`book_name` AS `book_name`,`electronic_books`.`author_name` AS `author_name`,`electronic_books`.`book_category_name` AS `book_category_name`,`electronic_books`.`book_publication_year` AS `book_publication_year`,`electronic_books`.`book_ISBN` AS `book_ISBN`,`electronic_books`.`book_rental_count` AS `book_rental_count`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,NULL AS `NULL`,`electronic_books`.`physical_electronic` AS `physical_electronic`,`electronic_books`.`available` AS `available`,`electronic_books`.`url` AS `url` from `electronic_books` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `current_logs`
--

/*!50001 DROP TABLE IF EXISTS `current_logs`*/;
/*!50001 DROP VIEW IF EXISTS `current_logs`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `current_logs` AS select max(`logs`.`log_id`) AS `max(log_id)`,`logs`.`record_id` AS `record_id`,max(`logs`.`expected_returning_date`) AS `expected_returning_date` from `logs` group by `logs`.`record_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `electronic_DVDs`
--

/*!50001 DROP TABLE IF EXISTS `electronic_DVDs`*/;
/*!50001 DROP VIEW IF EXISTS `electronic_DVDs`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `electronic_DVDs` AS select `records`.`record_id` AS `record_id`,`DVDs`.`DVD_id` AS `DVD_id`,`DVDs`.`DVD_name` AS `DVD_name`,`DVDs`.`DVD_publication_date` AS `DVD_publication_date`,`DVD_categories`.`DVD_category_name` AS `DVD_category_name`,`DVDs`.`DVD_rental_count` AS `DVD_rental_count`,`DVDs`.`DVD_duration` AS `DVD_duration`,`records`.`physical-electronic` AS `physical_electronic`,`er`.`url` AS `url`,(case when (((`logs`.`expected_returning_date` - curtime()) is not null) = 1) then (to_days(`logs`.`expected_returning_date`) - to_days(curdate())) else 0 end) AS `available` from ((((`DVDs` join `records` on(((`records`.`x_id` = `DVDs`.`DVD_id`) and (`records`.`type_id` = 3)))) join `electronic_resources` `er` on((`er`.`record_id` = `records`.`record_id`))) left join `logs` on((`logs`.`record_id` = `records`.`record_id`))) join `DVD_categories` on((`DVD_categories`.`DVD_category_id` = `DVDs`.`DVD_category_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `electronic_books`
--

/*!50001 DROP TABLE IF EXISTS `electronic_books`*/;
/*!50001 DROP VIEW IF EXISTS `electronic_books`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`tugrul`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `electronic_books` AS select `records`.`record_id` AS `record_id`,`books`.`book_id` AS `book_id`,`books`.`book_name` AS `book_name`,`authors`.`author_name` AS `author_name`,`book_categories`.`book_category_name` AS `book_category_name`,`books`.`book_publication_year` AS `book_publication_year`,`books`.`book_ISBN` AS `book_ISBN`,`books`.`book_rental_count` AS `book_rental_count`,`records`.`physical-electronic` AS `physical_electronic`,`er`.`url` AS `url`,(case when (((`logs`.`expected_returning_date` - curtime()) is not null) = 1) then (to_days(`logs`.`expected_returning_date`) - to_days(curdate())) else 0 end) AS `available` from (((((`books` left join `authors` on((`authors`.`author_id` = `books`.`author_id`))) join `records` on(((`records`.`x_id` = `books`.`book_id`) and (`records`.`type_id` = 1)))) join `electronic_resources` `er` on((`er`.`record_id` = `records`.`record_id`))) left join `logs` on((`logs`.`record_id` = `records`.`record_id`))) join `book_categories` on((`books`.`book_category_id` = `book_categories`.`book_category_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `electronic_magazines`
--

/*!50001 DROP TABLE IF EXISTS `electronic_magazines`*/;
/*!50001 DROP VIEW IF EXISTS `electronic_magazines`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `electronic_magazines` AS select `records`.`record_id` AS `record_id`,`magazines`.`magazine_id` AS `magazine_id`,`magazines`.`magazine_name` AS `magazine_name`,`magazine_categories`.`magazine_category_name` AS `magazine_category_name`,`magazines`.`magazine_publication_date` AS `magazine_publication_date`,`magazines`.`magazine_issue_number` AS `magazine_issue_number`,`magazines`.`magazine_rental_count` AS `magazine_rental_count`,`records`.`physical-electronic` AS `physical_electronic`,`er`.`url` AS `url`,(case when (((`logs`.`expected_returning_date` - curtime()) is not null) = 1) then (to_days(`logs`.`expected_returning_date`) - to_days(curdate())) else 0 end) AS `available` from ((((`magazines` join `records` on(((`records`.`x_id` = `magazines`.`magazine_id`) and (`records`.`type_id` = 2)))) join `electronic_resources` `er` on((`er`.`record_id` = `records`.`record_id`))) left join `logs` on((`logs`.`record_id` = `records`.`record_id`))) join `magazine_categories` on((`magazine_categories`.`magazine_category_id` = `magazines`.`magazine_category_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `magazine_informations`
--

/*!50001 DROP TABLE IF EXISTS `magazine_informations`*/;
/*!50001 DROP VIEW IF EXISTS `magazine_informations`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `magazine_informations` AS select `er`.`record_id` AS `record_id`,`er`.`magazine_id` AS `magazine_id`,`er`.`magazine_name` AS `magazine_name`,`er`.`magazine_category_name` AS `magazine_category_name`,`er`.`magazine_publication_date` AS `magazine_publication_date`,`er`.`magazine_issue_number` AS `magazine_issue_number`,`er`.`magazine_rental_count` AS `magazine_rental_count`,NULL AS `library_name`,NULL AS `floor_name`,NULL AS `bookcase_id`,NULL AS `bookshelf_id`,NULL AS `bookcase_name`,NULL AS `bookshelf_name`,NULL AS `book_column`,`er`.`physical_electronic` AS `physical_electronic`,`er`.`available` AS `available`,`er`.`url` AS `url` from `electronic_magazines` `er` union all select `physical_magazines`.`record_id` AS `record_id`,`physical_magazines`.`magazine_id` AS `magazine_id`,`physical_magazines`.`magazine_name` AS `magazine_name`,`physical_magazines`.`magazine_category_name` AS `magazine_category_name`,`physical_magazines`.`magazine_publication_date` AS `magazine_publication_date`,`physical_magazines`.`magazine_issue_number` AS `magazine_issue_number`,`physical_magazines`.`magazine_rental_count` AS `magazine_rental_count`,`physical_magazines`.`library_name` AS `library_name`,`physical_magazines`.`floor_name` AS `floor_name`,`physical_magazines`.`bookcase_id` AS `bookcase_id`,`physical_magazines`.`bookshelf_id` AS `bookshelf_id`,`physical_magazines`.`bookcase_name` AS `bookcase_name`,`physical_magazines`.`bookshelf_name` AS `bookshelf_name`,`physical_magazines`.`book_column` AS `book_column`,`physical_magazines`.`physical_electronic` AS `physical_electronic`,`physical_magazines`.`available` AS `available`,'' AS `url` from `physical_magazines` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `physical_DVDs`
--

/*!50001 DROP TABLE IF EXISTS `physical_DVDs`*/;
/*!50001 DROP VIEW IF EXISTS `physical_DVDs`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `physical_DVDs` AS select `records`.`record_id` AS `record_id`,`DVDs`.`DVD_id` AS `DVD_id`,`DVDs`.`DVD_name` AS `DVD_name`,`DVDs`.`DVD_publication_date` AS `DVD_publication_date`,`DVD_categories`.`DVD_category_name` AS `DVD_category_name`,`DVDs`.`DVD_rental_count` AS `DVD_rental_count`,`DVDs`.`DVD_duration` AS `DVD_duration`,`lb`.`library_name` AS `library_name`,`fl`.`floor_name` AS `floor_name`,`bc`.`bookcase_id` AS `bookcase_id`,`bs`.`bookshelf_id` AS `bookshelf_id`,`bc`.`bookcase_name` AS `bookcase_name`,`bs`.`bookshelf_name` AS `bookshelf_name`,`pr`.`book_column` AS `DVD_column`,`records`.`physical-electronic` AS `physical_electronic`,(case when ((`current_logs`.`expected_returning_date` is not null) = 1) then (to_days(`current_logs`.`expected_returning_date`) - to_days(curdate())) else 0 end) AS `available` from ((((((((`DVDs` join `records` on(((`records`.`x_id` = `DVDs`.`DVD_id`) and (`records`.`type_id` = 3)))) join `physical_resources` `pr` on((`pr`.`record_id` = `records`.`record_id`))) join `bookshelves` `bs` on((`bs`.`bookshelf_id` = `pr`.`bookshelf_id`))) join `bookcases` `bc` on((`bc`.`bookcase_id` = `bs`.`bookcase_id`))) join `floors` `fl` on((`fl`.`floor_id` = `bc`.`floor_id`))) join `libraries` `lb` on((`lb`.`library_id` = `fl`.`library_id`))) left join `current_logs` on((`current_logs`.`record_id` = `records`.`record_id`))) join `DVD_categories` on((`DVD_categories`.`DVD_category_id` = `DVDs`.`DVD_category_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `physical_books`
--

/*!50001 DROP TABLE IF EXISTS `physical_books`*/;
/*!50001 DROP VIEW IF EXISTS `physical_books`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `physical_books` AS select `records`.`record_id` AS `record_id`,`books`.`book_id` AS `book_id`,`books`.`book_name` AS `book_name`,`authors`.`author_name` AS `author_name`,`book_categories`.`book_category_name` AS `book_category_name`,`books`.`book_publication_year` AS `book_publication_year`,`books`.`book_ISBN` AS `book_ISBN`,`books`.`book_rental_count` AS `book_rental_count`,`lb`.`library_name` AS `library_name`,`fl`.`floor_name` AS `floor_name`,`bc`.`bookcase_id` AS `bookcase_id`,`bs`.`bookshelf_id` AS `bookshelf_id`,`bc`.`bookcase_name` AS `bookcase_name`,`bs`.`bookshelf_name` AS `bookshelf_name`,`pr`.`book_column` AS `book_column`,`records`.`physical-electronic` AS `physical_electronic`,(case when ((`current_logs`.`expected_returning_date` is not null) = 1) then (to_days(`current_logs`.`expected_returning_date`) - to_days(curdate())) else 0 end) AS `available` from (((((((((`books` left join `authors` on((`authors`.`author_id` = `books`.`author_id`))) join `records` on(((`records`.`x_id` = `books`.`book_id`) and (`records`.`type_id` = 1)))) join `physical_resources` `pr` on((`pr`.`record_id` = `records`.`record_id`))) join `bookshelves` `bs` on((`bs`.`bookshelf_id` = `pr`.`bookshelf_id`))) join `bookcases` `bc` on((`bc`.`bookcase_id` = `bs`.`bookcase_id`))) join `floors` `fl` on((`fl`.`floor_id` = `bc`.`floor_id`))) join `libraries` `lb` on((`lb`.`library_id` = `fl`.`library_id`))) left join `current_logs` on((`current_logs`.`record_id` = `records`.`record_id`))) join `book_categories` on((`books`.`book_category_id` = `book_categories`.`book_category_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `physical_magazines`
--

/*!50001 DROP TABLE IF EXISTS `physical_magazines`*/;
/*!50001 DROP VIEW IF EXISTS `physical_magazines`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mustafa`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `physical_magazines` AS select `records`.`record_id` AS `record_id`,`magazines`.`magazine_id` AS `magazine_id`,`magazines`.`magazine_name` AS `magazine_name`,`magazine_categories`.`magazine_category_name` AS `magazine_category_name`,`magazines`.`magazine_publication_date` AS `magazine_publication_date`,`magazines`.`magazine_issue_number` AS `magazine_issue_number`,`magazines`.`magazine_rental_count` AS `magazine_rental_count`,`lb`.`library_name` AS `library_name`,`fl`.`floor_name` AS `floor_name`,`bc`.`bookcase_id` AS `bookcase_id`,`bs`.`bookshelf_id` AS `bookshelf_id`,`bc`.`bookcase_name` AS `bookcase_name`,`bs`.`bookshelf_name` AS `bookshelf_name`,`pr`.`book_column` AS `book_column`,`records`.`physical-electronic` AS `physical_electronic`,(case when ((`logs`.`expected_returning_date` is not null) = 1) then (to_days(`logs`.`expected_returning_date`) - to_days(curdate())) else 0 end) AS `available` from ((((((((`magazines` join `records` on(((`records`.`x_id` = `magazines`.`magazine_id`) and (`records`.`type_id` = 2)))) join `physical_resources` `pr` on((`pr`.`record_id` = `records`.`record_id`))) join `bookshelves` `bs` on((`bs`.`bookshelf_id` = `pr`.`bookshelf_id`))) join `bookcases` `bc` on((`bc`.`bookcase_id` = `bs`.`bookcase_id`))) join `floors` `fl` on((`fl`.`floor_id` = `bc`.`floor_id`))) join `libraries` `lb` on((`lb`.`library_id` = `fl`.`library_id`))) left join `logs` on((`logs`.`record_id` = `records`.`record_id`))) join `magazine_categories` on((`magazine_categories`.`magazine_category_id` = `magazines`.`magazine_category_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-12-16 20:28:20
