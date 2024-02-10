<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "../stores/memberStore";
import { storeToRefs } from "pinia";
import deleteModal from "../components/modals/deleteModal.vue";
const showModal = ref(false);
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const { userEdit, userDelete } = memberStore;
const router = useRouter();

const updateName = ref("");
const updateEmail = ref("");
const updatePass = ref("");
const updateRank = ref("");
const updateMessage = ref("");
const updateImg = ref("");

onMounted(() => {
    updateName.value = userInfo.value.name;
});

const changeUpdateName = (e) => {
    updateName.value = e.target.value;
};

const changeUpdatePasword = (e) => {
    updatePass.value = e.target.value;
};

const changeUpdateImg = (e) => {
    updateImg.value = e.target.value;
    console.log(updateImg.value, "changeUpdateImg에서 확잉ㄴ");
};

const clickEditBtn = () => {
    console.log("saveInfo 실행.");
    console.log("updateName.value : ", updateName.value);
    // let updateUserInfo = userInfo.value;
    if (!updateName.value || !updatePass.value) {
        alert("수정하기 위해 빈칸 없이 모두 작성해주세요");
        return;
    }

    let updateUserInfo = JSON.parse(JSON.stringify(userInfo.value));
    updateUserInfo.name = updateName.value;
    updateUserInfo.pass = updatePass.value;
    updateUserInfo.user_img = updateImg.value;
    console.log(updateUserInfo.user_img, "여기까지 확인");
    userEdit(updateUserInfo, userInfo.value.email);
    alert("수정이 완료되었습니다!!");
    router.go(0); //새로고침
};

const deleteUser = () => {
    //유저 데이터 DB에 가서 지우고,
    //logout까지 진행되어야함.

    try {
        userDelete();
        alert("회원탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
        showModal.value = false;
        router.push({ name: "home" });
    } catch (error) {
        console.log("MyPageView deleteUser Error");
    }

    // 단, memberStore.js의 logout은 db의 refreshToken을 지우는 기능까지 있기 때문에,
    // 탈퇴시 로그아웃은 그냥 여기서 구현한다.

    //   >> 로그아웃시, session 비우고 memberStore에 있는 값들 (userinfo, isLogin 등등) 다 초기화.
    //     >>이건 MemberStore.js 에서 구현.

    //그리고 모달 끄고 HomePage로.
};

///////////////////////////

function resizeImg(file) {
    const MAX_WIDTH = 800; // 이미지의 최대 너비
    const MAX_HEIGHT = 600; // 이미지의 최대 높이

    // FileReader 객체 생성
    const reader = new FileReader();
    // 파일 로드가 완료되면 실행되는 이벤트 핸들러
    reader.onload = function (event) {
        // 이미지 객체 생성
        const img = new Image();
        img.src = event.target.result;
        // 이미지 로드가 완료되면 실행되는 이벤트 핸들러
        img.onload = function () {
            let width = img.width; // 이미지의 너비
            let height = img.height; // 이미지의 높이

            // 이미지의 가로 세로 비율에 따라 리사이징 수행
            if (width > height) {
                // 이미지의 가로가 세로보다 큰 경우
                if (width > MAX_WIDTH) {
                    // 가로가 최대 너비보다 크면 비율에 맞게 조정
                    height *= MAX_WIDTH / width;
                    width = MAX_WIDTH;
                }
            } else {
                // 이미지의 세로가 가로보다 크거나 같은 경우
                if (height > MAX_HEIGHT) {
                    // 세로가 최대 높이보다 크면 비율에 맞게 조정
                    width *= MAX_HEIGHT / height;
                    height = MAX_HEIGHT;
                }
            }

            // 캔버스 생성하여 이미지 리사이징 및 압축
            const canvas = document.createElement("canvas");
            canvas.width = width;
            canvas.height = height;

            const ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0, width, height);

            // 리사이즈 및 압축된 이미지 데이터를 base64 형태로 가져옴
            const resizedImage = canvas.toDataURL("image/jpeg", 0.7); // 이미지 품질 조절

            // 'resizedImage'에 리사이즈/압축된 이미지 데이터가 포함됨
            // 이를 사용자 객체에 할당하거나 서버에 업로드할 수 있음
            updateImg.value = resizedImage;
            userInfo.value.user_img = resizedImage;
            console.log(updateImg.value, "이름좀 봐봐");
        };
    };
    // 파일을 Data URL로 변환하여 읽음
    reader.readAsDataURL(file);
}

