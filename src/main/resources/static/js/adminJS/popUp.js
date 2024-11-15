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




//  팝업 비동기 조회
    const ListView = () => {
        const deviceNo = document.querySelector(".deviceNo");// 댓글 번호
        result = deviceNo.textContent;
        console.log(result);
        /* 팝업 켜짐 */
        popUpLayer.classList.remove('popUp-close');
        

        fetch("/popUpData", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: deviceNo
        })
            .then(response => {
                if (response.ok) return response.text();
                throw new Error("목록 조회 실패")
            })
            .then(result => {
                console.log(result);
                
            })
            .catch(err => console.error(err));
    }





