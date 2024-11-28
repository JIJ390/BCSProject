const colorCircle = document.querySelectorAll(".color-circle");
const capacityBox = document.querySelectorAll(".capacity-box");
const gradeBox = document.querySelectorAll(".grade-box");

document.addEventListener("DOMContentLoaded", () => {

  // 처음 페이지 로딩 시 
  showSlide(0);
  

  // 색깔 선택
  colorCircle.forEach(circle => {

    circle.addEventListener("click", () => {

      // 선택 시 모든 색깔에서 클래스 제거
      colorCircle.forEach(c => {
        c.classList.remove('selected-color')
      })

      // 선택 클래스 추가
      circle.classList.add('selected-color');

      const colorName = circle.getAttribute("data-value2");
      const index = circle.getAttribute("data-value3");

      showSlide(index);
      // 색상 클릭 시 이동
      document.querySelector(".color-name").innerText = colorName;
    })
  })





  
  // 저장소 용량 선택
  capacityBox.forEach(capacity => {

    capacity.addEventListener("click", () => {

      // 선택 시 모든 용량에서 클래스 제거
      capacityBox.forEach(c => {
        c.classList.remove('selected-capacity')
      })

      // 선택 클래스 추가
      capacity.classList.add('selected-capacity');

      expectedPrice();
    })
  })

  // 등급 선택
  gradeBox.forEach(grade => {

    grade.addEventListener("click", () => {

      // 선택 시 모든 등급에서 클래스 제거
      gradeBox.forEach(g => {
        g.classList.remove('selected-grade')
      })

      // 선택 클래스 추가
      grade.classList.add('selected-grade');

      expectedPrice();
    })
  })

})


const expectedPrice = () => {
  const selectedCapacity = document.querySelector(".selected-capacity");
  const selectedGrade = document.querySelector(".selected-grade");

  // 선택에 따라 가산되는 가격
  let plusPrice = 0;

  // 첫 선택 오류 해결
  if (selectedCapacity !== null) plusPrice += Number(selectedCapacity.children[1].getAttribute("data-value"));
  if (selectedGrade !== null) plusPrice += Number(selectedGrade.children[1].getAttribute("data-value"));

  const map = {
    "plusPrice" : plusPrice,
    "deviceNo"  : device.deviceNo
  }

  fetch("/device/sell/expectedPrice", {
    method : "POST", 
    headers: {"Content-Type": "application/json"}, 
    body : JSON.stringify(map)
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("변경 실패 : " + response.status);
  })
  .then(expectedPrice => {

    const priceSpan = document.querySelector(".selling-span2");

    priceSpan.innerText = expectedPrice.replace(/\B(?=(\d{3})+(?!\d))/g, ',') + ' ₩';
    
  })
  .catch(err => console.error(err));
  
}




const sellingBtn = document.querySelector("#sellingBtn");

// 유효성 검사
sellingBtn.addEventListener("click", e => {

  e.stopPropagation(); // 이벤트 전파 방지 팝업이 열리면서 꺼지는 현상 방지


  // 로그인하지 않았을 시 
  if(loginMember === null) {
  
    alert("로그인 후 이용해 주세요");
    if (confirm("로그인 페이지로 이동하시겠습니까?")) {
      location.href = "/myPage/myPageLogin";
    }
    return;
  }

  const selectedColor = document.querySelector(".selected-color");
  const selectedCapacity = document.querySelector(".selected-capacity");
  const selectedGrade = document.querySelector(".selected-grade");
  
  const colorContent = document.querySelector(".color-content");
  const capacityContent = document.querySelector(".capacity-content");
  const gradeContent = document.querySelector(".grade-content");

  if (selectedColor === null) {
    alert("색상을 선택해 주세요");

    const scrollPosition = colorContent.offsetTop;

    window.scrollTo({
      top : scrollPosition - 150 , 
      behavior : "smooth" 
    });

    return;
  }

  if (selectedCapacity === null) {
    alert("용량을 선택해 주세요");

    const scrollPosition = capacityContent.offsetTop;

    window.scrollTo({
      top : scrollPosition - 150 , 
      behavior : "smooth" 
    });

    return;
  }

  if (selectedGrade === null) {
    alert("등급을 선택해 주세요");

    const scrollPosition = gradeContent.offsetTop;

    window.scrollTo({
      top : scrollPosition - 150 ,
      behavior : "smooth" 
    });

    return;
  }


  popupOpen();
})

const sellingPopUp = document.querySelector(".device-selling-popup");

// 팝업 열기
const popupOpen = () => {
  sellingPopUp.classList.remove("close-popup");
  document.querySelector("#blackDisplay").classList.add("overlay");

  // 팝업 내용 채우기
  const selectedCapacity = document.querySelector(".selected-capacity").children[0].innerText;
  const selectedGrade = document.querySelector(".selected-grade").children[0].innerText;
  const selectedColor = document.querySelector(".color-name").innerText;

  let str = `${selectedColor} > ${selectedCapacity} > ${selectedGrade}`;

  // 기종 선택 정보
  document.querySelector("#deviceStatus").innerText = str;
  // 예상 가격 정보
  document.querySelector("#expectedPrice").innerText
   = document.querySelector(".selling-span2").innerText;


  console.log(str);

}

// 팝업 닫기
const popupClose = () => {
  sellingPopUp.classList.add("close-popup");
  document.querySelector("#blackDisplay").classList.remove("overlay");
}

window.addEventListener("click", e => {

  // 팝업 레이어 가 닫혀있지 않고 팝업 레이어 바깥을 눌렀을 때만 동작!!
  if (!sellingPopUp.classList.contains("close-popup")
      && (e.target !== sellingPopUp)) {
    popupClose();
  }
})

