DROP SCHEMA IF EXISTS HotelManager;
DROP USER IF EXISTS 'spq'@'localhost';
CREATE SCHEMA IF NOT EXISTS HotelManager;
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';
GRANT ALL ON HotelManager.* TO 'spq'@'localhost';