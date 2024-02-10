<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, rippleWrite, deleteRipple } from "@/api/community";
import { useMemberStore } from "../../stores/memberStore";
//storeToRefs는 store.js 파일 내부에서 선언한 변수값을 ref로 가져오는 함수
import { storeToRefs } from "pinia";

// import한 'useMemberStore' 함수를 사용해서 객체 memberStore를 생성
const memberStore = useMemberStore();

// store 내부에 있는 함수를 가져오고 싶다면,
const { userLogin, userAuth, getUserInfo } = memberStore;

// store 내부에 있는 변수를 ref로 가져오기
const { userInfo } = storeToRefs(memberStore);

const sameUser = "";
const route = useRoute();
const router = useRouter();
const { articleno } = route.params;
const article = ref({});
const count = ref({});
const ripple = ref({
    articleNo: 0,
    rippleNo: 0,
    content: "",
    email: "",
    registerTime: "",
});

onMounted(() => {
    getArticle();
});

const getArticle = () => {
    detailArticle(articleno, ({ data }) => {
        article.value = data;
        // console.log(article.value.list, "댓글 정보");
        // ripple.value = article.value.list;
        console.log(ripple.value, "ripple댓글 정보");
        count.value = article.value.list.length;
        // count = article.value.list.length;
    });
};
function moveList() {
    router.push({ name: "community-list" });
}

function moveModify() {
    router.push({ name: "community-modify", params: { articleno } });
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

function onDeleteRipple(rippleno) {
    // const { articleno } = route.params;
    console.log(rippleno, "번글 삭제하러 가자!!!");
    // API 호출
    deleteRipple(
        rippleno,
        articleno,
        (response) => {
            location.reload();
        },
        (error) => {
            console.error(error);
        }
    );
}

function writeRipple() {
    ripple.value.email = userInfo.value.email;
    console.log(ripple);
    ripple.value.articleNo = articleno;
    rippleWrite(
        ripple.value,
        (response) => {
            location.reload();
        }
        // (error) => console.error(error)
    );
}
</script>

<template>
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
                <button
                    type="button"
                    class="btn btn-sm btn-danger float-end ms-2 mt-2"
                    @click="onDeleteArticle"
                    v-if="userInfo.email === article.email || userInfo.email === 'admin'"
                >
                    삭제
                </button>
                <button
                    type="button"
                    class="btn btn-sm float-end mt-2"
                    style="background-color: pink; color: white"
                    @click="moveModify"
                    v-if="userInfo.email === article.email || userInfo.email === 'admin'"
                >
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
                        <span>익명</span>
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

        <div class="mb-3">
            <div class="tit-area fz2">
                총 <span class="c-blue">{{ count }}</span
                >건의 댓글이 있습니다.
            </div>
            <input type="text" class="form-control" v-model="ripple.content" placeholder="댓글을 남겨주세요." />
            <div class="reply-write-area">
                <div class="rw-inner">
                    <div class="btns tar">
                        <button type="button" class="btn btn-sm sz2" style="background-color: pink; color: white" @click="writeRipple">등록</button>
                    </div>
                </div>
            </div>

            <div class="reply-list-area">
                <ul>
                    <li class="" rel="" v-for="list in article.list" :key="list.articleNo">
                        <div class="status float-base">
                            <div class="column user-info">
                                <span class="name" v-if="list.email === 'admin'">관리자</span>
                                <span class="name" v-else>익명</span>
                            </div>
                            <div class="column date">
                                <span>{{ list.registerTime }}</span>
                            </div>
                        </div>
                        <div class="reply-content" style="line-break: anywhere">
                            {{ list.content }}
                            <button class="btn" @click="onDeleteRipple(list.rippleNo)" v-if="userInfo.email === list.email || userInfo.email === 'admin'">
                                <font-awesome-icon :icon="['fas', 'trash']" />
                            </button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="footer"></div>
    </div>
</template>

<style scoped></style>
