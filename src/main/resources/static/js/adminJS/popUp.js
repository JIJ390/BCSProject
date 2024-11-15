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



/* 팝업 열기 */
const openPopup = () => {
    document.querySelector('.android-img')
    popUpLayer.classList.remove('popUp-close');
    submit();
}


//  팝업 비동기 조회
const selectList = () => {
    fetch('/admin/api/popUpData')
    .then(res => {

        if (res.ok) return Response.json();
        throw new Error("조회 실패");  
    })
    .then(result => {
        console.log(result);
    })
    .catch(err => console.errerr(err));

}





