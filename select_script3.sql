DELIMITER $$
SELECT first_name as `first name`,
	last_name as `last name`,
	address as `address`
	FROM owners
	WHERE id IN (1,2,3)
$$
DELIMITER ;
