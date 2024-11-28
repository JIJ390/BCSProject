
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 내폰 판매내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 내폰 판매내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 내폰 판매내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 내폰 판매내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 내폰 판매내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 내폰 판매내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 내폰 판매내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */


// 리스트 조회 및 페이지네이션
const selectSellingList = (cp) => {

  fetch(`/myPage/selectSellingList?cp=${cp}&memberNo=${loginNo}`)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("AJAX 통신 실패");
    })
    .then(data => {
    
      // 비었을 때
      if (data.selectSellingList.length == 0) {
        const tableBody = document.querySelector("#orderTable tbody");
        tableBody.innerHTML = ""; 

        // 행 생성
        const row = document.createElement("tr");

        // 기종 정보 셀
        const modelCell = document.createElement("td");

        modelCell.innerText = "조회된 판매 내역이 없습니다";


        modelCell.setAttribute('colspan', '3')

        row.append(modelCell);

        tableBody.appendChild(row);

        const paginationBox = document.getElementById("paginationBox");
        paginationBox.innerHTML = ""; // 기존 페이지네이션 초기화
      } 
      else {

        const selectSellingList = data.selectSellingList;
        const pagination = data.pagination;
  
        // 판매 리스트 업데이트
        updateCompletedList(selectSellingList);
  
        // 페이지네이션 업데이트
        updatePagination(pagination);

      }

    })
    
    .catch(error => console.error("Error fetching completed list:", error));
};


// 리스트 업데이트 함수
const updateCompletedList = (completedList) => {
  const tableBody = document.querySelector("#orderTable tbody"); // 테이블 본문 선택
  tableBody.innerHTML = ""; // 기존 데이터 초기화

  completedList.forEach(item => {
    console.log(item);

    // 행 생성
    const row = document.createElement("tr");

    // 기종 정보 셀
    const modelCell = document.createElement("td");
    modelCell.textContent = 
      `${item.deviceName} / ${item.colorName} / ${item.capacityType}/ ${item.gradeType} 등급` || "정보 없음"; // 기종 정보가 없을 경우 기본값

    // 주문일자 셀
    const dateCell = document.createElement("td");
    dateCell.textContent = item.sellingDeviceDate || "날짜 없음"; // 날짜가 없을 경우 기본값

    // 상태 셀
    const statusCell = document.createElement("td");
    statusCell.textContent = item.statusContent || "상태 없음"; // 상태가 없을 경우 기본값

    // 행에 셀 추가
    row.appendChild(modelCell);
    row.appendChild(dateCell);
    row.appendChild(statusCell);

    // 테이블 본문에 행 추가
    tableBody.appendChild(row);
  });
};


// 페이지네이션 업데이트 함수
const updatePagination = (pagination) => {
  const paginationBox = document.getElementById("paginationBox");
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
      selectSellingList(cp); // 리스트만 업데이트
    });

    return button;
  };

  // <<, < 버튼
  paginationBox.appendChild(createPageButton(1, "<<"));
  paginationBox.appendChild(createPageButton(pagination.prevPage, "<"));

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
  selectSellingList(1); // 페이지 1번부터 시작
  // console.log(loginNo);
});


//ㅡㅡㅡㅡㅡㅡㅡ 뒤로가기 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ//

const backBtn = document.querySelector("#backBtn");

backBtn.addEventListener("click", () => {


    location.href = "myPageMain";


})
