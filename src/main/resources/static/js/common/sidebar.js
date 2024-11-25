const sidebarButton = document.querySelector(".sidebar-button");
const sidebarDiv = document.querySelector(".sidebar-div");

/* 사이드바 현재 위치 상태 변수 */
let sidebarViewType = 0;





sidebarButton.addEventListener('click', () => {
  if (sidebarDiv.classList.contains("divHidden")) {
    sidebarDiv.classList.remove("divHidden");
    setTimeout(() => {
      sidebarDiv.style.opacity = 1;
      sidebarDiv.style.transform = 'translate(0px, -10px)'
    }, 50);
    sidebarDiv.style.display = "block"
    if(sidebarViewType === 5){
      chatRead();
    }

  }
  else {
    sidebarDiv.classList.add("divHidden");
    sidebarDiv.style.opacity = 0;
    sidebarDiv.style.transform = 'translate(0px, 10px)'
    setTimeout(() => {
      sidebarDiv.style.display = "none"
    }, 50);
  }
})

const sidebarHome = document.querySelector(".sidebar-div-footer-1");
const sidebarChat = document.querySelector(".sidebar-div-footer-2");
const sidebarPro = document.querySelector(".sidebar-div-footer-3");

let sidebarIndex = 1;
sidebarHome.classList.add("sidebar-footer-click");

sidebarHome.addEventListener('click', () => {
  if (sidebarIndex === 1) {
    return;
  }
  sidebarIndex = 1;
  sidebarHome.classList.add("sidebar-footer-click");
  sidebarChat.classList.remove("sidebar-footer-click");
  sidebarPro.classList.remove("sidebar-footer-click");
  document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
  // document.querySelector(".sidebar-div-content").style.opacity='0';

  getHomeContent();

  // document.querySelector(".sidebar-div-content").style.opacity='1';

})

sidebarChat.addEventListener("click", () => {
  if (sidebarIndex === 2) {
    return;
  }
  sidebarIndex = 2;
  sidebarHome.classList.remove("sidebar-footer-click");
  sidebarChat.classList.add("sidebar-footer-click");
  sidebarPro.classList.remove("sidebar-footer-click");
  // document.querySelector(".sidebar-div-content").style.opacity='0';
  document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
  getChatContent();
  
  // document.querySelector(".sidebar-div-content").style.opacity='1';

})

sidebarPro.addEventListener('click', () => {
  if (sidebarIndex === 3) {
    return;
  }
  sidebarIndex = 3;
  sidebarHome.classList.remove("sidebar-footer-click");
  sidebarChat.classList.remove("sidebar-footer-click");
  sidebarPro.classList.add("sidebar-footer-click");
  document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
  getProContent();
  setTimeout(() => {
    getSidebarProfileBtn();
  }, 200);
})

const getHomeContent = () => {
  fetch("/sidebarHome")
    .then(Response => {
      if (Response.ok) {
        return Response.text();
      }
      throw new Error("홈 버튼 불러오기 실패")
    })
    .then(html => {
      setTimeout(() => {
        document.querySelector(".sidebar-div-content").innerHTML = html;
        document.querySelector(".sidebar-div-content").classList.add('sidebar-div-content-view');
        getHomeMessageBtn();
  
      }, 100);
      sidebarViewType = 1;
    })
    .catch(err => {
      console.log(err);
    })
}

