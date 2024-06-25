document.addEventListener("DOMContentLoaded", function () {

    // 저장 버튼 클릭 시 프로필 업데이트 함수 실행
    document.getElementById("saveButton").addEventListener("click", function () {
        if (!this.classList.contains('disabled')) {
            this.classList.add('disabled');
            updateProfile();
        }
    });
    //==========================================================================
    // 자기소개 저장 버튼 클릭 시 저장 함수 실행
    document.getElementById("info_saveButton").addEventListener("click", function () {
        checkIntroduction();
    });
});
//==========================================================================
// 비밀번호 유효성 검사
function validatePassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    if (password != confirmPassword) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return false;
    }
    return true;
}

//==========================================================================
// 프로필 업데이트
function updateProfile() {
    if (validatePassword()) {
        const userId = document.getElementById("userId").value;
        const userLocal = document.getElementById("userLocal").value;
        const password = document.getElementById("password").value;

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8090/hollroom/mypage/updateUser", true);
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

        const updateInfo = {userLocation: userLocal, userEmail: userId};
        if (password) {
            updateInfo.userPassword = password;
        }

        xhr.send(JSON.stringify(updateInfo));
    } else {
        document.getElementById("saveButton").classList.remove('disabled');
    }
}

//==========================================================================
// 정보 수정 활성화
function enableEditUserInfo() {
    document.getElementById('userInfoText').style.display = 'none';
    document.getElementById('userInfoInput').style.display = 'block';
    document.getElementById('editButton').style.display = 'none';
    document.getElementById('info_saveButton').style.display = 'inline';
}

//==========================================================================
// 자기소개 저장
function saveUserInfo() {
    var userInfoInput = document.getElementById('userInfoInput').value;
    const userId = document.getElementById("userId").value;

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8090/hollroom/mypage/updateUserInfo", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
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
    xhr.send(JSON.stringify({userIntroduce: userInfoInput, userEmail: userId}));
}

//==========================================================================
// 파일 업로드 트리거
function triggerFileUpload() {
    document.getElementById("fileInput").click();
}

//==========================================================================
// 이미지 미리보기 및 업로드
function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function () {
        const output = document.getElementById("profileImage");
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
    uploadImage(event.target.files[0]);
}

//==========================================================================
// 이미지 업로드
function uploadImage(file) {
    const formData = new FormData();
    const userId = document.getElementById("userId").value;
    formData.append("image", file);

    const profileData = JSON.stringify({userEmail: userId});
    const blob = new Blob([profileData], {type: "application/json"});
    formData.append("profile", blob);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8090/hollroom/mypage/uploadProfileImage", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert("이미지 업로드 성공!");
        } else if (xhr.readyState == 4) {
            alert("이미지 업로드 실패. 서버 응답: " + xhr.responseText);
        }
    };

    xhr.send(formData, JSON.stringify({userEmail: userId}));
}

//==========================================================================
// 마스킹 함수
function maskingApply() {
    let emailFiled = document.getElementById("email").value;
    let realnameField = document.getElementById("realname").value;
    //이메일 마스킹
    let emailValue = emailFiled.value;
    emailFiled.value = maskingFunc.email(emailValue);
    // 실명 마스킹
    let realnameValue = realnameField.value;
    realnameField.value = maskingFunc.name(realnameValue);
    //==========================================================================
    //실명 마스킹
    let maskingFunc = {
        checkNull: function () {
            if (typeof str == "undefined" || str == null || str == "") {
                return true;
            } else {
                return false;
            }
        },
        //==========================================================================
        // 이메일 마스킹
        email: function (str) {
            let originStr = str;
            let emailStr = originStr.match(/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi);
            let strLength;
            if (this.checkNull(originStr) == true || this.checkNull(emailStr) == true) {
                return originStr;
            } else {
                strLength = emailStr.toString().split('@')[0].length - 3;
                return originStr.toString().replace(new RegExp('.(?=.{0,' + strLength + '}@)', 'g'), '*').replace(/.{6}$/, "******");
            }
        },
        //==========================================================================
        // 핸드폰 번호 마스킹
        phone: function (str) {
            let originStr = str;
            let phoneStr;
            let maskingStr;
            if (this.checkNull(originStr) == true) {
                return originStr;
            }
            if (originStr.toString().split('-').length != 3) {
                phoneStr = originStr.length < 11 ? originStr.match(/\d{10}/gi) : originStr.match(/\d{11}/gi);
                if (this.checkNull(phoneStr) == true) {
                    return originStr;
                }
                if (originStr.length < 11) {
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{3})(\d{4})/gi, '$1***$3'));
                } else {
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{4})(\d{4})/gi, '$1****$3'));
                }
            } else {
                phoneStr = originStr.match(/\d{2,3}-\d{3,4}-\d{4}/gi);
                if (this.checkNull(phoneStr) == true) {
                    return originStr;
                }
                if (/-[0-9]{3}-/.test(phoneStr)) {
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{3}-/g, "-***-"));
                } else if (/-[0-9]{4}-/.test(phoneStr)) {
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{4}-/g, "-****-"));
                }
            }
            return maskingStr;
        },
        //==========================================================================
        // 주민번호 마스킹
        rrn: function (str) {
            let originStr = str;
            let rrnStr;
            let maskingStr;
            let strLength;
            if (this.checkNull(originStr) == true) {
                return originStr;
            }
            rrnStr = originStr.match(/(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4]{1}[0-9]{6}\b/gi);
            if (this.checkNull(rrnStr) == false) {
                strLength = rrnStr.toString().split('-').length;
                maskingStr = originStr.toString().replace(rrnStr, rrnStr.toString().replace(/(-?)([1-4]{1})([0-9]{6})\b/gi, "$1$2******"));
            } else {
                rrnStr = originStr.match(/\d{13}/gi);
                if (this.checkNull(rrnStr) == false) {
                    strLength = rrnStr.toString().split('-').length;
                    maskingStr = originStr.toString().replace(rrnStr, rrnStr.toString().replace(/([0-9]{6})$/gi, "******"));
                } else {
                    return originStr;
                }
            }
            return maskingStr;
        },
        //==========================================================================
        // 실명(이름) 마스킹
        name: function (str) {
            let originStr = str;
            let maskingStr;
            let strLength;
            if (this.checkNull(originStr) == true) {
                return originStr;
            }
            strLength = originStr.length;
            if (strLength < 3) {
                maskingStr = originStr.replace(/(?<=.{1})./gi, "*");
            } else {
                maskingStr = originStr.replace(/(?<=.{2})./gi, "*");
            }
            return maskingStr;
        }
    }
}