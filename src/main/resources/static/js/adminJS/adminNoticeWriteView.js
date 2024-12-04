
const backBtn = document.querySelector(".back-btn");

backBtn.addEventListener("click", () => {
  location.href = "/admin/adminNotice";
})




const noticeInsertFrm = document.querySelector("#noticeInsertFrm");

noticeInsertFrm.addEventListener("submit", e => {


  const noticeTitle = document.querySelector("[name=noticeTitle]")
  const noticeContent = document.querySelector("[name=noticeContent]")

  if (noticeTitle.value.trim().length === 0) {
    alert("제목을 작성해 주세요");
    e.preventDefault();
    return;
  }

  if (noticeContent.value.trim().length === 0) {
    alert("내용을 작성해 주세요");
    e.preventDefault();
    return;
  }


})