function uploadImg(event) {
    const file = event.target.files[0]; // 선택된 파일 가져오기
    resizeImg(file);
    const reader = new FileReader();
    reader.onload = (e) => {
        // userInfo.value.user_img = e.target.result;
        // updateImg.value = e.target.result; // 이미지를 사용자 정보에 할당
        // console.log(updateImg.value);
    };
    // reader.readAsDataURL(file); // 파일을 읽어서 데이터 URL로 변환
}
</script>

<template>
    <main class="">
        <div class="main bg-light p-4">
            <div class="row">
                <div class="col-12 col-xl-8">
                    <div class="card card-body shadow-sm mb-4">
                        <form v-on:submit.prevent>
                            <div class="row mt-3">
                                <div class="col-md-12 mb-3">
                                    <div>
                                        <label for="userName">Name</label>
                                        <input
                                            :value="userInfo.name"
                                            v-on:input="changeUpdateName"
                                            class="form-control"
                                            id="userName"
                                            type="text"
                                            placeholder="Enter your name"
                                            required
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 mb-3">
                                    <div class="form-group">
                                        <label for="userEmail">Email</label>
                                        <input :value="userInfo.email" class="form-control" id="userEmail" type="email" placeholder="ssafy@ssafy.com" readonly />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 mb-3">
                                    <div class="form-group">
                                        <label for="userPassword">Password</label>
                                        <!-- <div v-if="this.$store.state.userInfo.isKakao">
                      <input
                        :value="this.$store.state.userInfo.userPassword"
                        v-on:input="updatePass"
                        class="form-control"
                        id="userPassword"
                        type="password"
                        placeholder="********"
                        readonly
                      />
                      <p>KAKAO LOGIN 상태입니다.</p>
                    </div> -->
                                        <!-- <div v-if="this.$store.state.userInfo.isKakao == false"> -->
                                        <div v-if="true">
                                            <input
                                                v-on:input="changeUpdatePasword"
                                                class="form-control"
                                                id="userPassword"
                                                type="password"
                                                placeholder="수정할 비밀번호 입력"
                                                required
                                            />
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-12 mb-3">
                                    <div class="form-group">
                                        <label for="userRank">Rank</label>
                                        <input :value="'User'" v-on:input="updateRank" class="form-control" id="userRank" type="text" readonly />
                                    </div>
                                </div>
                            </div>

                            <!-- <div class="row">
                <div class="col-sm-12 mb-3">
                  <div class="form-group">
                    <label for="userMessage">Profile Message</label>
                    <textarea
                      :value="'템프 프로필 메세지'"
                      v-on:input="updateMessage"
                      class="form-control"
                      id="userMessage"
                      row="4"
                      placeholder="프로필 메세지...."
                      required
                    ></textarea>
                  </div>
                </div>
              </div> -->
                        </form>
                        <div class="row mb-3 mt-3">
                            <div class="col">
                                <div class="">
                                    <div class="" style="float: right">
                                        <button type="submit" id="successAlert" @click="clickEditBtn" class="btn btn-outline-dark">
                                            <font-awesome-icon :icon="['fas', 'user-edit']" /> 수정
                                        </button>
                                        <button type="submit" @click="showModal = true" class="btn btn-outline-danger"><font-awesome-icon :icon="['fas', 'eraser']" /> 탈퇴</button>

                                        <!-- <button id="show-modal" @click="showModal = true">Show Modal</button> -->

                                        <Teleport to="body">
                                            <deleteModal :showModal="showModal" @close="showModal = false" @closeModal="showModal = false" @deleteUser="deleteUser">
                                                <template #header>
                                                    <h3>정말로 계정을 탈퇴하시겠습니까?</h3>
                                                </template>
                                            </deleteModal>
                                        </Teleport>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-xl-4">
                    <div class="row">
                        <div class="col-12 mb-4">
                            <div class="card shadow-sm text-center p-0">
                                <div class="profile-cover rounded-top" style="background: linear-gradient(to bottom, #262b40, #ffffff)"></div>
                                <div class="card-body pb-5">
                                    <!-- <img src="@/assets/img/ssafy_logo.png" class="user-avatar large-avatar rounded-circle mx-auto mt-n7 mb-4" alt="Neil Portrait" /> -->
                                    <img
                                        v-if="userInfo.user_img"
                                        :src="userInfo.user_img"
                                        class="user-avatar large-avatar rounded-circle mx-auto mt-n7 mb-4"
                                        alt="Neil Portrait"
                                        style="width: 250px"
                                    />
                                    <img v-else src="@/assets/img/ssafy_logo.png" class="user-avatar large-avatar rounded-circle mx-auto mt-n7 mb-4" alt="Neil Portrait" />
                                    <!-- <h4 class="h3">{{ this.$store.state.userInfo.userName }}</h4> -->
                                    <h4 class="h3">{{ userInfo.name }}</h4>

                                    <!-- <p class="text-gray mb-4">{{ this.$store.state.userInfo.userMessage }}</p> -->
                                    <!-- <p class="text-gray mb-4">임시 유저 메세지</p> -->
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="card card-body shadow-sm mb-4">
                                <h2 class="h5 mb-4">Select profile photo</h2>
                                <div class="d-flex align-items-center mb-4">
                                    <div class="me-3">
                                        <!-- Avatar -->
                                        <div class="user-avatar xl-avatar">
                                            <img class="rounded" v-if="imageUrl" :src="imageUrl" alt="change avatar" />
                                        </div>
                                    </div>
                                    <div class="file-field">
                                        <div class="d-flex justify-content-xl-center ms-xl-3">
                                            <div class="d-flex">
                                                <span class="icon icon-md"><font-awesome-icon :icon="['fas', 'paperclip']" class="me-3" /></span>
                                                <input ref="imageInput" type="file" hidden @change="onChangeImages" id="inputFileUploadInsert" />
                                                <div class="d-md-block text-left">
                                                    <!-- <div class="fw-normal text-dark mb-1">Choose Image</div> -->
                                                    <div class="text-gray small">JPG, GIF or PNG. Max size of 800K</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="btn-group mb-2" style="float: left">
                                            <!-- <button type="submit" @click="onClickImageUpload" class="btn btn-outline-dark btn-sm">
                                                <font-awesome-icon :icon="['fas', 'image']" /> 사진 선택
                                            </button>
                                            <button type="submit" @click="onClickImageSend" class="btn btn-sm btn-outline-secondary">
                                                <font-awesome-icon :icon="['fas', 'upload']" /> 사진 업로드
                                            </button>-->
                                            <label for="fileBtn" class="btn btn-sm file-input-label">
                                                <span><font-awesome-icon :icon="['fas', 'upload']" /> 사진 업로드</span>
                                            </label>
                                            <input id="fileBtn" type="file" @change="uploadImg" accept="image/*" class="file-input" v-on:input="changeUpdateImg" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--/////////////////////////    -->
        <!-- 
        <div class="item1">
            <img v-if="user.user_img" :src="user.user_img" />
            <p>{{ user.user_name }}님 반갑습니다 :)</p>
            <button class="btn btn-outline-primary btn-sm">이미지 업로드</button>
            <label class="file-input-label">
                <span>이미지 업로드</span>
                <input type="file" @change="uploadImg" accept="image/*" class="file-input" />
            </label>
        </div>
 -->
        <!-- <secession-modal v-on:call-parent-secession-close="closeAfterSecession"></secession-modal> -->
    </main>
</template>

<style>
.file-input-label {
    position: relative;
    display: inline-block;
    width: inherit;
    height: inherit;
}

.file-input {
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    width: 100%;
    height: 100%;
    cursor: pointer;
}

.file-input-label span {
    display: inline-block;
    padding: 6px 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: #f5f5f5;
    color: #333;
    cursor: pointer;
}
</style>
