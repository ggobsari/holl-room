var stompClient = null;
var senderId = "",
    myId = "",
    roomId = "";
var dataExist = false;

function setChatData(room_id, sender_id, my_id) {
//  alert("setChatData() : " + sender_id + " , " + my_id);
  roomId = room_id;
  senderId = sender_id;
  myId = my_id;
  $.ajax({
    url : "/hollroom/chat/chatlist",
    type : "get",
    data : {"roomId" : room_id},
    success : function(chatlist) {
//      alert("ajax 성공");
      if (chatlist.length == 0) {
        alert(chatlist.length);
      } else {
        dataExist = true;
        for(let i = 0; i < chatlist.length; i++) {
//          alert("1: " + typeof chatlist[i].createDate + ": " + chatlist[i].createDate); //string
          let datetime = Date.parse(chatlist[i].createDate);
//          alert("2: " + typeof datetime + ": " + datetime);  //number
          let time = new Intl.DateTimeFormat('ko-KR', {
              hour12: false,
              month: "numeric",
              day: "numeric",
              hour: "numeric",
              minute: "numeric",
              timeZone: 'Asia/Seoul',
          }).format(datetime);
          alert("3: " + typeof time + ": " + time);
          if (chatlist[i].sender == my_id) {
            $("#tmp-area").append(
              "<div class='chat-area-right'>"
              + "<span class='chat-time'>" + time + "</span>"
              + "<span class='chat-text-common chat-text-right'>" + chatlist[i].message + "</span>"
              + "</div>"
            );
          } else {
            $("#tmp-area").append(
              "<div class='chat-area-left'>"
              + "<span class='chat-nickname'>" + $("#user-nick").text() + "</span>"
              + "<span class='chat-text-common chat-text-left'>" + chatlist[i].message + "</span>"
              + "<span class='chat-time'>" + time + "</span>"
              + "</div>"
            );
          }
        }
      }
    },
    error: function (request, status, error) {
      alert("ajax 에러");
      console.log("code: " + request.status)
      console.log("message: " + request.responseText)
      console.log("error: " + error);
    }
  });

  connect();
}

function connect() {
//   alert(roomId + " <- " + myId);
   // 접속
   var socket = new SockJS('/hollroom/websocket/start');
   stompClient = Stomp.over(socket);
   stompClient.connect({}, function (frame) {
       console.log('Connected: ' + frame);
       // 구독 신청 (sub 등록)
       stompClient.subscribe('/sub/chat/room/' + roomId, onMessageReceived);
//       alert("subscribe ok");
//       if (!dataExist) {
//         alert("if in");
//         stompClient.send("/pub/chat/enterUser",
//               {},
//               JSON.stringify({
//                   "roomId": roomId,
//                   "userId": myId
//               })
//         )
//       }
   });
}

// 브로커가 전달해준 payload가 매개변수로 전달
function onMessageReceived(payload) {
  console.log("payload: " + payload);
  let chatMsg = JSON.parse(payload.body);

  let datetime = Date.parse(chatMsg.createDate);
  let time = new Intl.DateTimeFormat('ko-KR', {
      hour12: false,
      month: "numeric",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      timeZone: 'Asia/Seoul',
  }).format(datetime);
  if (chatMsg.sender == myId) {
    $("#tmp-area").append(
      "<div class='chat-area-right'>"
      + "<span class='chat-time'>" + time + "</span>"
      + "<span class='chat-text-common chat-text-right'>" + chatMsg.message + "</span>"
      + "</div>"
    );
  } else {
    $("#tmp-area").append(
      "<div class='chat-area-left'>"
      + "<span class='chat-nickname'>" + $("#user-nick").text() + "</span>"
      + "<span class='chat-text-common chat-text-left'>" + chatMsg.message + "</span>"
      + "<span class='chat-time'>" + time + "</span>"
      + "</div>"
    );
  }
}

function sendChat() {
//    alert("test: " + roomId + " ------- " + myId);
    if ($("#input-message").val() != "") {
        console.log("/pub/" + roomId)
        console.log(stompClient)
        stompClient.send("/pub/" + roomId, {},
            JSON.stringify({
                'roomId': roomId,
                'sender': myId,
                'message': $("#input-message").val()
            })
        );
        $("#input-message").val("");
    }
}

$(document).ready(function() {
    $("#input-message").keydown(function(e) {
        let key = e.key || e.keyCode;
        if ((key === "Enter" && !e.shiftKey) || (key === 13 && key !== 16)) {
          e.preventDefault();
//          alert("전송");
          var socket = new SockJS('/hollroom/websocket/start');
          stompClient = Stomp.over(socket);
          stompClient.connect({}, function (frame) {
              console.log('Connected:' + frame);
              sendChat();
          });
          return false;
        }
    });
});