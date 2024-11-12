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
    })

    sidebarChat.addEventListener("click", () => {
      if (sidebarIndex === 2) {
        return;
      }
      sidebarIndex = 2;
      sidebarHome.classList.remove("sidebar-footer-click");
      sidebarChat.classList.add("sidebar-footer-click");
      sidebarPro.classList.remove("sidebar-footer-click");

    })

    sidebarPro.addEventListener('click', () => {
      if (sidebarIndex === 3) {
        return;
      }
      sidebarIndex = 3;
      sidebarHome.classList.remove("sidebar-footer-click");
      sidebarChat.classList.remove("sidebar-footer-click");
      sidebarPro.classList.add("sidebar-footer-click");
    })