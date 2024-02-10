<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";

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

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

const article = ref({});

onMounted(() => {
    getArticle();
});

const getArticle = () => {
    detailArticle(articleno, ({ data }) => {
        article.value = data;
    });
};

function moveList() {
    router.push({ name: "article-list" });
}

function moveModify() {
    router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
    // const { articleno } = route.params;
    console.log(articleno + "번글 삭제하러 가자!!!");
    // API 호출
    deleteArticle(
        articleno,
        (response) => {
            moveList();
        },
        (error) => {
            console.error(error);
        }
    );
}
</script>

<template>
    <div class="container my-5">
        <form class="row g-3 basic-form"></form>
    </div>

    <div class="container my-5">
        <div class="row">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h4 class="mb-3">{{ article.subject }}</h4>
                        <hr />
                        <div style="min-height: 500px">{{ article.content }}</div>
                    </div>
                </div>
                <button type="button" class="btn btn-sm mt-2" style="background-color: pink; color: white" @click="moveList">목록</button>
                <button type="button" class="btn btn-sm btn-danger float-end ms-2 mt-2" @click="onDeleteArticle" v-if="userInfo.email === 'admin'">삭제</button>
                <button type="button" class="btn btn-sm float-end mt-2" style="background-color: pink; color: white" @click="moveModify" v-if="userInfo.email === 'admin'">
                    수정
                </button>
            </div>
            <div class="col-md-4">
                <ul class="list-group">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        글번호 :
                        <span>{{ article.articleNo }}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        작성자 :
                        <span>{{ article.email }}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        조회수 :
                        <span>{{ article.hit }}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        작성일시 :
                        <span>{{ article.registerTime }}</span>
                    </li>
                    <li v-for="(file, index) in fileList" :key="index" class="list-group-item">
                        <a type="button" class="p-0 m-0 btn btn-outline btn-default btn-xs" v-bind:href="file.fileUrl" v-bind:download="file.fileName">
                            <img style="width: 20px" src="../../assets/images/download_icon.png" />
                            <span class="ms-2 fileName">{{ file.fileName }}</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="footer"></div>
    </div>
</template>

<style scoped></style>
