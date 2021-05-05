let token = localStorage.getItem("elearning-token");
let listCart = localStorage.getItem("list-cart");

init();

function init() {
    console.log(1);
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

	console.log(Array.isArray(listCart))
	console.log('debug 1...');
	console.log('listCart ' + listCart.length);
	if (JSON.parse(listCart) == "") {
		console.log('debug..');
		$("#course-content-cart").append('<h1>Your cart is empty. Keep shopping to find a course!</h1>');
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
		console.log(data.indexOf(element));
		data.splice(data.indexOf(element), 1);
		}
	});
	console.log(data);
	
	localStorage.setItem('list-cart', JSON.stringify(data));
	window.location.href = 'http://localhost:8080/cart';
}

function checkout() {
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
}