
const deliveryLive = document.querySelectorAll(".deliverylive");

const selectBtn = document.querySelectorAll(".select-btn");


const orderNo = document.querySelectorAll(".orderNo");

const trCon = document.querySelectorAll(".tr-container")




for (let i = 0; i < selectBtn.length; i++) {


    selectBtn[i].addEventListener("change", () => {

        const orderNober = orderNo[i].textContent;

        const orderStatusCode = selectBtn[i].value;


        console.log("orderNO",orderNober);
        console.log("orderStatusCode",orderStatusCode);


        const map = {
            "orderNo": orderNober,
            "orderStatusCode": orderStatusCode
        }
        const map1 = {
            "orderNo": orderNober,
            "memberNo": trCon[i].children[1].innerText
        }
        
        alert(map1)
        alert(orderNober)
        alert(trCon[i].children[1].innerText)
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
        
        if(orderStatusCode == 3){
            if( confirm("배송 완료로 변경했습니다, 해당 유저에게 리뷰 작성 알림을 보내시겠습니까?")){
                
                // 리뷰 알림 테이블에 없으면 insert 
                fetch("/sidebar/reviewNoti", {
                    method : "POST",
                    headers : {"Content-Type": "application/json"},
                    body : JSON.stringify(map1)
                })
                .then(Response => {
                    if(Response.ok){
                        return Response.text();
                    }
                    throw new Error("리뷰 알림 보내기 실패");
                })
                .then(result => {
                    if(result > 0){
                        console.log("리뷰 알림 등록 완료");
                    }
                    if(result == 0){
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



const searchFilter = document.querySelector(".search-fiter");
const listSearch = document.querySelector("#search");

//검색 필터
searchFilter.addEventListener("click", () => {


    searchResult = listSearch.value;

    console.log(searchResult);



    fetch("/admin/serachFilter", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: searchResult
    })
        .then((res) => {
            if (res.ok) return res.text();
            throw new Error("비동기 실패");
        })
        .then(result => {
            console.log(result); // 검색결과 담김.

            const listView = document.querySelector(".listView");

            listView.innerHTML = result;



        })

        .catch(err => { console.log(err); })
})
