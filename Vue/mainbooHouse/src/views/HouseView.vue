<script setup>
    import { ref, onMounted, onUpdated, watch } from 'vue';
    import axios from 'axios';
    import VKakaoMap from '../components/common/VKakaoMap.vue'; 
    import {favorRegisterApi, favorDeleteApi, getFavorListApi, getDealYearApi, getDealByYearApi} from '../api/houseApi'
    import { useMemberStore } from '../stores/memberStore';
    import { storeToRefs } from 'pinia';
    import { useRoute } from 'vue-router';
    import DealListVue from '../components/house/DealList.vue';
    import FavorListVue from '../components/house/FavorList.vue';
    const memberStore = useMemberStore();
    const {userInfo} = storeToRefs(memberStore);

    const loadingCount = ref(0);
    const base_url = "http://localhost:80/" + "addrs"

    const route = useRoute();
    const { showCondition } = route.params;

    const selectedSidoName = ref("empty");
    const selectedGugunName = ref("empty");
    // const selectedDongName = ref("");
    const selectedDongCode = ref("empty");

    const sidoList = ref();
    const gugunList = ref("");
    const dongList = ref("");
    const showYearAmount = ref(9);
    const startYear = ref();
    const startMonth = ref();
    const dealYear = ref("empty");
    const dealMonth = ref();
    const dealList = ref();
    const dealDict = ref({});

    const showWhat = ref(""); //showWhat == "favor"  || showWhat == "deal"
    const favorList = ref([]);
    const selectedIndex = ref();
    const isShowList = ref([]);
    const isShowListFavor = ref([]);

    const getSidoList = () => {
        axios.get(`${base_url}/`, {
        })
        .then((response) =>{
            sidoList.value = response.data
        })
    }

    const getGugunList = () => {
        axios.get(`${base_url}/${selectedSidoName.value}`, {

        })
        .then((response) =>{
            console.log("getGugunList response")
            gugunList.value = response.data
        })
    }

    const getDongList = () => {
        axios.get(`${base_url}/${selectedSidoName.value}/${selectedGugunName.value}`, {

        })
        .then((response) =>{
            console.log("getDongList response")
            dongList.value = response.data
        })
    }

    const getDealList = () => {
      
        axios.get(`${base_url}/deals/${selectedDongCode.value}`, {
            params:{
                year : dealYear.value,
                month : dealMonth.value,
                email : userInfo.value.email,
            },
        })
        .then((response)=> {
            console.log(response);
            if(response.data.length != 0){
                console.log("getDealList response")
                dealList.value = response.data;
                favorList.value = [];
                //모든 거래내역을 각각 보면서 건물별로 dealDict에 정리.
                dealDict.value = {};
                for (let i=0; i<dealList.value.length; i++){

                  
                  if(dealDict.value[dealList.value[i].apartmentName]) {
                    dealDict.value[dealList.value[i].apartmentName].list.push(dealList.value[i]);
                  }else{
                    dealDict.value[dealList.value[i].apartmentName] = {list:[], isFavor:false}
                    dealDict.value[dealList.value[i].apartmentName].list.push(dealList.value[i]);
                  }

                  if(dealList.value[i].email){
                    dealDict.value[dealList.value[i].apartmentName].isFavor = true;
                  }
                }

                //검색결과 북마크 표시 여부
                for(var key in dealDict.value) {
                  favorList.value.push(dealDict.value[key].isFavor ? true : false);
                  isShowList.value.push(false);
                  
                }

            }else{
                console.log("검색 결과 없음")
                dealList.value = null;
            }
            // showWhat.value = "deal"
            loadingCount.value = 0;
        })
        
    }

    //관심지역 보기 클릭
    const showFavorClick = (index) => {
      loadingCount.value = 1;
      selectedIndex.value = null;
      showWhat.value = "favor";
      getFavorListApi(
        userInfo.value.email,
        (response) => {
          console.log("getFavorListApi response");
          if(response.data.length != 0){
                console.log("getFavorListApi response")
                dealList.value = response.data;
                favorList.value = [];
                //모든 거래내역을 각각 보면서 건물별로 dealDict에 정리.
                dealDict.value = {};
                for (let i=0; i<dealList.value.length; i++){

                  let keyName = dealList.value[i].apartmentName +"_"+ dealList.value[i].dong;
                  if(dealDict.value[dealList.value[i].apartmentName]) {
                    dealDict.value[keyName].list.push(dealList.value[i]);
                  }else{
                    dealDict.value[keyName] = {list:[], isFavor:false, yearList:[], aptCode : dealList.value[i].aptCode, listByYear : []}
                    dealDict.value[keyName].list.push(dealList.value[i]);
                  }

                  if(dealList.value[i].email){
                    dealDict.value[keyName].isFavor = true;
                  }
                }

                //검색결과 북마크 표시 여부
                for(var key in dealDict.value) {
                  favorList.value.push(dealDict.value[key].isFavor ? true : false);
                  isShowList.value.push(false);
                  
                }

            }else{
                console.log("검색 결과 없음")
                dealList.value = null;

            }
            loadingCount.value = 0;
            showWhat.value = "favor";
        },
        (error) => {
          console.log("getFavorListApi error");
        }
      );
    }


    const clickBookmark = async (house, index) => {
        console.log("HouseView.vue favorRegister started");
        if (!favorList.value[index]){
          //관심 지역 등록
          await favorRegisterApi(
            {
              house:house,
              email:userInfo.value.email
            },
            (response) => {
              console.log("favorRegister response")
              house.email = userInfo.value.email;
              selectedIndex.value = index;
            },
            (error) => {
              console.log("favorRegister error")
            }
          )
        }
        else{
          //관심 지역 해제
          await favorDeleteApi(
            {
              house:house,
              email:userInfo.value.email
            },
            (response) => {
              console.log("favorDelete response")
              house.email = null;
              selectedIndex.value = index;
            },
            (error) => {
              console.log("favorDelete error");
            }
          )

        }
        favorList.value[index] = !favorList.value[index];
        
        
    }

    const clickIndex = (index) => {
      selectedIndex.value = index;
    }

    const clickYearIndex = (index, parentIndex, aptCode, dealYear, keyName) => {
      isShowListFavor.value[index] = !isShowListFavor.value[index];
      for (let i=0 ; i<isShowListFavor.value.length ; i++){
        if (i != index){
          isShowListFavor.value[i] = false;
        }
      }
      selectedIndex.value = parentIndex;

      //해당 연도에 해당하는 거래정보 가져오기.
      //aptCode, dealYear 필요
      console.log("clickYearIndex in HouseView.vue ==> ");

      getDealByYearApi(
        {aptCode, dealYear},
        (response) => {
          console.log("getDealByYearApi response");
          dealDict.value[keyName].listByYear = response.data;
        },
        (error) => {
          console.log("getDealByYearApi error");
        }
      )

    }

    const clickShowList = (index) => {
      isShowList.value[index] = !isShowList.value[index]
      selectedIndex.value = index;
    }






    //관심지역 건물중 하나를 클릭
    const clickShowFavorList = (index, keyName) => {

      //
      console.log("HouseView.vue :: >> clickShowFavorList called");
      isShowList.value[index] = !isShowList.value[index];
      let parseIndex = keyName.indexOf('_');
      let apartmentName = keyName.substr(0, parseIndex);

      getDealYearApi(
        dealDict.value[keyName].aptCode,
        (response) => {
          console.log("getDealYearApi response");
          let dealYearList = response.data;
          dealDict.value[keyName].yearList = dealYearList;
          for (let i=0 ; i<dealYearList.length ; i++){
            isShowListFavor.value.push(false);
          }

          selectedIndex.value = index;
        },
        (error) => {
          console.log("getDealYearApi error");
        }
      )


      //housedeal에 아파트의 동코드로 검색된 dealYear를 가져와서 1단계로 매물이 존재하는 연도를 보여주고,
      //dealDict.value[keyName].list = [쿼리결과.  dealYear : year]

      //연도를 선택하면 아파트이름 + 연도로 거래정보를 가져와서 이제 원래 보여줘야되는 dealList를 출력.
      //dealDict.value[keyName].list.dealYear = [dealList]
    }







    const clickShowListFromMarker = (index)=> {
      console.log("clickShowListFromMarker");

      for (let i=0 ; i < isShowList.value.length ; i++) {
        if(i != index){
          isShowList.value[i] = false;
        }else{
          isShowList.value[i] = true;
        }
      }
    }

    

    const  btnSearchClick = function() {
      console.log("btnSearchClick 시작")
      loadingCount.value = 1;
      selectedIndex.value = null;
      showWhat.value = "deal"
      getDealList();
    }

    onMounted(() => {
        getSidoList();
        let today = new Date();
        startYear.value = today.getFullYear() - showYearAmount.value;
        startMonth.value = today.getMonth();


        console.log("onMounted 실행.")
        
    })

    watch(selectedSidoName, () => {
        console.log("watch selectedSidoName");

        getGugunList();
    })

    watch(selectedGugunName, () => {
        console.log("watch selectedGugunName");
        getDongList();
    })

    watch(selectedDongCode, () => {
        console.log("watch selectedDongCode");
    })

