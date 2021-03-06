<?php
include_once 'login/includes/db_connect.php';
include_once 'login/includes/functions.php';
sec_session_start();

if(login_check($mysqli) == false) {
	die("LOGIN");
}

$destination=$_SERVER[DOCUMENT_ROOT]."/uploads/" . $_FILES["userfile"]["name"];
// Check for errors
if($_FILES['userfile']['error'] > 0){
    die('An error ocurred when uploading.');
}
//had to comment out the next section/s because it was not working with java
//I think the java code is not sending it in a way that 
//the following code knows is an image/jpeg
//if(!getimagesize($_FILES['userfile']['tmp_name'])){
//    die('Please ensure you are uploading an image.');
//}

// Check filetype
//if($_FILES['userfile']['type'] != 'image/jpeg'){
//    die('Unsupported filetype uploaded.');
//}

// Check filesize
if($_FILES['userfile']['size'] > 500000){
    die('File uploaded exceeds maximum upload size.');
}

//Commented out to make for easy testing
// Check if the file exists
//if(file_exists($destination)){
//    die('File with that name already exists.');
//}

// Upload file
if(!move_uploaded_file($_FILES['userfile']['tmp_name'], $destination)){
  
   $html_body = '<h1>File upload error!</h1>';
   switch ($_FILES[0]['error']) {
   case 1:
      $html_body .= 'The file is bigger than this PHP installation allows';
      break;
   case 2:
      $html_body .= 'The file is bigger than this form allows';
      break;
   case 3:
      $html_body .= 'Only part of the file was uploaded';
      break;
   case 4:
      $html_body .= 'No file was uploaded';
      break;
   default:
      $html_body .= 'unknown errror';
   } 
   echo ($html_body);	

	die('Error uploading file - check destination is writeable.');
}
die('File uploaded successfully.');
