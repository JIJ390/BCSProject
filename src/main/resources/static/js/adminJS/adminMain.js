const adminSubmitForm = () => {
    document.querySelector('form').submit();
}


const ctx = document.getElementById('myChart').getContext('2d');

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



// const date = new Date();

// let month = date.getMonth() + 1;
// let year = date.getFullYear();

// let currentDate = `${year}.${month}`;

// 월별 조회
const totalSale = ['','' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ];
const totalPurchase = ['','' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ];
const totalProfit = ['','' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ,'' ];



const date = new Date();

let month = date.getMonth() + 1;
let year = date.getFullYear();

let currentDate = `${year}.${month}`;

console.log(currentDate);

document.querySelector(".current-date").innerText = currentDate + " 매출 현황";


// 기기 수
annualTotalSales.forEach((item) => {

    if ((month) == item.month.substr(-2)) {
        let countDevice = item.countDevice;
        document.querySelector("#currentSaleDevice").innerText = countDevice;
    } 
})

// 기기 수
annualTotalPurchases.forEach((item) => {

    if ((month) == item.month.substr(-2)) {
        let countDevice = item.countDevice;
        document.querySelector("#currentPurchaseDevice").innerText = countDevice;
    } 
})



// 12 번 반복
for(let i = 0; i < 12; i ++) {

  annualTotalSales.forEach((item, index) => {

    if ((i + 1) == item.month.substr(-2)) {
        totalSale[i] = item.sumPrice 
        console.log(item.sumPrice);
    } 
  })


  annualTotalPurchases.forEach((item) => {

    if ((i + 1) == item.month.substr(-2)) {
        totalPurchase[i] = item.sumSellPrice 
        console.log(item.sumSellPrice);
    }
  })

}

// 빈 공간에 0 담기
for(let i = 0; i < 12; i ++) {

    if(totalSale[i] === '') {
        totalSale[i] = 0;
       }

    if(totalPurchase[i] === '') {
        totalPurchase[i] = 0;
    }


    totalProfit[i] = totalSale[i] + totalPurchase[i];

    if (i + 1 === month) {
        document.querySelector("#currentSale").innerText = totalSale[i].toLocaleString('ko-KR') + ' ₩';
        document.querySelector("#currentPurchase").innerText = totalPurchase[i].toLocaleString('ko-KR') + ' ₩';
        document.querySelector("#currentProfit").innerText = totalProfit[i].toLocaleString('ko-KR') + ' ₩';
    }
}

console.log(totalSale);
console.log(totalPurchase);
console.log(totalProfit);


totalSale.unshift(null);
totalSale.push(null);

totalPurchase.unshift(null);
totalPurchase.push(null);

totalProfit.unshift(null);
totalProfit.push(null);

const labels = ['', '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월', ''];

const myChart = new Chart(ctx, {
type: 'line',
data: {
  labels: labels,
  datasets: [
      {
          label: '총 매출',
          data: totalSale,
          borderColor: 'rgba(29, 29, 31, 1)',
          backgroundColor: 'rgba(29, 29, 31, 0.1)',
          fill: false,
          tension: 0.1,
          pointRadius: 0, // 데이터 포인트 표시 숨기기
          pointHoverRadius: 6, // 마우스를 올렸을 때 포인트 강조 크기
          pointHoverBackgroundColor: 'rgba(29, 29, 31, 1)',
          pointHitRadius: 15, // 마우스 오버 반응 범위를 넓게 설정
          tension: 0.1 // 직선으로 만들기
      },
      {
          label: '총 지출',
          data: totalPurchase,
          borderColor: 'rgba(255, 99, 132, 1)',
          backgroundColor: 'rgba(255, 99, 132, 0.1)',
          fill: false,
          tension: 0.1,
          pointRadius: 0, // 데이터 포인트 표시 숨기기
          pointHoverRadius: 6, // 마우스를 올렸을 때 포인트 강조 크기
          pointHoverBackgroundColor: 'rgba(255, 99, 132, 1)',
          pointHitRadius: 15, // 마우스 오버 반응 범위를 넓게 설정
          tension: 0.1 // 직선으로 만들기
      },
      {
          label: '순수익',
          data: totalProfit,
          borderColor: 'rgba(75, 192, 192, 1)',
          backgroundColor: 'rgba(75, 192, 192, 0.1)',
          fill: false,
          tension: 0.1,
          pointRadius: 0, // 데이터 포인트 표시 숨기기
          pointHoverRadius: 6, // 마우스를 올렸을 때 포인트 강조 크기
          pointHoverBackgroundColor: 'rgba(75, 192, 192, 1)',
          pointHitRadius: 15, // 마우스 오버 반응 범위를 넓게 설정
          tension: 0.1 // 직선으로 만들기
      }
  ]
},
options: {
  responsive: false,
  scales: {
      x: {
          grid: {
              display: false
          },
          ticks: {
              color: 'black'
          },
          border: {
              display: true,
              color: 'black'
          }
      },
      y: {
          beginAtZero: true,
          grid: {
              display: true, // y축의 격자선을 표시
              color: (context) => context.tick.value === 0 ? 'black' : 'rgba(0, 0, 0, 0.1)', // y=0 기준선을 강조
              lineWidth: (context) => context.tick.value === 0 ? 1 : 1 // y=0 기준선의 두께 조절
          },
          ticks: {
              callback: function (value) {
                  return value.toLocaleString(); // 천 단위 구분 기호
              },
              color: 'black'
          },
          border: {
              display: true,
              color: 'black'
          }
      }
  },
  plugins: {
      legend: {
          display: true // 상단의 데이터셋 라벨 표시
      }
  }
},
plugins: [verticalLinePlugin]
});
