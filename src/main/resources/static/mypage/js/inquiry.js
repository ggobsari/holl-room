document.addEventListener('DOMContentLoaded', function() {
    //글쓰기 버튼 활성화
    document.getElementById('write-button').addEventListener('click', function() {
        window.location.href = "http://localhost:8090/hollroom/mypage/inquiry_write";  //!!!!!
    });
});


//글 목록 불러오기
$(document).ready(function() {  //!!!!!
    $.ajax({
        url: 'http://localhost:8090/hollroom/mypage/inquiry',
        method: 'GET',
        success: function(data) {
            var tableBody = $('#inquiry-table-body');
            tableBody.empty();
            data.forEach(function(inquiry) {
                var row = `<tr>
                    <td>${inquiry.answerId}</td>
                    <td>${inquiry.community.title}</td>
                    <td>${inquiry.community.createdAt}</td>
                    <td>${inquiry.answerText ? '답변 완료' : '미답변'}</td>
                    <td>${inquiry.answerDate ? inquiry.answerDate : 'N/A'}</td>
                </tr>`;
                tableBody.append(row);
            });
        },
        error: function(error) {
            console.error('Error fetching data', error);
        }
    });
});
