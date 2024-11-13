
const chargeBox1 = document.querySelector("#chargeBox1");
const chargeBox2 = document.querySelector("#chargeBox2");
const chargeBox3 = document.querySelector("#chargeBox3");
const chargeBox4 = document.querySelector("#chargeBox4");
const chargeBox5 = document.querySelector("#chargeBox5");
const chargeBox6 = document.querySelector("#chargeBox6");

document.addEventListener('DOMContentLoaded', () => {
  IMP.init("imp14397622");
})


chargeBox1.addEventListener('click', () => {
  requestPay(100)
}) 

chargeBox2.addEventListener('click', () => {
  requestPay(30000)
}) 

chargeBox3.addEventListener('click', () => {
  requestPay(50000)
}) 

chargeBox4.addEventListener('click', () => {
  requestPay(100000)
}) 

chargeBox5.addEventListener('click', () => {
  requestPay(200000)
}) 

chargeBox6.addEventListener('click', () => {
  requestPay(300000)
}) 



/**
 * 결제 함수
 * @param {} paidAmount 
 */
const requestPay = (paidAmount) => {

  // 기본 결제 ID는 현재 시간 + 난수 6자리를 결합하여 16자리를 만듭니다.
  const timestamp = Date.now().toString().slice(-10); // 현재 시간의 마지막 10자리 사용
  const randomNum = Math.floor(Math.random() * 1000000).toString().padStart(6, '0'); // 6자리 난수
  
  // 결제 ID를 생성
  const paymentId = timestamp + randomNum;
  
  // 4자리씩 끊어서 포맷팅: 1234-5678-9012-3456 형식
  const formattedId = paymentId.match(/.{1,4}/g).join('-');

  console.log(formattedId);
    
  IMP.request_pay({
    channelKey: "channel-key-bf43e218-5567-4875-96da-3270e1fba054",
    pay_method: "card",
    merchant_uid: formattedId,
    name: "테스트 결제",
    amount: paidAmount,
    buyer_tel: "010-0000-0000",
  }, function(rsp) {
		console.log(rsp);
		
		 //결제 성공 시
		if (rsp.success) {

      // 결제된 금액
      const amount = rsp.paid_amount;

      // 비동기로 결제 금액에 따른 포인트 충전
      fetch("/point/charge", {
        method : "POST", 
        headers: {"Content-Type": "application/json"}, 
        body : amount
      })
      .then(response => {
        if(response.ok) return response.text();
        throw new Error("충전 실패 : " + response.status);
      })
      .then(beforeChangePoints => {

        // 비동기 충전 성공 시 이동
        const url = "/point/charge/compl";    // 요청 주소
  
        const form = document.createElement("form");
        form.action = url;            // 요청 주소
        form.method = "POST";         // 메소드 지정
      
        // amount 는 위 결제 정보에서 가져옴
        const input1 = document.createElement("input");
        input1.type  = "hidden";
        input1.name  = "amount";
        input1.value = amount;

        // beforeChangePoints 는 비동기로 조회해온 정보
        const input2 = document.createElement("input");
        input2.type  = "hidden";
        input2.name  = "beforeChangePoints";
        input2.value = beforeChangePoints;

        form.append(input1, input2); 
      
        document.querySelector("body").append(form);
      
        form.submit(); 
    
      })
      .catch(err => console.error(err));






		} else {
			let message = '결제에 실패하였습니다.\n';
			message += '에러 내용 : ' + rsp.error_msg;

      alert(msg);
		}
	});
} 

