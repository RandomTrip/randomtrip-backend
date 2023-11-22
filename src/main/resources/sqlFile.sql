
use enjoytrip;


-- 초기 데이터

CREATE TABLE `members` (
  `user_id` varchar(16) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `user_password` varchar(16) NOT NULL,
  `email_id` varchar(20) DEFAULT NULL,
  `email_domain` varchar(45) DEFAULT NULL,
  `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `members` (`user_id`,`user_name`,`user_password`,`email_id`,`email_domain`,`join_date`) VALUES ('admin','관리자','1234','admin','google.com','2023-09-15 09:15:29');
INSERT INTO `members` (`user_id`,`user_name`,`user_password`,`email_id`,`email_domain`,`join_date`) VALUES ('ssafy','김싸피','1234','ssafy','ssafy.com','2023-09-15 09:15:29');


CREATE TABLE `board` (
  `article_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `hit` int DEFAULT '0',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  KEY `board_to_members_user_id_fk` (`user_id`),
  CONSTRAINT `board_to_members_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `members` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (1,'ssafy','즐거운 싸생!!!','즐거운 싸생!!!',5,'2023-09-19 09:47:30');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (2,'ssafy','111','111',2,'2023-09-19 09:50:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (3,'ssafy','1','2',1,'2023-09-19 09:50:54');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (4,'ssafy','j','j',0,'2023-09-19 10:33:50');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (5,'ssafy','111','h',0,'2023-09-19 10:36:38');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (6,NULL,NULL,NULL,0,'2023-09-19 10:44:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (7,'ssafy',';;',';;',0,'2023-09-19 10:44:51');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (8,'ssafy','즐거운 싸생!!!','즐거운 싸생!!!',5,'2023-09-19 09:47:30');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (9,'ssafy','111','111',2,'2023-09-19 09:50:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (10,'ssafy','1','2',1,'2023-09-19 09:50:54');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (11,'ssafy','j','j',0,'2023-09-19 10:33:50');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (12,'ssafy','111','h',0,'2023-09-19 10:36:38');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (13,NULL,NULL,NULL,0,'2023-09-19 10:44:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (14,'ssafy',';;',';;',0,'2023-09-19 10:44:51');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (15,'ssafy','즐거운 싸생!!!','즐거운 싸생!!!',5,'2023-09-19 09:47:30');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (16,'ssafy','111','111',2,'2023-09-19 09:50:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (17,'ssafy','1','2',1,'2023-09-19 09:50:54');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (18,'ssafy','j','j',0,'2023-09-19 10:33:50');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (19,'ssafy','111','h',0,'2023-09-19 10:36:38');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (20,NULL,NULL,NULL,0,'2023-09-19 10:44:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (21,'ssafy',';;',';;',0,'2023-09-19 10:44:51');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (22,'ssafy','즐거운 싸생!!!','즐거운 싸생!!!',5,'2023-09-19 09:47:30');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (23,'ssafy','111','111',2,'2023-09-19 09:50:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (24,'ssafy','1','2',1,'2023-09-19 09:50:54');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (25,'ssafy','j','j',0,'2023-09-19 10:33:50');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (26,'ssafy','111','h',0,'2023-09-19 10:36:38');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (27,NULL,NULL,NULL,0,'2023-09-19 10:44:29');
INSERT INTO `board` (`article_no`,`user_id`,`subject`,`content`,`hit`,`register_time`) VALUES (28,'ssafy',';;',';;',0,'2023-09-19 10:44:51');












-- board 테이블 구조 변경
ALTER TABLE `enjoytrip`.`board`
ADD COLUMN `attraction_list` VARCHAR(45) NULL AFTER `register_time`,
ADD COLUMN `is_public` INT NOT NULL DEFAULT 0 AFTER `attraction_list`;

ALTER TABLE `enjoytrip`.`board`
ADD COLUMN `category` INT NOT NULL DEFAULT 0 AFTER `is_public`;



-- 댓글 테이블과 예제 데이터
-- 유저 닉네임은  member 테이블과의 join을 통해서 가져옵니다.

CREATE TABLE comments (
    comment_no INT AUTO_INCREMENT PRIMARY KEY,
    article_no INT,
    user_id VARCHAR(50),
    content TEXT,
    register_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_no)
        REFERENCES BOARD(article_no)
        ON DELETE CASCADE
);

INSERT INTO Comments (article_no, user_id, content)
VALUES
    (32, 'fwccjs', '32번 게시물에 대한 첫 번째 댓글입니다.'),
    (32, 'fwccjs', '32번 게시물에 대한 두 번째 댓글입니다.'),
    (32, 'ssafy', '32번 게시물에 대한 세 번째 댓글입니다.'),
    (32, 'fwccjs', '32번 게시물에 대한 네 번째 댓글입니다.'),
    (32, 'ssafy', '32번 게시물에 대한 다섯 번째 댓글입니다.');



-- 좋아요 수와 댓글 수를 저장하는 컬럼을 추가

ALTER TABLE board
ADD COLUMN comments_count INT DEFAULT 0,
ADD COLUMN like_count INT DEFAULT 0;



-- 여행 일차별 데이터 저장하는 테이블 생성

CREATE TABLE trip_plan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    article_no INT NOT NULL,
    day_no INT,
    attraction_list VARCHAR(45),
    CONSTRAINT fk_board_article_no FOREIGN KEY (article_no) REFERENCES board(article_no) ON DELETE CASCADE
);
