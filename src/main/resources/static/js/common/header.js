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

document.querySelectorAll(".info li a").forEach((link) => {
    link.addEventListener("click", function (event) {
        event.preventDefault();

        let category = this.textContent.trim();

        // 카테고리 매핑
        const categoryMapping = {
            "iPhone": "Apple",
            "Galaxy Phone": "SAMSUNG",
            "Galaxy Tab": "Galaxy Tab",
            "iPad": "iPad",
        };

        category = categoryMapping[category] || category;

        console.log(`Fetching data for category: ${category}`);

        fetch('/phoneList', {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: category,
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Server error: ${response.statusText}`);
                }
                return response.json();
            })
            .then((data) => {
                console.log("Received data:", data);

                const selectionMessage = document.querySelector("#selectionMessage");
                if (selectionMessage) {
                    selectionMessage.style.display = "none"; // 메시지 숨기기
                }

                const container = document.querySelector(`#buy_phoneModels_galaxy`);
                const moreButton = document.querySelector(`#moreButton`);
                if (!container || !moreButton) {
                    console.error(`Container or More Button not found.`);
                    return;
                }

                container.innerHTML = ""; // 기존 콘텐츠 초기화
                container.classList.add("active"); // 리스트 활성화
                container.style.display = "grid"; // 보여주기
                container.style.marginLeft = "-100px"; // 보여주기

                

                // 데이터 추가 (최대 6개)
                data.slice(0, 6).forEach((device) => {
                    const li = document.createElement("li");
                    const a = document.createElement("a");
                    a.textContent = device.deviceName; // 링크 텍스트
                    a.href = `/device/buy/${device.deviceNo}`; // 동적 링크 (예: 디바이스 상세 페이지)
                    a.style.textDecoration = "none"; // 링크 스타일 (선택 사항)
                    a.style.color = "inherit"; // 링크 색상 유지 (선택 사항)
                    li.appendChild(a); // <li> 안에 <a> 추가
                    container.appendChild(li); // <ul>에 <li> 추가
                });

                // "더보기 +" 버튼 표시 및 링크 설정
                moreButton.style.display = "block";
                moreButton.href = `/deviceList?category=${encodeURIComponent(category)}`; // 동적 링크 설정
            })
            .catch((error) => {
                console.error("Error fetching devices:", error);
            });
    });
});


// 드롭다운 닫힐 때 초기화
document.querySelectorAll(".dropdownContainor").forEach((dropdown) => {
    dropdown.addEventListener("mouseleave", () => {
        const selectionMessage = document.querySelector("#selectionMessage");
        const container = document.querySelector(`#buy_phoneModels_galaxy`);
        const moreButton = document.querySelector(`#moreButton`);

        if (selectionMessage) {
            selectionMessage.style.display = "block"; // '원하는 종류를 선택하세요' 다시 표시
            selectionMessage.style.marginTop = "22px"; 
        }

        if (container) {
            container.style.display = "none"; // 리스트 숨기기
            container.innerHTML = ""; // 리스트 초기화 (동적 <li> 삭제)
            container.classList.remove("active"); // 활성화 해제
        }

        if (moreButton) {
            moreButton.style.display = "none"; // 더보기 버튼 숨기기
            moreButton.href = "/deviceList"; // 기본 링크로 초기화
        }
    });
});












document.querySelectorAll(".dep2 .info li a").forEach((link) => {
    link.addEventListener("click", function (event) {
        event.preventDefault();

        let category = this.textContent.trim();

        // 카테고리 매핑
        const categoryMapping = {
            "iPhone": "Apple",
            "Galaxy Phone": "SAMSUNG",
            "Galaxy Tab": "Galaxy Tab",
            "iPad": "iPad",
        };

        category = categoryMapping[category] || category;

        console.log(`Fetching data for category: ${category} (판매하기)`);

        fetch('/sellList', {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: category,
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Server error: ${response.statusText}`);
                }
                return response.json();
            })
            .then((data) => {
                console.log("Received data:", data);

                const selectionMessage = document.querySelector("#sellselectionMessage");
                if (selectionMessage) {
                    selectionMessage.style.display = "none"; // 메시지 숨기기
                }

                const container = document.querySelector(`#sell_phoneModels_galaxy`); // 수정 필요
                const moreButton = document.querySelector(`#sellmoreButton`); // 수정 필요
                if (!container || !moreButton) {
                    console.error(`Container or More Button not found.`);
                    return;
                }

                container.innerHTML = ""; // 기존 콘텐츠 초기화
                container.classList.add("active"); // 리스트 활성화
                container.style.display = "grid"; // 보여주기
                container.style.marginLeft = "-100px"; // 보여주기

                // 데이터 추가 (최대 6개)
                data.slice(0, 6).forEach((device) => {
                    const li = document.createElement("li");
                    const a = document.createElement("a");
                    a.textContent = device.deviceName; // 링크 텍스트
                    a.href = `/device/sell/${device.deviceNo}`; // 동적 링크 (예: 디바이스 상세 페이지)
                    a.style.textDecoration = "none"; // 링크 스타일 (선택 사항)
                    a.style.color = "inherit"; // 링크 색상 유지 (선택 사항)
                    li.appendChild(a); // <li> 안에 <a> 추가
                    container.appendChild(li); // <ul>에 <li> 추가
                });

                // "더보기 +" 버튼 표시 및 링크 설정
                moreButton.style.display = "block";
                moreButton.href = `/deviceSellList?category=${encodeURIComponent(category)}`; // 동적 링크 설정
            })
            .catch((error) => {
                console.error("Error fetching devices:", error);
            });
    });
});



// 드롭다운 닫힐 때 초기화
document.querySelectorAll(".dropdownContainor").forEach((dropdown) => {
    dropdown.addEventListener("mouseleave", () => {
        const selectionMessage = document.querySelector("#sellselectionMessage");
        const container = document.querySelector(`#sell_phoneModels_galaxy`);
        const moreButton = document.querySelector(`#sellmoreButton`);

        if (selectionMessage) {
            selectionMessage.style.display = "block"; // '원하는 종류를 선택하세요' 다시 표시
            selectionMessage.style.marginTop = "22px"; 
        }

        if (container) {
            container.style.display = "none"; // 리스트 숨기기
            container.innerHTML = ""; // 리스트 초기화 (동적 <li> 삭제)
            container.classList.remove("active"); // 활성화 해제
        }

        if (moreButton) {
            moreButton.style.display = "none"; // 더보기 버튼 숨기기
            moreButton.href = "/deviceList"; // 기본 링크로 초기화
        }
    });
});


