<script setup>
import { ref, computed, onMounted, reactive } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "../stores/memberStore";

const memberStore = useMemberStore();
const { userJoin } = memberStore;

const router = useRouter();

const userName = ref("");
const userEmail = ref("");
const userPhone = ref("");
const userPass = ref("");
const userCPass = ref("");
const userRank = ref("");

const isUserNameFocus = ref("");
const isUserEmailFocus = ref("");
const isUserPhoneFocus = ref("");
const isUserPassFocus = ref("");
const isUserCPassFocus = ref("");

const isUserInputValid = ref("");

// const isUserNameValid = ref("");
const isUserNameValid = computed(() => {
    let regexp = /^[가-힣a-zA-Z0-9]{2,12}$/;
    // return userName.value.length > 0 ? true : false;
    return regexp.test(userName.value) ? true : false;
});

const isUserEmailValid = computed(() => {
    let regexp = /^[a-z0-9]+@[a-z]+(\.[a-z]{2,3}){1,2}$/;
    return regexp.test(userEmail.value) ? true : false;
});

const isUserPhoneValid = computed(() => {
    let regexp = /^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$/;
    // return userPhone.value.length > 0 ? true : false;
    return regexp.test(userPhone.value) ? true : false;
});

const isUserPassValid = computed(() => {
    let patternSpe = new RegExp(/[~`!@#$%^&*()_+|<>?:{}]+/);
    let patternStr = new RegExp(/[a-zA-z]+/);
    let patternNum = new RegExp(/[0-9]+/);
    let patternResult = patternNum.test(userPass.value) && patternStr.test(userPass.value) && patternSpe.test(userPass.value);
    return patternResult;
});
const isUserCPassValid = computed(() => {
    if (!userCPass.value) {
        return false;
    } else {
        return userCPass.value === userPass.value ? true : false;
    }
});
// const isUserEmailValid = ref("");
// const isUserPassValid = ref("");
// const isUserCPassValid = ref("");
// const isUserRankValid = ref("");

const codeList = ref([
    { code: 1, codeName: "Admin" },
    { code: 2, codeName: "User" },
]);

const join = async () => {
    console.log("clicked join btn");
    const newUser = {
        name: userName.value,
        email: userEmail.value,
        pass: userPass.value,
        phoneNumber: userPhone.value,
    };
    await userJoin(newUser);
};
</script>

<template>
    <main>
        <!-- Section -->
        <section class="d-flex align-items-center col-md-6 offset-md-3">
            <div class="container">
                <p class="text-center">
                    <router-link to="/" class="text-gray-700"><font-awesome-icon :icon="['fas', 'angle-left']" /> 이전 페이지로</router-link>
                </p>
                <div class="row justify-content-center form-bg-image">
                    <div class="d-flex align-items-center justify-content-center">
                        <div class="mb-4 mb-lg-0 bg-white shadow-soft border rounded border-light p-4 p-lg-5 w-100 fmxw-500">
                            <div class="text-center text-md-center mb-4 mt-md-0">
                                <h1 class="mb-0 h3">Create account</h1>
                            </div>
                            <!-- <form action="#"> -->
                            <!-- Form -->
                            <div class="form-group mb-4">
                                <label for="userName">Name</label>
                                <div class="input-group">
                                    <span class="input-group-text" id="basic-addon3"><font-awesome-icon :icon="['fas', 'user']" /></span>
                                    <input
                                        type="name"
                                        class="form-control"
                                        placeholder="Enter User Name"
                                        :class="{ 'is-valid': isUserNameValid && isUserNameFocus, 'is-invalid': !isUserNameValid && isUserNameFocus }"
                                        v-model="userName"
                                        @focus="isUserNameFocus = true"
                                        id="userName"
                                    />
                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback">올바른 이름을 입력해 주세요.(2 ~ 12자, 특수기호 제외)</div>
                                </div>
                            </div>
                            <!-- End of Form -->

                            <!-- Form -->
                            <div class="form-group mb-4">
                                <label for="email">Email</label>
                                <div class="input-group">
                                    <span class="input-group-text" id="basic-addon3"><font-awesome-icon :icon="['fas', 'envelope']" /></span>
                                    <input
                                        type="email"
                                        class="form-control"
                                        v-model="userEmail"
                                        placeholder="Enter your email"
                                        id="email"
                                        :class="{ 'is-valid': isUserEmailValid && isUserEmailFocus, 'is-invalid': !isUserEmailValid && isUserEmailFocus }"
                                        @focus="isUserEmailFocus = true"
                                    />

                                    <div class="valid-feedback"></div>
                                    <div class="invalid-feedback">올바른 Email 을 입력해 주세요.</div>
                                </div>
                            </div>
                            <!-- End of Form -->

                            <div class="form-group">
                                <!-- Form -->
                                <div class="form-group mb-4">
                                    <label for="pass">Password</label>
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon4"><font-awesome-icon :icon="['fas', 'key']" /></span>
                                        <input
                                            type="password"
                                            placeholder="pass"
                                            v-model="userPass"
                                            class="form-control"
                                            id="pass"
                                            :class="{ 'is-valid': isUserPassValid && isUserPassFocus, 'is-invalid': !isUserPassValid && isUserPassFocus }"
                                            @focus="isUserPassFocus = true"
                                        />
                                        <div class="valid-feedback"></div>
                                        <div class="invalid-feedback">1개 이상의 특수문자(~`!@#$%^&*()_+|&lt;>?:{}), 대소문자 및 숫자를 포함하고 8자리 이상이여야 합니다.</div>
                                    </div>
                                </div>
                                <!-- End of Form -->
                                <!-- Form -->
                                <div class="form-group mb-4">
                                    <label for="confirm_pass">Confirm Password</label>
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon5"><font-awesome-icon :icon="['fas', 'key']" /></span>
                                        <input
                                            type="password"
                                            placeholder="Confirm Password"
                                            class="form-control"
                                            :class="{ 'is-valid': isUserCPassValid && isUserCPassFocus, 'is-invalid': !isUserCPassValid && isUserCPassFocus }"
                                            v-model="userCPass"
                                            @input="validatePass2"
                                            @focus="isUserCPassFocus = true"
                                        />
                                        <div class="valid-feedback"></div>
                                        <div class="invalid-feedback">비밀번호가 일치하지 않습니다.</div>
                                    </div>
                                </div>
                                <!-- End of Form -->

                                <!-- Form -->
                                <div class="form-group mb-4">
                                    <label for="userPhone">Phone Number</label>
                                    <div class="input-group">
                                        <span class="input-group-text" id="basic-addon3"><font-awesome-icon :icon="['fas', 'phone']" /></span>
                                        <input
                                            type="tel"
                                            class="form-control"
                                            placeholder="Enter your phone number ex)xxx-xxxx-xxxx"
                                            :class="{ 'is-valid': isUserPhoneFocus && isUserPhoneFocus, 'is-invalid': !isUserPhoneValid && isUserPhoneFocus }"
                                            v-model="userPhone"
                                            @focus="isUserPhoneFocus = true"
                                            id="userPhone"
                                        />
                                        <div class="valid-feedback"></div>
                                        <div class="invalid-feedback">휴대전화 번호를 입력해주세요.(- 포함)</div>
                                    </div>
                                </div>
                                <!-- End of Form -->

                                <!-- Form -->
                                <div class="form-group mb-4">
                                    <div class="form-check form-check-inline" v-for="(code, index) in codeList" :key="index">
                                        <input class="form-check-input" :value="code.code" name="code.code" type="radio" v-model="userRank" />
                                        <label class="form-check-label">{{ code.codeName }}</label>
                                    </div>
                                </div>
                                <!-- End of Form -->
                            </div>
                            <div class="d-grid">
                                <button @click="join" class="btn btn-dark">Sign up</button>
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
                            <!-- </form> -->

                            <div class="d-flex justify-content-center align-items-center mt-4">
                                <span class="fw-normal">
                                    가입된 계정이 있습니까?
                                    <router-link to="/login" class="fw-bold"> Sign in </router-link>

                                    <!-- <button id="show-modal" @click="showModal = true">Show Modal</button> -->
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </main>
</template>

<style scoped></style>
