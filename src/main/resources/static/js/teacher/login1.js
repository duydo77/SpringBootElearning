function login() {
	let form = $('#login-form');
	let data = getFormData(form.serializeArray());
	let newdata = JSON.stringify(data);
	console.log(newdata);
	$.ajax({
		url: "http://localhost:8080/api/teacher/login",
		type: 'POST',
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			if (data === null) {
				$('#message').text("Email hoặc mật khẩu không đúng");
			} else {
				console.log(data);
				localStorage.setItem("elearning-token", data);
				location.replace("http://localhost:8080/teacher/");	
			}
			console.log(localStorage.getItem("elearning-token"));
		},
		error: function(jqXhr, textStatus, errorThrown) {
			$('#message').text("Email hoặc mật khẩu không đúng");
		}
	});
}

function getFormData(data) {
	var unindexed_array = data;
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}