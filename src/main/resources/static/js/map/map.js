
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = { 
    center: new kakao.maps.LatLng(37.57031178590083, 126.983167777667), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};
// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 


// 지도를 표시하는 div 크기를 변경하는 함수입니다
function resizeMap() {
mapContainer.style.width = '1295px';
mapContainer.style.height = '795px'; 
}

function relayout() {    
// 지도를 표시하는 div 크기를 변경한 이후 지도가 정상적으로 표출되지 않을 수도 있습니다
// 크기를 변경한 이후에는 반드시  map.relayout 함수를 호출해야 합니다 
// window의 resize 이벤트에 의한 크기변경은 map.relayout 함수가 자동으로 호출됩니다
map.relayout();
}


// 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다 
var positions = [
  {
      content:  
      '    <div class="info">' + 
      '        <div class="title">' + 
      '            BCS 1호점' + 
      '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' + 
      '        </div>' + 
      '        <div class="body">' + 
      '            <div class="desc">' + 
      '                <div class="ellipsis">서울 종로구 종로 55</div>' + 
      '                <div class="jibun ellipsis">(우) 03161 (지번) 종로1가 54</div>' + 
      '                <div><a href="https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%A2%85%EA%B0%81%EC%97%AD" target="_blank" class="link">길찾기</a></div>' + 
      '            </div>' + 
      '        </div>' + 
      '    </div>',   
      latlng: new kakao.maps.LatLng(37.57031178590083, 126.983167777667)
  },
];





for (var i = 0; i < positions.length; i ++) {
  // 마커를 생성합니다
  var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng // 마커의 위치
  });

  // 마커에 표시할 인포윈도우를 생성합니다 
  var infowindow = new kakao.maps.InfoWindow({
      content: positions[i].content // 인포윈도우에 표시할 내용
      

  });

  kakao.maps.event.addListener(marker, 'click', function() {
    infowindow.setMap(map);
});

// 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
  function closeOverlay() {
    infowindow.setMap(null);     
  }

  // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
  // 이벤트 리스너로는 클로저를 만들어 등록합니다 
  // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
  kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
  kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
function makeOverListener(map, marker, infowindow) {
  return function() {
      infowindow.open(map, marker);
  };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
function makeOutListener(infowindow) {
  return function() {
      infowindow.close();
  };
}


function shop1() {
  // 이동할 위도 경도 위치를 생성합니다 
  var moveLatLon = new kakao.maps.LatLng(37.57031178590083, 126.983167777667);
  // 지도 중심을 부드럽게 이동시킵니다
  // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
  map.panTo(moveLatLon);            
}       




kakao.maps.event.addListener(marker, 'click', function() {
  overlay.setMap(map);
});




document.addEventListener("DOMContentLoaded", () => {

  resizeMap();
  relayout();
  closeOverlay();
})