const getChatContent = () => {
  fetch("/sidebarChat")
    .then(Response => {
      if (Response.ok) {
        return Response.text();
      }
      throw new Error("채팅 버튼 불러오기 실패")
    })
    .then(html => {
      setTimeout(() => {
        document.querySelector(".sidebar-div-content").innerHTML = html;
        document.querySelector(".sidebar-div-content").classList.add('sidebar-div-content-view');
        getChattingDetailClick();
        if (aiMessageBackup !== '') {
          strIndex = aiMessageBackup.lastIndexOf('<div class="chattingArea" style="opacity: 1;">')
          backUpStr1 = aiMessageBackup.substring(strIndex + 120, aiMessageBackup.length - 12)
          if(backUpStr1.length > 40){
            backUpStr = backUpStr1.substring(0,40) + "..."
          }
          else{
            backUpStr = backUpStr1
          }
          document.querySelector(".chat-message-backup").innerHTML = backUpStr;
        }
          getAdminChatList();

      }, 100);
      sidebarViewType = 2;
    })
    .catch(err => {
      console.log(err);
    })
}
const getProContent = () => {
  fetch("/sidebarPro")
    .then(Response => {
      if (Response.ok) {
        return Response.text();
      }
      throw new Error("채팅 버튼 불러오기 실패")
    })
    .then(html => {
      setTimeout(() => {
        document.querySelector(".sidebar-div-content").innerHTML = html;
        document.querySelector(".sidebar-div-content").classList.add('sidebar-div-content-view');
        document.querySelector(".sidebar-profile-updateBtn")?.addEventListener("click", () => {
          location.href = '/myPage/myPageUpdate'
        })
        document.querySelector(".sidebar-profile-loginBtn")?.addEventListener("click", () => {
           location.href = '/myPage/myPageLogin'
        })
       
      }, 100);
      sidebarViewType = 3;
    })
    .catch(err => {
      console.log(err);
    })
}

const getAdminChatList = () => {
  fetch('/sidebar/chatting-admin-check?memberNo='+document.querySelector(".side-getLoginMemberNo").value)
  .then(Response => {
    if(Response.ok){
      return Response.text();
    }
    throw new Error("상담 내역 조회 실패")
  })
  .then(html => {

    document.querySelector(".inner-admin-chat").innerHTML = html;
      getAdminChatDetail();
  })
  .catch(err => {
    console.log(err);
  })
}


const getAdminChatDetail = () => {
  const adminChatlist = document.querySelectorAll(".chatting-admin-area");
  for(let i = 0; i < adminChatlist.length; i++){
    adminChatlist[i].addEventListener("click", (e) => {
      const roomNo = e.target.closest(".chatting-admin-area").dataset.chatRoomNo;
      chattingRoomNo = roomNo;
      fetch("/sidebar/adminChatDetail?ChattingRoomNo="+roomNo)
      .then(Response => {
        if(Response.ok){
          return Response.text();
        }
        throw new Error("채팅 조회 실패")
      })
      .then(html => {
        document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
          setTimeout(() => {
          document.querySelector(".sidebar-div-content").innerHTML = html;
            document.querySelector(".sidebar-chatting-detail-body-admin").scrollTop = document.querySelector(".sidebar-chatting-detail-body-admin").scrollHeight;
            getChatEnterBtn();

          




          
          document.querySelector(".sidebar-div-content").classList.add('sidebar-div-content-view');
          document.querySelector(".sidebar-chatting-back").addEventListener("click", () => {
            document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
            getChatContent();
          })
          
        }, 100);
        sidebarViewType = 5;



        chatRead()



      })
      .catch(err => {
        console.log(err);
      })

      document.querySelectorAll(".sidebar-2-ar")[0].style.color = "rgb(56, 56, 56)";
      document.querySelectorAll(".sidebar-2-ar")[1].style.color = "rgb(56, 56, 56)";




    })
  }
}



const chatRead = () => {
          /* 비동기 메시지 읽기 */
          fetch("/sidebar/chattingMessageRead?chattingRoomNo="+chattingRoomNo)
          .then(Response => {
            if(Response.ok){
              return Response.text();
            }
            throw new Error("읽기 실패")
          })
          .catch(err => {
            console.log(err);
          })

          sidebarBtnArCheck = 0;
          document.querySelector(".sidebar-button").style.backgroundColor = "#1d1d1f"
}


