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
for (let e = 0; e < divElement.length; e++) {
    const divElement = document.querySelectorAll(".status");
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


// --------------------------------------------------------- 오버뷰다른요소 클릭시 꺼짐
document.addEventListener("DOMContentLoaded", () => {
    addEventListener("click", e => {
        console.log(e.target)

        if (e.target.classList.contains("status")) return;

        const elements = status?.querySelectorAll("*");

        let flag = true;
        elements?.forEach(item => { // 클릭된 요소가 열려있는 신고 팝업의 후손인 경우 
            if (item == e.target) {
                flag = false;
                return;
            }
        })
        if(flag)  status.classList.add("status-hiden");
    });
})