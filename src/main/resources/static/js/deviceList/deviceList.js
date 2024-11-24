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


  // 쿼리스트링 브랜드 값 가져오기
  const url = new URL(location.href);
  const brandSearch = url.searchParams.get("category");

  // 객체에 값 담기
  obj['brand'] = brandSearch;

  console.log(brand);


  // 검색일 때
  if (brand !== undefined) {
    console.log(brand);
    obj['brand'] = brand;
  }


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
      const deviceLink = `/device/buy/${item.deviceNo}`; // 동적 링크

      // 아이템 박스 생성
      const itemBox = document.createElement("div");
      itemBox.classList.add("deviceList-item-box");

      // 링크 요소 생성
      const linkElement = document.createElement("a");
      linkElement.href = deviceLink;
      linkElement.classList.add("device-link"); // 필요하면 스타일링을 위해 클래스 추가

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

      // 링크에 섹션 추가
      linkElement.appendChild(itemSection);

      // 박스에 링크 추가
      itemBox.appendChild(linkElement);

      // 컨테이너에 박스 추가
      itemsContainer.appendChild(itemBox);
    });
  })
  .catch(err => console.error(err));
})


const dataBrand = document.querySelectorAll("[data-brand]");

let brand

dataBrand.forEach(filter => {
  // 각 요소에 클릭 이벤트 리스너 추가
  filter.addEventListener("click", () => {
    brand = filter.getAttribute("data-brand"); // 클릭된 요소의 data-brand 값 가져오기

    // 기존 선택 초기화
    dataBrand.forEach(el => el.classList.remove("selected"));

    // 콘솔 출력
    console.log(brand); 
    

    fetch(`/brandList?brand=${brand}`)
    .then(response => {
      if(response.ok) {
        return response.json();
      }
      throw new Error("데이터 요청 실패")
    })
    .then(result => {
      console.log(result);

    // 기존 내용을 초기화
    const itemsContainer = document.querySelector(".deviceList-items-container");
    itemsContainer.innerHTML = "";

    // 새로운 데이터로 업데이트
    result.forEach(item => {
      // 데이터에서 필요한 정보 추출
      const deviceName = item.deviceName || "이름 없음";
      const devicePrice = item.deviceBuyingPrice ? `${item.deviceBuyingPrice.toLocaleString()}원` : "가격 정보 없음";
      const deviceImg = item.deviceImg || "/images/default_image.png"; // 기본 이미지 처리
      const deviceLink = `/device/buy/${item.deviceNo}`; // 동적 링크

      // 아이템 박스 생성
      const itemBox = document.createElement("div");
      itemBox.classList.add("deviceList-item-box");

      // 링크 요소 생성
      const linkElement = document.createElement("a");
      linkElement.href = deviceLink;
      linkElement.classList.add("device-link"); // 필요하면 스타일링을 위해 클래스 추가

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

      // 링크에 섹션 추가
      linkElement.appendChild(itemSection);

      // 박스에 링크 추가
      itemBox.appendChild(linkElement);

      // 컨테이너에 박스 추가
      itemsContainer.appendChild(itemBox);
    });

  })
    .catch(error => {
      console.error(err);
    })
  })
})









