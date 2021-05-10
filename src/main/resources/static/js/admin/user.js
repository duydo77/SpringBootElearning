elearning_token = "";
login_path = "http://localhost:8080/admin/page/login";
$(document).ready(function() {

	elearning_token = localStorage.getItem("elearning-token");

	if (elearning_token === null || elearning_token === "") {
		location.replace("http://localhost:8080/admin/page/login");
	}

	$.ajax({
		url: "http://localhost:8080/api/admin/role",
		dataType: 'json',
		type: 'GET',
		headers: { "Authorization": elearning_token },
		contentType: 'text/html',
		success: function(data) {
			let selector = "#select-role";

			for (var i = 0; i < data.length; i++) {
				let content = "<option value='" + data[i].id + "'>" + data[i].desc + "</option>";
				$(selector).append(content);
			}

		},
		error: function(jqXhr, textStatus, errorThrown) {
			if (jqXhr.status === 403) {
				location.replace(login_path);
			}
		}
	});

	init();

});

function init() {
	$("#user-table > tbody").remove();
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
	$.ajax({
		url: "http://localhost:8080/api/admin/user",
		dataType: 'json',
		type: 'GET',
		headers: { "Authorization": elearning_token },
		contentType: 'text/html',
		success: function(data, textStatus, jqXhr) {
			console.log("jaja");
			let selector = "#user-table";
			buildHtmlTable(data, selector);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			notification("Error", "Load dữ liệu thất bại");
		}
	});
}

function buildHtmlTable(data, selector) {
	for (var i = 0; i < data.length; i++) {
		let content =
			"<tr>"
			+ "<td>" + data[i].id + "</td>"
			+ "<td>" + data[i].email + "</td>"
			+ "<td>" + data[i].fullname + "</td>"
			+ "<td>" + data[i].phone + "</td>"
			+ "<td>" + data[i].address + "</td>"
			+ "<td>" + data[i].roleDesc + "</td>"
			+ "<td>"
			+ "<button class=\"btn btn-sm btn-info\" onclick=\"editTogleModal(" + data[i].id + ")\"><i class=\"fa fa-pencil-square-o\"></i></button> "
			+ "<button onclick=\"deleteUser(" + data[i].id + ")\"  class=\"btn btn-sm btn-danger\"><i class=\"fa fa-trash-o\"></i></button>"
			+ "</td>"
			+ "</tr>";
		$(selector).append(content);
	}
}

function editTogleModal(id) {
	$.ajax({
		url: "http://localhost:8080/api/admin/user/" + id,
		type: 'GET',
		dataType: 'json',
		headers: { "Authorization": elearning_token },
		contentType: 'application/json',
		success: function(data) {
			$("#modal-name").text("Chỉnh sửa thông tin người dùng");
			$('#message').text("");
			$('#user-fullname').val(data.fullname);
			$('#user-email').val(data.email);
			$('#user-phone').val(data.phone);
			$('#user-password').val("");
			$('#user-address').val(data.address);
			$('#select-role').val(data.roleId);
			$("#save-btn").attr("onclick", "edit(" + id + ")");
			$("#userModal").modal();
		},
		error: function(jqXhr, textStatus, errorThrown) {
			notification("error", "Chỉnh sửa thất bại");
			console.log(errorThrown);
		}
	});
}

function addTogleModal(id) {
	$("#modal-name").text("Thêm mới người dùng");
	$("#message").text("");
	$('#user-fullname').val("");
	$('#user-email').val("");
	$('#user-phone').val("");
	$('#user-address').val("");
	$('#user-password').val("");
	$("#select-role option[value=1]").attr("selected", false);
	$("#save-btn").attr("onclick", "add()");
	$("#userModal").modal();
}

function edit(id) {
	let form = $('#user-form');
	let data = getFormData(form.serializeArray());
	data.id = id;
	data.roleName = "";
	data.roleDesc = "";
	let newdata = JSON.stringify(data);
	console.log("edit " + newdata);
	$.ajax({
		url: "http://localhost:8080/api/admin/user/" + id,
		type: 'PUT',
		headers: { "Authorization": elearning_token },
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			console.log(data);
			if (data === "SUCCESSED") {
				$("#userModal").modal('toggle');
				notification("success", "Chỉnh sửa thành công");
				init();
			} else {
				notification("error", data);
			}
		},
		error: function(jqXhr, textStatus, errorThrown) {
			notification("success", "Chỉnh sửa thất bại");
			console.log(errorThrown);
		}
	});
}

function add() {
	let form = $('#user-form');
	let data = getFormData(form.serializeArray());
	data.id = 0;
	data.roleName = "";
	data.roleDesc = "";
	if (data.email === "" || data.password === "") {
		notification("error", "Email và password không được để trống");
		return;
	}

	let newdata = JSON.stringify(data);
	console.log(newdata)
	$.ajax({
		url: "http://localhost:8080/api/admin/user",
		type: 'POST',
		headers: { "Authorization": elearning_token },
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			console.log(data);
			switch (data) {
				case "0":
					$("#userModal").modal('toggle');
					notification("success", "Thêm mới thành công");
					init();
					break;
				case "1":
					notification("error", "Chưa nhập eamail hoặc mật khẩu");
					break;
				case "2":
					notification("error", "Tài khoản đã tồn tại");
					break;
				default:
					notification("error", "Thêm mới thất bại");
			}

		},
		error: function(data) {
			notification("error", "Thêm mới thất bại");
			console.log(data);
		}
	});
}

function deleteUser(id) {
	console.log("delete function");
	$.ajax({
		url: "http://localhost:8080/api/admin/user/" + id,
		type: 'DELETE',
		headers: { "Authorization": elearning_token },
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

function logout() {
	localStorage.removeItem("elearning-token");
	location.reload();
}

window.onunload = () => {
	// Clear the local storage
	window.MyStorage.clear()
}

$('#search').keyup(function() {
	search_table($(this).val());
});

function search_table(value) {
	$('#user-table tbody tr').each(function() {
		let found = 'false';
		$(this).each(function () {
			if ($(this).text().toLowerCase().indexOf(value.toLowerCase()) >= 0) {
				found = 'true';
			}
		});
		if (found == 'true') {
			$(this).show();
		}else{
			$(this).hide();
		}
	})
}