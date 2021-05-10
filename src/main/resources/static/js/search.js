path = 'http://localhost:8080/'
token = "";

query_string = location.href.split('?')[1];

key = (query_string.split('&')[0]).split('=')[1].replaceAll('%20', ' ');
p = 1;  
$(document).ready(function() {

	init();

});

function init() {

	token = localStorage.getItem("elearning-token");
	if (token !== null) {
		$.ajax({
			url: path + "api/user/profile",
			type: "GET",
			dataType: "json",
			headers: { "Authorization": token },
			contentType: 'text/html',
			success: function(data) {
				let selector = "#info";
				console.log(data)
				$(selector).append("<div class='dropdown'>"
					+ "<div class='dropdown-toggle font-weight-bold text-dark' data-toggle='dropdown'>"
					+ data.fullname + " "
					+ "<img style='border: 3px outset #ddd;' width='35' height='35' class='avatar-title rounded-circle' src='" + data.avatar + "'>"
					+ "</div>"
					+ "<div class='dropdown-menu dropdown-menu-right'>"
					+ "<a class='dropdown-item' href='" + path + "profile'>Thông tin cá nhân</a>"
					+ "<a class='dropdown-item' href='" + path + "mycourse'>Khóa học của tôi</a>"
					+ "<div class='dropdown-divider'></div>"
					+ "<a class='dropdown-item' onclick='logout()'>Đăng xuất</a>"
					+ "</div>"
					+ "</div>");
			},

			error: function(jqXhr, textStatus, errorThrown) {
				console.log(jqXhr.responseText);
			}
		});

	} else {
		$("#info").append("<button class='btn btn-outline-secondary' data-toggle='modal'"
			+ "data-target='#loginModal'>Login</button>"
			+ "<button class='btn btn-danger ml-2' data-toggle='modal'"
			+ "data-target='#signUpModal'>Sign up</button>");
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
					
				let content_filter = ''
					+ '<div class="dropdown-item">'
					+ '<label for= "">  ' 
					+ '<input type="checkbox" name="category" value="' + data[i].name + '"/> '
					+ data[i].name + ' </label>'
					+ '</div >'
				$(selector).append(content);
				$('#filter-cate').append(content_filter);
			}
			$('#filter-cate').append('<div class="dropdown-item"><p onclick="uncheck_all(\'category\')">Clear</p></div>');
			
		},

		error: function(jqXhr, textStatus, errorThrown) {
			console.log(jqXhr.responseText);
		}
	});

	$.ajax({
		url: path + 'api/course/search?' + query_string,
		type: "GET",
		dataType: "json",
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

$('#btn-filter').click(() => {
	let data_filter = {
		"key": key,
		"p": p,
		"category": checked2array('category'),
		"duration": checked2array('duration'),
		"price": checked2array('price')
	}
	
	data_filter = JSON.stringify(data_filter);
	
	console.log(data_filter);
	$.ajax({
		url: path + 'api/course/filter',
		type: "POST",
		dataType: "json",
		data: data_filter,
		contentType: 'application/json',
		success: function(data) {
			let selector = "#list-course";
			$(selector).empty();
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

})


function logout() {
	localStorage.removeItem("elearning-token");
	location.replace(path);
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
		+ "<a href='" + path + "coursedetail/" + data.id + "' class='my-course-item'>"
		+ "<div class='row' >"
		+ "<div class='col-md-3'>"
		+ "<img style='width: 100%; height:150px' class='rounded mb-3 mb-md-0' src='" + data.image + "' alt=''>"
		+ "</div>"
		+ "<div class='col-md-7'>"
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
		+ "<div class='col-sm-2 text-center' >"
}

function detail(courseId) {
	console.log("onclick detail" + courseId)
	$.ajax({
		crossDomain: true,
		type: 'GET',
		url: path + 'api/course/' + courseId,
		headers: {
			'Authorization': 'Bearer ' + token,
			'Content-Type': 'application/json',
		},
		dataType: 'text',
		success: (dataa) => {
			window.location.href = path + "coursedetail/" + courseId;
		},
		error: () => {
		}
	});
};

function checked2array(selection) {
	return $('input[name=' + selection + ']:checked').map(function(_, el) {
		return $(el).val();
	}).get();
}

function uncheck_all(selection) {
	console.log('clear');
	$('input[name=' + selection + ']').map(function(_, el) {
		$(el).prop('checked', false);
	})
}



