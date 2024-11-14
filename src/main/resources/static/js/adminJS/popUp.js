/* 상위 요소 팝업 레이어 */
const popUpLayer = document.querySelector('.popUp-layer');
/* 팝업 내부에 존재하는 닫는 버튼 */
const closeBtn = document.querySelector('.close-btn');

/* 창닫기 */
closeBtn.addEventListener('click', () => {
    if (!popUpLayer.classList.contains('popUp-close')) {
        popUpLayer.classList.add('popUp-close');
    }
});





