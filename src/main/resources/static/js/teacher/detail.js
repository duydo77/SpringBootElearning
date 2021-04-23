const urlParams = window.location.href;
console.log(urlParams.substring((urlParams.lastIndexOf('/') + 1), urlParams.length));
const id = urlParams.substring((urlParams.lastIndexOf('/') + 1), urlParams.length); 
$(document).ready(() => {
    const inputFile = document.getElementById("video-file");
    let base64File = '';

    detail(id);

    inputFile.addEventListener('change', (e) => {
        const file = e.target.files[0];
        console.log('file: ' + file);
        
        if(file != undefined) {
            const reader = new FileReader();
            reader.onload = (e) => {
                base64File = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    $('#btn-save-video').click(() => {
        let uploadVideoData = {
            title : $("#video-title").val(),
            videoAsBase64: base64File
        }
        console.log(uploadVideoData);
        let data = JSON.stringify(uploadVideoData);
        console.log(data)
        $.ajax({
        	url: 'http://localhost:8080/api/teacher/video/base64',
        	type: 'POST',
        	dataType: 'json',
            data: data,
        	headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') },
        	contentType: 'application/json',
        	success: (data) => {
                
            },
        	error: () => {
        	}
        });
    });
    
});

function detail(courseId) {
    $.ajax({
    	crossDomain: true,
        type: 'GET',
        url: 'http://localhost:8080/api/teacher/course/detail/' + courseId,
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json',
        },
        dataType: 'text',
        success : (dataa) => {
            // $("section.course-banner").hide();
            // $("section.mt-5").hide();
            // $("section.course-content").show();
            var data = JSON.parse(dataa);
            $(".banner-content").append('<h1>'+data.course.title+'</h1>' +
                                        '<h5>' +
                                            data.course.content +
                                        '</h5>' +
                                        '<h6 class="mt-3">' +
                                            '<span><i class="fa fa-user m-1"></i> Created by </span>' +
                                            '<a href="#" class="text-white font-weight-bold mr-4">'+data.user.fullname+'</a>' +
                                            '<span><i class="fa fa-calendar-check-o mr-1"></i> Last updated 04/2019</span>' +
                                        '</h6>' +
                                        '<h6 class="mt-3">' +
                                            '<span><i class="fa fa-play-circle mr-1"></i> '+data.course.lectureCount+' lectures</span>' +
                                            '<span class="mx-1"> | </span>' +
                                            '<span><i class="fa fa-clock-o mr-1"></i> '+data.course.hourCount+' hours</span>' +
                                            '<span class="ml-2">with <b class="mx-1">568,171</b> students enrolled</span>' +
                                        '</h6>');
            data.targets.map((t) => {
                $("#left-course-desc-items").append(
                    '<li>' +
                        '<i class="fa fa-check"></i>' +
                        '<span>' + t.title + '</span>' +
                        '<span class="mr"><button class="btn btn-outline-secondary" onclick="openEditTargetModal('+t.id+')">Edit</button></span>' +
                        '<span class="mr"><button class="btn btn-outline-secondary" onclick="deleteTarget('+t.id+')">Delete</button></span>' +
                    '</li>');
            });
            $("span.mr-3").append(data.course.lectureCount + ' lectures')
            $(".mb-4.font-weight-bold").html(data.course.promotionPrice);
            $(".mb-4.font-weight-bold").append('<small>' + data.course.price + '</small>')
            $("#txtDesc").append(data.course.desc);
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-play-circle-o"></i> ' +data.course.hourCount+ ' hours on-demand video</small><br>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-file-o"></i> ' +data.course.lectureCount+ ' articles</small><br>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-code"></i> ' +data.course.lectureCount+ ' coding exercises</small><br>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-empire"></i> Full lifetime access</small><br>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-tablet"></i> Access on mobile and TV</small><br>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-recycle"></i> Certificate of Completion</small><br>');
            data.videos.map((v) => {
                $("#list-content").append(
                    '<li>' +
                    '<a href="'+v.url+'" class="btn-video" data-video-id="6xB-uXqbOqo">' +
                        '<span> <i class="fa fa-play-circle mr-1"></i>' +
                            v.title +
                        '</span>' +
                        '<span>'+v.timeCount+'</span>' +
                    '</a>' +
                '</li>');
            });
        },
        error: () => {

        }
    });
};

function openEditTargetModal(targetId) {
    $.ajax({
		url: 'http://localhost:8080/api/teacher/target/' + targetId,
		type: 'GET',
		dataType: 'json',
		headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') },
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
		headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') },
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

$("#btnAddTarget").click(() => {
    let formData = new FormData(document.getElementById("formAddTarget"));
    let object = {};
    formData.append('courseId', id);
    formData.forEach((value, key) => object[key] = value);
    let json = JSON.stringify(object);
    console.log(json);
    console.log(JSON.parse(json));
    console.log(JSON.parse(json).id);
    if(JSON.parse(json).id == undefined || JSON.parse(json).id == null | JSON.parse(json).id == "") {
        $.ajax({
            crossDomain: true,
            type: 'POST',
            url: 'http://localhost:8080/api/teacher/target',
            data: json,
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
                'Content-Type': 'application/json',
            },
            dataType: 'json',
            success : () => {
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
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
                'Content-Type': 'application/json',
            },
            dataType: 'json',
            success : () => {
            },
            error: () => {
                console.log('fail');
            }
        });
    }
    window.location.href = ('http://localhost:8080/teacher/detail/' + id);
});

$("#openAddVideoModal").click(() => {
    $("#addVideoModal").modal();
});