// 팝업 내부 요소 클릭 시 이벤트 전파 막기
sellingPopUp.addEventListener("click", e => {
  e.stopPropagation();
});


const sellingFrm = document.querySelector("#sellingFrm");

// 판매 신청 유효성 검사
sellingFrm.addEventListener("submit", e => {

  // 로그인하지 않았을 시 
  if(loginMember === null) {
    e.preventDefault();

    alert("로그인 후 이용해 주세요");
    if (confirm("로그인 페이지로 이동하시겠습니까?")) {
      location.href = "/myPage/myPageLogin";
    }
    return;
  }

  const selectedColor = document.querySelector(".selected-color");
  const selectedCapacity = document.querySelector(".selected-capacity");
  const selectedGrade = document.querySelector(".selected-grade");

  const colorNo = selectedColor.getAttribute("data-value");
  const capacityNumber = selectedCapacity.getAttribute("data-value");
  const gradeNumber = selectedGrade.getAttribute("data-value");

  const input1 = document.createElement("input");
  input1.name = "colorNo";
  input1.type = "hidden";
  input1.value = colorNo;

  const input2 = document.createElement("input");
  input2.name = "capacityNumber";
  input2.type = "hidden";
  input2.value = capacityNumber;

  const input3 = document.createElement("input");
  input3.name = "gradeNumber";
  input3.type = "hidden";
  input3.value = gradeNumber;

  console.log(colorNo, capacityNumber, gradeNumber);

  e.target.append(input1, input2, input3);

  if (selectedColor === null) {
    alert("색상을 선택해 주세요");
    e.preventDefault();
    return;
  }

  if (selectedCapacity === null) {
    alert("용량을 선택해 주세요");
    e.preventDefault();
    return;
  }

  if (selectedGrade === null) {
    alert("등급을 선택해 주세요");
    e.preventDefault();
    return;
  }

  const telTest = /^010[0-9]{8}$/;  // 010 으로 시작 이후 숫자 8개
  const accountTest = /^[0-9]{10,14}$/;   // 10 - 14 자리 숫자
  const nameTest =  /^[가-힣]{2,10}$/;  // 한글 이름 유효성 검사

  const clientName = document.querySelector("#clientName");
  const accountNo = document.querySelector("#accountNo");
  const clientTel = document.querySelector("#clientTel");

  const requestComment = document.querySelector("[name=requestComment]")

  if (telTest.test(clientTel.value.trim()) === false) {
    alert("사용할 수 있는 전화번호를 입력해 주세요");

    e.preventDefault();
    clientTel.focus();

    return;
  }

  if (accountTest.test(accountNo.value.trim()) === false) {
    alert("계좌 번호가 유효하지 않습니다");

    e.preventDefault();
    accountNo.focus();

    return;
  }

  if (nameTest.test(clientName.value.trim()) === false) {
    alert("2 글자 이상의 한글로 입력해 주세요");

    e.preventDefault();
    clientName.focus();

    return;
  }


  if ((requestComment.value.trim().length >= 80)) {
    alert("요청 사항은 80자 이내로 작성해 주세요");
    e.preventDefault();
    return;
  }

  if (!confirm("이대로 제출하시겠습니까?")) {

    alert("취소 되었습니다");
    e.preventDefault();

    return;
  }
  

  
})








let currentIndex = 0;

// 색상 슬라이드 기능
const showSlide = (index) => {
  const imgContainer = document.querySelector('.slide-img-container');
  const dots = document.querySelectorAll('.dot');
  const totalSlides = document.querySelectorAll('.img-slide').length;


  if (index >= totalSlides) {
    currentIndex = 0;
  } else if (index < 0) {
    currentIndex = totalSlides - 1;
  } else {
    currentIndex = index;
  }

  // 슬라이드 개수로 나누기
  const slateIndex = 100 / totalSlides;

  imgContainer.style.transform = `translateX(-${currentIndex * slateIndex}%)`;

  // 모든 dot을 비활성화하고 현재 슬라이드에 해당하는 dot만 활성화
  dots.forEach(dot => dot.classList.remove('active'));
  dots[currentIndex].classList.add('active');
}




// 이미지 팝업

// 팝업
const imgPopUp = document.querySelector(".img-popup");

// 팝업 내부 이미지
const popupImg = document.querySelector(".popup-img");

const imgList = document.querySelectorAll(".img-slide");

imgList.forEach((item, index) => {

  item.addEventListener("click", e => {

    console.log(item.src);

    popupImg.src = item.src.substr(16);

    e.stopPropagation(); // 이벤트 전파 방지 팝업이 열리면서 꺼지는 현상 방지
    popupOpenImg();
  })

})




// 팝업 열기
const popupOpenImg = () => {


  imgPopUp.classList.remove("close-popup");
  document.querySelector("#blackDisplay").classList.add("overlay");

}

// 팝업 닫기
const popupCloseImg = () => {
  imgPopUp.classList.add("close-popup");
  document.querySelector("#blackDisplay").classList.remove("overlay");
}

window.addEventListener("click", e => {

  // 팝업 레이어 가 닫혀있지 않고 팝업 레이어 바깥을 눌렀을 때만 동작!!
  if (!imgPopUp.classList.contains("close-popup")
      && (e.target !== imgPopUp)) {
      popupCloseImg();
  }
})

// 팝업 내부 요소 클릭 시 이벤트 전파 막기
imgPopUp.addEventListener("click", e => {
  e.stopPropagation();
});