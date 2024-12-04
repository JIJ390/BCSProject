const backBtn = document.querySelector(".back-btn");

backBtn.addEventListener("click", () => {
  location.href = "/admin";
})






// 페이지 이동을 위한 버튼 모두 얻어오기
const pageNoList = document.querySelectorAll(".pagination a");

// 페이지 이동 버튼이 클릭 되었을 때
pageNoList?.forEach((item, index) => {

  // 클릭 되었을 때
  item.addEventListener("click", e => {
    

    e.preventDefault();

    // 만약 클릭된 a태그에 "current" 클래스가 있을 경우
    // == 현재 페이지 숫자를 클릭한 경우
    if(item.classList.contains("current")){
      return;
    } 

    // const -> let 으로 변경
    let pathname = location.pathname; // 현재 게시판 조회 요청 주소

    // 클릭된 버튼이 <<, <, >, >> 인 경우
    // console.log(item.innerText);
    switch(item.innerText){
      case '<<' :   // 1 페이지로 이동
        pathname += "?cp=1";
        break;

      case '<'  : 
        pathname += "?cp=" + noticePagination.prevPage;
        break;

      case '>'  : 
        pathname += "?cp=" + noticePagination.nextPage;
        break;

      case '>>' : 
        pathname += "?cp=" + noticePagination.maxPage;
        break;

      default: 
        pathname += "?cp=" + item.innerText; 
    }

    // 페이지 이동
    location.href = pathname;

  });

});




const noticeInsertBtn = document.querySelector("#noticeInsertBtn");

noticeInsertBtn.addEventListener("click", () => {
  location.href="/admin/adminNoticeWriteView";
})





document.addEventListener("DOMContentLoaded", () => {

  const updateBtn = document.querySelectorAll(".update-btn");
  const deleteBtn = document.querySelectorAll(".delete-btn");

  updateBtn.forEach(item => {
    const noticeNumber = item.parentElement.parentElement.querySelector("td:first-child").innerText;


    item.addEventListener("click", () => {

      const form = document.createElement("form");
      form.action = "/admin/adminNoticeUpdateView";            // 요청 주소
      form.method = "POST";                                    // 메소드 지정

      const input = document.createElement("input");
      input.type  = "hidden";
      input.name  = "noticeNumber";
      input.value = noticeNumber;


      form.append(input); 
      
      document.querySelector("body").append(form);
    
      form.submit(); 
    })
  })



  deleteBtn.forEach(item => {
    
    const noticeNumber = item.parentElement.parentElement.querySelector("td:first-child").innerText;


    item.addEventListener("click", () =>{

      if (!confirm("정말 삭제하시겠습니까?")) {
        alert("취소되었습니다");
        return;
        
      }


      const form = document.createElement("form");
      form.action = "/admin/adminNoticeDelete";            // 요청 주소
      form.method = "POST";                                    // 메소드 지정

      const input = document.createElement("input");
      input.type  = "hidden";
      input.name  = "noticeNumber";
      input.value = noticeNumber;


      form.append(input); 
      
      document.querySelector("body").append(form);
    
      form.submit(); 

    })



  })


})