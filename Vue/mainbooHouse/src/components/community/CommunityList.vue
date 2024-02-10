<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { list } from "@/api/community.js";

const router = useRouter();

const articles = ref([]);

const param = ref("");

onMounted(() => {
    getArticleList();
});

const getArticleList = () => {
    list(
        param.value,
        ({ data }) => {
            console.log(data);
            articles.value = data;
        },
        (error) => {
            console.error(error);
        }
    );
};

const moveWrite = () => {
    router.push({ name: "community-write" });
};
</script>

<template>
    <div class="container my-5">
        <form class="row g-3 basic-form"></form>
    </div>
    <div class="container my-5">
        <div class="card bg-transparent border-0 mt-4">
            <table id="dataTable" class="myDataTable table align-middle table-bordered mb-0 custom-table nowrap dataTable text-center w-100">
                <thead>
                    <tr>
                        <th>제목</th>
                        <th>작성일시</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="article in articles" :key="article.articleNo" :article="article">
                        <td class="w-50 text-start">
                            <h6 class="mb-0">
                                <router-link :to="{ name: 'community-view', params: { articleno: article.articleNo } }" class="link-dark text-decoration-none">
                                    {{ article.subject }}
                                </router-link>
                                <font-awesome-icon :icon="['far', 'comment-dots']" />
                                {{ article.ripple }}
                            </h6>
                        </td>
                        <td>
                            <span class="d-block">{{ article.registerTime }}</span>
                        </td>
                        <td>
                            <span class="d-block">{{ article.hit }}</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="my-2 d-flex justify-content-end">
            <button type="button" class="btn" style="background-color: pink; color: white" @click="moveWrite">글쓰기</button>
            <!-- <router-link class="btn btn-primary" to="/board/notice/insert">글쓰기</router-link> -->
        </div>
    </div>
</template>

<style scoped></style>
