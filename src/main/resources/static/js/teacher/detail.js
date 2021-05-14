const urlParams = window.location.href;
const id = urlParams.substring((urlParams.lastIndexOf('/') + 1), urlParams.length);
let token = localStorage.getItem("elearning-token");
let file;
let obUrl;
let inputVideo = document.getElementById("input-video");
let duration;
detail(id);
//new FroalaEditor('textarea#froala-editor')

$("#video-file").change((event) => {
	let inputFile = document.getElementById("video-file");
	if (inputFile.files && inputFile.files[0]) {
		const reader = new FileReader();
		reader.onload = (e) => {
			e.target.result;
		};
		reader.readAsDataURL(inputFile.files[0]);
		
	}
	file = event.target.files[0]; 
		console.log(file);
		obUrl = URL.createObjectURL(file);
		console.log(obUrl);
		// inputVideo.append('<source src="' + obUrl + '" type="video/mp4">');
		inputVideo.setAttribute('src', obUrl);
});

inputVideo.onloadedmetadata = function(e) {
	duration = e.target.duration;
	console.log(e.target.duration);
	URL.revokeObjectURL(obUrl);
};

$('#btn-save-video').click(() => {
	let inputFile = document.getElementById("video-file");
		
	if (inputFile.files.length === 0) {
		alert("Vui lòng chọn file!");
		return;
	};
	let data = {
		courseId: id,
		title: $("#video-title").val(),
		timeCount: duration
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
			setTimeout(()=>{window.location.href = ('http://localhost:8080/teacher/detail/' + id)}, 5000);
		},
		error: (jqXhr, textStatus, errorThrown) => {
			console.log(errorThrown);
		}
	});
	
});

function detail(courseId) {
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
			location.replace("http://localhost:8080/teacher/login");
		}
	});

	$.ajax({
		crossDomain: true,
		type: 'GET',
		url: 'http://localhost:8080/api/teacher/course/detail/' + courseId,
		headers: {
			'Authorization': 'Bearer ' + token,
			'Content-Type': 'application/json',
		},
		dataType: 'text',
		success: (dataa) => {
			var data = JSON.parse(dataa);
			$(".banner-content").append('<h1>' + data.course.title + '</h1>' +
				'<h5>' +
				data.course.content +
				'</h5>' +
				'<h6 class="mt-3">' +
				'<span><i class="fa fa-user m-1"></i> Created by </span>' +
				'<a href="#" class="text-white font-weight-bold mr-4">' + data.user.fullname + '</a>' +
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
					'<li>'
					+ '<i class="fa fa-check"></i>'
					+ '<span>' 
					+ t.title
					+ ' <<small class="edit-target" onclick="openEditTargetModal(' + t.id + ')">Edit</small>' 
					+ " | "
					+ '<small class="edit-target" onclick="deleteTarget(' + t.id + ')">Delete</small>>'
					+ '</span>'
					+ '</li>');
				/*$("#right-course-desc-items").append(
					'<li>'
					+ '<div style="float:right; height:24;">'
					//+ '<span >'
					+ '<small class="edit-target" onclick="openEditTargetModal(' + t.id + ')">Edit</small>' 
					+ " | "
					//+ '</span>'
					//+ '<span>' 
					+ '<small class="edit-target" onclick="deleteTarget(' + t.id + ')">Delete</small>' 
					//+ '</span>' 
					+ '</div>'
					+ '</li>');*/
			});
			$("span.mr-3").append(data.course.lectureCount + ' lectures')
			$(".mb-4.font-weight-bold").html(data.course.promotionPrice + ' vnđ');
			$(".mb-4.font-weight-bold").append('<small>' + data.course.price + ' vnđ</small>')
			$("#txtDesc").append(data.course.desc);
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-play-circle-o"></i> ' + Math.round(data.course.hourCount / 60) + ':' + Math.round(data.course.hourCount % 60) + ' hours on-demand video</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-file-o"></i> ' + data.course.lectureCount + ' articles</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-code"></i> ' + data.course.lectureCount + ' coding exercises</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-empire"></i> Full lifetime access</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-tablet"></i> Access on mobile and TV</small><br>');
			$(".course-buy-info.mt-2").append('<small><i class="fa fa-recycle"></i> Certificate of Completion</small><br>');
			$("#course-list").append('<span>' + Math.round(data.course.hourCount / 60) + ':' + Math.round(data.course.hourCount % 60) + '</span>')
			data.videos.map((v) => {
				$("#list-content").append(
					'<li>' +
					'<a href="#" onclick="watchVideo(' + "'" + v.url + "'" + ')" class="btn-video" >' +
					'<span> <i class="fa fa-play-circle mr-1"></i>' +
					v.title +
					'</span>' +
					'<span>' + Math.round(v.timeCount / 60) + ':' + Math.round(v.timeCount % 60) + '</span>' +
					'</a>' +
					'</li>');
			});
		},
		error: () => {
			location.replace("http://localhost:8080/teacher/login");
		}
	});
};

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

function watchVideo(videoName) {
	if(videoName !== null) {
		console.log(videoName);
		$("#videoModal").modal();
		let video = document.getElementById("video-iframe");
		video.setAttribute("src", "http://localhost:8080/videos/" + videoName + ".mp4");
	} else {
		alert("Lỗi")
	}
}

$("#openAddVideoModal").click(() => {
	$("#addVideoModal").modal();
});

function logout() {
	localStorage.removeItem("elearning-token");
	location.reload();
}

$("#btnAddTarget").click(() => {
	let formData = new FormData(document.getElementById("formAddTarget"));
	let object = {};
	formData.append('courseId', id);
	formData.forEach((value, key) => object[key] = value);
	let json = JSON.stringify(object);
	console.log(json);
	console.log(JSON.parse(json));
	console.log(JSON.parse(json).id);
	if (JSON.parse(json).id == undefined || JSON.parse(json).id == null | JSON.parse(json).id == "") {
		$.ajax({
			crossDomain: true,
			type: 'POST',
			url: 'http://localhost:8080/api/teacher/target',
			data: json,
			headers: {
				'Authorization': 'Bearer ' + token,
				'Content-Type': 'application/json',
			},
			dataType: 'json',
			success: () => {
			},
			error: () => {
			}
		});
	} else {
		$.ajax({
			crossDomain: true,
			type: 'PUT',
			url: 'http://localhost:8080/api/teacher/target',
			data: json,
			headers: {
				'Authorization': 'Bearer ' + token,
				'Content-Type': 'application/json',
			},
			dataType: 'json',
			success: () => {
			},
			error: () => {
				console.log('fail');
			}
		});
	}
	window.location.href = ('http://localhost:8080/teacher/detail/' + id);
});

function second2duration(second){
	second
	Math.round(second / 60) + ':' + Math.round(second % 60)
}

// let element = convertHtmlToJQueryObject($("#addVideoModal").html());
// console.log(element);

// $("footer").append(element);
// function convertHtmlToJQueryObject(html){
//     var htmlDOMObject = new DOMParser().parseFromString(html, "text/html");
//     return $(htmlDOMObject.documentElement);
// }
