
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
  requestPay(10000)
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

  IMP.request_pay({
    channelKey: "channel-key-bf43e218-5567-4875-96da-3270e1fba054",
    pay_method: "card",
    merchant_uid: "test_m3cb5p87",
    name: "테스트 결제",
    amount: paidAmount,
    buyer_tel: "010-0000-0000",
  });
} 