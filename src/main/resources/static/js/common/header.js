/* 헤더 매뉴 hover시 background opacity */

const dep1 = document.querySelector(".dep1")
const dep2 = document.querySelector(".dep2")

dep1.addEventListener("mouseenter", () => {
  const main = document.querySelector("main")
  const footer = document.querySelector("footer")
  main.style.opacity = 0.3
  footer.style.opacity = 0.3
})

dep1.addEventListener("mouseleave", () => {
  const main = document.querySelector("main")
  const footer = document.querySelector("footer")
  main.style.opacity = 1
  footer.style.opacity = 1
})


document.addEventListener("DOMContentLoaded", function () {
  const galaxyPhoneLink = document.querySelector(".info li a[href='#']");
  const galaxyContent = document.getElementById("galaxy-content");
  const dropdown = document.querySelector('.dropdown');

  // 처음에 내용을 숨깁니다.
  galaxyContent.style.display = 'none';

  // "Galaxy Phone" 클릭 시 토글
  galaxyPhoneLink.addEventListener("click", function (event) {
      event.preventDefault(); // 기본 링크 동작을 막음

      // 내용을 토글(보이기/숨기기)합니다.
      if (galaxyContent.style.display === 'none') {
          galaxyContent.style.display = 'block';
      } else {
          galaxyContent.style.display = 'none';
      }
  });

  // 드롭다운 메뉴에서 마우스가 나갔을 때 숨기기
  dropdown.addEventListener("mouseleave", function () {
      galaxyContent.style.display = 'none';
  });
});


// document.addEventListener("DOMContentLoaded", function () {
//   const galaxyPhoneLink = document.querySelector(".info li a[href='#']");
//   const iPhoneLink = document.querySelector(".info li:nth-child(2) a");
//   const galaxyContent = document.getElementById("galaxy-content");
//   const iPhoneContent = document.getElementById("iPhone-content");
//   const dropdownContainer = document.querySelector('.dropdownContainor');
//   const galaxyItems = document.querySelectorAll("#galaxy-content li");
//   const iPhoneItems = document.querySelectorAll("#iPhone-content li");
//   const selectionMessage = document.querySelector(".sideMenu1 span");
//   const buy_phoneModels = document.getElementById("buy_phoneModels");
//   const sell_phoneModels = document.getElementById("sell_phoneModels");
//   const modelMessage = document.getElementById("selectionMessage");

//   // 처음에 내용을 숨깁니다.
//   galaxyContent.style.display = 'none';
//   iPhoneContent.style.display = 'none';

//   // 메뉴 토글을 비동기적으로 처리
//   function toggleMenu(contentElement) {
//       return fetch('https://jsonplaceholder.typicode.com/posts/1') // 임의의 비동기 요청 (더미 API)
//           .then(response => {
//               if (!response.ok) {
//                   throw new Error('네트워크 응답에 문제가 있습니다.');
//               }
//               return response.json(); // 응답을 JSON으로 변환
//           })
//           .then(data => {
//               // 데이터를 가져온 이후 메뉴를 토글
//               if (contentElement.style.display === 'none') {
//                   contentElement.style.display = 'block';
//                   selectionMessage.style.display = 'none'; // "원하는 종류를 선택하세요" 메시지를 숨김
//                   modelMessage.style.display = 'block'; // "종류별" 메시지는 유지
//                   buy_phoneModels.style.display = 'block'; // 폰 모델 목록을 보이게 함
//                   sell_phoneModels.style.display = 'block'; // 폰 모델 목록을 보이게 함
//               } else {
//                   contentElement.style.display = 'none';
//                   selectionMessage.style.display = 'block'; // 메시지를 다시 보이게 함
//                   modelMessage.style.display = 'none'; // "종류별" 메시지를 숨김
//                   buy_phoneModels.style.display = 'none'; // 폰 모델 목록을 숨김
//                   sell_phoneModels.style.display = 'none'; // 폰 모델 목록을 숨김
//               }
//           })
//           .catch(error => {
//               console.error('에러 발생:', error);
//           });
//   }

//   // "Galaxy Phone" 클릭 시 메뉴 토글
//   galaxyPhoneLink.addEventListener("click", function (event) {
//       event.preventDefault(); // 기본 링크 동작을 막음
//       toggleMenu(galaxyContent); // 비동기 방식으로 메뉴 토글
//   });

//   // "iPhone" 클릭 시 메뉴 토글
//   iPhoneLink.addEventListener("click", function (event) {
//       event.preventDefault(); // 기본 링크 동작을 막음
//       toggleMenu(iPhoneContent); // 비동기 방식으로 메뉴 토글
//   });

