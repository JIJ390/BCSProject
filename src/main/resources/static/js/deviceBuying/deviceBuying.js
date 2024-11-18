





// 색상 > 용량 > 등급 순으로 선택 하도록
// 상위 필터 선택하지 않으면 클릭 이벤트 불가

// + 비동기로 매물 조회해서 해당 매물 없으면 비활성화


const capacityContent = document.querySelector(".capacity-content");
const gradeContent = document.querySelector(".grade-content");

const colorCircle = document.querySelectorAll(".color-circle");
const capacityBox = document.querySelectorAll(".capacity-box");
const gradeBox = document.querySelectorAll(".grade-box");

const deviceNo = device.deviceNo;

// 불투명 설정
capacityContent.classList.add("none-click");
gradeContent.classList.add("none-click");

document.addEventListener("DOMContentLoaded", () => {

  // 색상 재고 확인 후 색상 회색 변경
  fetch("/device/buy/checkColor",  {
    method : "POST", 
    headers: {"Content-Type": "application/json"}, 
    body : deviceNo
  })
  .then(response => {
    if (response.ok) return response.json();
    throw new Error("조회 실패 : " + response.status);
  })
  .then(result => {

    // 결과(색상 번호 , 재고 수)
    result.forEach((item, index) => {
      if (item.colorCount === '0') colorCircle[index].classList.add("none-product");
    }) 
  })
  .catch(err => console.error(err));


  // 색깔 선택
  colorCircle.forEach(circle => {

    // 클릭 이벤트 부여
    circle.addEventListener("click", () => {

      // 선택된 색상 번호 
      const colorNo = circle.getAttribute("data-value");
      // 재고 화인
      checkSelectedColor(colorNo, circle);

    })
  })


  
  // 저장소 용량 선택
  capacityBox.forEach(capacity => {

    capacity.addEventListener("click", () => {

      const colorNo = document.querySelector(".selected-color").getAttribute("data-value");
      const capacityNumber = capacity.getAttribute("data-value");

      // 용량 재고 확인
      checkSelectedCapacity(colorNo, capacityNumber, capacity);

      expectedPrice();
    })
  })

  // 등급 선택
  gradeBox.forEach(grade => {

    grade.addEventListener("click", () => {

      
      const colorNo = document.querySelector(".selected-color").getAttribute("data-value");
      const capacityNumber = document.querySelector(".selected-capacity").getAttribute("data-value");
      const gradeNumber = grade.getAttribute("data-value");

      // 등급 재고 확인
      checkSelectedGrade(colorNo, capacityNumber, gradeNumber, grade);
    
      expectedPrice();
    })
  })

})









// 비동기 동기로 전환/ 색상 선택 시 재고 확인
const checkSelectedColor = async (colorNo, circle) => {

  const map = {
    "colorNo" : colorNo,  
    "deviceNo"  : deviceNo
  }

  try {
    // 재고 확인
    const response = await fetch("/device/buy/selectColor", {
      method: "POST",
      headers: {"Content-Type": "application/json"}, 
      body : JSON.stringify(map)
    });

    if (!response.ok) throw new Error("실패" + response.status);
    const colorCheck = await response.text();


    // 재고 0 이면 품절 알림
    if (colorCheck === '0') {
      alert("해당 색상 상품은 품절되었습니다");
      return;
    }

    
    gradeContent.classList.add("none-click");

    // 이전에 쌓인 클래스 제거
    capacityBox.forEach((item) => {
      item.classList.remove("none-product");
      item.classList.remove("selected-capacity");
    })

    // 이전에 쌓인 클래스 제거
    gradeBox.forEach((item) => {
      item.classList.remove("none-product");
      item.classList.remove("selected-grade");
    })

    // 용량 재고 확인 후 색상 회색 변경
    const response2 = await fetch("/device/buy/checkCapacity",  {
      method : "POST", 
      headers: {"Content-Type": "application/json"}, 
      body : JSON.stringify(map)
    });

    if (!response2.ok) throw new Error("실패" + response2.status);
    const result = await response2.json();

    // 결과(용량 번호 , 재고 수)
    result.forEach((item, index) => {
      if (item.capacityCount === '0') capacityBox[index].classList.add("none-product");
    }) 
    
    
    // 시간 지연
    setTimeout(function() {capacityContent.classList.remove("none-click")}, 50);

    // 선택 시 모든 색깔에서 클래스 제거
    colorCircle.forEach(c => {
      c.classList.remove('selected-color')
    })
    
    // 선택 클래스 추가
    circle.classList.add('selected-color');

    const colorName = circle.getAttribute("data-value2");

    document.querySelector(".color-name").innerText = colorName;


  } catch(err) {
    console.log(err);
  }

}



