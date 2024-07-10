//버튼 활성화
function toggleWithdrawalButton() {
    const checkbox = document.getElementById('agreeCheckbox').checked;
    const passwordElement = document.getElementById('password');
    const password = passwordElement ? passwordElement.value : '';
    const button = document.getElementById('withdrawalButton');
    const buttonSocial = document.getElementById('witdrawalWithoutPassword');
    const loginType = document.getElementById('loginType').value;

    // 소셜 로그인 버튼 활성화 조건
    if (buttonSocial && loginType !== '') {
        buttonSocial.disabled = !checkbox;
    }

    // 일반 로그인 버튼 활성화 조건
    if (button && loginType === '') {
        button.disabled = !(checkbox && password.length > 0);
    }
}

//일반로그인 회원탈퇴
function handleWithdrawal(event) {
    event.preventDefault();
    const password = document.getElementById('password').value;

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8090/hollroom/mypage/handleWithdrawal', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            const status = xhr.status;
            if (status === 0 || (status >= 200 && status < 400)) {
                const response = JSON.parse(xhr.responseText);
                if (response.success) {
                    alert('회원탈퇴가 성공적으로 처리되었습니다.');
                } else {
                    alert(response.message);
                }
            } else {
                alert('회원탈퇴 처리에 실패했습니다.');
            }
        }
    };

    xhr.send(JSON.stringify({ password }));
}
//소셜로그인 회원탈퇴
function WithoutPassword(event){
    event.preventDefault();

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8090/hollroom/mypage/WithoutPassword', true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            const status = xhr.status;
            if (status === 0 || (status >= 200 && status < 400)) {
                const response = JSON.parse(xhr.responseText);
                if (response.success) {
                    alert('회원탈퇴가 성공적으로 처리되었습니다.');
                } else {
                    alert(response.message);
                }
            } else {
                alert('회원탈퇴 처리에 실패했습니다.');
            }
        }
    };

    xhr.send(JSON.stringify({ }));
}

