





// 색상 > 용량 > 등급 순으로 선택 하도록
// 상위 필터 선택하지 않으면 클릭 이벤트 불가

// + 비동기로 매물 조회해서 해당 매물 없으면 비활성화


const capacityContent = document.querySelector(".capacity-content");
const gradeContent = document.querySelector(".grade-content");

const colorCircle = document.querySelectorAll(".color-circle");
const capacityBox = document.querySelectorAll(".capacity-box");
const gradeBox = document.querySelectorAll(".grade-box");

// 불투명 설정
capacityContent.classList.add("none-click");
gradeContent.classList.add("none-click");

document.addEventListener("DOMContentLoaded", () => {



  // 색깔 선택
  colorCircle.forEach(circle => {

    circle.addEventListener("click", () => {

      capacityContent.classList.remove("none-click");

      // 선택 시 모든 색깔에서 클래스 제거
      colorCircle.forEach(c => {
        c.classList.remove('selected-color')
      })

      // 선택 클래스 추가
      circle.classList.add('selected-color');

      const colorName = circle.getAttribute("data-value2");

      document.querySelector(".color-name").innerText = colorName;
    })
  })


  
  // 저장소 용량 선택
  capacityBox.forEach(capacity => {

    capacity.addEventListener("click", () => {

      gradeContent.classList.remove("none-click");

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
    "deviceNo"  : device.deviceNo
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



const buyingBtn = document.querySelector("#buyingBtn");

// 유효성 검사
buyingBtn.addEventListener("click", e => {

  // 로그인 관련 검사 추가해야함

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

});





















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