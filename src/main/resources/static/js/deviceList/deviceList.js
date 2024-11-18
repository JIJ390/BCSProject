/* 필터 관련 항목 얻어오기 */

const filterItem = document.querySelector("#filter-item");
const searchFilter = document.querySelector(".search-filter");

// 선택된 항목 저장 배열
let selectedItems = [];

// 전체 초기화 버튼 생성
const clearAllBtn = document.createElement("button");
clearAllBtn.classList.add("clear-btn");
clearAllBtn.textContent = "✕"; // 버튼 텍스트를 X로 설정
clearAllBtn.style.display = "none"; // 초기에는 숨김
clearAllBtn.addEventListener("click", () => {
  // 선택된 항목 초기화
  selectedItems = [];

  // 모든 필터 항목의 밑줄 제거
  document.querySelectorAll(".device-item").forEach(el => {
    el.style.textDecoration = "none";
  });

  // search-filter 초기화 (태그만 제거)
  searchFilter.querySelectorAll(".filter-tag").forEach(tag => {
    searchFilter.removeChild(tag);
  });

  // 전체 취소 버튼 숨김
  clearAllBtn.style.display = "none";
});

// 필터 리스트 생성 및 동작 설정
const selectFilterList = (filterType) => {
  fetch(`/selectFilterList?filterType=${filterType}`)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 오류");
    })
    .then(list => {
      // console.log("중복 제거 전 데이터:", list);

      // 중복 제거 및 정렬
      const uniqueList = [...new Set(list)];
      uniqueList.sort((a, b) => {
        if (filterType === "ram" || filterType === "inch") {
          return parseFloat(a) - parseFloat(b); // 숫자 정렬
        } else if (filterType === "hdd") {
          const sizeA = convertToGB(a);
          const sizeB = convertToGB(b);
          return sizeA - sizeB; // 용량 정렬
        }
      });

      // console.log("중복 제거 후 정렬된 데이터:", uniqueList);

      // 필터 항목 초기화
      filterItem.innerHTML = "";

      // 새 필터 항목 추가
      uniqueList.forEach(item => {
        const listItem = document.createElement("div");
        listItem.textContent = item;
        listItem.classList.add("device-item");

        // 클릭 이벤트 추가 (다중 선택)
        listItem.addEventListener("click", () => {
          if (selectedItems.includes(item)) {
            // 이미 선택된 경우: 배열에서 제거
            selectedItems = selectedItems.filter(i => i !== item);
            listItem.style.textDecoration = "none"; // 밑줄 제거
            listItem.style.fontWeight = "normal" // 두꺼운 글씨 제거

            // 태그 제거
            const tag = document.querySelector(`.filter-tag[data-value="${item}"]`);
            if (tag) {
              searchFilter.removeChild(tag);
            }
          } else {
            // 새로 선택된 경우: 배열에 추가
            selectedItems.push(item);
            listItem.style.textDecoration = "underline"; // 밑줄 추가
            listItem.style.fontWeight = "bold" // 두꺼운 글씨

            // 태그 추가
            const tag = document.createElement("div");
            tag.classList.add("filter-tag");
            tag.setAttribute("data-value", item);
            tag.textContent = item;

            // 태그에 제거 버튼 추가
            const removeBtn = document.createElement("button");
            removeBtn.classList.add("remove-btn");
            removeBtn.textContent = "✕";
            removeBtn.addEventListener("click", () => {
              // 태그 제거
              selectedItems = selectedItems.filter(i => i !== item);
              listItem.style.textDecoration = "none"; // 밑줄 제거
              listItem.style.fontWeight = "normal" // 두꺼운 글씨 제거
              searchFilter.removeChild(tag);
              

              // 전체 취소 버튼 숨기기 조건
              if (selectedItems.length === 0) {
                clearAllBtn.style.display = "none";
              }
            });

            tag.appendChild(removeBtn);
            searchFilter.insertBefore(tag, clearAllBtn); // 전체 취소 버튼 앞에 추가
          }

          // 전체 취소 버튼 표시
          if (selectedItems.length > 0) {
            clearAllBtn.style.display = "inline-block";
          } else {
            clearAllBtn.style.display = "none";
          }

          // console.log("현재 선택된 항목:", selectedItems);
        });

        filterItem.appendChild(listItem);
      });
    })
    .catch(err => console.error(err));
};

// 용량 변환 함수 (TB -> GB 변환)
const convertToGB = (size) => {
  if (size.includes("TB")) {
    return parseFloat(size) * 1024;
  } else if (size.includes("GB")) {
    return parseFloat(size);
  }
  return 0;
};

// 필터 버튼 클릭 이벤트
document.querySelector("#ram-filter").addEventListener("click", () => {
  selectFilterList("ram");
});
document.querySelector("#hdd-filter").addEventListener("click", () => {
  selectFilterList("hdd");
});
document.querySelector("#inch-filter").addEventListener("click", () => {
  selectFilterList("inch");
});

// search-filter 영역에 전체 초기화 버튼 추가
searchFilter.appendChild(clearAllBtn);




const deviceListContainer = document.querySelector(".deviceList-items-container");

searchBtn.addEventListener("click", () => {
  if (selectedItems.length === 0) {
    alert("선택된 항목이 없습니다!");
    return;
  }

  // 서버로 선택된 항목 전송
  fetch("/search", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      filters: selectedItems,
    }),
  })
    .then((response) => {
      if (response.ok) return response.json();
      throw new Error("검색 요청 실패");
    })
    .then((data) => {
      console.log("검색 결과:", data);

      // 기존 데이터 초기화
      deviceListContainer.innerHTML = "";

      // 서버에서 받은 데이터 동적으로 렌더링
      data.forEach((device) => {
        const deviceBox = document.createElement("div");
        deviceBox.classList.add("deviceList-item-box");

        deviceBox.innerHTML = `
          <div class="deviceList-item-section">
            <div class="deviceList-items">
              <img src="${device.deviceImg}" alt="${device.deviceName}">
            </div>
            <div style="margin-top: 15px;">${device.deviceName}</div>
          </div>
        `;

        // 선택된 DEVICE_NO는 data-value 속성으로 추가 (숨김 데이터)
        deviceBox.setAttribute("data-device-no", device.deviceNo);

        deviceListContainer.appendChild(deviceBox);
      });
    })
    .catch((err) => console.error(err));
});