const getChatEnterBtn = () => {
  const btn = document.querySelector(".sidebar-chatting-detail-inputbutton")
  btn.addEventListener("click", () => {
    sendMessage();
  })
  document.querySelector(".sidebar-chatting-detail-inputbar").addEventListener("keyup", (e) => {
    if(e.key == "Enter"){
      if(!e.shiftKey){ /// shift가 눌러지지 않은 경우
                      // == shift + enter 입력 시 제출 X
        sendMessage();
      }
    }
  })

}


let sidebarMessageIndex = 0;
let sidebarEmailIndex = 0;

const getSidebarProfileBtn = () => {

  if (sidebarMessageIndex === 0) {
    document.querySelector(".sidebar-profile-messageBtn").style.backgroundColor = "rgb(71, 71, 71)";
    document.querySelector(".sidebar-profile-footer-button1").style.transform = "translate(0, 0)";
  }
  else {
    document.querySelector(".sidebar-profile-messageBtn").style.backgroundColor = "green";
    document.querySelector(".sidebar-profile-footer-button1").style.transform = "translate(19px, 0)";
  }

  if (sidebarEmailIndex === 0) {
    document.querySelector(".sidebar-profile-emailBtn").style.backgroundColor = "rgb(71, 71, 71)";
    document.querySelector(".sidebar-profile-footer-button2").style.transform = "translate(0, 0)";
  }
  else {
    document.querySelector(".sidebar-profile-emailBtn").style.backgroundColor = "green";
    document.querySelector(".sidebar-profile-footer-button2").style.transform = "translate(19px, 0)";
  }

  document.querySelector(".sidebar-profile-messageBtn").addEventListener("click", () => {
    if (sidebarMessageIndex === 0) {
      document.querySelector(".sidebar-profile-messageBtn").style.backgroundColor = "green";
      document.querySelector(".sidebar-profile-footer-button1").style.transform = "translate(19px, 0)";
      sidebarMessageIndex = 1;
    }
    else {
      document.querySelector(".sidebar-profile-messageBtn").style.backgroundColor = "rgb(71, 71, 71)";
      document.querySelector(".sidebar-profile-footer-button1").style.transform = "translate(0, 0)";
      sidebarMessageIndex = 0;
    }
  })
  document.querySelector(".sidebar-profile-emailBtn").addEventListener("click", () => {
    if (sidebarEmailIndex === 0) {
      document.querySelector(".sidebar-profile-emailBtn").style.backgroundColor = "green";
      document.querySelector(".sidebar-profile-footer-button2").style.transform = "translate(19px, 0)";
      sidebarEmailIndex = 1;
    }
    else {
      document.querySelector(".sidebar-profile-emailBtn").style.backgroundColor = "rgb(71, 71, 71)";
      document.querySelector(".sidebar-profile-footer-button2").style.transform = "translate(0, 0)";
      sidebarEmailIndex = 0;
    }
  })




}

const getHomeMessageBtn = () => {
  const messageBtn = document.querySelector(".sidebar-messageBtn");
  messageBtn.addEventListener("click", () => {
    sidebarIndex = 2;
    sidebarHome.classList.remove("sidebar-footer-click");
    sidebarChat.classList.add("sidebar-footer-click");
    sidebarPro.classList.remove("sidebar-footer-click");
    document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');

    getHomeMessageBtnClick()

  })
}

const getHomeMessageBtnClick = () => {
  fetch("/sidebarChatDetail")
    .then(Response => {
      if (Response.ok) {
        return Response.text();
      }
      throw new Error("채팅창 불러오기 실패");
    })
    .then(html => {
      setTimeout(() => {
        document.querySelector(".sidebar-div-content").innerHTML = html;
        document.querySelector(".sidebar-div-content").classList.add('sidebar-div-content-view');
        document.querySelector(".sidebar-chatting-back").addEventListener("click", () => {
          document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
          getChatContent();
        })
        getMyMessageBtn();
      }, 100);
      sidebarViewType = 4;
    })
    .catch(err => {
      console.log(err);
    })

}

