// 쿠키에 저장된 여러 값 중 key가 일치하는 value 반환
function getCookie(key){


  const cookies = document.cookie; // "K=V;K=V;..."
  console.log(cookies);

  const arr = cookies.split(";");

  const cookieObj = {};

  for(let entry of arr){
    // entry == "K=V"
    // -> "=" 기준으로 쪼개기

    const temp = entry.split("=");

    cookieObj[temp[0]] = temp[1];
  }

  console.log(cookieObj);

  // 매개변수로 전달받은 key와 일치하는 value 전달
  return cookieObj[key];
}

// HTML 로딩(렌더링)이 끝난 후 수행
document.addEventListener("DOMContentLoaded", () => {

  const saveId = getCookie("saveId"); // 쿠키에 저장된 Id 얻어오기

  console.log(saveId);

  // 저장된 아이디가 없을경우
  if(saveId == undefined) return;

  const memberId
    = document.querySelector("#loginForm input[name=memberId]");

  const checkbox
    = document.querySelector("#loginForm input[name=saveId]");

    // 로그인 상태인 경우 함수 종료
  if(memberId == null) return;

  // 아이디 입력란에 저장된 아이디 출력
  memberId.value = saveId;

  // 아이디 저장 체크박스를 체크 상태로 바꾸기
  checkbox.checked = true;


})


// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 아이디 비번찾기 팝업레이어
const idPwFind = document.querySelector(".idPwFind");
const popup = document.querySelector(".popup")
const xBtn = document.querySelector(".xBtn");
  const tabButtons = document.querySelectorAll(".tab-button");
  const tabContents = document.querySelectorAll(".tab-content");

idPwFind.addEventListener("click", ()=>{
  popup.style.display = "block";

});

xBtn.addEventListener("click", ()=>{
  popup.style.display = "none";

})

 
    // tabButtons.addEventListener("click", () => {
    //   // Remove active class from all buttons and contents
    //   tabButtons.classList.remove("active");
    //   tabContents.classList.remove("active");

    //   // Add active class to the clicked button and corresponding content
    //   button.classList.add("active");
    //   const tabId = button.getAttribute("data-tab");
    //   document.getElementById(tabId).classList.add("active");
    // });



tabButtons[0].addEventListener("click", () => {

  tabButtons[0].classList.add("active");
  tabContents[0].classList.add("active");
  tabButtons[1].classList.remove("active");
  tabContents[1].classList.remove("active");

})
tabButtons[1].addEventListener("click", () => {

  tabButtons[1].classList.add("active");
  tabContents[1].classList.add("active");
  tabButtons[0].classList.remove("active");
  tabContents[0].classList.remove("active");

})


