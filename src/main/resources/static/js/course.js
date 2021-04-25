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
		// + "<a href='" + "http://localhost:8080/coursedetail/" + data.id + "' class='my-course-item'>"
		+ "<a href='#' onclick='detail("+data.id+")'" + "' class='my-course-item'>"
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
        success : (dataa) => {
            window.location.href = "http://localhost:8080/coursedetail/" + courseId;
            // console.log(data);
            // // $("section.course-banner").hide();
            // // $("section.mt-5").hide();
            // // $("section.course-content").show();
            // var data = JSON.parse(dataa);
            // data.targets.map((t) => {
            //     $("#left-course-desc-items").append(
            //         '<li>' +
            //             '<i class="fa fa-check"></i>' +
            //             '<span>' + t.title + '</span>' +
            //         '</li>');
            // });
            // $(".mb-4.font-weight-bold").html(data.course.promotionPrice);
            // $(".mb-4.font-weight-bold").append('<small>' + data.course.price + '</small>')
            // $("#txtDesc").append(data.course.desc);
            // $(".course-buy-info.mt-2").append('<small><i class="fa fa-play-circle-o"></i> ' +data.course.hourCount+ ' hours on-demand video</small>');
            // $(".course-buy-info.mt-2").append('<small><i class="fa fa-file-o"></i> ' +data.course.lectureCount+ ' articles</small>');
            // $(".course-buy-info.mt-2").append('<small><i class="fa fa-code"></i> ' +data.course.lectureCount+ ' coding exercises</small>');
            // $(".course-buy-info.mt-2").append('<small><i class="fa fa-empire"></i> Full lifetime access</small>');
            // $(".course-buy-info.mt-2").append('<small><i class="fa fa-tablet"></i> Access on mobile and TV</small>');
            // $(".course-buy-info.mt-2").append('<small><i class="fa fa-recycle"></i> Certificate of Completion</small>');
            // data.videos.map((v) => {
            //     $("#list-content").append(
            //         '<li>' +
            //         '<a href="'+v.url+'" class="btn-video" data-video-id="6xB-uXqbOqo">' +
            //             '<span> <i class="fa fa-play-circle mr-1"></i>' +
            //                 v.title +
            //             '</span>' +
            //             '<span>'+v.timeCount+'</span>' +
            //         '</a>' +
            //     '</li>');
            // });
        },
        error: () => {

        }
    });
};
