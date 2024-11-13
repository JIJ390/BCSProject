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

document.addEventListener("DOMContentLoaded", function () {
  const galaxyPhoneLink = document.querySelector(".info li a[href='#']");
  const galaxyContent = document.getElementById("galaxy-content");
  const dropdown = document.querySelector('.dropdown');

  // 처음에 내용을 숨깁니다.
  galaxyContent.style.display = 'none';

  // "Galaxy Phone" 클릭 시 토글
  galaxyPhoneLink.addEventListener("click", function (event) {
      event.preventDefault(); // 기본 링크 동작을 막음

      // 내용을 토글(보이기/숨기기)합니다.
      if (galaxyContent.style.display === 'none') {
          galaxyContent.style.display = 'block';
      } else {
          galaxyContent.style.display = 'none';
      }
  });

  // 드롭다운 메뉴에서 마우스가 나갔을 때 숨기기
  dropdown.addEventListener("mouseleave", function () {
      galaxyContent.style.display = 'none';
  });
});
