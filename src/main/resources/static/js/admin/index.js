elearning_token = ""
$(document).ready(function() {

	elearning_token = localStorage.getItem("elearning-token");
	console.log(elearning_token);
	if (elearning_token === null || elearning_token === "") {
		location.replace("http://localhost:8080/admin/page/login");
	}
	init();
});

function init() {

	$.ajax({
		url: "http://localhost:8080/api/admin/user/profile",
		dataType: 'json',
		type: 'GET',
		headers: { "Authorization": elearning_token },
		contentType: 'text/html',
		success: function(data) {
			$("#dropdownId").text(data.fullname);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			if (jqXhr.status === 403) {
				location.replace("http://localhost:8080/405");
			}
		}
	});
}