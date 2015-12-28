drop procedure if exists RecordStep;

delimiter $$

CREATE PROCEDURE `p3` (in txt varchar(100))
LANGUAGE SQL
DETERMINISTIC
SQL SECURITY DEFINER
COMMENT 'A procedure'
BEGIN
    SELECT concat('Hello World: ', txt);
END$$

delimiter ;

call p3("test");
call p("this will work too");
