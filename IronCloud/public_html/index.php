<?php
include_once 'login/includes/db_connect.php';
include_once 'login/includes/functions.php';

sec_session_start();

if (login_check($mysqli) == true) {
    $logged = 'in';
} else {
    $logged = 'out';
}
?>

<html>
<head><title>"The main page"</title></head>
<body>	
	<h1>Welcome to the home page!</h1>
	<p>You are logged  <?php echo($logged); ?>.</p>
	<p>If you are not login, please <a href="login/login.php">login</a>.</p>
	<p>If you don't have a login, please <a href="login/register.php">register</a>.</p>
        <p>If you are done, please <a href="login/includes/logout.php">log out</a>.</p>		
</body>
</html>
