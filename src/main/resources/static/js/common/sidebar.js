const sidebarButton = document.querySelector(".sidebar-button");
const sidebarDiv = document.querySelector(".sidebar-div");
sidebarButton.addEventListener('click', () => {
  if (sidebarDiv.classList.contains("divHidden")) {
    sidebarDiv.classList.remove("divHidden");
    setTimeout(() => {
      sidebarDiv.style.opacity = 1;
      sidebarDiv.style.transform = 'translate(0px, -10px)'
    }, 50);
    sidebarDiv.style.display = "block"

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
        if (aiMessageBackup !== '') {
          strIndex = aiMessageBackup.lastIndexOf('<div class="chattingArea" style="opacity: 1;">')
          backUpStr = aiMessageBackup.substring(strIndex + 120, aiMessageBackup.length - 12)
          document.querySelector(".chat-message-backup").innerHTML = backUpStr;
        }
        getHomeMessageBtn();
      }, 100);

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
          backUpStr = aiMessageBackup.substring(strIndex + 120, aiMessageBackup.length - 12)
          document.querySelector(".chat-message-backup").innerHTML = backUpStr;
        }
      }, 100);
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
        document.querySelector(".chat-message-backup").innerHTML = backUpStr;
      }, 100);
    })
    .catch(err => {
      console.log(err);
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
      div.innerText = "상단의 판매하기 메뉴에서 판매하세요"
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


}







document.addEventListener("DOMContentLoaded", () => {
  getHomeContent();
})