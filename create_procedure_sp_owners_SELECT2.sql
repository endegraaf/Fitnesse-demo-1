-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_owners_SELECT`(
        IN   p_id                 		INT(4)       , 
        OUT  p_first_name               VARCHAR(30)   , 
        OUT  p_last_name                VARCHAR(30)    , 
        OUT  p_address                  VARCHAR(255)   , 
        OUT  p_city                     VARCHAR(80)   , 
        OUT  p_telephone                VARCHAR(20)
     )
BEGIN 

    SELECT id                      		, 
           first_name                   , 
           last_name                    , 
           address                     	, 
           city                   		, 
           telephone                    
    INTO   p_id                      	, 
           p_first_name                 , 
           p_last_name                  , 
           p_address                    , 
           p_city                   	, 
           p_telephone                    
    FROM   owners
    WHERE  id = p_id ; 

END