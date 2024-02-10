import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '../views/LoginView.vue'
// import CommunityView from '../views/CommunityView.vue'
import HomeView from '../views/HomeView.vue'
import HouseView from '../views/HouseView.vue'
import MyPageView from '../views/MyPageView.vue'
import axios, { HttpStatusCode } from 'axios';
import { jwtDecode } from "jwt-decode";
import { userAuthApi } from "../api/memberApi";
import { useMemberStore } from '../stores/memberStore';
import { storeToRefs } from 'pinia';
import { onMounted } from 'vue';
import JoinView from '../views/JoinView.vue'
// import NoticeView from "../views/NoticeView.vue";


const requireLogin = () => async (from, to, next) => {
  const token = sessionStorage.getItem('token');
  const accessToken = sessionStorage.getItem('accessToken');
  const memberStore = useMemberStore();
  const { userAuth } = memberStore;
  const { isLogin, userInfo, isValidToken } = storeToRefs(memberStore);

  console.log("requireLogin start")

  if(isLogin.value){
    if (userInfo.value != null && accessToken){
      //만료된 토큰인지, 올바른 토큰인지 검증을 위해 서버로 보냄
      //decodeToken.userid
      console.log("index.js/ if : userInfo != null && accessToken == true")
      await userAuth(accessToken);
    }

    if (!isValidToken.value || userInfo.value === null) {
      alert("로그인 오류");
      console.log("토큰이 만료되었거나 문제가 발생하였음. 재 로그인이 필요");
      router.push({name : "login"});
    } else{
      console.log("로그인 확인 완료");
      next();
    }

  }else{
    alert("로그인이 필요합니다.");
    router.push({name : "login"});
  }

}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/bookmarkHouse/:showCondition',
      name: 'bookmarkHouse',
      component: HouseView,
      beforeEnter: requireLogin(),
    },
    {
      path: '/searchDong/:showCondition',
      name: 'searchDong',
      component: HouseView,
      beforeEnter: requireLogin(),
    },
    {
      path: '/myPage',
      name: 'myPage',
      component: MyPageView,
    },
    {
      path: '/join',
      name: 'join',
      component: JoinView,
    },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/TheNoticeView.vue"),
      beforeEnter: requireLogin(),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "view/:articleno",
          name: "article-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
    //커뮤니티
    {
      path: "/community",
      name: "community",
      component: () => import("../views/CommunityView.vue"),
      beforeEnter: requireLogin(),
      redirect: { name: "community-list" },
      children: [
        {
          path: "list",
          name: "community-list",
          component: () => import("@/components/community/CommunityList.vue"),
        },
        {
          path: "view/:articleno",
          name: "community-view",
          component: () => import("@/components/community/CommunityDetail.vue"),
        },
        {
          path: "write",
          name: "community-write",
          component: () => import("@/components/community/CommunityWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "community-modify",
          component: () => import("@/components/community/CommunityModify.vue"),
        },
      ],
    },
  ],
});


router.beforeEach(async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userAuth, getUserInfo } = memberStore;
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  console.log("beforeEach 실행")
  
  let refreshToken = sessionStorage.getItem("refreshToken");

  //로그인은 했으나, 새로고침해서 userInfo가 날아갔을때.
  if(userInfo.value == null && refreshToken != null){
    console.log("userInfo가 존재하지 않음. 새로 가져옴.")
    await getUserInfo(refreshToken);
  }
  console.log("beforeEach 결과")
  //캔슬하려면 return false;
  next();
})

export default router
