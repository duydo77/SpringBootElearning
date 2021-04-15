token = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QGdtYWlsLmNvbSIsImlhdCI6MTYxODQwOTEwMSwiZXhwIjoxNjE5MjczMTAxfQ.kxbie4dxXKHlqnf3qGsALGmiIDgV9PnkyoO3yID3Sk3YAcYahC9jbA1De5UEFhnxS6BeS4Kj868Z5RxKZ4avoA';
$(document).ready(function() {

	axios({
		url: 'http://localhost:8080/api/user/profile',
		method: 'GET',
		headers: {
			'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QGdtYWlsLmNvbSIsImlhdCI6MTYxODQwOTEwMSwiZXhwIjoxNjE5MjczMTAxfQ.kxbie4dxXKHlqnf3qGsALGmiIDgV9PnkyoO3yID3Sk3YAcYahC9jbA1De5UEFhnxS6BeS4Kj868Z5RxKZ4avoA'
		}
	})
		.then(function(resp) {
			let profile = resp.data;
			document.getElementById('fullname').setAttribute("value", profile.fullname);
			document.getElementById('email').setAttribute("value", profile.email);
			document.getElementById('phone').setAttribute("value", profile.phone);
			document.getElementById('address').setAttribute("value", profile.address);
			document.getElementById('imgAvatar').setAttribute("src", profile.avatar);
		})
		.catch(function(err) {
			console.log(err)
		})

});

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
			'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50QGdtYWlsLmNvbSIsImlhdCI6MTYxODQwOTEwMSwiZXhwIjoxNjE5MjczMTAxfQ.kxbie4dxXKHlqnf3qGsALGmiIDgV9PnkyoO3yID3Sk3YAcYahC9jbA1De5UEFhnxS6BeS4Kj868Z5RxKZ4avoA'
		}
	}).then(function(resp) {
		let imageUrl = resp.data;
		// TRUY CẬP TỚI THẺ IMG
		let imgAvatar = document.getElementById('imgAvatar');
		// THAY ĐỔI GIÁ TRI CỦA THẺ SRC
		imgAvatar.setAttribute('src', imageUrl);
	})
		.catch(function(err) {
			console.log(err)
		})

}

function updateProfile() {
	let $form = $("#profile");
	var formData = $form.serializeArray();;
	console.log(formData);
	$.ajax({
		type: "PUT",
		url: "http://localhost:8080/api/user/update",
		data: formData,
		headers: { "Authorization": token },
		success: function() { },
		dataType: "json",
		contentType: "application/json",
		success: function(data) {

			let msg = data.data;
			console.log(msg);

		},
	});
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
			console.log("DATA sPOSTED SUCCESSFULLY" + data);
		},
		error: function(jqXhr, textStatus, errorThrown) {
			console.log(errorThrown);
		}
	});

})

function getFormData(data) {
	var unindexed_array = data;
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});
	
	indexed_array.avatar=null;
	indexed_array.id=null;
	indexed_array.password=null;
	indexed_array.roleId=0;
	indexed_array.roleDesc=null;
	indexed_array.roleName=null;

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