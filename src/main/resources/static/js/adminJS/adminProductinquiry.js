const filterBtn = document.querySelectorAll(".filter-btn");







for (let i = 0; i < filterBtn.length; i++) {
    filterBtn[i].addEventListener("click", () => {
        
        search = filterBtn[i].textContent;
        cp = 1;
        adminProductPageNation();
        adminProductPage();

        // fetch("/admin/adminBrandFilter", {
        //     method: "POST",
        //     headers: { "Content-Type": "application/json" },
        //     body: brandFilter
        // })
        //     .then((res) => {
        //         if (res.ok) return res.text();
        //         throw new Error("비동기 실패");
        //     })
        //     .then(result => {
        //         console.log(result); // 검색결과 담김.
        //         console.log("asdsaddads",result); // 검색결과 담김.

        //         const deviceListColorBody = document.querySelector(".deviceListColorBody");


        //         console.log(result);
        //         deviceListColorBody.innerHTML = "";
        //         deviceListColorBody.innerHTML = result;
                
                

        //     })
        //     .catch(err => { console.log(err); })

    })
}

/* ============================================================== */


let cp = 1;
let search = '';

const adminProductPageNation = () => {

    console.log(cp);
    console.log(search);

    fetch("/admin/adminProductPageNation?cp="+cp+"&search="+search)
        .then(Response => {
            if (Response.ok) {
                return Response.text();
            }
            throw new Error("페이지네이션 조회 실패")
        })
        .then(result => {
            console.log(result);
            document.querySelector(".adminSalePagination").innerHTML = result;
            getEventPageMoveClick()
            getEventPageNumClick()
        })
}


const adminProductPage = () => {

    fetch("/admin/adminProductPage?cp="+cp+"&search="+search)
    .then(Response => {
        if (Response.ok) {
            return Response.text();
        }
        throw new Error("비동기 조회 실패")
    })
    .then(result => {
        console.log(result);
        document.querySelector(".deviceListColorBody").innerHTML = result;
    })
}


const getEventPageNumClick = () => {
    const numbers = document.querySelectorAll(".pnBtn")
    for (let i = 0; i < numbers.length; i++) {
      numbers[i]?.addEventListener('click', () => {

        const cpNumber = numbers[i].children[0].innerText;
        cp = cpNumber
        adminProductPageNation();
        adminProductPage();
      })
    }

    const maxNumber = document.querySelector(".pn-5")
    maxNumber?.addEventListener('click', () => {
      cp = maxNumber.children[0].innerText;
      adminProductPageNation();
      adminProductPage();
    })

  }


const getEventPageMoveClick = () => {
    const moveBtn1 = document.querySelector(".pn-1")
    const moveBtn2 = document.querySelector(".pn-2")
    const moveBtn3 = document.querySelector(".pn-3")
    const moveBtn4 = document.querySelector(".pn-4")

    document.querySelector(".pn-1").addEventListener("click", () => {
      cp = 1;
      adminProductPageNation();
      adminProductPage();
    })
    document.querySelector(".pn-2").addEventListener("click", () => {
      cp--;
      if (cp === 0) {
        cp++;
      }
      adminProductPageNation();
      adminProductPage();
    })
    document.querySelector(".pn-3").addEventListener("click", () => {
      cp++;
      if (cp > Number(document.querySelector(".getCpCount").value)) {
        cp--;
      }
      adminProductPageNation();
      adminProductPage();
    })
    document.querySelector(".pn-4").addEventListener("click", () => {
      cp = Number(document.querySelector(".getCpCount").value);
      adminProductPageNation();
      adminProductPage();
    })
  }
  
  
  const productinquiryBtn = document.querySelector(".productinquirySearchBtn");
  productinquiryBtn.addEventListener("click", () => {
    
      const productinquirySearch = document.querySelector("[name=search]");

    search = productinquirySearch.value;
    cp = 1;


    adminProductPageNation()
    adminProductPage()
  })

  const productinquirySearch = document.querySelector("[name=search]");
  productinquirySearch.addEventListener("keyup", (e) => {


 
    
    

    if (e.key === "Enter") {
      const productinquirySearch = document.querySelector("[name=search]");

      search = productinquirySearch.value;
      cp = 1;
    
    
      adminProductPageNation()
      adminProductPage()
    }
})




addEventListener("DOMContentLoaded", () => {
    adminProductPageNation()
    adminProductPage()
})