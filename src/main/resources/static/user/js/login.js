$(document).ready(function () {

    let isEmailVerified = false;

    $('#kakao-login-btn').click(function (e) {
        e.preventDefault();
        window.location.href =
            'https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=eab736d5f220cb419d2fe00eadae71e2&redirect_uri=http://localhost:8090/hollroom/kakao';
    })

    // 폼 제출 이벤트 처리
    $('#enter').click(function (e) {
        e.preventDefault();//기본 이벤트 제거(폼 제출 방지)

        let formData = new FormData();

        //폼 데이터 추가
        formData.append('userEmail', $('#userEmail').val());
        formData.append('userPassword', $('#userPassword').val());

        $.ajax({
            url: '/hollroom/login',
            type: 'POST',
            data: formData,
            contentType: false,
            dataType: "text",
            processData: false,
            success: function () {
                alert("로그인 성공");
                window.location.href = '/hollroom';
            },
            error: function () {
                alert('로그인 실패');
            }
        })
    })

    $('#reissue-password').click(function (e) {
        e.preventDefault();
        $('#passwordModal').modal('show');
    })

    $('#resetPasswordButton').click(function (e) {
        e.preventDefault();

        if(!isEmailVerified){
            alert("이메일 인증이 필요합니다.");
            return;
        }

        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();

        if (newPassword !== confirmPassword) {
            alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        var formData = {
            email: $('#resetEmail').val(),
            newPassword: newPassword
        }

        $.ajax({
            url: '/hollroom/resetPassword',
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function () {
                alert('비밀번호가 성공적으로 변경되었습니다.');
                $('#passwordModal').modal('hide');
            },
            error: function () {
                alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요.');
            }
        })
    })

    //이메일 유효성 확인
    function validateEmail() {
        var emailInput = document.getElementById('resetEmail');

        var emailValue = emailInput.value;

        var emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;

        return emailPattern.test(emailValue);

    }

    $('#sendResetEmailButton').click(function (e) {
        e.preventDefault();

        var email = $('#resetEmail').val();

        if (!email) {
            alert('이메일을 입력하세요.');
            return;
        } else if(!validateEmail()){
            alert('이메일 형식에 맞게 작성해 주세요.')
            return;
        }

        var formData = {
            email: email
        }

        $.ajax({
            url: '/hollroom/forgotPassword',
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function () {
                alert('비밀번호 재설정 이메일이 전송되었습니다.');
                $('#emailVerificationGroup').show();
                console.log(formData);
            },
            error: function () {
                alert('이메일 전송에 실패했습니다. 다시 시도해주세요.');
                console.log(formData);
            }
        })
    })

    $('#verifyCodeButton').click(function (e) {
        e.preventDefault();

        var verificationCode = $('#verificationCode').val();

        if (!verificationCode) {
            alert('인증 코드를 입력하세요.');
            return;
        }

        var formData = {
            email: $('#resetEmail').val(),
            code: verificationCode
        }

        $.ajax({
            url: '/hollroom/verifyCode',
            type: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function () {
                alert('이메일 인증이 완료되었습니다.');
                isEmailVerified = true;
                $('#emailVerificationGroup').hide();
            },
            error: function () {
                alert('이메일 인증에 실패했습니다. 다시 시도해주세요.');
            }
        })
    })

});