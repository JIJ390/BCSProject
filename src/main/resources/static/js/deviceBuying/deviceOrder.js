const findAddressBtn = document.querySelector("#findAddressBtn")

/* 다음 주소 API 로 주소 검색하기 */

function findAddress() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수
      // var extraAddr = ''; // 참고항목 변수, 필요 없음

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else { // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('postcode').value = data.zonecode;
      document.getElementById("address").value = addr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById("detailAddress").focus();
    }
  }).open();
}

findAddressBtn.addEventListener("click", findAddress);

// 회원 세션에 저장된 주소지 정보 가져오기
const memberAddressBtn = document.querySelector("#memberAddressBtn");

memberAddressBtn.addEventListener("click", () => {

  if (loginMember.memberAddress === null) {
    alert("등록된 주소가 없습니다");
    return;
  }

  const result = loginMember.memberAddress.split(",");

  if (result[0].trim().length === 0) {
    alert("등록된 주소가 없습니다");
    return;
  }


  document.getElementById('postcode').value = result[0];
  document.getElementById("address").value = result[1];
  document.getElementById("detailAddress").value = result[2];

});












// 유효성 검사
const form = document.querySelector("#orderFrm");

form.addEventListener("submit", e => {

  // 로그인하지 않았을 시 
  if(loginMember === null) {
    e.preventDefault();

    alert("로그인 후 이용해 주세요");
    if (confirm("로그인 페이지로 이동하시겠습니까?")) {
      location.href = "/myPage/myPageLogin";
    }
    return;
  }

  if (orderBuyingDevice.buyingDevicePrice > loginMember.memberPoint) {
    e.preventDefault();

    alert("잔액이 부족합니다");
    if (confirm("포인트 충전 페이지로 이동하시겠습니까?")) {
      location.href = "/point";
    }
    return;
  }

  /* 입력 사항 */
  const orderTel = document.querySelector("[name=orderTel]")
  const orderName = document.querySelector("[name=orderName]")
  const orderComment = document.querySelector("[name=orderComment]")

  console.log(orderTel);

  /* 주소 */
  const postcode = document.querySelector("#postcode");
  const address = document.querySelector("#address");
  const detailAddress = document.querySelector("#detailAddress");


  const regEx =  /^[가-힣]{2,10}$/;  // 한글 이름 유효성 검사
  const inputName = orderName.value.trim()

  if (inputName.length < 2) {
    alert("이름은 두 글자 이상으로 입력해 주세요");
    orderName.focus();
    e.preventDefault();
    return;
  }

  if ((regEx.test(inputName) === false)){
    alert("한글로 된 이름을 입력해 주세요");
    orderName.focus();
    e.preventDefault();
    return;
  }


  const regExTel = /^010[0-9]{8}$/; // 010으로 시작, 이후 숫자 8개 (총 11자)

  // 전화 번호
  if ((regExTel.test(orderTel.value.trim()) === false)) {
    alert("유효한 전화번호를 입력해 주세요");
    orderTel.focus();
    e.preventDefault();
    return;
  }

  // 주소창 둘 중 하나가 비었을 때
  if ((postcode.value.trim().length === 0) || (address.value.trim().length === 0)) {
    alert("주소를 검색해 주세요");
    postcode.focus();
    e.preventDefault();
    return;
  }

  // 상세 주소
  if ((detailAddress.value.trim().length === 0)) {
    alert("상세 주소를 입력해 주세요");
    detailAddress.focus();
    e.preventDefault();
    return;
  }

  if ((orderComment.value.trim().length >= 80)) {
    alert("요청 사항은 80자 이내로 작성해 주세요");
    orderComment.focus();
    e.preventDefault();
    return;
  }

  let deviceNo = 0;
  
  deviceNo = location.href.substr(-1);

  console.log(deviceNo);

  const input = document.createElement("input");
  input.name = "deviceNo";
  input.type = "hidden";
  input.value = deviceNo;

  e.target.append(input);

});