const getChattingDetailClick = () => {
  const chatList = document.querySelector(".sidebar-div-content-chatting").children[0];
  chatList.addEventListener("click", () => {
    document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
    getHomeMessageBtnClick();
  })
}

let aiMessageBackup = "";

const getMyMessageBtn = () => {

  if (aiMessageBackup !== '') {
    document.querySelector(".sidebar-chatting-detail-body").innerHTML = aiMessageBackup;
    document.querySelector(".sidebar-chatting-detail-body").scrollTop = document.querySelector(".sidebar-chatting-detail-body").scrollHeight;
  }

  document.querySelector(".chattingArea").style.opacity = 1;

  const btn1 = document.querySelector(".chatting-qlist").children[0];
  btn1.addEventListener("click", () => {

    const area = document.createElement("div")
    area.classList.add("chattingArea")
    area.style.opacity = 0



    const div = document.createElement("div");
    div.innerText = btn1.innerText;
    div.classList.add("chatting-right")

    area.append(div)

    setTimeout(() => {
      area.style.opacity = 1
      document.querySelector(".sidebar-chatting-detail-body").scrollTop = document.querySelector(".sidebar-chatting-detail-body").scrollHeight;
    }, 50);

    document.querySelector(".sidebar-chatting-detail-body").append(area);

    document.querySelector(".chatting-qlist").style.display = "none";
    document.querySelector(".chatting-qlist").style.opacity = 0;

    setTimeout(() => {

      const area = document.createElement("div")
      area.classList.add("chattingArea")
      area.style.opacity = 0;

      const img = document.createElement("img");
      img.classList.add("chatting-img")
      img.src = "/images/상담.jpg";

      area.append(img);

      const div = document.createElement("div");
      div.innerText = "기본 배송지는 서울특별시 중구 남대문로 120 그레이츠 청계(구 대일빌딩) 2F입니다"
      div.classList.add("chatting-left")

      area.append(div)

      document.querySelector(".sidebar-chatting-detail-body").append(area);
      setTimeout(() => {
        area.style.opacity = 1;
      }, 50);
      document.querySelector(".chatting-qlist").style.display = "flex";
      document.querySelector(".chatting-qlist").style.opacity = 1;
      document.querySelector(".sidebar-chatting-detail-body").scrollTop = document.querySelector(".sidebar-chatting-detail-body").scrollHeight;
      setTimeout(() => {
        aiMessageBackup = document.querySelector(".sidebar-chatting-detail-body").innerHTML;
      }, 50);
    }, 1000);


  })


  const btn2 = document.querySelector(".chatting-qlist").children[1];
  btn2.addEventListener("click", () => {

    const area = document.createElement("div")
    area.classList.add("chattingArea")
    area.style.opacity = 0



    const div = document.createElement("div");
    div.innerText = btn2.innerText;
    div.classList.add("chatting-right")

    area.append(div)

    setTimeout(() => {
      area.style.opacity = 1
      document.querySelector(".sidebar-chatting-detail-body").scrollTop = document.querySelector(".sidebar-chatting-detail-body").scrollHeight;
    }, 50);

    document.querySelector(".sidebar-chatting-detail-body").append(area);

    document.querySelector(".chatting-qlist").style.display = "none";
    document.querySelector(".chatting-qlist").style.opacity = 0;

    setTimeout(() => {

      const area = document.createElement("div")
      area.classList.add("chattingArea")
      area.style.opacity = 0;

      const img = document.createElement("img");
      img.classList.add("chatting-img")
      img.src = "/images/상담.jpg";

      area.append(img);

      const div = document.createElement("div");
      div.innerText = "상단의 판매하기 메뉴에서 원하시는 기종 선택 후 클릭하시면 판매 페이지로 이동할 수 있습니다"
      div.classList.add("chatting-left")

      area.append(div)

      document.querySelector(".sidebar-chatting-detail-body").append(area);
      setTimeout(() => {
        area.style.opacity = 1;
      }, 50);
      document.querySelector(".chatting-qlist").style.display = "flex";
      document.querySelector(".chatting-qlist").style.opacity = 1;
      document.querySelector(".sidebar-chatting-detail-body").scrollTop = document.querySelector(".sidebar-chatting-detail-body").scrollHeight;
      setTimeout(() => {
        aiMessageBackup = document.querySelector(".sidebar-chatting-detail-body").innerHTML;
      }, 50);
    }, 1000);


  })




  const btn3 = document.querySelector(".chatting-qlist").children[2];
  btn3.addEventListener("click", () => {

    const area = document.createElement("div")
    area.classList.add("chattingArea")
    area.style.opacity = 0



    const div = document.createElement("div");
    div.innerText = btn3.innerText;
    div.classList.add("chatting-right")

    area.append(div)

    setTimeout(() => {
      area.style.opacity = 1
      document.querySelector(".sidebar-chatting-detail-body").scrollTop = document.querySelector(".sidebar-chatting-detail-body").scrollHeight;
    }, 50);

    document.querySelector(".sidebar-chatting-detail-body").append(area);

    document.querySelector(".chatting-qlist").style.display = "none";
    document.querySelector(".chatting-qlist").style.opacity = 0;

    setTimeout(() => {

      const area = document.createElement("div")
      area.classList.add("chattingArea")
      area.style.opacity = 0;

      const img = document.createElement("img");
      img.classList.add("chatting-img")
      img.src = "/images/상담.jpg";

      area.append(img);

      const div = document.createElement("div");
      div.innerText = "입금 계좌는 은행  XXX-YY-ZZZZZZC 입니다"
      div.classList.add("chatting-left")

      area.append(div)

      document.querySelector(".sidebar-chatting-detail-body").append(area);
      setTimeout(() => {
        area.style.opacity = 1;
      }, 50);
      document.querySelector(".chatting-qlist").style.display = "flex";
      document.querySelector(".chatting-qlist").style.opacity = 1;
      document.querySelector(".sidebar-chatting-detail-body").scrollTop = document.querySelector(".sidebar-chatting-detail-body").scrollHeight;
      setTimeout(() => {
        aiMessageBackup = document.querySelector(".sidebar-chatting-detail-body").innerHTML;
      }, 50);
    }, 1000);


  })

  const btn4 = document.querySelector(".admin-chat-conn");
  btn4.addEventListener("click", () => {

    if(notificationLoginCheck == false){
      location.href = "/myPage/myPageLogin";
    }

    // 채팅방 만들고 호출
    createChatRoom()
    sidebarViewType = 5;

  })

}


