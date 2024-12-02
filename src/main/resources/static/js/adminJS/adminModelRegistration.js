const inputImg = document.getElementsByClassName("input-img");
const previewList = document.getElementsByClassName("preview");
const deleteImage = document.getElementsByClassName("delete_image");

const validFile = [null, null, null, null, null, null, null]



    const updatePreview = (file, order) => {

        //선택된 이미지 백업하기
        validFile[order] = file;


        // 파일 읽어오는 객체
        const reader = new FileReader();
        
        reader.readAsDataURL(file);

        reader.addEventListener("load", e => {
            previewList[order].src = e.target.result;
            /* 파일이 형변환된 주소 형태 문자열 */
    })
    
}



for (let i = 0; i < inputImg.length; i++) {

    // input 태그 미리보기 이미지 선택시 미리보기 함수 호출
    inputImg[i].addEventListener("change", e => {

        // value에 저장하는게 아닌 file에 저장.
        const file = e.target.files[0];

                
        /* 선택된 파일이 없는 경우 */
        if(file === undefined){

            /* 이전에 선택된 파일이 없는경우 */
            if(validFile[i] === null) return;

            const dataTransfer = new DataTransfer();
            // validFile[i]  추가
            dataTransfer.items.add(validFile[i]);
            
            inputImg[i].files = dataTransfer.files;

            updatePreview(validFile[i], i);

            return;
        } 








        updatePreview(file, i);

    })


    deleteImage[i].addEventListener("click", () => {

        previewList[i].src = ""; //미리보기 삭제
        inputImg[i].value = ""; // 선택된 파일 삭제
        validFile[i] = null; //백업본 없앰

    })




}


const deviceInsertFrm = document.querySelector("#deviceInsertFrm");


deviceInsertFrm.addEventListener("submit", e => {

    const capacityPrice = document.querySelectorAll("[name=capacityPrice]");

    capacityPrice.forEach((price, index) => {
        
        if (price.value.trim().length === 0) {
            price.value = "null";
        }
        price.type = "hidden";
    })

    const capacitySellPrice = document.querySelectorAll("[name=capacitySellPrice]");

    capacitySellPrice.forEach((sellPrice, index) => {
        
        if (sellPrice.value.trim().length === 0) {
            sellPrice.value = "null"
        }
        sellPrice.type = "hidden";
    })


})





