// 쿠키에 저장된 여러 값 중 key가 일치하는 value 반환
function getCookie(key) {


  const cookies = document.cookie; // "K=V;K=V;..."

  const arr = cookies.split(";");

  const cookieObj = {};

  for (let entry of arr) {
    // entry == "K=V"
    // -> "=" 기준으로 쪼개기

    const temp = entry.split("=");

    cookieObj[temp[0]] = temp[1];
  }


  // 매개변수로 전달받은 key와 일치하는 value 전달
  return cookieObj[key];
}

// HTML 로딩(렌더링)이 끝난 후 수행
document.addEventListener("DOMContentLoaded", () => {

  const saveId = getCookie("saveId"); // 쿠키에 저장된 Id 얻어오기


  // 저장된 아이디가 없을경우
  if (saveId == undefined) return;

  const memberId
    = document.querySelector("#loginForm input[name=memberId]");

  const checkbox
    = document.querySelector("#loginForm input[name=saveId]");

  // 로그인 상태인 경우 함수 종료
  if (memberId == null) return;

  // 아이디 입력란에 저장된 아이디 출력
  memberId.value = saveId;

  // 아이디 저장 체크박스를 체크 상태로 바꾸기
  checkbox.checked = true;


})


const checkObj = {
  "memberEmail": false,
  "memberPw": false,
  "memberPwConfirm": false,
  "memberId": false,
  "memberTel": false,
  "authKey": false
};

/* ---------- 이메일 유효성 검사 --------- */
const memberEmail = document.querySelector('#memberEmail');
const emailMessage = document.querySelector('#emailMessage');

// 이메일 메시지 미리 작성
const emailMessageObj = {}; // 빈 객체

emailMessageObj.normal = "메일을 받을 수 있는 이메일을 입력해주세요.";
emailMessageObj.invaild = "알맞은 이메일 형식으로 작성해주세요";
emailMessageObj.duplication = "나머지를 순서대로 작성해주세요.";

// 이메일이 입력될 때마다 유효성 검사 수행
memberEmail?.addEventListener('input', e => {

  // 입력된 값 얻어오기
  const inputEmail = memberEmail.value.trim();

  // 입력된 이메일이 없을 경우
  if (inputEmail.length === 0) {

    // 이메일 메시지를 normal 상태 메시지로 변경
    emailMessage.innerText = emailMessageObj.normal;

    // #emailMessage에 색상 관련 클래스를 모두 제거
    emailMessage.classList.remove("confirm", "error");

    // checkObj에서 memberEmail를 false로 변경
    checkObj.memberEmail = false;

    memberEmail.value = ""; // 잘못 입력된 값(띄어쓰기)제거

    return;
  }

  // 이메일 형식 검사(정규 표현식을 이용한 검사)

  const regEx = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // 입력 값이 이메일 형식이 아닌경우
  if (regEx.test(inputEmail) === false) {
    emailMessage.innerText = emailMessageObj.invaild; // 유효 X 메시지
    emailMessage.classList.add("error"); // 빨간 글씨 추가
    emailMessage.classList.remove("confirm"); // 청록 글씨 제거
    checkObj.memberEmail = false;
    return;
  }

  // 이메일 중복 검사(AJAX)
  fetch("/signUp/emailCheck?email=" + inputEmail)
    .then(response => {
      if (response.ok) { // HTTP 응답 상태코드 200번(응답 성공)
        return response.text(); // 응답 결과를 text로 파싱
      }

      throw new Error("이메일 중복검사 에러");
    })
    .then(count => { // 중복인 경우
      // 매개변수 count : 첫 번째 then에서 return된 값이 저장된 변수

      if (count == 1) { // 중복인경우
        emailMessage.innerText = emailMessageObj.duplication; // 중복메시지
        emailMessage.classList.add("confirm");
        emailMessage.classList.remove("error");
        checkObj.memberEmail = false;
        return;
      }

      // 중복이 아닌경우
      emailMessage.classList.add("confirm");
      emailMessage.classList.remove("error");
      checkObj.memberEmail = true; // 유효한 이메일임을 기록

    })
    .catch(err => console.error(err));



})




/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  이름 입력  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
const memberName = document.querySelector(".memberName");
const nameMessage = document.querySelector(".nameMessage");

