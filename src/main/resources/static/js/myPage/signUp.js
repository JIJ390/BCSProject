/* 회원가입 페이지에서 계정 만들기로 이동하는 버튼 */
const createBtn = document.querySelector('.signup-button');

createBtn?.addEventListener('click', () => {
  window.location.href = '/signUp/signUpCreate';
});


/* 회원가입 완료하는 버튼 */
const signUpBtn = document.querySelector('.signup-button-create');

signUpBtn.addEventListener('click', () => {

  console.log(signUpBtn);

  window.location.href = '/myPage/myPageLogin';
});

/***** 회원 가입 유효성 검사 *****/

/* 필수 입력 항목의 유효성 검사여부를 체크하기 위한 객체(체크리스트)*/
const checkObj = {
  "memberEmail"     : false,
  "memberPw"        : false,
  "memberPwConfirm" : false,
  "memberId"  : false,
  "memberTel"       : false,
  "authKey"         : false
};


/* ---------- 이메일 유효성 검사 --------- */
const memberEmail = document.querySelector('#memberEmail');
const emailMessage = document.querySelector('#emailMessage');

// 이메일 메시지 미리 작성
const emailMessageObj = {}; // 빈 객체

emailMessageObj.normal = "메일을 받을 수 있는 이메일을 입력해주세요.";
emailMessageObj.invaild = "알맞은 이메일 형식으로 작성해주세요";
emailMessageObj.duplication = "이미 사용중인 이메일입니다";
emailMessageObj.check = "이메일 중복 확인 완료";

// 이메일이 입력될 때마다 유효성 검사 수행
memberEmail.addEventListener('input', e => {

  // 입력된 값 얻어오기
  const inputEmail = memberEmail.value.trim();

  // 입력된 이메일이 없을 경우
  if(inputEmail.length === 0){

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
  if( regEx.test(inputEmail) === false ){
    emailMessage.innerText = emailMessageObj.invaild; // 유효 X 메시지
    emailMessage.classList.add("error"); // 빨간 글씨 추가
    emailMessage.classList.remove("confirm"); // 청록 글씨 제거
    checkObj.memberEmail = false;
    return;
  }

  // 이메일 중복 검사(AJAX)
  fetch("/signUp/emailCheck?email=" + inputEmail)
  .then(response => {
    if(response.ok){ // HTTP 응답 상태코드 200번(응답 성공)
      return response.text(); // 응답 결과를 text로 파싱
    }

    throw new Error("이메일 중복검사 에러");
  })
  .then(count => { // 중복인 경우
    // 매개변수 count : 첫 번째 then에서 return된 값이 저장된 변수

    if(count == 1){ // 중복인경우
      emailMessage.innerText = emailMessageObj.duplication; // 중복메시지
      emailMessage.classList.add("error");
      emailMessage.classList.remove("confirm");
      checkObj.memberEmail = false;
      return;
    }

    // 중복이 아닌경우
    emailMessage.innerText = emailMessageObj.check; // 중복X 메시지
    emailMessage.classList.add("confirm");
    emailMessage.classList.remove("error");
    checkObj.memberEmail = true; // 유효한 이메일임을 기록

  })
  .catch( err => console.error(err));

})

/*  유효성 검사 */
// 1) 아이디 유효성 검사에 따라 아이디 메시지를 변경

const memberId = document.querySelector("#memberId");
const idMessage = document.querySelector("#idMessage");

// 2) 아이디 관련 메시지 작성
const idMessageObj = {};
idMessageObj.normal = "영어, 숫자포함 6~14글자";
idMessageObj.invaild = "6~14글자로 입력해주세요.";
idMessageObj.duplication = "이미 사용중인 아이디입니다";
idMessageObj.check = "사용가능한 아이디입니다.";

// 3) 아이디을 입력할 때마다 유효성 검사
memberId.addEventListener('input', e => {
  
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
      idMessage.classList.remove("confirm");
      idMessage.classList.add("error");
      checkObj.memberId = false;
      return;
    }
  
    // 중복이 아닌경우
    idMessage.innerText = idMessageObj.check; // 중복x 메시지
    idMessage.classList.remove("error");
    idMessage.classList.add("confirm");
    checkObj.memberId = true; // 유요한 아이디임을 기록
   })
  .catch(err => console.error(err));

})

