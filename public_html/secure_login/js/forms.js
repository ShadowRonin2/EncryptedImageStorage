function formhash(form, password) {
	//creat new element input, this is for a hashed password field
	var p = document.creatElement("input");
	
	//add element to the form
	form.appendChild(p);
	p.name = "p";
	p.type = "hidden";
	p.value = hex_sha512(password.value);
	
	//make sure plaintext is not sent
	password.vaule = "";
	
	//submit the form
	form.submit();
}

function regformhash(form, uid, email, password, conf) {
	//make sure each field has a value
	if(uid.value == '' || email.value == '' || password.value == '' || conf.value == '') {
		alert('You must provide all the requested details. Please try again');
		return false;
	}
	
	re = /^\w+$/;
	if(!re.test(form.username.value)) {
		alert("Username must contain only letters, numbers and underscores. Please try again");
		form.username.focus();
		return false;
	}

	//check length of password
	if(password.value.length < 6) {
		alert('Passwords must be at least 6 characters long. Please try again');
		form.password.focus();
		return false;
	}

	//check password for 1 uppercase, 1 lowercase, and 1 number
	var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
	if(! re.test(password.value)) {
		alert('Passwords must contain 1 number, 1 lowercase letter, and 1 uppercase letter');
		return false;
	}

	//make hashed password field
	var p = document.createElement("input");
	form.appendChild(p);
	p.name = "p";
	p.type = "hidden";
	p.value = hex_sha512(password.value);

	//make sure plaintext not sent
	password.value = "";
	conf.value = "";

	//submit
	form.submit
	return true;
}