const nameMessageObj = {};
nameMessageObj.normal = "전화번호를 입력해주세요.(- 제외)";
nameMessageObj.invaild = "이름형식이 유효하지 않습니다.";
nameMessageObj.check = "유효한 이름입니다.";

memberName?.addEventListener("input", () => {

  const inputName = memberName.value.trim();

  if (inputName === 0) {
    nameMessage.innerText = "한글로 입력해주세요."
    nameMessage.classList.remove("confirm", "error");
    checkObj.memberName = false;
    memberName.value = "";
    return;
  }

  const regEx = /^[가-힣]+$/;

  if (regEx.test(inputName) === false) {
    nameMessage.innerText = nameMessageObj.invaild;
    nameMessage.classList.remove("confirm");
    nameMessage.classList.add("error");
    checkObj.memberTel = false;
    return;
  }

  nameMessage.innerText = nameMessageObj.check;
  nameMessage.classList.remove("error");
  nameMessage.classList.add("confirm");
  checkObj.memberTel = true;

})

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 아이디 비번찾기 팝업레이어
const idPwFind = document.querySelector(".idPwFind");
const popup = document.querySelector(".popup")
const xBtn = document.querySelector(".xBtn");
const tabButtons = document.querySelectorAll(".tab-button");
const tabContents = document.querySelectorAll(".tab-content");

idPwFind.addEventListener("click", () => {
  popup.style.display = "block";

});

xBtn.addEventListener("click", () => {
  popup.style.display = "none";

})


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

// 클릭 시 빈칸

// 모든 입력 필드 선택
const inputFields = document.querySelectorAll('.idFind');

inputFields.forEach(input => {
  input.addEventListener('focus', function () {
    // 포커스 시 기존 placeholder 저장하고 제거
    this.setAttribute('data-placeholder', this.placeholder);
    this.placeholder = '';
  });

  input.addEventListener('blur', function () {
    // 포커스 해제 시 placeholder 복원
    this.placeholder = this.getAttribute('data-placeholder');
  });
});



// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 아이디확인 팝업레이어 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
const idConfirm = document.querySelector(".idConfirm");
const idConfirmBtn = document.querySelector(".idConfirmBtn");
const findId = document.querySelector(".findId");
const getNumber = document.querySelector(".getNumber");



getNumber?.addEventListener("click", () => {

  fetch("/myPage/emailName?memberEmail="
        +document.querySelector("#memberEmail").value
        +"&memberName="+document.querySelector("#memberName").value)
.then(response => {
  if(response.ok){
  return response.text();
  }
  throw new Error("에러");
  })
  .then(result => {
  if(result < 1){
  return;
  }
})


})



findId?.addEventListener("click", () => {

  idConfirm.style.display = "block";

  /* 비동기로 값을 찾아올거임 */



  fetch("/myPage/findId", {
    method:"POST",
    headers:{"Content-Type" : "application/json"},
    body: document.querySelector("#memberEmail").value
    })
    .then(response => {
      if(response.ok){
        return response.text();
      }
      throw new Error("아이디 확인 실패");
    })
    .then(findId => {
      idResult.innerText = findId
    })
    .catch(err=>console.error(err));
})

//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 아이디 불러오기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
const idResult = document.querySelector(".idResult");


//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 이메일 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ//

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

/* 이메일 인증 */

// [1] 인증번호를 작성된 이메일로 발송하기

// 인증번호 받기버튼
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");

// 인증관련 메시지 출력 span
const authKeyMessage = document.querySelector("#authKeyMessage");

const initTime = "05:00"; // 인증 초기시간 지정
const initMin = 4; // 초기값 5분에 1초 감소된 후 분
const initSec = 59; // 초기값 5분에 1초 감소된 후 초

// 실제 줄어든 시간(분/초)를 저장할 변수
let min = initMin;
let sec = initSec;

let authTimer; // 타이머 역할의 setInterval을 저장할 변수
        // -> 타이머를 멈추는 clearInterval 수행을 위해 필요


