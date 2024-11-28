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



const checkObj = {
  "memberEmail": false,
  "memberPw": false,
  "memberPwConfirm": false,
  "memberId": false,
  "memberTel": false,
  "authKey": false,
  "memberName" : false,
  "authKey3" : false
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

      if (count == 1) { // 중복인경우 == DB 에 있는경우
        emailMessage.innerText = emailMessageObj.duplication; // 중복메시지
        emailMessage.classList.add("confirm");
        emailMessage.classList.remove("error");
        checkObj.memberEmail = true;
        return;
      }

      // 중복이 아닌경우
      emailMessage.classList.add("confirm");
      emailMessage.classList.remove("error");
      checkObj.memberEmail = false; // 유효한 이메일임을 기록

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

idPwFind?.addEventListener("click", () => {
  popup.style.display = "block";

});

xBtn?.addEventListener("click", () => {
  popup.style.display = "none";

})


tabButtons[0]?.addEventListener("click", () => {

  tabButtons[0].classList.add("active");
  tabContents[0].classList.add("active");
  tabButtons[1].classList.remove("active");
  tabContents[1].classList.remove("active");

})
tabButtons[1]?.addEventListener("click", () => {

  tabButtons[1].classList.add("active");
  tabContents[1].classList.add("active");
  tabButtons[0].classList.remove("active");
  tabContents[0].classList.remove("active");

})

// 클릭 시 빈칸

// 모든 입력 필드 선택
const inputFields = document.querySelectorAll('.idFind');

inputFields.forEach(input => {
  input?.addEventListener('focus', function () {
    // 포커스 시 기존 placeholder 저장하고 제거
    this.setAttribute('data-placeholder', this.placeholder);
    this.placeholder = '';
  });

  input?.addEventListener('blur', function () {
    // 포커스 해제 시 placeholder 복원
    this.placeholder = this.getAttribute('data-placeholder');
  });
});



// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 아이디확인 팝업레이어 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
const idConfirm = document.querySelector(".idConfirm");
const idConfirmBtn = document.querySelector(".idConfirmBtn");
const findId = document.querySelector(".findId");
const getNumber = document.querySelector(".getNumber");
const authKey = document.querySelector(".AuthKey");
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");


findId?.addEventListener("click", () => {

  
  

  // + (추가조건) 타이머 00:00인 경우 버튼클릭 막기
  if(min === 0 && sec === 0){
    alert("인증번호 입력 제한시간을 초과하였습니다!");
    return;
  }

  // 1) 인증번호 6자리가 입력되었는지 확인
  if(authKey.value.trim().length < 6){
    alert("인증번호가 잘못입력되었습니다!");
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
    idConfirm.style.display = "block";
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
    body : JSON.stringify(obj2)

    // POST 방식으로 /email/sendAuthKey 요청을 처리하는 컨트롤러에
    // 입력된 이메일을 body에 담아서 제출
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("이메일 발송 실패");
    
  })
  .then(result => {
    console.log(result);
    if(result == 0){
      alert("이메일과 이름이 일치하지 않습니다!")
    }else{
      
    }

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


/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 비밀번호 찾기 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */


/*  유효성 검사 */
// 1) 아이디 유효성 검사에 따라 아이디 메시지를 변경

const memberId = document.querySelector("#memberId");
const idMessage = document.querySelector("#idMessage");

// 2) 아이디 관련 메시지 작성
const idMessageObj = {};
idMessageObj.normal = "영어, 숫자포함 6~14글자";
idMessageObj.invaild = "6~14글자로 입력해주세요.";
idMessageObj.duplication = "아이디 확인이 되었습니다.";
idMessageObj.check = "없는 아이디입니다.";

// 3) 아이디을 입력할 때마다 유효성 검사
memberId?.addEventListener('input', e => {
  
  // 입력 받은 아이디
  const inputId = memberId.value.trim();

  // 4) 입력된 아이디이 없을경우
  if(inputId.length === 0){
    
    // 아이디 메시지를 normal 상태 메시지로 변경
    idMessage.innerText = idMessageObj.normal;
    
    // #idMessage에 색상관련 클래스를 모두 제거
    idMessage.classList.remove("confirm", "error");
    
    // checkObj에서 memberId를 false로 변경
    checkObj.memberId = false;
    
    memberId.value = ""; // 잘못 입력된 값(띄어쓰기)제거
    
    return;
  }

  // 5) 아이디 유효성검사(정규 표현식)

  const regEx = /^[a-zA-Z0-9]{6,14}$/; // 영어, 숫자로만 6~14글자

  // 입력 값이 아이디 형식이 아닌경우
  if( regEx.test(inputId) === false ){
    idMessage.innerText = idMessageObj.invaild; // 유효 X 메시지
    idMessage.classList.add("error"); // 빨간 글씨 추가
    idMessage.classList.remove("confirm"); // 청록 글씨 제거
    checkObj.memberId = false;
    return;
  }

  // 아이디 중복 검사(AJAX)
  fetch("/signUp/idCheck?id=" + inputId)
  .then(response => {
      if(response.ok) {
        return response.text();
    }
    throw new Error("아이디 중복검사 실패");
  })
  .then(count => {
    if(count == 1){ // 중복인경우
      idMessage.innerText = idMessageObj.duplication; // 중복메시지
      idMessage.classList.remove("error");
      idMessage.classList.add("confirm");
      checkObj.memberId = true;
      return;
    }
  
    // 중복이 아닌경우
    idMessage.innerText = idMessageObj.check; // 중복x 메시지
    idMessage.classList.remove("confirm");
    idMessage.classList.add("error");
    checkObj.memberId = false; // 유요한 아이디임을 기록
   })
  .catch(err => console.error(err));

})


/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ비밀번호 찾기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

const getNumberBtn = document.querySelector("#getNumberPw");
const findPwBtn    = document.querySelector(".findPw");
const authKeyMessage2 = document.querySelector("#authKeyMessage2");
const getMemberId = document.querySelector("#memberId");
const memberNamePw = document.querySelector("#memberName");
const memberName2 = document.querySelector(".memberName2")

getNumberBtn?.addEventListener("click", ()=>{

  checkObj.authKey = false; // 인증안된 상태로 기록
  authKeyMessage2.innerText = ""; // 인증관련 메시지 삭제
  if(authTimer != undefined){
  clearInterval(authTimer);// 이전 인증타이머 없애기
  } 

  // 1) 작성된 이메일이 유효하지 않은 경우
  if(checkObj.memberId === false){
    alert("유효한 아이디 작성 후 클릭하세요.");
    return;
  }


  const obj3 = {
    "id" : memberId.value, // 입력한 아이디
    "name" : memberName2.value    // 입력한 인증번호
  };
  // 2) 비동기로 서버에서 작성된 이메일로 인증코드 발송(AJAX)
  fetch("/email/emailPw", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj3)

    // POST 방식으로 /email/emailPw 요청을 처리하는 컨트롤러에
    // 입력된 이메일, 이름을 body에 담아서 제출
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("인증번호 발송 실패");
    
  })
  .then(result => {

    console.log(result);

    if(result == 0){
      alert("아이디와 이름이 일치하지 않습니다!");
      return;
    } else {
    // 3) 이메일 발송 메시지 출력 + 5분타이머 출력
    alert("인증번호가 발송되었습니다!")

    authKeyMessage2.innerText = initTime; // 05:00 문자열 출력
    authKeyMessage2.classList.remove("confirm", "error"); // 검정글씨

    // 1초가 지날 때 마다 함수 내부 내용이 실행되는 setInterval 작성
    authTimer = setInterval(()=>{
      authKeyMessage2.innerText = `${addZero(min)}:${addZero(sec)}`;

      // 0분 0초인 경우
      if(min === 0 & sec === 0){
        checkObj.authKey = false; // 인증 못했다고 기록
        clearInterval(authTimer); // 1초마다 동작하는 setInterval 멈춤
        authKeyMessage2.classList.add("error");
        authKeyMessage2.classList.remove("confirm");
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
  }

 
 
  })


  .catch(err => console.error(err));
  
});

//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ//


// -------------------------------------------
/* 인증번호를 입력하고 인증하기 버튼을 클릭한 경우 */
// const authKey3 = document.querySelector("#authKey");
const checkAuthKeyBtn3 = document.querySelector("#checkAuthKeyBtn3");

checkAuthKeyBtn3?.addEventListener("click", () => {

  // + (추가조건) 타이머 00:00인 경우 버튼클릭 막기
  if(min === 0 && sec === 0){
    alert("인증번호 입력 제한시간을 초과하였습니다!");
    return;
  }

  
  const authKey3 = document.querySelector("#authKey1");
  // 1) 인증번호 6자리가 입력되었는지 확인
  // if(authKey.value.trim().length < 6){
  //   alert("인증번호가 잘못입력되었습니다!!!!");
  //   return;
  // }

  // 2) 입력된 이메일과 인증번호를 비동기로 서버에 전달하여
  // Redis에 저장된 이메일, 인증번호와 일치하는지 확인

  /* AJAX로 여러 데이터를 서버로 전달하고 싶을 땐
    JSON 형태로 값을 전달해야한다! */

  // 서버로 제출할 데이터를 저장한 객체생성
  const obj = {
    "id" : memberId.value, // 입력한 아이디
    "authKey" : authKey3.value    // 입력한 인증번호
  };

  // JSON.stringify(객체) : 객체 -> JSON 변환 (문자열화)

  fetch("/email/checkAuthKey2", {
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
    console.log(result);
    console.log(result);
    console.log(result);
    alert("인증번호가 일치하지 않습니다");
    checkObj1.authKey3 = false;
    return;
   }else{ // 인증 성공
    clearInterval(authTimer);
    checkObj1.authKey3 = true;
   }

   // 4) 일치하는 경우
   // - 타이머 멈춤
   clearInterval(authTimer);

   // + "인증되었습니다" 화면에 초록색으로 출력
   authKeyMessage2.innerText = "인증 되었습니다";
   authKeyMessage2.classList.add("confirm");
   authKeyMessage2.classList.remove("error");

   checkObj.authKey = true; // 인증 완료표시
  })
  .catch(err => console.error(err));
  
});


const authKey3 = document.querySelector("#authKey1");


// ** 이메일 임시비밀번호 발급 &*&*////////////

// 임시 비밀번호 발송

const sendEmail = document.querySelector("#sendEmail"); // 이메일 발송 버튼

const checkObj1 = {
  "authKey3": false
};


sendEmail?.addEventListener("click", () => { // 이메일 발송 버튼 클릭 시



  if(memberId.value.trim().length == 0){
    alert("아이디를 입력해주세요")
    return
  }
  


  if(checkObj.memberId && checkObj1.authKey3 == false){
    alert("아이디 또는 인증번호를 확인해주세요")
    console.log(checkObj.memberId);
    console.log(checkObj1.authKey3);
    console.log(checkObj.authKey);
    e.preventDefault();
    return;
  } else {
    alert("인증된 이메일로 임시비밀번호를 발송드렸습니다")
  } 

  // 서버에서 작성된 이메일로 인증 코드 발송 (Ajax)
  fetch("/email/sendAuthKey3", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : memberId.value
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("이메일 발송 실패")
  })
  .then(result => {
    console.log(result);
    if(result === 1){
    }else{
    }
  })
  .catch(err => console.error(err));

  
  location.href = "/myPage/myPageLogin";




});



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

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 회원탈퇴, 비밀번호 수정 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

const signupDeleteBtn = document.querySelectorAll(".signup-button-delete");

for(let i=0; i < signupDeleteBtn.length; i++){
signupDeleteBtn[i]?.addEventListener("click", () => {

  location.href = "myPageMain";

})
}

/* 비밀번호 변경 */

// 비밀번호 변경 form 태그
const changePw = document.querySelector(".pwChangeForm");

changePw?.addEventListener("submit", e => {

  // 입력 요소 모두 얻어오기
  const currentPw = document.querySelector("#currentPw");
  const newPw = document.querySelector("#newPw");
  const newPwConfirm = document.querySelector("#newPwConfirm");

  // 현재 비밀번호, 새 비밀번호, 새 비밀번호 확인 입력 여부 체크

  let str;

  if(newPwConfirm.value.trim().length == 0)
    str = "새 비밀번호를 확인을 입력해 주세요"; 

  if(newPw.value.trim().length == 0)
    str = "새 비밀번호를 입력해 주세요"; 

  if(currentPw.value.trim().length == 0)
    str = "현재 비밀번호를 입력해 주세요"; 
  
  if(str !== undefined){ // 입력되지 않은 값이 존재
    alert(str);
    e.preventDefault(); // form 제출 막기
    return; // submit 이벤트 핸들러 종료
  }

  // 2. 새 비밀번호가 알맞은 형태로 작성 되었는가 확인
  const lengthCheck = newPw.value.length >= 6 && newPw.value.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(newPw.value); // 영어 알파벳 포함
  const numberCheck = /\d/.test(newPw.value); // 숫자 포함
  const specialCharCheck = /[!@#_-]/.test(newPw.value); // 특수문자 포함

  // 조건이 하나라도 만족하지 못하면
  if ( !(lengthCheck && letterCheck && numberCheck && specialCharCheck) ) {
      alert("영어, 숫자, 특수문자 1글자 이상, 6~20자 사이로 입력해주세요")
      e.preventDefault();
      return;
  }

  // 3. 새 비밀번호, 새 비밀번호 확인이 같은지 체크
  if(newPw.value !== newPwConfirm.value){
    alert("새 비밀번호가 일치하지 않습니다.");
    e.preventDefault();
    return;
  }
});

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* 회원 탈퇴 유효성 검사 */
const withdrawal = document.querySelector("#withdrawal");
withdrawal?.addEventListener("submit", e => {
  if(confirm("정말 탈퇴하시겠습니까?") == false) { // 취소 클릭 시
    alert("탈퇴가 취소되었습니다");
    e.preventDefault();
    return;
  }
})

/*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 1대1문의로 넘어가기 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
const chat1vs1 = document.querySelector(".chat1vs1");
chat1vs1?.addEventListener("click", () => {
  document.querySelector(".sidebar-button").click();
  document.querySelector(".sidebar-messageBtn").click();
})

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* 다음 주소 API */
function findAddress() {
  new daum.Postcode({
      oncomplete: function(data) {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var addr = ''; // 주소 변수
    

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
              addr = data.roadAddress;
          } else { // 사용자가 지번 주소를 선택했을 경우(J)
              addr = data.jibunAddress;
          }

          
          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById("postcode").value = data.zonecode;
          document.getElementById("address").value = addr;
          // 커서를 상세주소 필드로 이동한다.
          document.getElementById("detailAddress").focus();
      }
  }).open();
}

/* 주소 검색버튼 클릭 시 */
document.querySelector("#findAddressBtn")
  ?.addEventListener("click", findAddress);

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  폰유효성?  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

const phoneInput = document.getElementById('number');
const errorMessage = document.getElementById('error-message');

// 입력 길이 제한
phoneInput?.addEventListener('input', function () {
    const value = phoneInput.value;

    // 11자를 초과할 경우 잘라내기
    if (value.length > 11) {
        phoneInput.value = value.slice(0, 11);
    }
});




// 전화번호 유효성 검사 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ //
const telChangeForm = document.querySelector("[name=telChangeForm]");

const memberTel = document.querySelector("[name=phoneNumber]");
const telMessage = document.querySelector("#telMessage");

const telMessageObj ={};
telMessageObj.normal = "전화번호를 입력해주세요.(- 제외)";
telMessageObj.invaild = "유효하지 않은 전화번호 형식입니다.";
telMessageObj.check = "유효한 전화번호 형식입니다.";

memberTel?.addEventListener("input", () => {

  const inputTel = memberTel.value.trim();

  if(inputTel.length === 0){
    telMessage.innerText = telMessageObj.normal;
    telMessage.classList.remove("confirm", "error");
    checkObj.memberTel = false;
    memberTel.value = "";
    return;
  }

  const regEx = /^010[0-9]{8}$/; // 090으로 시작, 이후 숫자 8개 (총 11자)

  if(regEx.test(inputTel) === false){
    telMessage.innerText = telMessageObj.invaild;
    telMessage.classList.remove("confirm");
    telMessage.classList.add("error");
    checkObj.memberTel = false;
    return;
  }

  telMessage.innerText = telMessageObj.check;
  telMessage.classList.remove("error");
  telMessage.classList.add("confirm");
  checkObj.memberTel = true;

});

telChangeForm.addEventListener("submit", e => {


  if(checkObj.memberTel) {
    e.target.submit();
  }

  else {
    e.preventDefault();
    alert("유효한 전화번호를 입력해주세요");
  }

  return;

})