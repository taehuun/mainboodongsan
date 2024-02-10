<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/community";

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
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
    articleNo: 0,
    subject: "",
    content: "",
    email: "",
    hit: 0,
    registerTime: "",
});

if (props.type === "modify") {
    let { articleno } = route.params;
    console.log(articleno + "번글 얻어와서 수정할거야");
    getModifyArticle(
        articleno,
        ({ data }) => {
            article.value = data;
            isUseId.value = true;
        },
        (error) => {
            console.error(error);
        }
    );
    isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
    () => article.value.subject,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 30) {
            subjectErrMsg.value = "제목을 확인해 주세요!!!";
        } else subjectErrMsg.value = "";
    },
    { immediate: true }
);
watch(
    () => article.value.content,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 500) {
            contentErrMsg.value = "내용을 확인해 주세요!!!";
        } else contentErrMsg.value = "";
    },
    { immediate: true }
);

function onSubmit() {
    // event.preventDefault();

    if (subjectErrMsg.value) {
        alert(subjectErrMsg.value);
    } else if (contentErrMsg.value) {
        alert(contentErrMsg.value);
    } else {
        props.type === "regist" ? writeArticle() : updateArticle();
    }
}

function writeArticle() {
    console.log("글등록하자!!", article.value);
    article.value.email = userInfo.value.email;
    registArticle(
        article.value,
        (response) => {
            let msg = "글등록이 완료되었습니다.";
            alert(msg);
            moveList();
        },
        (error) => console.error(error)
    );
}

function updateArticle() {
    console.log(article.value.articleNo + "번글 수정하자!!", article.value);
    modifyArticle(
        article.value,
        (response) => {
            let msg = "글정보 수정이 완료되었습니다.";
            alert(msg);
            moveList();
            // router.push({ name: "article-view" });
            // router.push(`/board/view/${article.value.articleNo}`);
        },
        (error) => console.log(error)
    );
}

function moveList() {
    router.push({ name: "community-list" });
}
</script>

<template>
    <form @submit.prevent="onSubmit">
        <div class="mb-3">
            <label for="subject" class="form-label">제목 : </label>
            <input type="text" class="form-control" v-model="article.subject" placeholder="제목..." />
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">내용 : </label>
            <textarea class="form-control" v-model="article.content" rows="10"></textarea>
        </div>
        <div class="col-auto text-center">
            <button type="submit" class="btn float-end" style="background-color: pink; color: white" v-if="type === 'regist'">글작성</button>
            <button type="submit" class="btn float-end" style="background-color: pink; color: white" v-else>글수정</button>
            <button type="button" class="me-2 btn btn-secondary float-end" @click="moveList">취소</button>
        </div>
    </form>
</template>

<style scoped></style>
