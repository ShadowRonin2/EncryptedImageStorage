#! /bin/bash

install_location='/var/www'
mysql_database_name="IronCloud"
mysql_admin_user="root"
mysql_admin_pass="password"
mysql_server_user="serverUser"
mysql_server_password=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1)

echo "Installing dependencies"
apt-get update
echo mysql-server mysql-server/root_password password $mysql_admin_pass | sudo debconf-set-selections
echo mysql-server mysql-server/root_password_again password $mysql_admin_pass | sudo debconf-set-selections
apt-get install lamp-server^ phpmyadmin -y

apt-get install php5-mysql -y
apt-get install mod_rewrite -y
a2enmod rewrite
service apache2 restart
echo ""

echo "Decompressing package"
tar -xfv IronCoud.tar -C $install_location

echo "Setting up mysql"
#configure apache & make mysql tables
temp = "CREATE DATABASE $mysql_database_name;"
mysql -u $mysql_admin_user -p $mysql_admin_pass -e $temp
temp = "CREATE TABLE `members` (`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `username` VARCHAR(30) NOT NULL, `email` VARCHAR(50) NOT NULL,`password` CHAR(128) NOT NULL,`salt` CHAR(128) NOT NULL) ENGINE = InnoDB;"
mysql -u $mysql_admin_user -p $mysql_admin_pass -D $mysql_database_name -e $temp

echo "Configureing server"
apache-config
IronCloud-config

echo "Cleaning up"

function apache-config {
  cp apache2.conf /etc/apache2/apache2.conf
  sed "s|IRONCLOUDDES|$install_location/IronCloud/public_html|g" /etc/apache2/apache2.conf
}

function IronCloud-config {
  echo "<?php" > $install_location"/private_html/includes/psl-config.php"
  echo "define("HOST", "localhost");" >> $install_location"/private_html/includes/psl-config.php"
  echo "define("USER", "loginuser");" >> $install_location"/private_html/includes/psl-config.php"
  echo "define("PASSWORD", "examplepassword");" >> $install_location"/private_html/includes/psl-config.php"
  echo "define("DATABASE", "login");" >> $install_location"/private_html/includes/psl-config.php"
  echo  "" >> $install_location"/private_html/includes/psl-config.php"
  echo "define("CAN_REGISTER", "any");" >> $install_location"/private_html/includes/psl-config.php"
  echo "define("DEFAULT_ROLE", "member");" >> $install_location"/private_html/includes/psl-config.php"
  echo "" >> $install_location"/private_html/includes/psl-config.php"
  echo "define("SECURE", FALSE);" >> $install_location"/private_html/includes/psl-config.php"

}