fetch("/DeviceList", {
  method : "POST",
  headers : {"ContentType" : "application/json"},
  body : JSON.stringify(data)
})

.then(response => {
  if(response.ok) return response.json
  throw new Error("제품 등록 실패")
})