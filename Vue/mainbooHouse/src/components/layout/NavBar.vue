<script setup>
import { ref } from "vue";
import { useMemberStore } from "../../stores/memberStore";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import router from "../../router";

const memberStore = useMemberStore();
const { isLogin, userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore;

// const tempUser = ref({
//   name: "ssafy",
//   email: "ssafy@ssafy.com",
// });

const tempUser = ref({
    name: "ssafy",
    email: "ssafy@ssafy.com",
});

const myPage = () => {
    console.log("myPage로 이동");
    router.push({ name: "myPage" });
};

const logout = () => {
    console.log("logout 실행");
    userLogout();
    router.push({ name: "home" });
};

const login = () => {
    console.log("login 실행");
    router.push({ name: "login" });
};

const join = () => {
    console.log("join 실행");
    router.push({ name: "join" });
};

const isMyPage = ref(true);
// export default {
//   name: 'NavBar',
//   data() {
//     return {
//       mypageBreadCrumb: {
//         title: 'MyPage',
//         subTitle: '개인정보 조회',
//         desc: '개성있는 프로필 사진을 등록해보세요.',
//       },
//     };
//   },
//   methods: {
//     changePage(page) {
//       if (page === 'mypage') this.$emit('change-page', { page: page, breadCrumbInfo: this.mypageBreadCrumb });
//     },
//     logout(){

//       this.$store.commit('SET_LOGOUT');

//       this.$router.push("/");
//     }
//   },
//   computed : {
//         requireImg : function(){
//             console.log(this.$store.state.userInfo.userProfileImageUrl);

//            if (this.$store.state.userInfo.userProfileImageUrl == '') {
//                 return require('../assets/img/noProfile.png')
//             } else {
//                 return this.$store.state.userInfo.userProfileImageUrl;
//             }

//         }
//             ,

//     },

// };
</script>

<template>
    <main class="" style="overflow: visible">
        <nav class="navbar navbar-top navbar-expand navbar-dashboard navbar-dark ps-0 pe-2 pb-0">
            <div class="container-fluid px-0">
                <div class="d-flex justify-content-between w-100" id="navbarSupportedContent">
                    <div class="d-flex">
                        <button id="btnSsafyLogo" class="navbar-brand btn">
                            <router-link to="/">
                                <img class="img-fluid" style="width: 120px; height: 80px" src="@/assets/img/logo.png" alt="" />
                            </router-link>
                        </button>
                    </div>

                    <ul class="navbar-nav align-items-center" style="height: 50px">
                        <!-- 기존 nav바 -->
                        <!-- <img class="user-avatar md-avatar rounded-circle m-1" alt="Image placeholder" :src="requireImg" />
            
            <li class="nav-item m-2" id="navItem1" >
              <strong>{{ $store.state.userInfo.userName }}({{ $store.state.userInfo.userEmail }})</strong>님 환영합니다.
            </li>
            
            <li class="nav-item" id="navItem2">
              <button id="logout" @click="logout" class="nav-item btn btn-light"><font-awesome-icon :icon="['fas', 'sign-out-alt']"/> Logout</button>
            </li>
            <li class="nav-item" id="navItem3" :class="{ active: $store.state.curPage == 'mypage' }" @click="changePage('mypage')">
              <router-link to="/mypage" id="myPage" class="nav-item btn btn-light"> <font-awesome-icon :icon="['fas', 'user-circle']"/> My Page </router-link>
            </li> -->

                        <!-- dropdown nav바 -->
                        <li class="nav-item dropdown">
                            <template v-if="isLogin">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="height: 55px; margin-top: 30px">
                                    <div class="media d-flex align-items-center">
                                        <img class="user-avatar md-avatar rounded-circle m-1" alt="Image placeholder" src="../../assets/img/noProfile.png" style="width: 40px" />
                                        <div class="media-body ms-2 text-dark align-items-center d-none d-lg-block">
                                            <span class="mb-0 fw-bold"
                                                ><strong>{{ userInfo.name }}({{ userInfo.email }})</strong></span
                                            >
                                        </div>
                                    </div>
                                </a>
                                <div class="dropdown-menu dashboard-dropdown dropdown-menu-end">
                                    <li class="dropdown-item rounded-top fw-bold" id="navItem3">
                                        <div class="nav-item" style="cursor: pointer" @click="myPage"><font-awesome-icon :icon="['fas', 'user-circle']" /> My Page</div>
                                        <!-- <router-link to="/mypage" id="myPage" class="nav-item btn btn-light"> <font-awesome-icon :icon="['fas', 'user-circle']"/> My Page </router-link> -->
                                    </li>
                                    <div role="separator" class="dropdown-divider my-0"></div>
                                    <li class="dropdown-item rounded-bottom fw-bold" id="navItem2">
                                        <!-- <button id="logout" @click="logout" class="nav-item btn btn-light"><font-awesome-icon :icon="['fas', 'sign-out-alt']" class="text-danger"/> Logout</button> -->
                                        <div class="nav-item" style="cursor: pointer" @click="logout">
                                            <font-awesome-icon :icon="['fas', 'sign-out-alt']" class="text-danger" /> Logout
                                        </div>
                                    </li>
                                </div>
                            </template>
                            <template v-else>
                                <a class="nav-link dropdown-toggle" role="button" aria-expanded="false" style="height: 55px; margin-top: 30px">
                                    <div class="media d-flex align-items-center">
                                        <img class="user-avatar md-avatar rounded-circle m-1" alt="Image placeholder" src="../../assets/img/noProfile.png" style="width: 40px" />
                                        <div class="media-body ms-2 text-dark align-items-center d-none d-lg-block" @click="login">
                                            <span class="mb-0 fw-bold"><strong>Login</strong></span>
                                        </div>
                                        <div class="media-body ms-2 text-dark align-items-center d-none d-lg-block" @click="join">
                                            <span class="mb-0 fw-bold ms-3"><strong>회원가입</strong></span>
                                        </div>
                                    </div>
                                </a>
                            </template>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <hr />
    </main>
</template>

<style></style>
