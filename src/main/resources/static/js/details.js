let root = "http://localhost:8080"
$(document).ready(() => {

	init();

});


function init() {

	elearning_token = localStorage.getItem("elearning-token");

	if (elearning_token === "" || elearning_token === null) {
		location.replave(root + "/");
		return 0;
	}

	let courseId = $("#courseId").val();

	$.ajax({
		url: "http://localhost:8080/api/course/" + courseId,
		type: "GET",
		dataType: "json",
		headers: { "Authorization": elearning_token },
		contentType: 'text/html',
		success: function(data) {
			date = new Date(data.course.lastUpdate);
			last_update = date.toDateString();
			$("#title").text(data.course.title);
			$("#content").text(data.course.content);
			$("#teacher-name").text(data.course.teacherName);
			$("#last-update").text(last_update);
			$("#lecture-count").text(data.course.letureCount);
			$("#hour-count").text(data.course.hourCount);
			$("#view-count").text(data.course.viewCount);
		},

		error: function(jqXhr, textStatus, errorThrown) {
			console.log(jqXhr.responseText);
		}
	});




}



function logout() {
	localStorage.removeItem("elearning-token");
	location.reload();
}
