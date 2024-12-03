/* 팝업레이어창 */

document.getElementById('open-popup').addEventListener('click', function() {
  document.getElementById('popup-layer').classList.remove('hidden');
});

document.getElementById('close-popup').addEventListener('click', function() {
  document.getElementById('popup-layer').classList.add('hidden');
});

/* 취소버튼 클릭 시 admin 페이지로 */
const registrationDelete = document.querySelector(".registrationDelete");

registrationDelete.addEventListener("click", ()=>{

  location.href = "/admin";

})

/* 버튼 */
const buttons = document.querySelectorAll(".buttonChoice");

buttons.forEach((button) => {
    button.addEventListener("click", () => {
        // 모든 버튼에서 active 클래스 제거
        buttons.forEach((btn) => btn.classList.remove("active"));
        // 클릭된 버튼에만 active 클래스 추가
        button.classList.add("active");
    });
});

const buttons1 = document.querySelectorAll(".buttonChoice1");

buttons1.forEach((button1) => {
    button1.addEventListener("click", () => {
        // 모든 버튼에서 active 클래스 제거
        buttons1.forEach((btn1) => btn1.classList.remove("active"));
        // 클릭된 버튼에만 active 클래스 추가
        button1.classList.add("active");
    });
});

/* 버튼 눌렀을 제조사, 모델 모두 하나씩만 클릭될 수 있도록 */

/* 매물등록 제조사별 클릭 시 모델이 나올 수 있게 */

const manufacturer = document.querySelector("#manufacturer-list");
const modelList = document.querySelector("#model-list");

manufacturer.addEventListener("click", ()=>{

  const brandName = manufacturer.querySelector(".active").innerText;

  console.log(brandName);

  modelList.innerHTML = "";

  

  fetch(`/admin/modelSelect/${brandName}`)
  .then(response => {
    if(response.ok) {
      return response.text();
    }
    throw new Error("브랜드명 찾기실패");
  })
  .then(html => {

    document.querySelector("#model-list").innerHTML = html;



  })
  
})

let chooseDeviceNo;


const deviceSelectBtn = (e) => {


  const allBtn = e.parentElement.querySelectorAll(".buttonChoice1");

  allBtn.forEach((item) => {
    item.classList.remove("active");
  })

  e.classList.add("active");

  console.log("adsdassa");

  chooseDeviceNo = e.getAttribute("data-value");

}



const closeBtn = document.querySelector("#close-popup");

closeBtn.addEventListener("click", () => {

  const deviceNo = chooseDeviceNo;

  if (deviceNo === undefined) {
    return;
  }

  fetch(`admin/selectDeviceInfo/${deviceNo}`)
  .then(resp => {
    if (resp.ok) {
      return resp.json();
    }
  })
  .then(result => {

    const registrationNumber = document.querySelector("#registrationNumber");

    registrationNumber.value = deviceNo;


    const gradeSelect = document.querySelector("#gradeSelect");

    const colorSelect = document.querySelector("#colorSelect");

    const capacitySelect = document.querySelector("#capacitySelect");


    gradeSelect.innerText = "";
    colorSelect.innerText = "";
    capacitySelect.innerText = "";


    
        
    result.colorList.forEach((item, index) => {
      const option = document.createElement("option");
      option.innerText = item.colorName;
      option.value = item.colorNo;


      colorSelect.append(option);
    })


    
    result.gradeList.forEach((item, index) => {
      const option = document.createElement("option");
      option.innerText = item.gradeType;
      option.value = item.gradeNumber;


      gradeSelect.append(option);
    })

        
    result.capacityList.forEach((item, index) => {
      const option = document.createElement("option");
      option.innerText = item.capacityType;
      option.value = item.capacityNumber;


      capacitySelect.append(option);
    })


  })
  


});



const popupCloseBtn = document.querySelector(".popup-closeBtn");


popupCloseBtn.addEventListener("click", () => {
    document.getElementById('popup-layer').classList.add('hidden');


})



