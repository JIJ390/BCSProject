const pageNoList = document.querySelectorAll(".pagination a");

pageNoList?.forEach((item, index) => {
  item.addEventListener("click", e => {
    e.preventDefault();

    // 현재 페이지 숫자 클릭 시
    if(item.classList.contains("current")){
      return;
    }

    const pathname = location.pathname;

    let prevPage = Number(pagination.currentPage) - 1
    let nextPage = Number(pagination.currentPage) + 1

    if (pagination.currentPage == 1) {
      prevPage = 1;
    }

    if (pagination.currentPage == pagination.maxPage) {
      nextPage = pagination.maxPage;
    }

    // 클릭 버튼 << , < , > , >> 일 때 
    switch(item.innerText){
      case '<<' : location.href = pathname + "?cp=1"; break;
      case '<'  : location.href = pathname + "?cp=" + prevPage; break;
      case '>'  : location.href = pathname + "?cp=" + nextPage; break;
      case '>>' : location.href = pathname + "?cp=" + pagination.maxPage; break;
      default : location.href = pathname + "?cp=" + item.innerText; // 클릭 페이지 이동
    }

  })
});

// ----------------------------------------

// faq 토글 
const faqDivs = document.querySelectorAll(".faq")
const faq = document.querySelectorAll(".faq-open")

faqDivs.forEach((faqDiv, index) => {
  faqDiv.addEventListener("click", e => {
    if (faq[index].style.display === "none"){
      faq[index].style.display = "block";
      faqDiv.querySelector('.arrowBtn').style.transform = "rotate( 180deg )";
    } else {
      faq[index].style.display = "none";
      faqDiv.querySelector('.arrowBtn').style.transform = "rotate( 0deg )";
    }
  })
})


