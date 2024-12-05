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
        if (file === undefined) {

            /* 이전에 선택된 파일이 없는경우 */
            if (validFile[i] === null) return;

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




// form 태그
const deviceInsertFrm = document.querySelector("#deviceInsertFrm");


// 컬러 체크
const check = () => {

    
    // deviceReleaseDate: 출시일 (Release Date)
    deviceReleaseDate = document.querySelector("#deviceReleaseDate");

    // deviceReleasePrice: 출시가격 (Release Price)
    deviceReleasePrice = document.querySelector("#deviceReleasePrice");

    // deviceOs: 운영체제 (Operating System)
    deviceOs = document.querySelector("#deviceOs");

    // deviceResolution: 해상도 (Resolution)
    deviceResolution = document.querySelector("#deviceResolution");

    // devicePixel: 픽셀 밀도 (Pixel Density)
    devicePixel = document.querySelector("#devicePixel");

    // deviceFrontPixel: 전면 카메라 픽셀 밀도 (Front Camera Pixel Density)
    deviceFrontPixel = document.querySelector("#deviceFrontPixel");

    // deviceWeight: 무게 (Weight)
    deviceWeight = document.querySelector("#deviceWeight");

    // deviceBatteryCapacity: 배터리 용량 (Battery Capacity)
    deviceBatteryCapacity = document.querySelector("#deviceBatteryCapacity");

    // deviceName: 기기명 (English) (Device Name)
    deviceName = document.querySelector("#deviceName");

    // deviceNameKor: 기기명 (한국어) (Device Name Korean)
    deviceNameKor = document.querySelector("#deviceNameKor");

    // deviceBuyingPrice: 구매가격 (Buying Price)
    deviceBuyingPrice = document.querySelector("#deviceBuyingPrice");

    // deviceSellingPrice: 판매가격 (Selling Price)
    deviceSellingPrice = document.querySelector("#deviceSellingPrice");

    // deviceCpuName: CPU명 (CPU Name)
    deviceCpuName = document.querySelector("#deviceCpuName");

        //등록할 가격
        const bGradeType = document.querySelector("#BGradeTypeRegis");
        const aGradeType = document.querySelector("#AGradeTypeRegis");
        const sGradeType = document.querySelector("#SGradeTypeRegis");
    
    
        //판매할 가격
        const bGradeTypeSell = document.querySelector("#BGradeTypeSell");
        const aGradeTypeSell = document.querySelector("#AGradeTypeSell");
        const sGradeTypeSell = document.querySelector("#SGradeTypeSell");


    const color1 = document.getElementById("color1");
    const color2 = document.getElementById("color2");
    const color3 = document.getElementById("color3");
    const color4 = document.getElementById("color4");
    const color5 = document.getElementById("color5");
    const color6 = document.getElementById("color6");


    const colorText1 = document.getElementById("colorText1");
    const colorCode1 = document.getElementById("colorCode1");
    if (color1.value == "") {
        alert("단일 색 사진 최소 한개");
        event.preventDefault();
        color1.focus();
        return;
    }
    if (!color1.value == "" && colorText1.value == "" && colorCode1.value == "") {
        alert("1번째 이미지는 존재하나 색상코드 or 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color1.focus();
        return;
    }

    if (!color1.value == "" && !colorText1.value == "" && colorCode1.value == "") {
        alert("1번째 색상코드 작성하지않았습니다.")
        event.preventDefault();
        color1.focus();
        return;
    }

    if (!color1.value == "" && colorText1.value == "" && !colorCode1.value == "") {
        alert("1번째 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color1.focus();
        return;
    }
    const colorText2 = document.getElementById("colorText2");
    const colorCode2 = document.getElementById("colorCode2");

    if (!color2.value == "" && colorText2.value == "" && colorCode2.value == "") {
        alert("2번째 이미지는 존재하나 색상코드 or 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color2.focus();
        return;
    }

    if (!color2.value == "" && !colorText2.value == "" && colorCode2.value == "") {
        alert("2번째 색상코드 작성하지않았습니다.")
        event.preventDefault();
        color2.focus();
        return;
    }

    if (!color2.value == "" && colorText2.value == "" && !colorCode2.value == "") {
        alert("2번째 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color2.focus();
        return;
    }

    const colorText3 = document.getElementById("colorText3");
    const colorCode3 = document.getElementById("colorCode3");

    if (!color3.value == "" && colorText3.value == "" && colorCode3.value == "") {
        alert("3번째 이미지는 존재하나 색상코드 or 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color3.focus();
        return;
    }

    if (!color3.value == "" && !colorText3.value == "" && colorCode3.value == "") {
        alert("3번째 색상코드 작성하지않았습니다.")
        event.preventDefault();
        color3.focus();
        return;
    }

    if (!color3.value == "" && colorText3.value == "" && !colorCode3.value == "") {
        alert("3번째 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color3.focus();
        return;
    }

    const colorText4 = document.getElementById("colorText4");
    const colorCode4 = document.getElementById("colorCode4");

    if (!color4.value == "" && colorText4.value == "" && colorCode4.value == "") {
        alert("4번째 이미지는 존재하나 색상코드 or 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color4.focus();
        return;
    }

    if (!color4.value == "" && !colorText4.value == "" && colorCode4.value == "") {
        alert("4번째 색상코드 작성하지않았습니다.")
        event.preventDefault();
        color4.focus();
        return;
    }

    if (!color4.value == "" && colorText4.value == "" && !colorCode4.value == "") {
        alert("4번째 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color4.focus();
        return;
    }

    const colorText5 = document.getElementById("colorText5");
    const colorCode5 = document.getElementById("colorCode5");

    if (!color5.value == "" && colorText5.value == "" && colorCode5.value == "") {
        alert("5번째 이미지는 존재하나 색상코드 or 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color5.focus();
        return;
    }

    if (!color5.value == "" && !colorText5.value == "" && colorCode5.value == "") {
        alert("5번째 색상코드 작성하지않았습니다.")
        event.preventDefault();
        color5.focus();
        return;
    }

    if (!color5.value == "" && colorText5.value == "" && !colorCode5.value == "") {
        alert("5번째 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color5.focus();
        return;
    }

    const colorText6 = document.getElementById("colorText6");
    const colorCode6 = document.getElementById("colorCode6");

    if (!color6.value == "" && colorText6.value == "" && colorCode6.value == "") {
        alert("6번째 이미지는 존재하나 색상코드 or 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color6.focus();
        return;
    }

    if (!color6.value == "" && !colorText6.value == "" && colorCode6.value == "") {
        alert("6번째 색상코드 작성하지않았습니다.")
        event.preventDefault();
        color6.focus();
        return;
    }

    if (!color6.value == "" && colorText6.value == "" && !colorCode6.value == "") {
        alert("6번째 색상이름 작성하지않았습니다.")
        event.preventDefault();
        color6.focus();
        return;
    }
    if (deviceReleaseDate.value === "") {
        alert("출시일을 작성해 주세요");
        event.preventDefault();
        deviceReleaseDate.focus();
        return;
    }else if(deviceReleaseDate.value.length > 8){
        alert("'출시일'값의 수가 충분하지 않습니다.")
        event.preventDefault();
        deviceReleaseDate.focus();
        return;
    }

    if (deviceReleasePrice.value === "") {
        alert("출시가격을 작성해 주세요");
        event.preventDefault();
        deviceReleasePrice.focus();
        return;
    }

    if (deviceOs.value === "") {
        alert("운영체제를 작성해 주세요");
        event.preventDefault();
        deviceOs.focus();
        return;
    }

    if (deviceResolution.value === "") {
        alert("해상도를 작성해 주세요");
        event.preventDefault();
        deviceResolution.focus();
        return;
    }

    if (devicePixel.value === "") {
        alert("픽셀 밀도를 작성해 주세요");
        event.preventDefault();
        devicePixel.focus();
        return;
    }

    if (deviceFrontPixel.value === "") {
        alert("전면 카메라 픽셀 밀도를 작성해 주세요");
        event.preventDefault();
        deviceFrontPixel.focus();
        return;
    }

    if (deviceWeight.value === "") {
        alert("무게를 작성해 주세요");
        event.preventDefault();
        deviceWeight.focus();
        return;
    }

    if (deviceBatteryCapacity.value === "") {
        alert("배터리 용량을 작성해 주세요");
        event.preventDefault();
        deviceBatteryCapacity.focus();
        return;
    }

    if (deviceName.value === "") {
        alert("기기명을 작성해 주세요");
        event.preventDefault();
        deviceName.focus();
        return;
    }

    if (deviceNameKor.value === "") {
        alert("기기명(한국어)을 작성해 주세요");
        event.preventDefault();
        deviceNameKor.focus();
        return;
    }

    if (deviceBuyingPrice.value === "") {
        alert("구매가격을 작성해 주세요");
        event.preventDefault();
        deviceBuyingPrice.focus();
        return;
    }

    if (deviceSellingPrice.value === "") {
        alert("판매가격을 작성해 주세요");
        event.preventDefault();
        deviceSellingPrice.focus();
        return;
    }

    if (deviceCpuName.value === "") {
        alert("CPU명을 작성해 주세요");
        event.preventDefault();
        deviceCpuName.focus();
        return;
    }

    
    if (bGradeType.value == "" && bGradeTypeSell.value == "") {
        alert("B등록가격와 B판매가격을 작성해주세요.");
        event.preventDefault();
        bGradeType.focus();
        return;
    }

    if (bGradeType.value == "") {
        alert("B등록가격을 작성해 주세요");
        event.preventDefault();
        bGradeType.focus();
        return;

    }
    if (bGradeTypeSell.value == "") {
        alert("B판매가격을 작성해 주세요");
        event.preventDefault();
        bGradeTypeSell.focus();
        return;
    }


    if (aGradeType.value == "" && aGradeTypeSell.value == "") {
        alert("A등록가격와 A판매가격을 작성해 주세요.");
        event.preventDefault();
        aGradeType.focus();
        return;
    }

    if (aGradeType.value == "") {
        alert("A등록가격을 작성해 주세요");
        event.preventDefault();
        aGradeType.focus();
        return;

    }
    if (aGradeTypeSell.value == "") {
        alert("A판매가격을 작성해 주세요");
        event.preventDefault();
        aGradeTypeSell.focus();
        return;
    }

    if (sGradeType.value == "" && sGradeTypeSell.value == "") {
        alert("S등록가격와 S판매가격을 작성해 주세요.");
        event.preventDefault();
        sGradeType.focus();
        return;
    }

    if (sGradeType.value == "") {
        alert("S등록가격을 작성해 주세요");
        event.preventDefault();
        sGradeType.focus();
        return;

    }
    if (sGradeTypeSell.value == "") {
        alert("S판매가격을 작성해 주세요");
        event.preventDefault();
        sGradeTypeSell.focus();
        return;
    }




    
}
/* form 태그======================= */

deviceInsertFrm.addEventListener("submit", (event, e) => {
    // event.preventDefault();

    const capacityPrice = document.querySelectorAll("[name=capacityPrice]");

    /* 검사 */
    const fileInput = document.getElementById('main-device');
    console.log(fileInput.value);

    if (fileInput.value == "") {
        fileInput.focus();
        alert("메인사진이 선택되지않았습니다");
        event.preventDefault();
        return;
    }


    //insert 검사하기
    check()






    capacityPrice.forEach((price, index) => {

        if (price.value.trim().length === 0) {
            price.value = "null";
        }
        price.type = "hidden";
        price.type = "text";
    })

    const capacitySellPrice = document.querySelectorAll("[name=capacitySellPrice]");

    capacitySellPrice.forEach((sellPrice, index) => {



        if (sellPrice.value.trim().length === 0) {
            sellPrice.value = "null"
        }
        sellPrice.type = "hidden";
        sellPrice.type = "text";
    })


})

/* ================================================================ */
//insert 버튼
const insertBtn = document.querySelector(".insert-btn");
//메인 사진
const mainDevice = document.querySelector("#main-device");















