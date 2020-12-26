/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.73-community : Database - examonline
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`examonline` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `examonline`;

/*Table structure for table `classs` */

DROP TABLE IF EXISTS `classs`;

CREATE TABLE `classs` (
  `classs_id` varchar(50) NOT NULL,
  `classs_name` varchar(50) DEFAULT NULL,
  `classs_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`classs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `classs` */

insert  into `classs`(`classs_id`,`classs_name`,`classs_time`) values ('2b45baaf-7b84-49fc-9e25-15c5b4f84370','2018-07-02','2018年08月20日 12:51:10'),('7573fb53-0eda-46bd-b351-b1b7524fbaeb','2018-08-02','2018年08月16日 17:27:27'),('c1','2018-08-15','2018年08月14日 16:33:02'),('e049f5d5-79d4-458f-ba97-a5b9a6606893','2018-08-20','2018年08月20日 12:49:42');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` varchar(50) NOT NULL,
  `course_name` varchar(50) DEFAULT NULL,
  `course_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`course_id`,`course_name`,`course_time`) values ('c1','MySQL数据库技术','2018年08月14日 16:48:58'),('c2','HttpServlet动态网页','2018年08月14日 16:46:58'),('c3','JSP动态网页新技术','2018年08月14日 16:46:47');

/*Table structure for table `examinee` */

DROP TABLE IF EXISTS `examinee`;

