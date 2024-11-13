// 준비중 버튼 눌르면 뷰 열리는 버튼
const listBtn = document.querySelectorAll(".list-btn");
// 숨기는 클래스
const statusHiden = document.querySelectorAll(".status-hiden");
/* 뷰 열리는지 화살표 확인 */
const arrow = document.querySelectorAll(".arrow");
// --------------------------------------------------------- 전역변수

for (let i = 0; i < listBtn.length; i++) {
    listBtn[i]?.addEventListener("click", () => {
        if (statusHiden[i].style.display === "none") {
            statusHiden[i].style.display = "block";

            arrow[i].innerText = "▼";
        } else {
            statusHiden[i].style.display = "none";
            arrow[i].innerText = "△";
        }
    });
}



/* p 태그 감싸고 있는 div */
const divElement = document.querySelectorAll(".status");
for (let e = 0; e < divElement.length; e++) {
    /* p 태그 */
    const pElements = divElement[e].querySelectorAll("p");
    /* p 태그 반복문 */
    for (let i = 0; i < pElements.length; i++) {
        pElements[i].addEventListener("click", () => {
            const text = pElements[i].textContent;
            // console.log(text);
            listBtn[e].innerText = text;
        })
    }
}

for (let i = 0; i < listBtn.length; i++) {
    listBtn[i]?.addEventListener("click", () => {
        // 열려있는 뷰를 모두 닫음
        for (let j = 0; j < statusHiden.length; j++) {
            statusHiden[j].style.display = "none";
            arrow[j].innerText = "△";
        }

        // 클릭한 뷰를 열음
        if (statusHiden[i].style.display === "none") {
            statusHiden[i].style.display = "block";
            arrow[i].innerText = "▼";
        }
    });
}
// 바깥쪽 영역을 눌렀을 때 뷰가 꺼지게 하기
document.addEventListener("click", (event) => {
    if (!event.target.closest(".list-btn") ) {
      for (let i = 0; i < statusHiden.length; i++) {
        statusHiden[i].style.display = "none";
        arrow[i].innerText = "△";
      }
    }
  });





