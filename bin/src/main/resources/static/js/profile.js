function saveAvatar() {
    let avatarInput = document.getElementById("avatar");
    // KIỂM TRA XEM CHỌN HÌNH CHƯA
    if(avatarInput.files.length === 0){
        alert("Vui lòng chọn file!");
        return;
    }

    // ADD FILE VÀO ĐỐI TƯỢNG FORMDATA
    let formData = new FormData();
    formData.append('file', avatarInput.files[0]);

    axios({
        url: 'http://localhost:8080/api/file/upload',
        method: 'POST',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
    .then(function(resp){
        let imageUrl = resp.data;
        // TRUY CẬP TỚI THẺ IMG
        let imgAvatar = document.getElementById('imgAvatar');
        // THAY ĐỔI GIÁ TRI CỦA THẺ SRC
        imgAvatar.setAttribute('src', `http://localhost:8080/${imageUrl}`);
    })
    .catch(function(err){
        console.log(err)
    })
    
}