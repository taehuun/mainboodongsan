<script setup>
    const props = defineProps({
        dealDict : Object,
        favorList : Array,
        isShowList : Array,
        loadingCount : Number,
    })

    const emit = defineEmits([
        "clickShowList", 
        "clickBookmark",  
        "clickIndex"
    ])

    const clickShowList = (index) => {
        emit("clickShowList", index);
    }

    const clickBookmark = (house, index) => {
        emit("clickBookmark", house, index);
    }

    const clickIndex = (index) => {
        emit("clickIndex", index)
    }
    

</script>

<template>
    <div v-for="(value, name, index) in props.dealDict" :key="index" class="border-light apart" :id="'apartInfo' + (index)" > 
        <div class=" cust_box row p-3 pt-4 rounded" style=" margin-right:10px;" :class="{loading : props.loadingCount != 0}">
            <div class="col-8 " @click="clickShowList(index)" style="cursor: pointer; padding-left:30px">
            <h5>{{ name }}</h5>
            <div>(총 : {{ value.list.length }} 건)</div>
            <!-- <p class="m-0">거래금액: {{ house.dealAmount }}</p>
            <p class="m-0">전용면적: {{ house.area }}</p>
            <p class="m-0">등록일: {{ house.dealYear }}년 {{ house.dealMonth }}월</p> -->
            </div>
            <div class="col-4 align-self-center" style="text-align: center;">
            <div class="row">
                <!-- <div class="col-6">X</div> -->
                <div
                @click="clickBookmark(value.list[0], index)"
                :id="'bookmarkStar' + (index)"
                aria-hidden="true"
                style="font-size: 25px; cursor: pointer;"
                class=" scale-up-5 col-3"
                >
                <img v-if="favorList[index]" src="../../assets/img/star-fill.svg" alt="">
                <img v-else src="../../assets/img/star.svg" alt="">
                
                <input type="hidden" value="' + dealNo + '" />
                </div>
                <div 
                @click="clickShowList(index)"
                style="cursor: pointer;"
                class="col-3 mt-2"
                >
                <span v-if="props.isShowList[index]"><font-awesome-icon :icon="['fas', 'angle-down']" /></span>
                <span v-else><font-awesome-icon :icon="['fas', 'angle-up']" /></span>
                </div>
            </div>
            </div>

            <div class="text-center rounded-bottom" style="cursor: pointer;">
            <h6 class="m-0 text-center text-white"></h6>
            </div>
        </div>
        <hr style="margin-top:0px; margin-left:30px; margin-right:20px; margin-bottom:0px;" />
        <!-- 각 건물별 거래내역 리스트 토글식으로 보여주기. -->
        <!-- 버튼 누르면 listShow를 true/false로 바꾸면서 v-show로 listShow 값을 보면서 리스트를 출력할지 말지 결정. -->
        <div v-show="props.isShowList[index]">
            <div v-for="(v, k) in value.list" :key="k" class="border-light apart" :id="'deal'+k">
            <div class=" cust_box row p-3 pt-4 rounded" style=" margin-right:10px;" :class="{loading : props.loadingCount != 0}" 
            @click="clickIndex(index)">
                <div class="col-2"></div>
                <div class="col-2">{{ k+1 }}</div>
                <div class="col-8 " style="cursor: pointer; padding-left:30px">
                <h5>{{v.dealYear}}년 {{ v.dealMonth }}월</h5>
                <p class="m-0">거래금액: {{ v.dealAmount }}</p>
                <p class="m-0">전용면적: {{ v.area }}</p>
                <p class="m-0">등록일: {{ v.dealYear }}년 {{ v.dealMonth }}월</p>
                </div>
                <div class="text-center rounded-bottom" style="cursor: pointer;">
                <h6 class="m-0 text-center text-white"></h6>
                </div>
            </div>
            <hr style="margin-top:0px; margin-left:30px; margin-right:20px; margin-bottom:0px;" />
            </div>
        </div>
        
    </div>
</template>

<style scoped>
.loading{
  opacity: 0.1;
}
</style>