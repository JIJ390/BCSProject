const connectSse = () => {

  console.log(notificationLoginCheck);
  console.log(notificationLoginCheck);

  /* 로그인이 되어있지 않은 경우 함수 종료 */
  if (notificationLoginCheck === false) return;

  console.log("connectSse() 호출");

  // 서버의 "/sse/connect" 주소로 연결 요청
  const eventSource = new EventSource("/sse/connect");

  // --------------------------------------

  /* 서버로 부터 메시지가 왔을 경우 */
  eventSource.addEventListener("message", e => {
    console.log(e.data); // e.data == 전달 받은 메시지

    getMemberPagenation();
    getMemberSearchList();

    // -> Spring HTTPMessageConverter가
    //    JSON 을 변환해서 응답해줌

    // const obj = JSON.parse(e.data);
    // console.log(obj);

    // 알림 개수 표시
    // const notificationCountArea = document.querySelector(".notification-count-area");
    // notificationCountArea.innerText = obj.notiCount;


  });

  /* 서버 연결이 종료된 경우(타임아웃 초과) */
  eventSource.addEventListener("error", () => {
    console.log("SSE 재연결 시도");

    eventSource.close(); // 기존 연결 닫기

    setTimeout(() => {
      connectSse();
    }, 5000);
  })


  eventSource.addEventListener("message", e => {
    console.log(e.data); // e.data : 전달 받은 메시지
  });

};

const sendNotification = (pkNo) => {

  /* 로그인이 되어있지 않은 경우 함수 종료 */
  if (notificationLoginCheck === false) return;

  fetch("/sse/send", {
    method : "POST",
    headers : {"Content-Type": "application/json"},
    body : pkNo
  })
  .then(Response => {
    if(!Response.ok){
      throw new Error ("알림 전송 실패")
    }
    console.log("알림 전송 성공");
  })
  .catch(err => {
    console.log(err);
  })

}

const getMessageReadCount = () => {

  const messageCount = 0;
  /* 로그인이 되어있지 않은 경우 함수 종료 */
  if (notificationLoginCheck === false) {
    return;
  }
  fetch("/sse/searchNewList")
  .then(Response => {
    if(Response.ok){
      return Response.text();
    }
    throw new Error("알림 조회 실패")
  })
  .then(count => {
    messageCount = count;

    if(messageCount > 0){
      
    }





  })

}