token = "";
path = "http://localhost:8080/"
let cateId = location.href.split('/').pop();

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
				if (data[i].id === parseInt(cateId)) {$('#cate-name').html('Category: ' + data[i].name);}
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
		url: path + 'api/course/cate/' + cateId,
		type: "GET",
		dataType: "json",
		headers: {
			'Authorization': token
		},
		contentType: 'text/html',
		success: function(data) {
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
	location.replace(path);
}

function json2html(data) {
	return "<div class='col-md-3'>"
		+ "<a href='#' onclick='detail(" + data.id + ")'" + "' class='my-course-item'>"
		+ "<img style='height: 125px' src='" + data.image + "'>"
		+ "<h6 class='my-course-title'> " + data.title + "</h6>"
		+ "<div class='my-course-desc'>"
		+ data.desc
		+ "</div >"
		+ "<div class='my-course-author'>"
		+ "<h6>"
		+ "<small>" + data.teacherName + "</small>"
		+ "<small>View detail</small>"
		+ "</h6>"
		+ "</div>"
		+ "</a>"
		+ "</div>"
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
        success : (data) => {
            window.location.href = "http://localhost:8080/coursedetail/" + courseId;
        },
        error: () => {

        }
    });
};