// 비동기 동기로 전환/ 용량 선택 시 재고 확인
const checkSelectedCapacity = async (colorNo, capacityNumber, capacity) => {

  const map = {
    "colorNo" : colorNo,  
    "capacityNumber" : capacityNumber,
    "deviceNo" : deviceNo
  }


  try {
    // 용량 재고 확인
    const response = await fetch("/device/buy/selectCapacity", {
      method: "POST",
      headers: {"Content-Type": "application/json"}, 
      body : JSON.stringify(map)
    });

    if (!response.ok) throw new Error("실패" + response.status);
    const capacityCheck = await response.text();

    // 재고 0 이면 품절 알림
    if (capacityCheck === '0') {
      alert("해당 용량 상품은 품절되었습니다");
      return;
    }

    // 이전에 쌓인 클래스 제거
    gradeBox.forEach((item) => {
      item.classList.remove("none-product");
      item.classList.remove("selected-grade");
    })

    // 등급 재고 확인 후 색상 회색 변경
    const response2 = await fetch("/device/buy/checkGrade",  {
      method : "POST", 
      headers: {"Content-Type": "application/json"}, 
      body : JSON.stringify(map)
    });

    if (!response2.ok) throw new Error("실패" + response2.status);
    const result = await response2.json();

      // 결과(용량 번호 , 재고 수)
    result.forEach((item, index) => {
      if (item.gradeCount === '0') gradeBox[index].classList.add("none-product");
    }) 
    
    // 시간 지연
    setTimeout(function() {gradeContent.classList.remove("none-click")}, 50);

    // 선택 시 모든 용량에서 클래스 제거
    capacityBox.forEach(c => {
      c.classList.remove('selected-capacity')
    })
  
    // 선택 클래스 추가
    capacity.classList.add('selected-capacity');


  } catch(err) {
    console.log(err);
  }

}



const checkSelectedGrade = async (colorNo, capacityNumber, gradeNumber, grade) => {
  const map = {
    "colorNo" : colorNo,  
    "capacityNumber" : capacityNumber,
    "gradeNumber" : gradeNumber,
    "deviceNo" : deviceNo,
  }

  try {

    // 등급 재고 확인
    const response = await fetch("/device/buy/selectGrade", {
      method: "POST",
      headers: {"Content-Type": "application/json"}, 
      body : JSON.stringify(map)
    });

    if (!response.ok) throw new Error("실패" + response.status);
    const gradeCheck = await response.text();

    // 재고 0 이면 품절 알림
    if (gradeCheck === '0') {
      alert("해당 등급 상품은 품절되었습니다");
      return;
    }

    // 선택 시 모든 등급에서 클래스 제거
    gradeBox.forEach(g => {
      g.classList.remove('selected-grade')
    })
    
    // 선택 클래스 추가
    grade.classList.add('selected-grade');


  } catch(err) {
    console.log(err);
  }

}










const buyingFrm = document.querySelector("#buyingFrm");

// 유효성 검사
buyingFrm.addEventListener("submit", e => {

  // 로그인 관련 검사 추가해야함

  const selectedColor = document.querySelector(".selected-color");
  const selectedCapacity = document.querySelector(".selected-capacity");
  const selectedGrade = document.querySelector(".selected-grade");
  
  const colorContent = document.querySelector(".color-content");
  const capacityContent = document.querySelector(".capacity-content");
  const gradeContent = document.querySelector(".grade-content");

  if (selectedColor === null) {
    e.preventDefault();
    alert("색상을 선택해 주세요");

    const scrollPosition = colorContent.offsetTop;

    window.scrollTo({
      top : scrollPosition - 150 , 
      behavior : "smooth" 
    });


    return;
  }

  if (selectedCapacity === null) {
    e.preventDefault();
    alert("용량을 선택해 주세요");

    const scrollPosition = capacityContent.offsetTop;

    window.scrollTo({
      top : scrollPosition - 150 , 
      behavior : "smooth" 
    });

    return;
  }

  if (selectedGrade === null) {
    e.preventDefault();
    alert("등급을 선택해 주세요");

    const scrollPosition = gradeContent.offsetTop;


    window.scrollTo({
      top : scrollPosition - 150 ,
      behavior : "smooth" 
    });

    return;
  }


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

  e.preventDefault();
  return;

  e.target.append(input1, input2, input3);

});










