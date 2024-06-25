document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("saveButton").addEventListener("click", function() {
        if (!this.classList.contains('disabled')) {
            this.classList.add('disabled');
            updateProfile();
        }
    });

    document.getElementById("info_saveButton").addEventListener("click", function() {
        checkIntroduction();
    });

    // 지도 초기화
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667),
            level: 10
        };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude,
                lon = position.coords.longitude;

            var locPosition = new kakao.maps.LatLng(lat, lon),
                message = '<div style="padding:5px;">여기에 계신가요?!</div>';

            displayMarker(locPosition, message);
        });
    } else {
        var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
            message = 'geolocation을 사용할수 없어요..'

        displayMarker(locPosition, message);
    }

    function displayMarker(locPosition, message) {
        var marker = new kakao.maps.Marker({
            map: map,
            position: locPosition
        });

        var iwContent = message,
            iwRemoveable = true;

        var infowindow = new kakao.maps.InfoWindow({
            content: iwContent,
            removable: iwRemoveable
        });

        infowindow.open(map, marker);
        map.setCenter(locPosition);
    }

    var geocoder = new kakao.maps.services.Geocoder();
    var marker = new kakao.maps.Marker(),
        infowindow = new kakao.maps.InfoWindow({zindex:1});

    searchAddrFromCoords(map.getCenter(), displayCenterInfo);

    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
        searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
                detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';

                var content = '<div class="bAddr">' +
                    '<span class="title">법정동 주소정보</span>' +
                    detailAddr +
                    '</div>';

                marker.setPosition(mouseEvent.latLng);
                marker.setMap(map);
                infowindow.setContent(content);
                infowindow.open(map, marker);
            }
        });
    });

    kakao.maps.event.addListener(map, 'idle', function() {
        searchAddrFromCoords(map.getCenter(), displayCenterInfo);
    });

    function searchAddrFromCoords(coords, callback) {
        geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
    }

    function searchDetailAddrFromCoords(coords, callback) {
        geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
    }

    function displayCenterInfo(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var infoDiv = document.getElementById('centerAddr');
            for(var i = 0; i < result.length; i++) {
                if (result[i].region_type === 'H') {
                    infoDiv.innerHTML = result[i].address_name;
                    break;
                }
            }
        }
    }

    geocoder.addressSearch('제주특별자치도 제주시 첨단로 242', function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });
            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
            });
            infowindow.open(map, marker);
            map.setCenter(coords);
        }
    });

    window.sample5_execDaumPostcode = function() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address;
                document.getElementById("sample5_address").value = addr;
                geocoder.addressSearch(data.address, function(results, status) {
                    if (status === daum.maps.services.Status.OK) {
                        var result = results[0];
                        var coords = new daum.maps.LatLng(result.y, result.x);
                        mapContainer.style.display = "block";
                        map.relayout();
                        map.setCenter(coords);
                        marker.setPosition(coords)
                    }
                });
            }
        }).open();
    }
});

// 비밀번호 판별
function validatePassword() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    if (password != confirmPassword) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return false;
    }
    return true;
}

// 프로필 업데이트
function updateProfile() {
    if (validatePassword()) {
        const userId = document.getElementById("userId").value;
        const userLocal = document.getElementById("userLocal").value;
        const password = document.getElementById("password").value;

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8090/hollroom/mypage/updateUser", true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    const response = JSON.parse(xhr.responseText);
                    alert(response.message);
                    location.reload();
                } else {
                    alert("업데이트에 실패했습니다. 서버 응답: " + xhr.responseText);
                }
                document.getElementById("saveButton").classList.remove('disabled');
            }
        };

        const updateInfo = { userLocation: userLocal, userEmail: userId };
        if (password) {
            updateInfo.userPassword = password;
        }

        xhr.send(JSON.stringify(updateInfo));
    } else {
        document.getElementById("saveButton").classList.remove('disabled');
    }
}

// 정보 수정 활성화
function enableEditUserInfo() {
    document.getElementById('userInfoText').style.display = 'none';
    document.getElementById('userInfoInput').style.display = 'block';
    document.getElementById('editButton').style.display = 'none';
    document.getElementById('info_saveButton').style.display = 'inline';
}

