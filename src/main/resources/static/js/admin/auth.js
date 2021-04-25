elearning_token = localStorage.getItem("elearning-token");
if (elearning_token === null || elearning_token === "") {
	location.replace("http://localhost:8080/admin/page/login");
}

function logout(){
	localStorage.removeItem("elearning-token");
	location.reload();
}