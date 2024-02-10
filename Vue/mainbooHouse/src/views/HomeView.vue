<script setup>
import { HttpStatusCode } from "axios";

const test = () => {};
</script>

<template>
    <main class="">
        <div class="col-md-8 offset-md-2">
            <div class="row justify-content-center mb-5 mb-lg-6">
                <div class="text-center shadow-lg rounded scale mt-8 mb-5" style="background-color: pink; padding: 40px; border-radius: 200px; width: 550px">
                    <span class="lead fw-bolder text-muted display-5">똑똑한 내 집 마련의 시작</span><br />
                    <span class="lead fw-bolder text-white display-1"> 마인부우동산 </span>
                </div>
                <div class="container my-5">
                    <form class="row g-3 basic-form"></form>
                </div>
                <div class="row mb-3 mt-5">
                    <div class="col-6 col-md-3 text-center mb-4">
                        <router-link :to="{name : 'searchDong', params:{showCondition: 'search'}}">
                            <div class="icon icon-shape icon-lg bg-white page-preview scale-up-3 shadow-lg border-light rounded-circle icon-secondary mb-4">
                                <img src="@/assets/img/houses.png" style="width: 55px" />
                                <div class="text-center show-on-hover rounded-bottom">
                                    <p class="m-0 text-center">매물검색</p>
                                </div>
                            </div>
                        </router-link>
                        <h3 class="fw-bolder mt-1">다양한 매물 정보</h3>
                        <p class="text-gray">당신이 원하는 바로 그 집</p>
                    </div>

                    <div class="col-6 col-md-3 text-center mb-4">
                        <router-link :to="{name : 'bookmarkHouse', params:{showCondition: 'bookmark'}}">
                            <div class="icon icon-shape icon-lg bg-white page-preview scale-up-3 shadow-lg scale-up-3 border-light rounded-circle icon-secondary mb-4">
                                <img src="@/assets/img/group.png" style="width: 55px" />
                                <div class="text-center show-on-hover rounded-bottom">
                                    <p class="m-0 text-center">MY관심</p>
                                </div>
                            </div>
                        </router-link>
                        <h3 class="fw-bolder mt-1">유저 맞춤 서비스</h3>
                        <p class="text-gray">관심 매물, 관심 동네 모아보기</p>
                    </div>
                    <div class="col-6 col-md-3 text-center">
                        <router-link to="/board">
                            <div class="icon icon-shape icon-lg bg-white page-preview scale-up-3 shadow-lg scale-up-3 border-light rounded-circle icon-secondary mb-4">
                                <img src="@/assets/img/analysis.png" style="width: 55px" />
                                <div class="text-center show-on-hover rounded-bottom">
                                    <p class="m-0 text-center">공지사항</p>
                                </div>
                            </div>
                        </router-link>
                        <h3 class="fw-bolder mt-1">공지사항</h3>
                        <p class="text-gray">마인부우동산 공지사항</p>
                    </div>
                    <div class="col-6 col-md-3 text-center">
                        <router-link to="/community">
                            <div class="icon icon-shape icon-lg bg-white page-preview scale-up-3 shadow-lg scale-up-3 border-light rounded-circle icon-secondary mb-4">
                                <img src="@/assets/img/community.png" style="width: 55px" />
                                <div class="text-center show-on-hover rounded-bottom">
                                    <p class="m-0 text-center">커뮤니티</p>
                                </div>
                            </div>
                        </router-link>
                        <h3 class="fw-bolder mt-1">커뮤니티</h3>

                        <p class="text-gray">우리 동네 이야기</p>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
a {
    text-decoration: none;
    color: black;
}
a:hover {
    color: pink;
}
</style>
