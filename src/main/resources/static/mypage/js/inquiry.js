document.addEventListener('DOMContentLoaded', function () {
    //글쓰기 버튼 활성화
    document.getElementById('write-button').addEventListener('click', function () {
        window.location.href = "http://localhost:8090/hollroom/mypage/inquiry_write";
    })
    //==============================================================================================================
    // 테이블 데이터 정렬 및 목록 번호 생성
    const tableBody = document.querySelector('tbody');
    const rows = Array.from(tableBody.querySelectorAll('tr'));
    rows.reverse(); // 역순으로 정렬

    // 기존 postId를 순차적인 목록 번호로 변경
    rows.forEach((row, index) => {
        const cell = row.querySelector('td:first-child');
        cell.textContent = rows.length - index;
    });

    // 정렬된 데이터를 테이블에 다시 추가
    rows.forEach(row => tableBody.appendChild(row));
});

