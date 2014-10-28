<?php
include_once 'db_connect.php';
include_once 'functions.php';
 
sec_session_start(); 
echo("made it this far"); 
if (isset($_POST['email'], $_POST['p'])) {
    $email = $_POST['email'];
    $password = $_POST['p']; 
    echo($email . $password);
 
    if (login($email, $password, $mysqli) == true) {
        // Login successful
	echo("win");
        header('Location: ../../protected_page.php');
    } else {
        // Login failed 
	echo("lose");
        header('Location: ../index.php?error=1');
    }
} else { 
    echo 'Invalid Request';
}
