#! /bin/bash

install_location='/var/www'
mysql_database_name="IronCloud"
mysql_admin_user="root"
mysql_admin_pass="password"
mysql_server_user="serverUser"
mysql_server_password=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 32 | head -n 1)

:<<'END'

echo "Installing dependencies"
apt-get update
echo mysql-server mysql-server/root_password password $mysql_admin_pass | sudo debconf-set-selections
echo mysql-server mysql-server/root_password_again password $mysql_admin_pass | sudo debconf-set-selections
echo mysql-server phpmyadmin/root_password password $mysql_admin_pass | sudo debconf-set-selections
echo mysql-server phpmyadmin/root_password_again password $mysql_admin_pass | sudo debconf-set-selections
apt-get install lamp-server^ phpmyadmin -y

apt-get install php5-mysql -y
apt-get install mod_rewrite -y
a2enmod rewrite
service apache2 restart
echo ""

echo "Decompressing package"
tar -xv -f "IronCloud.tar" -C $install_location

END
echo "Setting up mysql"
#configure apache & make mysql tables
temp="CREATE DATABASE $mysql_database_name;"
mysql --user="$mysql_admin_user" --password="$mysql_admin_pass" -e "$temp"
temp="CREATE TABLE \`members\` (\`id\` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, \`username\` VARCHAR(30) NOT NULL, \`email\` VARCHAR(50) NOT NULL,\`password\` CHAR(128) NOT NULL,\`salt\` CHAR(128) NOT NULL) ENGINE = InnoDB;"
mysql --user="$mysql_admin_user" --password="$mysql_admin_pass" -D "$mysql_database_name" -e "$temp"

echo "Configureing server"
rm /etc/apache2/apache2.conf
cp apache2.conf /etc/apache2/apache2.conf
sed -n "s|IRONCLOUDDES|$install_location/IronCloud/public_html|g" /etc/apache2/apache2.conf

rm "$install_location//public_html/psl-config.php"
touch "$install_location/IronCloud/public_html/includes/psl-config.php"
echo '<?php' > $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "define("HOST", "localhost");" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "define("USER", "loginuser");" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "define("PASSWORD", "examplepassword");" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "define("DATABASE", "login");" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo  "" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "define("CAN_REGISTER", "any");" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "define("DEFAULT_ROLE", "member");" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "" >> $install_location"/IronCloud/public_html/includes/psl-config.php"
echo "define("SECURE", FALSE);" >> $install_location"/IronCloud/public_html/includes/psl-config.php"


echo "Cleaning up"