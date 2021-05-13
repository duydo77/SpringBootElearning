const urlParams = window.location.href;
console.log(urlParams.substring((urlParams.lastIndexOf('/') + 1), urlParams.length));
const id = urlParams.substring((urlParams.lastIndexOf('/') + 1), urlParams.length);

let token = localStorage.getItem("elearning-token");
let listcart = localStorage.getItem("list-cart");

let test = JSON.parse(listcart);
test.forEach(element => {
	console.log(element);
});
if (!listcart) {
	let cartList = [];
	localStorage.setItem("list-cart", JSON.stringify(cartList));
}

if (token != null) {
	detailAndCheck(id);
} else {
	detail(id);
}

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

			let data = JSON.parse(dataa);
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
			$(".course-buy").append('<button onclick="addToCart(' + data.course.id + ')" class="btn btn-danger w-100">Add to cart</button>');
			$(".course-buy").append('<button class="btn btn-outline-secondary w-100">Buy now</button>');
			// if(data.bought == false) {
			// 	$(".course-buy").append('<button onclick="addToCart(' + data.course.id + ')" class="btn btn-danger w-100">Add to cart</button>');
			// 	$(".course-buy").append('<button class="btn btn-outline-secondary w-100">Buy now</button>');
			// } else {
			// 	$(".course-buy").append('<button class="btn btn-danger w-100">Bought</button>');
			// }
			
			$(".course-buy").append('<div class="course-buy-info mt-2"> ' +
											'<span>This course includes</span>	 ' +											
											'</div> ' +
											'<a class="course-buy-share border-top" href="#"> ' +
												'<i class="fa fa-share"></i> Share ' +
											'</a>');
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

function detailAndCheck(courseId) {
	$.ajax({
		crossDomain: true,
		type: 'GET',
		url: 'http://localhost:8080/api/course/check/' + courseId,
		headers: {
			'Authorization': 'Bearer ' + token,
			'Content-Type': 'application/json',
		},
		dataType: 'text',
		success: (dataa) => {

			let data = JSON.parse(dataa);
			console.log(data.bought);
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
			// $(".course-buy").append('<button onclick="addToCart(' + data.course.id + ')" class="btn btn-danger w-100">Add to cart</button>');
			// $(".course-buy").append('<button class="btn btn-outline-secondary w-100">Buy now</button>');
			if(data.bought == false) {
				$(".course-buy").append('<button onclick="addToCart(' + data.course.id + ')" class="btn btn-danger w-100">Add to cart</button>');
				$(".course-buy").append('<button class="btn btn-outline-secondary w-100">Buy now</button>');
			} else {
				$(".course-buy").append('<button class="btn btn-danger w-100">Bought</button>');
			}
			
			$(".course-buy").append('<div class="course-buy-info mt-2"> ' +
									'<span>This course includes</span>	 ' +											
									'</div> ' +
									'<a class="course-buy-share border-top" href="#"> ' +
									'<i class="fa fa-share"></i> Share ' +
									'</a>');
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
}

function addToCart(courseId) {

	let data = JSON.parse(listcart);
	let flag = true;

	data.forEach(element => {
		if(element.id == courseId) {
			flag = false;
		}
	});	

	if(flag == true) {
		$.ajax({
			url: 'http://localhost:8080/api/course/' + courseId,
			type: 'GET',
			contentType: 'application/json',
			success: (data) => {
				
				let course = {
					"id": data.course.id,
					"image": "hinh1.jpg",
					"title": data.course.title,
					"teacherName": data.course.teacherName,
					"price": data.course.price,
					"promotionPrice": data.course.promotionPrice
				}
				console.log(course);
				if (localStorage.getItem('list-cart') == '') {
					let cartList = [];
					cartList.push(course);
					localStorage.setItem('list-cart', JSON.stringify(cartList));
				} else {
					console.log('local storage' + localStorage.getItem('list-cart'));
					let cartList = JSON.parse(localStorage.getItem('list-cart'));
					cartList.push(course);
					localStorage.setItem('list-cart', JSON.stringify(cartList));
				}
				listcart = localStorage.getItem("list-cart");
			},
			error: () => {
			}
		});
	} else {
		console.log('da ton tai');
	}
}