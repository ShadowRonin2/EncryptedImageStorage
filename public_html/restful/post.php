<?php
/*
	Input:
		$_GET['format'] = [ json | html | xml ]
		$_GET['method'] = []
		$_POST['username']= ""
		$_POST['password']=""

	Author: Benjamin Wunschel
	Original code(from a online tutriol) by: Mark Roland
*/
include_once 'includes/functions.php';
// --- Step 1: Initialize variables and functions

// Define whether an HTTPS connection is required
$HTTPS_required = FALSE;

// Define whether user authentication is required
$authentication_required = TRUE;

// Define API response codes and their related HTTP response
$api_response_code = array(
	0 => array('HTTP Response' => 400, 'Message' => 'Unknown Error'),
	1 => array('HTTP Response' => 200, 'Message' => 'Success'),
	2 => array('HTTP Response' => 403, 'Message' => 'HTTPS Required'),
	3 => array('HTTP Response' => 401, 'Message' => 'Authentication Required'),
	4 => array('HTTP Response' => 401, 'Message' => 'Authentication Failed'),
	5 => array('HTTP Response' => 404, 'Message' => 'Invalid Request'),
	6 => array('HTTP Response' => 400, 'Message' => 'Invalid Response Format')
);

// Set default HTTP response of unknown error
$response['code'] = 0;
$response['status'] = 404;
$response['data'] = NULL;

// --- Step 2: Authorization

// Optionally require connections to be made via HTTPS
if( $HTTPS_required && $_SERVER['HTTPS'] != 'on' ){
	$response['code'] = 2;
	$response['status'] = $api_response_code[ $response['code'] ]['HTTP Response'];
	$response['data'] = $api_response_code[ $response['code'] ]['Message'];

	// Return Response to browser. This will exit the script.
	deliver_response($_GET['format'], $response);
}

// Optionally require user authentication
if( $authentication_required ){

	if( empty($_POST['username']) || empty($_POST['password']) ){
		$response['code'] = 3;
		$response['status'] = $api_response_code[ $response['code'] ]['HTTP Response'];
		$response['data'] = $api_response_code[ $response['code'] ]['Message'];

		// Return Response to browser
		deliver_response($_GET['format'], $response);

	}
	
	//This has to be before the login, because it is a new user
	if(strcasecmp($_GET['method'],'user') == 0){
	  /*
	  * Returns: "Success" | "Invalid Password" | "Invalid Username" | "Invalid Email" | "Invalid Arguments"
	  */
	  register($_POST['username'], $_POST['password']);
	}

	// Return an error response if user fails authentication. This is a very simplistic example
	// that should be modified for security in a production environment
	elseif(!login($_POST['username'], $_POST['password'])){
		$response['code'] = 4;
		$response['status'] = $api_response_code[ $response['code'] ]['HTTP Response'];
		$response['data'] = $api_response_code[ $response['code'] ]['Message'];

		// Return Response to browser
		deliver_response($_GET['format'], $response);

	}

}

// --- Step 3: Process Request

//user is up before login, because the account must be made before it is possible to log in

if(strcasecmp($_GET['method'],'file') == 0){
	/*
	* Input: _FILE['file']
	* Return: "Success" | "Invalid file type" | "Error movinvg file" | "File is too large" | "Error uploading" | "File already exists"
	*/
	uploadFile($_FILES['userfile'], False, $_POST['username']);
	if($result == 'Success') {
	  $response['code'] = 0;
	  $response['status'] = $api_response_code[ $response['code'] ]['HTTP Response'];
	  $response['data'] = 'Success';
	} else {
	  $response['code'] = 1;
	  $response['status'] = $api_response_code[ $response['code'] ]['HTTP Response'];
	  $response['data'] = $result;
	}
}


// --- Step 4: Deliver Response

//return response to client
deliver_response($_GET['format'], $response);
