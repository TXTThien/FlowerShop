/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80037 (8.0.37)
 Source Host           : localhost:3306
 Source Schema         : flowershop

 Target Server Type    : MySQL
 Target Server Version : 80037 (8.0.37)
 File Encoding         : 65001

 Date: 04/12/2024 13:53:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `AccountID` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Role` enum('admin','user','shipper','staff') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Fullname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Phonenumber` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Type` int NULL DEFAULT 1,
  `Consume` decimal(50, 2) NULL DEFAULT 0.00,
  `Email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Avatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'https://storage.googleapis.com/webproject-65086.appspot.com/avatar-15.png',
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  `otp` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `otp_expiry` datetime(6) NULL DEFAULT NULL,
  PRIMARY KEY (`AccountID`) USING BTREE,
  INDEX `Type`(`Type` ASC) USING BTREE,
  INDEX `Email`(`Email` ASC) USING BTREE,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`Type`) REFERENCES `type` (`TypeID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'aaa', 'bbbb', 'user', 'aaadfasfasdf', '0989898989', '1sfadsfasdfasdf', 3, 60.00, 'thienthodfdfdf2205@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/db7684ae-9612-4c36-9f0e-d145dce71532', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (7, 'txtt1103', '$2a$10$B8hcl5PK7X7rGmUFUeMPMuuw9cxbaFO9otPzUgiLVFaytYPjnjnjK', 'user', 'Tester Number 1', '0559348044', '10 A B C D E ', 5, 3736000.00, 'sktththien@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/db7684ae-9612-4c36-9f0e-d145dce71532', 'ENABLE', '831721', '2024-12-01 18:32:10.151308');
INSERT INTO `account` VALUES (18, 'thientho2205', '$2a$10$BYOvzPULEc3b1IUoTv.uoeDyhqhkTP4kMYVEAXSnMvJRUPJ7iQxEu', 'admin', 'thientho220503', '0845418995', 'TPHCM UTE', 3, 900000.00, 'thientho.izu03@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/7278e727-75b5-4680-8604-1089b1b9d823', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (19, 'thienthostaff', '$2a$10$APOB6b86XTHa2f.FUbvGHuhsPO8elXQzWpQaK5jjKoR4grouPq6za', 'staff', 'staff HTT', '0845418922', 'đàasdfadsfasdfa', 1, 0.00, 'thientho.izu032@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/avatar-15.png', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (20, 'thienthoshipper', '$2a$10$5gR7nJ8Ce.OuEErl7HOFW.qQuXSEkJSnp6R6sW809PopUoKecPP6.', 'shipper', 'thienthoshipperr', '08454182222', 'đàasdfadsfasdfa', 2, 0.00, 'thientho.izu023@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/avatar-15.png', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (21, 'thientho2205user', '$2a$10$XQXnWL2jlX7ZyUDcmnzCLuQlEnHIhVIGS3uoRPNSo/AiaSf3F4w2y', 'user', 'thientho2205user', '0845418295', 'dfasdfadsfas', 7, 13800000.00, 'thientho.izu033@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/avatar-15.png', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (22, 'thientho1231', '$2a$10$Z9WqU0gRYwTG4ipHu3z.DubZYmjnowOg0qz6RPMuyh.dBFXbkoq6W', 'user', 'iuzmi2', '0845418922', 'dfasdfadsfas', 1, 0.00, '323232323fdszafds2zu03@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/avatar-15.png', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (23, 'thientho1232', '$2a$10$0Mu/3OH6naJzMejqadfEbOyKaeAefeTfufAQEHCLaw/Xpo8SL8/6S', 'user', 'izmumkdfj2', '08454182222', 'đàasdfadsfasdfa', 1, 0.00, 'thientho232.izu03@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/d41e5955-9369-40cf-88a3-98f3b99da9fe', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (24, 'thientho1233', '$2a$10$phTnzeEYdLnkuy5cbrsY8.BbU/vE.JRJZ34sEqhbsXTncaCFQi0Z6', 'user', 'Huy Tran', '0845118995', 'đàasdfadsfasdfa', 1, 0.00, 'thientho.i222u03@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/3abd101e-a332-4da6-a59f-5e6a6909164e', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (25, 'thientho1234', '$2a$10$kjdmwoktQJLm1Zm3DU7lUe6/aQy.6GgS323vnvoTCg4mc/gCDIaJS', 'user', 'Người đẹp trai nhất hành tinh', '0845118995', '2safdsfadsfa', 1, 0.00, 'thientho.i232zu03@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/e6151899-2155-41ee-af54-e10c7194bc11', 'ENABLE', NULL, NULL);
INSERT INTO `account` VALUES (26, 'thientho1235', '$2a$10$EdfzQgC2YVcKzkjwuSL4X.omskcVLW788fPmwVazDEwWPWfsWrk76', 'user', 'Chồng của Kafka', '0845418922', 'dfasdfadsfas', 1, 0.00, 'thienthdfdfdfzu03@gmail.com', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/5ecad9e1-cb47-447b-870b-aa5149fc12f9', 'ENABLE', NULL, NULL);

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `BannerID` int NOT NULL AUTO_INCREMENT,
  `Image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `link_to_flowerid` int NULL DEFAULT NULL,
  `link_to_newsid` int NULL DEFAULT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  `link_to_categoryid` int NULL DEFAULT NULL,
  `link_to_purposeid` int NULL DEFAULT NULL,
  PRIMARY KEY (`BannerID`) USING BTREE,
  INDEX `LinkToFlowerID`(`link_to_flowerid` ASC) USING BTREE,
  INDEX `LinkToNewsID`(`link_to_newsid` ASC) USING BTREE,
  INDEX `banner_ibfk_3`(`link_to_categoryid` ASC) USING BTREE,
  INDEX `banner_ibfk_4`(`link_to_purposeid` ASC) USING BTREE,
  CONSTRAINT `banner_ibfk_1` FOREIGN KEY (`link_to_flowerid`) REFERENCES `flower` (`FlowerID`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `banner_ibfk_2` FOREIGN KEY (`link_to_newsid`) REFERENCES `news` (`NewsID`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `banner_ibfk_3` FOREIGN KEY (`link_to_categoryid`) REFERENCES `category` (`CategoryID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `banner_ibfk_4` FOREIGN KEY (`link_to_purposeid`) REFERENCES `purpose` (`PurposeID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES (1, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/0dV6BQm4u3op4lMP4SM6puQvR62mt8kQQxnLWsb0.gif', 1, NULL, 'ENABLE', NULL, NULL);
INSERT INTO `banner` VALUES (2, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/DSC04534.jpg', NULL, 2, 'ENABLE', 2, NULL);
INSERT INTO `banner` VALUES (3, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/P673b0beabe37f9.52715411.webp', NULL, NULL, 'ENABLE', 1, NULL);
INSERT INTO `banner` VALUES (4, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/z3302688952961_7b9e8232f87b3412b50e26d0a7e17136-e1648721729895-1024x512.jpg', NULL, NULL, 'ENABLE', NULL, 1);

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `CartID` int NOT NULL AUTO_INCREMENT,
  `AccountID` int NOT NULL,
  `FlowersizeID` int NOT NULL,
  `Quantity` int NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`CartID`) USING BTREE,
  INDEX `AccountID`(`AccountID` ASC) USING BTREE,
  INDEX `cart_ibfk_2`(`FlowersizeID` ASC) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`FlowersizeID`) REFERENCES `flowersize` (`flower_sizeid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (22, 18, 36, 1, 'ENABLE');
INSERT INTO `cart` VALUES (23, 18, 37, 1, 'ENABLE');
INSERT INTO `cart` VALUES (24, 18, 48, 1, 'ENABLE');
INSERT INTO `cart` VALUES (25, 18, 112, 1, 'ENABLE');
INSERT INTO `cart` VALUES (33, 20, 43, 1, 'ENABLE');
INSERT INTO `cart` VALUES (34, 20, 39, 1, 'ENABLE');
INSERT INTO `cart` VALUES (50, 7, 39, 1, 'ENABLE');
INSERT INTO `cart` VALUES (64, 26, 39, 1, 'ENABLE');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `Categoryname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`CategoryID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 'Hoa thật', 'ENABLE');
INSERT INTO `category` VALUES (2, 'Hoa giấy', 'ENABLE');
INSERT INTO `category` VALUES (3, 'Hoa đá', 'ENABLE');
INSERT INTO `category` VALUES (7, 'Hoa khô', 'ENABLE');
INSERT INTO `category` VALUES (8, 'Hoa bó', 'ENABLE');
INSERT INTO `category` VALUES (9, 'Hoa cắm bình', 'ENABLE');
INSERT INTO `category` VALUES (10, 'Hoa lụa', 'ENABLE');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `CommentID` int NOT NULL AUTO_INCREMENT,
  `CommenttypeID` int NOT NULL,
  `AccountID` int NOT NULL,
  `comment_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `comment_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `comment_date` datetime NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  `Stative` enum('Waiting','Processing','Complete') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Waiting',
  `Image` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`CommentID`) USING BTREE,
  INDEX `CommentTypeID`(`CommenttypeID` ASC) USING BTREE,
  INDEX `AccountID`(`AccountID` ASC) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`CommenttypeID`) REFERENCES `commenttype` (`CommenttypeID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 7, 'Còn hoa cưới không?', 'Cần tìm hoa cưới miền Tây', '2024-11-27 17:19:50', 'ENABLE', 'Processing', NULL);
INSERT INTO `comment` VALUES (7, 1, 7, 'Còn hoa cưới không?', 'Cần tìm hoa cưới miền Tây', '2024-11-27 17:19:50', 'ENABLE', 'Processing', NULL);
INSERT INTO `comment` VALUES (9, 3, 7, 'hỏi đáp', 'shop cho mình hỏi bên mình có ship ra hà nội k ạ', '2024-12-04 13:05:09', 'ENABLE', 'Complete', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/4e7975bd-483c-44a7-9728-596ac07a6538');
INSERT INTO `comment` VALUES (10, 5, 7, 'tôi muốn hỏi một số thứ ', 'shop có thể tư vấn k ạ', '2024-12-04 12:59:04', 'ENABLE', 'Complete', '');
INSERT INTO `comment` VALUES (12, 1, 18, 'Có hoa giấy không shop', 'Có hoa giấy không shop ? tôi muốn mua', '2024-11-30 15:36:12', 'ENABLE', 'Complete', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/27596419-b08b-4c80-b09e-dcd036bd4145');
INSERT INTO `comment` VALUES (13, 4, 18, 'Tôi cần tư vấn', 'Cho hỏi tôi có thể thanh toán online không', '2024-11-30 15:36:55', 'ENABLE', 'Processing', '');
INSERT INTO `comment` VALUES (14, 5, 7, 'Tôi muốn hỏi nhân viên', 'Shop mình có hoa cưới không ạ', '2024-12-04 00:00:00', 'ENABLE', 'Processing', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/c5f8c551-254a-411d-befe-2646fe02607c');

-- ----------------------------
-- Table structure for commenttype
-- ----------------------------
DROP TABLE IF EXISTS `commenttype`;
CREATE TABLE `commenttype`  (
  `CommenttypeID` int NOT NULL AUTO_INCREMENT,
  `Commenttypename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`CommenttypeID`) USING BTREE,
  INDEX `CommenttypeID`(`CommenttypeID` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of commenttype
-- ----------------------------
INSERT INTO `commenttype` VALUES (1, 'Hỏi về sản phẩm', 'ENABLE');
INSERT INTO `commenttype` VALUES (2, 'Báo cáo vấn đề', 'ENABLE');
INSERT INTO `commenttype` VALUES (3, 'Yêu cầu hoàn tiền', 'ENABLE');
INSERT INTO `commenttype` VALUES (4, 'Tư vấn', 'ENABLE');
INSERT INTO `commenttype` VALUES (5, 'Tìm hiểu về cách hoạt động của tiệm', 'ENABLE');

-- ----------------------------
-- Table structure for discount
-- ----------------------------
DROP TABLE IF EXISTS `discount`;
CREATE TABLE `discount`  (
  `DiscountID` int NOT NULL AUTO_INCREMENT,
  `CategoryID` int NULL DEFAULT NULL,
  `TypeID` int NULL DEFAULT NULL,
  `PurposeID` int NULL DEFAULT NULL,
  `Discountpercent` decimal(5, 2) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`DiscountID`) USING BTREE,
  INDEX `CategoryID`(`CategoryID` ASC) USING BTREE,
  INDEX `TypeID`(`TypeID` ASC) USING BTREE,
  INDEX `PurposeID`(`PurposeID` ASC) USING BTREE,
  CONSTRAINT `discount_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `category` (`CategoryID`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `discount_ibfk_2` FOREIGN KEY (`TypeID`) REFERENCES `type` (`TypeID`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `discount_ibfk_3` FOREIGN KEY (`PurposeID`) REFERENCES `purpose` (`PurposeID`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of discount
-- ----------------------------
INSERT INTO `discount` VALUES (1, 1, NULL, NULL, 10.00, '2024-11-26 00:00:00', '2025-04-01 00:00:00', 'ENABLE');
INSERT INTO `discount` VALUES (2, NULL, 1, NULL, 20.00, '2024-11-26 00:00:00', '2024-12-02 13:36:00', 'DISABLE');
INSERT INTO `discount` VALUES (3, NULL, 3, NULL, 30.00, '2024-11-26 00:00:00', '2025-02-25 00:00:00', 'ENABLE');
INSERT INTO `discount` VALUES (4, NULL, 5, NULL, 30.00, '2024-11-26 00:00:00', '2025-02-25 00:00:00', 'ENABLE');
INSERT INTO `discount` VALUES (5, NULL, 4, NULL, 15.00, '2024-12-02 14:28:41', '2024-12-02 14:30:00', 'DISABLE');
INSERT INTO `discount` VALUES (6, NULL, NULL, 3, 75.00, '2024-12-02 14:36:00', '2024-12-02 14:44:00', 'DISABLE');

-- ----------------------------
-- Table structure for flower
-- ----------------------------
DROP TABLE IF EXISTS `flower`;
CREATE TABLE `flower`  (
  `FlowerID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CategoryID` int NOT NULL,
  `language_of_flowers` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PurposeID` int NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`FlowerID`) USING BTREE,
  INDEX `CategoryID`(`CategoryID` ASC) USING BTREE,
  INDEX `purpose`(`PurposeID` ASC) USING BTREE,
  CONSTRAINT `flower_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `category` (`CategoryID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `purpose` FOREIGN KEY (`PurposeID`) REFERENCES `purpose` (`PurposeID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flower
-- ----------------------------
INSERT INTO `flower` VALUES (1, 'Hoa Calla Lily (hoa rum) cầm tay', 'Hoa Calla Lily (hoa rum) cầm tay là một lựa chọn tinh tế và sang trọng cho hoa cưới. Với những cánh hoa mượt mà, hình dáng thanh thoát và màu sắc nhẹ nhàng, hoa Calla Lily mang đến vẻ đẹp thanh thoát và đầy ý nghĩa. Được thiết kế đơn giản nhưng đầy ấn tượng, hoa Calla Lily cầm tay là biểu tượng của sự thanh khiết, tinh tế và tình yêu vĩnh cửu, là lựa chọn lý tưởng cho cô dâu trong ngày trọng đại của mình.\n\n\n\n\n\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20Calla%20Lily.webp', 1, 'Ngôn ngữ loài hoa của hoa Calla Lily (hoa rum) là sự thuần khiết, sự thanh thoát và tình yêu vĩnh cửu. Loài hoa này thường được xem là biểu tượng của sự duyên dáng và tinh tế.\n\n\n\n\n\n\n', 1, 'ENABLE');
INSERT INTO `flower` VALUES (2, 'Hoa cuoeis hồng trắng', 'Hoa cưới cúc hồng trắng là lựa chọn tuyệt vời cho ngày trọng đại, mang lại vẻ đẹp tinh tế và thanh khiết. Những cánh hoa mềm mại, màu sắc nhẹ nhàng của hoa thể hiện tình yêu thuần khiết, sự chân thành và sự khởi đầu mới. Đây là loài hoa lý tưởng để làm nổi bật không gian cưới, mang đến không khí ấm áp và đầy ý nghĩa.\n\n\n\n\n\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20cuoeis%20h%E1%BB%93ng%20tr%E1%BA%AFng.webp', 1, 'Ngôn ngữ loài hoa của hoa cúc hồng trắng là sự chân thành, tình yêu thuần khiết và sự ngưỡng mộ. Hoa cúc hồng trắng thường được dùng để biểu tượng cho tình yêu trong sáng, sự tôn trọng và lòng biết ơn. Trong ngữ cảnh cưới hỏi, hoa này cũng thể hiện một khởi đầu mới đầy hy vọng và hạnh phúc.\n\n\n\n\n\n\n', 1, 'ENABLE');
INSERT INTO `flower` VALUES (3, 'Hoa cưới cát tường mix hồng trắng', 'Hoa cưới cát tường mix hồng trắng là sự kết hợp tinh tế giữa sắc trắng tinh khôi và hồng dịu dàng. Hoa cát tường mang vẻ đẹp nhẹ nhàng, thanh thoát, kết hợp với hoa hồng trắng tạo nên sự hoàn hảo, lãng mạn, thường được dùng trong các bó hoa cưới để tôn lên vẻ đẹp thanh thoát và thanh lịch của cô dâu.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20c%C3%A1t%20t%C6%B0%E1%BB%9Dng%20mix%20h%E1%BB%93ng%20tr%E1%BA%AFng.webp', 1, 'Hoa cát tường: Biểu tượng của sự tôn trọng và lòng biết ơn.Hoa hồng trắng: Đại diện cho tình yêu thuần khiết, trong sáng và sự thanh lịch.Sự kết hợp của hai loại hoa này thể hiện sự trân trọng, tình yêu trong sáng và mong muốn một cuộc sống hôn nhân viên mãn.\n\n\n\n\n\n\n', 1, 'ENABLE');
INSERT INTO `flower` VALUES (4, 'Hoa cưới giấy khô', 'Hoa cưới giấy khô là lựa chọn độc đáo cho những cô dâu yêu thích vẻ đẹp tự nhiên, bền lâu. Những bông hoa này được làm từ giấy nhưng vẫn giữ được sự tươi mới và mềm mại. Chúng thường được phối hợp khéo léo với các loại hoa tươi khác, tạo nên một bó hoa cưới đặc biệt, mang phong cách tinh tế, hiện đại và đầy sáng tạo.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20gi%E1%BA%A5y%20kh%C3%B4.webp', 2, 'Hoa giấy khô: Biểu tượng của sự bền vững và trường tồn. Chúng đại diện cho tình yêu mạnh mẽ, vượt qua thời gian và không bị phai mờ.\n\n\n\n\n\n', 1, 'ENABLE');
INSERT INTO `flower` VALUES (5, 'Hoa cưới hướng dương mix tana', 'Hoa cưới hướng dương mix tana là một sự kết hợp tươi sáng và nổi bật cho ngày trọng đại. Hướng dương với màu vàng rực rỡ tượng trưng cho sự nhiệt huyết và niềm vui, kết hợp với hoa tana (hoa mâm xôi) mang vẻ đẹp thanh thoát, dịu dàng. Bó hoa cưới này tạo nên một tổng thể hài hòa, vừa tươi tắn, vừa lãng mạn, thể hiện tình yêu đầy sức sống và sự chân thành.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20h%C6%B0%E1%BB%9Bng%20d%C6%B0%C6%A1ng%20mix%20tana.webp', 1, 'Hoa hướng dương: Tượng trưng cho tình yêu mãnh liệt, niềm tin và sự trung thành.Hoa tana: Biểu tượng của sự duyên dáng, thanh khiết và tình yêu thuần khiết.', 1, 'ENABLE');
INSERT INTO `flower` VALUES (6, 'Hoa cưới hồng mix baby', 'Hoa cưới hồng mix baby là một bó hoa mang vẻ đẹp dịu dàng và đầy lãng mạn. Hoa hồng với màu sắc nổi bật tượng trưng cho tình yêu và đam mê, kết hợp với hoa baby nhỏ nhắn, trắng muốt như tô điểm thêm sự tinh khôi và ngọt ngào. Đây là sự lựa chọn hoàn hảo cho cô dâu yêu thích sự nhẹ nhàng nhưng vẫn đầy cuốn hút.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20h%E1%BB%93ng%20mix%20baby.webp', 1, 'Hoa hồng: Tượng trưng cho tình yêu chân thành, lãng mạn và sự quyến rũ.Hoa baby: Biểu tượng của sự thuần khiết, dịu dàng và tình yêu trong sáng.', 1, 'ENABLE');
INSERT INTO `flower` VALUES (7, 'Hoa cưới hồng trắng mix phi yến', 'Hoa cưới hồng trắng mix phi yến là sự kết hợp hài hòa giữa vẻ đẹp cổ điển và thanh lịch. Hoa hồng trắng tượng trưng cho tình yêu thuần khiết, chân thành, trong khi hoa phi yến với những bông hoa mềm mại và rực rỡ mang đến nét duyên dáng, sang trọng. Bó hoa này tạo nên một phong cách tinh tế và lãng mạn cho ngày cưới.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20h%E1%BB%93ng%20tr%E1%BA%AFng%20mix%20phi%20y%E1%BA%BFn.webp', 1, 'Hoa hồng trắng: Tình yêu trong sáng, sự tôn trọng và khởi đầu mới. Hoa phi yến: Tượng trưng cho trái tim rộng mở, sự nhẹ nhàng và niềm hy vọng.', 1, 'ENABLE');
INSERT INTO `flower` VALUES (8, 'Hoa cưới sen đá ', 'Hoa cưới sen đá là một lựa chọn độc đáo và hiện đại, mang vẻ đẹp mộc mạc nhưng đầy sức sống. Với hình dáng chắc chắn, lá dày, và màu sắc xanh tươi, sen đá đại diện cho sự trường tồn và tình yêu bền chặt. Bó hoa thường được kết hợp với các loại hoa phụ để tạo nên sự cân bằng hoàn hảo giữa vẻ đẹp tự nhiên và tinh tế.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20sen%20%C4%91%C3%A1%20.webp', 3, 'Sen đá: Tình yêu vĩnh cửu, sự kiên cường và lòng chung thủy.', 10, 'ENABLE');
INSERT INTO `flower` VALUES (9, 'Hoa cưới tulip ', 'Hoa cưới tulip mang vẻ đẹp tinh tế, thanh lịch và hiện đại. Với nhiều màu sắc khác nhau, từ trắng thuần khiết, hồng nhẹ nhàng, đến đỏ rực rỡ, tulip dễ dàng phù hợp với phong cách của mọi cô dâu. Đây là loài hoa biểu tượng cho tình yêu trọn vẹn và niềm hạnh phúc viên mãn.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20tulip%20.webp', 1, 'Tulip: Tình yêu hoàn hảo, niềm đam mê và sự thổ lộ chân thành.\n\n\n\n\n\n\n', 1, 'ENABLE');
INSERT INTO `flower` VALUES (10, 'Hoa sen cưới', 'Hoa sen cưới mang vẻ đẹp thanh cao, thuần khiết và truyền thống. Với cánh hoa mềm mại, mùi hương nhẹ nhàng, hoa sen thường được chọn trong các đám cưới mang phong cách trang nhã và đậm chất Á Đông, tượng trưng cho sự hòa hợp và một tình yêu bền vững.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20sen%20c%C6%B0%E1%BB%9Bi.webp', 1, 'Hoa sen: Sự thuần khiết, lòng chung thủy và sự vươn lên vượt khó khăn trong tình yêu.\n\n\n\n\n\n\n', 5, 'ENABLE');
INSERT INTO `flower` VALUES (11, 'Albus', 'Albus là một bó hoa mang sắc trắng thuần khiết và trang nhã, tượng trưng cho sự thanh cao và lòng thành kính. Được thiết kế từ 50 cành hồng trắng cồ, 20 cành cúc lưới, 10 cành lan Thái, và 10 cành hồng môn trắng, tất cả đều được nhập trực tiếp từ Đà Lạt, mang đến sự tinh tế và đẳng cấp, thích hợp cho các dịp đặc biệt như lễ kỷ niệm, chia buồn hoặc tặng người thân yêu.\n\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Albus.jpg', 1, 'Hồng trắng: Tình yêu trong sáng, sự thuần khiết.\nCúc lưới: Tưởng nhớ, sự tôn trọng.\nLan Thái: Vẻ đẹp kiêu sa và sự ngưỡng mộ.\nHồng môn trắng: Lòng thành kính và sự trang nghiêm.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (12, 'Always In Heart', 'Always In Heart là bó hoa chia buồn mang vẻ đẹp thanh nhã, được kết hợp từ:\n\n75 bông hồng trắng cồ: Tượng trưng cho sự tôn kính và lời tiễn biệt trang trọng.\n14 bông hồng tím: Mang ý nghĩa của sự trân trọng và những kỷ niệm đẹp.\nĐây là sự kết hợp tinh tế giữa màu sắc nhẹ nhàng và thiết kế ý nghĩa, phù hợp để bày tỏ lòng thành kính đến người đã khuất. Hoa được nhập trực tiếp từ Đà Lạt, đảm bảo độ tươi lâu khi bảo quản đúng cách.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Always%20In%20Heart.jpg', 1, 'Hồng trắng: Biểu tượng của sự ngây thơ, tinh khiết và tiễn biệt đầy kính trọng.\nHồng tím: Đại diện cho lòng biết ơn và những ký ức trân quý không thể quên.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (13, 'Antonio', 'Antonio là vòng hoa chia buồn được thiết kế trang nhã, sang trọng với:\n\n50 cành hồng trắng cồ: Tượng trưng cho sự kính trọng và lời tiễn biệt thanh tao.\n20 cành hoa cúc: Đại diện cho sự tưởng nhớ và lòng biết ơn sâu sắc.\n10 cành lan Thái: Biểu tượng của sự trang trọng và tinh tế.\n5 lá trầu bà Nam Mỹ: Tạo điểm nhấn xanh mát, cân bằng và hài hòa cho tổng thể.\nAntonio là lựa chọn hoàn hảo để bày tỏ lòng thành kính, gửi lời chia buồn đến gia đình người đã khuất.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Antonio.jpg', 2, 'Hồng trắng: Sự tinh khiết, tiễn biệt đầy tôn kính.\nHoa cúc: Tượng trưng cho sự trường tồn và lòng tri ân.\nLan Thái: Lời chào tạm biệt trang nhã và trân trọng.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (14, 'Asher ', 'Asher là bó hoa sang trọng và thanh nhã, gồm:\n\n100 cành hoa hồng trắng cồ: Biểu tượng của sự trong sáng, thanh cao và tôn vinh những tình cảm chân thành.\n10 cành phi yến: Mang ý nghĩa của sự duyên dáng, nhẹ nhàng, và những lời chúc tốt đẹp.\nBó hoa Asher mang đến vẻ đẹp thuần khiết và đầy cảm xúc, là lựa chọn hoàn hảo cho những dịp trang trọng hoặc để bày tỏ lòng kính trọng.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Asher.jpg', 3, 'Hồng trắng: Tượng trưng cho sự trong sáng, lòng kính trọng và tình yêu thuần khiết.\nPhi yến: Thể hiện sự duyên dáng, nhẹ nhàng và những lời chúc an lành.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (15, 'Delilah', 'Bó hoa Delilah mang đến sự kết hợp hoàn hảo giữa vẻ đẹp tinh tế và sang trọng:\n\n100 cành hoa hồng trắng cồ: Biểu trưng cho sự trong sáng, thuần khiết và tình yêu vĩnh cửu.\n20 cành hoa cúc lưới: Với sắc trắng nhẹ nhàng, hoa cúc lưới thể hiện sự thanh thản và tình yêu bền vững.\n5 lá trầu bà Nam Mỹ: Mang đến sự tươi mới và một nét đẹp tự nhiên, cùng với ý nghĩa may mắn và bảo vệ.\nDelilah là bó hoa tuyệt vời dành cho những dịp trọng đại, thể hiện sự sang trọng, tinh tế và đầy ý nghĩa.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Delilah.jpg', 3, 'Hồng trắng: Tượng trưng cho sự thuần khiết, tình yêu vĩnh cửu, và lòng kính trọng.\nCúc lưới: Là biểu tượng của sự trân trọng và tình yêu bền vững.\nTrầu bà Nam Mỹ: Mang ý nghĩa may mắn và bảo vệ, là một dấu hiệu của sự sinh sôi, phát triển.\n', 2, 'ENABLE');
INSERT INTO `flower` VALUES (16, 'Grace ', 'Bó hoa Grace là sự kết hợp hoàn hảo giữa sự tinh tế và vẻ đẹp dịu dàng, bao gồm:\n\n50 cành hoa hồng trắng cồ: Biểu trưng cho sự thuần khiết, tình yêu vĩnh cửu và lòng kính trọng sâu sắc.\n40 cành hoa cúc lưới: Với sắc trắng nhẹ nhàng, hoa cúc lưới thể hiện sự thanh thản, bền vững và là biểu tượng của sự bình yên.\nGrace là lựa chọn hoàn hảo cho những dịp đặc biệt, thể hiện vẻ đẹp tinh tế và sự sang trọng, mang lại cảm giác thanh thoát, nhẹ nhàng nhưng không kém phần ấn tượng.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Grace.jpg', 2, 'Hồng trắng: Tượng trưng cho sự thuần khiết, tình yêu vĩnh cửu, và lòng kính trọng.\nCúc lưới: Biểu tượng của sự bình yên, sự thanh thản và tình yêu bền vững.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (17, 'Noah', 'Bó hoa Noah bao gồm:\n\n100 cành hoa hồng trắng cồ: Mỗi cành hoa hồng trắng cồ được nhập trực tiếp từ Đà Lạt, biểu trưng cho sự thuần khiết, tình yêu vĩnh cửu và lòng kính trọng sâu sắc.\nNoah là sự lựa chọn hoàn hảo cho những dịp trọng đại như lễ cưới, kỷ niệm hay những sự kiện quan trọng. Sự tinh tế và thanh thoát của hoa hồng trắng mang đến một không gian trang trọng, đầy ý nghĩa.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Noah.jpg', 2, 'Hồng trắng: Tượng trưng cho sự thuần khiết, tình yêu vĩnh cửu và lòng kính trọng.\n', 2, 'ENABLE');
INSERT INTO `flower` VALUES (18, 'Respect', 'Bó hoa Respect bao gồm:\n\n35 bông hồng trắng cồ: Được nhập trực tiếp từ Đà Lạt, hoa hồng trắng biểu trưng cho sự thuần khiết, lòng kính trọng và tình yêu vĩnh cửu.\n30 bông hồng tím: Cũng được nhập từ Đà Lạt, hoa hồng tím thể hiện sự ngưỡng mộ, tôn kính và sự lãng mạn sâu sắc.\n10 bông cúc bánh bao: Từ Đà Lạt, hoa cúc bánh bao tượng trưng cho sự bình an và sự tươi sáng, tạo thêm vẻ đẹp nhẹ nhàng cho bó hoa.\nNếu được chăm sóc và bảo quản trong điều kiện mát lạnh, bó hoa này có thể giữ được độ tươi lên đến 10 ngày.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Respect.jpg', 1, 'Hồng trắng: Tượng trưng cho sự thuần khiết và tình yêu vĩnh cửu.\nHồng tím: Đại diện cho sự ngưỡng mộ, tôn kính và lãng mạn sâu sắc.\nCúc bánh bao: Mang ý nghĩa bình an, sự tươi sáng và một tình yêu bền lâu.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (19, 'See you in Heaven\r\n\r\n', 'Bó hoa See You in Heaven bao gồm:\n\n9 bông cúc bánh bao: Được nhập trực tiếp từ Đà Lạt, hoa cúc bánh bao mang ý nghĩa của sự bình an và sự tươi mới, thích hợp để bày tỏ lòng kính trọng và yêu thương.\n15 bông hồng Ohara tím: Một lựa chọn đặc biệt, hoa hồng Ohara tím tượng trưng cho sự ngưỡng mộ, quý trọng và tình yêu sâu sắc.\n60 bông hồng tím: Được nhập từ Đà Lạt, hoa hồng tím là biểu tượng của tình yêu vĩnh cửu, sự chân thành và lãng mạn.\nNếu được chăm sóc và bảo quản ở nhiệt độ máy lạnh mát, bó hoa này có thể giữ được độ tươi lên tới 10 ngày.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/See%20you%20in%20Heaven.jpg', 1, 'Cúc bánh bao: Mang ý nghĩa bình an, tươi sáng và sự hài hòa trong cuộc sống.\nHồng Ohara tím: Tượng trưng cho sự ngưỡng mộ và tình yêu lãng mạn, sự quý trọng sâu sắc.\nHồng tím: Đại diện cho tình yêu vĩnh cửu, sự chung thủy và lòng chân thành.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (20, 'Won\'t Be Forgotten\r\n\r\n', 'Bó hoa Won\'t Be Forgotten bao gồm:\n\n10 bông cúc bánh bao: Hoa cúc bánh bao mang đến thông điệp về sự tươi mới và bình an, thích hợp cho những dịp bày tỏ lòng kính trọng và sự quý mến.\n56 bông hồng trắng cồ: Những bông hồng trắng cồ mang ý nghĩa của sự thuần khiết, tôn trọng và tình yêu vĩnh cửu. Hoa hồng trắng luôn là lựa chọn lý tưởng cho những dịp quan trọng và đầy ý nghĩa.\n12 bông hồng tím: Hoa hồng tím biểu trưng cho sự ngưỡng mộ, tình yêu lãng mạn và sự tôn trọng sâu sắc.\nNếu được chăm sóc và bảo quản ở nhiệt độ máy lạnh mát, bó hoa này có thể giữ được độ tươi lên tới 10 ngày.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Won\'t%20Be%20Forgotten.jpg', 1, 'Cúc bánh bao: Mang ý nghĩa về sự tươi mới, bình an và sự hài hòa trong cuộc sống.\nHồng trắng cồ: Tượng trưng cho sự thuần khiết, lòng kính trọng và tình yêu vĩnh cửu.\nHồng tím: Đại diện cho sự ngưỡng mộ, tình yêu lãng mạn và lòng tôn trọng sâu sắc.', 2, 'ENABLE');
INSERT INTO `flower` VALUES (21, 'Big Success', 'Bó hoa Big Success bao gồm:\n\n250 hoa hồng Ecuador nhập khẩu: Hoa hồng Ecuador nổi bật với những bông hoa lớn, màu sắc tươi tắn và hương thơm quyến rũ. Đây là lựa chọn tuyệt vời để thể hiện sự thành công, chiến thắng và lòng biết ơn.\n10 cành thiên điểu nhập khẩu: Thiên điểu mang vẻ đẹp độc đáo và là biểu tượng của sự tự do, sức mạnh, cũng như khát vọng vươn tới thành công.\n10 cúc mẫu đơn nhập khẩu Malaysia: Cúc mẫu đơn mang ý nghĩa của sự thịnh vượng và may mắn, phù hợp để chúc mừng những thành tựu đạt được.\n10 lá trầu bà Nam Mỹ: Lá trầu bà không chỉ mang vẻ đẹp tự nhiên mà còn tượng trưng cho sự phát triển mạnh mẽ và sự bảo vệ.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Big%20Success.jpg', 1, 'Hoa hồng Ecuador: Tượng trưng cho tình yêu và sự thành công, những bông hoa hồng lớn thể hiện sự lộng lẫy và vương giả.\nThiên điểu: Là biểu tượng của sự tự do, khát vọng và sự nổi bật trong cuộc sống.\nCúc mẫu đơn: Đại diện cho sự thịnh vượng, may mắn và những thành công lớn.\nLá trầu bà Nam Mỹ: Mang ý nghĩa bảo vệ và sự phát triển bền vững.', 3, 'ENABLE');
INSERT INTO `flower` VALUES (22, 'Celebration Flowers', 'Bó hoa Celebration Flowers bao gồm:\n\n15 cành hoa hồng cam Free Spirit: Hoa hồng cam Free Spirit mang vẻ đẹp tươi sáng và sự quyến rũ. Màu cam tượng trưng cho sự nhiệt huyết, năng lượng và khát khao trong cuộc sống.\n5 cành hoa hồng vàng Helen: Hoa hồng vàng Helen thể hiện sự tươi vui, tình bạn chân thành và sự lạc quan. Đây là màu hoa hoàn hảo để chúc mừng những dịp vui vẻ và hạnh phúc.\n15 cành hoa hồng Capuchino: Hoa hồng Capuchino có màu sắc nhẹ nhàng, tinh tế, mang đến một cảm giác bình yên và thanh thản. Hoa này biểu tượng cho sự yêu thương nhẹ nhàng, chân thành.\n4 cành thiên điểu: Thiên điểu mang vẻ đẹp độc đáo, tượng trưng cho sự tự do, sức mạnh và khát vọng. Đây là loài hoa mang thông điệp mạnh mẽ về sự thành công và niềm tin vào tương lai.', 'https://storage.cloud.google.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Celebration%20Flowers.jpg', 1, 'Hoa hồng cam Free Spirit: Biểu tượng của sự tự do, khát khao và đam mê.\nHoa hồng vàng Helen: Đại diện cho tình bạn, sự vui tươi và lạc quan.\nHoa hồng Capuchino: Mang đến sự bình yên và yêu thương nhẹ nhàng.\nThiên điểu: Là biểu tượng của sự tự do, sức mạnh và khát vọng vươn tới thành công.', 3, 'ENABLE');
INSERT INTO `flower` VALUES (23, 'Lovely Day ', 'Bó hoa Lovely Day bao gồm:\n\n25 hoa hồng trắng cồ: Hoa hồng trắng là biểu tượng của sự thuần khiết, tinh khiết và tình yêu trong sáng. Đây là sự lựa chọn hoàn hảo cho những dịp đặc biệt, thể hiện sự chân thành và trân trọng.\n25 hoa hồng cam Victor: Hoa hồng cam Victor mang đến sự ấm áp, khát khao và đam mê. Màu cam của hoa hồng này tượng trưng cho sự năng động, nhiệt huyết và tình yêu mạnh mẽ.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Lovely%20Day.jpg', 8, 'Hoa hồng trắng: Biểu tượng của sự thuần khiết, tình yêu chân thành và sự tôn trọng.\nHoa hồng cam Victor: Đại diện cho đam mê, năng lượng và khát khao trong tình yêu và cuộc sống.', 3, 'ENABLE');
INSERT INTO `flower` VALUES (24, 'Naila ', 'Bó hoa Naila bao gồm:\n\n40 cành hoa hồng Ecuador: Hoa hồng Ecuador nổi bật với những cánh hoa mịn màng và màu sắc rực rỡ. Chúng là biểu tượng của tình yêu nồng cháy, sự trân trọng và ngưỡng mộ.\n20 cành hoa hồng cam Free Spirit: Hoa hồng cam Free Spirit mang đến vẻ đẹp tươi mới, tượng trưng cho sự nhiệt huyết, tình yêu đam mê và lòng dũng cảm.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Naila.jpg', 2, 'Hoa hồng Ecuador: Biểu tượng của tình yêu sâu đậm, sự ngưỡng mộ và sự trân trọng. Đây là loài hoa thể hiện sự lãng mạn và chân thành.\nHoa hồng cam Free Spirit: Đại diện cho tình yêu mạnh mẽ, đam mê và tự do. Màu cam mang đến sự ấm áp, nhiệt huyết và khát khao sống.', 3, 'ENABLE');
INSERT INTO `flower` VALUES (25, 'The Best Wishes', 'Bó hoa The Best Wishes bao gồm:\n\n50 hoa hồng Free Spirit nhập khẩu từ Côn Minh: Những bông hồng cam Free Spirit mang đến vẻ đẹp mạnh mẽ và tươi mới. Hoa hồng cam là biểu tượng của sự nhiệt huyết, đam mê và lòng dũng cảm, thể hiện sự mạnh mẽ trong tình yêu và cuộc sống.\n25 hoa hồng Ecuador giống nhập khẩu: Hoa hồng Ecuador nổi bật với cánh hoa mịn màng và màu sắc rực rỡ. Đây là biểu tượng của tình yêu nồng cháy và sự ngưỡng mộ, thể hiện sự lãng mạn và chân thành trong mọi mối quan hệ.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/The%20Best%20Wishes.jpg', 2, 'Hoa hồng Free Spirit (cam): Tượng trưng cho tình yêu nồng nàn, nhiệt huyết và sự tự do. Hoa hồng cam thể hiện sự sáng tạo và khát khao vươn lên.\nHoa hồng Ecuador: Biểu trưng cho tình yêu chân thành, lãng mạn và sự ngưỡng mộ. Màu sắc tươi sáng của hoa hồng Ecuador thể hiện sự tôn trọng và yêu thương vô điều kiện.', 3, 'ENABLE');
INSERT INTO `flower` VALUES (26, 'Xin', 'Bó hoa Xin bao gồm:\n\n25 cành hoa hồng Ecuador nhập khẩu từ Đà Lạt: Những bông hoa hồng Ecuador nổi bật với cánh hoa mịn màng và màu sắc tươi sáng, mang ý nghĩa của tình yêu chân thành, lãng mạn và ngưỡng mộ.\n5 cành hoa hồng kem cồ nhập khẩu từ Đà Lạt: Hoa hồng kem cồ với sắc trắng kem nhẹ nhàng tượng trưng cho sự thuần khiết, thanh tao và những lời chúc tốt đẹp.\n5 cành hoa phi yến nhập khẩu từ Đà Lạt: Hoa phi yến mang đến sự tinh tế và thanh thoát, thể hiện sự quý phái và lòng tôn trọng sâu sắc đối với người nhận.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Xin.jpg', 3, 'Hoa hồng Ecuador: Biểu trưng cho tình yêu chân thành và ngưỡng mộ, hoa hồng Ecuador là món quà lý tưởng cho những tình cảm lãng mạn và tôn trọng.\nHoa hồng kem: Tượng trưng cho sự thuần khiết, tình yêu tinh khôi và những lời chúc tốt đẹp trong mối quan hệ.\nHoa phi yến: Mang ý nghĩa của sự thanh lịch và tôn trọng, thể hiện một tình cảm sâu sắc, tinh tế và đáng trân trọng.', 3, 'ENABLE');
INSERT INTO `flower` VALUES (27, 'Yashita ', 'Bó hoa Yashita bao gồm:\n\n100 cành hoa hồng Ecuador nhập khẩu từ Đà Lạt: Những bông hoa hồng Ecuador với sắc màu tươi sáng và mùi hương quyến rũ, mang đến vẻ đẹp hoàn mỹ và tượng trưng cho tình yêu vĩnh cửu và ngưỡng mộ.\n30 cành hoa hồng Free Spirit nhập khẩu từ Đà Lạt: Hoa hồng Free Spirit với màu cam rực rỡ và kiểu dáng thanh lịch, tượng trưng cho niềm vui, sự tự do và niềm đam mê mãnh liệt.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Yashita.jpg', 3, 'Hoa hồng Ecuador: Biểu tượng của tình yêu đích thực, vĩnh cửu và sự ngưỡng mộ sâu sắc. Hoa hồng Ecuador là món quà tuyệt vời cho những dịp đặc biệt, thể hiện tình cảm lãng mạn và tình yêu nồng nàn.\nHoa hồng Free Spirit: Mang ý nghĩa của sự tự do, niềm vui và khát vọng sống mãnh liệt. Đây là lựa chọn lý tưởng cho những ai yêu thích sự tươi mới, sáng tạo và đam mê.', 3, 'ENABLE');
INSERT INTO `flower` VALUES (28, 'KN-01', 'Bó hoa KN-01 bao gồm:\n\n40 cành hoa hồng được xử lý màu nhập trực tiếp từ Đà Lạt: Những bông hoa hồng được xử lý màu tươi sáng, mang lại sự nổi bật và quyến rũ, hoàn hảo cho các dịp đặc biệt.\n5 cành cúc mẫu đơn được xử lý màu nhập trực tiếp từ Đà Lạt: Cúc mẫu đơn mang nét đẹp thanh tao, kết hợp với màu sắc tươi mới để tạo nên vẻ đẹp hài hòa và sang trọng.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-01.png', 1, 'Hoa hồng: Biểu tượng của tình yêu, sự ngưỡng mộ và lòng trung thành. Màu sắc của hoa hồng có thể thể hiện các thông điệp khác nhau, từ tình yêu lãng mạn đến sự tôn trọng và lòng biết ơn.\nCúc mẫu đơn: Là biểu tượng của sự may mắn, thịnh vượng và hạnh phúc. Loài hoa này còn tượng trưng cho sự vĩnh cửu và trường tồn, đặc biệt là trong các dịp chúc mừng.', 4, 'ENABLE');
INSERT INTO `flower` VALUES (29, 'KN-02', 'Bó hoa này sẽ chứa những lời nhắn yêu thương của bạn tới nửa kia', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-02.png', 1, 'Hoa hồng: Biểu tượng của tình yêu, sự ngưỡng mộ và lòng trung thành. Màu sắc của hoa hồng có thể thể hiện các thông điệp khác nhau, từ tình yêu lãng mạn đến sự tôn trọng và lòng biết ơn.\n', 4, 'ENABLE');
INSERT INTO `flower` VALUES (30, 'KN-03', 'Bó hoa KN-03 được thiết kế đặc biệt dành cho dịp kỷ niệm ngày 8/3, ngày Quốc tế Phụ nữ, với sự kết hợp tinh tế của các loài hoa biểu tượng cho vẻ đẹp và sự quý phái:\n\n50 cành hoa hồng đỏ nhập trực tiếp từ Đà Lạt: Hoa hồng đỏ là biểu tượng của tình yêu nồng nàn và sự tôn vinh vẻ đẹp tuyệt vời của người phụ nữ. Màu đỏ rực rỡ cũng thể hiện sự đam mê và lòng kính trọng.\n30 cành hoa cúc lưới trắng nhập trực tiếp từ Đà Lạt: Cúc lưới trắng mang lại sự thanh thoát, nhẹ nhàng, tượng trưng cho sự tinh khiết, trường thọ và sự trân trọng.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-03.png', 1, 'Hoa hồng đỏ: Biểu tượng của tình yêu mãnh liệt, niềm đam mê, và lòng kính trọng. Hoa hồng đỏ là món quà không thể thiếu vào ngày 8/3, thể hiện sự ngưỡng mộ và tình yêu sâu sắc dành cho người phụ nữ.\nHoa cúc lưới trắng: Tượng trưng cho sự tinh khiết, lòng biết ơn và sự trường thọ. Hoa cúc trắng thể hiện sự thanh cao và tôn vinh vẻ đẹp nội tâm của người phụ nữ.', 4, 'ENABLE');
INSERT INTO `flower` VALUES (31, 'KN-04', 'Bó hoa KN-04 được thiết kế đặc biệt dành cho Ngày của Mẹ, một ngày để tri ân và tôn vinh những người mẹ tuyệt vời. Bó hoa này bao gồm:\n\n40 cành hoa hồng phấn nhập trực tiếp từ Đà Lạt: Hoa hồng phấn thể hiện tình yêu thương dịu dàng và sự trân trọng, phù hợp để gửi gắm tình cảm sâu sắc dành cho người mẹ.\n20 cành hoa lily trắng nhập trực tiếp từ Đà Lạt: Hoa lily trắng biểu trưng cho sự thuần khiết, tôn vinh lòng nhân ái và tình mẫu tử bao la.', 'https://storage.cloud.google.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-04.png', 1, 'Hoa hồng phấn: Mang đến thông điệp về tình yêu ngọt ngào, sự chăm sóc, và lòng biết ơn dành cho mẹ. Đây là màu sắc thể hiện sự ấm áp, dịu dàng và đầy yêu thương.\nHoa lily trắng: Tượng trưng cho sự thuần khiết, lòng biết ơn sâu sắc và sự tôn kính. Hoa lily trắng thể hiện sự cao quý, thanh khiết và tình yêu vô bờ bến của người mẹ.', 4, 'ENABLE');
INSERT INTO `flower` VALUES (32, 'GH-01', 'Bó hoa GH-01 được thiết kế tinh tế và sang trọng, gồm:\n\n15 cành hoa hồng Helen nhập trực tiếp từ Đà Lạt: Hoa hồng Helen với sắc đỏ tươi biểu trưng cho tình yêu mãnh liệt và nồng nàn, là lựa chọn lý tưởng để thể hiện sự yêu thương chân thành.\n4 cành hoa hồng Callaly nhập trực tiếp từ Đà Lạt: Hoa hồng Callaly mang màu sắc thanh lịch và nhẹ nhàng, thể hiện sự tôn trọng và ngưỡng mộ.', 'https://storage.cloud.google.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-01.png', 9, 'Hoa hồng Helen: Biểu tượng của tình yêu nồng nàn, đam mê và sự quyến rũ. Hoa hồng đỏ luôn là biểu trưng của tình yêu vĩnh cửu và lãng mạn.\nHoa hồng Callaly: Màu sắc thanh nhã và trang trọng của hoa hồng Callaly thể hiện sự tôn kính, trân trọng và tình yêu sâu sắc.', 7, 'ENABLE');
INSERT INTO `flower` VALUES (33, 'GH-02', 'Bó hoa GH-02 mang đến một sự kết hợp đầy màu sắc và ý nghĩa, bao gồm:\n\n8 cành cúc mẫu đơn nhập trực tiếp từ Đà Lạt: Với vẻ đẹp giản dị và thanh tao, cúc mẫu đơn biểu trưng cho sự kính trọng, lòng biết ơn và sự thuần khiết.\n15 cành hoa hồng lạc thần nhập trực tiếp từ Đà Lạt: Hoa hồng lạc thần với màu sắc rực rỡ và vẻ đẹp độc đáo thể hiện sự huyền bí, đam mê và sự lôi cuốn.\n5 cành hoa hồng Victor nhập trực tiếp từ Đà Lạt: Hoa hồng Victor mang một sắc cam tươi sáng, là biểu tượng của sự tươi mới, niềm vui và sự lạc quan.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-02.png', 10, 'Cúc mẫu đơn: Biểu trưng cho sự thuần khiết, lòng biết ơn và sự kính trọng.\nHoa hồng lạc thần: Biểu tượng của sự huyền bí, đam mê và sự lôi cuốn, tạo nên sự hấp dẫn khó cưỡng.\nHoa hồng Victor: Mang trong mình thông điệp về niềm vui, sự tươi mới và tinh thần lạc quan.', 6, 'ENABLE');
INSERT INTO `flower` VALUES (34, 'GH-03', 'Bó hoa GH-03 mang đến vẻ đẹp thanh thoát và tinh tế, bao gồm:\n\n5 cành cẩm tú cầu nhập trực tiếp từ Đà Lạt: Cẩm tú cầu với những bông hoa tròn trịa, màu sắc nhẹ nhàng, biểu trưng cho tình yêu vĩnh cửu, sự thủy chung và lòng biết ơn sâu sắc.\n17 cành hoa hồng trắng cồ nhập trực tiếp từ Đà Lạt: Hoa hồng trắng cồ mang vẻ đẹp thuần khiết, là biểu tượng của tình yêu trong sáng, sự kính trọng và sự trân trọng.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-03.png', 1, 'Cẩm tú cầu: Biểu tượng của tình yêu vĩnh cửu, sự thủy chung và lòng biết ơn.\nHoa hồng trắng cồ: Đại diện cho sự thuần khiết, tình yêu trong sáng, và lòng kính trọng.', 5, 'ENABLE');
INSERT INTO `flower` VALUES (35, 'GH-04', 'Bó hoa GH-04 với sự kết hợp tinh tế của những cành hoa lan mang đến vẻ đẹp sang trọng và thanh lịch, phù hợp cho nhiều dịp đặc biệt.\n\nHoa lan: Các cành hoa lan được nhập trực tiếp từ Đà Lạt, nổi bật với sắc màu rực rỡ và hình dáng thanh thoát. Hoa lan là biểu tượng của sự tinh tế, vẻ đẹp quyến rũ và sự giàu có.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-04.png', 10, 'Hoa lan: Là biểu tượng của sự thanh lịch, tình yêu cao thượng, sự tôn trọng và vẻ đẹp trường tồn. Hoa lan cũng mang đến thông điệp về sự quý phái, giàu sang và sự duyên dáng.', 9, 'ENABLE');
INSERT INTO `flower` VALUES (36, 'GH-05', 'Bó hoa GH-05 là sự kết hợp của nhiều loài hoa đá, với vẻ đẹp độc đáo và bền bỉ. Những cành hoa đá mang đến sự mới mẻ, khác biệt và phù hợp cho những dịp muốn thể hiện sự tinh tế và sự bền vững.\n\nHoa đá: Loài hoa này thường có hình dáng đẹp mắt, bền lâu và không yêu cầu quá nhiều sự chăm sóc, thể hiện sự kiên cường và bền bỉ. Hoa đá được sử dụng phổ biến trong các bó hoa cưới hay trang trí vì màu sắc đa dạng và kiểu dáng đặc biệt.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-05.png', 3, 'Hoa đá: Mang ý nghĩa về sự kiên trì, bền vững và sự trường tồn. Hoa đá tượng trưng cho sức mạnh nội tại, sự kiên cường trong những thử thách và sự vượt qua khó khăn. Nó cũng đại diện cho sự vững chắc và tình yêu không thay đổi theo thời gian.\n', 5, 'ENABLE');
INSERT INTO `flower` VALUES (37, 'GH-06', 'Bó hoa GH-06 là một sự lựa chọn tuyệt vời với hoa trường thọ, một biểu tượng của sự sống lâu dài, may mắn và bình an. Loài hoa này không chỉ mang đến vẻ đẹp thanh thoát mà còn mang ý nghĩa sâu sắc về sức khỏe, sự trường thọ và hạnh phúc.\n\nHoa trường thọ: Thường được biết đến là loài hoa mang lại sự phước lành, sức khỏe dồi dào và những điều tốt lành trong cuộc sống. Đây là loại hoa lý tưởng để dành tặng cho những dịp quan trọng như lễ mừng thọ, sinh nhật, hay các dịp cầu mong sự may mắn, sức khỏe và sự an lành.\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-06.png', 1, 'Hoa trường thọ: Mang ý nghĩa về sự trường tồn, sức khỏe vĩnh cửu và hạnh phúc trọn đời. Đây là loài hoa tượng trưng cho sự sống lâu, an yên và hạnh phúc bền vững. Tặng hoa trường thọ thể hiện lời chúc tốt đẹp cho người nhận, mong họ sống lâu, mạnh khỏe và luôn vui vẻ.\n', 5, 'ENABLE');
INSERT INTO `flower` VALUES (38, 'GH-07', 'Bó hoa GH-07 kết hợp giữa hoa hồng và hoa xanh, tạo nên một sự pha trộn hoàn hảo giữa sắc đẹp và ý nghĩa. Hoa hồng, biểu tượng của tình yêu và sự lãng mạn, kết hợp với hoa xanh, biểu tượng của hy vọng, sự tươi mới và sự bền vững, sẽ là món quà tuyệt vời cho những dịp đặc biệt.\n\nHoa hồng: Là loài hoa biểu trưng cho tình yêu, sự ngọt ngào và cảm xúc sâu lắng. Mỗi màu sắc của hoa hồng mang một ý nghĩa khác nhau, từ tình yêu lãng mạn, sự tôn trọng đến lòng biết ơn.\n\nHoa xanh: Biểu trưng cho sự tươi mới, hy vọng và sự ổn định. Hoa xanh cũng là biểu tượng của sự yên bình và tươi đẹp, mang đến cảm giác thư giãn và trong lành.', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH_07.png', 7, 'Hoa hồng: Tình yêu đích thực, sự lãng mạn, lòng tôn trọng và sự tri ân.\nHoa xanh: Hy vọng, sự đổi mới và sự bền vững trong cuộc sống.', 5, 'ENABLE');
INSERT INTO `flower` VALUES (40, 'Hoa Giấy', 'Hoa giấy', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/6d06e7be-aa2a-4141-8291-6ad8686dff74', 2, 'Hoa giấy', 2, 'DISABLE');
INSERT INTO `flower` VALUES (41, 'hoa giấy abc', 'hoa giấy abc', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/5e5975fe-f988-4d0b-b0a5-1ed8b51358b2', 3, 'hoa giấy abc', 8, 'DISABLE');
INSERT INTO `flower` VALUES (42, 'hoa giấy abvc', 'hoa giấy av', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/bdadab72-0ff4-49ec-9118-e80f9ae4d77d', 8, 'hoa giấyabc', 8, 'DISABLE');

-- ----------------------------
-- Table structure for flowerimages
-- ----------------------------
DROP TABLE IF EXISTS `flowerimages`;
CREATE TABLE `flowerimages`  (
  `flower_imageid` int NOT NULL AUTO_INCREMENT,
  `FlowerID` int NOT NULL,
  `ImageURL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`flower_imageid`) USING BTREE,
  INDEX `FlowerID`(`FlowerID` ASC) USING BTREE,
  CONSTRAINT `flowerimages_ibfk_1` FOREIGN KEY (`FlowerID`) REFERENCES `flower` (`FlowerID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1116 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flowerimages
-- ----------------------------
INSERT INTO `flowerimages` VALUES (1, 1, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20Calla%20Lily.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (2, 2, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20cuoeis%20h%E1%BB%93ng%20tr%E1%BA%AFng.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (3, 3, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20c%C3%A1t%20t%C6%B0%E1%BB%9Dng%20mix%20h%E1%BB%93ng%20tr%E1%BA%AFng.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (4, 4, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20gi%E1%BA%A5y%20kh%C3%B4.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (5, 5, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20h%C6%B0%E1%BB%9Bng%20d%C6%B0%C6%A1ng%20mix%20tana.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (6, 6, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20h%E1%BB%93ng%20mix%20baby.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (7, 7, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20h%E1%BB%93ng%20tr%E1%BA%AFng%20mix%20phi%20y%E1%BA%BFn.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (8, 8, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20sen%20%C4%91%C3%A1%20.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (9, 9, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20c%C6%B0%E1%BB%9Bi%20tulip%20.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (10, 10, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20c%C6%B0%E1%BB%9Bi/Hoa%20sen%20c%C6%B0%E1%BB%9Bi.webp', 'ENABLE');
INSERT INTO `flowerimages` VALUES (11, 11, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Albus.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (12, 12, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Always%20In%20Heart.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (13, 13, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Antonio.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (14, 13, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Antonio2.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (15, 13, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Antonio3.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (16, 13, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Antonio4.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (17, 14, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Asher.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (18, 15, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Delilah.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (19, 16, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Grace.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (20, 17, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Noah.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (21, 18, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Respect.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (22, 19, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/See%20you%20in%20Heaven.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (23, 20, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20tang/Won\'t%20Be%20Forgotten.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (24, 21, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Big%20Success.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (25, 22, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Celebration%20Flowers.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (26, 22, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Celebration%20Flowers2.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (27, 22, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Celebration%20Flowers3.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (28, 22, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Celebration%20Flowers4.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (29, 23, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Lovely%20Day.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (30, 24, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Naila.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (31, 25, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/The%20Best%20Wishes.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (32, 26, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Xin.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (33, 26, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Xin2.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (34, 26, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Xin3.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (35, 27, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ch%C3%BAc%20m%E1%BB%ABng/Yashita.jpg', 'ENABLE');
INSERT INTO `flowerimages` VALUES (36, 28, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-01.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (37, 29, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-02.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (38, 29, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-022.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (39, 29, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-023.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (40, 29, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-024.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (41, 30, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-03.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (42, 30, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-032.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (43, 30, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-033.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (44, 31, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20k%E1%BB%B7%20ni%E1%BB%87m/KH-04.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (45, 32, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-01.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (46, 32, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-012.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (47, 32, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-013.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (48, 33, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-02.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (49, 33, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-022.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (50, 33, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-023.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (51, 33, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-024.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (52, 34, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-03.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (53, 34, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-032.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (54, 34, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-033.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (55, 34, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-034.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (56, 35, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-04.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (57, 35, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-042.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (58, 35, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-043.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (59, 36, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-05.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (60, 36, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-052.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (61, 36, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-053.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (62, 37, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-06.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (63, 37, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH-062.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (64, 38, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH_07.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (65, 38, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH_072.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (66, 38, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH_073.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (67, 38, 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/Hoa%20ki%E1%BB%83ng/GH_074.png', 'ENABLE');
INSERT INTO `flowerimages` VALUES (1115, 19, 'https://storage.googleapis.com/webproject-65086.appspot.com/product/4fe74995-60eb-4710-9c65-792cffc7ae74', 'DISABLE');

-- ----------------------------
-- Table structure for flowersize
-- ----------------------------
DROP TABLE IF EXISTS `flowersize`;
CREATE TABLE `flowersize`  (
  `flower_sizeid` int NOT NULL AUTO_INCREMENT,
  `FlowerID` int NOT NULL,
  `size_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Length` double NOT NULL,
  `High` double NOT NULL,
  `Width` double NOT NULL,
  `Weight` double NOT NULL,
  `Stock` int NOT NULL,
  `Cost` decimal(50, 2) NOT NULL,
  `Price` decimal(50, 2) NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`flower_sizeid`) USING BTREE,
  INDEX `FKj6k6cwg6qaue81uvrttg7lou5`(`FlowerID` ASC) USING BTREE,
  CONSTRAINT `FKj6k6cwg6qaue81uvrttg7lou5` FOREIGN KEY (`FlowerID`) REFERENCES `flower` (`FlowerID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 139 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of flowersize
-- ----------------------------
INSERT INTO `flowersize` VALUES (1, 1, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (2, 1, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (3, 1, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (4, 2, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (5, 2, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (6, 2, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (7, 2, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (8, 3, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (9, 3, 'Size vừa', 20, 20, 20, 5, 42, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (10, 3, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (11, 4, 'Size nhỏ', 8, 8, 8, 1.5, 35, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (12, 4, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (13, 4, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (14, 4, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (15, 5, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (16, 5, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (17, 5, 'Size lớn', 25, 25, 25, 10, 30, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (18, 6, 'Size nhỏ', 8, 8, 8, 1.5, 43, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (19, 6, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (20, 6, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (21, 6, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (22, 7, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (23, 7, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (24, 7, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (25, 8, 'Size nhỏ', 8, 8, 8, 1.5, 45, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (26, 8, 'Size vừa', 15, 15, 15, 4, 48, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (27, 8, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (28, 8, 'Size khủng', 30, 30, 30, 15, 8, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (29, 9, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (30, 9, 'Size vừa', 20, 20, 20, 5, 45, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (31, 9, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (32, 10, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (33, 10, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (34, 10, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (35, 10, 'Size khủng', 30, 30, 30, 15, 5, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (36, 11, 'Size nhỏ', 10, 10, 10, 2, 41, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (37, 11, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (38, 11, 'Size lớn', 25, 25, 25, 10, 19, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (39, 12, 'Size nhỏ', 8, 8, 8, 1.5, 43, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (40, 12, 'Size vừa', 15, 15, 15, 4, 44, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (41, 12, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (42, 12, 'Size khủng', 30, 30, 30, 15, 7, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (43, 13, 'Size nhỏ', 10, 10, 10, 2, 49, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (44, 13, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (45, 13, 'Size lớn', 25, 25, 25, 10, 13, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (46, 14, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (47, 14, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (48, 14, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (49, 14, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (50, 15, 'Size nhỏ', 10, 10, 10, 2, 42, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (51, 15, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (52, 15, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (53, 16, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (54, 16, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (55, 16, 'Size lớn', 23, 23, 23, 9, 15, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (56, 16, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (57, 17, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (58, 17, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (59, 17, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (60, 18, 'Size nhỏ', 8, 8, 8, 1.5, 45, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (61, 18, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (62, 18, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (63, 18, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (64, 19, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (65, 19, 'Size vừa', 20, 20, 20, 5, 44, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (66, 19, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (67, 20, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (68, 20, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (69, 20, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (70, 20, 'Size khủng', 30, 30, 30, 15, 5, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (71, 21, 'Size nhỏ', 10, 10, 10, 2, 39, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (72, 21, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (73, 21, 'Size lớn', 25, 25, 25, 10, 16, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (74, 22, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (75, 22, 'Size vừa', 15, 15, 15, 4, 15, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (76, 22, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (77, 22, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (78, 23, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (79, 23, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (80, 23, 'Size lớn', 25, 25, 25, 10, 15, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (81, 24, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (82, 24, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (83, 24, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (84, 24, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (85, 25, 'Size nhỏ', 10, 10, 10, 2, 40, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (86, 25, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (87, 25, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (88, 26, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (89, 26, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (90, 26, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (91, 26, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (92, 27, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (93, 27, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (94, 27, 'Size lớn', 25, 25, 25, 10, 10, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (95, 28, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (96, 28, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (97, 28, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (98, 28, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (99, 29, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (100, 29, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (101, 29, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (102, 30, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (103, 30, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (104, 30, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (105, 30, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (106, 31, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (107, 31, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (108, 31, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (109, 32, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (110, 32, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (111, 32, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (112, 32, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (113, 33, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (114, 33, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (115, 33, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (116, 34, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (117, 34, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (118, 34, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (119, 34, 'Size khủng', 30, 30, 30, 15, 8, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (120, 35, 'Size nhỏ', 10, 10, 10, 2, 50, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (121, 35, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (122, 35, 'Size lớn', 25, 25, 25, 10, 20, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (123, 36, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (124, 36, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (125, 36, 'Size lớn', 23, 23, 23, 9, 19, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (126, 36, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (127, 37, 'Size nhỏ', 10, 10, 10, 2, 48, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (128, 37, 'Size vừa', 20, 20, 20, 5, 50, 140000.00, 160000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (129, 37, 'Size lớn', 25, 25, 25, 10, 19, 200000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (130, 38, 'Size nhỏ', 8, 8, 8, 1.5, 50, 50000.00, 80000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (131, 38, 'Size vừa', 15, 15, 15, 4, 50, 120000.00, 140000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (132, 38, 'Size lớn', 23, 23, 23, 9, 20, 180000.00, 240000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (133, 38, 'Size khủng', 30, 30, 30, 15, 10, 240000.00, 300000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (136, 3, 'Size nhỏ', 20, 20, 210, 3, 10, 80000.00, 100000.00, 'ENABLE');
INSERT INTO `flowersize` VALUES (137, 9, 'dsafdsaf', 11, 11, 11, 11, 18, 1111109.00, 1111111.00, 'ENABLE');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `NewsID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  `Content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Date` datetime NOT NULL,
  PRIMARY KEY (`NewsID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, 'Hoa tươi cao cấp nhập khẩu từ nước ngoài\r\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/News/cr16-2-1-1367044107.jpg', 'ENABLE', 'Hàng trăm sản phẩm hoa tươi cao cấp của 38˚ Flowers đã được nhiều khách hàng đặt mua trong tuần lễ 20/10 vừa qua. Với thế mạnh là hoa tươi nhập khẩu và kỹ thuật thiết kế hoa, 38˚ Flowers là một trong những thương hiệu được ưa chuộng hiện nay. Trong dịp 20/10 vừa qua, các sản phẩm hoa tươi, đặc biệt là dòng sản phẩm cao cấp của cửa hàng (trị giá từ 10 triệu đồng trở lên) đã nhận được sự hưởng ứng từ nhiều khách hàng trên toàn quốc.\r\n\r\n38˚ Flowers cũng tạo dấu ấn trong cộng đồng với các hoạt động triển lãm hoa và khóa học thiết kế hoa quốc tế. Triển lãm hoa quốc tế diễn ra từ ngày 18 đến 20/10 tại Cresent Mall, quận 7, TP HCM đã thu hút hơn 3.000 lượt khách tham quan và nhận được những phản hồi tích cực từ fan nghệ thuật cắm hoa. Đây cũng là thương hiệu hoa đầu tiên tại Việt Nam có sự cộng tác về mặt thiết kế với các nghệ nhân quốc tế, trong đó có Walter Fedyshyn, nhà thiết kế hoa tươi hàng đầu tại Mỹ.\r\n\r\n', '2024-11-26 18:56:08');
INSERT INTO `news` VALUES (2, 'Chương trình quà tặng đặc biệt từ 38˚ Flowers\r\n', 'https://storage.googleapis.com/webproject-65086.appspot.com/banner%20flower/Flower/News/h1-hinh-anh-tu-buoi-trien-lam-1367044972.jpg', 'ENABLE', 'Duy nhất trong tối 18/10, 100 phần quà đặc biệt sẽ được 38˚ Flowers gửi tặng những vị khách đầu tiên đến tham quan buổi biểu diễn của nghệ nhân thiết kế hoa nổi tiếng thế giới - Walter Fedyshyn AIFD, PFCI.\r\n\r\nBuổi biểu diễn nghệ thuật cắm hoa quốc tế là một trong các hoạt động thuộc Triển lãm hoa tươi nghệ thuật cao cấp được 38˚ Flowers tổ chức tại Cresent Mall, quận 7, trong 3 ngày, 18, 19 và 20/10. Triển lãm được chuẩn bị công phu để chào mừng ngày Phụ nữ Việt Nam, với sự góp mặt của Walter Fedyshyn AIFD, PFCI, người nổi tiếng trong lĩnh vực thiết kế hoa tươi đến từ Mỹ và các nghệ nhân trên toàn quốc.\r\n\r\n100 vị khách đầu tiên đến thưởng lãm buổi biểu diễn từ 18h30 đến 20h30 ngày 18/10 sẽ được nhận phần quà giá trị từ 38˚ Flowers. Đó là những cành tulip tinh khiết đầu mùa từ Hà Lan, hoa hồng nhập khẩu từ Ecuador với kích thước lớn kèm theo voucher trị giá 300.000 đồng.\r\n\r\n', '2024-11-26 18:56:55');
INSERT INTO `news` VALUES (3, 'Gợi ý 4 loài hoa tặng cha đặc biệt nhất bạn nên biết', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/04606510-7e9c-42e9-bf58-1e0c5b91a142', 'ENABLE', 'Người ta thường ví công cha như núi Thái Sơn cao vời vợi không bao giờ có thể đo được. Nếu tình mẹ là suối nguồn dịu dàng, êm ả thì tình cha lại vô cùng vững chãi, là ngọn núi kiên cố nhất để con tựa vào, là tấm khiên chắc chắn nhất bảo vệ con khỏi những điều xấu xa. Đối với con, nhiều lúc cha rất nghiêm khắc nhưng con biết đằng sau sự nghiêm khắc đó là sự hy sinh thầm lặng, là những lúc lo âu cho đứa con thơ của mình.\n\nSắp đến Ngày của Cha Hoa18 sẽ bật mí cho bạn 4 loài hoa tặng cha ý nghĩa nhất để gửi tặng người cha hiền kính yêu của mình nhé! Nhân dịp này, Hoa18 cũng xin gửi lời cảm ơn đến tất cả người cha trên thế giới này và chúc cho họ luôn luôn vui vẻ, khoẻ mạnh.\n\nGợi ý 4 loài hoa tặng cha đặc biệt nhất bạn nên biết\nNgày của Cha là ngày nào trong năm nay?\nTrước khi đi vào nội dung chính của bài chúng ta hãy cùng tìm hiểu về Ngày của Cha nhé!\n\nNgày của Cha hay còn gọi là ngày Father’s Day bắt nguồn từ lễ tưởng niệm được tổ chức vào ngày 5/7/1908 tại Fairmont (West Virginia, Hoa Kỳ) bởi Grace Golden Clayton nhằm tưởng nhớ đến những người cha đã bị thiệt mạng trong một vụ nổ khủng khiếp vào tháng 12/1907. Ngày của Cha không có quy định ngày cụ thể mà được quy định vào chủ nhật thứ 3 của tháng Sáu. Năm 2022, ngày của Cha là ngày 19/6/2022.\n\nỞ Việt Nam, nhờ sự sự giao thoa văn hóa trong những năm gần đây nên bên cạnh những dịp truyền thống như lễ vu lan báo hiếu thì Ngày của Cha cũng dần trở nên quen thuộc với mọi người. Đây là dịp để con cái thể hiện tấm lòng hiếu kính và gửi những lời chúc cũng như món quà ý nghĩa đến người Cha của mình.\n\nĐối với các bậc làm cha mẹ, không có niềm vui nào sánh bằng việc con cái trưởng thành lớn khôn. Vì vậy hãy cố gắng rèn luyện phấn đấu từng ngày để ngày nào cũng là ngày của Cha, ngày của Mẹ. Chúng ta hãy luôn trân trọng từng giây phút bên cạnh gia đình thân yêu của mình, bạn nhé!\n\nBật mí 4 loài hoa tượng trưng cho cha\n1. Lan hồ điệp\n\nhoa tặng cha\nLựa chọn hoa tươi trao tặng người thân yêu tại Hoa18\nThực tế nhiều người rất khó nói ra một câu ngắn gọn là “con yêu bố” dù trong lòng họ hình ảnh người cha vô cùng to lớn, vĩ đại. Khi ấy, loài hoa tượng trưng cho cha như hoa lan hồ điệp là sự lựa chọn tuyệt vời để gửi tặng đến cha thay cho lời yêu thương và cảm ơn của đứa con.\n\nLan hồ điệp là một trong số ít loài hoa toát lên vẻ đẹp cao quý và sang trọng. Bên cạnh đó loài hoa này còn cuốn hút bởi rất nhiều màu sắc tươi đẹp và hương thơm nồng nàn. Vẻ đẹp của hoa là biểu tượng của sự vẹn toàn, mang đến nguồn năng lượng và vượng khí tốt. Một chậu lan mang đến những điều tốt đẹp dành tặng cha, cầu mong cho cha sẽ luôn bình an, sức khoẻ dồi dào và hạnh phúc.\n\nKhi con cái trưởng thành, lập gia đình, rời khỏi vòng tay cha mẹ cũng là lúc cha mẹ ngày càng già đi. Người lớn tuổi thường luôn cảm thấy cô đơn, trống trải nên việc tặng hoa là sự quan tâm rất ngọt ngào và tinh tế. Tặng bố chậu lan giúp bố có thêm niềm vui mới, không chỉ được ngắm hoa mỗi ngày mà bố có thêm thú vui chăm sóc cho cây. Không gian ngôi nhà cũng có thêm sức sống và ngập tràn niềm vui, sự ấm áp.\n\nHoa mừng ngày của cha “Lan Hồ Điệp Cốm” với sắc hoa tinh tế, sang trọng là món quà rất phù hợp để gửi tặng đến cha. Cánh hoa tươi thơm nhè nhẹ cùng màu sắc xanh cốm độc đáo khiến lòng ta bỗng dưng thấy yên bình đến lạ thường. Qua đó cũng thể hiện được sự kính trọng, lòng hiếu thảo và biết ơn sâu sắc của bạn đối với đấng sinh thành.\nhoa tặng cha\nThiết kế hoa mừng ngày của cha tao nhã và sang trọng\n2. Hoa hướng dương\n\nhoa tặng cha\nHoa tặng cha đẹp và ý nghĩa bạn nên biết\nĐối với nhiều bạn việc dành một đóa hoa tặng cha là hơi lạ, vì trong suy nghĩ của nhiều người hoa chỉ để dành tặng cho phái nữ, mà chúng ta lại quên đi rằng hoa tươi có thể tặng chung cho mọi đối tượng. Những bông hoa hướng dương luôn hướng về ánh mặt trời dù nó có khó khăn thế nào giống như hình ảnh của Cha luôn luôn mạnh mẻ vượt qua mọi khó khăn trong cuộc sống hàng ngày.\n\nĐoá hoa tựa như mặt trời nhỏ, tình cha như mặt trời lớn luôn soi sáng dẫn dắt con bước từng bước trên đường đời, chiếu rọi những tia nắng ấm áp của niềm tin và hy vọng giúp con trưởng thành và trở nên mạnh mẽ hơn. Sẽ tuyệt vời biết bao khi bạn dành tặng cho Cha đoá hướng dương để bày tỏ tình cảm và lòng biết ơn của mình, chắc chắn khi nhận được quà cha sẽ rất vui mừng và hạnh phúc đấy!\n\nBó hoa hướng dương “Ánh Sáng Ban Mai” với hoa hướng dương làm chủ đạo, điểm xuyến thêm cho nó là những cánh hoa Salem nhẹ như giấy giống như những trang nhật ký yêu thương của cha ghi lại hành trình trưởng thành của đứa con thơ. Đó là những hồi ức đẹp nhất trong cuộc đời cha và cũng là hồi ức đẹp nhất của con vì luôn có cha mẹ đồng hành và dẫn lối.\nhoa tặng cha\nLựa chọn hoa tươi ý nghĩa gửi tặng người cha đáng kính\n3. Hoa sen\n\nhoa tặng cha\nThiết kế hoa tươi sang trọng và ý nghĩa\nHoa sen là loài hoa vô cùng quen thuộc trong đời sống người Việt Nam, từ ngàn xưa, hình ảnh hoa sen đã xuất hiện trong các tác phẩm văn học hay thi ca, kiến trúc,… Những đóa sen rạng rỡ, tỏa sáng cả một vùng trời, mùi thơm thoang thoảng, không hề bị hòa lẫn với những mùi “hôi tanh”, hoa sen đi vào cuộc sống hàng ngày của người dân Việt Nam với nhiều ý nghĩa khác nhau.\n\nTrong cuộc sống hằng ngày, người ta tặng nhau đóa hoa sen để bày tỏ lòng tôn kính, để tán dương phẩm chất trong sáng, thuần khiết của đối phương. Với ý nghĩa này thì hoa sen là một loài hoa đáng để chúng ta lựa chọn làm hoa mừng ngày của Cha thể hiện lòng kính yêu và sự ngưỡng mộ với người cha vĩ đại của mình.\n\nHoa để bàn “Đoá Sen Hồng” vừa sang trọng vừa tiện dụng là món quà hoa tươi hoàn hảo gửi tặng cha, sen hồng là loài sen phổ biến nhất ở Việt Nam và được chọn là quốc hoa biểu tượng cho những đức tính cao đẹp của con người Việt Nam. Ngoài ra, loài hoa này còn thể hiện tình cảm chân thành, sâu sắc và trong sáng của người con gửi đến đấng sinh thành. Gửi tặng cha món quà từ hoa sen để cha đặt trên bàn làm việc, ở bàn tiếp khách hoặc nơi cha thường uống trà chơi cờ vừa tao nhã vừa toả hương thơm ngát giải toả căng thẳng.\n', '2024-12-04 05:24:32');
INSERT INTO `news` VALUES (4, 'Top 5 loài hoa tặng mẹ đong đầy ý nghĩa bạn nên biết', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/f13af6e4-8e78-4443-95a2-8a859420bfdb', 'ENABLE', 'Top 5 loại hoa tặng mẹ đẹp và ý nghĩa nhất bạn nên biết\n1. Hoa hồng\nhoa tặng mẹ\nLựa chọn hoa tặng mẹ đầy ý nghĩa tại Hoa18\nChắc hẳn ai trong chúng ta cũng biết rằng hoa hồng là loài hoa đại diện cho tình yêu nhưng có một điều vô cùng đặc biệt đó là mỗi loại hoa hồng khác nhau với màu sắc khác nhau sẽ mang theo từng ý nghĩa tình cảm riêng đấy.\n\nĐể chọn đóa hồng làm hoa tặng mẹ bạn có thể chọn hoa hồng phấn hoặc hồng kem với màu sắc vô cùng nhẹ nhàng và trang nhã. Hai loại hoa này đều thể hiện sự ngọt ngào và tha thiết trong tình yêu và cũng là loài hồng dùng để bày tỏ lòng biết ơn với mẹ. Ngoài ra, một đóa hồng tím tượng trưng cho sự vĩnh cửu cũng rất thích hợp để tặng mẹ yêu. Tình cảm của mẹ dành cho con luôn là điều vĩnh hằng và tình yêu con dành cho mẹ cũng vậy.\n\nGợi ý: Gửi tặng mẹ bó hoa tươi “Ánh Nắng Ấm Áp” bày tỏ tình cảm chân thành nhất đến mẹ. Với những bông hồng cam nhạt vừa nhẹ nhàng vừa ngọt ngào, lãng mạn. Loài hồng này tuy không có sắc màu rực rỡ như chị em cùng họ của mình nhưng vẫn gây thương nhớ bởi vẻ ngoài quá đỗi mong manh, xinh đẹp.\n\nhoa tặng mẹ\nHoa tươi đẹp và ý nghĩa dành tặng người thân yêu\n2. Hoa cẩm chướng\nhoa tặng mẹ\nHoa tặng mẹ đẹp và ý nghĩa nhất tại Hoa18\nHoa cẩm chướng là một trong những loài hoa được biết đến và sử dụng nhiều nhất trên thế giới. Tương truyền, hoa này đã có từ rất lâu tại Hy Lạp thời cổ đại sau đó được du nhập sang châu Âu và các nước khác. Hoa cẩm chướng có danh pháp khoa học là Dianthus, tên gọi này được ghép thành từ 2 từ trong tiếng latin là “Dios” có nghĩa là “Chúa trời” và “anthos” có nghĩa là “Hoa” vì vậy, hoa cẩm chướng còn được gọi là loài hoa của chúa trời.\n\nCẩm chướng cũng là loài hoa mang ý nghĩa tượng trưng cho tình yêu. Tuy nhiên, ý nghĩa tình yêu của hoa cẩm chướng không đơn thuần chỉ là tình yêu đôi lứa, mà còn là tình cảm, sự yêu thương giữa những người thân trong gia đình, giữa những người bạn. Và đặc biệt hoa cẩm chướng là loài hoa biểu tượng cho tình mẹ, và cũng là loài hoa được dùng để tặng mẹ nhiều nhất trong các dịp lễ.\n\nBạn có thể lựa chọn cẩm chướng đỏ để thể hiện lòng cảm phục của bạn, mẹ là người phụ nữ tuyệt vời và vĩ đại nhất trong cuộc đời của con. Hoặc có thể lựa chọn đóa cẩm chướng hồng tặng để bày tình cảm đơn thuần, sự kính mến và lòng tri ân sâu sắc đến người mẹ tuyệt vời.\n\nGợi ý: Bó hoa “Màu Yêu Thương” với hoa chủ đạo là cẩm chướng tím kết hợp cùng hoa hồng và hoa cát tường đong đầy bao yêu thương con dành cho mẹ. Sự hoàn mỹ của bó hoa là ở vẻ ngoài rực rỡ và ý nghĩa sâu lắng. Không chỉ thu hút người nhìn bởi vẻ ngoài đầy kiêu sa, ngọt ngào mà còn hấp dẫn bởi những ý nghĩa sâu sắc. Đây chắc chắn sẽ là thông điệp yêu thương rõ ràng nhất gửi đến những người bạn yêu thương.\n\nhoa tặng mẹ\nThiết kế hoa tươi sang trọng\n3. Hoa lan hồ điệp\nhoa tặng mẹ\nMua hoa tặng mẹ đẹp nhất tại Hoa18\nHoa lan hồ điệp không chỉ là một loài hoa cao quý, sang trọng, tinh tế mà còn mang ý nghĩa may mắn, tài lộc. Người ta cho rằng, lan hồ điệp mang ý nghĩa của sự may mắn, sung túc và hạnh phúc. Tặng mẹ hoa lan hồ điệp để truyền đạt mong mỏi mẹ được vui vẻ, hạnh phúc mãi mãi.\n\nLan hồ điệp còn còn tượng trưng cho người phụ nữ dịu dàng, đằm thắm nhưng cũng rất mạnh mẽ. Người phụ nữ ấy hy sinh cả đời vì chồng vì con, luôn muốn dành những điều tốt nhất cho gia đình nhỏ của mình. Cho nên, gửi tặng mẹ nhành hoa lan nhằm tôn vinh sự cao cả của mẹ, dù cho có trải qua bao sóng gió thì vẫn còn đó tấm lòng của mẹ đẹp son sắt với thời gian.\n\nNgoài ra, lan hồ điệp còn là biểu tượng của no ấm, đủ đầy, của một gia đình gắn kết và bền chặt, đó cũng là ước mơ mẹ cả đời vun vén, và cũng là nỗ lực của con bù đắp khó nhọc của mẹ. Tặng một chậu lan, mẹ và con cùng chăm sóc sẽ giúp gắn kết tình cảm hai mẹ con thêm thân thiết hơn.\n\nGợi ý: Chậu hồ điệp tím “May Mắn” vừa sang trọng vừa thanh lịch rất thích hợp để làm hoa tặng ngày của mẹ. Nhành lan tím uyển chuyển, duyên dáng tựa như người phụ nữ Việt Nam luôn dịu dang, tần tảo sớm hôm vun vén hạnh phúc gia đình. Trao yêu thương đến người mẹ hiền, gửi đến mẹ lời cảm ơn sâu sắc nhất của con.\n\nhoa tặng mẹ\nLựa chọn hoa tươi đẹp mang lại bất ngờ tuyệt vời cho người thương\n4. Hoa cát tường\nhoa tặng mẹ\nMua hoa tặng mẹ vừa đẹp vừa ý nghĩa tại Hoa18\nHoa cát tường không rực rỡ như hoa hướng dương, không mãnh liệt như hoa hồng đỏ nhưng lại toát lên vẻ đẹp quý phái, đằm thắm và ngọt ngào. Người Nhật Bản và hầu hết người Á Đông đều chuộng hoa cát tường vì cát tường còn có nghĩa là may mắn, như ý. Đây còn là loài hoa biểu tượng cho tình cảm nhẹ nhàng, tinh tế nhưng cũng rất sâu sắc.\n\nLoại hoa tươi này mang nhiều màu sắc khác nhau với nhiều ý nghĩa khác nhau nhưng màu nào cũng vô cùng duyên dáng đáng yêu. Những bông hoa cát tường với cánh hoa mềm mại như khoác lên mình chiếc váy bồng bềnh xinh đẹp khiến người ta liên tưởng tới vẻ đẹp đằm thắm của người phụ nữ, lại có chút gì đó mạnh mẽ, kiên cường như mẹ vậy. Dù cho có bao nhiêu khó khăn vất vả nhưng vì con mẹ vẫn cố gắng vượt qua, lo cho con cuộc sống tốt nhất có thể.\n\nVì vậy, đây cũng là loài hoa thường được sử dụng nhiều trong ngày của mẹ. Bởi vì màu sắc của hoa thiên về nhẹ nhàng nên màu hoa nào cũng phù hợp để bạn gửi tặng mẹ của mình. Bạn có thể chọn màu theo sở thích của mẹ hoặc chọn theo thông điệp của màu hoa.\n\nHoa cát tường màu hồng bày tỏ tình yêu thương xuất phát từ sâu trong trái tim bạn\nHoa cát tường trắng tượng trưng cho sự khởi đầu và cũng thể hiện sự tinh tế trong tình cảm\nHoa cát tường tím đầy sang trọng biểu trưng cho tình yêu vĩnh cửu, là tình mẹ dạt dào và cũng là tình yêu con dành cho mẹ.\nHoa cát tường vàng thể hiện niềm vui và hạnh phúc, là lời chúc sức khỏe và may mắn gửi đến mẹ\nHoa cát tường xanh đại diện cho sự giàu có, phồn thịnh, chúc cho mẹ luôn luôn gặp được những điều tốt đẹp nhất trong cuộc sống này.\nGợi ý: “Chân trời mới” là bó hoa tươi độc đáo bởi sắc hoa vừa hiện đại lại sang trọng. Sắc hoa cát tường nhẹ nhàng điểm tô thêm màu chuỗi ngọc ngọt ngào mang đến cho bạn một sản phẩm hoa ngày của mẹ không thể bỏ qua trong ngày của mẹ sắp tới. Bó hoa này chắc chắn sẽ là món quà ngọt ngào nhất gửi tặng đến mẹ yêu đấy!', '2024-12-04 05:26:21');
INSERT INTO `news` VALUES (5, '🌸 Chào Mừng Tháng 10 – Tháng của Phụ Nữ Việt Nam 20/10 🌸', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/2fcfb996-3a15-4895-b191-a8a64adbbc8f', 'ENABLE', '🌟 Đặt hàng ngay hôm nay để nhận ưu đãi đặc biệt và làm cho những dịp lễ trong tháng 10 của bạn thêm phần rực rỡ! 🌟\nTháng 10 đã đến, và với nó là một loạt các dịp lễ đặc biệt để thể hiện tình yêu và sự quan tâm của mình qua những bó hoa tươi thắm.\nHãy để Tiệm Hoa Nàng Thơm giúp bạn làm cho những khoảnh khắc này thêm phần ý nghĩa và đáng nhớ hơn nhé', '2024-10-20 05:28:31');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `OrderID` int NOT NULL AUTO_INCREMENT,
  `AccountID` int NOT NULL,
  `order_date` datetime NOT NULL,
  `IsPaid` enum('Yes','No') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `total_amount` decimal(50, 2) NULL DEFAULT NULL,
  `delivery_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  `ShippingID` int NULL DEFAULT NULL,
  `Note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Condition` enum('Pending','Processing','Prepare','In_Transit','Shipper_Delivering','First_Attempt_Failed','Second_Attempt_Failed','Third_Attempt_Failed','Cancelled','Delivered_Successfully','Return_to_shop','Cancel_is_Processing') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Pending',
  PRIMARY KEY (`OrderID`) USING BTREE,
  INDEX `AccountID`(`AccountID` ASC) USING BTREE,
  INDEX `shipping`(`ShippingID` ASC) USING BTREE,
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `shipping` FOREIGN KEY (`ShippingID`) REFERENCES `shipping` (`ShippingID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 7, '2024-11-01 07:00:00', 'Yes', 500000.00, 'testt', '0989087676', 'testtdà', 'ENABLE', 1, 'testtfdsf', 'Shipper_Delivering');
INSERT INTO `order` VALUES (2, 7, '2024-09-11 07:00:00', 'Yes', 180000.00, '123 Street, City', '123456789', 'John Doe', 'ENABLE', 1, 'Please deliver before 5 PM', 'In_Transit');
INSERT INTO `order` VALUES (4, 7, '2024-10-15 07:00:00', 'Yes', 270000.00, '123 Street, City', '123456789', 'John Doe', 'ENABLE', NULL, 'Please deliver before 5 PM', 'Pending');
INSERT INTO `order` VALUES (5, 7, '2024-07-10 07:00:00', 'Yes', 220000.00, '123 Street, City', '123456789', 'John Doe', 'ENABLE', 4, 'Please deliver before 5 PM', 'Pending');
INSERT INTO `order` VALUES (6, 7, '2024-10-19 07:00:00', 'Yes', 220000.00, '123 Street, City', '123456789', 'John Doe', 'ENABLE', NULL, 'Please deliver before 5 PM', 'Cancel_is_Processing');
INSERT INTO `order` VALUES (7, 7, '2024-10-10 07:00:00', 'Yes', 220000.00, '123 Street, City', '123456789', 'John Doe', 'ENABLE', NULL, 'Please deliver before 5 PM', 'Prepare');
INSERT INTO `order` VALUES (8, 7, '2024-11-12 07:00:00', 'Yes', 220000.00, '123 Street, City', '123456789', 'John Doe', 'ENABLE', NULL, 'Please deliver before 5 PM', 'Processing');
INSERT INTO `order` VALUES (10, 7, '2024-10-27 07:00:00', 'Yes', 500000.00, '123', '123', '123', 'ENABLE', 4, '123', 'Prepare');
INSERT INTO `order` VALUES (12, 18, '2024-11-30 15:50:20', 'Yes', 480000.00, 'TPHCM UTE', '0845418995', 'thientho220503', 'ENABLE', NULL, 'gói hàng giúp tôi', 'Cancelled');
INSERT INTO `order` VALUES (13, 18, '2024-07-30 07:00:00', 'Yes', 144000.00, 'TPHCM UTE', '0845418995', 'thientho220503', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (14, 18, '2024-11-30 15:54:44', 'Yes', 144000.00, 'TPHCM UTE', '0845418995', 'thientho220503', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (15, 18, '2024-10-24 07:00:00', 'Yes', 270000.00, 'TPHCM UTE', '0845418995', 'thientho220503', 'ENABLE', 7, '', 'Pending');
INSERT INTO `order` VALUES (16, 18, '2024-11-30 15:59:22', 'Yes', 100000.00, 'TPHCM UTE', '0845418995', 'thientho220503', 'ENABLE', 6, '', 'Delivered_Successfully');
INSERT INTO `order` VALUES (17, 21, '2024-11-30 16:39:31', 'No', 240000.00, 'dfasdfadsfas', '0845418295', 'thientho2205user', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (18, 21, '2024-11-30 16:39:46', 'No', 1000000.00, 'dfasdfadsfas', '0845418295', 'thientho2205user', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (19, 21, '2024-11-30 16:40:04', 'No', 1280000.00, 'dfasdfadsfas', '0845418295', 'thientho2205user', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (20, 21, '2024-11-30 16:49:13', 'Yes', 9600000.00, 'dfasdfadsfas', '0845418295', 'thientho2205user', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (21, 21, '2024-10-30 07:00:00', 'Yes', 4200000.00, 'dfasdfadsfas', '0845418295', 'thientho2205user', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (22, 7, '2024-11-30 17:32:15', 'Yes', 1476000.00, '10 A B C D E ', '0559348044', 'Tester Number 1', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (23, 7, '2024-11-30 17:37:10', 'Yes', 574000.00, '10 A B C D E ', '0559348044', 'Tester Number 1', 'ENABLE', 8, '', 'Delivered_Successfully');
INSERT INTO `order` VALUES (24, 7, '2024-11-30 23:03:33', 'Yes', 966000.00, '10 A B C D E ', '0559348044', 'Tester Number 1', 'ENABLE', 9, '', 'Third_Attempt_Failed');
INSERT INTO `order` VALUES (25, 22, '2024-12-04 12:31:11', 'No', 100000.00, 'dfasdfadsfas', '0845418922', 'iuzmi2', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (26, 22, '2024-12-04 12:31:48', 'No', 140000.00, 'dfasdfadsfas', '0845418922', 'iuzmi2', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (27, 23, '2024-12-04 12:33:31', 'No', 100000.00, 'đàasdfadsfasdfa', '08454182222', 'izmumkdfj2', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (28, 23, '2024-12-04 12:33:49', 'No', 80000.00, 'đàasdfadsfasdfa', '08454182222', 'izmumkdfj2', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (29, 23, '2024-12-04 12:33:57', 'No', 100000.00, 'đàasdfadsfasdfa', '08454182222', 'izmumkdfj2', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (30, 24, '2024-12-04 12:36:34', 'No', 180000.00, 'đàasdfadsfasdfa', '0845118995', 'Huy Tran', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (31, 24, '2024-12-04 12:38:52', 'No', 180000.00, 'đàasdfadsfasdfa', '0845118995', 'Huy Tran', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (32, 25, '2024-12-04 12:39:41', 'No', 180000.00, '2safdsfadsfa', '0845118995', 'Người đẹp trai nhất hành tinh', 'ENABLE', NULL, '', 'Pending');
INSERT INTO `order` VALUES (33, 26, '2024-12-04 12:42:24', 'No', 180000.00, 'dfasdfadsfas', '0845418922', 'Chồng của Kafka', 'ENABLE', NULL, '', 'Pending');

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail`  (
  `OrderdetailID` int NOT NULL AUTO_INCREMENT,
  `OrderID` int NOT NULL,
  `flower_sizeid` int NOT NULL,
  `Quantity` int NOT NULL,
  `Price` decimal(50, 2) NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`OrderdetailID`) USING BTREE,
  INDEX `OrderID`(`OrderID` ASC) USING BTREE,
  INDEX `fs`(`flower_sizeid` ASC) USING BTREE,
  CONSTRAINT `fs` FOREIGN KEY (`flower_sizeid`) REFERENCES `flowersize` (`flower_sizeid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orderdetail_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orderdetail
-- ----------------------------
INSERT INTO `orderdetail` VALUES (1, 1, 11, 3, 20.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (2, 1, 8, 8, 20.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (3, 1, 15, 10, 11.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (4, 1, 2, 20, 11.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (5, 1, 21, 20, 11.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (6, 1, 28, 3, 20.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (7, 1, 34, 8, 20.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (8, 1, 40, 10, 11.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (9, 1, 48, 20, 11.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (10, 1, 111, 20, 11.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (13, 4, 35, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (14, 4, 40, 5, 80000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (15, 4, 45, 5, 90000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (16, 5, 50, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (17, 5, 50, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (18, 5, 55, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (19, 5, 55, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (20, 6, 60, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (21, 6, 60, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (22, 6, 65, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (23, 6, 65, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (24, 7, 70, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (25, 7, 70, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (26, 7, 75, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (27, 7, 75, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (28, 8, 80, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (29, 8, 80, 5, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (30, 8, 85, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (31, 8, 85, 5, 120000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (33, 10, 85, 5, 500000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (34, 10, 85, 5, 500000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (36, 12, 28, 2, 480000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (37, 13, 39, 2, 144000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (38, 14, 65, 1, 144000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (39, 15, 127, 2, 180000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (40, 15, 71, 1, 90000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (41, 16, 129, 1, 216000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (42, 16, 36, 3, 270000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (43, 17, 38, 1, 240000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (44, 18, 71, 10, 1000000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (45, 19, 9, 8, 1280000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (46, 20, 17, 30, 7200000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (47, 20, 94, 10, 2400000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (48, 21, 75, 30, 4200000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (49, 22, 119, 2, 540000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (50, 22, 73, 4, 864000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (51, 22, 18, 1, 72000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (52, 23, 26, 2, 196000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (53, 23, 50, 3, 210000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (54, 23, 125, 1, 168000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (55, 24, 45, 2, 336000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (56, 24, 42, 3, 630000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (57, 25, 36, 1, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (58, 26, 40, 1, 140000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (59, 27, 36, 1, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (60, 28, 39, 1, 80000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (61, 29, 43, 1, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (62, 30, 39, 1, 80000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (63, 30, 36, 1, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (64, 31, 39, 1, 80000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (65, 31, 36, 1, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (66, 32, 39, 1, 80000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (67, 32, 36, 1, 100000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (68, 33, 39, 1, 80000.00, 'ENABLE');
INSERT INTO `orderdetail` VALUES (69, 33, 36, 1, 100000.00, 'ENABLE');

-- ----------------------------
-- Table structure for purpose
-- ----------------------------
DROP TABLE IF EXISTS `purpose`;
CREATE TABLE `purpose`  (
  `PurposeID` int NOT NULL AUTO_INCREMENT,
  `purpose_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`PurposeID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of purpose
-- ----------------------------
INSERT INTO `purpose` VALUES (1, 'Hoa cưới', 'ENABLE');
INSERT INTO `purpose` VALUES (2, 'Hoa chia buồn', 'ENABLE');
INSERT INTO `purpose` VALUES (3, 'Hoa chúc mừng', 'ENABLE');
INSERT INTO `purpose` VALUES (4, 'Hoa kỷ niệm', 'ENABLE');
INSERT INTO `purpose` VALUES (5, 'Hoa kiểng', 'ENABLE');
INSERT INTO `purpose` VALUES (6, 'Hoa sinh nhật', 'ENABLE');
INSERT INTO `purpose` VALUES (7, 'Hoa để bàn', 'ENABLE');
INSERT INTO `purpose` VALUES (8, 'Hoa treo cửa', 'ENABLE');
INSERT INTO `purpose` VALUES (9, 'Hoa phong thủy', 'ENABLE');
INSERT INTO `purpose` VALUES (10, 'Hoa handmade', 'ENABLE');

-- ----------------------------
-- Table structure for repcomment
-- ----------------------------
DROP TABLE IF EXISTS `repcomment`;
CREATE TABLE `repcomment`  (
  `RepcommentID` int NOT NULL AUTO_INCREMENT,
  `CommentID` int NOT NULL,
  `AccountID` int NOT NULL,
  `Repcommentdate` datetime NULL DEFAULT NULL,
  `Repcommenttext` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  `Image` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`RepcommentID`) USING BTREE,
  INDEX `CommentID`(`CommentID` ASC) USING BTREE,
  INDEX `AccountID`(`AccountID` ASC) USING BTREE,
  CONSTRAINT `repcomment_ibfk_1` FOREIGN KEY (`CommentID`) REFERENCES `comment` (`CommentID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `repcomment_ibfk_2` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of repcomment
-- ----------------------------
INSERT INTO `repcomment` VALUES (1, 1, 1, '2024-11-27 17:22:17', 'Không còn nhưng avatar anh đẹp', 'ENABLE', '');
INSERT INTO `repcomment` VALUES (11, 7, 7, '2024-11-27 17:22:17', 'Không còn nhưng avatar anh đẹp', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/a8d73b69-6970-4fd2-8cdb-0ffb3a6c7cc3');
INSERT INTO `repcomment` VALUES (12, 7, 7, '2024-11-27 17:22:17', 'Không còn nhưng avatar anh đẹp', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/a8d73b69-6970-4fd2-8cdb-0ffb3a6c7cc3');
INSERT INTO `repcomment` VALUES (13, 7, 7, '2024-11-29 01:50:38', 'dep ma :)) ', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/19b96ba6-e57f-401f-a6a7-23a464c5504e');
INSERT INTO `repcomment` VALUES (14, 7, 7, '2024-11-29 01:54:20', 'đẹp :))', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/f57c126d-4deb-4fc8-a313-6b0c7faafb2d');
INSERT INTO `repcomment` VALUES (15, 1, 7, '2024-11-29 01:55:28', 'test', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (16, 1, 7, '2024-11-29 01:55:42', 'test', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (17, 1, 7, '2024-11-29 01:55:53', 'test', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/ef54a25d-c6a6-40e9-ba30-03b168dd1944');
INSERT INTO `repcomment` VALUES (18, 1, 7, '2024-11-29 01:56:16', 'bị gì vậy???', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/50542eb1-bec9-495a-b74d-63037984368f');
INSERT INTO `repcomment` VALUES (19, 7, 7, '2024-11-29 01:56:31', 'alo ', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/f4901687-e909-4a6a-b17a-263ecdd8ba20');
INSERT INTO `repcomment` VALUES (20, 1, 7, '2024-11-29 02:00:07', 'test', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (21, 1, 7, '2024-11-29 02:00:25', 'test', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (22, 1, 7, '2024-11-29 02:01:39', 'tessttttt', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (23, 1, 7, '2024-11-29 02:01:45', 'testttttt', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/9874b5e0-ac3a-46c4-a61d-73080d7e57b1');
INSERT INTO `repcomment` VALUES (24, 1, 7, '2024-11-29 02:03:47', 'ok hết chưa\n', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (25, 1, 7, '2024-11-29 02:03:55', 'goyyyy', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/2f2ee052-e287-4706-b865-c3ca22303f0f');
INSERT INTO `repcomment` VALUES (26, 12, 19, '2024-11-30 16:09:37', 'chúng tôi chưa có sản phẩm này rồi, rất xin lỗi bạn ', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (27, 13, 19, '2024-11-30 16:10:07', 'Dạ được ạ', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/1a87256c-23f8-4c54-9d47-1a14187fd14a');
INSERT INTO `repcomment` VALUES (29, 10, 19, '2024-12-04 13:21:56', 'có ạ', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/0954b561-b66a-4a26-901c-5536fadf54b8');
INSERT INTO `repcomment` VALUES (30, 10, 19, '2024-12-04 13:22:23', 'ád', 'ENABLE', NULL);
INSERT INTO `repcomment` VALUES (31, 9, 19, '2024-12-04 13:28:18', 'có ạ', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/5b9cf1f8-a990-4b6c-aed2-2255785539b6');
INSERT INTO `repcomment` VALUES (32, 9, 19, '2024-12-04 13:28:42', 'abc', 'ENABLE', NULL);

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review`  (
  `ReviewID` int NOT NULL AUTO_INCREMENT,
  `FlowerID` int NOT NULL,
  `AccountID` int NOT NULL,
  `rating` float NULL DEFAULT NULL,
  `comment` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `Date` datetime NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  `Image` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ReviewID`) USING BTREE,
  INDEX `FlowerID`(`FlowerID` ASC) USING BTREE,
  INDEX `AccountID`(`AccountID` ASC) USING BTREE,
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`FlowerID`) REFERENCES `flower` (`FlowerID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `review_chk_1` CHECK (`Rating` between 1 and 5)
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of review
-- ----------------------------
INSERT INTO `review` VALUES (1, 1, 7, 1, '1', '2024-11-26 19:53:21', 'ENABLE', NULL);
INSERT INTO `review` VALUES (3, 4, 7, 3, 'beautiful', '2024-11-27 03:15:28', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/337fd415-50f0-472f-81d8-ed29c8f14206');
INSERT INTO `review` VALUES (13, 25, 7, 2, 'aaaa', '2024-11-28 18:57:55', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/337fd415-50f0-472f-81d8-ed29c8f14206');
INSERT INTO `review` VALUES (14, 11, 18, 4, 'sản phẩm rất đẹp', '2024-11-30 09:00:07', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/d43a15b9-a25e-4a36-b329-c853fd0a0d67');
INSERT INTO `review` VALUES (16, 12, 7, 5, 'đẹp', '2024-12-01 14:16:30', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/337fd415-50f0-472f-81d8-ed29c8f14206');
INSERT INTO `review` VALUES (17, 11, 22, 5, 'Hoa rất đẹp, nhân viên nhiệt tình', '2024-12-04 12:31:37', 'ENABLE', NULL);
INSERT INTO `review` VALUES (18, 12, 22, 5, 'Mình nhận được hoa rồi, hoa đẹp lắm a', '2024-12-04 12:32:15', 'ENABLE', NULL);
INSERT INTO `review` VALUES (19, 13, 23, 4, 'hoa tươi', '2024-12-04 12:34:38', 'ENABLE', NULL);
INSERT INTO `review` VALUES (20, 12, 23, 5, 'hoa đẹp', '2024-12-04 12:34:43', 'ENABLE', NULL);
INSERT INTO `review` VALUES (21, 11, 23, 5, 'Đẹp lắm mn ơi ', '2024-12-04 05:34:51', 'ENABLE', 'https://storage.googleapis.com/webproject-65086.appspot.com/product/56dd8f0d-a2a9-4728-b440-4045ea856a12');
INSERT INTO `review` VALUES (22, 11, 24, 5, 'hoa đẹp , ship nhanh, nv nhiệt tình', '2024-12-04 12:37:00', 'ENABLE', NULL);
INSERT INTO `review` VALUES (23, 12, 24, 4, 'nhan vien de thuong, hoa rat dep', '2024-12-04 12:37:13', 'ENABLE', NULL);
INSERT INTO `review` VALUES (24, 11, 25, 3, 'cung oke, sp kha tot', '2024-12-04 12:40:09', 'ENABLE', NULL);
INSERT INTO `review` VALUES (25, 12, 25, 5, 'sp rat dep', '2024-12-04 12:40:18', 'ENABLE', NULL);
INSERT INTO `review` VALUES (26, 11, 26, 5, 'hoa đẹp quá', '2024-12-04 12:42:40', 'ENABLE', NULL);
INSERT INTO `review` VALUES (27, 12, 26, 5, 'hoa đẹp qua', '2024-12-04 12:42:49', 'ENABLE', NULL);

-- ----------------------------
-- Table structure for shipping
-- ----------------------------
DROP TABLE IF EXISTS `shipping`;
CREATE TABLE `shipping`  (
  `ShippingID` int NOT NULL AUTO_INCREMENT,
  `start_date` datetime NULL DEFAULT NULL,
  `complete_date` datetime NULL DEFAULT NULL,
  `ShipperID` int NOT NULL,
  `Note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`ShippingID`) USING BTREE,
  INDEX `shipperID`(`ShipperID` ASC) USING BTREE,
  CONSTRAINT `shipperID` FOREIGN KEY (`ShipperID`) REFERENCES `account` (`AccountID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shipping
-- ----------------------------
INSERT INTO `shipping` VALUES (1, '2024-11-26 19:40:43', '2024-11-28 19:40:45', 1, NULL, 'ENABLE');
INSERT INTO `shipping` VALUES (3, '2024-11-28 11:56:21', '2024-11-28 11:56:55', 7, NULL, 'ENABLE');
INSERT INTO `shipping` VALUES (4, '2024-11-28 11:25:25', '2024-11-28 11:42:19', 1, 'Cute ghê', 'ENABLE');
INSERT INTO `shipping` VALUES (5, '2024-11-28 15:14:34', '2024-11-28 15:14:36', 1, NULL, 'ENABLE');
INSERT INTO `shipping` VALUES (6, NULL, NULL, 20, 'Tôi sẽ giao', 'ENABLE');
INSERT INTO `shipping` VALUES (7, NULL, NULL, 20, 'giao', 'ENABLE');
INSERT INTO `shipping` VALUES (8, '2024-11-30 17:38:40', '2024-11-30 17:38:42', 7, 'test', 'ENABLE');
INSERT INTO `shipping` VALUES (9, '2024-12-02 12:50:11', NULL, 7, '', 'ENABLE');

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token`  (
  `id` int NOT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token_type` enum('BEARER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `accountid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_pddrhgwxnms2aceeku9s2ewy5`(`token` ASC) USING BTREE,
  INDEX `FKhyvliq6i3eq5vnrkcdf2ba6lu`(`accountid` ASC) USING BTREE,
  CONSTRAINT `FKhyvliq6i3eq5vnrkcdf2ba6lu` FOREIGN KEY (`accountid`) REFERENCES `account` (`AccountID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of token
-- ----------------------------
INSERT INTO `token` VALUES (152, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjYxMjA5NiwiZXhwIjoxNzMyNjk4NDk2fQ.MpNDxR1UJEfdfr-KW1UPbg5uKjqP5rFgRVvc4uRtYnQ', 'BEARER', 7);
INSERT INTO `token` VALUES (153, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjYxMjEyMSwiZXhwIjoxNzMyNjk4NTIxfQ.6BTb2QBAXhQcVKQtZ2PO70qxsbRh3m1Y9-c_URLKGIQ', 'BEARER', 7);
INSERT INTO `token` VALUES (202, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjYxMjM1NywiZXhwIjoxNzMyNjk4NzU3fQ.C8BIP2Lx5sw5Q1dEsH-sy5R-GKose-ab_Wd87Sidbgg', 'BEARER', 7);
INSERT INTO `token` VALUES (252, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjcwMDc2MSwiZXhwIjoxNzMyNzg3MTYxfQ.5nccyKw19Gsupnu0kCAnbslmsGqzmYoASS03OTwQJgs', 'BEARER', 7);
INSERT INTO `token` VALUES (302, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjcxMTAxNiwiZXhwIjoxNzMyNzk3NDE2fQ.4UfOoSzfpwnaxhqwf1g77PvekHBHYBiQqslD1L1zUUk', 'BEARER', 7);
INSERT INTO `token` VALUES (352, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjc4MjUwMCwiZXhwIjoxNzMyODY4OTAwfQ.8rRop_ZzGw5AILgCP3GpA0dt8l9zLZ5szOHrQRs87_c', 'BEARER', 7);
INSERT INTO `token` VALUES (402, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjc4NzMwNywiZXhwIjoxNzMyODczNzA3fQ.JLQ1uPFojXk85hm6W6JiQVa4QX5wqAevEsnyyasVPBY', 'BEARER', 7);
INSERT INTO `token` VALUES (403, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjc4ODA1MCwiZXhwIjoxNzMyODc0NDUwfQ.A8L0MqcFx9xcrI211o4Uy9mrbVPfKdys0kD10GTU-JM', 'BEARER', 7);
INSERT INTO `token` VALUES (404, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjc4ODA2OSwiZXhwIjoxNzMyODc0NDY5fQ.16dMmI6bhoNDxIWD03IqdkprraCmbUWjt44e_qWJDG8', 'BEARER', 7);
INSERT INTO `token` VALUES (452, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjgwMDYwMywiZXhwIjoxNzMyODg3MDAzfQ.kdd6dNDmqf7GIDk5IZryKtRItQMkCKSltizASiGdP14', 'BEARER', 7);
INSERT INTO `token` VALUES (502, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NTUzOTAsImV4cCI6MTczMzA0MTc5MH0.XE71XAjtar0959kBC01Ati2QMnap7PFsOqdiEFjxl4o', 'BEARER', 18);
INSERT INTO `token` VALUES (503, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NTUzOTksImV4cCI6MTczMzA0MTc5OX0._z8u6M5uQBbZbdnCOmofYyZDdbufXUrbW6DTMEuGDs8', 'BEARER', 18);
INSERT INTO `token` VALUES (504, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NTU0NjAsImV4cCI6MTczMzA0MTg2MH0.5117OR8qHfsZRaST_Ug7ev-aUOcDYigclrk1PIoTOI4', 'BEARER', 18);
INSERT INTO `token` VALUES (505, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NTU4NDgsImV4cCI6MTczMzA0MjI0OH0.uD7yc44oN-tpc6gTW9T3i9iOGEUzNN78fZrzySw5C5s', 'BEARER', 18);
INSERT INTO `token` VALUES (506, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3N0YWZmIiwiaWF0IjoxNzMyOTU3NzMwLCJleHAiOjE3MzMwNDQxMzB9.m-ij62OeRSbbbBrn0gr8JXSaeXuRHpmWfv-sfXXY374', 'BEARER', 19);
INSERT INTO `token` VALUES (507, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3N0YWZmIiwiaWF0IjoxNzMyOTU3NzM1LCJleHAiOjE3MzMwNDQxMzV9.zUJ5z7ltiX8SvbYbWs55xa7fCFjDtz3OHnIneAbfp1k', 'BEARER', 19);
INSERT INTO `token` VALUES (508, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3N0YWZmIiwiaWF0IjoxNzMyOTU4ODQyLCJleHAiOjE3MzMwNDUyNDJ9.aEAogx9NXxnoloWpIJtdIzzhsk9Q9CCIaAIT5iLDEZw', 'BEARER', 19);
INSERT INTO `token` VALUES (509, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3NoaXBwZXIiLCJpYXQiOjE3MzI5NTg4OTgsImV4cCI6MTczMzA0NTI5OH0.8fWzH7ygx8ZP2nO-Y4zxn11p7zX8zzrmMx0u20qG_4k', 'BEARER', 20);
INSERT INTO `token` VALUES (510, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3NoaXBwZXIiLCJpYXQiOjE3MzI5NTg5MDYsImV4cCI6MTczMzA0NTMwNn0.qlQrV7stCs82xnfDgftwPI7mVHui4FXp72cyf4ciAF0', 'BEARER', 20);
INSERT INTO `token` VALUES (511, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3NoaXBwZXIiLCJpYXQiOjE3MzI5NTkyMDksImV4cCI6MTczMzA0NTYwOX0.wDj6cdenmBe_GZg4Wc-No4Tl68DPxbuuWd73UL6-Dbg', 'BEARER', 20);
INSERT INTO `token` VALUES (512, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NTkyMjgsImV4cCI6MTczMzA0NTYyOH0.w8J8_oLwxFyfskjrqsJ0dM2nS-b9gq7TKU0N1BWdLT4', 'BEARER', 18);
INSERT INTO `token` VALUES (513, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NTkyMzYsImV4cCI6MTczMzA0NTYzNn0.vJEfvmCybxK-R38fcvs76IWvorPJBww3Vj5Hvaw3oHE', 'BEARER', 18);
INSERT INTO `token` VALUES (514, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDV1c2VyIiwiaWF0IjoxNzMyOTU5NTU0LCJleHAiOjE3MzMwNDU5NTR9.ysrT7-pWcdxAhhkVyLieT-VieU8ipngxG-51U7uRCwo', 'BEARER', 21);
INSERT INTO `token` VALUES (515, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDV1c2VyIiwiaWF0IjoxNzMyOTU5NTYxLCJleHAiOjE3MzMwNDU5NjF9.gxbYsWgiBadEKNhfBQbNA7zbWeNq3Wt6ZCV02tTgI64', 'BEARER', 21);
INSERT INTO `token` VALUES (516, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NTk2MTUsImV4cCI6MTczMzA0NjAxNX0.V76nksdlDy0XJZmMlNh6bYuI14juVdgbHn0MgowAdAE', 'BEARER', 18);
INSERT INTO `token` VALUES (517, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDV1c2VyIiwiaWF0IjoxNzMyOTU5NzgxLCJleHAiOjE3MzMwNDYxODF9.tFra3Qvs_K0oWc4z4UUUFAsLpstWtDM_Er7ruFqv-OQ', 'BEARER', 21);
INSERT INTO `token` VALUES (518, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NjAxNjIsImV4cCI6MTczMzA0NjU2Mn0.XhJMD47R6rEeS2jwMmiJGNj9hf9aAZJg3ZJLmvCrSGQ', 'BEARER', 18);
INSERT INTO `token` VALUES (519, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDV1c2VyIiwiaWF0IjoxNzMyOTYwNDYzLCJleHAiOjE3MzMwNDY4NjN9.6sTqgkmKdR9Q1bGwvvCESsta4CxWsanKUwJlL9XkjAU', 'BEARER', 21);
INSERT INTO `token` VALUES (520, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzI5NjA1NjQsImV4cCI6MTczMzA0Njk2NH0.jL4edMwwuRuXWyJ_I14z-vgWSaQyoO7mxpH8OiLphVI', 'BEARER', 18);
INSERT INTO `token` VALUES (552, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjk2MjUxMywiZXhwIjoxNzMzMDQ4OTEzfQ.ilkMw0gLbA6JGutBOxCllzFPdU1XEcm6HGTF7dEXs8c', 'BEARER', 7);
INSERT INTO `token` VALUES (602, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjk3MDk5NCwiZXhwIjoxNzMzMDU3Mzk0fQ.D0OnZ9L-Z9Ic49o7XlKesv3wBqD4J2oKozDMr3zKx0o', 'BEARER', 7);
INSERT INTO `token` VALUES (652, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMjk4MjU0NywiZXhwIjoxNzMzMDY4OTQ3fQ.EBbglE5Beh4o1UdMMENPF4cDTUfu4R7BjNxnd4hNbTs', 'BEARER', 7);
INSERT INTO `token` VALUES (702, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMzA1NTA0NCwiZXhwIjoxNzMzMTQxNDQ0fQ.kkFzXaM4NrFFARRlvhn2_tifWLshkCB8sDyiX09ivdE', 'BEARER', 7);
INSERT INTO `token` VALUES (703, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMzA2MDAxMCwiZXhwIjoxNzMzMTQ2NDEwfQ.9SIt8E3eMk5WPnL-M_1TSkmMX1Yjr9tHpPgnq_njqIk', 'BEARER', 7);
INSERT INTO `token` VALUES (752, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eHR0MTEwMyIsImlhdCI6MTczMzExODI0OCwiZXhwIjoxNzMzMjA0NjQ4fQ.rb0xwmY4p83jk8MQxw2B07EDdY6HgAvHJ8gJ1nZYD40', 'BEARER', 7);
INSERT INTO `token` VALUES (802, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyNDcyOTcsImV4cCI6MTczMzMzMzY5N30.bk4rMCjzljvrB0EMi5LY5zE2fK0k8Js-qy_Dr1d6Ze8', 'BEARER', 18);
INSERT INTO `token` VALUES (852, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyODk3MjEsImV4cCI6MTczMzM3NjEyMX0.hhs42a1pyhF6QisFbmJMKHQlR-a4ZxP8A6ECHggcWGE', 'BEARER', 18);
INSERT INTO `token` VALUES (853, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTAxMjYsImV4cCI6MTczMzM3NjUyNn0.TlEVOSh_5VS-_XjvwSLQoErwQL0maOWOlfizVQXxQ8o', 'BEARER', 18);
INSERT INTO `token` VALUES (854, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzEiLCJpYXQiOjE3MzMyOTAyMzAsImV4cCI6MTczMzM3NjYzMH0.qCF_yF8WA-HTcX6U9rEPS2co4V3nME-a9Dhf7VGX7sA', 'BEARER', 22);
INSERT INTO `token` VALUES (855, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzEiLCJpYXQiOjE3MzMyOTAyNDcsImV4cCI6MTczMzM3NjY0N30.qOEeNrwlUPFtxAB0F9Y-YcaxYhxxu-mDzX4UBuSKrhY', 'BEARER', 22);
INSERT INTO `token` VALUES (856, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzIiLCJpYXQiOjE3MzMyOTAzNzYsImV4cCI6MTczMzM3Njc3Nn0.FU1uMACIoergjpCaa1I0HLgpdS5v1GSFehjo9kQtz_I', 'BEARER', 23);
INSERT INTO `token` VALUES (857, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzIiLCJpYXQiOjE3MzMyOTAzODUsImV4cCI6MTczMzM3Njc4NX0.1YROMubQF0ewA6PE2K3jv02l3lYUoi1iU2yU29pQBn0', 'BEARER', 23);
INSERT INTO `token` VALUES (858, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzMiLCJpYXQiOjE3MzMyOTA1NDQsImV4cCI6MTczMzM3Njk0NH0.ZWiOUfPZVbku5iNeCE3rdEYE6W0723s4-ixTPMadzeA', 'BEARER', 24);
INSERT INTO `token` VALUES (859, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzMiLCJpYXQiOjE3MzMyOTA1NTIsImV4cCI6MTczMzM3Njk1Mn0.E2qVJk5Kkb32o4twyzz5tYTex6EkEPAUoa-cM3zTSTA', 'BEARER', 24);
INSERT INTO `token` VALUES (860, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzQiLCJpYXQiOjE3MzMyOTA2OTUsImV4cCI6MTczMzM3NzA5NX0.ymPK1gJnwotKbBc1_JeuT5PTMc-O8RWog88OKIabKJc', 'BEARER', 25);
INSERT INTO `token` VALUES (861, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzMiLCJpYXQiOjE3MzMyOTA3MTIsImV4cCI6MTczMzM3NzExMn0.LVUTN86h6MJFS5DCDobzYiwa9XvLa7pIcR-38z5fyK4', 'BEARER', 24);
INSERT INTO `token` VALUES (862, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzQiLCJpYXQiOjE3MzMyOTA3NDcsImV4cCI6MTczMzM3NzE0N30.PQf_el19wEtrb5pq1DNqccy9Azu0kl2CxeFNMwUPVlc', 'BEARER', 25);
INSERT INTO `token` VALUES (863, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzUiLCJpYXQiOjE3MzMyOTA4NjMsImV4cCI6MTczMzM3NzI2M30.6BpIciaRdUnTgjZgovv6b6SvKVNZo1L7o1ycmBt6I5U', 'BEARER', 26);
INSERT INTO `token` VALUES (864, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzEyMzUiLCJpYXQiOjE3MzMyOTA5MTgsImV4cCI6MTczMzM3NzMxOH0.8TwMVlKNkQ4K4m45WhYwDvquztHIw26_ESFunDpaOTI', 'BEARER', 26);
INSERT INTO `token` VALUES (865, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTEwMTUsImV4cCI6MTczMzM3NzQxNX0.8bLA2WimZG6RcDZuyba_mS-mCGiF8XkXR67kXJYIOgY', 'BEARER', 18);
INSERT INTO `token` VALUES (866, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTE0MDcsImV4cCI6MTczMzM3NzgwN30.bGnIYPk83OQCoEPcbijgCQAz5UqvxYuyrVdQvd61d78', 'BEARER', 18);
INSERT INTO `token` VALUES (867, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTE1MzYsImV4cCI6MTczMzM3NzkzNn0.VYNL1-l9qzgRqGo58_7ghVMgCRmYli3f4YFUoHOk3UE', 'BEARER', 18);
INSERT INTO `token` VALUES (868, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3N0YWZmIiwiaWF0IjoxNzMzMjkxNzU1LCJleHAiOjE3MzMzNzgxNTV9.tPCbVL7USMb_KKvC1hjqe9cZSDUBMWmwo4QLKq0L2Ak', 'BEARER', 19);
INSERT INTO `token` VALUES (869, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTE3ODcsImV4cCI6MTczMzM3ODE4N30.zLMPsI2_lQMWeysruo3i2aRho8VwHM73GjFT_Lhagh8', 'BEARER', 18);
INSERT INTO `token` VALUES (870, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTIzMjksImV4cCI6MTczMzM3ODcyOX0.ouq-iArF38YNwqHE6aY0g9dNxEFG02OpYhTPVYSFm3g', 'BEARER', 18);
INSERT INTO `token` VALUES (871, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3N0YWZmIiwiaWF0IjoxNzMzMjkyMzQwLCJleHAiOjE3MzMzNzg3NDB9.JjqCFdbIYSinW4j5cZgtYfQyJpyEYtkb-E9XCFspNlY', 'BEARER', 19);
INSERT INTO `token` VALUES (872, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTMxMjQsImV4cCI6MTczMzM3OTUyNH0.fVVdaRutIggz5-WxQ19Db6xDmsYe7sVHt1l2liC26f0', 'BEARER', 18);
INSERT INTO `token` VALUES (873, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRob3N0YWZmIiwiaWF0IjoxNzMzMjkzMTM3LCJleHAiOjE3MzMzNzk1Mzd9.vw_7kamyWeIMOAukyXHdXklpjORFh2rrqOk9PwPakHQ', 'BEARER', 19);
INSERT INTO `token` VALUES (874, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQxMzgsImV4cCI6MTczMzM4MDUzOH0.HJx36R52pz_c7W9etvkUxHQZ1uK08IvvdiUKeYYgQho', 'BEARER', 18);
INSERT INTO `token` VALUES (875, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQxNDEsImV4cCI6MTczMzM4MDU0MX0.FTTahRGZPaEhqMiS23R01bakpP0By4W1h8UsyGRU-ao', 'BEARER', 18);
INSERT INTO `token` VALUES (876, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQyNzUsImV4cCI6MTczMzM4MDY3NX0.VChO6kvaHuv5JFN04e4mOED6Gf401IxvptHIj2IybKI', 'BEARER', 18);
INSERT INTO `token` VALUES (877, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQzMzgsImV4cCI6MTczMzM4MDczOH0.yAgCNY0T0_GkqBv89w476v2I2WrpVZ_agy1dDGcquFM', 'BEARER', 18);
INSERT INTO `token` VALUES (878, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQzNjIsImV4cCI6MTczMzM4MDc2Mn0.BaHkLcn0rnGXY2Sa9lSuG7WLw-wvt29hwKhF-Mia7j8', 'BEARER', 18);
INSERT INTO `token` VALUES (879, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQ0NzksImV4cCI6MTczMzM4MDg3OX0.5JeqB8Eiusitjw8CsJ9Xme0rzrBmrpX1ay0iX2qY2Iw', 'BEARER', 18);
INSERT INTO `token` VALUES (880, b'1', b'1', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQ2MDIsImV4cCI6MTczMzM4MTAwMn0.xZM9X5WUPCeYfmXpTThwa5MLQNFSGqQmolAS2gElmCc', 'BEARER', 18);
INSERT INTO `token` VALUES (881, b'0', b'0', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGllbnRobzIyMDUiLCJpYXQiOjE3MzMyOTQ3NDAsImV4cCI6MTczMzM4MTE0MH0.6LePK7rj5rQ-og9T8hGzJwhj5liLi1j4BCT-fWvO4Ho', 'BEARER', 18);

-- ----------------------------
-- Table structure for token_seq
-- ----------------------------
DROP TABLE IF EXISTS `token_seq`;
CREATE TABLE `token_seq`  (
  `next_val` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of token_seq
-- ----------------------------
INSERT INTO `token_seq` VALUES (951);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `TypeID` int NOT NULL AUTO_INCREMENT,
  `min_consume` decimal(10, 3) NOT NULL,
  `type_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`TypeID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, 0.000, 'Khách mua lần đầu', 'ENABLE');
INSERT INTO `type` VALUES (2, 1.000, 'Khách ', 'ENABLE');
INSERT INTO `type` VALUES (3, 500000.000, 'Khách quen', 'ENABLE');
INSERT INTO `type` VALUES (4, 1000000.000, 'Khách hàng thân thiết', 'ENABLE');
INSERT INTO `type` VALUES (5, 2000000.000, 'Khách VIP', 'ENABLE');
INSERT INTO `type` VALUES (6, 5000000.000, 'Khách \"Siu\" VIP', 'ENABLE');
INSERT INTO `type` VALUES (7, 9999999.999, 'Khách \"Cổ Đông\"', 'ENABLE');

-- ----------------------------
-- Table structure for wishlist
-- ----------------------------
DROP TABLE IF EXISTS `wishlist`;
CREATE TABLE `wishlist`  (
  `wish_listid` int NOT NULL AUTO_INCREMENT,
  `AccountID` int NOT NULL,
  `FlowerID` int NOT NULL,
  `Status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ENABLE',
  PRIMARY KEY (`wish_listid`) USING BTREE,
  INDEX `AccountID`(`AccountID` ASC) USING BTREE,
  INDEX `FlowerID`(`FlowerID` ASC) USING BTREE,
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`FlowerID`) REFERENCES `flower` (`FlowerID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wishlist
-- ----------------------------
INSERT INTO `wishlist` VALUES (7, 7, 13, 'ENABLE');
INSERT INTO `wishlist` VALUES (10, 7, 15, 'ENABLE');
INSERT INTO `wishlist` VALUES (14, 18, 12, 'ENABLE');
INSERT INTO `wishlist` VALUES (15, 18, 19, 'ENABLE');
INSERT INTO `wishlist` VALUES (16, 18, 20, 'ENABLE');
INSERT INTO `wishlist` VALUES (17, 18, 11, 'ENABLE');

-- ----------------------------
-- Event structure for event_discount
-- ----------------------------
DROP EVENT IF EXISTS `event_discount`;
delimiter ;;
CREATE EVENT `event_discount`
ON SCHEDULE
EVERY '1' SECOND STARTS '2024-12-02 14:34:44'
DO BEGIN
    -- Cập nhật trạng thái DISABLE nếu thời gian hiện tại vượt quá end_date hoặc chưa tới start_date
    UPDATE discount
    SET Status = 'DISABLE'
    WHERE NOW() > end_date OR NOW() < start_date;

    -- Cập nhật trạng thái ENABLE nếu thời gian hiện tại nằm trong khoảng start_date và end_date
    UPDATE discount
    SET Status = 'ENABLE'
    WHERE NOW() BETWEEN start_date AND end_date;
END
;;
delimiter ;

-- ----------------------------
-- Event structure for update_discount_status
-- ----------------------------
DROP EVENT IF EXISTS `update_discount_status`;
delimiter ;;
CREATE EVENT `update_discount_status`
ON SCHEDULE
EVERY '1' MINUTE STARTS '2024-12-02 13:49:10'
DO BEGIN
    -- Cập nhật trạng thái DISABLE nếu thời gian vượt quá end_date
    UPDATE discount
    SET Status = 'DISABLE'
    WHERE CURDATE() > end_date;

    -- Cập nhật trạng thái ENABLE nếu thời gian nằm trong khoảng start_date và end_date
    UPDATE discount
    SET Status = 'ENABLE'
    WHERE CURDATE() BETWEEN start_date AND end_date;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table account
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_cart_on_account`;
delimiter ;;
CREATE TRIGGER `disable_cart_on_account` AFTER UPDATE ON `account` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE cart 
        SET status = 'DISABLE' 
        WHERE AccountID = NEW.AccountID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE cart 
        SET status = 'ENABLE' 
        WHERE AccountID = NEW.AccountID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table account
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_review_on_account`;
delimiter ;;
CREATE TRIGGER `disable_review_on_account` AFTER UPDATE ON `account` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE review 
        SET status = 'DISABLE' 
        WHERE AccountID = NEW.AccountID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE review 
        SET status = 'ENABLE' 
        WHERE AccountID = NEW.AccountID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table account
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_order_on_account`;
delimiter ;;
CREATE TRIGGER `disable_order_on_account` AFTER UPDATE ON `account` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE `order` 
        SET status = 'DISABLE' 
        WHERE AccountID = NEW.AccountID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE `order`  
        SET status = 'ENABLE' 
        WHERE AccountID = NEW.AccountID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table account
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_comment_on_account`;
delimiter ;;
CREATE TRIGGER `disable_comment_on_account` AFTER UPDATE ON `account` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE `comment` 
        SET status = 'DISABLE' 
        WHERE AccountID = NEW.AccountID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE `comment`  
        SET status = 'ENABLE' 
        WHERE AccountID = NEW.AccountID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table account
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_wishlist_on_account`;
delimiter ;;
CREATE TRIGGER `disable_wishlist_on_account` AFTER UPDATE ON `account` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE `wishlist` 
        SET status = 'DISABLE' 
        WHERE AccountID = NEW.AccountID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE `wishlist`  
        SET status = 'ENABLE' 
        WHERE AccountID = NEW.AccountID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table category
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_banner_on_category`;
delimiter ;;
CREATE TRIGGER `disable_banner_on_category` AFTER UPDATE ON `category` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE banner 
        SET status = 'DISABLE' 
        WHERE link_to_categoryid = NEW.CategoryID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE banner 
        SET status = 'ENABLE' 
        WHERE link_to_categoryid = NEW.CategoryID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table comment
-- ----------------------------
DROP TRIGGER IF EXISTS `disable image`;
delimiter ;;
CREATE TRIGGER `disable image` AFTER UPDATE ON `comment` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE repcomment 
        SET status = 'DISABLE' 
        WHERE CommentID = NEW.CommentID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE repcomment
        SET status = 'ENABLE' 
        WHERE CommentID = NEW.CommentID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table commenttype
-- ----------------------------
DROP TRIGGER IF EXISTS `disable comment`;
delimiter ;;
CREATE TRIGGER `disable comment` AFTER UPDATE ON `commenttype` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE `comment` 
        SET status = 'DISABLE' 
        WHERE CommenttypeID = NEW.CommenttypeID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE `comment` 
        SET status = 'ENABLE' 
        WHERE CommenttypeID = NEW.CommenttypeID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table flower
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_flowersize_on_flower`;
delimiter ;;
CREATE TRIGGER `disable_flowersize_on_flower` AFTER UPDATE ON `flower` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE flowersize 
        SET status = 'DISABLE' 
        WHERE FlowerID = NEW.FlowerID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE flowersize 
        SET status = 'ENABLE' 
        WHERE FlowerID = NEW.FlowerID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table flower
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_review_on_flower`;
delimiter ;;
CREATE TRIGGER `disable_review_on_flower` AFTER UPDATE ON `flower` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE review 
        SET status = 'DISABLE' 
        WHERE FlowerID = NEW.FlowerID;
    ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE review 
        SET status = 'ENABLE' 
        WHERE FlowerID = NEW.FlowerID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table flower
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_image_on_flower`;
delimiter ;;
CREATE TRIGGER `disable_image_on_flower` AFTER UPDATE ON `flower` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE flowerimages 
        SET status = 'DISABLE' 
        WHERE FlowerID = NEW.FlowerID;
    ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE flowerimages 
        SET status = 'ENABLE' 
        WHERE FlowerID = NEW.FlowerID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table flower
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_banner_on_flower`;
delimiter ;;
CREATE TRIGGER `disable_banner_on_flower` AFTER UPDATE ON `flower` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE banner 
        SET status = 'DISABLE' 
        WHERE link_to_flowerid = NEW.FlowerID;
    ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE banner 
        SET status = 'ENABLE' 
        WHERE link_to_flowerid = NEW.FlowerID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table order
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_orderdetail_on_order`;
delimiter ;;
CREATE TRIGGER `disable_orderdetail_on_order` AFTER UPDATE ON `order` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE orderdetail 
        SET status = 'DISABLE' 
        WHERE OrderID = NEW.OrderID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE orderdetail 
        SET status = 'ENABLE' 
        WHERE OrderID = NEW.OrderID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table purpose
-- ----------------------------
DROP TRIGGER IF EXISTS `disable_flower_on_purpose`;
delimiter ;;
CREATE TRIGGER `disable_flower_on_purpose` AFTER UPDATE ON `purpose` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE flower 
        SET status = 'DISABLE' 
        WHERE PurposeID = NEW.PurposeID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE flower 
        SET status = 'ENABLE' 
        WHERE PurposeID = NEW.PurposeID;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table purpose
-- ----------------------------
DROP TRIGGER IF EXISTS `disable banner`;
delimiter ;;
CREATE TRIGGER `disable banner` AFTER UPDATE ON `purpose` FOR EACH ROW BEGIN
    IF NEW.status = 'DISABLE' THEN
        UPDATE banner 
        SET status = 'DISABLE' 
        WHERE link_to_purposeid = NEW.PurposeID;
		 ELSEIF NEW.status = 'ENABLE' THEN
        UPDATE banner 
        SET status = 'ENABLE' 
        WHERE link_to_purposeid = NEW.PurposeID;
    END IF;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