/* 전화번호 유효성 검사 */

const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

const telMessageObj ={};
telMessageObj.normal = "전화번호를 입력해주세요.(- 제외)";
telMessageObj.invaild = "유효하지 않은 전화번호 형식입니다.";
telMessageObj.check = "유효한 전화번호 형식입니다.";

memberTel.addEventListener("input", () => {

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

// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 비밀번호 유효성 검사  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

const pwMessageObj ={};
pwMessageObj.normal = "영어, 숫자, 특수문자 1글자 이상 6~20글자 사이로 입력해주세요.";
pwMessageObj.invaild = "유효하지 않은 비밀번호 형식입니다.";
pwMessageObj.vaild = "유효한 비밀번호 형식입니다.";
pwMessageObj.error = "비밀번호가 일치하지 않습니다";
pwMessageObj.check = "비밀번호가 일치합니다.";

memberPw.addEventListener("input", () => {
  
  const inputPw = memberPw.value.trim();

  if(inputPw.length === 0){ // 비밀번호 미입력
    pwMessage.innerText = pwMessageObj.normal;
    pwMessage.classList.remove("confirm", "error");
    checkObj.memberPw = false;
    memberPw.value = "";
    return;
  }

  // 비밀번호 정규표현식 검사
  const lengthCheck = inputPw.length >= 6 && inputPw.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(inputPw); // 영어 알파벳 포함
  const numberCheck = /\d/.test(inputPw); // 숫자 포함
  const specialCharCheck = /[\!\@\#\_\-]/.test(inputPw); // 특수문자 포함

  // 조건이 하나라도 만족하지 못하면
  if ( !(lengthCheck && letterCheck && numberCheck && specialCharCheck) ) {
    pwMessage.innerText = pwMessageObj.invaild;
    pwMessage.classList.remove("confirm");
    pwMessage.classList.add("error");
    checkObj.memberPw = false;
    return;
  }

  pwMessage.innerText = pwMessageObj.vaild;
  pwMessage.classList.remove("error");
  pwMessage.classList.add("confirm");
  checkObj.memberPw = true;

  // 비밀번호 확인이 작성된 상태에서
  // 비밀번호가 입력된 경우
  if(memberPwConfirm.value.trim().length > 0){
    checkPw(); // 같은지 비교하는 함수 호출
  }

});

/* ----- 비밀번호, 비밀번호 확인 같은지 검사하는 함수 ----- */
function checkPw(){

  // 같은 경우
  if(memberPw.value === memberPwConfirm.value){
    pwMessage.innerText = pwMessageObj.check;
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPwConfirm = true;
    return;
  }

  // 다른 경우
    pwMessage.innerText = pwMessageObj.error;
    pwMessage.classList.add("error");
    pwMessage.classList.remove("confirm");
    checkObj.memberPwConfirm = false;
}

/* ----- 비밀번호 확인이 입력 되었을 때 ----- */
memberPwConfirm.addEventListener("input", () => {

    // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
    if(checkObj.memberPw === true){
      checkPw();
      return;
    }
  
    // 비밀번호 input에 작성된 값이 유효하지 않은 경우
    checkObj.memberPwConfirm = false;

});

/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ  이름 입력  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
const memberName = document.querySelector("#memberName");
const nameMessage = document.querySelector("#nameMessage");

const nameMessageObj = {};
nameMessageObj.normal = "전화번호를 입력해주세요.(- 제외)";
nameMessageObj.invaild = "이름형식이 유효하지 않습니다.";
nameMessageObj.check = "유효한 이름입니다.";

memberName.addEventListener("input", () => {

  const inputName = memberName.value.trim();

  if(inputName === 0 ){
    nameMessage.innerText = "한글로 입력해주세요."
    nameMessage.classList.remove("confirm", "error");
    checkObj.memberName = false;
    memberName.value = "";
    return;
  }

  const regEx =  /^[가-힣]+$/;

  if(regEx.test(inputName) === false){
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