CREATE TABLE `examinee` (
  `examinee_id` varchar(50) NOT NULL,
  `classs_id` varchar(50) DEFAULT NULL,
  `examinee_name` varchar(50) DEFAULT NULL,
  `examinee_pass` varchar(50) DEFAULT NULL,
  `examinee_sex` varchar(50) DEFAULT NULL,
  `examinee_question` varchar(50) DEFAULT NULL,
  `examinee_answer` varchar(50) DEFAULT NULL,
  `examinee_specialty` varchar(50) DEFAULT NULL,
  `examinee_identity` varchar(50) DEFAULT NULL,
  `examinee_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`examinee_id`),
  KEY `FK_Reference_1` (`classs_id`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`classs_id`) REFERENCES `classs` (`classs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `examinee` */

insert  into `examinee`(`examinee_id`,`classs_id`,`examinee_name`,`examinee_pass`,`examinee_sex`,`examinee_question`,`examinee_answer`,`examinee_specialty`,`examinee_identity`,`examinee_time`) values ('20120820001','2b45baaf-7b84-49fc-9e25-15c5b4f84370','徐喆','6624499','男','00','00','','230123199009133277','2018年08月20日 12:52:32'),('20120820002','2b45baaf-7b84-49fc-9e25-15c5b4f84370','陆阳','13945536021','男','诸葛亮','孔明','计算机','350313350','2018年08月20日 12:52:32'),('20120820003','2b45baaf-7b84-49fc-9e25-15c5b4f84370','魑魅','465541479','男','谁是小狗','JLL','计算机','230205199008171012','2018年08月20日 12:52:32'),('20120820004','2b45baaf-7b84-49fc-9e25-15c5b4f84370','王晶宇','wangjingyu','女','我的生日','0113','计算机','232128199101134524','2018年08月20日 12:52:32'),('20120820005','2b45baaf-7b84-49fc-9e25-15c5b4f84370','鲁海强','648459603','男','别问我的生日','我不告诉你','集成电路设计与集成系统','xxxxxxxxxxxxxxxxxxxxxxx','2018年08月20日 12:52:32'),('20120820006','2b45baaf-7b84-49fc-9e25-15c5b4f84370','王乾骄','123456','男','你猜','你猜错了','计算机','231083199008166917','2018年08月20日 12:52:32'),('20120820007','2b45baaf-7b84-49fc-9e25-15c5b4f84370','烽火','123456','男','1','1','1','1','2018年08月20日 12:52:32'),('20120820008','2b45baaf-7b84-49fc-9e25-15c5b4f84370','陈湦文','123456','男','111','111','111','111','2018年08月20日 12:52:32'),('20120820009','2b45baaf-7b84-49fc-9e25-15c5b4f84370','朱诗瑶','654321','男','我的生日','7月17日','计算机','231002199005311551','2018年08月20日 12:52:32'),('20120820010','2b45baaf-7b84-49fc-9e25-15c5b4f84370','孙秀秀','123456','女','我喜欢的动物','牛牛','计算机','140123456','2018年08月20日 12:52:32'),('20120820012','2b45baaf-7b84-49fc-9e25-15c5b4f84370','蒋天恒','396312372','男','qq','396312372','0.0','230102199111156115','2018年08月20日 12:52:32'),('20120820013','2b45baaf-7b84-49fc-9e25-15c5b4f84370','张金鑫','1234567890','男','我的生日？','1990-10-16','计算机','239005199010160053','2018年08月20日 12:52:32'),('20120820014','2b45baaf-7b84-49fc-9e25-15c5b4f84370','曹凯','54156320','男','我的生日？','7月17日','计算机软件','230182198902270210','2018年08月20日 12:52:32'),('20120820016','2b45baaf-7b84-49fc-9e25-15c5b4f84370','陈克杭','chen910607','男','曹操他爹是干啥的','地主','计算机','23118119910606081X','2018年08月20日 12:52:32'),('20120820017','2b45baaf-7b84-49fc-9e25-15c5b4f84370','盛美玲','000000','女','生日','5月2日','计算机','230230199005022122','2018年08月20日 12:52:32'),('20120820018','2b45baaf-7b84-49fc-9e25-15c5b4f84370','匡昊','111111','男','k?','k','计算机科学与技术','230106199012032548','2018年08月20日 12:52:32'),('20120820019','2b45baaf-7b84-49fc-9e25-15c5b4f84370','李艳庆','liyanqing','男','我的开学日期','2012-07-02','计算机','230555555555555555','2018年08月20日 12:52:32'),('20120820020','2b45baaf-7b84-49fc-9e25-15c5b4f84370','黄金伟','123456','女','0702','0702','','123456','2018年08月20日 12:52:32'),('20120820021','2b45baaf-7b84-49fc-9e25-15c5b4f84370','李秋燕','123456','女','abc','abc','计算机软件','123456789','2018年08月20日 12:52:32'),('20120820022','2b45baaf-7b84-49fc-9e25-15c5b4f84370','江天华','123456','男','我是谁？','江天华','计算机','320681199004086415','2018年08月20日 12:52:32'),('20120820023','2b45baaf-7b84-49fc-9e25-15c5b4f84370','于佳欢','111111','女','1','1','计算机','230224198903153923','2018年08月20日 12:52:32'),('20120820024','2b45baaf-7b84-49fc-9e25-15c5b4f84370','周鹏','zhoupeng','男','我的生日','090623','计算机','230822199006236977','2018年08月20日 12:52:32'),('20120820025','2b45baaf-7b84-49fc-9e25-15c5b4f84370','杨波','123456','男','0702','0702','计算机','','2018年08月20日 12:52:32'),('20120820026','2b45baaf-7b84-49fc-9e25-15c5b4f84370','纪青萍','123456','女','我的生日','7月7日','计算机科学与技术','232301199007077888','2018年08月20日 12:52:32'),('20120820027','2b45baaf-7b84-49fc-9e25-15c5b4f84370','王佳伟','123456','男','9%9/9=?','1','11','1111111111111','2018年08月20日 12:52:32'),('20120820028','2b45baaf-7b84-49fc-9e25-15c5b4f84370','张斯文','zswzsw','女','我的名字','张斯文','计算机','2123123234234234235234','2018年08月20日 12:52:32'),('20120820029','2b45baaf-7b84-49fc-9e25-15c5b4f84370','烽火','123456','男','1','1','1','1','2018年08月20日 12:52:32'),('20120820030','2b45baaf-7b84-49fc-9e25-15c5b4f84370','贾洪伟','0147852369','男','qq','413635283','计算机','','2018年08月20日 12:52:32'),('20120820031','2b45baaf-7b84-49fc-9e25-15c5b4f84370','陈湦文','123456','男','111','111','111','111','2018年08月20日 12:52:32'),('20120820032','2b45baaf-7b84-49fc-9e25-15c5b4f84370','段文博','spl123','男','生日','123123','','23123123123123','2018年08月20日 12:52:32'),('20120820033','2b45baaf-7b84-49fc-9e25-15c5b4f84370','马婧','123456','女','我的生日','5月24日','地理信息系统','23012219900524','2018年08月20日 12:52:32'),('20120820034','2b45baaf-7b84-49fc-9e25-15c5b4f84370','李文龙 ','maple123','男','？？','..','计算机科学与技术','','2018年08月20日 12:52:32'),('20120820035','2b45baaf-7b84-49fc-9e25-15c5b4f84370','张鹏','000000','男','生日','12月7日','计算机','000000000000000','2018年08月20日 12:52:32'),('20120820036','2b45baaf-7b84-49fc-9e25-15c5b4f84370','王健','76231301','男','我的生日','0101','计算机','','2018年08月20日 12:52:32');

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

CREATE TABLE `grade` (
  `grade_id` varchar(50) NOT NULL,
  `examinee_id` varchar(50) DEFAULT NULL,
  `course_id` varchar(50) DEFAULT NULL,
  `grade_minute_use` varchar(50) DEFAULT NULL,
  `grade_radio_mark` int(11) DEFAULT NULL,
  `grade_check_mark` int(11) DEFAULT NULL,
  `grade_sum` int(11) DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  KEY `FK_Reference_2` (`examinee_id`),
  KEY `FK_Reference_3` (`course_id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`examinee_id`) REFERENCES `examinee` (`examinee_id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `grade` */

insert  into `grade`(`grade_id`,`examinee_id`,`course_id`,`grade_minute_use`,`grade_radio_mark`,`grade_check_mark`,`grade_sum`) values ('07f47813-bdb3-4616-a10a-c57b7d8bb6f1','20120820025','c3','2012年08月20日',15,0,15),('1ce6871c-2ec6-47d3-b36f-fed7e163eeef','20120820023','c3','2012年08月20日',10,15,25),('1e7fd963-08a8-4e9e-b9a3-92c9c2c51463','20120820010','c3','2012年08月20日',15,15,30),('24c31ff8-76a9-4f16-bf89-981c811aee96','20120820027','c3','2012年08月20日',15,15,30),('42a6f72d-e9df-495e-9cce-b723c4dd58d4','20120820020','c3','2012年08月20日',25,15,40),('43375aa7-0df5-48b1-a6f8-2f7cd119e037','20120820006','c3','2012年08月20日',5,0,5),('53a760b9-486f-4ead-92cd-3a28a63bf8fe','20120820034','c3','2012年08月20日',5,5,10),('5526dae1-daaf-46df-b139-8d59c9e2abdf','20120820014','c3','2012年08月20日',15,15,30),('67631706-30b2-4f27-b69b-b1305bce5646','20120820003','c3','2012年08月20日',15,10,25),('6f4ce442-9b2e-4739-a9fc-926a9a608a3e','20120820019','c3','2012年08月20日',15,15,30),('831ef0ef-528f-443e-a1cb-c680a23ee1de','20120820033','c3','2012年08月20日',20,15,35),('8471265c-3eff-4200-a644-67e351523b2f','20120820031','c3','2012年08月20日',5,15,20),('891ef56b-3674-4fa5-aa23-cc63036038cc','20120820024','c3','2012年08月20日',15,15,30),('8eb71123-41f4-4260-87c5-5f995c8b0a8b','20120820018','c3','2012年08月20日',15,15,30),('9100db5a-367d-465a-a62f-906c1ef31f34','20120820030','c3','2012年08月20日',15,15,30),('976e47b4-3353-4167-97b3-df7d1d960a35','20120820001','c3','2012年08月20日',5,5,10),('9af1da59-d7ed-4e3e-a1db-4f3373fe4995','20120820004','c3','2012年08月20日',20,10,30),('9e29c052-0e2b-4e58-8e42-ffa9bc12d3fa','20120820016','c3','2012年08月20日',25,15,40),('9f7dbbb7-4f94-4451-b851-c5935ecb159d','20120820009','c3','2012年08月20日',0,0,0),('a2ef700d-0e9d-488b-b3f6-9621796c438e','20120820012','c3','2012年08月20日',20,25,45),('aa2ec112-f3ab-45fd-b3e6-622daf8a5246','20120820007','c3','2012年08月20日',30,15,45),('abe70ded-33e0-4af5-8d7d-4a4eaf9ebdc7','20120820032','c3','2012年08月20日',20,15,35),('bd5ab492-5904-43aa-8b83-578c0dd694a9','20120820028','c3','2012年08月20日',10,5,15),('c2b3b4ab-75f9-4c71-9561-f516d17e4ee8','20120820036','c3','2012年08月20日',10,10,20),('c33fed16-8995-45ac-aba5-bba11eb16c53','20120820017','c3','2012年08月20日',5,0,5),('cb539211-36a1-4dc6-996d-6c8225752a96','20120820021','c3','2012年08月20日',10,15,25),('cbded4cf-c04a-4f79-879f-e0b77674ec00','20120820005','c3','2012年08月20日',10,15,25),('dabcba0d-6103-49c4-9db3-4e5849d0ee44','20120820002','c3','2012年08月20日',20,5,25),('f0ead41a-7999-4410-9ea2-d9c72e33e9ba','20120820026','c3','2012年08月20日',20,15,35),('f2ca7a51-0dc2-4a5c-b5ea-e4322f9b2175','20120820022','c3','2012年08月20日',15,5,20),('f98a195a-e53e-4719-832d-caf406682b6f','20120820013','c3','2012年08月20日',10,15,25);

/*Table structure for table `manager` */

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `manager_id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_name` varchar(50) DEFAULT NULL,
  `manager_pass` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`manager_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `manager` */

insert  into `manager`(`manager_id`,`manager_name`,`manager_pass`) values (1,'11','11'),(2,'22','22'),(3,'33','33');

/*Table structure for table `subject` */

DROP TABLE IF EXISTS `subject`;

CREATE TABLE `subject` (
  `subject_id` varchar(50) NOT NULL,
  `course_id` varchar(50) DEFAULT NULL,
  `subject_name` text,
  `subject_type` varchar(50) DEFAULT NULL,
  `subject_A` text,
  `subject_B` text,
  `subject_C` text,
  `subject_D` text,
  `subject_answer` varchar(50) DEFAULT NULL,
  `subject_remark` text,
  PRIMARY KEY (`subject_id`),
  KEY `FK_Reference_4` (`course_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `subject` */

insert  into `subject`(`subject_id`,`course_id`,`subject_name`,`subject_type`,`subject_A`,`subject_B`,`subject_C`,`subject_D`,`subject_answer`,`subject_remark`) values ('02efdde5-222c-409f-a7e1-b6a0a3146e1c','c3','超数','单选题','a+b=c','a+b=d','c+d=e','d+c=b','B','超数理念'),('0369e4f5-c5a2-4350-a906-10a2e732b319','c3','拉格朗日中值定理','多选题','fx =c','fx = q','fx= w','fx = q','A,B','你弄明白了吗？'),('07e496f5-7716-4dae-b8ab-086c68db541d','c3','Q宠大乐斗中，师徒乐斗多增加多少经验？','单选题','30%','40%','50%','60%','C','不玩你就不会'),('0f4c9824-5f0f-44c4-b460-861a69c54bf6','c3','你知道“孩子”这个词是什么时候','单选题','1岁','2岁','3岁','娘胎里','D','因为你妈妈会经常和你说'),('15110e5b-e6de-4eb0-ad15-5e2524ae5a7d','c3','1+1=?','单选题','1','2','3','4','B',''),('1c98b0ee-5224-4ba7-bf3a-fa2468a089df','c3',' 你说1+1=？','多选题','4','6','9','7','A,B,C,D','其实没正确答案'),('25ee1d66-0055-4364-9dd1-07549904fbcb','c3','1','多选题','1','1','11','1','A',''),('267577ef-c872-4d67-b5a4-5651c519969a','c3','高数','多选题','回家的福克斯','过放电','功夫','加油','B,C',''),('2c4a7832-dada-4e88-a887-6ad38aeae059','c3','1','单选题','e1','1','1','1','A',''),('2c4f71c6-747d-487a-a420-32f4037603af','c3','你傻吗？','多选题','不傻','不太傻','有点傻','超级傻','A,B,C,D',''),('2fb4f1a5-7c15-4233-98cc-4f1f6f914e29','c3','张鹏飞内裤是什么颜色的？','多选题','根本就没穿','原先是白色N年后变黄色','花裤衩','偷绳润泽的','A,B,C,D',''),('333af7fe-b87f-4f76-893d-544ef1b6f2da','c3','曾小贤选的B还是D','单选题','1941年','1942年','1943年','1944年','B','哈哈'),('3432bbb7-9878-4cf3-b83e-54137386b488','c3','今天下午放假吗？？','多选题','放假','放假','放假','放假','A,B,C,D',''),('35a805b5-6737-467b-8d36-61a746ea343d','c3','问人有多少根头发','单选题','1','2','3','不知道','C',''),('37632da1-3df4-4751-8f38-4e79869ba778','c1','333','单选题','44','44','44','44','B','44'),('3dcbc0df-a131-43c8-b005-6d597be4ac3a','c3','5+6=？','单选题','5','6','10','11','D','-'),('41e04841-ca7f-41c6-9adb-afbbda3365c2','c3','1+1+?','多选题','1','2','3','10','B,D',''),('58ceaeea-0550-4d89-b1b7-1bf1e8c98bde','c3','八边形内角和多少度','单选题','360度','720度','1440度','1000度','C',''),('58f8942a-71f6-4db9-a3e6-90e28d10e3f2','c3','3','多选题','4','4','44444','4','A',''),('611ecc14-5719-4c26-b71b-a2e0b454acaf','c1','测试','单选题','1','2','3','4','A','5'),('68a15298-8bae-4fa2-8926-13c8577b59c9','c3','7','多选题','7','7','7','7','A',''),('6b33845c-28a9-4c00-b03b-ae0de814b649','c3','2+2=？','多选题','4','4','2','2','A,B',''),('6ee8414b-0082-44da-b1ec-f73bf155a0c5','c3','祖玛','多选题','你都玩过什么祖玛游戏','蟾蜍','祖玛大之窗','的  的','A,B,D','哦哦 '),('738a3e07-0212-4842-a8a5-aa6b6d0369df','c3','5','多选题','5','55','5','5','A,C,D','5555'),('82c670c6-b62d-4b5c-894d-1edbbcea0caf','c3','线性代数','单选题','矩阵式什么？','行列式？','对角矩阵有什么特点','逆矩阵？','B','下写的额'),('834f4e99-8211-4e3a-94b0-f2090c835d56','c2','咱班有几个美女','单选题','2','3','4','0','D','不带现数的'),('8542dfdd-9b60-438d-9166-2d5423326798','c3','问人的智商是多少','多选题','1','2','3','200','A,B,C,D',''),('877bc4f8-7af3-43b7-8906-cd14964c7bd4','c3','这样提问，才会有高手回帖','多选题','简单易懂','长篇大论','精辟屁精','大白话','A,C','题蛋疼。答案更蛋疼'),('8a67c48b-730a-44aa-a9d0-25502115e010','c3','刘备,关羽,张飞 谁是老嘎子?','单选题','刘备','张飞','关羽','曹艹','B',''),('8e83526d-dfae-4ffd-b379-19263958f428','c3','1234455','单选题','ds','dsgdd','sdf','dg','D','dg'),('8e92a3f7-5e35-4d3b-a2d8-2468cf23064b','c2','大个和大绳谁能干过谁','单选题','大个','大绳','才',' 的','A','啊'),('8f2cdedd-fcc7-492a-a862-dea23ba1af17','c3','9','单选题','9','9','9','9','A',''),('90b42b5e-0a2d-489a-98a7-67a4cf2f4110','c3','4','单选题','3','43333','3','3','B',''),('967c805c-7ba7-4ce4-9798-6d0c77dd46bd','c3','10','单选题','1','1','1','1','C',''),('97c5e431-f525-41e0-b56e-eb74d6828a2e','c3','1+1等于几','单选题','2','3','1','4','B','蛋疼的数学题'),('9babd726-7f2f-4fdb-af35-8002a7938042','c3','你知道什么啊？','多选题','就知道吃','就知道玩','就知道睡','给你懒得','A,B,C,D',''),('9c4699ee-de12-42ec-b739-967c4f4af5ad','c3','积分原理','多选题','543','agdfmsk','76hgf','vcxf','B,C,D','加油'),('a5a0d81f-b61c-4e78-a6fa-bf94b36fd5e7','c3','66-44=?','单选题','11','22','33','44','B','e '),('aa026f66-65b7-402f-9c36-440eba20f9fb','c3','√3=？','单选题','1','1.73','3','4','B',''),('adc92d99-2f6e-4a0a-ba85-f4a18d4e7e5b','c3','微分原理','单选题','a%b','a*b','a+b','a-b','A','加油'),('b3d606e7-2340-4013-a886-c2dbbf3370fa','c3','谁考了第一名','单选题','小张','小王','小李','小赵','B',''),('b4232bc6-f681-407d-9e35-f3538e8acf7b','c3','曹操他媳妇是谁？','多选题','关羽','刘备','张飞','吕布','A,B,C,D',''),('b56aed89-f771-4c9e-8ac3-0a9990905a79','c3','1+1在什么情况下不等于2','单选题','在错误的情况下不等于二','在任何情况下都不等于二','在错误的情况下等于二','以上三个都对','A','2'),('b9f49101-437b-4840-8828-7ffb9f112658','c3','我是谁？','多选题','我是你','我是我','我不是我','我不是你','A,B',''),('beb3495c-3adb-42cb-810f-78aca37d00ad','c3','大个是谁','多选题','大个是大个','傻','呆','二','A,B,C,D',''),('bebf84de-01e4-43c0-acba-45b322f5b510','c3','日本是中国的吗？','多选题','一直都是；','肯定是；','必须是','谁说不是','A,B,C,D','备注'),('c26acd2b-f291-4bea-be26-f28782207288','c3','你再猜？！','多选题','呃！','还来？','这。。。。','坑跌呀！！','A,C,D',''),('c68dd017-58d7-4d13-a5bc-e9787c00b126','c3','BIG','多选题','大肚皮','大嘴巴','小眼睛','大鼻子','A,C','猜猜我是谁'),('c72a24cd-ba43-4022-a7b6-603dc4835929','c3','lol首胜加多少经验？','单选题','10','100','150','200','D',''),('c80a5b73-bfee-4cb5-9844-39ded28fd380','c3','1+1=?','单选题','1','2','3','4','B',''),('cff9bd5b-5ffa-4f10-adae-a841f22019c3','c3','假如你的老婆和你娘掉进海里你现救谁呢？','单选题','救你老婆','救你娘','谁都不救','救她妹！！！','D','刘宇鹏 。 Lin。ml'),('d2230ab6-68a4-4e20-b8a6-07efe40b7c4f','c3','三角形有几条边','单选题','1','2','3','4','C','三角形有三条边'),('d70ffdee-a053-4d91-98ad-9e201c6a6d79','c3','DOTA中最好玩的英雄是是吗？','单选题','宙斯','影魔','圣堂刺客','卡尔','A','不玩你就不知道'),('d739d90e-773d-4046-aa3f-5db8b9096ad0','c3','一个男人一生可以娶多少个媳妇？？','多选题','1个','2个','5个','n个','A,C,D',''),('d94894ef-7428-4a3c-9dae-f28aa9c5e3b4','c3','8','多选题','8','8','8','8','A',''),('da0e38f7-dcb8-4bbc-8041-bde017970617','c3','你是男是女？？？？','单选题','男','女','不男不女','我不知道','D','嘿嘿'),('dcdba4ae-8414-4e7d-9969-781a7d44f517','c2','今天中午吃什么','多选题','馒头','米饭','面条','大个','A,B,C,D','A'),('de7bde17-b38c-46b6-a0b1-c76048ea5001','c3','2','多选题','2','2','22','2','A',''),('df594850-bb2b-4c5e-be94-0fd770ed6e4f','c2','十万个为什么','单选题','发个','嘟嘟','妹妹','咔咔','C','听挑剔'),('e5eca00b-3a29-4529-8562-a88335eb18be','c3','爱笑会议室都有谁？','多选题','张一鸣','乔杉','张子栋','古筝','A,B,C,D','猜猜吧？'),('ea082101-fbe7-4c3c-bda0-8bab9fe826e6','c3','火影忍者更新到多少集 我也不知道','单选题','567','546','345','555','A','嘟嘟'),('f16ac107-9848-4e3d-80f0-86f64a01136a','c3','三角形相似条件','多选题','角边角','边角边','边边边','交角边','A,B,C',''),('f362ff43-7934-45ca-8fb3-edd48b3bb1ef','c3','图灵今天做了多少道CDU','多选题','NNNNNNNNN','N+1','N+2','N>>*&^%$#@!','A,B,D','');

/*Table structure for table `testpaper` */

DROP TABLE IF EXISTS `testpaper`;

CREATE TABLE `testpaper` (
  `testpaper_id` varchar(50) NOT NULL,
  `classs_id` varchar(50) DEFAULT NULL,
  `course_id` varchar(50) DEFAULT NULL,
  `testpaper_name` varchar(50) DEFAULT NULL,
  `testpaper_radio_num` int(11) DEFAULT NULL,
  `testpaper_check_num` int(11) DEFAULT NULL,
  `testpaper_time_use` varchar(50) DEFAULT NULL,
  `testpaper_time_begin` varchar(50) DEFAULT NULL,
  `testpaper_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`testpaper_id`),
  KEY `FK_Reference_5` (`classs_id`),
  KEY `FK_Reference_6` (`course_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`classs_id`) REFERENCES `classs` (`classs_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `testpaper` */

insert  into `testpaper`(`testpaper_id`,`classs_id`,`course_id`,`testpaper_name`,`testpaper_radio_num`,`testpaper_check_num`,`testpaper_time_use`,`testpaper_time_begin`,`testpaper_state`) values ('54aad6b0-53f7-4524-ae17-d2e271d19318','2b45baaf-7b84-49fc-9e25-15c5b4f84370','c3','测试一',10,10,'5','2012年08月20日 13:04:06',4),('6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','c1','c3','111',10,10,'20','2012年08月18日 15:21:20',4),('ff31412d-0867-4c45-b28c-f4a8941f0cc8','c1','c1','22',10,10,'20','2012年08月18日 15:22:25',4);

/*Table structure for table `testpaper_list` */

DROP TABLE IF EXISTS `testpaper_list`;

CREATE TABLE `testpaper_list` (
  `testpaper_list_id` varchar(50) NOT NULL,
  `testpaper_id` varchar(50) DEFAULT NULL,
  `subject_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`testpaper_list_id`),
  KEY `FK_Reference_7` (`testpaper_id`),
  KEY `FK_Reference_8` (`subject_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`testpaper_id`) REFERENCES `testpaper` (`testpaper_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `testpaper_list` */

insert  into `testpaper_list`(`testpaper_list_id`,`testpaper_id`,`subject_id`) values ('063a6667-6ab7-439f-a55c-4d14556f2e95','ff31412d-0867-4c45-b28c-f4a8941f0cc8','611ecc14-5719-4c26-b71b-a2e0b454acaf'),('08feaf6c-c5f8-4f56-b0f6-77ebd5a25172','54aad6b0-53f7-4524-ae17-d2e271d19318','58f8942a-71f6-4db9-a3e6-90e28d10e3f2'),('192db011-9d7c-4688-91ee-cf601fc0b8da','54aad6b0-53f7-4524-ae17-d2e271d19318','97c5e431-f525-41e0-b56e-eb74d6828a2e'),('19469d4e-0d1d-49c8-b8a1-ffbe2a7d863c','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','c26acd2b-f291-4bea-be26-f28782207288'),('19f1e749-2043-44c1-a724-141588cba84d','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','267577ef-c872-4d67-b5a4-5651c519969a'),('1ad672bd-eea7-4019-ae02-36eb3309140c','54aad6b0-53f7-4524-ae17-d2e271d19318','b4232bc6-f681-407d-9e35-f3538e8acf7b'),('1b84e919-cb0a-4054-ad64-9b14fec3179a','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','de7bde17-b38c-46b6-a0b1-c76048ea5001'),('1cfc49fa-3c4f-4283-bca9-2c667a06cbff','54aad6b0-53f7-4524-ae17-d2e271d19318','8f2cdedd-fcc7-492a-a862-dea23ba1af17'),('23b4cd9d-6361-41e8-b18b-4ccbec0eb374','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','3dcbc0df-a131-43c8-b005-6d597be4ac3a'),('37a56c63-68c2-4a53-874b-9f7c3421fd0a','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','90b42b5e-0a2d-489a-98a7-67a4cf2f4110'),('3e1319ae-d574-4cf0-adfb-9c60f50a0579','ff31412d-0867-4c45-b28c-f4a8941f0cc8','37632da1-3df4-4751-8f38-4e79869ba778'),('4144461e-fd5e-43ab-abd8-35924effcdcb','54aad6b0-53f7-4524-ae17-d2e271d19318','c68dd017-58d7-4d13-a5bc-e9787c00b126'),('42fa9025-4961-4ac3-bcf3-eecec29d6198','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','6b33845c-28a9-4c00-b03b-ae0de814b649'),('438a202e-f724-4389-a8fa-d421e1e98c54','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','2c4a7832-dada-4e88-a887-6ad38aeae059'),('47d64ef2-9ec1-4680-b111-b8b6a741ab4b','54aad6b0-53f7-4524-ae17-d2e271d19318','d70ffdee-a053-4d91-98ad-9e201c6a6d79'),('4a30baab-dc64-4990-8413-7667e3105dfb','54aad6b0-53f7-4524-ae17-d2e271d19318','c26acd2b-f291-4bea-be26-f28782207288'),('55fe5723-3286-4ba3-aebe-b14659380fc1','54aad6b0-53f7-4524-ae17-d2e271d19318','877bc4f8-7af3-43b7-8906-cd14964c7bd4'),('68ca41cb-22dc-42e7-9f1b-1b0cc69d98b8','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','cff9bd5b-5ffa-4f10-adae-a841f22019c3'),('6c0246d8-6b4e-47a6-a9dd-768d9c00b202','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','c68dd017-58d7-4d13-a5bc-e9787c00b126'),('6d816280-408e-469e-b0fe-af894550607c','54aad6b0-53f7-4524-ae17-d2e271d19318','02efdde5-222c-409f-a7e1-b6a0a3146e1c'),('7748d5e3-43e8-4966-a4be-263b17d1c4af','54aad6b0-53f7-4524-ae17-d2e271d19318','9c4699ee-de12-42ec-b739-967c4f4af5ad'),('7b8113a9-b8c3-493d-ac2f-94b85a599acd','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','beb3495c-3adb-42cb-810f-78aca37d00ad'),('7ba28bcb-f491-47bf-a29d-7a1de6d61a17','54aad6b0-53f7-4524-ae17-d2e271d19318','9babd726-7f2f-4fdb-af35-8002a7938042'),('84befb88-6e25-45af-836e-035bf3aa4a10','54aad6b0-53f7-4524-ae17-d2e271d19318','07e496f5-7716-4dae-b8ab-086c68db541d'),('854886ab-bced-4692-b6df-4552ca142bb9','54aad6b0-53f7-4524-ae17-d2e271d19318','cff9bd5b-5ffa-4f10-adae-a841f22019c3'),('87ad1fbf-b6d4-4875-94b1-2434445527c0','54aad6b0-53f7-4524-ae17-d2e271d19318','35a805b5-6737-467b-8d36-61a746ea343d'),('89929c0b-2d41-441e-aaa9-65efbebb0a9b','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','f16ac107-9848-4e3d-80f0-86f64a01136a'),('8ca7677b-db65-44c8-bb35-5d5791287a00','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','15110e5b-e6de-4eb0-ad15-5e2524ae5a7d'),('947d07d3-3560-45fd-9880-e9d779b91931','54aad6b0-53f7-4524-ae17-d2e271d19318','c72a24cd-ba43-4022-a7b6-603dc4835929'),('9845e4f9-de18-4294-92c8-246c07c1f103','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','8a67c48b-730a-44aa-a9d0-25502115e010'),('aa7f59e1-3847-4539-ae82-149419b261cd','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','d94894ef-7428-4a3c-9dae-f28aa9c5e3b4'),('ab662e0c-a405-4000-be23-c0f291378a6b','54aad6b0-53f7-4524-ae17-d2e271d19318','b56aed89-f771-4c9e-8ac3-0a9990905a79'),('b598a3db-8164-40d4-87b0-4f58b9871dd3','54aad6b0-53f7-4524-ae17-d2e271d19318','e5eca00b-3a29-4529-8562-a88335eb18be'),('b5e1e8b1-4930-4de9-9ffc-6e2406ead4fc','54aad6b0-53f7-4524-ae17-d2e271d19318','de7bde17-b38c-46b6-a0b1-c76048ea5001'),('bf7b7c01-62e9-4561-8b7f-9367854c4f08','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','8f2cdedd-fcc7-492a-a862-dea23ba1af17'),('c85c32ce-5010-45e3-b49b-789f8369dc35','54aad6b0-53f7-4524-ae17-d2e271d19318','8e83526d-dfae-4ffd-b379-19263958f428'),('d3d24440-11e2-44bd-9549-73b2c95488fe','54aad6b0-53f7-4524-ae17-d2e271d19318','25ee1d66-0055-4364-9dd1-07549904fbcb'),('dcb54136-e96e-4ae6-a88d-fdce69b6c580','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','d70ffdee-a053-4d91-98ad-9e201c6a6d79'),('e9354eff-ed95-4f26-9749-18d4601c6d58','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','b9f49101-437b-4840-8828-7ffb9f112658'),('ecc5dade-a042-4d4e-98fa-9a61bccdeaac','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','97c5e431-f525-41e0-b56e-eb74d6828a2e'),('f1feda3e-89b5-40c4-81d4-c8f79640c699','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','58f8942a-71f6-4db9-a3e6-90e28d10e3f2'),('f6b9a88f-fa19-4fc8-86ad-8ea6b54268b4','6c3df97d-ac8a-4ce6-88fa-44dfe25c290d','c80a5b73-bfee-4cb5-9844-39ded28fd380');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
