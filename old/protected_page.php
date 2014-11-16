<?php
include_once 'login/includes/db_connect.php';
include_once 'login/includes/functions.php';

sec_session_start();
?>

<!DOCTYPE html>
<html>
	<head><title>A sample protected page</title></head>
	<body>
		<!-- Display diffrent html depending on if user is logged in -->
		<?php if(login_check($mysqli) == true) : ?>
		<h1>Welcome to the protected page!</h1>
		<p>Go <a href="uploadform.php">here</a> to upload a image or use the java code</p>
		
		<?php else : ?>
		<h1>Who are you?!?</h1>
		<p>The page is not for you. Come back after you <a href="login/login.php">login</a>.</p>
		
		<?php endif; ?>
	</body>
</html>
