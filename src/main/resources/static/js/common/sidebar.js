const sidebarButton = document.querySelector(".sidebar-button");
const sidebarDiv = document.querySelector(".sidebar-div");
sidebarButton.addEventListener('click', () => {
  if (sidebarDiv.classList.contains("divHidden")) {
    sidebarDiv.classList.remove("divHidden");
    sidebarDiv.style.transform = 'translate(0px, -10px)'
    sidebarDiv.style.opacity = 1;

  }
  else {
    sidebarDiv.classList.add("divHidden");
    sidebarDiv.style.opacity = 0;
    sidebarDiv.style.transform = 'translate(0px, 10px)'
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
  else{
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



document.addEventListener("DOMContentLoaded", () => {
  // getHomeContent();
})