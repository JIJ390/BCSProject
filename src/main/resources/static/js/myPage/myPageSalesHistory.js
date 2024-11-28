/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 구매 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 구매 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 구매 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 구매 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 구매 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 구매 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 구매 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

// 구매내역 조회 및 페이지네이션
const selectBuyingList = (cp) => {

  console.log(cp);
  console.log(cp);

  console.log(loginNo);
  
  fetch(`/myPage/selectBuyingList?cp=${cp}&memberNo=${loginNo}`)
  .then(response => {
      if (response.ok) return response.json();
      throw new Error("AJAX 통신 실패");
    })
    .then(data => {

          // 비었을 때
          if (data.selectBuyingList.length == 0) {
            const tableBody = document.querySelector("#buyingTable tbody");
            tableBody.innerHTML = ""; 
    
            // 행 생성
            const row = document.createElement("tr");
    
            // 기종 정보 셀
            const modelCell = document.createElement("td");
    
            modelCell.innerText = "조회된 구매 내역이 없습니다";
    
    
            modelCell.setAttribute('colspan', '3')
    
            row.append(modelCell);
    
            tableBody.appendChild(row);
    
            const paginationBox = document.getElementById("paginationBuyingBox");
            paginationBox.innerHTML = ""; // 기존 페이지네이션 초기화
            
          }      else {

          console.log("asdasdasdas");


      const selectBuyingList = data.selectBuyingList;
      const pagination = data.pagination;

      console.log(selectBuyingList);
      console.log(pagination);

      // 구매 내역 업데이트
      updateBuyingList(selectBuyingList);

      // 페이지네이션 업데이트
      updatePagination2(pagination);
      
      }
      
          
    })
    .catch(error => console.error("Error fetching completed list:", error));
};


// 리스트 업데이트 함수
const updateBuyingList = (buyingList) => {
  const tableBody = document.querySelector("#buyingTable tbody"); // 테이블 본문 선택
  tableBody.innerHTML = ""; // 기존 데이터 초기화

  buyingList.forEach(item => {
    console.log(item);

    // 행 생성
    const row = document.createElement("tr");

   // 기종 정보 셀
   const phoneInfo = document.createElement("td");
   phoneInfo.textContent = 
     `${item.deviceName} / ${item.colorName} / ${item.capacityType}/ ${item.gradeType} 등급` || "정보 없음"; // 기종 정보가 없을 경우 기본값

    // 구매일자
    const buyingDate = document.createElement("td");
    buyingDate.textContent = item.orderDate || "날짜 없음"; // 날짜가 없을 경우 기본값

    // 구매상태
    const buyingStatus = document.createElement("td");
    buyingStatus.textContent = item.orderStatusContent || "구매내역 없음"; // 내역 없을 경우 기본값


    // 행에 셀 추가
    row.appendChild(phoneInfo);
    row.appendChild(buyingDate);
    row.appendChild(buyingStatus);


    // 테이블 본문에 행 추가
    tableBody.appendChild(row);
  });
};


// 페이지네이션 업데이트 함수
const updatePagination2 = (pagination) => {
  const paginationBox = document.getElementById("paginationBuyingBox");
  paginationBox.innerHTML = ""; // 기존 페이지네이션 초기화

  const createPageButton = (page, text, isActive = false) => {
    const button = document.createElement("a");
    button.href = "#";
    button.classList.add("page-btn");
    button.dataset.page = page > 0 ? page : 1; // 0 이하일 경우 1로 보정
    button.textContent = text;

    if (isActive) button.classList.add("active");

    button.addEventListener("click", (event) => {
      event.preventDefault();
      const cp = parseInt(event.target.dataset.page); // 클릭된 버튼의 페이지 번호 추출
      selectBuyingList(cp); // 리스트만 업데이트
    });

    return button;
  };

  // <<, < 버튼
  paginationBox.appendChild(createPageButton(1, "<<"));
  paginationBox.appendChild(createPageButton(pagination.prevPage, "<"));

  console.log(pagination.startPage);
  console.log(pagination.endPage);

  // 동적 페이지 번호 버튼
  for (let i = pagination.startPage; i <= pagination.endPage; i++) {
    const isActive = i === pagination.currentPage;
    paginationBox.appendChild(createPageButton(i, i, isActive));
  }

  // >, >> 버튼
  paginationBox.appendChild(createPageButton(pagination.nextPage, ">"));
  paginationBox.appendChild(createPageButton(pagination.maxPage, ">>"));
};

// 초기 실행
document.addEventListener("DOMContentLoaded", () => {
  selectBuyingList(1); // 페이지 1번부터 시작
  console.log(loginNo);
});

//ㅡㅡㅡㅡㅡㅡㅡ 뒤로가기 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ//

const backBtn = document.querySelector("#backBtn");

backBtn.addEventListener("click", () => {


    location.href = "myPageMain";


})