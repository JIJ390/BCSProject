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


// 슬라이드 이동 함수
let currentIndex2 = 1; // 슬라이드는 복사된 슬라이드(1번)부터 시작
const slideContainer2 = document.querySelector('.sub-slide');
const slideItems2 = Array.from(document.querySelectorAll('.items'));
const totalSlides2 = slideItems2.length;

function cloneSlides() {
  const firstClone = slideItems2[0].cloneNode(true);
  const lastClone = slideItems2[totalSlides2 - 1].cloneNode(true);

  slideContainer2.appendChild(firstClone); // 마지막에 첫 번째 슬라이드 추가
  slideContainer2.insertBefore(lastClone, slideItems2[0]); // 처음에 마지막 슬라이드 추가
}

function setInitialPosition() {
  const slideWidth = slideItems2[0].offsetWidth + 80; // 이미지 너비 + gap
  slideContainer2.style.transform = `translateX(-${currentIndex2 * slideWidth}px)`; // 첫 번째 슬라이드로 이동
}

// 슬라이드 이동 함수
function updateSlide2() {
  const slideWidth = slideItems2[0].offsetWidth + 80; // 이미지 너비 + gap
  const newTransform = -(currentIndex2 * slideWidth);

  slideContainer2.style.transition = 'transform 0.5s ease-in-out';
  slideContainer2.style.transform = `translateX(${newTransform}px)`;

  // 슬라이드 위치 초기화 (복사된 슬라이드 처리)
  slideContainer2.addEventListener('transitionend', () => {
    slideContainer2.style.transition = 'none'; // 애니메이션 제거
    if (currentIndex2 === 0) {
      currentIndex2 = totalSlides2; // 마지막 슬라이드로 이동
      slideContainer2.style.transform = `translateX(-${currentIndex2 * slideWidth}px)`;
    } else if (currentIndex2 === totalSlides2 + 1) {
      currentIndex2 = 1; // 첫 번째 슬라이드로 이동
      slideContainer2.style.transform = `translateX(-${currentIndex2 * slideWidth}px)`;
    }
  });
}

// 이전 슬라이드로 이동
function prevSlide2() {
  currentIndex2--;
  updateSlide2();
}

// 다음 슬라이드로 이동
function nextSlide2() {
  currentIndex2++;
  updateSlide2();
}

// 초기화 함수
function initSlider() {
  cloneSlides(); // 복사 슬라이드 생성
  setInitialPosition(); // 초기 위치 설정
}

// 슬라이더 초기화
initSlider();


