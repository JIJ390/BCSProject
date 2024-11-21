const stars = document.querySelectorAll(".star");
let selectedRating = 0;

document.addEventListener("DOMContentLoaded", () => {
  
  stars.forEach((star, index) => {

      // 마우스 위에 올렸을 때
      star.addEventListener("mouseover", () => {
          resetStars();
          highlightStars(star.dataset.value);
      });

      star.addEventListener("click", () => {
        selectedRating = star.dataset.value;
      });

      star.addEventListener("mouseout", () => {
          // 선택된 별점 없으면 별 리셋
          resetStars();
          // 선택되어있는 별점이 존재할 때 유지
          if (selectedRating > 0) highlightStars(selectedRating);
      });
  });

});

// 별 비우기
const resetStars = () => {
  stars.forEach((star, index) => {

            // 0.5 단위
    if (index % 2 !== 0) {
      star.src = "/images/review/empty-star-right.png";
    }
    else {
      star.src = "/images/review/empty-star-left.png";
    }
  })

}

// 별 채우기
const highlightStars = (rating) => {
  stars.forEach((star, index) => {
      if (star.dataset.value <= rating) {
        
        // 0.5 단위
        if (index % 2 !== 0) {
          star.src = "/images/review/filled-star-right.png";
        }
        else {
          star.src = "/images/review/filled-star-left.png";
        }

      }
  });
}