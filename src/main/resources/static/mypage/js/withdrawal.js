function toggleWithdrawalButton() {
    const checkbox = document.getElementById('agreeCheckbox').checked;
    const password = document.getElementById('password').value;
    const button = document.getElementById('withdrawalButton');

    button.disabled = !(checkbox && password);
}

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