// 가격 비동기 변경
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
    "deviceNo"  : deviceNo
  }

  fetch("/device/buy/expectedPrice", {
    method : "POST", 
    headers: {"Content-Type": "application/json"}, 
    body : JSON.stringify(map)
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("변경 실패 : " + response.status);
  })
  .then(expectedPrice => {

    const priceSpan = document.querySelector(".price-span");

    priceSpan.innerText = expectedPrice.replace(/\B(?=(\d{3})+(?!\d))/g, ',') + ' ₩';
    
  })
  .catch(err => console.error(err));
  
}






// 그래프

document.addEventListener('DOMContentLoaded', () => {
  const ctx = document.getElementById('myChart').getContext('2d');

  // Custom plugin for vertical dotted lines
  const verticalLinePlugin = {
      id: 'verticalLinePlugin',
      afterDraw: (chart) => {
          const ctx = chart.ctx;
          const xScale = chart.scales.x;
          const yScale = chart.scales.y;

          chart.data.datasets[0].data.forEach((value, index) => {
              if (value !== undefined) { // 비어 있지 않은 데이터만 처리
                  const x = xScale.getPixelForValue(index); 
                  const y = yScale.getPixelForValue(value); 

                  ctx.save();
                  ctx.setLineDash([5, 5]); // 점선 설정 (5px 선, 5px 간격)
                  ctx.strokeStyle = 'rgba(0, 0, 0, 0.5)'; // 점선 색상 (반투명 검정)
                  ctx.beginPath();
                  ctx.moveTo(x, yScale.getPixelForValue(yScale.min)); 
                  ctx.lineTo(x, y); 
                  ctx.stroke();
                  ctx.restore();
              }
          });
      }
  };

  // Register the plugin
  Chart.register(verticalLinePlugin);

  const myChart = new Chart(ctx, {
      type: 'line',
      data: {
          labels: ['', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', ''],
          datasets: [{
              label: '', // 라벨을 빈 문자열로 설정하여 숨김
              data: [null, 1000000, 1200000, 1400000, 1500000, 1300000, 1100000, 1400000, 1600000, 1200000, 1300000, 1000000, 1000000],
              fill: false,
              borderColor: 'rgba(29, 29, 31, 1)',
              pointRadius: 0, // 데이터 포인트 표시 숨기기
              pointHoverRadius: 6, // 마우스를 올렸을 때 포인트 강조 크기
              pointHoverBackgroundColor: 'black',
              pointHitRadius: 15, // 마우스 오버 반응 범위를 넓게 설정
              tension: 0.1 // 직선으로 만들기
          }]
      },
      options: {
          responsive: false,
          scales: {
              x: {
                  grid: {
                      display: false // x축 격자선 숨기기
                  },
                  ticks: {
                      display: true, // x축 라벨 표시
                      color: 'black' // x축 라벨의 색상 설정
                  },
                  border: {
                      display: true, // x축 라인 표시
                      color: 'black'
                  }
              },
              y: {
                  beginAtZero: true, // y축의 시작점을 0으로 설정
                  grid: {
                      display: false // y축 격자선 숨기기
                  },
                  ticks: {
                      callback: function(value) {
                          return value.toLocaleString(); // y축에 천 단위 구분 기호 추가
                      },
                      display: true // y축 눈금 표시 여부
                  },
                  border: {
                      display: false // y축 라인 숨기기
                  }
              }
          },
          plugins: {
              legend: {
                  display: false // 상단의 라벨 숨기기
              }
          }
      }
  });

})