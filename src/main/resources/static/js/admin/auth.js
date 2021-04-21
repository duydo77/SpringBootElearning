elearning_token = localStorage.getItem("elearning-token");
if (elearning_token === null) {
	location.replace("http://localhost:8080/admin/page/login");
}