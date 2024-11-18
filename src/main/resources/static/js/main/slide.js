let currentIndex = 0;

// 상단 슬라이드 기능
function showSlide(index) {
  const slides = document.querySelector('.slides');
  const dots = document.querySelectorAll('.dot');
  const totalSlides = document.querySelectorAll('.slide').length;

  if (index >= totalSlides) {
    currentIndex = 0;
  } else if (index < 0) {
    currentIndex = totalSlides - 1;
  } else {
    currentIndex = index;
  }

  slides.style.transform = `translateX(-${currentIndex * 100}%)`;

  // 모든 dot을 비활성화하고 현재 슬라이드에 해당하는 dot만 활성화
  dots.forEach(dot => dot.classList.remove('active'));
  dots[currentIndex].classList.add('active');
}

function nextSlide() {
  showSlide(currentIndex + 1);
}

function prevSlide() {
  showSlide(currentIndex - 1);
}

function currentSlide(index) {
  showSlide(index);
}

// 일정 시간마다 자동으로 다음 슬라이드로 넘어가도록 설정
setInterval(nextSlide, 5000);

// 페이지 로드 시 첫 슬라이드를 표시
showSlide(currentIndex);



/* 서브 슬라이드 부분 */

let currentIndex2 = 0; // 현재 슬라이드 인덱스
const slideContainer2 = document.querySelector('.sub-slide');
const slideItems2 = document.querySelectorAll('.items');
const totalSlides2 = slideItems2.length;

// 슬라이드 이동 함수
function updateSlide2() {
  const slideWidth = slideItems2[0].offsetWidth + 80; // 이미지 너비 + gap
  const maxOffset = (totalSlides2 * slideWidth) - slideContainer2.parentElement.offsetWidth; // 전체 슬라이드 - 화면 너비
  const newTransform = -(currentIndex2 * slideWidth);

  // 공백 방지
  slideContainer2.style.transform = `translateX(${Math.max(Math.min(newTransform, 0), -maxOffset)}px)`;
}

// 이전 슬라이드로 이동
function prevSlide2() {
  if (currentIndex2 > 0) {
    currentIndex2--;
  } else {
    currentIndex2 = totalSlides2 - 1; // 처음으로 돌아감
  }
  updateSlide2();
}

// 다음 슬라이드로 이동
function nextSlide2() {
  if (currentIndex2 < totalSlides2 - 1) {
    currentIndex2++;
  } else {
    currentIndex2 = 0; // 처음으로 돌아감
  }
  updateSlide2();
}


