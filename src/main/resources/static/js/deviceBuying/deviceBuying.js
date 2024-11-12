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