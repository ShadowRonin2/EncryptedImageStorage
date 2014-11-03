<?php
function deliver_response($format, $api_response){
	/**
	* Delivers the response to the client
	*/

	// Define HTTP responses
	$http_response_code = array(
		200 => 'OK',
		400 => 'Bad Request',
		401 => 'Unauthorized',
		403 => 'Forbidden',
		404 => 'Not Found'
	);

	// Set HTTP Response
	header('HTTP/1.1 '.$api_response['status'].' '.$http_response_code[ $api_response['status'] ]);

	// Process different content types
	if( strcasecmp($format,'json') == 0 ){

		// Set HTTP Response Content Type
		header('Content-Type: application/json; charset=utf-8');

		// Format data into a JSON response
		$json_response = json_encode($api_response);

		// Deliver formatted data
		echo $json_response;

	}elseif( strcasecmp($format,'xml') == 0 ){

		// Set HTTP Response Content Type
		header('Content-Type: application/xml; charset=utf-8');

		// Format data into an XML response (This is only good at handling string data, not arrays)
		$xml_response = '<?xml version="1.0" encoding="UTF-8"?>'."\n".
			'<response>'."\n".
			"\t".'<code>'.$api_response['code'].'</code>'."\n".
			"\t".'<data>'.$api_response['data'].'</data>'."\n".
			'</response>';

		// Deliver formatted data
		echo $xml_response;

	}else{

		// Set HTTP Response Content Type (This is only good at handling string data, not arrays)
		header('Content-Type: text/html; charset=utf-8');

		// Deliver formatted data
		echo $api_response['data'];

	}

	// End script process
	exit;

}

function login($username, $password) {
  /**
  * Login the user
  * Return: True if the credentials are correct and false otherwise.
  */
  return True;
}

function uploadFile($file, $overwrite) {
  /**
  * Input: 
  * $overwrite = True | False
  * $_FILE['file']
  *
  */
  echo("it worked!");
  $destination=$_SERVER[DOCUMENT_ROOT]."../uploads/" . $file["name"];
  // Check for errors
  if($file['error'] > 0){
      die('An error ocurred when uploading.');
  }

  // Check filetype
  //if($_FILES['file']['type'] != 'image/jpeg'){
  //    die('Unsupported filetype uploaded.');
  //}

  // Check filesize
  if($file['size'] > 500000){
      die('File uploaded exceeds maximum upload size.');
  }

  //Check if the file exists
  if(!$overwrite and file_exists($destination)){
      die('File already exists!');
  }

  // Upload file
  if(!move_uploaded_file($file['tmp_name'], $destination)){
  
    $html_body = '<h1>File upload error!</h1>';
    switch ($file['error']) {
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
}