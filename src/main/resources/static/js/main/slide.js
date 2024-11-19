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


// 슬라이드 이동 변수
let currentIndex2 = 3; // 복사된 슬라이드 포함 시작 위치
const slideContainer2 = document.querySelector('.sub-slide');
const slideItems2 = Array.from(document.querySelectorAll('.items'));
const totalSlides2 = slideItems2.length;

// 복사 슬라이드 추가
function cloneSlides() {
  const cloneCount = 3; // 복사할 슬라이드 수
  const total = slideItems2.length;

  // 뒤쪽에 복사된 슬라이드 추가
  for (let i = 0; i < cloneCount; i++) {
    const clone = slideItems2[i].cloneNode(true);
    slideContainer2.appendChild(clone);
  }

  // 앞쪽에 복사된 슬라이드 추가
  for (let i = total - cloneCount; i < total; i++) {
    const clone = slideItems2[i].cloneNode(true);
    slideContainer2.insertBefore(clone, slideItems2[0]);
  }

  // 복사된 슬라이드를 포함한 배열 업데이트
  const updatedSlides = Array.from(document.querySelectorAll('.items'));
  slideItems2.length = 0; // 기존 배열 초기화
  updatedSlides.forEach((item) => slideItems2.push(item));
}

// 초기 위치 설정
function setInitialPosition() {
  const slideWidth = slideItems2[0].offsetWidth + 80; // 이미지 너비 + gap
  currentIndex2 = 3; // 첫 번째 슬라이드가 3번째 위치에 오도록 설정
  slideContainer2.style.transform = `translateX(-${currentIndex2 * slideWidth}px)`; // 초기 위치 설정
}

// 슬라이드 업데이트
function updateSlide2() {
  const slideWidth = slideItems2[0].offsetWidth + 80; // 이미지 너비 + gap
  const newTransform = -(currentIndex2 * slideWidth);

  slideContainer2.style.transition = 'transform 0.5s ease-in-out';
  slideContainer2.style.transform = `translateX(${newTransform}px)`;

  slideContainer2.addEventListener(
    'transitionend',
    () => {
      slideContainer2.style.transition = 'none'; // 애니메이션 제거
      if (currentIndex2 <= 2) {
        currentIndex2 = totalSlides2 + 2; // 마지막 복사 슬라이드로 이동
        slideContainer2.style.transform = `translateX(-${currentIndex2 * slideWidth}px)`;
      } else if (currentIndex2 >= totalSlides2 + 3) {
        currentIndex2 = 3; // 첫 번째 슬라이드로 이동
        slideContainer2.style.transform = `translateX(-${currentIndex2 * slideWidth}px)`;
      }
    },
    { once: true } // 이벤트가 한 번만 실행되도록 설정
  );
}

// 이전 슬라이드 이동
function prevSlide2() {
  currentIndex2--;
  updateSlide2();
}

// 다음 슬라이드 이동
function nextSlide2() {
  currentIndex2++;
  updateSlide2();
}

// 슬라이더 초기화
function initSlider() {
  cloneSlides(); // 복사 슬라이드 생성
  setInitialPosition(); // 초기 위치 설정
}

// 초기화 실행
initSlider();



