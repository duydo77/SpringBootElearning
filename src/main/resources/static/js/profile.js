//token = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QGdtYWlsLmNvbSIsImlhdCI6MTYxODQwOTEwMSwiZXhwIjoxNjE5MjczMTAxfQ.kxbie4dxXKHlqnf3qGsALGmiIDgV9PnkyoO3yID3Sk3YAcYahC9jbA1De5UEFhnxS6BeS4Kj868Z5RxKZ4avoA';
elearnitoken = "";
$(document).ready(function() {

	token = localStorage.getItem("elearning-token");
	if (token === null) {
		location.replace("http://localhost:8080/");
	}
	init();

});

function init() {

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

	console.log(token);
	/*axios({
		url: 'http://localhost:8080/api/user/profile',
		method: 'GET',
		
		headers: {
			'Authorization': token
		}
	})
		.then(function(resp) {
			let profile = resp.data;
			$("#avatar-icon").find('div').text(profile.fullname);
			$("#avatar-icon").find('img').attr('src', profile.avatar);
			document.getElementById('fullname').setAttribute("value", profile.fullname);
			document.getElementById('email').setAttribute("value", profile.email);
			document.getElementById('phone').setAttribute("value", profile.phone);
			document.getElementById('address').setAttribute("value", profile.address);
			document.getElementById('imgAvatar').setAttribute("src", profile.avatar);
			document.getElementById('security_email').setAttribute("value", profile.email);
			document.getElementById('banner_email').innerHTML = profile.email;
			document.getElementById('banner_fullname').innerHTML = profile.fullname;
		})
		.catch(function(err) {
			console.log(err)
		})*/

	$.ajax({
		url: "http://localhost:8080/api/user/profile",
		type: "GET",
		dataType: "json",
		headers: {
			'Authorization': token
		},
		contentType: 'text/html',
		success: function(resp) {
			let profile = resp;
			$("#avatar-icon").find('div').text(profile.fullname);
			$("#avatar-icon").find('img').attr('src', profile.avatar);
			document.getElementById('fullname').setAttribute("value", profile.fullname);
			document.getElementById('email').setAttribute("value", profile.email);
			document.getElementById('phone').setAttribute("value", profile.phone);
			document.getElementById('address').setAttribute("value", profile.address);
			document.getElementById('imgAvatar').setAttribute("src", profile.avatar);
			document.getElementById('security_email').setAttribute("value", profile.email);
			document.getElementById('banner_email').innerHTML = profile.email;
			document.getElementById('banner_fullname').innerHTML = profile.fullname;
		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(jqXhr.responseText);
		}
	});
}

function saveAvatar() {

	let avatarInput = document.getElementById("avatar");
	// KI???M TRA XEM CH???N H??NH CH??A
	if (avatarInput.files.length === 0) {
		alert("Vui l??ng ch???n file!");
		return;
	}

	// ADD FILE V??O ?????I T?????NG FORMDATA
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
		// TRUY C???P T???I TH??? IMG
		let imgAvatar = document.getElementById('imgAvatar');
		// THAY ?????I GI?? TRI C???A TH??? SRC
		imgAvatar.setAttribute('src', imageUrl);
		$("#avatar-icon").find('img').attr('src', imageUrl);
		notification("success", "Thay ?????i ???nh ?????i di???n th??nh c??ng");
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
			// TRUY C???P T???I TH??? IMG
			let imgAvatar = document.getElementById('imgAvatar');
			// THAY ?????I GI?? TRI C???A TH??? SRC
			imgAvatar.setAttribute('src', imageUrl);
			$("#avatar-icon").find('img').attr('src', imageUrl);
			notification("success", "Thay ?????i ???nh ?????i di???n th??nh c??ng");
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
		notification("warning", "Ch??a nh???p m???t kh???u m???i!!!");
		return 0;
	}

	if (new_password !== confirm_password) {
		notification("warning", "M???t kh???u m???i nh???p l???i kh??ng ????ng!!!")
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
					notification("success", "C???p nh???t th??nh c??ng!!!");
					console.log(data.token);
					localStorage.setItem("elearning-token", data.token);
					init();
					break;
				case "1":
					notification("error", "M???t kh???u c?? kh??ng ????ng!!!");
					break;
				case "2":
					notification("error", "T??i kho???n kh??ng t???n t???i");
					break;
				case "3":
					notification("error", "M???t kh???u m???i v?? m???t kh???u c?? kh??ng ???????c tr??ng nhau");
					break;
				default:
					notification("error", "C???p nh???t th???t b???i!!!");
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