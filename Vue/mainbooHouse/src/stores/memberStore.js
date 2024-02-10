import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { useRouter } from "vue-router";
import { jwtDecode } from "jwt-decode";

import { HttpStatusCode } from "axios";
import { userLoginApi, userAuthApi, refreshTokenApi, getUserInfoApi, userLogoutApi, userJoinApi, userDeleteApi, userEditApi } from "../api/memberApi";

export const useMemberStore = defineStore("memberStore", () => {

    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userInfo = ref(null);
    const isValidToken = ref(false);

    const loginEmail = ref("");
    const router = useRouter();
    const userLogin = async (loginUser) => {
        await userLoginApi(
            loginUser,
            (response) => {
                if (response.status === HttpStatusCode.Created) {
                    let { data } = response;
                    let accessToken = data["accessToken"];
                    let refreshToken = data["refreshToken"];
                    isLogin.value = true;
                    isLoginError.value = false;
                    isValidToken.value = true;
                    loginEmail.value = loginUser.email;

                    sessionStorage.setItem("accessToken", accessToken);
                    sessionStorage.setItem("refreshToken", refreshToken);

                } else {
                    console.log("로그인 실패");
                    isLogin.value = false;
                    isLoginError.value = true;
                    isValidToken.value = false;
                }
            },
            (error) => {
                console.log(error);
            }
        );
    };

    // 새로고침시, stores/memberSore.js에 있는
    // 현재 로그인한 유저 정보(userInfo)가 날아가는 문제가 있는데,
    // 이를 해결하기 위해 refreshToken을 서버에 보내 UserInfo를 다시 가져오는 것

    const userLogout = async () => {
        console.log("memberStore.js userLogout 실행");
        //session에 있는 토큰 지우고.
        sessionStorage.removeItem("accessToken");
        sessionStorage.removeItem("refreshToken");
        console.log("Token 삭제");
        //현재 유저에 대한 db의 token 데이터도 지우고.
        await userLogoutApi(
            userInfo.value,
            (response) => {
                console.log("userLogout response");
                isLogin.value = false;
                isLoginError.value = false;
                userInfo.value = null;
                isValidToken.value = false;
                loginEmail.value = "";
            },
            (error) => {
                console.log("userLogout error");
                //로그아웃 실패는 뭐 어떻게해야하지????
            }
        );

        // isLogin.value = false;
        // isLoginError.value = false;
        // userInfo.value = null;
        // isValidToken.value = false;
    };

    const userJoin = async (joinUser) => {
        console.log("memberStore.js userJoin 실행");
        await userJoinApi(
            joinUser,
            (response) => {
                console.log("userJoin response");
                if (response.status === 200) {
                    console.log("status == OK");
                    alert("회원가입이 완료되었습니다. 가입하신 정보로 로그인 해주세요.");
                    router.push({ name: "login" });
                } else {
                    console.log("error : wrong response. ");
                }
            },
            (error) => {
                console.log("userJoin error");
                if (error.response.status == 401) {
                    console.log("status == UNAUTHORIZED");
                    alert("이미 존재하는 회원입니다.");
                } else {
                    console.log(error);
                }
            }
        );
    };

    const userDelete = async (email) => {
        console.log("memberStore.js userDelte 실행");
        userDeleteApi(
            userInfo.value,
            (response) => {
                console.log("userDelete response");
                //isLogin, userInfo 등등 초기화.
                //session 비우기
                if (response.status == 200) {
                    isLogin.value = false;
                    isLoginError.value = false;
                    userInfo.value = null;
                    isValidToken.value = false;
                    loginEmail.value = "";
                    sessionStorage.removeItem("accessToken");
                    sessionStorage.removeItem("refreshToken");
                }
            },
            (error) => {
                console.log("userDelete error");
            }
        );
    };

    const userEdit = async (updateUserInfo, originalEmail) => {
        console.log("memberStore.js userEdit 실행");
        await userEditApi(
            {
                update: updateUserInfo,
                email: originalEmail,
            },
            (response) => {
                console.log("userEdit response");
            },
            (error) => {
                console.log("userEdit error");
            }
        );
    };

    const getUserInfo = async (token) => {
        let decodeToken = jwtDecode(token);
        console.log("getUserInfo 호출");
        await getUserInfoApi(
            token,
            (response) => {
                console.log("getUserInfo : response");
                userInfo.value = response.data.userInfo;
                isLogin.value = true;
                isLoginError.value = false;
                isValidToken.value = true;
            },
            (error) => {
                console.log("getUserInfo error : ", error);
                isLogin.value = false;
                isLoginError.value = true;
                isValidToken.value = false;
            }
        );
    };

    const userAuth = async (token) => {
        console.log("memberStore.js userAuth 실행");
        let decodeToken;
        try {
            decodeToken = jwtDecode(token);
        } catch (error) {
            console.log("jwtDecode 실행 실패. token 훼손됨.");
            if (isLogin.value) {
                //로그인은 잘 되어있음. 토큰 재발급
                refreshToken();
            } else {
                //로그인도 안되어있으니 잘못된 접근. 재로그인 필요.
                //사실 애초에 이 userAuth가 실행되려면 isLogin == true인 상황이어야함.
                //여기 else가 실행될 일은 없을거임.
                isValidToken.value = false;
            }
            return;
        }
        console.log("정상실행");
        userAuthApi(
            {
                email: decodeToken.email,
                token: sessionStorage.getItem("accessToken"),
                // token : sessionStorage.getItem("zzzz")
            },
            (response) => {
                if (response.status === HttpStatusCode.Ok) {
                    console.log("userAuthApi response");
                    let { data } = response;
                    userInfo.value = response.data.userInfo;
                } else {
                    console.log("유저 정보가 없음.");
                }
            },
            (error) => {
                console.log("userAuth error. ", error);
                // 토큰이 만료되었거나 문제가 생겼음. 따라서 토큰을 재발급 하거나 재로그인을 요청해야함.
                // 원래 로그인이 되어있던 상태면 재발급을,
                if (isLogin.value) {
                    isValidToken.value = false;
                    refreshToken();
                } else {
                    // 애초에 로그인이 안되어있다면 다시 로그인 하라고 요청.
                    isLogin.value = false;
                    isLoginError.value = false;
                    userInfo.value = null;
                    isValidToken.value = false;
                    loginEmail.value = "";
                }
            }
        );
    };

    const refreshToken = async () => {
        console.log("memberStore.js refreshToken 실행");
        refreshTokenApi(
            JSON.stringify(userInfo.value),
            (response) => {
                if (response.status === HttpStatusCode.Created) {
                    let accessToken = response.data["accessToken"];
                    console.log("accessToken 재발급 완료");
                    sessionStorage.setItem("accessToken", accessToken);
                    isValidToken.value = true;
                }
            },
            async (error) => {
                //여러 에러가 있을 수 있는데, 일단 기간 만료부터.
                if (error.response.status === HttpStatusCode.Unauthorized) {
                    // refreshToken이 만료된 경우.
                    // 강제 logout. logout 요청
                    console.log("refreshTOken error : UNAUTHORIZED. logout 실시");
                }
            }
        );
    };

    return {
        isLogin,
        isLoginError,
        userInfo,
        isValidToken,
        userLogin,
        userLogout,
        userEdit,
        userJoin,
        userDelete,
        getUserInfo,
        userAuth,
    };
});