const createChatRoom = () => {
  fetch('/sidebar/createChatRoom')
  .then(Response => {
    if(Response.ok){
      return Response.text();
    }
    throw new Error("채팅방 생성 실패")
  })
  .then(result => {

    console.log(result);

    if(result > 0){

      chattingRoomNo = result;
      fetch("/sidebar/adminChatDetail?ChattingRoomNo="+result)
        .then(Response => {
          if(Response.ok){
            return Response.text();
          }
          throw new Error("채팅 조회 실패")
        })
        .then(html => {
          document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
            setTimeout(() => {
            document.querySelector(".sidebar-div-content").innerHTML = html;
              document.querySelector(".sidebar-chatting-detail-body-admin").scrollTop = document.querySelector(".sidebar-chatting-detail-body-admin").scrollHeight;
              getChatEnterBtn();

            




            
            document.querySelector(".sidebar-div-content").classList.add('sidebar-div-content-view');
            document.querySelector(".sidebar-chatting-back").addEventListener("click", () => {
              document.querySelector(".sidebar-div-content").classList.remove('sidebar-div-content-view');
              getChatContent();
            })
            
          }, 100);
        })

    }
    else{
      alert("채팅방 생성 실패")
    }

  })
  


  

}













let chattingSock;
let chattingRoomNo = 0;
let sidebarBtnArCheck = 0;

