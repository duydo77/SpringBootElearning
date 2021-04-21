let token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFjaGVyMUBnbWFpbC5jb20iLCJpYXQiOjE2MTg5MjI4ODgsImV4cCI6MTYxOTc4Njg4OH0.bPfxDCvJmwi0zxalbg0aFkeWJbdIHfJdbOK1vTMkgtAILOEwp86AhXoRlQUUbxG-f3MJhl3Mf0Ui7oXAXcKjTg';

// $(document).ready(() => {
    $("#btn-login").click(function() {
        // const txtEmail = $("#lgEmail").val();
        // const txtPassword = $("#lgPassword").val();

        let form = $("#lgForm");
        let data = getFormData(form.serializeArray());
        
        console.log("btn login click"); 
        //console.log(txtEmail);
        //console.log(txtPassword);
        console.log(data);
        console.log(JSON.stringify(data));
        $.ajax({
            url: "http://localhost:8080/api/teacher/login",
            type: "POST",
            //headers: { "Authorization": token },
            contentType: 'application/json; charset=utf-8',
            // dataType: 'json',
            data: JSON.stringify(data),
            
            success: function(data) {
                if (data === null) {
                    $('#message').text("email hoặc mật khẩu không đúng");
                } else {
                    console.log(data);
                    localStorage.removeItem("elerning-token");
                    localStorage.setItem("elearning-token", data);
                }
                console.log(localStorage.getItem("elearning-token"));
            },
            error: function(jqXhr, textStatus, errorThrown) {
                $('#message').text("email hoặc mật khẩu không đúng");
            }
        });
    });

    function getFormData(data) {
        var unindexed_array = data;
        var indexed_array = {};
    
        $.map(unindexed_array, function(n, i) {
            indexed_array[n['name']] = n['value'];
        });
    
        return indexed_array;
    }
    // JSON.stringify({
    //     "email" : txtEmail, 
    //     "password" : txtPassword
    // })

// });

// function login() {
    
// 	$.ajax({
// 		url: "http://localhost:8080/api/admin/login",
// 		type: 'POST',
// 		contentType: 'application/json',
// 		data: newdata,
// 		success: function(data) {		
//             console.log(data);
//             localStorage.setItem("elearning-token", data);
//             localStorage.removeItem("elerning-token");
// 			console.log(localStorage.getItem("elearning-token"));
// 		},
// 		error: function(jqXhr, textStatus, errorThrown) {
// 			$('#message').text("email hoặc mật khẩu không đúng");
// 		}
// 	});
// }
