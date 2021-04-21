token = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QGdtYWlsLmNvbSIsImlhdCI6MTYxODQwOTEwMSwiZXhwIjoxNjE5MjczMTAxfQ.kxbie4dxXKHlqnf3qGsALGmiIDgV9PnkyoO3yID3Sk3YAcYahC9jbA1De5UEFhnxS6BeS4Kj868Z5RxKZ4avoA';
admin_token = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjFAZ21haWwuY29tIiwiaWF0IjoxNjE4OTE2NDYxLCJleHAiOjE2MTk3ODA0NjF9.J2jMYc2D2_FQqxRGD-n6gs-PRY8UhMcgVqsced09oFLQmVQAvwKAZ6AUOJyHA5PCE5Xq8yXNG9OBRjWjc9eyqQ';

function login() {
	let form = $('#login-form');
	let data = getFormData(form.serializeArray());
	let newdata = JSON.stringify(data);
	console.log(newdata);
	$.ajax({
		url: "http://localhost:8080/api/admin/login",
		type: 'POST',
		contentType: 'application/json',
		headers: { "Authorization": admin_token },
		data: newdata,
		success: function(data) {
			if (data === null) {
				$('#message').text("email hoặc mật khẩu không đúng");
			} else {
				console.log(data);
				localStorage.setItem("elearning-token", data);
				localStorage.removeItem("elerning-token");
			}
			console.log(localStorage.getItem("elearning-token"));
		},
		error: function(jqXhr, textStatus, errorThrown) {
			$('#message').text("email hoặc mật khẩu không đúng");
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