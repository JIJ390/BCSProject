const filterBtn = document.querySelectorAll(".filter-btn");







for (let i = 0; i < filterBtn.length; i++) {
    filterBtn[i].addEventListener("click", () => {
        
        const brandFilter = filterBtn[i].textContent;
        

        fetch("/admin/adminBrandFilter", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: brandFilter
        })
            .then((res) => {
                if (res.ok) return res.text();
                throw new Error("비동기 실패");
            })
            .then(result => {
                console.log(result); // 검색결과 담김.
                console.log("asdsaddads",result); // 검색결과 담김.

                const deviceListColorBody = document.querySelector(".deviceListColorBody");


                console.log(result);
                deviceListColorBody.innerHTML = "";
                deviceListColorBody.innerHTML = result;
                
                

            })
            .catch(err => { console.log(err); })

    })
}





colorList = () => {

    fetch("/admin/colorList", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: brandFilter
    })
        .then((res) => {
            if (res.ok) return res.text();
            throw new Error("비동기 실패");
        })
        .then(result => {
            console.log(result); // 검색결과 담김.

        })
        .catch(err => { console.log(err); })

}



addEventListener("DOMContentLoaded", () => {

})