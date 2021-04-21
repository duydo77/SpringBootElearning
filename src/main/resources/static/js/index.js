const path = "http://localhost:8080/api/";

$(document).ready(() => {

	elearning_token = localStorage.getItem("elearning-token");
	if (elearning_token === null) {
		$("#info").append("<button class='btn btn-outline-secondary' data-toggle='modal'"
			+ "data-target='#loginModal'>Login</button>"
			+ "<button class='btn btn-danger ml-2' data-toggle='modal'"
			+ "data-target='#signUpModal'>Sign up</button>");
	} else {
		$("#info").append("<div class='dropdown'>"
			+ "<div class='dropdown-toggle font-weight-bold text-dark' data-toggle='dropdown'>"
			+ "Cybersoft"
			+ "</div>"
			+ "<div class='dropdown-menu dropdown-menu-right'>"
			+ "<a class='dropdown-item' href='http://localhost:8080/profile'>Thông tin cá nhân</a>"
			+ "<a class='dropdown-item' href='http://localhost:8080/mycourse'>Khóa học của tôi</a>"
			+ "<div class='dropdown-divider'></div>"
			+ "<a class='dropdown-item' href='#'>Đăng xuất</a>"
			+ "</div>"
			+ "</div>");
	}
	init();

});

function loadUserInfo() {

}

function init() {

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
		url: "http://localhost:8080/api/course/promotion",
		type: "GET",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			let selector = "#sale-off";
			item_count = 0
			for (var i = 0; i < data.length; i++) {
				if (item_count === 4) {
					break;
				}
				$(selector).append(json2course_promotion(data[i]));
				item_count += 1;
			}
		},

		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

	$.ajax({
		url: "http://localhost:8080/api/course/popular",
		type: "GET",
		dataType: "json",
		contentType: "application/json",
		success: function(data) {
			let selector = "#popular";
			item_count = 0
			console.log(data.length);
			let content = ""
			for (var i = 0; i < data.length; i++) {
				if (item_count === 0) {
					content = "<div class='row'>";
				}

				content += json2course_popular(data[i]);
				item_count += 1;

				if (item_count === 6 || i === data.length - 1) {
					content += "</div>";
					$(selector).append(content);
					item_count = 0;
				}
			}
		},

		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});
}

function login() {
	let newdata = {}
	newdata.email = $("#lgEmail").val();
	newdata.password = $("#lgPassword").val();
	newdata = JSON.stringify(newdata);
	console.log(newdata);
	$.ajax({
		url: "http://localhost:8080/api/auth/login",
		type: 'POST',
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			if (data === null) {
				$('#message').text("email hoặc mật khẩu không đúng");
			} else {
				console.log(data);
				localStorage.setItem("elearning-token", data);
				location.replace("http://localhost:8080/");
			}
			console.log(localStorage.getItem("elearning-token"));
		},
		error: function(jqXhr, textStatus, errorThrown) {
			$('#message').text("email hoặc mật khẩu không đúng");
		}
	});
}

function json2course_promotion(data) {
	return "<div class='col-md-3'>"
		+ "<div class='course'>"
		+ "<img src='./img/html-css.jpg' />"
		+ "<h6 class='course-title'>" + data.title + "</h6>"
		+ "<small class='course-content'>"
		+ data.content
		+ "</small>"
		+ "<div class='course-price'>"
		+ "	<span>" + data.promotionPrice + " đ</span>"
		+ "	<small>" + data.price + " đ</small>"
		+ "</div>"
		+ "<div class='seller-label'>Sale" + data.discount + "%</div>"
		+ "<div class='course-overlay'>"
		+ "<a href='details.html'>"
		+ "<h6 class='course-title'>"
		+ data.title
		+ "</h6 >"
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
		+ "<small class='course-content'>"
		+ data.content
		+ "</small>"
		+ "</a>"
		+ "<a href = '#' class='btn btn-sm btn-danger text-white w-100'>" + "Chi tiết" + "</a>"
		+ "</div >"
		+ "</div >"
		+ "</div >"
}

function json2course_popular(data) {
	return "<div class='col-md-2'>"
		+ "<div class='course'>"
		+ "<img src='./img/html-css.jpg' />"
		+ "<h6 class='course-title'>" + data.title + "</h6>"
		+ "<small class='course-content'>"
		+ data.content
		+ "</small>"
		+ "<div class='course-price'>"
		+ "	<span>" + data.price + " đ</span>"
		+ "</div>"
		+ "<div class='course-overlay'>"
		+ "<a href='details.html'>"
		+ "<h6 class='course-title'>"
		+ data.title
		+ "</h6 >"
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
		+ "<small class='course-content'>"
		+ data.content
		+ "</small>"
		+ "</a>"
		+ "<a href = '#' class='btn btn-sm btn-danger text-white w-100'>" + "Chi tiết" + "</a>"
		+ "</div >"
		+ "</div >"
		+ "</div >"
}