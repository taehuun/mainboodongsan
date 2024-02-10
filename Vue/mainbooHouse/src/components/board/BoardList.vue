<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { list } from "@/api/board.js";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

//여기서부터 pinia 활용.
//먼저 pinia를 사용할 store.js 파일을 import한다.
//(주의 : useMemberStore에서 작성한 변수 및 함수를 반드시 return해야 다른 파일에서 import가 됨)
import { useMemberStore } from "../../stores/memberStore";
//storeToRefs는 store.js 파일 내부에서 선언한 변수값을 ref로 가져오는 함수
import { storeToRefs } from "pinia";

// import한 'useMemberStore' 함수를 사용해서 객체 memberStore를 생성
const memberStore = useMemberStore();

// store 내부에 있는 함수를 가져오고 싶다면,
const { userLogin, userAuth, getUserInfo } = memberStore;

// store 내부에 있는 변수를 ref로 가져오기
const { userInfo } = storeToRefs(memberStore);

const router = useRouter();

const selectOption = ref([
    { text: "검색조건", value: "" },
    { text: "글번호", value: "article_no" },
    { text: "제목", value: "subject" },
    { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
});

onMounted(() => {
    getArticleList();
});

const changeKey = (val) => {
    console.log("BoarList에서 선택한 조건 : " + val);
    param.value.key = val;
};

const getArticleList = () => {
    console.log("서버에서 글목록 얻어오자!!!", param.value);
    list(
        param.value,
        ({ data }) => {
            console.log(data);
            articles.value = data;
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
            console.log(articles.value);
            console.log(currentPage.valuevalue);
            number.value = articles.value.length;
            console.log(number.value, "글 갯수");
        }
        // (error) => {
        //   console.error(error);
        // }
    );
};

const onPageChange = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!");
    currentPage.value = val;
    param.value.pgno = val;
    getArticleList();
};

const moveWrite = () => {
    router.push({ name: "article-write" });
};

const number = ref({});
</script>

<template>
    <div class="container my-5">
        <form class="row g-3 basic-form"></form>
    </div>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="row align-self-center mb-2">
                    <div class="col-md-2 text-start">
                        <button type="button" class="btn btn-sm" style="background-color: pink; color: white" @click="moveWrite" v-if="userInfo.email === 'admin'">글쓰기</button>
                    </div>
                    <div class="col-md-5 mb-2 offset-5">
                        <form class="d-flex">
                            <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
                            <div class="input-group input-group-sm">
                                <input type="text" class="form-control" v-model="param.word" placeholder="검색어..." />
                                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
                            </div>
                        </form>
                    </div>

                    <!-- <div class="d-flex justify-content-center mb-2" style=" height:100px;">
              <div class="row">
                <div class="col align-self-center">
                  <input type="text" @keydown.enter="boardList" placeholder="검색어를 입력하세요" class="form-control" id="searchText" style="width:400px;" />
                </div>
                <div class="col align-self-center">
                  <button @click="boardList" class="btn btn-secondary" type="button">Search</button> </div>
              </div>
            </div> -->
                </div>
                <table class="table table-hover">
                    <thead>
                        <tr class="text-center">
                            <th scope="col">글번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">작성자</th>
                            <th scope="col">조회수</th>
                            <th scope="col">작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <BoardListItem v-for="(article, index) in articles" :key="article.articleNo" :article="article" :number="number - index"></BoardListItem>
                    </tbody>
                </table>
            </div>
            <div class="container my-5">
                <form class="row g-3 basic-form"></form>
            </div>
            <!-- <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation> -->
        </div>
    </div>
    <!-- ////////////////////////// -->

    <!-- //////////////// -->
</template>

<style scoped></style>