// 인증번호 받기버튼 클릭 시
sendAuthKeyBtn?.addEventListener("click", () => {

  checkObj.authKey = false; // 인증안된 상태로 기록
  authKeyMessage.innerText = ""; // 인증관련 메시지 삭제
  if(authTimer != undefined){
  clearInterval(authTimer);// 이전 인증타이머 없애기
  } 

  // 1) 작성된 이메일이 유효하지 않은 경우
  if(checkObj.memberEmail === false){
    alert("유효한 이메일 작성 후 클릭하세요.");
    return;
  }

  
  const obj2 = {
    "email" : memberEmail.value, // 입력한 이메일
    "name" : memberName.value    // 입력한 인증번호
  };
  
  
  // 2) 비동기로 서버에서 작성된 이메일로 인증코드 발송(AJAX)
  fetch("/email/emailName", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : (obj2)

    // POST 방식으로 /email/sendAuthKey 요청을 처리하는 컨트롤러에
    // 입력된 이메일을 body에 담아서 제출
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("이메일 발송 실패");
    
  })
  .then(result => {
    console.log(result);
  })
  .catch(err => console.error(err));

  // 3) 이메일 발송 메시지 출력 + 5분타이머 출력
  alert("인증번호가 발송되었습니다!")

  authKeyMessage.innerText = initTime; // 05:00 문자열 출력
  authKeyMessage.classList.remove("confirm", "error"); // 검정글씨

  // 1초가 지날 때 마다 함수 내부 내용이 실행되는 setInterval 작성
  authTimer = setInterval(()=>{
    authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;

    // 0분 0초인 경우
    if(min === 0 & sec === 0){
      checkObj.authKey = false; // 인증 못했다고 기록
      clearInterval(authTimer); // 1초마다 동작하는 setInterval 멈춤
      authKeyMessage.classList.add("error");
      authKeyMessage.classList.remove("confirm");
      return
    }

    if(sec === 0){ // 출력된 초가 0인 경우(1분 지남)
      sec = 60;
      min--; // 분 감소
    }

    sec--; // 1초가 지날 때 마다 sec 값 1씩 감소


  }, 1000);

  /* 전달 받은 숫자가 10미만(한 자리 수) 인 경우 
  앞에 0을 붙여서 반환하는 함수*/

  function addZero(num){
    if(num < 10) return "0" + num;
    else         return num;
  }

});

// -------------------------------------------
/* 인증번호를 입력하고 인증하기 버튼을 클릭한 경우 */
const authKey = document.querySelector(".AuthKey");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

checkAuthKeyBtn?.addEventListener("click", () => {

  // + (추가조건) 타이머 00:00인 경우 버튼클릭 막기
  if(min === 0 && sec === 0){
    alert("인증번호 입력 제한시간을 초과하였습니다!");
    return;
  }

  // 1) 인증번호 6자리가 입력되었는지 확인
  if(authKey.value.trim().length < 6){
    alert("인증번호가 잘못입력되었습니다!!!!");
    return;
  }

  // 2) 입력된 이메일과 인증번호를 비동기로 서버에 전달하여
  // Redis에 저장된 이메일, 인증번호와 일치하는지 확인

  /* AJAX로 여러 데이터를 서버로 전달하고 싶을 땐
    JSON 형태로 값을 전달해야한다! */

  // 서버로 제출할 데이터를 저장한 객체생성
  const obj = {
    "email" : memberEmail.value, // 입력한 이메일
    "authKey" : authKey.value    // 입력한 인증번호
  };

  // JSON.stringify(객체) : 객체 -> JSON 변환 (문자열화)

  fetch("/email/checkAuthKey", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj)
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("인증 에러");
  })
  .then(result => {
    console.log("인증결과: ", result);

   if(result == 'false'){ // 인증 실패
    alert("인증번호가 일치하지 않습니다");
    checkObj.authKey = false;
    return;
   }else{ // 인증 성공
    clearInterval(authTimer);
   }

   // 4) 일치하는 경우
   // - 타이머 멈춤
   clearInterval(authTimer);

   // + "인증되었습니다" 화면에 초록색으로 출력
   authKeyMessage.innerText = "인증 되었습니다";
   authKeyMessage.classList.add("confirm");
   authKeyMessage.classList.remove("error");

   checkObj.authKey = true; // 인증 완료표시
  })
  .catch(err => console.error(err));
  
});