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

      console.log("중복 제거 후 정렬된 데이터:", uniqueList);

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

            if (filterType === 'ram') {
              tag.classList.add("ram");
            }

            if (filterType === 'hdd') {
              tag.classList.add("hdd");
            }
            
            if (filterType === 'inch') {
              tag.classList.add("inch");
            }

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


// 필터 검색
const searchBtn = document.querySelector("#searchBtn").addEventListener("click", () => {

  const ramList = document.querySelectorAll(".ram");
  const hddList = document.querySelectorAll(".hdd");
  const inchList = document.querySelectorAll(".inch");

  let obj = {};

  
  ramList.forEach((item, index) => {
    obj[`ram${index}`] = item.getAttribute("data-value");
  })
  
  hddList.forEach((item, index) => {
    obj[`hdd${index}`] = item.getAttribute("data-value");
  })
  
  inchList.forEach((item, index) => {
    obj[`inch${index}`] = item.getAttribute("data-value");
  })
  
  if(Object.keys(obj).length === 0){
    alert("하나 이상의 필터를 선택해 주세요")
    e.preventdefault();

    return
  }
  // console.log(obj);

  

  fetch('/searchDetail', {
    method : "POST",
    headers : {"Content-Type": "application/json"},
    body : JSON.stringify(obj)
  }) 
  .then(response => {
    console.log("HTTP Status Code:", response.status); // 상태 코드 확인
    if(response.ok){
      return response.json();
    }
    throw new Error("조회 실패");
  })
  .then(result => {
    console.log(result); // 가져온 데이터 확인
  
    // 기존 내용을 초기화
    const itemsContainer = document.querySelector(".deviceList-items-container");
    itemsContainer.innerHTML = "";
  
    // 새로운 데이터로 업데이트
    result.forEach(item => {
      // 데이터에서 필요한 정보 추출
      const deviceName = item.deviceName || "이름 없음";
      const devicePrice = item.deviceBuyingPrice ? `${item.deviceBuyingPrice.toLocaleString()}원` : "가격 정보 없음";
      const deviceImg = item.deviceImg || "/images/default_image.png"; // 기본 이미지 처리
  
      // 아이템 박스 생성
      const itemBox = document.createElement("div");
      itemBox.classList.add("deviceList-item-box");
  
      // 아이템 섹션 생성
      const itemSection = document.createElement("div");
      itemSection.classList.add("deviceList-item-section");
  
      // 이미지 요소 생성
      const imgElement = document.createElement("img");
      imgElement.src = deviceImg;
      imgElement.alt = deviceName;
  
      // 이름 요소 생성
      const nameElement = document.createElement("div");
      nameElement.style.marginTop = "15px";
      nameElement.textContent = deviceName;
  
      // 가격 요소 생성
      const priceElement = document.createElement("div");
      priceElement.textContent = devicePrice;
  
      // 섹션에 요소 추가
      itemSection.appendChild(imgElement);
      itemSection.appendChild(nameElement);
      itemSection.appendChild(priceElement);
  
      // 박스에 섹션 추가
      itemBox.appendChild(itemSection);
  
      // 컨테이너에 박스 추가
      itemsContainer.appendChild(itemBox);
    });
  })
  .catch(err => console.error(err));
})









// // DOM 요소 가져오기
// const brandFilters = document.querySelectorAll("#galaxyFilter, #galaxyTabFilter, #iPhoneFilter, #iPadFilter");
// const filterContainer = document.querySelector(".filter-container");
// const searchContainer = document.querySelector(".search-container");

// // 선택 상태 저장 객체
// const filterState = {
//   brand: null,
//   filters: {
//     ram: [],
//     hdd: [],
//     inch: []
//   }
// };

// // 브랜드 클릭 이벤트
// brandFilters.forEach((brandFilter) => {
//   brandFilter.addEventListener("click", () => {
//     const brandMap = {
//       galaxyFilter: "SAMSUNG",
//       galaxyTabFilter: "SAMSUNGTAB",
//       iPhoneFilter: "Apple",
//       iPadFilter: "AppleTab"
//     };

