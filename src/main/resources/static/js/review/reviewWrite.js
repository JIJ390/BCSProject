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
      star.src = "/images/review2/empty-star-right.png";
    }
    else {
      star.src = "/images/review2/empty-star-left.png";
    }
  })

}

// 별 채우기
const highlightStars = (rating) => {
  stars.forEach((star, index) => {
      if (star.dataset.value <= rating) {
        
        // 0.5 단위
        if (index % 2 !== 0) {
          star.src = "/images/review2/filled-star-right.png";
        }
        else {
          star.src = "/images/review2/filled-star-left.png";
        }

      }
  });
}



/* 미리 보기 */
const previewImg = document.querySelector(".preview-img");

// 팝업 내부 이미지
const previewImg2 = document.querySelector(".preview-img2")

const inputImage = document.querySelector("[name=imgInput]");

// 마지막으로 선택된 파일을 저장할 배열
let lastValidFile = null;

const updatePreview = (file) => {


  // 선택된 파일이 지정된 크기를 초과한 경우 선택 막기
  const maxSize = 1024 * 1024 * 10;

  if (file.size > maxSize) {  // 파일 크기 초과 시
    alert("10 MB 이하의 이미지만 선택해 주세요");
    

    if (lastValidFile === null) {
      inputImage.value = ""; // 선택 파일 삭제
      return;
    }

    const dataTransfer = new DataTransfer();

    dataTransfer.items.add(lastValidFile);

    inputImage.files = dataTransfer.files;

    updatePreview(lastValidFile); 

    return;
  }

  lastValidFile = file;

  const reader = new FileReader();

  reader.readAsDataURL(file);

  console.log("abs");

  reader.addEventListener("load", e => {
    previewImg.src = e.target.result;
    previewImg.style.visibility="visible"

    previewImg2.src = e.target.result;
  })

}

/* input 태그 이벤트 리스너 추가 */

inputImage.addEventListener("change", e => {
  const file = e.target.files[0];

  console.log(file);

  if (file === undefined) { 

    if (lastValidFile === null) return;

    const dataTransfer = new DataTransfer();

    dataTransfer.items.add(lastValidFile);

    inputImage.files = dataTransfer.files;

    updatePreview(lastValidFile); 

    return;
  }

  console.log(file);

  updatePreview(file);
})





const previewImgBox = document.querySelector(".preview-img-box");


previewImgBox.addEventListener("click", e => {
  e.stopPropagation(); // 이벤트 전파 방지 팝업이 열리면서 꺼지는 현상 방지
  popupOpen();
})


// 팝업
const imgPopUp = document.querySelector(".preview-img-popup");

// 팝업 열기
const popupOpen = () => {
  console.log("asdas");

  imgPopUp.classList.remove("close-popup");
  document.querySelector("#blackDisplay").classList.add("overlay");

}

// 팝업 닫기
const popupClose = () => {
  imgPopUp.classList.add("close-popup");
  document.querySelector("#blackDisplay").classList.remove("overlay");
}

window.addEventListener("click", e => {

  // 팝업 레이어 가 닫혀있지 않고 팝업 레이어 바깥을 눌렀을 때만 동작!!
  if (!imgPopUp.classList.contains("close-popup")
      && (e.target !== imgPopUp)) {
    popupClose();
  }
})

// 팝업 내부 요소 클릭 시 이벤트 전파 막기
imgPopUp.addEventListener("click", e => {
  e.stopPropagation();
});



const insertBtn = document.querySelector("#insertBtn");


insertBtn.addEventListener("click", () => {


  // 별점 선택 유효성 검사
  if (!(selectedRating > 0 && selectedRating <= 5)) {
    alert("별점을 선택해 주세요");
    return;
  }

  const reviewContent = document.querySelector("[name=reviewContent]");

  // 리뷰 내용 유효성 검사
  if (reviewContent.value.trim().length === 0) {
    alert("리뷰 내용을 작성해 주세요");
    return;
  }

  // 이미지 선택 유효성 검사
 
  if(inputImage.value.trim().length === 0) {
    alert("대표 이미지를 선택해 주세요");
    e.preventDefault();
    return;
  }


  const insertFrm = document.querySelector("#insertFrm")

  const input = document.querySelector("[name=reviewScore]");
  input.value = selectedRating;

  console.log(input.value);

  insertFrm.append(input);

  fetch("/sidebar/deleteReviewRN",{
    method : "DELETE",
    headers : {"Content-Type" : "application/json"},
    body : location.pathname.split("/")[3]
  })
  .then(Response => {
    if(Response.ok){
      return Response.text();
    }
    throw new Error("리뷰알림 삭제 실패");
  })
  .then(result => {
    if(result > 0){
      getHomeContent()
    }
  })
  .catch(err => {
    console.log(err);
  })

  insertFrm.submit();
})