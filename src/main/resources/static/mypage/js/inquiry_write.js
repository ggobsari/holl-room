document.addEventListener('DOMContentLoaded', function () {
    initializeFileInput();  // 파일첨부 초기화
    document.getElementById("upload-button").addEventListener("click", function () {
        if (!this.classList.contains('disabled')) {
            this.classList.add('disabled');
            uploadInquiry();
        }
    });

    //이메일 마스킹
    const emailInput = document.getElementById("email");
    emailInput.value = maskingFunc.email(emailInput.value);
});
//=============================================================================================================
// 파일첨부 초기화
function initializeFileInput() {
    const fileInput = document.getElementById('file-input');
    const fileList = document.getElementById('file-list');

    if (fileInput && fileList) {
        fileInput.addEventListener('change', () => {
            addFiles(fileInput.files);  // 파일이 변경될 때 파일 추가 함수 호출
        });
    } else {
        console.error('File input or file list element not found.');
    }
}
let currentFiles = [];  // 현재 파일 목록을 저장하는 배열

function addFiles(newFiles) {
    currentFiles = [...currentFiles, ...Array.from(newFiles)];  // 기존 파일 목록에 새로운 파일 추가
    updateFileList();  // 파일 목록 업데이트
}
function removeFile(index) {
    currentFiles.splice(index, 1);  // 파일 목록에서 해당 파일 삭제
    updateFileList();  // 파일 목록 업데이트
}
function updateFileList() {
    const fileList = document.getElementById('file-list');
    fileList.innerHTML = '';  // 파일 목록 초기화

    currentFiles.forEach((file, index) => {
        const fileItem = document.createElement('div');
        fileItem.classList.add('file-item');
        fileItem.innerHTML = `
            ${file.name} <button type="button" onclick="removeFile(${index})" class="remove-file-button">x</button>
        `;  // 파일 이름과 삭제 버튼 추가
        fileList.appendChild(fileItem);  // 파일 항목을 파일 목록에 추가
    });
}
//=============================================================================================================
// 글 올리기
function uploadInquiry() {
    const category = document.getElementById("category").value;
    const title = document.getElementById("title").value;
    const content = document.getElementById("content").value;

    if (!title || !content || !category) {
        alert("모든 필드를 작성해 주세요.");
        document.getElementById("upload-button").classList.remove('disabled');
        return;
    }

    const formData = new FormData();
    formData.append('category', category);
    formData.append('title', title);
    formData.append('content', content);

    if (currentFiles.length > 0) {
        currentFiles.forEach(file => {
            formData.append('files', file);
        });
    }

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/hollroom/mypage/upload", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                const response = JSON.parse(xhr.responseText);
                window.location.href = "/hollroom/mypage/inquiry?category=all&page=0"; // 리디렉션 URL
            } else {
                alert("업로드에 실패했습니다. 서버 응답: " + xhr.responseText);
            }
            document.getElementById("upload-button").classList.remove('disabled');
        }
    };

    xhr.send(formData);
}
//=============================================================================================================
//마스킹
let maskingFunc = {
    checkNull: function (str) {
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
        }else {
            let maskedEmails = emailStr.map(email => {
                const [localPart, domainPart] = email.split('@');
                const localPartLength = localPart.length;

                // 마스킹할 인덱스 설정 (0부터 시작하기 때문에 1, 3, 4, 5, 7, 9에 해당)
                const maskIndices = new Set([1, 3, 4, 6, 9].filter(index => index < localPartLength));

                // 문자열을 배열로 변환한 후 지정된 인덱스를 마스킹
                const maskedLocalPart = localPart.split('').map((char, index) =>
                    maskIndices.has(index) ? '●' : char
                ).join('');

                return `${maskedLocalPart}@${domainPart}`;
            });

            // 마스킹된 이메일 주소를 원래 문자열에 삽입
            let maskedStr = originStr;
            emailStr.forEach((email, index) => {
                maskedStr = maskedStr.replace(email, maskedEmails[index]);
            });

            return maskedStr;
        }
    }
    }