//   // 메뉴에서 마우스가 나갔을 때 메뉴 숨기기
//   dropdownContainer.addEventListener("mouseleave", function () {
//       galaxyContent.style.display = 'none';
//       iPhoneContent.style.display = 'none';
//       selectionMessage.style.display = 'block'; // "원하는 종류를 선택하세요" 메시지를 다시 보이게 함
//       buy_phoneModels.style.display = 'none'; // 폰 모델 목록을 숨김
//       sell_phoneModels.style.display = 'none'; // 폰 모델 목록을 숨김
//   });

//   // 각 갤럭시 메뉴 항목에 클릭 이벤트 추가 (필터 및 검색 기능 구현)
//   galaxyItems.forEach(item => {
//       item.addEventListener("click", function () {
//           // 기존에 active 클래스가 있는 항목에서 제거
//           galaxyItems.forEach(i => i.classList.remove("active"));
//           // 현재 클릭된 항목에 active 클래스 추가
//           item.classList.add("active");

//           // 선택된 항목의 텍스트를 서버로 전송하여 필터 요청 수행
//           const selectedModel = item.textContent;

//           // 비동기적으로 서버에 데이터 요청
//           fetch('https://example.com/api/filter', { // 서버 엔드포인트 주소
//               method: 'POST',
//               headers: {
//                   'Content-Type': 'application/json'
//               },
//               body: JSON.stringify({ model: selectedModel }) // 서버로 선택된 모델 데이터 전송
//           })
//           .then(response => {
//               if (!response.ok) {
//                   throw new Error('네트워크 응답에 문제가 있습니다.');
//               }
//               return response.json();
//           })
//           .then(data => {
//               // 서버에서 필터링된 데이터를 받아온 후 화면에 표시
//               displayFilteredResults(data);
//           })
//           .catch(error => {
//               console.error('에러 발생:', error);
//           });
//       });
//   });

//   // 각 iPhone 메뉴 항목에 클릭 이벤트 추가 (필터 및 검색 기능 구현)
//   iPhoneItems.forEach(item => {
//       item.addEventListener("click", function () {
//           // 기존에 active 클래스가 있는 항목에서 제거
//           iPhoneItems.forEach(i => i.classList.remove("active"));
//           // 현재 클릭된 항목에 active 클래스 추가
//           item.classList.add("active");

//           // 선택된 항목의 텍스트를 서버로 전송하여 필터 요청 수행
//           const selectedModel = item.textContent;

//           // 비동기적으로 서버에 데이터 요청
//           fetch('https://example.com/api/filter', { // 서버 엔드포인트 주소
//               method: 'POST',
//               headers: {
//                   'Content-Type': 'application/json'
//               },
//               body: JSON.stringify({ model: selectedModel }) // 서버로 선택된 모델 데이터 전송
//           })
//           .then(response => {
//               if (!response.ok) {
//                   throw new Error('네트워크 응답에 문제가 있습니다.');
//               }
//               return response.json();
//           })
//           .then(data => {
//               // 서버에서 필터링된 데이터를 받아온 후 화면에 표시
//               displayFilteredResults(data);
//           })
//           .catch(error => {
//               console.error('에러 발생:', error);
//           });
//       });
//   });

//   // 필터링된 결과를 화면에 표시하는 함수
//   function displayFilteredResults(data) {
//       // 여기에 필터링된 데이터를 HTML에 표시하는 로직을 작성하세요
//       // 예: 특정 섹션에 데이터를 추가하여 필터링된 제품 목록을 보여줌
//       const resultSection = document.getElementById('resultSection'); // 결과를 표시할 섹션 (가정)
//       resultSection.innerHTML = ''; // 기존 내용 초기화

//       data.forEach(item => {
//           const productElement = document.createElement('div');
//           productElement.className = 'product-item';
//           productElement.innerHTML = `
//               <h3>${item.name}</h3>
//               <p>${item.description}</p>
//               <p>가격: ${item.price}원</p>
//           `;
//           resultSection.appendChild(productElement);
//       });
//   }
// });





dep2.addEventListener("mouseenter", () => {
  const main = document.querySelector("main")
  const footer = document.querySelector("footer")
  main.style.opacity = 0.3
  footer.style.opacity = 0.3
})

dep2.addEventListener("mouseleave", () => {
  const main = document.querySelector("main")
  const footer = document.querySelector("footer")
  main.style.opacity = 1
  footer.style.opacity = 1
})