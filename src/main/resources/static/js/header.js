//path = 'http://localhost:8080/'
function search() {
	let key = $("#search-input").val()
	if (key === "") {
		$("#search-result").removeClass('show');
	} else {
		$.ajax({
			url: "http://localhost:8080/api/course/search?key=" + key,
			type: "GET",
			dataType: "json",
			contentType: "application/json",
			success: function(data) {
				let content = '';
				if (data.length == 0) {
					content = '<p class="dropdown-item">No result found!!!</p>'
				}
				let count = (data.length < 6) ? data.length : 5;
				for (var i = 0; i < count; i++) {
					content += 	'<div>' 
								+ '<a class="dropdown-item" style="color: #000" href="' + path + 'coursedetail/' + data[i].id + '">' 
								+ "<img style='width: 40px' src='" + data[i].image + "' /> "
								+ data[i].title + '<a>'
								+ '</div>'
				}
				$('#search-result').html(content);
			},

			error: function(jqXhr, textStatus, errorThrown) {
				console.log(errorThrown);
			}
		});
		$("#search-result").addClass('show');
	}
}

$('#btn-search').click(function() {
	console.log('haha');
	let key = $("#search-input").val()
	if (key !== ""){
		location.replace(path + 'search?key=' + key + '&p=1');
	}
})