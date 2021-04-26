elearning_token = localStorage.getItem("elearning-token");


if (elearning_token === null || elearning_token === "") {
	location.replace("http://localhost:8080/admin/page/login");
}

$.ajax({
	url: "http://localhost:8080/api/admin/user/profile",
	dataType: 'json',
	type: 'GET',
	headers: { "Authorization": elearning_token },
	contentType: 'text/html',
	success: function(data) {
		
	},
	error: function(jqXhr, textStatus, errorThrown) {
		location.replace("http://localhost:8080/admin/page/login");
	}
});

function logout() {
	localStorage.removeItem("elearning-token");
	location.reload();
}