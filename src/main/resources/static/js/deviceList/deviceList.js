/* 필터 관련 항목 얻어오기 */

const filterItem = document.querySelector("#filter-item");

const selectFilterList = (filterType) => {
  fetch(`/selectFilterList?filterType=${filterType}`) // 필터 타입 전달
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .then(list => {
      console.log("중복 제거 전 데이터:", list);

      // 중복 제거
      const uniqueList = [...new Set(list)];

      // 정렬 (숫자를 추출한 후 비교)
      uniqueList.sort((a, b) => {
        const numA = parseFloat(a); // "6.1" -> 6.1
        const numB = parseFloat(b); // "6.06" -> 6.06
        return numA - numB;
      });

      console.log("중복 제거 후 정렬된 데이터:", uniqueList);

      // 이전 데이터 삭제
      filterItem.innerHTML = "";

      // 새 데이터 추가
      uniqueList.forEach(item => {
        const listItem = document.createElement("div");
        listItem.textContent = item; // 단순히 데이터 출력
        listItem.classList.add("device-item");
        filterItem.appendChild(listItem);
      });
    })
    .catch(err => console.error(err));
};

// 필터 클릭 이벤트
document.querySelector("#ram-filter").addEventListener('click', () => {
  selectFilterList("ram");
});
document.querySelector("#hdd-filter").addEventListener('click', () => {
  selectFilterList("hdd");
});
document.querySelector("#inch-filter").addEventListener('click', () => {
  selectFilterList("inch");
});
