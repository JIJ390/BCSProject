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


// 하단 슬라이드 기능
let currentIndex2 = 0;

function showSlide2(index) {
  const slides2 = document.querySelector('.slides2');
  const totalSlides2 = 2; // 슬라이드는 2페이지만 존재

  if (index >= totalSlides2) {
    currentIndex2 = 0;
  } else if (index < 0) {
    currentIndex2 = totalSlides2 - 1;
  } else {
    currentIndex2 = index;
  }

  slides2.style.transform = `translateX(-${currentIndex2 * 100}%)`;
}

function nextSlide2() {
  showSlide2(currentIndex2 + 1);
}

function prevSlide2() {
  showSlide2(currentIndex2 - 1);
}

// 페이지 로드 시 첫 슬라이드를 표시
showSlide2(currentIndex2);