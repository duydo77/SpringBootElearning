token = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QGdtYWlsLmNvbSIsImlhdCI6MTYxODQwOTEwMSwiZXhwIjoxNjE5MjczMTAxfQ.kxbie4dxXKHlqnf3qGsALGmiIDgV9PnkyoO3yID3Sk3YAcYahC9jbA1De5UEFhnxS6BeS4Kj868Z5RxKZ4avoA';
admin_token = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpYXQiOjE2MTg4MDA4ODQsImV4cCI6MTYxOTY2NDg4NH0.e2CoqDPWHCI24szPwJfWt3y4PVZM-Emvo3bf7QCrERmdjCRH5nMP_W_47iqtfoIjzW_Lu4ZxmNjhj1Pa1M1Fsw';


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
				location.replace("http://localhost:8080/admin/page");	
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