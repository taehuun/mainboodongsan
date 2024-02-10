<script setup>
import { ref, watch, onMounted } from "vue";
import yellowMarker from "../../assets/img/yellowMarker.png"

var map;
/* global kakao */
var infoWindow = null;
var overlay = null;
var geocoder = null;
const positions = ref([]);
const markers = ref([]);

var selectedMarkerIndex = ref(null);
var selectedApartmentName = ref(null);
var selectedContent = ref("");

const props = defineProps({houses : Object, favorList : Array, selectedIndex : Number, showCondition : String});
const emit = defineEmits(["clickMarker", "bookmarkInit"]);

let exSelectedIndex = "";

const clickMarker = (index) => {
  console.log("clickMarker 실행");
  emit("clickMarker", index);
}


onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    //autoload=false 필수.
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
  
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  infoWindow = new kakao.maps.InfoWindow();
  overlay = new kakao.maps.CustomOverlay();
  geocoder = new kakao.maps.services.Geocoder();

  if(props.showCondition=='bookmark'){
    emit("bookmarkInit");
  }

  // loadMarkers();
};


watch(
  () => props.houses,
  () => {
    positions.value = [];
    if (props.houses){
      console.log("VKakaoMap >> watch props");

      let index = 0
      for(var key in props.houses) {
        let obj = {};
        obj.latlng = new kakao.maps.LatLng(props.houses[key].list[0].lat, props.houses[key].list[0].lng);
        obj.title = key;
        obj.isBookMark = props.houses[key].list[0].email ? true : false;
        obj.index = index;
        index += 1;
        positions.value.push(obj);
      }

      if(props.selectedIndex == null){
        overlay.setMap(null);
      }
      
      loadMarkers();
    }else{
      deleteMarkers();
    }
    
  },
  { deep: true }
);

watch(
  () => props.selectedIndex,
  () => {
    if(props.selectedIndex == null){
      return;
    }
    let newLatLng = positions.value[props.selectedIndex].latlng;
    selectedApartmentName.value = positions.value[props.selectedIndex].title;

    map.panTo(newLatLng);

    overlay.setMap(null);
    overlay.setPosition(newLatLng);
    selectedContent.value = 
            '<div class="wrap">' + 
            '    <div class="info">' + 
            '        <div class="body">' + 
            '            <div class="desc">' + 
                            `<div class="ellipsis">${selectedApartmentName.value}</div>` +
            '            </div>' + 
            '        </div>' + 
            '    </div>' +    
            '</div>';
    overlay.setContent(selectedContent.value);
    overlay.setMap(map);
  },
)

watch(
  () => props.favorList,
  () => {
    if(props.selectedIndex == null){
      return;
    }
    console.log("watch favorList");
    positions.value[props.selectedIndex].isBookMark = props.favorList[props.selectedIndex] ? true : false;
    loadMarkers();
  },
  { deep: true}
);

function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}


var iwContent = '<div style="">Hello World!</div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    let markerImage = ""
    let imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/2012/img/marker_p.png'; //기본 마커
    let markImageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png'; //노란별 마커
    if(position.isBookMark){
      const imageSize = new kakao.maps.Size(24, 35)
      markerImage = new kakao.maps.MarkerImage(markImageSrc, imageSize)
    }else{
      const imageSize = new kakao.maps.Size(30, 30)
      markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize)
    }
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
      image: markerImage
    });

    /* global kakao */
    
    kakao.maps.event.addListener(marker, 'click', () => {

      let roadAddr;
      let address;
      searchAddrFromCoords(marker.getPosition(), (result, status) => {
        if(status === kakao.maps.services.Status.OK) {
          console.log("searchAddrFromCoords : status==OK")

          roadAddr = result[0].address_name;
          address = !!result[0].address ? result[0].address.address_name : '';
        }
      })
      overlay.setMap(null);
      selectedApartmentName.value = position.title;
      selectedContent.value = 
            '<div class="wrap">' + 
            '    <div class="info">' + 
            '        <div class="body">' + 
            '            <div class="desc">' + 
                            `<div class="ellipsis">${selectedApartmentName.value}</div>` +
            '            </div>' + 
            '        </div>' + 
            '    </div>' +    
            '</div>';
            
      overlay.setContent(selectedContent.value);
      overlay.setMap(map);
      overlay.setPosition(marker.getPosition());



      clickMarker(position.index);
      selectedMarkerIndex.value = position.index;
    })

    kakao.maps.event.addListener(marker, 'mouseover', () => {
      const overImageSrc = position.isBookMark ? markImageSrc : imageSrc;
      const overImageSize = position.isBookMark ? new kakao.maps.Size(35, 47) : new kakao.maps.Size(40,40);
      const overMarkerImage = new kakao.maps.MarkerImage(overImageSrc, overImageSize)
      marker.setImage(overMarkerImage);
    })
    /* global kakao */
    kakao.maps.event.addListener(marker, 'mouseout', () => {
      // infoWindow.close();
      marker.setImage(markerImage);
      
    })
    // addEventListener
    markers.value.push(marker);
  });

  

  /* global kakao */
  

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);

  if (props.selectedIndex == null){
    const bounds = positions.value.reduce(
      (bounds, position) => bounds.extend(position.latlng),
      new kakao.maps.LatLngBounds()
    );
    if (Object.keys(bounds).length){
      map.setBounds(bounds);
    }
  } else{
    //북마크 등록/해제한 위치로 지도 움직이기
    let newLatLng = positions.value[props.selectedIndex].latlng;
    map.panTo(newLatLng);
  }
  
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};






</script>

<template>
  <div id="map"></div>
</template>

<style>
#map {
  width: 100%;
  height: 100%;
}

.wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 60px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .info {width: 286px;height: 50px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .info .title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
    .info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
    .info .close:hover {cursor: pointer;}
    .info .body {position: relative;overflow: hidden;}
    .info .desc {position: relative;margin: 13px 0 0 10px;height: 75px;}
    .desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap; font-size: 18px;font-weight: bold; text-align: center;}
    .desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
    .info .img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}
    .info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
    .info .link {color: #5085BB;}

</style>
