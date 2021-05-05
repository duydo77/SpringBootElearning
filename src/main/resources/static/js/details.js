const urlParams = window.location.href;
console.log(urlParams.substring((urlParams.lastIndexOf('/') + 1), urlParams.length));
const id = urlParams.substring((urlParams.lastIndexOf('/') + 1), urlParams.length);

let token = localStorage.getItem("elearning-token");

detail(id);
init();

function init() {
	if (token !== null || token === "") {
		$.ajax({
			url: "http://localhost:8080/api/user/profile",
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
					+ "<a class='dropdown-item' href='http://localhost:8080/profile'>Thông tin cá nhân</a>"
					+ "<a class='dropdown-item' href='http://localhost:8080/mycourse'>Khóa học của tôi</a>"
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

}


function detail(courseId) {
	$.ajax({
		crossDomain: true,
		type: 'GET',
		url: 'http://localhost:8080/api/course/' + courseId,
		headers: {
			'Content-Type': 'application/json',
		},
		dataType: 'text',
		success: (dataa) => {

			var data = JSON.parse(dataa);
			console.log(data)
			console.log(JSON.stringify(data));
			$(".banner-content").append('<h1>' + data.course.title + '</h1>' +
				'<h5>' +
				data.course.content +
				'</h5>' +
				'<h6 class="mt-3">' +
				'<span><i class="fa fa-user m-1"></i> Created by </span>' +
				'<a href="#" class="text-white font-weight-bold mr-4">' + data.course.teacherName + '</a>' +
				'<span><i class="fa fa-calendar-check-o mr-1"></i> Last updated ' + String(data.course.lastUpdate).substring(0,10) + '</span>' +
				'</h6>' +
				'<h6 class="mt-3">' +
				'<span><i class="fa fa-play-circle mr-1"></i> ' + data.course.lectureCount + ' lectures</span>' +
				'<span class="mx-1"> | </span>' +
				'<span><i class="fa fa-clock-o mr-1"></i> ' + data.course.hourCount + ' hours</span>' +
				'<span class="ml-2">with <b class="mx-1">' + data.course.viewCount + '</b> students enrolled</span>' +
				'</h6>');
			data.targets.map((t) => {
				$("#left-course-desc-items").append(
					'<li>' +
					'<i class="fa fa-check"></i>' +
					'<span>' + t.title + '</span>' +
					'</li>');
			});
			$("span.mr-3").append(data.course.lectureCount + ' lectures')
			$(".mb-4.font-weight-bold").html(data.course.promotionPrice);
			$(".mb-4.font-weight-bold").append('<small>' + data.course.price + '</small>')
			$("#txtDesc").append(data.course.desc);
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-play-circle-o"></i> ' + data.course.hourCount + ' hours on-demand video</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-file-o"></i> ' + data.course.lectureCount + ' articles</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-code"></i> ' + data.course.lectureCount + ' coding exercises</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-empire"></i> Full lifetime access</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-tablet"></i> Access on mobile and TV</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-recycle"></i> Certificate of Completion</small><br>');
			data.videos.map((v) => {
				$("#list-content").append(
					'<li>' +
					'<a href="#" onclick="watchVideo(' + "'" + v.url + "'" + ')" class="btn-video" >' +
					'<span> <i class="fa fa-play-circle mr-1"></i>' +
					v.title +
					'</span>' +
					'<span>' + v.timeCount + '</span>' +
					'</a>' +
					'</li>');
			});
		},
		error: () => {

		}
	});
};

function logout() {
	localStorage.removeItem("elearning-token");
	location.reload();
}

$("#video-file").change(() => {
	let inputFile = document.getElementById("video-file");
	if (inputFile.files && inputFile.files[0]) {
		const reader = new FileReader();
		reader.onload = (e) => {
			e.target.result;
		};
		reader.readAsDataURL(inputFile.files[0]);
	}
});

$('#btn-save-video').click(() => {
	let inputFile = document.getElementById("video-file");

	if (inputFile.files.length === 0) {
		alert("Vui lòng chọn file!");
		return;
	};
	let data = {
		courseId: id,
		title: $("#video-title").val()
	};

	data = JSON.stringify(data);

	$.ajax({
		url: 'http://localhost:8080/api/teacher/video',
		type: 'POST',
		dataType: 'json',
		data: data,
		headers: { 'Authorization': 'Bearer ' + token },
		contentType: 'application/json',
		success: (videoId) => {
			console.log(videoId);
			let formData = new FormData();

			formData.append('file', inputFile.files[0]);
			console.log(formData);
			console.log(formData.get('file'))
			axios({
				url: 'http://localhost:8080/api/teacher/video/file/' + videoId,
				method: 'POST',
				data: formData,
				headers: {
					'Authorization': 'Bearer ' + token,
					'Content-Type': 'multipart/form-data'
				}
			})
				.then(() => { })
				.catch((jqXhr, textStatus, errorThrown) => {
					console.log(errorThrown);
				});
			window.location.href = ('http://localhost:8080/teacher/detail/' + id);
		},
		error: (jqXhr, textStatus, errorThrown) => {
			console.log(errorThrown);
		}
	});
});

function openEditTargetModal(targetId) {
	$.ajax({
		url: 'http://localhost:8080/api/teacher/target/' + targetId,
		type: 'GET',
		dataType: 'json',
		headers: { 'Authorization': 'Bearer ' + token },
		contentType: 'application/json',
		success: (data) => {
			$("#target-title").val(data.title);
			$("#target-id").val(data.id);
			$("#target-modal-title").val('Edit target')
			$("#addTargetModal").modal();
		},
		error: () => {
		}
	});
};

function deleteTarget(targetId) {
	$.ajax({
		url: 'http://localhost:8080/api/teacher/target/' + targetId,
		type: 'DELETE',
		dataType: 'json',
		headers: { 'Authorization': 'Bearer ' + token },
		contentType: 'application/json',
		success: () => {

		},
		error: () => {
		}
	});
	window.location.href = ('http://localhost:8080/teacher/detail/' + id);
};

$("#btnOpenAddTargetModal").click(() => {
	$("#addTargetModal").modal();
});



$("#openAddVideoModal").click(() => {
	$("#addVideoModal").modal();
});

function watchVideo(videoName) {
	console.log(videoName);
	$("#videoModal").modal();
	let video = document.getElementById("video-iframe");
	video.setAttribute("src", "http://localhost:8080/videos/" + videoName + ".mp4");
	// axios({
	//     url: 'http://localhost:8080/api/teacher/video/video/'+ videoName,
	//     method: 'GET',
	//     headers: { 
	//         'Authorization': 'Bearer ' + token ,
	//     }
	// })
	// .then((data) => {
	//     let video = document.getElementById("watch-video-modal");
	//     video.setAttribute('src', data);
	//     // $("#watch-video-modal").(data);
	// })
	// .catch((jqXhr, textStatus, errorThrown) => {
	//     console.log(errorThrown);
	// });
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
				location.reload();
			}
			console.log(localStorage.getItem("elearning-token"));
		},
		error: function(jqXhr, textStatus, errorThrown) {
			$('#message').text("email hoặc mật khẩu không đúng");
		}
	});
}

