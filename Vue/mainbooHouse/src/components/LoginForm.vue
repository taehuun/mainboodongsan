<script setup>
import { ref } from "vue";

const userEmail = ref("");
const userPassword = ref("");
const loginUser = ref({
    email: userEmail,
    pass : userPassword,
});

const emit = defineEmits(["requestLogin"]);

const login = () => {
    console.log("login 실행.");
    console.log("userEmail : ", userEmail.value);
    console.log("userPassword : ", userPassword.value);
    emit("requestLogin", loginUser.value);
};
</script>

<template>
    <main>
        <!-- Section -->
        <section class="d-flex align-items-center my-5 mt-lg-8 mb-lg-5">
            <div class="container">
                <p class="text-center">
                    <router-link to="/" class="text-gray-700"><font-awesome-icon :icon="['fas', 'angle-left']" /> 이전 페이지로</router-link>
                </p>
                <div class="col-md-6 offset-md-3 d-flex align-items-center justify-content-center">
                    <div class="bg-white shadow-soft border rounded border-light p-4 p-lg-5 w-100 fmxw-500">
                        <div class="text-center text-md-center mb-4 mt-md-0">
                            <h1 class="mb-0 h3">로그인</h1>
                        </div>
                        <div action="#" class="mt-4">
                            <!-- Form -->
                            <div class="form-group mb-4">
                                <label for="email">Email</label>
                                <div class="input-group">
                                    <span class="input-group-text" id="basic-addon1"><font-awesome-icon :icon="['fas', 'envelope']" /></span>
                                    <input
                                        type="email"
                                        class="form-control"
                                        v-model="userEmail"
                                        placeholder="example@example.com"
                                        id="email"
                                        autofocus
                                        required
                                        @keyup.enter="login"
                                    />
                                </div>
                            </div>
                            <!-- End of Form -->
                            <div class="form-group">
                                <!-- Form -->
                                <div class="form-group mb-4">
                                    <label for="password">Password</label>
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon2"><font-awesome-icon :icon="['fas', 'key']" /></span>
                                        <input type="password" placeholder="Password" v-model="userPassword" class="form-control" id="password" required @keyup.enter="login" />
                                    </div>
                                </div>
                                <!-- End of Form -->
                                <div class="d-flex justify-content-between align-items-top mb-4">
                                    <div class="form-check"></div>
                                    <div><button class="btn btn-sm text-right">비밀번호 찾기</button></div>
                                </div>
                            </div>
                            <div class="d-grid">
                                <button class="btn btn-dark" @click="login">Sign in</button>
                            </div>
                            <div class="mt-3 mb-4 text-center">
                                <span class="fw-normal">or</span>
                            </div>
                            <div class="d-flex justify-content-center my-4">
                                <img @click="KakaoLogin" class="btn" src="../assets/img/kakao_login_medium_wide.png" />
                                <!-- <a href="#" class="btn btn-icon-only btn-pill btn-outline-gray-400 text-facebook me-2" type="button" aria-label="kakao button" title="kakao button">
                                    <font-awesome-icon :icon="['fas', 'facebook']"/> 
                                    
                                </a> -->
                            </div>
                        </div>

                        <div class="d-flex justify-content-center align-items-center mt-4">
                            <span class="fw-normal">
                                가입된 계정이 없습니까?
                                <router-link to="/join" class="fw-bold"> Sign up </router-link>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
</template>

<style scoped></style>
