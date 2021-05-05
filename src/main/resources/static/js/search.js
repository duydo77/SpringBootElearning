path = 'http://localhost:8080/'
token = "";

query_string = location.href.split('?')[1];

key = (query_string.split('&')[0]).split('=')[1].replaceAll('%20', ' ');

$(document).ready(function() {

	init();

});

function init() {

	token = localStorage.getItem("elearning-token");
	if (token === null) {
		location.replace(path);
	}

	$.ajax({
		url: path + "api/category",
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
		url: path + 'api/user/profile',
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
		url: path + 'api/course/search?' + query_string,
		type: "GET",
		dataType: "json",
		headers: {
			'Authorization': token
		},
		contentType: 'text/html',
		success: function(data) {
			let selector = "#list-course";
			let content = ""
			
			if (data.length === 0) {
				$(selector).append("No result found");
			}
			
			$('#result-count').text(data.length + ' results for "' + key + '"')
			
			for (var i = 0; i < data.length; i++) {
				content += json2html2(data[i]);
				 
				content += '<span><b>' + data[i].promotionPrice + "$" + '</b></span>';
			
				if (data[i].discount !== 0) {
					content += '</br><small style="text-decoration: line-through">' + data[i].price + '$</small>'
				}

				content += "</div>"
					+ "</div>"
					+ "</a>"
			}
			$(selector).append(content);
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
	return ""
		+ "<a href='" + path + "coursedetail/" + data.id + "'>"
		+ "<div class='course'>"
		+ '<div class="row">'
		+ '<div class="col-md-4">'
		+ "<img src='" + data.image + "' />"
		+ "</div >"
		+ '<div class="col-md-8">'
		+ "<h6 class='course-title'>" + data.title + "</h6>"
		+ "<small class='course-content'>"
		+ data.content
		+ "</small>"
		+ "<div class='course-author'>"
		+ "<b>" + data.teacherName + "</b>"
		+ "<span class='mx-1'> | </span>"
		+ "<b>" + data.cateName + "</b>"
		+ "</div >"
		+ "<div class='course-info'>"
		+ "<span><i class='fa fa-play-circle'></i> " + data.lectureCount + " lectures</span>"
		+ "<span class='mx-1'> | </span>"
		+ "<span><i class='fa fa-clock-o'></i>" + data.hourCount + " hours</span >"
		+ "</div >"
		+ "<a href = '" + path + "coursedetail/" + data.id + "' class='btn btn-sm btn-danger text-white w-100'>" + "Chi tiết" + "</a>"
		+ "</div>"
		+ "</div>"
		+ "</div>"
		+ "</a>"
}

function json2html2(data) {
	return ""
		+ "<a href='#' onclick='detail(" + data.id + ")'" + "' class='my-course-item'>"
		+ "<div class='row' >"
		+ "<div class='col-md-3'>"
		+ "<img style='width: 100%; height:150px' class='rounded mb-3 mb-md-0' src='" + data.image + "' alt=''>"
		+ "</div>"
		+ "<div class='col-md-8'>"
		+ "<h6 class='my-course-title'> " + data.title + "</h6>"
		+ "<div class='my-course-desc'>"
		+ data.content
		+ "</div >"
		+ "<div class='my-course-author'>"
		+ "<h6>"
		+ "<small>" + data.teacherName + ' | ' + data.cateName + "</small>"
		+ "<small>View detail</small>"
		+ "</h6>"
		+ "</div>"
		+ "</div>"
		+ "<div class='col-md-1'>"


}

function detail(courseId) {
	console.log("onclick detail" + courseId)
	$.ajax({
		crossDomain: true,
		type: 'GET',
		url: 'http://localhost:8080/api/course/' + courseId,
		headers: {
			'Authorization': 'Bearer ' + token,
			'Content-Type': 'application/json',
		},
		dataType: 'text',
		success: (dataa) => {
			window.location.href = "http://localhost:8080/coursedetail/" + courseId;
		},
		error: () => {
		}
	});
};
