const colorCircle = document.querySelectorAll(".color-circle");
const capacityBox = document.querySelectorAll(".capacity-box");
const gradeBox = document.querySelectorAll(".grade-box");

document.addEventListener("DOMContentLoaded", () => {
  

  // 색깔 선택
  colorCircle.forEach(circle => {

    circle.addEventListener("click", () => {

      // 선택 시 모든 색깔에서 클래스 제거
      colorCircle.forEach(c => {
        c.classList.remove('selected')
      })

      // 선택 클래스 추가
      circle.classList.add('selected');

    })
  })



  // 등급 선택
  gradeBox.forEach(grade => {

    grade.addEventListener("click", () => {

      // 선택 시 모든 등급에서 클래스 제거
      gradeBox.forEach(g => {
        g.classList.remove('selected-box')
      })

      // 선택 클래스 추가
      grade.classList.add('selected-box');

      expectedPrice();
    })
  })

  
  // 저장소 용량 선택
  capacityBox.forEach(capacity => {

    capacity.addEventListener("click", () => {

      // 선택 시 모든 용량에서 클래스 제거
      capacityBox.forEach(c => {
        c.classList.remove('selected-box')
      })

      // 선택 클래스 추가
      capacity.classList.add('selected-box');

      expectedPrice();
    })
  })

})


const expectedPrice = () => {
  const selectedContent = document.querySelectorAll(".selected-box");

  // 선택에 따라 가산되는 가격
  let plusPice = 0;

  selectedContent.forEach(select => {
    plusPice += Number(select.getAttribute("data-value"));
  })

  fetch("/device/sell/expectedPrice", {
    method : "POST", 
    headers: {"Content-Type": "application/json"}, 
    body : plusPice
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("충전 실패 : " + response.status);
  })
  .then(expectedPrice => {

    console.log(expectedPrice);

    const priceSpan = document.querySelector(".selling-span2");

    priceSpan.innerText = expectedPrice.replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '₩';
    
  })
  .catch(err => console.error(err));
  
}