<?php
/**
* Input: 
* $_POST['overwrite'] = True | False
* $_FILE['file']
*
*/
$destination=$_SERVER[DOCUMENT_ROOT]."/uploads/" . $_FILES["file"]["name"];
// Check for errors
if($_FILES['file']['error'] > 0){
    die('An error ocurred when uploading.');
}

// Check filetype
//if($_FILES['file']['type'] != 'image/jpeg'){
//    die('Unsupported filetype uploaded.');
//}

// Check filesize
if($_FILES['file']['size'] > 500000){
    die('File uploaded exceeds maximum upload size.');
}

//Check if the file exists
if($_POST['overwrite']==False & file_exists($destination)){
    die('File already exists!');
}

// Upload file
if(!move_uploaded_file($_FILES['file']['tmp_name'], $destination)){
  
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
      $html_body .= 'Empty';
      break;
   default:
      $html_body .= 'unknown errror';
   } 
   echo ($html_body);	

	die('Error uploading file - check destination is writeable.');
}
die('Success');
