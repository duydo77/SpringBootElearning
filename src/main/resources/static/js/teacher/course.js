console.log(localStorage.token);
$(document).ready(() => {
    $("section.course-content").hide();
    $("section.course-banner").show();
    $("section.mt-5").show();
    init();
})

$("#btn-create-course").click(() => {
    $("#create-course-modal").modal();

});

$("#btn-save-course").click(() => {
    let addForm = $("#add-course-form");
    let data = getFormData(addForm.serializeArray());
    console.log(data);
    data.id = 0
    let newData = JSON.stringify(data)
    console.log(newData);
    $.ajax({
		url: "http://localhost:8080/api/teacher/course",
		type: 'POST',
		headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
		contentType: 'application/json',
		data: newData,
		success: () => {
            notification("success", "Thêm thành công");
			init();
		},
		error: () => {
            notification("success", "Thêm thất bại");
		}
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
            // window.location.href = "http://localhost:8080/teacher/detail";
            // console.log(data);
            $("section.course-banner").hide();
            $("section.mt-5").hide();
            $("section.course-content").show();
            var data = JSON.parse(dataa);
            data.targets.map((t) => {
                $("#left-course-desc-items").append(
                    '<li>' +
                        '<i class="fa fa-check"></i>' +
                        '<span>' + t.title + '</span>' +
                    '</li>');
            });
            $(".mb-4.font-weight-bold").html(data.course.promotionPrice);
            $(".mb-4.font-weight-bold").append('<small>' + data.course.price + '</small>')
            $("#txtDesc").append(data.course.desc);
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-play-circle-o"></i> ' +data.course.hourCount+ ' hours on-demand video</small>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-file-o"></i> ' +data.course.lectureCount+ ' articles</small>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-code"></i> ' +data.course.lectureCount+ ' coding exercises</small>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-empire"></i> Full lifetime access</small>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-tablet"></i> Access on mobile and TV</small>');
            $(".course-buy-info.mt-2").append('<small><i class="fa fa-recycle"></i> Certificate of Completion</small>');
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

function init() {
    $.ajax({
    	crossDomain: true,
        type: 'GET',
        url: 'http://localhost:8080/api/teacher/course/ofteacher',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json',
        },
        dataType: 'text',
        success : (data1) => {
            var data2 = JSON.parse(data1);
            data2.map((d) => {
                $('section.mt-5 .container .row').append('<div class="col-md-3">' +
                '<a href="#" class="my-course-item" onclick="detail('+d.id+')">' +
                    '<img src="../img/html-css.jpg" alt="">' +
                    '<h6 class="my-course-title">'+d.title+'</h6>' +
                    '<div class="my-course-desc">' +
                        d.desc +
                    '</div>' +
                    '<div class="my-course-author">' +
                        '<h6>' +
                            '<small>Lê Quang Song</small>' +
                            '<small>Start course</small>' +
                        '</h6>' +
                    '</div>' +
                '</a>' +
            '</div> ');
            }) 
        },
        error: () => {

        }
    });
};

function getFormData(data) {
    var unindexed_array = data;
    var indexed_array = {};

    $.map(unindexed_array, function(n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}
