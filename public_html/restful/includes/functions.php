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

function uploadFile($file, $overwrite, $username) {
  /**
  * Input: 
  * $overwrite = True | False
  * $_FILE['file']
  *
  */
  $directory = realpath("/var/www/webhost/public_html/uploads/". $username);
  $destination= $directory ."/". $file["name"];
  
  //check to make sure the directory exists
  echo(is_dir("/var/www/webhost/public_html/uploads/");
  if(!is_dir($directory)) {
    if(!mkdir($directory)) {
      die("Failed to make the directory! This should never happen!");
    }
  }
  
  // Check for errors
  if($file['error'] > 0){
      return 'Error uploading';
  }

  // Check filetype
  //if($file['type'] != 'image/jpeg'){
  //    return "Invalid file type";
  //}

  // Check filesize
  if($file['size'] > 500000){
      return 'File is too large';
  }

  //Check if the file exists
  if(!$overwrite and file_exists($destination)){
      return "File already exists";
  }

  // Upload file
  if(!move_uploaded_file($file['tmp_name'], $destination)){
      return 'Error movinvg file';
  }
  return 'Success';
}

function register($username, $password) {

}

function changePassword($username, $newPassword) {

}