user: includes an outline for the restful api, a sample java program to upload a file, and a sample java program for restful


Installation:
Have a LAMP server set up.
Have apache host the main directory.
Make a mysql database with 2 tables: members, and login_attempts. 
	members should be as below:
	CREATE TABLE `db_name`.`members` (
    		`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    		`username` VARCHAR(30) NOT NULL,
   		 `email` VARCHAR(50) NOT NULL,
   		 `password` CHAR(128) NOT NULL,
   		 `salt` CHAR(128) NOT NULL 
		 ) ENGINE = InnoDB;

	login_attempts as below:
		CREATE TABLE `secure_login`.`login_attempts` (
    		`user_id` INT(11) NOT NULL,
 		`time` VARCHAR(30) NOT NULL
		) ENGINE=InnoDB;

Adjust the info in public_html/login/includes/psl-config.php. This info consists of: host, username, password, database_name. The user for the database must of SELECT and INSERT permissions.

To upload files apache needs read/write permission for public_html/uploads.
