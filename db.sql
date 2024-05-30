# 데이터 베이스 삭제 및 생성
DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;
USE text_board;

# 게시물 테이블 생성
CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title CHAR(100) NOT NULL,
	`body` TEXT NOT NULL
);

# 회원 테이블 생성
CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(100) NOT NULL UNIQUE,
	loginPw CHAR(200) NOT NULL,
	`name` CHAR(100) NOT NULL
);

# 회원 테스트 데이터
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = '1234',
`name` = '관리자';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = '1234',
`name` = '회원1';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = '1234',
`name` = '회원2';

# 게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

# 게시물 테이블에 hit(조회수) 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT UNSIGNED NOT NULL AFTER `body`;

DESC article;

# 게시물 테스트 데이터, memberId 추가
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
title = '제목1',
`body` = '내용1',
hit = 0;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
title = '제목2',
`body` = '내용2',
hit = 0;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
title = '제목3',
`body` = '내용3',
hit = 0;