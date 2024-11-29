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



document.addEventListener("DOMContentLoaded", () => {


    const capacityPriceInput = document.querySelectorAll("[name=capacityPrice]");

    
    capacityPriceInput.forEach((price, index) => {
        
        capacityPrice.forEach(capacity => {

            if (capacity.capacityNumber === index + 1) {
                price.value = capacity.capacityPrice;

            }
        })

    })

    const capacitySellPriceInput = document.querySelectorAll("[name=capacitySellPrice]")
    capacitySellPriceInput.forEach((price, index) => {
        
        capacityPrice.forEach(capacity => {

            if (capacity.capacityNumber === index + 1) {
                price.value = capacity.capacitySellPrice;

            }
        })

    })
    
})







const deviceUpdateFrm = document.querySelector("#deviceUpdateFrm");


deviceUpdateFrm.addEventListener("submit", e => {

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




const pName = document.querySelectorAll(".p-name");
const deletePtag = document.querySelectorAll(".delete_image");



for (let i = 0; i < deletePtag.length; i++) {
    deletePtag[i].addEventListener("click", () => {

        pName[i - 1].textContent = "파일 선택해주세요";


    })
}




const imgInput1 = document.querySelector("#file1");
const imgInput2 = document.querySelector("#file2");
const imgInput3 = document.querySelector("#file3");
const imgInput4 = document.querySelector("#file4");
const imgInput5 = document.querySelector("#file5");
const imgInput6 = document.querySelector("#file6");


imgInput1.addEventListener("change", e => {

    const fileName = e.target.files[0].name;

    

    console.log(fileName);

    pName[0].innerText = fileName;
})
imgInput2.addEventListener("change", e => {

    const fileName = e.target.files[0].name;

    

    console.log(fileName);

    pName[1].innerText = fileName;
})
imgInput3.addEventListener("change", e => {

    const fileName = e.target.files[0].name;

    

    console.log(fileName);

    pName[2].innerText = fileName;
})
imgInput4.addEventListener("change", e => {

    const fileName = e.target.files[0].name;

    

    console.log(fileName);

    pName[3].innerText = fileName;
})
imgInput5.addEventListener("change", e => {

    const fileName = e.target.files[0].name;

    

    console.log(fileName);

    pName[4].innerText = fileName;
})
imgInput6.addEventListener("change", e => {

    const fileName = e.target.files[0].name;

    

    console.log(fileName);

    pName[5].innerText = fileName;
})