function register() {
	$("#rg-message").text("")
	let rg_email = $("#rg-email").val();
	let rg_fullname = $("#rg-fullname").val();
	let rg_password = $("#rg-password").val();
	let rg_confim = $("#rg-confirm").val();
	
	console.log(rg_password !== rg_confim);
	
	if (rg_email === ""){
		$("#rg-message").text("Chưa nhập email!!!");
		return 0;
	}
	if (rg_fullname === ""){
		$("#rg-message").text("Chưa nhập fullname!!!");
		return 0;
	}
	if (rg_password === ""){
		$("#rg-message").text("Chưa nhập mật khẩu!!!");
		return 0;
	}
	if (rg_password !== rg_confim){
		$("#rg-message").text("Nhập lại mật khẩu không đúng!!!");
		return 0;
	}
	
	let newdata = {
		"fullname" : rg_fullname,
		"email" : rg_email,
		"password" : rg_password	
	}
	
	newdata = JSON.stringify(newdata);
	
	console.log(newdata);
	
	$.ajax({
		url: "http://localhost:8080/api/auth/register",
		type: 'POST',
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			switch (data.message){
				case "0":
					$("#rg-message").text("");
					localStorage.setItem("elearning-token", data.token);
					location.reload();
					break;
				case "1":
					$("#rg-message").text("Chưa nhập đủ thông tin!!!");
					break;
				case "2":
					$("#rg-message").text("Tài khoản đã tồn tại!!!");
					break;
				default:
					$("#rg-message").text("Đăng ký thất bại!!!");	
			}
		},
		error: function(jqXhr, textStatus, errorThrown) {
			$("#rg-message").text("Đăng ký thất bại haha !!!");
		}
	});
}