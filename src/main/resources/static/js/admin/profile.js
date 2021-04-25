token = "";
$(document).ready(function() {

	token = localStorage.getItem("elearning-token");
	if (token === null) {
		location.replace("http://localhost:8080/");
	}
	init();

});

function init() {

	$.ajax({
		url: "http://localhost:8080/api/admin/user/profile",
		type: "GET",
		dataType: "json",
		headers: {
			'Authorization': token
		},
		contentType: 'text/html',
		success: function(resp) {
			let profile = resp;
			$("#dropdownId").text(profile.fullname);
			$("#avatar-icon").find('div').text(profile.fullname);
			$("#avatar-icon").find('img').attr('src', profile.avatar);
			document.getElementById('fullname').setAttribute("value", profile.fullname);
			document.getElementById('email').setAttribute("value", profile.email);
			document.getElementById('phone').setAttribute("value", profile.phone);
			document.getElementById('address').setAttribute("value", profile.address);
			document.getElementById('imgAvatar').setAttribute("src", profile.avatar);
			document.getElementById('security_email').setAttribute("value", profile.email);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			if (jqXhr.status === 403) {
				location.replace("http://localhost:8080/405");
			}
		}
	});
}

function saveAvatar() {

	let avatarInput = document.getElementById("avatar");
	// KIỂM TRA XEM CHỌN HÌNH CHƯA
	if (avatarInput.files.length === 0) {
		alert("Vui lòng chọn file!");
		return;
	}

	// ADD FILE VÀO ĐỐI TƯỢNG FORMDATA
	let formData = new FormData();
	formData.append('file', avatarInput.files[0]);

	axios({
		url: 'http://localhost:8080/api/user/updateAvatar',
		method: 'POST',
		data: formData,
		headers: {
			'Content-Type': 'multipart/form-data',
			'Authorization': token,
		}
	}).then(function(resp) {
		let imageUrl = resp.data;
		// TRUY CẬP TỚI THẺ IMG
		let imgAvatar = document.getElementById('imgAvatar');
		// THAY ĐỔI GIÁ TRI CỦA THẺ SRC
		imgAvatar.setAttribute('src', imageUrl);
		$("#avatar-icon").find('img').attr('src', imageUrl);
		notification("success", "Thay đổi ảnh đại diện thành công");
	})
		.catch(function(err) {
			console.log(err)
		})

	/*$.ajax({
		url: "http://localhost:8080/api/user/updateAvatar",
		type: 'POST',
		processData: false,
		headers: {
			'Content-Type': 'multipart/form-data',
			'Authorization': token,
		},
		data: formData,
		success: function(resp) {
			let imageUrl = respS;
			// TRUY CẬP TỚI THẺ IMG
			let imgAvatar = document.getElementById('imgAvatar');
			// THAY ĐỔI GIÁ TRI CỦA THẺ SRC
			imgAvatar.setAttribute('src', imageUrl);
			$("#avatar-icon").find('img').attr('src', imageUrl);
			notification("success", "Thay đổi ảnh đại diện thành công");
		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});*/

}

$('#profile').submit(function(e) {
	e.preventDefault();
	var form = $('#profile');
	var data = form.serializeArray();
	var newdata = JSON.stringify(getFormData(data));

	console.log()
	$.ajax({
		url: "http://localhost:8080/api/user/update",
		dataType: 'json',
		type: 'PUT',
		headers: { "Authorization": token },
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			let resp = data;
			console.log(resp.token);
			if (resp.token != null) token = resp.token;
			init();
			notification("success", resp.message);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

})

function changePassword() {
	console.log("changepassword");
	let new_password = $("#new-password").val();
	let confirm_password = $("#confirm-password").val();

	if (new_password === "" || confirm_password === "") {
		notification("warning", "Chưa nhập mật khẩu mới!!!");
		return 0;
	}

	if (new_password !== confirm_password) {
		notification("warning", "Mật khẩu mới nhập lại không đúng!!!")
		return 0;
	}

	let old_password = $("#old-password").val();
	let newdata = {
		"email": $("#security_email").val(),
		"newPassword": new_password,
		"oldPassword": old_password
	}

	newdata = JSON.stringify(newdata);
	console.log(newdata);
	$.ajax({
		url: "http://localhost:8080/api/user/password",
		dataType: 'json',
		type: 'PUT',
		headers: { "Authorization": token },
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			console.log(data.message);
			switch (data.message) {
				case "0":
					notification("success", "Cập nhật thành công!!!");
					console.log(data.token);
					localStorage.setItem("elearning-token", data.token);
					init();
					break;
				case "1":
					notification("error", "Mật khẩu cũ không đúng!!!");
					break;
				case "2":
					notification("error", "Tài khoản không tồn tại");
					break;
				case "3":
					notification("error", "Mật khẩu mới và mật khẩu cũ không được trùng nhau");
					break;
				default:
					notification("error", "Cập nhật thất bại!!!");
			}

		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});
}

function getFormData(data) {
	var unindexed_array = data;
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	/*indexed_array.avatar = null;
	indexed_array.id = null;
	indexed_array.password = null;
	indexed_array.roleId = 0;
	indexed_array.roleDesc = null;
	indexed_array.roleName = null;*/

	return indexed_array;
}

function previewAvatar(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$('#imgAvatar').attr('src', e.target.result);
		}

		reader.readAsDataURL(input.files[0]); // convert to base64 string
	}
}

$("#avatar").change(function() {
	previewAvatar(this);
});

function logout() {
	localStorage.removeItem("elearning-token");
	location.replace("http://localhost:8080/");
}

/*function resizeImage() {
	let reader = new FileReader();
	reader.onload = function (readerEvent) {
		let image = new Image();
		image.onload = function(imageEvent) {
			//resize image
			let canvas = document.createElement('canvas'),
				max_size = 544,
				width = image.width,
				height = image.height;
			if (width > height){
				if (width > max_size){
					height *= max_size / width;
					width = max_size;
				}
			} else {
				if (height > max_size){
					width *= max_size / height;
					height = max_size;
				}
			}
			canvas.width = width;
			canvas.height = height;
			canvas.getContext('2d').drawImage(image, 0, 0, width, height);
			let dataUrl = canvas.toDataURL('image/jpeg');
			let resizedImage = dataURLToBlob(dataUrl);
		}
	}
}*/