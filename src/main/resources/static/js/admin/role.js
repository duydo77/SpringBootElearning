elearning_token = ""
login_path = "http://localhost:8080/admin/page/login";
$(document).ready(function() {
	
	elearning_token = localStorage.getItem("elearning-token");

	if (elearning_token === null){
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
				location.replace("http://localhost:8080/admin/page/login");
			}
		}
	});
	$("#role-table > tbody").remove();
	$.ajax({
		url: "http://localhost:8080/api/admin/role",
		dataType: 'json',
		type: 'GET',
		headers: { "Authorization": elearning_token},
		contentType: 'text/html',
		success: function(data, textStatus, jqXhr) {
			let selector = "#role-table";
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
			+ "<td>" + data[i].name + "</td>"
			+ "<td>" + data[i].desc + "</td>"
			+ "<td>"
			+ "<button class=\"btn btn-sm btn-info\" onclick=\"editTogleModal(" + data[i].id + ")\"><i class=\"fa fa-pencil-square-o\"></i></button> "
			+ "<button onclick=\"deleteRole(" + data[i].id + ")\"  class=\"btn btn-sm btn-danger\"><i class=\"fa fa-trash-o\"></i></button>"
			+ "</td>"
			+ "</tr>";
		$(selector).append(content);
	}
}

function editTogleModal(id) {
	$.ajax({
		url: "http://localhost:8080/api/admin/role/" + id,
		type: 'GET',
		dataType: 'json',
		headers: { "Authorization": elearning_token },
		contentType: 'application/json',
		success: function(data) {
			$('#role-name').val(data.name);
			$('#role-description').val(data.desc);
			$("#save-btn").attr("onclick", "editRole(" + id + ")");
			$("#roleModal").modal();
		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});
}

function addTogleModal(id) {
	$('#role-name').val("");
	$('#role-description').val("");
	$("#save-btn").attr("onclick", "addRole()");
	$("#roleModal").modal();
}

function editRole(id) {
	let form = $('#role-form');
	let data = getFormData(form.serializeArray());
	data.id = id;
	let newdata = JSON.stringify(data);
	console.log("edit " + newdata);
	$.ajax({
		url: "http://localhost:8080/api/admin/role/" + id,
		type: 'PUT',
		headers: { "Authorization": elearning_token },
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

function addRole() {
	let form = $('#role-form');
	let data = getFormData(form.serializeArray());
	data.id = 0;
	let newdata = JSON.stringify(data);
	$.ajax({
		url: "http://localhost:8080/api/admin/role",
		type: 'POST',
		headers: { "Authorization": elearning_token },
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

function deleteRole(id) {
	console.log("delete function");
	$.ajax({
		url: "http://localhost:8080/api/admin/role/" + id,
		type: 'DELETE',
		headers: { "Authorization": elearning_token },
		contentType: 'application/json',
		success: function(data) {
			init();
			notification("success","Đã xoá");
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