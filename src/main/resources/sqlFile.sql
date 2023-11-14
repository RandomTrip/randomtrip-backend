
// board 테이블 구조 변경
ALTER TABLE `enjoytrip`.`board`
ADD COLUMN `attraction_list` VARCHAR(45) NULL AFTER `register_time`,
ADD COLUMN `is_public` INT NOT NULL DEFAULT 0 AFTER `attraction_list`;
