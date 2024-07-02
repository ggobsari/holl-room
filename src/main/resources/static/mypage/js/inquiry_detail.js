// script.js 파일의 적절한 위치에 아래 내용을 추가
document.addEventListener("DOMContentLoaded", function () {
    // DOM이 완전히 로드된 후에 실행할 코드 작성
    const fileInput = document.getElementById('upload-form');
    if (fileInput) {
        fileInput.addEventListener('change', function (e) {
            const files = e.target.files;
            const fileList = document.getElementById('file-list');
            fileList.innerHTML = ''; // 기존 목록을 초기화

            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                const listItem = document.createElement('li');
                listItem.textContent = file.name;
                fileList.appendChild(listItem);
            }
        });
    }
});
