/* 준비중 버튼 눌르면 뷰 열리는 버튼 */

const listBtn = document.querySelectorAll(".list-btn");
const statusHiden = document.querySelectorAll(".status-hiden");

for (let i = 0; i < listBtn.length; i++) {
    listBtn[i]?.addEventListener("click", () => {
        if (statusHiden[i].style.display === "none") {
            statusHiden[i].style.display = "block";

            listBtn[i].innerText = "준비중▼";
        } else {
            statusHiden[i].style.display = "none";
            listBtn[i].innerText = "준비중△";
        }
    });
}