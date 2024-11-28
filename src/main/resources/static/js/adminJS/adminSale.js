
const deliveryLive = document.querySelectorAll(".deliverylive");

const selectBtn = document.querySelectorAll(".select-btn");


const orderNo = document.querySelectorAll(".orderNo");






for (let i = 0; i < selectBtn.length; i++) {


    selectBtn[i].addEventListener("click", () => {

        const orderNober = orderNo[i].textContent;

        const orderStatusCode = selectBtn[i].value;


        // console.log("orderNO",orderNober);
        // console.log("orderStatusCode",orderStatusCode);


        const map = {
            "orderNo": orderNober,
            "orderStatusCode": orderStatusCode
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
