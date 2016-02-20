function retrievePassword() {
	var nameField = document.getElementById("nameField").value;
	var passwordField = document.getElementById("passwordField").value;

		if(nameField.match(/\S/) && passwordField.match(/\S/)) {
			var out = sjcl.hash.sha256.hash(passwordField);
			var hash = sjcl.codec.base64.fromBits(out);
			document.getElementById("hashedPassword").value=hash;
		}
	
}

function retrieveRegisterPassword() {
	var nameField = document.getElementById("nameField").value;
	var passwordField = document.getElementById("passwordField").value;
	var passwordAgainField = document.getElementById("passwordAgainField").value;

	if(nameField.match(/\S/) && passwordField.match(/\S/) && passwordAgainField.match(/\S/)) {
		var out = sjcl.hash.sha256.hash(passwordField);
		var hash = sjcl.codec.base64.fromBits(out);
		document.getElementById("hashedPassword").value=hash;
		

		out = sjcl.hash.sha256.hash(passwordAgainField);
		hash = sjcl.codec.base64.fromBits(out);
		document.getElementById("hashedPasswordAgain").value=hash;
	}
}