DELIMITER $$
CALL sp_owners_SELECT(<OWNERID>, @p_first_name,@p_last_name, @p_address,@p_city,@p_telephone)$$
SELECT
    @p_first_name AS `first name`,
    @p_last_name AS `last name`,
    @p_address AS `address`,
    @p_city AS `city`,
    @p_telephone AS `telephone`
$$
DELIMITER ;
