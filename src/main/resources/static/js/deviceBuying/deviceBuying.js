

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

  // 처음 페이지 로딩 시 
  showSlide(0);

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
    
    })
  })

  // 반올림
  let avgScore = document.querySelector(".star-score").innerText;

  avgScore = Math.round(avgScore * 2) / 2;

  // 리뷰 전체 별점 부여
  highlightStars(avgScore);




  // 리뷰 목록 별점 부여 (초기 3개)
  const reviewStarContainer = document.querySelectorAll(".review-star-container");

  reviewStarContainer.forEach((reviewStar) => {
    const score = reviewStar.getAttribute("data-value");

    console.log(score);

                  // 요소 내부에서 모두 찾기
    const stars = reviewStar.querySelectorAll(".review-star");

    console.log(stars);

    stars.forEach((star, index) => {
      if (star.dataset.value <= score) {
        
        // 0.5 단위
        if (index % 2 !== 0) {
          star.src = "/images/review2/filled-star-right.png";
        }
        else {
          star.src = "/images/review2/filled-star-left.png";
        }

      }
    });

  })

})




// 별 채우기 함수
const highlightStars = (rating) => {

  console.log(rating);

  const stars = document.querySelectorAll(".star-container .star");

  stars.forEach((star, index) => {
      if (star.dataset.value <= rating) {
        
        // 0.5 단위
        if (index % 2 !== 0) {
          star.src = "/images/review2/filled-star-right.png";
        }
        else {
          star.src = "/images/review2/filled-star-left.png";
        }

      }
  });
}










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

    // 색상 인덱스로 슬라이드 이동
    const index = circle.getAttribute("data-value3");

    showSlide(index);

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
    expectedPrice();

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
    expectedPrice();

  } catch(err) {
    console.log(err);
  }

}






const buyingFrm = document.querySelector("#buyingFrm");

// 유효성 검사
buyingFrm.addEventListener("submit", e => {

  // 로그인하지 않았을 시 
  if(loginMember === null) {
    e.preventDefault();

    alert("로그인 후 이용해 주세요");

    if (confirm("로그인 페이지로 이동하시겠습니까?")) {
      urlBackup = location.pathname;
      location.href = "/myPage/myPageLogin";
    }

    if(loginMember !== null) {
      location.href = `/device/buy/${deviceNo}`;
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


  // 결제 페이지에서 뒤로 가기 시 여러 개의 input 태그가 쌓여 오류 발생 해결 
  // form 내부의 모든 input 태그 가져오기
  const inputs = e.target.querySelectorAll('input');
  // 각 input 태그를 삭제
  inputs.forEach(input => input.remove());



  const input1 = document.createElement("input");
  input1.name = "colorNo";
  input1.type = "hidden";
  input1.value = selectedColor.getAttribute("data-value");

  const input2 = document.createElement("input");
  input2.name = "capacityNumber";
  input2.type = "hidden";
  input2.value = selectedCapacity.getAttribute("data-value");


  const input3 = document.createElement("input");
  input3.name = "gradeNumber";
  input3.type = "hidden";
  input3.value = selectedGrade.getAttribute("data-value");

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

  const centerTextPlugin = {
    id: 'centerTextPlugin',
    beforeDraw: (chart) => {
        const { width, height, ctx } = chart;

        // 텍스트 설정
        const text = "최근 거래가 존재하지 않습니다";
        const fontSize = 40; // 텍스트 크기
        ctx.save();
        ctx.font = `${fontSize}px Pretendard-Regular`;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';

        // 캔버스 중앙 좌표 계산
        const centerX = width / 2;
        const centerY = height / 2;

        // 텍스트 그리기
        ctx.fillStyle = 'gray'; // 텍스트 색상
        ctx.fillText(text, centerX, centerY);
        ctx.restore();
    }
};

  // Register the plugin
  Chart.register(verticalLinePlugin);

  if (priceList.length === 0) {
    Chart.register(centerTextPlugin);
  }



  const date = new Date();

  let month = date.getMonth() + 1;
  let year = date.getFullYear();

  let currentDate = `${year}.${month}`;

  console.log(currentDate);

  document.querySelector(".current-date").innerText = currentDate;

  // 월별 조회
  const priceArr = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];

  // 12 번 반복
  for(let i = 0; i < 12; i ++) {

    priceList.forEach((item) => {

      priceArr[i].month = i + 1

      if ((i + 1) == item.month.substr(-2)) {
        priceArr[i].avgPrice = item.avgPrice 
      }
    })
  }



  const myChart = new Chart(ctx, {
      type: 'line',
      data: {
          labels: ['', 1, 2, 3, 4, 
                       5, 6, 7, 8,
                       9, 10, 11, 12, ''],
          datasets: [{
              label: '', // 라벨을 빈 문자열로 설정하여 숨김
              data: [null, priceArr[0].avgPrice, priceArr[1].avgPrice, priceArr[2].avgPrice, priceArr[3].avgPrice, 
                           priceArr[4].avgPrice, priceArr[5].avgPrice, priceArr[6].avgPrice, priceArr[7].avgPrice,
                           priceArr[8].avgPrice, priceArr[9].avgPrice, priceArr[10].avgPrice, priceArr[11].avgPrice, null],
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






// 리뷰 더 보기!!
const reviewPlusBtn = document.querySelector("#reviewPlusBtn");

reviewPlusBtn?.addEventListener("click", () => {

  const reviewCount = document.querySelectorAll(".review-content-box").length;

  // 현재 화면 상의 리뷰 숫자와 기종 번호 비동기 통신으로 보내기
  const obj = {
    "reviewCount" : reviewCount + 1,
    "deviceNo" : deviceNo
  }

  fetch("/device/buy/reviewPlus", {
    method : "POST", 
    headers: {"Content-Type": "application/json"}, 
    body : JSON.stringify(obj)
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("리뷰 조회 실패 : " + response.status);
  })
  .then(html => {

    const reviewContainer = document.querySelector(".review-container");

    // div 에 응답 html 모두 넣기
    const div = document.createElement("div");
    div.innerHTML = html;

    // div 내부 별점 컨테이너 위치 찾기
    const reviewStar = div.querySelector(".review-star-container")

    // 컨테이너 별점 value 얻기
    const score = reviewStar.getAttribute("data-value");

    // 요소 내부에서 모두 찾기
    const stars = reviewStar.querySelectorAll(".review-star");

    console.log(stars);

    stars.forEach((star, index) => {
      if (star.dataset.value <= score) {
        
        // 0.5 단위
        if (index % 2 !== 0) {
          star.src = "/images/review2/filled-star-right.png";
        }
        else {
          star.src = "/images/review2/filled-star-left.png";
        }

      }
    });


    // reviewCheck 확인 확인
    const reviewCheck = div.querySelector(".review-check");

    /* review-check 가 존재하고 그 값이 1일떄 */
    if ((reviewCheck !== null) && (reviewCheck.getAttribute("data-value") == 1)) {
      reviewPlusBtn.remove();
    }

    reviewContainer.append(div);

    
    
  })
  .catch(err => console.error(err));

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