</script>

<template>

  <main class="">
    <div class="row">
      <div class="col-12 mb-4">
        <div class="card border-light shadow-sm ">
          <div class="card-body">
            <!-- selectbar start  -->
            <div class="d-flex justify-content-center mb-2" style="height:100px;">
              <div class="row">
                <div class="col align-self-center">
                  <select v-model="selectedSidoName" class="form-select select" aria-label="시" style="width:auto;">
                    <option value="empty" selected disabled>시/도</option>
                    <option v-for="(sido) in sidoList" :key="sido.sidoName" :value="sido.sidoName">{{ sido.sidoName }}</option>
                  </select>
                </div>
                <div class="col align-self-center">
                  <select v-model="selectedGugunName" class="form-select select" aria-label="구" style="width: auto;">
                    <option value="empty" selected disabled>구/군</option>
                    <option v-for="(gugun) in gugunList" :key="gugun.gugunName" :value="gugun.gugunName">{{ gugun.gugunName }}</option>
                  </select>
                </div>
                <div class="col align-self-center">
                  <select v-model="selectedDongCode" class="form-select select" aria-label="동" style="width: auto;">
                    <option value="empty" selected disabled>동</option>
                    <option v-for="(dong) in dongList" :key="dong.dongName" :value="dong.dongCode">{{ dong.dongName }}</option>
                  </select>
                </div>
                <div class="col align-self-center">
                  <select v-model="dealYear" class="form-select select" aria-label="년" style="width: auto;">
                    <option value="empty" selected disabled>거래년도</option>
                    <option v-for="(year, index) in (showYearAmount)" :key="index" :value="year+startYear">{{ year+startYear }}년</option>
                  </select>
                </div>
                
                <div class="col align-self-center">
                  <input type="button" class="btn btn-secondary ml-3" value="검색" @click="btnSearchClick" :disabled="selectedDongCode == 'empty' || loadingCount != 0" />
                </div>
                <div class="col align-self-center">
                  <input
                    type="button"
                    value="내 관심지역 보기"
                    class="btn ml-3 btn-warning"
                    @click="showFavorClick"
                    :disabled="!selectedDongCode"
                  />
                </div>
              </div>
            </div>
            <!-- selectbar end  -->
            <div class="row m-1" style="height:550px;">
              <!-- pulseLoader -->
              <div class="col-3" id="apartInfo" style="height: 100%; position: relative;">
                <div v-show="loadingCount != 0" style="position: absolute; left: 50%; top: 50%;">
                    <div class="spinner-border d-flex justify-content-center" role="status"></div>
                </div>
                <!-- aptInfo start -->
                <div class="text-center">거래정보 검색</div>
                <div class="myscroll" style="height: 100%; overflow-y: scroll; width: 100%;" >
                  
                  <div>
                    <template v-if="dealDict">
                      <template v-if="showWhat == 'deal'">
                        <DealListVue :dealDict="dealDict" :favorList="favorList" :isShowList="isShowList" :loadingCount="loadingCount"
                                  @clickShowList="clickShowList"
                                  @clickBookmark="clickBookmark"
                                  @clickIndex="clickIndex">
                        </DealListVue>
                      </template>
                      <template v-else-if="showWhat == 'favor'">
                        <FavorListVue :dealDict="dealDict" 
                                      :favorList="favorList" 
                                      :isShowList="isShowList" :isShowListFavor="isShowListFavor" 
                                      :loadingCount="loadingCount"
                                  @clickShowFavorList="clickShowFavorList"
                                  @clickBookmark="clickBookmark"
                                  @clickIndex="clickIndex"
                                  @clickYearIndex="clickYearIndex">
                        </FavorListVue>
                      </template>

                    </template>
                    <template v-else>
                      <div class="border-light apart"> 
                        <div class=" cust_box row p-3 pt-4 rounded" style=" margin-right:10px;" :class="{loading : loadingCount != 0}">
                          <div class="col-9 " style="cursor: pointer; padding-left:30px">
                            <p class="m-0">검색 결과가 존재하지 않습니다.</p>
                          </div>
                          <div class="col-3 align-self-center" style="text-align: center;">
                          </div>
                        </div>
                        <hr style="margin-top:0px; margin-left:30px; margin-right:20px; margin-bottom:0px;" />
                      </div>
                    </template>
                  </div>
                  <hr style="margin-top:0px; margin-left:30px; margin-right:20px; margin-bottom:0px;" />
                </div>
                <!-- aptInfo end -->
                
                
              </div>
              <!-- aptInfo End -->

              <!-- kakoMap Start -->
              <div class="col-9">
                <VKakaoMap :houses="dealDict" :favorList="favorList" :selectedIndex="selectedIndex" :showCondition="showCondition" 
                @clickMarker="clickShowListFromMarker"
                @bookmarkInit="showFavorClick" />
              </div>
              <!-- kakaoMap End -->

              <!-- <div id="map" class="col" style=" width:100%; height: 550px"></div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
.myscroll {
    width: 250px;
    height: 140px;
    overflow: auto;
  }
  .myscroll::-webkit-scrollbar {
    width: 10px;
  }
  .myscroll::-webkit-scrollbar-thumb {
    background-color: #2f3542;
    border-radius: 10px;
    background-clip: padding-box;
    border: 2px solid transparent;
  }
  .myscroll::-webkit-scrollbar-track {
    background-color: rgb(209, 208, 208);
    border-radius: 10px;
    box-shadow: inset 0px 0px 5px white;
  }

.loading{
  opacity: 0.1;
}
</style>