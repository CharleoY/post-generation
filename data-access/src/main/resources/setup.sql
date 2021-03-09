create database if not exists post_generation

-- create the users for each database
CREATE USER 'user'@'%' IDENTIFIED BY 'u$3r_@dm1n';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `post_generation`.* TO 'user'@'%';

FLUSH PRIVILEGES;