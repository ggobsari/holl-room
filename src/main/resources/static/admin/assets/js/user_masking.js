document.addEventListener("DOMContentLoaded", function () {
    //마스킹 함수 실행
    maskMaking();

});
//=============================================================================
//마스킹 함수 실행
function maskMaking(){
    //이메일 마스킹
    const emailElements = document.querySelectorAll(".email"); // 클래스가 email인 모든 요소 선택
    emailElements.forEach(function(emailElement) {
        const emailText = emailElement.textContent || emailElement.innerText;
        emailElement.textContent = maskingFunc.email(emailText);
    });

    //실명 마스킹
    const usernameElements = document.querySelectorAll(".username"); // 클래스가 username인 모든 요소 선택
    usernameElements.forEach(function(usernameElement) {
        const nameText = usernameElement.textContent || usernameElement.innerText;
        usernameElement.textContent = maskingFunc.name(nameText);
    });

    //휴대폰 번호 마스킹
    const phoneElements = document.querySelectorAll(".userPhoneNumberMasked"); // 클래스가 userPhoneNumberMasked인 모든 요소 선택
    phoneElements.forEach(function(phoneElement) {
        const phoneText = phoneElement.textContent || phoneElement.innerText;
        phoneElement.textContent = maskingFunc.phone(phoneText);
    });
}
//=============================================================================
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
                maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{3})(\d{4})/gi, '$1●●●$3'));
            } else {
                maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{4})(\d{4})/gi, '$1●●●●$3'));
            }
        } else {
            phoneStr = originStr.match(/\d{2,3}-\d{3,4}-\d{4}/gi);
            if (this.checkNull(phoneStr) == true) {
                return originStr;
            }
            if (/-[0-9]{3}-/.test(phoneStr)) {
                maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{3}-/g, "-●●●-"));
            } else if (/-[0-9]{4}-/.test(phoneStr)) {
                maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{4}-/g, "-●●●●-"));
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
            maskingStr = originStr.toString().replace(rrnStr, rrnStr.toString().replace(/(-?)([1-4]{1})([0-9]{6})\b/gi, "$1$2●●●●●●"));
        } else {
            rrnStr = originStr.match(/\d{13}/gi);
            if (this.checkNull(rrnStr) == false) {
                strLength = rrnStr.toString().split('-').length;
                maskingStr = originStr.toString().replace(rrnStr, rrnStr.toString().replace(/([0-9]{6})$/gi, "●●●●●●"));
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
            maskingStr = originStr.replace(/(?<=.{1})./gi, "●");
        } else {
            const middleIndex = Math.floor(strLength / 2);
            return originStr.substring(0, middleIndex) + "●" + originStr.substring(middleIndex + 1);
        }
        return maskingStr;
    }
}
//==============================================================================================
// 닉네임 중복 검사 함수
function checkNickname(nickname) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/mypage/checkNickname?nickname=" + encodeURIComponent(nickname), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            const isTaken = JSON.parse(xhr.responseText);
            const resultElement = document.getElementById("nicknameCheckResult");
            if (isTaken) {
                resultElement.textContent = "이미 사용 중인 닉네임입니다.";
                resultElement.style.color = "red";
            } else {
                resultElement.textContent = "사용 가능한 닉네임입니다.";
                resultElement.style.color = "green";
            }
        } else if (xhr.readyState == 4) {
            alert("닉네임 중복 검사에 실패했습니다. 서버 응답: " + xhr.responseText);
        }
    };
    xhr.send();
}
//==============================================================================================
// 닉네임 중복 검사 함수
function checkPhoneNum(phoneNum) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/hollroom/mypage/checkPhoneNum?phoneNum=" + encodeURIComponent(phoneNum), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            const isTaken = JSON.parse(xhr.responseText);
            const resultElement = document.getElementById("phoneNumCheckResult");
            if (isTaken) {
                resultElement.textContent = "이미 등록된 번호입니다.";
                resultElement.style.color = "red";
            } else {
                resultElement.textContent = "등록되지 않은 번호입니다.";
                resultElement.style.color = "green";
            }
        } else if (xhr.readyState == 4) {
            alert("닉네임 중복 검사에 실패했습니다. 서버 응답: " + xhr.responseText);
        }
    };
    xhr.send();
}