//token = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QGdtYWlsLmNvbSIsImlhdCI6MTYxODQwOTEwMSwiZXhwIjoxNjE5MjczMTAxfQ.kxbie4dxXKHlqnf3qGsALGmiIDgV9PnkyoO3yID3Sk3YAcYahC9jbA1De5UEFhnxS6BeS4Kj868Z5RxKZ4avoA';
token = "";
$(document).ready(function() {

	init();

});

function init() {

	token = localStorage.getItem("elearning-token");
	if (token === null) {
		location.replace("http://localhost:8080/");
	}

	$.ajax({
		url: "http://localhost:8080/api/category",
		type: "GET",
		dataType: "json",
		contentType: 'text/html',
		success: function(data) {
			let selector = "#list-category";
			for (var i = 0; i < data.length; i++) {
				let content =
					"<a class='dropdown-item' href='" + data[i].id + "'>"
					+ "<i class='fa " + data[i].icon + " mr-1'></i>"
					+ "<span> " + data[i].name + "</span>"
					+ "</a >";
				$(selector).append(content);
			}
		},

		error: function(jqXhr, textStatus, errorThrown) {
			console.log(jqXhr.responseText);
		}
	});

	$.ajax({
		url: 'http://localhost:8080/api/user/profile',
		type: "GET",
		dataType: "json",
		headers: {
			'Authorization': token
		},
		contentType: 'text/html',
		success: function(data) {
			$("#avatar-icon").find('div').text(data.fullname);
			$("#avatar-icon").find('img').attr('src', data.avatar);
		},

		error: function(jqXhr, textStatus, errorThrown) {
			console.log(jqXhr.responseText);
		}
	});

	$.ajax({
		url: 'http://localhost:8080/api/course/mycourse',
		type: "GET",
		dataType: "json",
		headers: {
			'Authorization': token
		},
		contentType: 'text/html',
		success: function(data) {
			console.log(data)
			let selector = "#list-course";
			item_count = 0
			let content = ""
			for (var i = 0; i < data.length; i++) {
				if (item_count === 0) {
					content = "<div class='row'>";
				}

				content += json2html(data[i]);
				item_count += 1;

				if (item_count === 6 || i === data.length - 1) {
					content += "</div>";
					$(selector).append(content);
					item_count = 0;
				}
			}
		},

		error: function(jqXhr, textStatus, errorThrown) {
			console.log(jqXhr.responseText);
		}
	});

}

function logout() {
	localStorage.removeItem("elearning-token");
	location.replace("http://localhost:8080/");
}

function json2html(data) {
	return "<div class='col-md-3'>"
		+ "<a href='" + "http://localhost:8080/coursedetail/" + data.id + "' class='my-course-item'>"
		+ "<img src='./img/html-css.jpg' alt=''>"
		+ "<h6 class='my-course-title'> " + data.title + "</h6>"
		+ "<div class='my-course-desc'>"
		+ data.desc
		+ "</div >"
		+ "<div class='my-course-author'>"
		+ "<h6>"
		+ "<small>" + data.teacherName + "</small>"
		+ "<small>Start course</small>"
		+ "</h6>"
		+ "</div>"
		+ "</a>"
		+ "</div>"
}
