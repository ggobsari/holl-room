$(document).ready(function () {
    let isVerified = false;
    let isNicknameValid = false;
    let isEmailValid = false;

    //휴대폰 번호 유효성 검사 함수
    function validatePhoneNumber() {
        var phoneNumberInput = document.getElementById('userPhoneNumber');
        var phoneNumberError = document.getElementById('phone_number_error');
        var phoneNumberValue = phoneNumberInput.value;

        var phoneNumberPattern = /^\d{3}-\d{3,4}-\d{4}$/;

        if (!phoneNumberPattern.test(phoneNumberValue)) {
            phoneNumberError.style.display = 'block';
            alert("휴대폰 번호를 확인해주세요. - 포함되어야 합니다.");
            return false;
        }
        phoneNumberError.style.display = 'none';
        return true;
    }

    //인증번호 발송 버튼 클릭 이벤트
    $('#phoneCheck').click(function () {
        if (!validatePhoneNumber()) {
            return
        }
        var userPhoneNumber = $('#userPhoneNumber').val();

        $.ajax({
            url: '/hollroom/sendSMS',
            type: 'POST',
            data: {userPhoneNumber: userPhoneNumber},
            success: function (response) {
                if (response) {
                    alert("인증번호가 전송되었습니다. 입력하세요.");
                    $('#sendSMSCheck').show();
                } else {
                    alert("번호를 다시 입력해주세요.");
                }
            },
            error: function () {
                alert('서버 오류입니다. 다시 시도해주세요.');
            }
        })
    })

    $('#sendSMSCheckButton').click(function () {
        var verificationCode = $('#sendSMSInput').val();
        var userPhoneNumber = $('#userPhoneNumber').val();

        $.ajax({
            url: '/hollroom/verify',
            type: 'POST',
            data: {
                userPhoneNumber: userPhoneNumber,
                verifyCode: verificationCode
            },
            success: function (response) {
                if (response === true) {
                    alert("인증에 성공했습니다.");
                    isVerified = true;
                    $('#sendSMSCheck').hide();
                } else {
                    alert("인증번호가 올바르지 않습니다. 다시 확인해주세요.");
                }
            },
            error: function () {
                alert('서버 오류입니다. 다시 시도해주세요.');
            }
        })
    })

    //이메일 유효성 확인
    function validateEmail() {
        var emailInput = document.getElementById('userEmail');
        // var emailDuplicated = document.getElementById('email-duplicated');
        var emailError = document.getElementById('email-error');
        var emailValid = document.getElementById('email-valid');
        var emailValue = emailInput.value;

        var emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;

        if (emailPattern.test(emailValue)) {
            // emailDuplicated.style.display = 'none';
            emailError.style.display = 'none';
            emailValid.style.display = 'block';
            return true;
        } else {
            // emailDuplicated.style.display = 'none';
            emailError.style.display = 'block';
            emailValid.style.display = 'none';
            alert("유효하지 않은 이메일 주소입니다.");
            return false;
        }

    }

    // $('#userImage').change(previewImage);
    // 파일 선택 시 미리보기
    // $("#userImage").change(function () {
    //     console.log("#userImage");
    //     let file = this.files[0];
    //     let reader = new FileReader();
    //     reader.onload = function (e) {
    //         $("#preview").attr("src", e.target.result);
    //     };
    //     reader.readAsDataURL(file);
    // });

    // $('#emailCheck').click(function () {
    //

    //닉네임 중복 확인
    $('#idCheck').click(function () {
        var userNickname = $('#userNickname').val();

        $.ajax({
            url: '/hollroom/checkNickname',
            type: 'GET',
            data: {userNickname: userNickname},
            success: function (response) {
                if (response === "닉네임이 이미 사용 중입니다.") {
                    alert('중복된 닉네임입니다.');
                    $('#nickname-error').show();
                    $('#nickname-valid').hide();
                    isNicknameValid = false;
                } else {
                    alert('사용 가능한 닉네임입니다.');
                    $('#nickname-error').hide();
                    $('#nickname-valid').show();
                    isNicknameValid = true;
                }
            },
            error: function () {
                alert('서버 오류입니다. 다시 시도해주세요.');
            }
        })
    })

    // 폼 제출 이벤트 처리
    $('#enter').click(function (e) {
        console.log("Is verified: " + isVerified + ", Is nickname valid: " + isNicknameValid);
        if (!isVerified || !isNicknameValid) {
            e.preventDefault();
            if (!isVerified) alert("휴대폰 번호를 인증해주세요.");
            if (!isNicknameValid) alert('닉네임을 확인해주세요.');
            return;
        }

        if (!validateEmail()) {
            return;
        }

        var userEmail = $('#userEmail').val();

        $.ajax({
            url: '/hollroom/checkEmail',
            type: 'GET',
            data: {userEmail: userEmail},
            success: function (response) {
                if (response === "이미 사용 중인 이메일입니다.") {
                    alert("이미 가입되어 있는 이메일입니다.");
                } else{
                    isEmailValid = true;
                }
            },
            error: function () {
                alert('서버 오류입니다. 다시 시도해주세요.');
            }
        })

        if (!validatePhoneNumber()) {
            return;
        }

        if (isEmailValid === true){
            let formData = new FormData();
            // let myfile = $("#userImage")[0];

            //폼 데이터 추가
            formData.append('userEmail', $('#userEmail').val());
            formData.append('userPassword', $('#userPassword').val());
            formData.append('userName', $('#userName').val());
            formData.append('userNickname', $('#userNickname').val());
            // formData.append('userImage', myfile.files[0]);
            formData.append('userIntroduce', $('#userIntroduce').val());
            formData.append('userPhoneNumber', $('#userPhoneNumber').val());
            formData.append('userBirthday', $('#userBirthday').val());
            formData.append('userGender', $('input[name=gender]:checked').val());
            formData.append('userLocation', $('#userLocation').val());

            for (val of formData.values()) {
                console.log(val)
            }

            $.ajax({
                url: '/hollroom/signup',
                type: 'POST',
                data: formData,
                contentType: false, //파일 업로드를 위해 false
                dataType: "text",
                processData: false,//파일 업로드를 위해 false
                success: function () {
                    alert('회원가입 성공');
                    window.location.href = '/hollroom/login'; //회원가입 성공 후 리다이렉트
                },
                error: function (response) {
                    if (response === "닉네임이 이미 사용 중입니다.") {
                        alert('중복된 닉네임입니다.');
                    } else {
                        alert("회원가입 실패");
                    }
                }
            })
        }
    })
});

