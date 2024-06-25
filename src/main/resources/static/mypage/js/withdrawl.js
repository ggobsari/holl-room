document.getElementById('withdrawalForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var password = document.getElementById('password').value;

    fetch('http://localhost:8090/hollroom/mypage/verifypassword', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRFToken': getCookie('csrftoken')
        },
        body: JSON.stringify({ 'password': password })
    })
        .then(response => response.json())
        .then(data => {
            if (data.valid) {
                if (confirm('정말로 회원을 탈퇴하시겠습니까?')) {
                    fetch('http://localhost:8090/hollroom/mypage/deleteuser', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRFToken': getCookie('csrftoken')
                        },
                        body: JSON.stringify({ 'userId': data.userId })
                    })
                        .then(response => response.json())
                        .then(data => {
                            alert('회원이 탈퇴되었습니다.');
                            window.location.href = '/';
                        });
                }
            } else {
                alert('비밀번호가 다릅니다.');
            }
        });
});

function getCookie(name) {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}
