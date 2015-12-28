DELIMITER $$
CALL sp_owners_SELECT
   (
	 10,
          @p_first_name,
          @p_last_name, 
          @p_address,
          @p_city,
          @p_telephone
   )$$
SELECT 
    @p_first_name AS p_first_name,
    @p_last_name AS p_last_name,
    @p_address AS p_address,
    @p_firstname AS p_city,
    @p_telephone AS p_telephone
$$
delimiter ;