// 자기소개 저장
function saveUserInfo() {
    var userInfoInput = document.getElementById('userInfoInput').value;
    const userId = document.getElementById("userId").value;

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8090/hollroom/mypage/updateUserInfo", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                document.getElementById('userInfoText').textContent = userInfoInput;
                document.getElementById('userInfoText').style.display = 'block';
                document.getElementById('userInfoInput').style.display = 'none';
                document.getElementById('editButton').style.display = 'inline';
                document.getElementById('info_saveButton').style.display = 'none';
            } else {
                alert("저장에 실패했습니다. 서버 응답: " + xhr.responseText);
            }
        }
    };
    xhr.send(JSON.stringify({ userIntroduce: userInfoInput, userEmail: userId }));
}

// 이미지 업로드 트리거 함수
function triggerFileUpload() {
    document.getElementById("fileInput").click();
}

// 이미지 미리보기 및 업로드 함수
function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const output = document.getElementById("profileImage");
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
    uploadImage(event.target.files[0]);
}

// 이미지 업로드 함수
function uploadImage(file) {
    const formData = new FormData();
    const userId = document.getElementById("userId").value;
    formData.append("image", file);

    const profileData = JSON.stringify({ userEmail: userId });
    const blob = new Blob([profileData], { type: "application/json" });
    formData.append("profile", blob);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8090/hollroom/mypage/uploadProfileImage", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert("이미지 업로드 성공!");
        } else if (xhr.readyState == 4) {
            alert("이미지 업로드 실패. 서버 응답: " + xhr.responseText);
        }
    };

    xhr.send(formData, JSON.stringify({ userEmail: userId}));
}

//마스킹
function maskingApply(){
    let emailFiled = document.getElementById("email").value;
    let realnameField = document.getElementById("realname").value;

    let emailValue = emailFiled.value;
    emailFiled.value = maskingFunc.email(emailValue);

    //실명 마스킹
    let realnameValue = realnameField.value;
    realnameField.value = maskingFunc.name(realnameValue);

    let maskingFunc = {
        checkNull: function () {
            if (typeof str == "undefined" || str == null || str == "") {
                return true;
            } else {
                return false;
            }
        },

        //이메일 마스킹
        email: function (str) {
            let originStr = str;
            let emailStr = originStr.match(/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi);
            let strLength;
            if (this.checkNull(originStr) == true || this.checkNull(emailStr) == true) {
                return originStr;
            } else {
                strLength = emailStr.toString().split('@')[0].length - 3;
                return originStr.toString().replace(new RegExp('.(?=.{0,' + strLength + '}@)', 'g'), '*').replace(/.{6}$/, "******");
            }
        },

        //핸드폰 번호 마스킹
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
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{3})(\d{4})/gi, '$1***$3'));
                } else {
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{4})(\d{4})/gi, '$1****$3'));
                }
            } else {
                phoneStr = originStr.match(/\d{2,3}-\d{3,4}-\d{4}/gi);
                if (this.checkNull(phoneStr) == true) {
                    return originStr;
                }
                if (/-[0-9]{3}-/.test(phoneStr)) {
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{3}-/g, "-***-"));
                } else if (/-[0-9]{4}-/.test(phoneStr)) {
                    maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{4}-/g, "-****-"));
                }
            }
            return maskingStr;
        },

        //주민번호 마스킹
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
                maskingStr = originStr.toString().replace(rrnStr, rrnStr.toString().replace(/(-?)([1-4]{1})([0-9]{6})\b/gi, "$1$2******"));
            } else {
                rrnStr = originStr.match(/\d{13}/gi);
                if (this.checkNull(rrnStr) == false) {
                    strLength = rrnStr.toString().split('-').length;
                    maskingStr = originStr.toString().replace(rrnStr, rrnStr.toString().replace(/([0-9]{6})$/gi, "******"));
                } else {
                    return originStr;
                }
            }
            return maskingStr;
        },

        //실명(이름) 마스킹
        name: function (str) {
            let originStr = str;
            let maskingStr;
            let strLength;
            if (this.checkNull(originStr) == true) {
                return originStr;
            }
            strLength = originStr.length;
            if (strLength < 3) {
                maskingStr = originStr.replace(/(?<=.{1})./gi, "*");
            } else {
                maskingStr = originStr.replace(/(?<=.{2})./gi, "*");
            }
            return maskingStr;
        }
    }
}





