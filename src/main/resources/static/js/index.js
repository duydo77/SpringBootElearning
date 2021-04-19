const path = "http://localhost:8080/api/";

$(document).ready(() => {
    const btnLogin = document.getElementById("#btn-login");

    btnlogin.onclick(() => {
        const txtEmailLogin = $("#lgEmail").val();
        const txtPasswordLogin = $("#lgPassword").val();

        const data = {
            email: txtEmailLogin,
            password: txtPasswordLogin
        }

        $.ajax({
            url: path + "teacher/login",
            type: "POST",
            dataType: "json",
            data: data,
            contentType: "application/json",
            success: () => {
                console.log("success");
            },
            error: () => {
                console.log("fail");
            }
        })
    })
});