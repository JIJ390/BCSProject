/* 팝업레이어창 */

document.getElementById('open-popup').addEventListener('click', function() {
  document.getElementById('popup-layer').classList.remove('hidden');
});

document.getElementById('close-popup').addEventListener('click', function() {
  document.getElementById('popup-layer').classList.add('hidden');
});

/* 취소버튼 클릭 시 admin 페이지로 */
const registrationDelete = document.querySelector(".registrationDelete");

registrationDelete.addEventListener("click", ()=>{

  location.href = "/admin";

})

/* 버튼 */
const buttons = document.querySelectorAll(".buttonChoice");

buttons.forEach((button) => {
    button.addEventListener("click", () => {
        // 모든 버튼에서 active 클래스 제거
        buttons.forEach((btn) => btn.classList.remove("active"));
        // 클릭된 버튼에만 active 클래스 추가
        button.classList.add("active");
    });
});

const buttons1 = document.querySelectorAll(".buttonChoice1");

buttons1.forEach((button1) => {
    button1.addEventListener("click", () => {
        // 모든 버튼에서 active 클래스 제거
        buttons1.forEach((btn1) => btn1.classList.remove("active"));
        // 클릭된 버튼에만 active 클래스 추가
        button1.classList.add("active");
    });
});