/* 사이드바 알림 추가 함수 */
const sidebarBtnAr = () => {

  if(sidebarBtnArCheck === 1){
    document.querySelector(".sidebar-button").style.backgroundColor = "red"
  }
  else{
    return;
  }
}

// 로그인이 되어있을 경우 
if(notificationLoginCheck){ // common.html에 선언된 전역 변수

  // 서버로 ws://chattingSock 요청
  // -> 요청으로 처리하는 WebSockHandler와 연결
  //  --> WebSockHandler에 연결된 회원의 정보를 모아두게 된다!!!
  chattingSock = new SockJS("/chattingSock");

  /* 비동기로 안읽은 채팅 개수 가져와서 있으면 알림 */
  fetch("/sidebar/firstArCheck?memberNo="+notificationLoginNo)
  .then(Response => {
    if(Response.ok){
      return Response.text();
    }
    throw new Error("알림 조회 실패")
  })
  .then(result => {

    if(result > 0){

      /* 알림 있으면 사이드바 버튼과 사이드바 채팅버튼, 안읽은 메시지 개수(자동) 조회 */
      sidebarBtnArCheck = 1;
      sidebarBtnAr();
      document.querySelectorAll(".sidebar-2-ar")[0].style.color = "red";
      document.querySelectorAll(".sidebar-2-ar")[1].style.color = "red";
    }

  })



}


const sendMessage = () => {

  const senderNo = notificationLoginNo;
  let receiverNo = 0;

  if(document.querySelector(".chat-room-member1").value == senderNo){
    receiverNo = document.querySelector(".chat-room-member2").value;
  }
  else{
    receiverNo = document.querySelector(".chat-room-member1").value;
  }
  const msg = document.querySelector(".sidebar-chatting-detail-inputbar").value.trim();

  if(msg.length === 0)  {
    alert("메시지 입력")
    document.querySelector(".sidebar-chatting-detail-inputbar").value = '';
    return;
  }




  const chattingObj = {
    "receiverNo" : receiverNo,
    "chattingMessageContent" : msg,
    "chattingRoomNo" : chattingRoomNo,
    "senderNo" : senderNo
  }

  console.log(chattingObj);

  chattingSock.send( JSON.stringify(chattingObj) );

  document.querySelector(".sidebar-chatting-detail-inputbar").value = '';

}





