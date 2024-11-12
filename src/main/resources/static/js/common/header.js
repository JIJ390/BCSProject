const dep1 = document.querySelector(".dep1")

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


document.addEventListener("DOMContentLoaded", () => {
  
  // 상단으로 올라가기
  const topBtn = document.querySelector("#topBtn");
  
  topBtn.addEventListener("click", () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth' // 부드러운 스크롤 효과
    });
  });

})