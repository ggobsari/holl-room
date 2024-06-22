document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("saveButton").addEventListener("click", function() {
        if (!this.classList.contains('disabled')) {
            this.classList.add('disabled');
            updateProfile();
        }
    });
});
//===============================================================
//비밀번호 판별
function validatePassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    if (password != confirmPassword) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return false;
    }
    return true;
}
//===============================================================
//프로필 업데이트
function updateProfile() {
    if (validatePassword()) {
        const userId = document.getElementById("userId").value;
        const userLocal = document.getElementById("userLocal").value;
        const password = document.getElementById("password").value;

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8090/hollroom/mypage/profile.do", true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    const response = JSON.parse(xhr.responseText);
                    alert(response.message);
                    location.reload();
                } else {
                    alert("업데이트에 실패했습니다. 서버 응답: " + xhr.responseText);
                }
                document.getElementById("saveButton").classList.remove('disabled');
            }
        };
        xhr.send(JSON.stringify({ userEmail: userId, userLocal: userLocal, userPassword: password }));
    } else {
        document.getElementById("saveButton").classList.remove('disabled');
    }
}
//===============================================================
//
function enableEditUserInfo() {
    document.getElementById('userInfoText').style.display = 'none';
    document.getElementById('userInfoInput').style.display = 'block';
    document.getElementById('editButton').style.display = 'none';
    document.getElementById('info_saveButton').style.display = 'inline';
}
//===============================================================
//자기소개 저장
function saveUserInfo() {
    var userInfoInput = document.getElementById('userInfoInput').value;

    // 데이터 저장 요청
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8090/hollroom/mypage/profile.do", true); // 실제 엔드포인트로 수정 필요
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // 저장된 데이터를 불러와서 출력
                document.getElementById('userInfoText').textContent = userInfoInput;
                document.getElementById('userInfoText').style.display = 'block';
                document.getElementById('userInfoInput').style.display = 'none';
                document.getElementById('editButton').style.display = 'inline';
                document.getElementById('info_saveButton').style.display = 'none';
            } else {
                alert("저장에 실패했습니다. 서버 응답: " + xhr.responseText);
            }
        }
    };
    xhr.send(JSON.stringify({ userInfo: userInfoInput }));
}

//===============================================================
//사진
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("profileImage").addEventListener("click", function() {
        document.getElementById("fileInput").click();
    });
    document.getElementById("fileInput").addEventListener("change", function(event) {
        previewImage(event);
    });
});

function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const output = document.getElementById("profileImage");
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
    uploadImage(event.target.files[0]);
}

function uploadImage(file) {
    const formData = new FormData();
    formData.append("image", file);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/hollroom/mypage/profile.img", true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            const response = JSON.parse(xhr.responseText);
            if (xhr.status == 200) {
                alert(response.message);
            } else {
                alert("이미지 업로드 실패. 서버 응답: " + response.message);
            }
        }
    };
    xhr.send(formData);
}