/* 연결된 웹소켓 객체를 통해 서버로 부터 메시지를 전달 받은 경우 */
if(chattingSock != undefined){

  chattingSock.addEventListener("message", e => {
    console.log(e.data);

    // 메소드를 통해 전달받은 JSON을 JS Object로 변환해서 msg 변수에 저장.
    const msg = JSON.parse(e.data); 
    console.log(msg);




    /* 현재 채팅방을 보고 있을 떄 */
    if(sidebarViewType === 5){
    const chatArea = document.createElement("div")
    chatArea.classList.add("chattingAreaAdmin");
    chatArea.style.opacity = 0;

    // 내가 보냄
    if(msg.senderNo == notificationLoginNo){

      const div = document.createElement("div");
      div.classList.add("chatting-receiver-area")
      div.innerText = msg.chattingMessageContent;

      chatArea.append(div)
      document.querySelector(".sidebar-chatting-detail-body-admin").append(chatArea)
    }
    // 내가 받음
    else{

      const div1 = document.createElement("div");

      const div2 = document.createElement("div");
      const img1 = document.createElement("img");
      const div3 = document.createElement("div");

      div2.classList.add("chatting-receiver-div1")
      img1.classList.add("chatting-receiver-img1")
      img1.src = "/images/상담.jpg";
      div3.classList.add("chatting-sender-name");
      div3.innerText = msg.senderName;

      div1.append(div2)
      div2.append(img1)
      div2.append(div3)

      const div4 = document.createElement("div");
      div4.classList.add("chatting-receiver-div2")
      div4.innerText = msg.chattingMessageContent;

      div1.append(div4)

      chatArea.append(div1)

      document.querySelector(".sidebar-chatting-detail-body-admin").append(chatArea)

      
    }
      document.querySelector(".sidebar-chatting-detail-body-admin").lastChild.style.opacity=1;

    document.querySelector(".sidebar-chatting-detail-body-admin").scrollTop = document.querySelector(".sidebar-chatting-detail-body-admin").scrollHeight;

  }

  /* 현재 홈 화면 보고 있을 떄 */
  if(sidebarViewType === 1){

    // 채팅이랑 사이드바 알림 표시
    document.querySelectorAll(".sidebar-2-ar")[0].style.color = "red";
    document.querySelectorAll(".sidebar-2-ar")[1].style.color = "red";

    sidebarBtnArCheck = 1;
    sidebarBtnAr();

  }

  /* 현재 채팅 화면 보고 있을 떄 */
  if(sidebarViewType === 2){

    // 채팅이랑 사이드바 알림 표시
    document.querySelectorAll(".sidebar-2-ar")[0].style.color = "red";
    document.querySelectorAll(".sidebar-2-ar")[1].style.color = "red";

    getAdminChatList();

    sidebarBtnArCheck = 1;
    sidebarBtnAr();
  }
  /* 현재 프로필 화면 보고 있을 떄 */
  if(sidebarViewType === 3){

    // 채팅이랑 사이드바 알림 표시
    document.querySelectorAll(".sidebar-2-ar")[0].style.color = "red";
    document.querySelectorAll(".sidebar-2-ar")[1].style.color = "red";

    sidebarBtnArCheck = 1;
    sidebarBtnAr();

  }
  /* 현재 ai봇 화면 보고 있을 떄 */
  if(sidebarViewType === 4){

    // 채팅이랑 사이드바 알림 표시
    document.querySelectorAll(".sidebar-2-ar")[0].style.color = "red";
    document.querySelectorAll(".sidebar-2-ar")[1].style.color = "red";

    sidebarBtnArCheck = 1;
    sidebarBtnAr();

  }

  if(sidebarViewType === 5){
    if(document.querySelector(".sidebar-div ").classList.contains("divHidden")){
       document.querySelector(".sidebar-button").style.backgroundColor = "red";
       return;
    }
      chatRead();
  }

















    // // 현재 채팅방을 보고있는 경우
    // if(selectChattingNo == msg.chattingRoomNo){


    //   const ul = document.querySelector(".display-chatting");
    
    //   // 메세지 만들어서 출력하기
    //   //<li>,  <li class="my-chat">
    //   const li = document.createElement("li");
    
    //   // 보낸 시간
    //   const span = document.createElement("span");
    //   span.classList.add("chatDate");
    //   span.innerText = msg.sendTime;
    
    //   // 메세지 내용
    //   const p = document.createElement("p");
    //   p.classList.add("chat");
    //   p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML
    
    //   // 내가 작성한 메세지인 경우
    //   if(loginMemberNo == msg.senderNo){ 
    //     li.classList.add("my-chat");
        
    //     li.append(span, p);
        
    //   }else{ // 상대가 작성한 메세지인 경우
    //     li.classList.add("target-chat");
    
    //     // 상대 프로필
    //     const img = document.createElement("img");
    //     img.setAttribute("src", selectTargetProfile);
        
    //     const div = document.createElement("div");
    
    //     // 상대 이름
    //     const b = document.createElement("b");
    //     b.innerText = selectTargetName; // 전역변수
    
    //     const br = document.createElement("br");
    
    //     div.append(b, br, p, span);
    //     li.append(img,div);
    
    //   }
    
    //   ul.append(li)
    //   ul.scrollTop = ul.scrollHeight; // 스크롤 제일 밑으로
    // }

    // selectRoomList();

  })
}




















document.addEventListener("DOMContentLoaded", () => {
  getHomeContent();
})