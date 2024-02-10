<script setup>
import imageUrl from "@/assets/img/illustrations/signin.svg"
import LoginForm from "../components/LoginForm.vue";
import axios, { HttpStatusCode } from 'axios';
import {storeToRefs} from "pinia";
import { useRouter } from "vue-router";
import { userLoginApi } from "../api/memberApi";
import {useMemberStore} from "../stores/memberStore";


const base_url = "http://localhost:80/"
const router = useRouter();

const memberStore = useMemberStore();
const {isLogin} = storeToRefs(memberStore);
const {userLogin, userAuth} = memberStore;

const requestLogin = async (loginUser) => {
  console.log("여기는 LoginView. requestLogin 실행");
  console.log(isLogin.value);
  await userLogin(loginUser);

  if(isLogin.value){
    console.log("로그인 성공");
    //로그인 성공시, 최초 1회 토큰 인증을 실행해서 로그인한 유저 정보를 memberStore에 저장.
    await userAuth(sessionStorage.getItem("accessToken"));
    router.push({name:"home"});
  }else{
    console.log("로그인 실패");
    alert("아이디 및 비밀번호를 다시 확인해주세요.");
  }

}


</script>

<template>
    <LoginForm @request-login="requestLogin"/>
</template>

<style scoped>

</style>