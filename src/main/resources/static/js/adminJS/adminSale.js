const changeLlist = () => {
    const deliveryLive = document.querySelectorAll(".deliverylive");

    const selectBtn = document.querySelectorAll(".select-btn");


    const orderNo = document.querySelectorAll(".orderNo");

    const trCon = document.querySelectorAll(".tr-container")




    for (let i = 0; i < selectBtn.length; i++) {


        selectBtn[i].addEventListener("change", () => {

            const orderNober = orderNo[i].textContent;

            const orderStatusCode = selectBtn[i].value;


            console.log("orderNO", orderNober);
            console.log("orderStatusCode", orderStatusCode);


            const map = {
                "orderNo": orderNober,
                "orderStatusCode": orderStatusCode
            }
            const map1 = {
                "orderNo": orderNober,
                "memberNo": trCon[i].children[1].innerText
            }

            fetch("/admin/delivery", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(map)

            })
                .then((res) => {
                    if (res.ok) return res.text();
                    throw new Error("비동기 실패");
                })
                .then(result => {
                    console.log(result);
                })
                .catch(err => { console.log(err); })

            if (orderStatusCode == 3) {
                if (confirm("배송 완료로 변경했습니다, 해당 유저에게 리뷰 작성 알림을 보내시겠습니까?")) {

                    // 리뷰 알림 테이블에 없으면 insert 
                    fetch("/sidebar/reviewNoti", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(map1)
                    })
                        .then(Response => {
                            if (Response.ok) {
                                return Response.text();
                            }
                            throw new Error("리뷰 알림 보내기 실패");
                        })
                        .then(result => {
                            if (result > 0) {
                                console.log("리뷰 알림 등록 완료");
                            }
                            if (result == 0) {
                                console.log("이미 알림이 있음");
                            }
                        })
                        .catch(err => {
                            console.log(err);
                        })
                }
            }
        })
    }
}


const searchFilter = document.querySelector(".search-fiter");

//검색 필터
searchFilter.addEventListener("click", () => {
    const listSearch = document.querySelector("#search");


    search = listSearch.value;
    cp = 1;
    adminSalePage();
    adminSalePageNation();


    // fetch("/admin/serachFilter", {
    //     method: "POST",
    //     headers: { "Content-Type": "application/json" },
    //     body: searchResult
    // })
    //     .then((res) => {
    //         if (res.ok) return res.text();
    //         throw new Error("비동기 실패");
    //     })
    //     .then(result => {
    //         console.log(result); // 검색결과 담김.

    //         const listView = document.querySelector(".listView");

    //         listView.innerHTML = result;



    //     })

    //     .catch(err => { console.log(err); })
})

let cp = 1;
let search = '';
let deviceNo = Number(location.pathname.split("/")[3]);

const adminSalePageNation = () => {

    console.log(deviceNo);
    console.log(cp);
    console.log(search);

    fetch("/admin/adminSalePageNation?cp=" + cp + "&deviceNo=" + deviceNo + "&search=" + search)
        .then(Response => {
            if (Response.ok) {
                return Response.text();
            }
            throw new Error("페이지네이션 조회 실패")
        })
        .then(result => {
            console.log(result);
            document.querySelector(".adminSalePagination").innerHTML = result;
            getEventPageNumClick()
            getEventPageMoveClick()

        })



}


const adminSalePage = () => {

    fetch("/admin/adminSalePage?cp=" + cp + "&deviceNo=" + deviceNo + "&search=" + search)
    .then(Response => {
        if (Response.ok) {
            return Response.text();
        }
        throw new Error("비동기 조회 실패")
    })
    .then(result => {
        console.log(result);
        document.querySelector(".listView").innerHTML = result;
        changeLlist();
    })
}







/* 페이지네이션 숫자 클릭 이벤트  */
const getEventPageNumClick = () => {
    const numbers = document.querySelectorAll(".pnBtn")
    for (let i = 0; i < numbers.length; i++) {
      numbers[i]?.addEventListener('click', () => {

        const cpNumber = numbers[i].children[0].innerText;
        cp = cpNumber
        adminSalePageNation();
        adminSalePage();
      })
    }

    const maxNumber = document.querySelector(".pn-5")
    maxNumber?.addEventListener('click', () => {
      cp = maxNumber.children[0].innerText;
      adminSalePageNation();
      adminSalePage();
    })

  }


  /* 페이지네이션 화살표 이벤트 */
  const getEventPageMoveClick = () => {
    const moveBtn1 = document.querySelector(".pn-1")
    const moveBtn2 = document.querySelector(".pn-2")
    const moveBtn3 = document.querySelector(".pn-3")
    const moveBtn4 = document.querySelector(".pn-4")

    document.querySelector(".pn-1").addEventListener("click", () => {
      cp = 1;
      adminSalePageNation();
      adminSalePage();
    })
    document.querySelector(".pn-2").addEventListener("click", () => {
      cp--;
      if (cp === 0) {
        cp++;
      }
      adminSalePageNation();
      adminSalePage();
    })
    document.querySelector(".pn-3").addEventListener("click", () => {
      cp++;
      if (cp > Number(document.querySelector(".getCpCount").value)) {
        cp--;
      }
      adminSalePageNation();
      adminSalePage();
    })
    document.querySelector(".pn-4").addEventListener("click", () => {
      cp = Number(document.querySelector(".getCpCount").value);
      adminSalePageNation();
      adminSalePage();
    })
  }













document.addEventListener("DOMContentLoaded", () => {
    adminSalePageNation()
    adminSalePage()
})