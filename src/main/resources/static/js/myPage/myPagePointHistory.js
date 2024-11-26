/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 포인트 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 포인트 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 포인트 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 포인트 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 포인트 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 포인트 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 포인트 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

// 포인트 조회 및 페이지네이션
const selectPointList = (cp) => {


  fetch(`/myPage/selectPointList?cp=${cp}&memberNo=${loginNo}`)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("AJAX 통신 실패");
    })
    .then(data => {
      
      const selectPointList = data.selectPointList;
      const pagination = data.pagination;

      // console.log(selectPointList);
      // console.log(pagination);

      // 포인트 내역 업데이트
      updatePointList(selectPointList);

      // 페이지네이션 업데이트
      updatePagination1(pagination);
    })
    
    .catch(error => console.error("Error fetching completed list:", error));
};


// 리스트 업데이트 함수
const updatePointList = (pointList) => {
  const tableBody = document.querySelector("#pointTable tbody"); // 테이블 본문 선택
  tableBody.innerHTML = ""; // 기존 데이터 초기화

  pointList.forEach(item => {
    console.log(item);

    // 행 생성
    const row = document.createElement("tr");

    // 포인트 내역
    const logContent = document.createElement("td");
    logContent.textContent = item.pointLogContent || "내용 없음"; // 포인트 내역 없을 경우 기본값

    // 적립/차감일시
    const logDate = document.createElement("td");
    logDate.textContent = item.pointLogDate || "날짜 없음"; // 날짜가 없을 경우 기본값

    // 포인트 +/-
    const logAmount = document.createElement("td");
    logAmount.textContent = item.pointLogAmount || "내역 없음"; // 내역 없을 경우 기본값

    // 총 포인트
    const logBalance = document.createElement("td");
    logBalance.textContent = item.pointLogBalance || "내역 없음"; // 내역 없을 경우 기본값

    // 행에 셀 추가
    row.appendChild(logContent);
    row.appendChild(logDate);
    row.appendChild(logAmount);
    row.appendChild(logBalance);

    // 테이블 본문에 행 추가
    tableBody.appendChild(row);
  });
};


// 페이지네이션 업데이트 함수
const updatePagination1 = (pagination) => {
  const paginationBox = document.getElementById("paginationPointBox");
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
      selectPointList(cp); // 리스트만 업데이트
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
  selectPointList(1); // 페이지 1번부터 시작
});