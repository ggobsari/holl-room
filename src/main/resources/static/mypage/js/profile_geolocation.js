document.addEventListener("DOMContentLoaded", function () {
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
        };

    //지도 표시
    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 지도 확대 축소를 제어할 수 있는 줌 컨트롤을 생성합니다
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

    // 클릭한 위치를 표시할 마커입니다
    var marker = new kakao.maps.Marker();

    // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
    if (navigator.geolocation) {

        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function(position) {

            var lat = position.coords.latitude, // 위도
                lon = position.coords.longitude; // 경도

            var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다

            // 마커와 인포윈도우를 표시합니다
            displayMarker(locPosition, message);
            displayMarkerAddress(locPosition);
        });

    } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

        var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
            message = 'geolocation을 사용할수 없어요..'

        displayMarker(locPosition, message);
        displayMarkerAddress(locPosition);
    }

    document.getElementById("address_search").addEventListener("click", AddressSearch)

    //이벤트===============================================================================================================
    // 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
        searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var detailAddr = !!result[0].road_address ? result[0].road_address.address_name + '<br>' : '';
                detailAddr += result[0].address.address_name;

                // 마커를 클릭한 위치에 표시합니다
                marker.setPosition(mouseEvent.latLng);
                marker.setMap(map);

                // 마커의 좌표를 기반으로 주소 정보를 지도 좌측 상단에 표시합니다
                var infoDiv = document.getElementById('centerAddr');
                infoDiv.innerHTML = detailAddr;
            }
        });
    });
    //함수==================================================================================================
    // 마커의 좌표를 기반으로 주소 정보를 지도 좌측 상단에 표시하는 함수입니다
    function displayMarkerAddress(coords) {
        searchDetailAddrFromCoords(coords, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var infoDiv = document.getElementById('centerAddr');
                if (result[0].road_address) {
                    infoDiv.innerHTML = result[0].road_address.address_name + "<br>" + result[0].address.address_name;
                } else {
                    infoDiv.innerHTML = result[0].address.address_name;
                }
            }
        });
    }
    //======================================================================================================
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    function searchDetailAddrFromCoords(coords, callback) {
        geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
    }
    //======================================================================================================
    // 지도에 마커와 인포윈도우를 표시하는 함수입니다
    function displayMarker(locPosition, message) {
        // 마커 이미지 설정
        var imageSrc = '/hollroom/mypage/img/map-marker-icon_34392.png', // 빨간색 마커 이미지 URL 서버 연결하면 바꾸기!!!
            imageSize = new kakao.maps.Size(35, 39), // 마커 이미지의 크기
            imageOption = {offset: new kakao.maps.Point(16, 42)}; // 마커 이미지의 옵션, 마커의 좌표와 일치시킬 부분 설정

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: locPosition,
            image: markerImage
        });

        var iwContent = message, // 인포윈도우에 표시할 내용
            iwRemoveable = true;

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
            content : iwContent,
            removable : iwRemoveable
        });

        // 인포윈도우를 마커위에 표시합니다
        infowindow.open(map, marker);

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);
    }
    //======================================================================================================
    // 지도에 마커와 인포윈도우를 표시하는 함수입니다
    function displayMarker(locPosition, message) {
        // 마커 이미지 설정
        var imageSrc = '/hollroom/mypage/img/map-marker-icon_34392.png',
            // 빨간색 마커 이미지 URL 서버 연결하면 바꾸기!!!
            imageSize = new kakao.maps.Size(35, 39), // 마커 이미지의 크기
            imageOption = {offset: new kakao.maps.Point(16, 42)}; // 마커 이미지의 옵션, 마커의 좌표와 일치시킬 부분 설정

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: locPosition,
            image: markerImage
        });

        var iwContent = message, // 인포윈도우에 표시할 내용
            iwRemoveable = true;

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
            content : iwContent,
            removable : iwRemoveable
        });

        // 인포윈도우를 마커위에 표시합니다
        infowindow.open(map, marker);

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);
    }
    //===========================================================================================
    //주소검색
    function AddressSearch() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address; // 최종 주소 변수

                // 주소 정보를 해당 필드에 넣는다.
                document.getElementById("userLocal").value = addr;
                // 주소로 상세 정보를 검색
                geocoder.addressSearch(data.address, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {

                        var result = results[0]; //첫번째 결과의 값을 활용

                        var imageSrc = '/hollroom/mypage/img/map-marker-icon_34392.png',
                            // 빨간색 마커 이미지 URL 서버 연결하면 바꾸기!!!
                            imageSize = new kakao.maps.Size(35, 39), // 마커 이미지의 크기
                            imageOption = {offset: new kakao.maps.Point(16, 42)}; // 마커 이미지의 옵션, 마커의 좌표와 일치시킬 부분 설정

                        // 마커 이미지를 생성합니다
                        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

                        // 마커를 생성합니다
                        var marker = new kakao.maps.Marker({
                            map: map,
                            position: locPosition,
                            image: markerImage
                        });

                        // 해당 주소에 대한 좌표를 받아서
                        var coords = new daum.maps.LatLng(result.y, result.x);
                        // 지도를 보여준다.
                        mapContainer.style.display = "block";
                        map.relayout();
                        // 지도 중심을 변경한다.
                        map.setCenter(coords);
                        // 마커를 결과값으로 받은 위치로 옮긴다.
                        marker.setPosition(coords);

                        // 검색된 주소의 상세 정보를 지도 좌측 상단에 표시한다.
                        displayMarkerAddress(coords);

                    }
                });
            }
        }).open({
            left: (window.screen.width / 2) - (500 / 2),
            top: (window.screen.height / 2) - (600 / 2)
        });
    }
});
