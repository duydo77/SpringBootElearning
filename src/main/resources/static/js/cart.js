let token = localStorage.getItem("elearning-token");
let listCart = localStorage.getItem("list-cart");

init();

function init() {
 
	if (token !== null) {
		$.ajax({
			url: "http://localhost:8080/api/user/profile",
			type: "GET",
			dataType: "json",
			headers: { "Authorization": "Bearer " + token },
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

	if (JSON.parse(listCart) == "") {
		$("#course-content-cart").append('<h2>Your cart is empty. Keep shopping to find a course!</h2>');
	} else {
		let data = JSON.parse(listCart);
		let totalPrice = 0;
		let totalPromotionPrice = 0;
		$("#course-content-cart").append('<h3>' + data.length + ' courses in cart</h3>');
		data.map((d) => {
			totalPrice += d.price;
			totalPromotionPrice += d.promotionPrice;
			$("#course-content-cart div#list-cart-box").append('<div class="row list-cart-item"> ' +
																'<div class="col-md-4"> ' +
																'<img src="./img/html-css.jpg" alt="" style="height: 100px;"> ' +
																'</div> ' +
																'<div class="col-md-5"> ' +
																'<div><a href="http://localhost:8080/coursedetail/' + d.id + '"><h3>' + d.title + '</h3></a></div> ' +
																'<div>' + d.teacherName + '</div> ' +
																'</div> ' +
																'<div class="col-md-2">' +
																'<h1>' + d.promotionPrice + '</h1> ' +
																'<div><h3 class="line-through-text">' + d.price + '</h3></div> ' +
																'</div> ' +
																'<div class="col-md-1">' +
																'<button class="btn btn-danger" onclick="removeCartItem(' + d.id + ')">X</button>' +
																'</div> ' +
																'</div>');
		});

		$("div#checkout-box").append('<div class="course-buy">' +
									'<h2 class="mb-4 font-weight-bold">  ' +
									'Total: ' + totalPromotionPrice +
									'</h2>' +
									'<h4><h3 class="line-through-text">' + totalPrice + '</h3></h4>' +
									'<button onclick="checkout()" class="btn btn-success w-100">Check out</button>' +
									'<div class="course-buy-info mt-2">' +
									'</div>' +
									'</div>');
                
	}   
}

function removeCartItem(itemId) {
	let data = JSON.parse(listCart);
	data.forEach(element => {
		if (element.id == itemId) {
		data.splice(data.indexOf(element), 1);
		}
	});
	
	localStorage.setItem('list-cart', JSON.stringify(data));
	window.location.href = 'http://localhost:8080/cart';
}

function checkout() {

	if(localStorage.getItem("elearning-token") != null) {
		let data = JSON.parse(listCart);
		data.forEach(element => {
			console.log(element.id)
			$.ajax({
				url: "http://localhost:8080/api/usercourse/" + element.id,
				type: "POST",
				headers: { "Authorization": token },
				dataType: "json",
				contentType: 'text/html',
				success: function(data) {
					
				},
				error: function(jqXhr, textStatus, errorThrown) {
					console.log(jqXhr.responseText);
				}
			});
		});
		localStorage.setItem('list-cart', "[]");
		window.location.href = 'http://localhost:8080/cart';
	} else {
		$('#loginModal').modal();
	}
}

function login() {
	let newdata = {}
	newdata.email = $("#lgEmail").val();
	newdata.password = $("#lgPassword").val();
	newdata = JSON.stringify(newdata);
	$.ajax({
		url: "http://localhost:8080/api/auth/login",
		type: 'POST',
		contentType: 'application/json',
		data: newdata,
		success: function(data) {
			if (data === null) {
				$('#message').text("email hoặc mật khẩu không đúng");
			} else {
				localStorage.setItem("elearning-token", data);
				$("#info").html('');
				$.ajax({
					url: "http://localhost:8080/api/user/profile",
					type: "GET",
					dataType: "json",
					headers: { "Authorization": "Bearer " + localStorage.getItem('elearning-token') },
					contentType: 'text/html',
					success: function(data) {
						$("#info").append("<div class='dropdown'>"
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
			}
			if(localStorage.getItem('list-cart') != '') {
				$.ajax({
					url: "http://localhost:8080/api/course/mycourse",
					type: 'GET',
					contentType: 'application/json',
					headers: { "Authorization": "Bearer " + localStorage.getItem('elearning-token') },
					success: function(data) {
						let cartList = JSON.parse(localStorage.getItem('list-cart'));
						data.map(d => {
							cartList.map(c => {
								if(d.id == c.id) {
									removeCartItem(c.id);
								} 
							})
						});
					},
					error: function(jqXhr, textStatus, errorThrown) {
						
					}
				});
			}
		},
		error: function(jqXhr, textStatus, errorThrown) {
			$('#message').text("email hoặc mật khẩu không đúng");
		}
	});
	
}

function logout() {
	localStorage.removeItem("elearning-token");
	location.reload();
}