//     // 브랜드 상태 업데이트
//     filterState.brand = brandMap[brandFilter.id];

//     // 선택된 브랜드 스타일 업데이트
//     brandFilters.forEach((el) => el.classList.remove("active-brand"));
//     brandFilter.classList.add("active-brand");

//     console.log("선택된 브랜드:", filterState.brand);

//     // 브랜드 선택 후 필터 초기화
//     initializeFilters();
//   });
// });

// // 필터 초기화 함수
// function initializeFilters() {
//   fetch(`/initializeFilters?brand=${filterState.brand}`)
//     .then((response) => {
//       if (response.ok) return response.json();
//       throw new Error("필터 초기화 실패");
//     })
//     .then((filters) => {
//       renderFilters(filters);
//     })
//     .catch((err) => console.error(err));
// }

// // 필터 렌더링 함수
// function renderFilters(filters) {
//   const { ram, hdd, inch } = filters;
//   filterContainer.innerHTML = "";

//   // 각 필터 추가
//   renderFilterList("RAM", ram, "ram");
//   renderFilterList("용량", hdd, "hdd");
//   renderFilterList("화면 크기", inch, "inch");
// }

// // 개별 필터 리스트 생성
// function renderFilterList(title, list, type) {
//   const filterGroup = document.createElement("div");
//   filterGroup.classList.add("filter-group");

//   const titleElement = document.createElement("h4");
//   titleElement.textContent = title;
//   filterGroup.appendChild(titleElement);

//   list.forEach((item) => {
//     const filterItem = document.createElement("div");
//     filterItem.textContent = item;
//     filterItem.classList.add("filter-item");

//     // 클릭 이벤트 추가
//     filterItem.addEventListener("click", () => {
//       toggleFilter(type, item);
//       filterItem.classList.toggle("selected-filter");
//       console.log("선택된 필터:", filterState.filters);
//     });

//     filterGroup.appendChild(filterItem);
//   });

//   filterContainer.appendChild(filterGroup);
// }

// // 필터 추가/제거
// function toggleFilter(type, value) {
//   const filterList = filterState.filters[type];
//   if (filterList.includes(value)) {
//     filterState.filters[type] = filterList.filter((item) => item !== value);
//   } else {
//     filterState.filters[type].push(value);
//   }
// }

// // 검색 버튼 이벤트
// searchContainer.querySelector("#searchBtn").addEventListener("click", () => {

//   const requestPayload = {
//     brand: filterState.brand,
//     filters: filterState.filters
//   };

//   console.log(requestPayload);

//   fetch("/searchByBrandAndFilters", {
//     method: "POST",
//     headers: { "Content-Type": "application/json" },
//     body: JSON.stringify(requestPayload)
//   })
//     .then((response) => {
//       if (response.ok) return response.json();
//       throw new Error("검색 실패");
//     })
//     .then((result) => {
//       updateSearchResults(result);
//     })
//     .catch((err) => console.error(err));
// });

// // 검색 결과 업데이트
// function updateSearchResults(results) {
//   const itemsContainer = document.querySelector(".deviceList-items-container");
//   itemsContainer.innerHTML = "";

//   results.forEach((item) => {
//     const itemBox = document.createElement("div");
//     itemBox.classList.add("deviceList-item-box");

//     const itemSection = document.createElement("div");
//     itemSection.classList.add("deviceList-item-section");

//     const imgElement = document.createElement("img");
//     imgElement.src = item.deviceImg || "/images/default_image.png";
//     imgElement.alt = item.deviceName || "이름 없음";

//     const nameElement = document.createElement("div");
//     nameElement.textContent = item.deviceName || "이름 없음";

//     const priceElement = document.createElement("div");
//     priceElement.textContent = item.deviceBuyingPrice
//       ? `${item.deviceBuyingPrice.toLocaleString()}원`
//       : "가격 정보 없음";

//     itemSection.appendChild(imgElement);
//     itemSection.appendChild(nameElement);
//     itemSection.appendChild(priceElement);
//     itemBox.appendChild(itemSection);
//     itemsContainer.appendChild(itemBox);
//   });
// }










