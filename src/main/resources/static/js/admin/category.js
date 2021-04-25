elearning_token = ""
$(document).ready(function() {

	elearning_token = localStorage.getItem("elearning-token");

	if (elearning_token === null) {
		location.replace("http://localhost:8080/admin/page/login");
	}
	init();

});

function init() {
	$.ajax({
		url: "http://localhost:8080/api/admin/user/profile",
		dataType: 'json',
		type: 'GET',
		headers: { "Authorization": elearning_token },
		contentType: 'text/html',
		success: function(data) {
			$("#dropdownId").text(data.fullname);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			if (jqXhr.status === 403) {
				location.replace("http://localhost:8080/405");
			}
		}
	});
	$("#cate-table > tbody").remove();
	$.ajax({
		url: "http://localhost:8080/api/admin/category",
		dataType: 'json',
		type: 'GET',
		headers: { "Authorization": elearning_token },
		contentType: 'text/html',
		success: function(data, textStatus, jqXhr) {
			let selector = "#cate-table";
			buildHtmlTable(data, selector);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(jqXhr.responseText);
		}
	});
}

function buildHtmlTable(data, selector) {
	for (var i = 0; i < data.length; i++) {
		let content =
			"<tr>"
			+ "<td>" + data[i].id + "</td>"
			+ "<td>" + "<i class='fa " + data[i].icon + "'></i>" + "</td>"
			+ "<td>" + data[i].name + "</td>"
			+ "<td>"
			+ "<button class=\"btn btn-sm btn-info\" onclick=\"editTogleModal(" + data[i].id + ")\"><i class=\"fa fa-pencil-square-o\"></i></button> "
			+ "<button onclick=\"deleteCate(" + data[i].id + ")\"  class=\"btn btn-sm btn-danger\"><i class=\"fa fa-trash-o\"></i></button>"
			+ "</td>"
			+ "</tr>";
		$(selector).append(content);
	}
}

function editTogleModal(id) {
	$.ajax({
		url: "http://localhost:8080/api/admin/category/" + id,
		type: 'GET',
		dataType: 'json',
		headers: { "Authorization": admin_token },
		contentType: 'application/json',
		success: function(data) {
			$('#cate-name').val(data.name);
			$('#cate-icon').val(data.icon);
			$("#save-btn").attr("onclick", "edit(" + id + ")");
			$("#cateModal").modal();
		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});
}

function addTogleModal(id) {
	$('#cate-name').val("");
	$('#cate-icon').val("");
	$("#save-btn").attr("onclick", "add()");
	$("#cateModal").modal();
}

function edit(id) {
	let form = $('#cate-form');
	let data = getFormData(form.serializeArray());
	data.id = id;
	let newdata = JSON.stringify(data);
	console.log("edit " + newdata);
	$.ajax({
		url: "http://localhost:8080/api/admin/category/" + id,
		type: 'PUT',
		headers: { "Authorization": admin_token },
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			notification("success", "Chỉnh sửa thành công");
			init();
		},
		error: function(jqXhr, textStatus, errorThrown) {
			notification("success", "Chỉnh sửa thất bại");
			console.log(errorThrown);
		}
	});
}

function add() {
	let form = $('#cate-form');
	let data = getFormData(form.serializeArray());
	data.id = 0;
	let newdata = JSON.stringify(data);
	$.ajax({
		url: "http://localhost:8080/api/admin/category",
		type: 'POST',
		headers: { "Authorization": admin_token },
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			notification("success", "Thêm mới thành công");
			init();
		},
		error: function() {
			notification("success", "Thêm mới thất bại");
			console.log("loi");
		}
	});
}

function deleteCate(id) {
	console.log("delete function");
	$.ajax({
		url: "http://localhost:8080/api/admin/category/" + id,
		type: 'DELETE',
		headers: { "Authorization": admin_token },
		contentType: 'application/json',
		success: function(data) {
			init();
			notification("success", "Đã xoá");
		},
		error: function(jqXhr, textStatus, errorThrown) {
			notification("Error", "Xoá không thành công")
		}
	});
}

function getFormData(data) {
	var unindexed_array = data;
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}