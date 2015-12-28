drop procedure if exists p4;

delimiter $$

CREATE PROCEDURE `p4` (in txt varchar(100))
LANGUAGE SQL
DETERMINISTIC
SQL SECURITY DEFINER
COMMENT 'A procedure'
BEGIN
    SELECT concat('Hello World: ', txt);
END$$

delimiter ;

call p4("test");
call p4("this will work too");